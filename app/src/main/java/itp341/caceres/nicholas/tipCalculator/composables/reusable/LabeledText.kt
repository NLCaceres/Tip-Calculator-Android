package itp341.caceres.nicholas.tipCalculator.composables.reusable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledText(
  label: String, value: String, modifier: Modifier = Modifier,
  labelWeight: FontWeight = FontWeight.Bold, labelAlign: TextAlign = TextAlign.Start
) {
  Row(modifier.fillMaxWidth()) {
    Text(label, Modifier.weight(0.3f), fontSize = 18.sp, fontWeight = labelWeight, textAlign = labelAlign)
    Text("$${value}", Modifier.weight(0.7f).padding(start = 16.dp), fontSize = 18.sp)
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
fun LabelTextPreview() {
  Column(Modifier.padding(start = 32.dp)) {
    LabeledText("Bill Amount", "100.00", modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
    LabeledText("Tip Amount", "20.00")
    LabeledText("Tip", "20.00", modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
    LabeledText("Total", "120.00")
  }
}
