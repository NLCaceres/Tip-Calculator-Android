package itp341.caceres.nicholas.tipCalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import itp341.caceres.nicholas.tipCalculator.composables.MainScreen

class MainActivity : ComponentActivity() {
  private var viewModel = ViewModelMain()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MainScreen(viewModel)
    }
  }
}
