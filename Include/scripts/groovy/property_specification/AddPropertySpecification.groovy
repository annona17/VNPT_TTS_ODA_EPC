package property_specification
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When

import mongodb.MongoUtils
import mongodb.MongoConnection
import org.bson.Document

class AddPropertySpecification {
	@When("I click button Add to add new Property Specification")
	def click_add_new_ps() {
		WebUI.click(findTestObject("Object Repository/Page_Root Config/icon_add"))
	}
	@And("I enter name (.*) code (.*) and description (.*)")
	def enter_infor(String name, String code, String description) {
		WebUI.setText(findTestObject("Object Repository/PropertySpecification/Page_Root Config/input__Name_add"), name.toString())
		WebUI.setText(findTestObject("Object Repository/PropertySpecification/Page_Root Config/input_Code_add"), code.toString())
		WebUI.setText(findTestObject("Object Repository/PropertySpecification/Page_Root Config/textarea_Description_add"), description.toString())
	}

	@Then("I receive required notifications with text (.*)")
	def require_noti(String text) {
		TestObject message = findTestObject("Object Repository/PropertySpecification/Page_Root Config/small__required_noti", [('text'): text])
		WebUI.waitForElementPresent(message, 10)
		WebUI.waitForElementVisible(message, 5)
		String actualText = WebUI.getText(message)
		println(">>> Actual Notification: " + actualText)
		assert actualText.contains(text)
	}
	@And("I navigate to edit this Property Specification")
	def navigate_to_edit_form() {
		WebUI.verifyElementNotPresent(findTestObject("Object Repository/PropertySpecification/Page_Root Config/div_Property Specification_Edit_form"), 10, FailureHandling.STOP_ON_FAILURE)
	}
}