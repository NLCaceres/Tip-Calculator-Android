package itp341.caceres.nicholas.tipCalculator.helpers

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
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
  fun checkPerPersonSection(displayed: Boolean) {
    if (displayed) composeTestRule.onNodeWithText("Per Person").assertExists()
    else composeTestRule.onNodeWithText("Per Person").assertDoesNotExist()
  }
}
