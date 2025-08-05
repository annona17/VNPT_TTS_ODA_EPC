package notification

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

import com.kms.katalon.core.util.KeywordUtil

public class NotificationKeywords {
	@Keyword
	def verifyNotificationText(TestObject notificationObject, String expectedPartialText) {
		WebUI.waitForElementVisible(notificationObject, 10)

		String actualText = WebUI.getText(notificationObject).replaceAll("\\s+", " ").trim().toLowerCase()
		expectedPartialText = expectedPartialText.toLowerCase()

		KeywordUtil.logInfo("Actual notification text: " + actualText)
		KeywordUtil.logInfo("Expected (partial match): " + expectedPartialText)

		if (!actualText.contains(expectedPartialText)) {
			KeywordUtil.markFailed("Expected text not found in actual notification.")
		} else {
			KeywordUtil.markPassed("Notification text contains expected message.")
		}
	}
}
