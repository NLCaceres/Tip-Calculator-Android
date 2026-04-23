package itp341.caceres.nicholas.tipCalculator.composables.reusable.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledTextField(label: String, text: String, onTextUpdate: (String) -> Unit) {
  Row(Modifier.padding(vertical = 10.dp, horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
    Text(label, Modifier.weight(0.3f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    TextField(text, onValueChange = { onTextUpdate(it) },
      modifier = Modifier.weight(0.7f), placeholder = { Text("0.00") }, prefix = { Text("$") },
      colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
      ),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
  }
}

@Preview(widthDp = 320, heightDp = 500, showBackground = true)
@Composable
fun LabeledTextFieldPreview() {
  var text by remember { mutableStateOf("") }
  LabeledTextField("Bill Amount", text) { text = it }
}
