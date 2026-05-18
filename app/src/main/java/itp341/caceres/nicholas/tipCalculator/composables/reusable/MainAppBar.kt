package itp341.caceres.nicholas.tipCalculator.composables.reusable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(title: String, content: @Composable ((PaddingValues) -> Unit)) {
  Scaffold(topBar = { TopAppBar(title = { Text(title) }) }) { innerPadding ->
    content(innerPadding)
  }
}

@Preview
@Composable
fun MainAppBarPreview() {
  MainAppBar("Example") {
    Text("Hello world!", Modifier.padding(it).padding(horizontal = 18.dp))
  }
}