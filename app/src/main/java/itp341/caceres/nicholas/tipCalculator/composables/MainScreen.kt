package itp341.caceres.nicholas.tipCalculator.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import itp341.caceres.nicholas.tipCalculator.Bill
import itp341.caceres.nicholas.tipCalculator.ViewModelMain
import itp341.caceres.nicholas.tipCalculator.composables.reusable.LabeledText
import itp341.caceres.nicholas.tipCalculator.composables.reusable.form.LabeledDropdown
import itp341.caceres.nicholas.tipCalculator.composables.reusable.form.LabeledSlider
import itp341.caceres.nicholas.tipCalculator.composables.reusable.form.LabeledTextField

@Composable
fun MainScreen(viewModel: ViewModelMain = viewModel(), modifier: Modifier = Modifier) {
  val bill by viewModel.bill.collectAsState()
  val billError by viewModel.billError.collectAsState()
  MainScreen(
    bill, billError, viewModel.splitIndex,
    viewModel::updateAmount, viewModel::updatePercent, viewModel::updateSplit,
    modifier
  )
}

@Composable
fun MainScreen(
  bill: Bill, billError: String, splitIndex: Int,
  onAmountUpdate: (String) -> Unit, onPercentUpdate: (Float) -> Unit, onSplitUpdate: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(modifier) {
    LabeledTextField(
      "Bill Amount", bill.amount, billError, "0.00", "$", KeyboardOptions(keyboardType = KeyboardType.Decimal)
    ) { onAmountUpdate(it) }
    LabeledSlider("Percent", bill.percent, 0f..0.30f, 31) { onPercentUpdate(it) }
    LabeledText("Tip", bill.tip, Modifier.padding(20.dp, 20.dp, 10.dp, 10.dp))
    LabeledText("Total", bill.total, Modifier.padding(20.dp, 20.dp, 10.dp, 10.dp))
    HorizontalDivider(modifier = Modifier.padding(10.dp, 15.dp), thickness = 2.dp, color = Color.DarkGray)
    LabeledDropdown("Split Bill?", ViewModelMain.options, splitIndex) { onSplitUpdate(it) }
    if (bill.split > 1) {
      PerPerson(bill.perPersonTip, bill.perPersonTotal)
    }
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
fun MainScreenPreview() {
  MainScreen(Bill("", 0.15f, 1), "", 0, {}, {}, {})
}