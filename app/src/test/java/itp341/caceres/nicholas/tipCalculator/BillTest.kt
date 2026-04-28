package itp341.caceres.nicholas.tipCalculator

import org.junit.Assert.assertEquals
import org.junit.Test

class BillTest {

    @Test
    fun `test bill default values`() {
        val bill = Bill("", 0f, 0)
        assertEquals("0.00", bill.tipNum)
        assertEquals("$0.00", bill.tip)
        assertEquals("0.00", bill.totalNum)
        assertEquals("$0.00", bill.total)
        // WHEN split is zero, THEN default to split of 1
        assertEquals("0.00", bill.perPersonTipNum)
        assertEquals("$0.00", bill.perPersonTip)
        assertEquals("0.00", bill.perPersonTotalNum)
        assertEquals("$0.00", bill.perPersonTotal)

        val normalBill = Bill(amount = "100.00", percent = 0.15f, split = 1)
        assertEquals("15.00", normalBill.tipNum)
        assertEquals("$15.00", normalBill.tip)
        assertEquals("115.00", normalBill.totalNum)
        assertEquals("$115.00", normalBill.total)
        assertEquals("15.00", normalBill.perPersonTipNum)
        assertEquals("$15.00", normalBill.perPersonTip)
        assertEquals("115.00", normalBill.perPersonTotalNum)
        assertEquals("$115.00", normalBill.perPersonTotal)
    }

    @Test
    fun `test bill calculations n ways`() {
        val bill = Bill(amount = "100.00", percent = 0.15f, split = 2)
        assertEquals("15.00", bill.tipNum)
        assertEquals("$15.00", bill.tip)
        assertEquals("115.00", bill.totalNum)
        assertEquals("$115.00", bill.total)
        assertEquals("7.50", bill.perPersonTipNum)
        assertEquals("$7.50", bill.perPersonTip)
        assertEquals("57.50", bill.perPersonTotalNum)
        assertEquals("$57.50", bill.perPersonTotal)

        // While the view has a limit to split, the bill class supports any number of splitting
        bill.split = 10
        assertEquals("15.00", bill.tipNum)
        assertEquals("$15.00", bill.tip)
        assertEquals("115.00", bill.totalNum)
        assertEquals("$115.00", bill.total)
        assertEquals("1.50", bill.perPersonTipNum)
        assertEquals("$1.50", bill.perPersonTip)
        assertEquals("11.50", bill.perPersonTotalNum)
        assertEquals("$11.50", bill.perPersonTotal)
    }

    @Test
    fun `test bill rounding`() {
        val bill = Bill(amount = "10.00", percent = 0.15f, split = 3)
        // WHEN tip is calculated, THEN it's (amount * percent) rounded to two decimal places
        assertEquals("1.50", bill.tipNum)
        assertEquals("$1.50", bill.tip)
        // WHEN perPersonTip is calculated, THEN its (tip / split) rounded to two decimal places
        assertEquals("0.50", bill.perPersonTipNum)
        assertEquals("$0.50", bill.perPersonTip)
        // WHEN total is calculated, THEN its (amount + tip) rounded to two decimal places
        assertEquals("11.50", bill.totalNum)
        assertEquals("$11.50", bill.total)
        // WHEN perPersonTotal is calculated, THEN its (amount / split) rounded to two decimal places
        assertEquals("3.83", bill.perPersonTotalNum)
        assertEquals("$3.83", bill.perPersonTotal)

        val evenBill = Bill(amount = "10", percent = 0.15f, split = 3)
        assertEquals("1.50", evenBill.tipNum)
        assertEquals("$1.50", evenBill.tip)
        assertEquals("0.50", evenBill.perPersonTipNum)
        assertEquals("$0.50", evenBill.perPersonTip)
        assertEquals("11.50", evenBill.totalNum)
        assertEquals("$11.50", evenBill.total)
        assertEquals("3.83", evenBill.perPersonTotalNum)
        assertEquals("$3.83", evenBill.perPersonTotal)
    }

