package itp341.caceres.nicholas.tipCalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import itp341.caceres.nicholas.tipCalculator.composables.MainScreen
import itp341.caceres.nicholas.tipCalculator.composables.reusable.MainAppBar
import itp341.caceres.nicholas.tipCalculator.composables.reusable.theme.AppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val viewModel: ViewModelMain = viewModel()
      AppWithTheme(viewModel)
    }
  }
}

@Composable
fun AppWithTheme(viewModel: ViewModelMain) {
  AppTheme {
    MainAppBar("Tip Calculator") { innerPadding ->
      Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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
  AppWithTheme(viewModel)
}
