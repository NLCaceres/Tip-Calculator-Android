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

@Composable
fun PerPerson(tip: String, total: String) {
  Row(Modifier.padding(vertical = 10.dp, horizontal = 16.dp)) {
    Text("Per Person", Modifier.weight(0.3f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    Column(Modifier.weight(0.7f).padding(start = 16.dp)) {
      Row(Modifier.padding(bottom = 16.dp)) {
        Text("Tip", Modifier.weight(0.3f).padding(end = 16.dp), fontSize = 18.sp, textAlign = TextAlign.End)
        Text("$$tip", Modifier.weight(0.7f), fontSize = 18.sp)
      }
      Row {
        Text("Total", Modifier.weight(0.3f).padding(end = 16.dp), fontSize = 18.sp, textAlign = TextAlign.End)
        Text("$$total", Modifier.weight(0.7f), fontSize = 18.sp)
      }
    }
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
fun PerPersonPreview() {
  PerPerson("10.00", "20.00")
}
