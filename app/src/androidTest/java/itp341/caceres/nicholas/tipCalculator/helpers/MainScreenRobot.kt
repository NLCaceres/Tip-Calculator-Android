package itp341.caceres.nicholas.tipCalculator.helpers

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement

class MainScreenRobot(private val composeTestRule: ComposeContentTestRule) {
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
}
