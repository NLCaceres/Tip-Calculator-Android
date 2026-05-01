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
import androidx.compose.runtime.mutableIntStateOf
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

/** A reusable composable for forms to add a Dropdown to select from a list of text options.
 * When an option is selected, the callback emits the option's original list index to help
 * update the selection from its parent. It maintains its own state to control its expansion.
 * It has a bolded label to describe the list that takes 30% of its parent Row's width while
 * the dropdown takes up the remaining 70%. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledDropdown(label: String, options: List<String>, selectedIndex: Int, updateSelection: (Int) -> Unit) {
  var expanded by remember { mutableStateOf(false) }

  Row(Modifier.padding(vertical = 10.dp, horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
    Text(label, Modifier.weight(0.3f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }, Modifier.weight(0.7f)) {
      TextField(value = options[selectedIndex], onValueChange = {}, readOnly = true,
        modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
          focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent
        ),
      )
      ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        options.forEachIndexed { i, option ->
          DropdownMenuItem(text = { Text(option) }, onClick = { expanded = false; updateSelection(i) })
        }
      }
    }
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
private fun DropdownPreview() {
  val options = listOf("Option 1", "Option 2", "Option 3")
  var selectedIndex by remember { mutableIntStateOf(0) }

  Column {
    LabeledDropdown("List", options, selectedIndex) { selectedIndex = it }
  }
}
