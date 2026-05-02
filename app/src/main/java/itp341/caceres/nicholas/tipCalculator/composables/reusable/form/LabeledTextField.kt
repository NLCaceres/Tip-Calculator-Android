package itp341.caceres.nicholas.tipCalculator.composables.reusable.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** A reusable form composable that breaks the label and textField into 2 composables in a Row.
 * The label takes 30% of available Row width space with the textField filling the remaining 70%.
 * The textField is still somewhat customizable, allowing the user to specify a placeholder String,
 * prefix String, and keyboard type */
@Composable
fun LabeledTextField(
  label: String, text: String, errorMsg: String = "", placeholder: String = "", prefix: String = "",
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  textFieldColors: TextFieldColors = defaultColors(), onTextUpdate: (String) -> Unit
) {
  Row(Modifier.padding(16.dp, 10.dp, 16.dp, 4.dp), verticalAlignment = Alignment.CenterVertically) {
    Text(label, Modifier.weight(0.3f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    TextField(text, { onTextUpdate(it) }, Modifier.weight(0.7f), singleLine = true,
      placeholder = { Text(placeholder) }, prefix = { Text(prefix) }, keyboardOptions = keyboardOptions,
      supportingText = { Text(errorMsg, color = Color.Red) }, isError = errorMsg.isNotBlank(),
      colors = textFieldColors
    )
  }
}

@Composable
private fun defaultColors() = TextFieldDefaults.colors(
  focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
  errorContainerColor = Color.Transparent, errorTextColor = Color.Red, errorPrefixColor = Color.Red
)

@Preview(widthDp = 320, heightDp = 150, showBackground = true)
@Composable
fun LabeledTextFieldPreview() {
  var text by remember { mutableStateOf("") }
  LabeledTextField("Bill Amount", text) { text = it }
}

@Preview(widthDp = 320, heightDp = 150, showBackground = true)
@Composable
fun ErrorLabeledTextFieldPreview() {
  var text by remember { mutableStateOf("abc") }
  var textErr by remember { mutableStateOf("Big problem!") }
  LabeledTextField("Bill Amount", text, textErr) { text = it }
}
