package itp341.caceres.nicholas.tipCalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ViewModelMain : ViewModel() {
  private val _bill = MutableStateFlow(Bill("0.00", 0.15f, 1))
  val bill: StateFlow<Bill> = _bill

  fun updateAmount(amount: String) {
    _bill.value = _bill.value.copy(amount = amount)
  }
  fun updatePercent(percent: Float) {
    _bill.value = _bill.value.copy(percent = percent)
  }
  fun updateSplit(split: String) {
    when (split) {
      "No" -> _bill.value = _bill.value.copy(split = 1)
      "2 ways" -> _bill.value = _bill.value.copy(split = 2)
      "3 ways" -> _bill.value = _bill.value.copy(split = 3)
      "4 ways" -> _bill.value = _bill.value.copy(split = 4)
    }
  }
}
