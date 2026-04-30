package itp341.caceres.nicholas.tipCalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import itp341.caceres.nicholas.tipCalculator.composables.MainScreen

class MainActivity : AppCompatActivity() {
  private lateinit var mainComposeView: ComposeView
  private var viewModel = ViewModelMain()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mainComposeView = findViewById(R.id.main_compose_view)
    mainComposeView.setContent {
      MainScreen(viewModel)
    }
  }
}
