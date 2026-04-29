package itp341.caceres.nicholas.tipCalculator.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import itp341.caceres.nicholas.tipCalculator.composables.reusable.LabeledText

@Composable
fun PerPerson(tip: String, total: String) {
  Row(Modifier.padding(vertical = 10.dp, horizontal = 16.dp)) {
    Text("Per Person", Modifier.weight(0.3f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    Column(Modifier.weight(0.7f).padding(start = 16.dp)) {
      LabeledText("Tip", tip, Modifier.padding(bottom = 16.dp), FontWeight.Medium, TextAlign.End)
      LabeledText("Total", total, labelWeight = FontWeight.Medium, labelAlign = TextAlign.End)
    }
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
fun PerPersonPreview() {
  Column {
    PerPerson("10.00", "20.00")
    PerPerson("$10.00", "$20.00")
  }
}
