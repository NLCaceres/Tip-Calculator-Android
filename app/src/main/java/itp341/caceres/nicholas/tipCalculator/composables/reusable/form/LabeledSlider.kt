package itp341.caceres.nicholas.tipCalculator.composables.reusable.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

/** A reusable composable for forms to add a percent Slider/Seekbar with a Text read-out of the
 * Slider's value and bolded label, updatable in real-time via `onSliderChange` callback.
 * The label takes 35% of the Row's width, the value Text 15% and Slider 50%. */
@Composable
fun LabeledSlider(label: String, percent: Float, onSliderChange: (Float) -> Unit) {
  Row(Modifier.padding(horizontal = 16.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
    Text(label, Modifier.weight(0.35f), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    Text( "${(percent * 100).roundToInt()}%", Modifier.weight(0.15f), fontSize = 18.sp)
    Slider(
      percent, onValueChange = { onSliderChange(it) }, modifier = Modifier.weight(0.5f),
      valueRange = 0f..0.30f, steps = 31, colors = SliderDefaults.colors(
        activeTickColor = Color.Transparent, inactiveTickColor = Color.Transparent
      )
    )
  }
}

@Preview(widthDp = 360, heightDp = 500, showBackground = true)
@Composable
fun LabeledSliderPreview() {
  var percentage by remember { mutableFloatStateOf(0.15f) }
  Column {
    LabeledSlider("Percent", percentage) { percentage = it }
  }
  LabeledSlider("Percent", percentage) { percentage = it }
}

