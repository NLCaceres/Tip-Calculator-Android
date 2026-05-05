package itp341.caceres.nicholas.tipCalculator

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.test.ext.junit.runners.AndroidJUnit4
import itp341.caceres.nicholas.tipCalculator.composables.MainScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenUITest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testAppLaunches() {
    composeTestRule.setContent {
      MainScreen(ViewModelMain())
    }
    // WHEN the app launches, THEN the following are the default displayed
    composeTestRule.onNodeWithText("Bill Amount").assertExists()
    composeTestRule.onNodeWithText("Percent").assertExists()
    composeTestRule.onNodeWithText("Percent").assert(hasAnySibling(hasText("15%")))
    composeTestRule.onNodeWithText("Tip").assertExists() // Only 1 by default
    composeTestRule.onNodeWithText("Total").assertExists() // Only 1 by default UNTIL split
    composeTestRule.onNodeWithText("Split Bill?").assertExists()
    composeTestRule.onNodeWithText("Split Bill?").assert(hasAnySibling(hasText("No")))
  }
  @Test
  fun testTextField() {
    composeTestRule.setContent {
      MainScreen(ViewModelMain())
    }
    composeTestRule.onAllNodes(hasText("Tip")).assertAll(hasAnySibling(hasTextExactly("$0.00")))
    composeTestRule.onAllNodes(hasText("Total")).assertAll(hasAnySibling(hasTextExactly("$0.00")))
    // WHEN the user inputs a number value
    composeTestRule.onNodeWithText("0.00").performTextInput("100")
    // THEN the tip and total values are calculated to a default 15% (no per-person)
    composeTestRule.onNodeWithText("$15.00").assertExists()
    composeTestRule.onNodeWithText("$115.00").assertExists()

    composeTestRule.onNodeWithText("Invalid amount").assertDoesNotExist()
    // WHEN the user inputs a non-number value ("100" is now "100a")
    composeTestRule.onNodeWithText("100").performTextInput("a")
    composeTestRule.onNodeWithText("100a").assertExists()
    // THEN all tip and total values are set to "$0.00" as error occurs and validation error text appears
    composeTestRule.onAllNodes(hasText("$0.00")).assertCountEquals(2) // Only main tip + total
    composeTestRule.onNodeWithText("Invalid amount").assertExists()

    // WHEN the user inputs a dollar value (including the "$")
    composeTestRule.onNodeWithText("100a").performTextReplacement("$50")
    composeTestRule.onNodeWithText("$50").assertExists()
    // THEN the tip and total values are still set to "$0.00" with the error message remaining
    composeTestRule.onAllNodes(hasText("$0.00")).assertCountEquals(2) // Only main tip + total
    composeTestRule.onNodeWithText("Invalid amount").assertExists()

    // WHEN the user inputs a decimal number value
    composeTestRule.onNodeWithText("$50").performTextReplacement("50.00")
    // THEN the tip and total are correctly calculated (no validation error text)
    composeTestRule.onNodeWithText("$7.50").assertExists()
    composeTestRule.onNodeWithText("$57.50").assertExists()
    composeTestRule.onNodeWithText("Invalid amount").assertDoesNotExist()

    // WHEN the user clears the text field
    composeTestRule.onNodeWithText("50.00").performTextReplacement("")
    // THEN the textField "0.00" placeholder reappears AND tip/total values reset to "$0.00
    composeTestRule.onNodeWithText("0.00").assertExists()
    composeTestRule.onAllNodes(hasText("$0.00")).assertCountEquals(2) // Only main tip + total
  }

  @Test
  fun testSlider() {
    composeTestRule.setContent {
      MainScreen(ViewModelMain())
    }
    // WHEN the user moves the Slider thumb all the way to its right BUT the textField is empty
    composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo(0.15f, 0.0f..0.3f, 31))).performTouchInput {
      swipe(percentOffset(0.5f, 0.5f), percentOffset(1.0f, 0.5f))
    }
    // THEN the tip and total is still "$0.00"
    composeTestRule.onAllNodes(hasText("Tip")).assertAll(hasAnySibling(hasTextExactly("$0.00")))
    composeTestRule.onAllNodes(hasText("Total")).assertAll(hasAnySibling(hasTextExactly("$0.00")))

    // WHEN the textField is now "100.00"
    composeTestRule.onNodeWithText("0.00").performTextInput("100.00")
    // THEN the Slider, having been set to 30%, sets the tip to "$30.00" and the total to "$130.00"
    composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo(0.3f, 0.0f..0.3f, 31))).assertExists()
    composeTestRule.onAllNodes(hasText("Tip")).assertAll(hasAnySibling(hasTextExactly("$30.00")))
    composeTestRule.onAllNodes(hasText("Total")).assertAll(hasAnySibling(hasTextExactly("$130.00")))

    // WHEN the Slider is set to 0% (all the way left) -- Alt easier method to do so (swipe is finicky)
    composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo(0.3f, 0.0f..0.3f, 31))).
      performSemanticsAction(SemanticsActions.SetProgress) { it(0.0f) }
    composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo(0.0f, 0.0f..0.3f, 31))).assertExists()
    // THEN the tip and total is set to "$0.00"
    composeTestRule.onAllNodes(hasText("Tip")).assertAll(hasAnySibling(hasTextExactly("$0.00")))
    composeTestRule.onAllNodes(hasText("Total")).assertAll(hasAnySibling(hasTextExactly("$0.00")))
  }

  @Test
  fun testDropdown() {
    composeTestRule.setContent {
      MainScreen(ViewModelMain())
    }
    // WHEN the user clicks on the dropdown
    composeTestRule.onNode(isPopup()).assertDoesNotExist()
    composeTestRule.onNodeWithText("No").performClick()
    // THEN the dropdown is displayed
    composeTestRule.onNode(isPopup()).assertIsDisplayed()
    // UNTIL a dropdown item is clicked
    composeTestRule.onNodeWithText("2 ways").performClick()
    composeTestRule.onNode(isPopup()).assertDoesNotExist()
    // AND since split > 1, THEN the per-person tip and total are displayed
    // AND since the textField is still empty, THEN ALL tips and totals remains "$0.00"
    composeTestRule.onAllNodesWithText("$0.00").assertCountEquals(4)

    // WHEN the textField is set
    composeTestRule.onNodeWithText("0.00").performTextInput("100.00")
    // THEN the tip and total is a different set of values compared to the per-person version
    composeTestRule.onNodeWithText("$15.00").assertExists() // Actual full tip
    composeTestRule.onNodeWithText("$115.00").assertExists() // Actual full total
    composeTestRule.onNodeWithText("$7.50").assertExists() // Tip split in 2
    composeTestRule.onNodeWithText("$57.50").assertExists() // Total split in 2

    // WHEN the same dropdown item value is selected
    composeTestRule.onNodeWithText("2 ways").performClick()
    composeTestRule.onNode(isPopup()).assertIsDisplayed()
    composeTestRule.onNode(hasTextExactly("2 ways") and hasParent(hasScrollAction())).performClick()
    // THEN the dropdown closes
    composeTestRule.onNode(isPopup()).assertIsNotDisplayed()
    // AND the tip and total as well as per-person tip and total remain exactly the same
    composeTestRule.onNodeWithText("$15.00").assertExists() // Actual full tip
    composeTestRule.onNodeWithText("$115.00").assertExists() // Actual full total
    composeTestRule.onNodeWithText("$7.50").assertExists() // Tip split in 2
    composeTestRule.onNodeWithText("$57.50").assertExists() // Total split in 2

    // WHEN the dropdown item value is changed
    composeTestRule.onNodeWithText("2 ways").performClick() // Open dropdown
    composeTestRule.onNodeWithText("10 ways").performClick() // Select new value
    // THEN the per-person tip and total values change, NOT the actual tip and total
    composeTestRule.onNodeWithText("$15.00").assertExists() // Actual full tip still
    composeTestRule.onNodeWithText("$115.00").assertExists() // Actual full total still
    composeTestRule.onNodeWithText("$1.50").assertExists() // Tip split by 10 people
    composeTestRule.onNodeWithText("$11.50").assertExists() // Total split by 10 people

    // WHEN the dropdown is opened
    composeTestRule.onNodeWithText("10 ways").performClick()
    composeTestRule.onNode(isPopup()).assertIsDisplayed()
    // AND the dropdown menu box is clicked
    composeTestRule.onNode(hasRole(Role.DropdownList) and hasTextExactly("10 ways")).performClick()
    // THEN the dropdown is closed without changing the value
    composeTestRule.onNode(isPopup()).assertIsNotDisplayed()
    // AND the tip and total as well as per-person tip and total remain exactly the same
    composeTestRule.onNodeWithText("$15.00").assertExists() // Actual full tip
    composeTestRule.onNodeWithText("$115.00").assertExists() // Actual full total
    composeTestRule.onNodeWithText("$1.50").assertExists() // Tip split in 10
    composeTestRule.onNodeWithText("$11.50").assertExists() // Total split in 10
  }
  private fun hasRole(role: Role) = SemanticsMatcher("${SemanticsProperties.Role.name} contains '$role'") {
    it.config.getOrNull(SemanticsProperties.Role) == role
  }
}