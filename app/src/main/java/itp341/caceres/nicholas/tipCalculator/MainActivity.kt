package itp341.caceres.nicholas.tipCalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import itp341.caceres.nicholas.tipCalculator.composables.MainScreen

class MainActivity : ComponentActivity() {
  private var viewModel = ViewModelMain()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { Text("Tip Calculator") })
      }) { innerPadding ->
        MainScreen(viewModel, Modifier.padding(innerPadding))
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppPreview() {
  val viewModel = ViewModelMain()
  Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
    TopAppBar(title = { Text("Tip Calculator") })
  }) { innerPadding ->
    MainScreen(viewModel, Modifier.padding(innerPadding))
  }
}
