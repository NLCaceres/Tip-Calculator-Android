package itp341.caceres.nicholas.tipCalculator.helpers

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.isPopup
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import itp341.caceres.nicholas.tipCalculator.ViewModelMain
import itp341.caceres.nicholas.tipCalculator.composables.MainScreen

class MainScreenRobot(private val composeTestRule: ComposeContentTestRule) {
  fun start() {
    composeTestRule.setContent { MainScreen(ViewModelMain()) }
  }
  fun checkLabel(label: String) {
    composeTestRule.onNodeWithText(label).assertExists()
  }
  fun checkBillAmount(amount: String) {
    composeTestRule.onNodeWithText(amount).assertExists()
  }
  fun enterBillAmount(amount: String) {
    composeTestRule.onNode(isEditable()).performTextInput(amount)
  }
  fun replaceBillAmount(amount: String) {
    composeTestRule.onNode(isEditable()).performTextReplacement(amount)
  }
  fun checkZeroTipAndTotal(count: Int) {
    composeTestRule.onAllNodesWithText("$0.00").assertCountEquals(count)
  }
  fun checkTipAndTotalUpdate(tip: String, total: String) {
    composeTestRule.onNodeWithText(tip).assertExists()
    composeTestRule.onNodeWithText(total).assertExists()
  }
  fun hasValidationError(displayed: Boolean) {
    if (displayed) composeTestRule.onNodeWithText("Invalid amount").assertExists()
    else composeTestRule.onNodeWithText("Invalid amount").assertDoesNotExist()
  }
  fun checkSlider(value: Float) {
    composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo(value, 0.0f..0.3f, 31))).assertExists()
  }
  fun moveSlider(value: Float) {
    composeTestRule.onNode(hasKey(SemanticsActions.SetProgress))
      .performSemanticsAction(SemanticsActions.SetProgress) { it(value) }
  }
  fun checkDropdown(open: Boolean) {
    if (open) composeTestRule.onNode(isPopup()).assertExists()
    else composeTestRule.onNode(isPopup()).assertDoesNotExist()
  }
  fun toggleDropdown() {
    composeTestRule.onNode(hasRole(Role.DropdownList)).performClick()
  }
  fun selectDropdownItem(text: String) {
    composeTestRule.onNode(hasTextExactly(text) and hasParent(hasScrollAction())).performClick()
  }
  fun checkPerPersonSection(displayed: Boolean) {
    if (displayed) composeTestRule.onNodeWithText("Per Person").assertExists()
    else composeTestRule.onNodeWithText("Per Person").assertDoesNotExist()
  }
  fun checkAllTipsAndTotal(tip: String, total: String, perPersonTip: String, perPersonTotal: String) {
    checkTipAndTotalUpdate(tip, total)
    checkTipAndTotalUpdate(perPersonTip, perPersonTotal)
  }
}