    @Test
    fun `test bill with non-number amount value`() {
        val bill = Bill(amount = "abc", percent = 0.15f, split = 1)
        assertEquals("0.00", bill.tipNum)
        assertEquals("$0.00", bill.tip)
        assertEquals("0.00", bill.totalNum)
        assertEquals("$0.00", bill.total)
        assertEquals("0.00", bill.perPersonTipNum)
        assertEquals("$0.00", bill.perPersonTip)
        assertEquals("0.00", bill.perPersonTotalNum)
        assertEquals("$0.00", bill.perPersonTotal)
    }

    @Test
    fun `test bill with zero amount and percent`() {
        val bill = Bill(amount = "0.00", percent = 0.15f, split = 1)
        assertEquals("0.00", bill.tipNum)
        assertEquals("$0.00", bill.tip)
        assertEquals("0.00", bill.totalNum)
        assertEquals("$0.00", bill.total)
        assertEquals("0.00", bill.perPersonTipNum)
        assertEquals("$0.00", bill.perPersonTip)
        assertEquals("0.00", bill.perPersonTotalNum)
        assertEquals("$0.00", bill.perPersonTotal)

        val zeroTipBill = Bill(amount = "100", percent = 0f, split = 1)
        assertEquals("0.00", zeroTipBill.tipNum)
        assertEquals("$0.00", zeroTipBill.tip)
        assertEquals("100.00", zeroTipBill.totalNum)
        assertEquals("$100.00", zeroTipBill.total)
        assertEquals("0.00", zeroTipBill.perPersonTipNum)
        assertEquals("$0.00", zeroTipBill.perPersonTip)
        assertEquals("100.00", zeroTipBill.perPersonTotalNum)
        assertEquals("$100.00", zeroTipBill.perPersonTotal)
    }

    @Test
    fun `test bill with high tip percent`() {
        val bill = Bill(amount = "50.00", percent = 0.25f, split = 2)
        assertEquals("12.50", bill.tipNum)
        assertEquals("$12.50", bill.tip)
        assertEquals("62.50", bill.totalNum)
        assertEquals("$62.50", bill.total)
        assertEquals("6.25", bill.perPersonTipNum)
        assertEquals("$6.25", bill.perPersonTip)
        assertEquals("31.25", bill.perPersonTotalNum)
        assertEquals("$31.25", bill.perPersonTotal)

        val highTipBill = Bill(amount = "100.00", percent = 0.5f, split = 3)
        assertEquals("50.00", highTipBill.tipNum)
        assertEquals("$50.00", highTipBill.tip)
        assertEquals("150.00", highTipBill.totalNum)
        assertEquals("$150.00", highTipBill.total)
        assertEquals("16.67", highTipBill.perPersonTipNum)
        assertEquals("$16.67", highTipBill.perPersonTip)
        assertEquals("50.00", highTipBill.perPersonTotalNum)
        assertEquals("$50.00", highTipBill.perPersonTotal)

        val veryHighTipBill = Bill(amount = "100.00", percent = 1f, split = 4)
        assertEquals("100.00", veryHighTipBill.tipNum)
        assertEquals("$100.00", veryHighTipBill.tip)
        assertEquals("200.00", veryHighTipBill.totalNum)
        assertEquals("$200.00", veryHighTipBill.total)
        assertEquals("25.00", veryHighTipBill.perPersonTipNum)
        assertEquals("$25.00", veryHighTipBill.perPersonTip)
        assertEquals("50.00", veryHighTipBill.perPersonTotalNum)
        assertEquals("$50.00", veryHighTipBill.perPersonTotal)

        val impossibleTipBill = Bill(amount = "500.00", percent = 2f, split = 1)
        assertEquals("1000.00", impossibleTipBill.tipNum)
        assertEquals("$1000.00", impossibleTipBill.tip)
        assertEquals("1500.00", impossibleTipBill.totalNum)
        assertEquals("$1500.00", impossibleTipBill.total)
        assertEquals("1000.00", impossibleTipBill.perPersonTipNum)
        assertEquals("$1000.00", impossibleTipBill.perPersonTip)
        assertEquals("1500.00", impossibleTipBill.perPersonTotalNum)
        assertEquals("$1500.00", impossibleTipBill.perPersonTotal)
    }
}
