package itp341.caceres.nicholas.tipCalculator.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import itp341.caceres.nicholas.tipCalculator.ViewModelMain
import itp341.caceres.nicholas.tipCalculator.composables.reusable.LabeledText
import itp341.caceres.nicholas.tipCalculator.composables.reusable.form.LabeledDropdown
import itp341.caceres.nicholas.tipCalculator.composables.reusable.form.LabeledSlider
import itp341.caceres.nicholas.tipCalculator.composables.reusable.form.LabeledTextField

@Composable
fun MainScreen(viewModel: ViewModelMain) {
  val bill by viewModel.bill.collectAsState()
  Column {
    LabeledTextField("Bill Amount", bill.amount) { viewModel.updateAmount(it) }
    LabeledSlider("Percent", bill.percent) { viewModel.updatePercent(it) }
    LabeledText("Tip", bill.tip, Modifier.padding(20.dp, 20.dp, 10.dp, 10.dp))
    LabeledText("Total", bill.total, Modifier.padding(20.dp, 20.dp, 10.dp, 10.dp))
    HorizontalDivider(modifier = Modifier.padding(10.dp, 15.dp), thickness = 2.dp, color = Color.DarkGray)
    LabeledDropdown("Split Bill?", ViewModelMain.options, viewModel.splitIndex) { viewModel.updateSplit(it) }
    PerPerson(bill.perPersonTip, bill.perPersonTotal)
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
fun MainScreenPreview() {
  MainScreen(ViewModelMain())
}