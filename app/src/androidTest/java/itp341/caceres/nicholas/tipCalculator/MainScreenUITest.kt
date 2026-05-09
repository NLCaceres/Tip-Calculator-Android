package itp341.caceres.nicholas.tipCalculator

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
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
    mainScreenBot = MainScreenRobot(composeTestRule)
    // WHEN app launches
    mainScreenBot.start()
    // THEN following label composables should  render
    mainScreenBot.checkLabel("Bill Amount")
    mainScreenBot.checkLabel("Percent")
    mainScreenBot.checkLabel("Tip") // Only 1 Tip renders at first by default
    mainScreenBot.checkLabel("Total") // Only 1 by default
    mainScreenBot.checkLabel("Split Bill?")
    mainScreenBot.checkPerPersonSection(false) // EXCEPT this section
  }

  @Test
  fun testTextField() {
    val mainScreenBot = MainScreenRobot(composeTestRule)
    mainScreenBot.checkZeroTipAndTotal(2)
    // WHEN the user inputs a number value
    mainScreenBot.enterBillAmount("100")
    // THEN the tip and total values are calculated to a default 15% (no per-person)
    mainScreenBot.checkTipAndTotalUpdate("$15.00", "$115.00")

    mainScreenBot.hasValidationError(false)
    // WHEN the user inputs a non-number value ("100" is now "100a")
    mainScreenBot.enterBillAmount("a")
    mainScreenBot.checkBillAmount("100a")
    // THEN all tip and total values are set to "$0.00" as error occurs and validation error text appears
    mainScreenBot.checkZeroTipAndTotal(2)
    mainScreenBot.hasValidationError(true)

    // WHEN the user inputs a dollar value (including the "$")
    mainScreenBot.replaceBillAmount("$50")
    mainScreenBot.checkBillAmount("$50")
    // THEN the tip and total values are still set to "$0.00" with the error message remaining
    mainScreenBot.checkZeroTipAndTotal(2)
    mainScreenBot.hasValidationError(true)

    // WHEN the user inputs a decimal number value
    mainScreenBot.replaceBillAmount("50.00")
    // THEN the tip and total are correctly calculated (no validation error text)
    mainScreenBot.checkTipAndTotalUpdate("$7.50", "$57.50")
    mainScreenBot.hasValidationError(false)

    // WHEN the user clears the text field
    mainScreenBot.replaceBillAmount("")
    // THEN the textField "0.00" placeholder reappears AND tip/total values reset to "$0.00
    mainScreenBot.checkBillAmount("0.00")
    mainScreenBot.checkZeroTipAndTotal(2)
  }

  @Test
  fun testSlider() {
    // WHEN the user moves the Slider thumb all the way to its right BUT the textField is empty
    mainScreenBot.checkSlider(0.15f) // Defaults to 15%
    mainScreenBot.moveSlider(0.3f)
    // THEN the tip and total is still "$0.00"
    mainScreenBot.checkZeroTipAndTotal(2)

    // WHEN the textField is now "100.00"
    mainScreenBot.enterBillAmount("100.00")
    // THEN the Slider, having been set to 30%, sets the tip to "$30.00" and the total to "$130.00"
    mainScreenBot.checkSlider(0.3f)
    mainScreenBot.checkTipAndTotalUpdate("$30.00", "$130.00")

    // WHEN the Slider is set to 0% (all the way left) -- Alt easier method to do so (swipe is finicky)
    mainScreenBot.moveSlider(0.0f)
    mainScreenBot.checkSlider(0.0f)
    // THEN the tip is $0.00 and the total only $100.00
    mainScreenBot.checkTipAndTotalUpdate("$0.00", "$100.00")
  }

  @Test
  fun testDropdown() {
    // WHEN the user clicks on the dropdown
    mainScreenBot.checkDropdown(false)
    mainScreenBot.toggleDropdown()
    // THEN the dropdown is displayed
    mainScreenBot.checkDropdown(true)
    // UNTIL a dropdown item is clicked
    mainScreenBot.selectDropdownItem("2 ways")
    mainScreenBot.checkDropdown(false)
    // AND since split > 1, THEN the per-person tip and total are displayed
    // AND since the textField is still empty, THEN ALL tips and totals remains "$0.00"
    mainScreenBot.checkZeroTipAndTotal(4)

    // WHEN the textField is set
    mainScreenBot.enterBillAmount("100.00")
    // THEN the main tip and total is a different pair of values compared to the per-person version
    mainScreenBot.checkAllTipsAndTotal("$15.00", "$115.00", "$7.50", "$57.50")

    // WHEN the same dropdown item value is selected
    mainScreenBot.toggleDropdown()
    mainScreenBot.checkDropdown(true)
    mainScreenBot.selectDropdownItem("2 ways")
    // THEN the dropdown closes
    mainScreenBot.checkDropdown(false)
    // AND the tip and total as well as per-person tip and total remain exactly the same
    mainScreenBot.checkAllTipsAndTotal("$15.00", "$115.00", "$7.50", "$57.50")

    // WHEN the dropdown item value is changed
    mainScreenBot.toggleDropdown()
    mainScreenBot.selectDropdownItem("10 ways")
    // THEN the per-person tip and total values change, NOT the actual tip and total
    mainScreenBot.checkAllTipsAndTotal("$15.00", "$115.00", "$1.50", "$11.50")

    // WHEN the dropdown is opened
    mainScreenBot.toggleDropdown()
    mainScreenBot.checkDropdown(true)
    // AND the dropdown menu box is clicked
    mainScreenBot.toggleDropdown()
    // THEN the dropdown is closed without changing the value
    mainScreenBot.checkDropdown(false)
    // AND the tip and total as well as per-person tip and total remain exactly the same
    mainScreenBot.checkAllTipsAndTotal("$15.00", "$115.00", "$1.50", "$11.50")
  }

  @Test
  fun testPerPersonSection() {
    // WHEN the app launches, No Per-Person section displayed/rendered
    composeTestRule.onNodeWithText("Per Person").assertDoesNotExist()
    // UNTIL the dropdown is opened, and split selected > 1
    composeTestRule.onNodeWithText("No").performClick()
    composeTestRule.onNodeWithText("2 ways").performClick()
    // THEN the Per-Person composable renders
    composeTestRule.onNodeWithText("Per Person").assertExists()
    // AND another tip/total section appears (2 tip + grand total and 2 per-person tip + total)
    composeTestRule.onAllNodesWithText("$0.00").assertCountEquals(4)

    // WHEN the textField sets a bill amount and the split > 1
    composeTestRule.onNodeWithText("0.00").performTextInput("100")
    // THEN the per-person section tip and total is calculated
    composeTestRule.onNodeWithText("$7.50").assertExists() // Per-person tip
    composeTestRule.onNodeWithText("$57.50").assertExists() // Per-person total

    // WHEN the textField has an amount error
    composeTestRule.onNodeWithText("100").performTextInput("a")
    // THEN the per-person section tip and total is ALSO set to "$0.00"
    composeTestRule.onAllNodesWithText("$0.00").assertCountEquals(4)

    composeTestRule.onNode(SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress))
      .performSemanticsAction(SemanticsActions.SetProgress) { it(0.3f) }
    composeTestRule.onAllNodesWithText("$0.00").assertCountEquals(4)
    composeTestRule.onNodeWithText("100a").performTextReplacement("30")
    composeTestRule.onNodeWithText("$9.00").assertExists()
    composeTestRule.onNodeWithText("$39.00").assertExists()
    composeTestRule.onNodeWithText("$4.50").assertExists()
    composeTestRule.onNodeWithText("$19.50").assertExists()
  }
}