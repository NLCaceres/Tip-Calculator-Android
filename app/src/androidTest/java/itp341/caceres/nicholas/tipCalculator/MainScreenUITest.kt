package itp341.caceres.nicholas.tipCalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import itp341.caceres.nicholas.tipCalculator.helpers.MainScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenUITest {
  @get:Rule
  val composeTestRule = createComposeRule()
  private lateinit var mainScreenBot: MainScreenRobot

  @Before
  fun setupContent() {
    mainScreenBot = MainScreenRobot(composeTestRule).apply {
      start() // WHEN the app launches, THEN the following label composables should render
      checkLabel("Bill Amount")
      checkLabel("Percent")
      checkLabel("Tip") // Only 1 Tip renders at first by default
      checkLabel("Total") // Only 1 by default
      checkLabel("Split Bill?")
      checkPerPersonSection(false) // EXCEPT this section
    }
  }

  @Test
  fun testTextField() {
    mainScreenBot.run {
      checkZeroTipAndTotal(2)
      // WHEN the user inputs a number value
      enterBillAmount("100")
      // THEN the tip and total values are calculated to a default 15% (no per-person)
      checkTipAndTotalUpdate("$15.00", "$115.00")

      hasValidationError(false)
      // WHEN the user inputs a non-number value ("100" is now "100a")
      enterBillAmount("a")
      checkBillAmount("100a")
      // THEN all tip and total values are set to "$0.00" as error occurs and validation error text appears
      checkZeroTipAndTotal(2)
      hasValidationError(true)

      // WHEN the user inputs a dollar value (including the "$")
      replaceBillAmount("$50")
      checkBillAmount("$50")
      // THEN the tip and total values are still set to "$0.00" with the error message remaining
      checkZeroTipAndTotal(2)
      hasValidationError(true)

      // WHEN the user inputs a decimal number value
      replaceBillAmount("50.00")
      // THEN the tip and total are correctly calculated (no validation error text)
      checkTipAndTotalUpdate("$7.50", "$57.50")
      hasValidationError(false)

      // WHEN the user clears the text field
      replaceBillAmount("")
      // THEN the textField "0.00" placeholder reappears AND tip/total values reset to "$0.00
      checkBillAmount("0.00")
      checkZeroTipAndTotal(2)
    }
  }

  @Test
  fun testSlider() {
    mainScreenBot.run {
      // WHEN the user moves the Slider thumb all the way to its right BUT the textField is empty
      checkSlider(0.15f) // Defaults to 15%
      moveSlider(0.3f)
      // THEN the tip and total is still "$0.00"
      checkZeroTipAndTotal(2)

      // WHEN the textField is now "100.00"
      enterBillAmount("100.00")
      // THEN the Slider, having been set to 30%, sets the tip to "$30.00" and the total to "$130.00"
      checkSlider(0.3f)
      checkTipAndTotalUpdate("$30.00", "$130.00")

      // WHEN the Slider is set to 0% (all the way left) -- Alt easier method to do so (swipe is finicky)
      moveSlider(0.0f)
      checkSlider(0.0f)
      // THEN the tip is $0.00 and the total only $100.00
      checkTipAndTotalUpdate("$0.00", "$100.00")
    }
  }

  @Test
  fun testDropdown() {
    mainScreenBot.run {
      // WHEN the user clicks on the dropdown
      checkDropdown(false)
      toggleDropdown()
      // THEN the dropdown is displayed
      checkDropdown(true)
      // UNTIL a dropdown item is clicked
      selectDropdownItem("2 ways")
      checkDropdown(false)
      // AND since split > 1, THEN the per-person tip and total are displayed
      // AND since the textField is still empty, THEN ALL tips and totals remains "$0.00"
      checkZeroTipAndTotal(4)

      // WHEN the textField is set
      enterBillAmount("100.00")
      // THEN the main tip and total is a different pair of values compared to the per-person version
      checkAllTipsAndTotal("$15.00", "$115.00", "$7.50", "$57.50")

      // WHEN the same dropdown item value is selected
      toggleDropdown()
      checkDropdown(true)
      selectDropdownItem("2 ways")
      // THEN the dropdown closes
      checkDropdown(false)
      // AND the tip and total as well as per-person tip and total remain exactly the same
      checkAllTipsAndTotal("$15.00", "$115.00", "$7.50", "$57.50")

      // WHEN the dropdown item value is changed
      toggleDropdown()
      selectDropdownItem("10 ways")
      // THEN the per-person tip and total values change, NOT the actual tip and total
      checkAllTipsAndTotal("$15.00", "$115.00", "$1.50", "$11.50")

      // WHEN the dropdown is opened
      toggleDropdown()
      checkDropdown(true)
      // AND the dropdown menu box is clicked
      toggleDropdown()
      // THEN the dropdown is closed without changing the value
      checkDropdown(false)
      // AND the tip and total as well as per-person tip and total remain exactly the same
      checkAllTipsAndTotal("$15.00", "$115.00", "$1.50", "$11.50")
    }
  }

  @Test
  fun testPerPersonSection() {
    mainScreenBot.run {
      // WHEN the app launches, No Per-Person section displayed/rendered
      checkPerPersonSection(false)
      // UNTIL the dropdown is opened, and split selected > 1
      toggleDropdown()
      selectDropdownItem("2 ways")
      // THEN the Per-Person composable renders
      checkPerPersonSection(true)
      // AND another tip/total section appears (2 tip + grand total and 2 per-person tip + total)
      checkZeroTipAndTotal(4)

      // WHEN the textField sets a bill amount and the split > 1
      enterBillAmount("100")
      // THEN the per-person section tip and total is calculated
      checkAllTipsAndTotal("$15.00", "$115.00", "$7.50", "$57.50")

      // WHEN the textField has an amount error
      enterBillAmount("a")
      // THEN the per-person section tip and total is ALSO set to "$0.00"
      checkZeroTipAndTotal(4)

      moveSlider(0.3f)
      checkZeroTipAndTotal(4)
      replaceBillAmount("30")
      checkAllTipsAndTotal("$9.00", "$39.00", "$4.50", "$19.50")
    }
  }
}