package itp341.caceres.nicholas.tipCalculator.composables.reusable.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledDropdown(label: String, updateSplit: (String) -> Unit) {
  var expanded by remember { mutableStateOf(false) }
  val options = listOf("No", "2 ways", "3 ways", "4 ways")
  var selectedOption by remember { mutableStateOf(options[0]) }

  Row(Modifier.padding(vertical = 10.dp, horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
    Text(label, Modifier.weight(3f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }, Modifier.weight(7f)) {
      TextField(value = selectedOption, onValueChange = {}, readOnly = true,
        modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
          focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent
        ),
      )
      ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        options.forEach { option ->
          DropdownMenuItem(text = { Text(option) }, onClick = {
            selectedOption = option
            expanded = false
            updateSplit(option)
          })
        }
      }
    }
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
private fun DropdownPreview() {
  Column {
    LabeledDropdown("List") { }
  }
}
