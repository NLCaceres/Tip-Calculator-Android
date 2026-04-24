package itp341.caceres.nicholas.tipCalculator

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelMainTest {

    private lateinit var viewModel: ViewModelMain

    @Before
    fun setUp() {
        viewModel = ViewModelMain()
    }

    @Test
    fun testInitialState() {
        val bill = viewModel.bill.value
        assertEquals("", bill.amount)
        assertEquals(0.15f, bill.percent)
        assertEquals(1, bill.split)
    }

    @Test
    fun testUpdateAmount() {
        assertEquals("", viewModel.bill.value.amount)
        viewModel.updateAmount("50.00")
        assertEquals("50.00", viewModel.bill.value.amount)
    }

    @Test
    fun testUpdatePercent() {
        assertEquals(0.15f, viewModel.bill.value.percent)
        viewModel.updatePercent(0.20f)
        assertEquals(0.20f, viewModel.bill.value.percent)
    }

    @Test
    fun testUpdateSplit() {
        assertEquals(1, viewModel.bill.value.split)
        viewModel.updateSplit(4)
        assertEquals(4, viewModel.bill.value.split)
    }
}
