package itp341.caceres.nicholas.tipCalculator

import java.math.BigDecimal
import java.math.RoundingMode

data class Bill(val amount: String, val percent: Float, var split: Int) {
  companion object {
    private const val ZERO_BILL = "0.00"
  }
  private val roundedPercent
    get() = percent.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)

  val tipNum: String
    get() = amount.toBigDecimalOrNull()?.multiply(roundedPercent)?.
      setScale(2, RoundingMode.HALF_EVEN)?.toString() ?: ZERO_BILL
  val tip: String get() = "$$tipNum"

  val totalNum: String
    get() = amount.toBigDecimalOrNull()?.
      add(BigDecimal(tipNum))?.setScale(2, RoundingMode.HALF_EVEN)?.
        toString() ?: ZERO_BILL
  val total: String get() = "$$totalNum"

  val perPersonTipNum: String
    get() = tipNum.toBigDecimalOrNull()?.
      divide(BigDecimal(if (split > 0) split else 1), 2, RoundingMode.HALF_EVEN)?.
        toString() ?: ZERO_BILL
  val perPersonTip: String get() = "$$perPersonTipNum"

  val perPersonTotalNum: String
    get() = totalNum.toBigDecimalOrNull()?.
      divide(BigDecimal(if (split > 0) split else 1), 2, RoundingMode.HALF_EVEN)?.
        toString() ?: ZERO_BILL
  val perPersonTotal: String get() = "$$perPersonTotalNum"
}
