package itp341.caceres.nicholas.tipCalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelMain : ViewModel() {
  private val _bill = MutableStateFlow(Bill("", 0.15f, 1))
  val bill: StateFlow<Bill> = _bill

  val splitIndex: Int get() = _bill.value.split - 1

  fun updateAmount(amount: String) {
    _bill.value = _bill.value.copy(amount = amount)
  }
  fun updatePercent(percent: Float) {
    _bill.value = _bill.value.copy(percent = percent)
  }
  fun updateSplit(index: Int) {
    _bill.value = _bill.value.copy(split = index + 1)
  }

  companion object {
    val options = listOf("No", "2 ways", "3 ways", "4 ways")
  }
}
