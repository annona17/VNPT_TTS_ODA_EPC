package product_group
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
import notification.NotificationKeywords

class AddProductGroup {

	@And("I enter valid name (.*) code (.*) and description (.*)")
	def enter_infor(String name, String code, String description) {
		WebUI.callTestCase(findTestCase('Test Cases/ProductGroup/Add_Product_Group'),
				[('name'): name, ('code'): code, ('description'): description],
				FailureHandling.STOP_ON_FAILURE)
	}

	@And('I click save button')
	def click_save() {
		WebUI.click(findTestObject('Object Repository/AddProductGroup/Page_Root Config/button_Save'))
	}

	@Then("I receive successful notification")
	def receive_success_noti() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject('Object Repository/AddProductGroup/Page_Root Config/div_NotificationCreate new product group successfully'),
				"create new product group successfully"
				)
	}

	@And("I navigate to edit this product group")
	def navigate_edit_product_group_form() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/AddProductGroup/Page_Root Config/div_Edit_ProductGroup_form'), 5)
	}

	@And("I empty name (.*) and enter valid code (.*), enter valid description (.*)")
	def empty_require_name(String name, String code, String description) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Add_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description],
				FailureHandling.STOP_ON_FAILURE)
	}
	@And("I enter valid name (.*), empty code (.*) and enter valid description (.*)")
	def enter_same_code(String name, String code, String description) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Add_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description],
				FailureHandling.STOP_ON_FAILURE)
	}

	@Then("I receive compulsory name notifications")
	def receive_compulsory_name() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject('Object Repository/AddProductGroup/Page_Root Config/div_Name is required'),
				"Name is required"
				)
	}

	@Then("I receive compulsory code notifications")
	def receive_compulsory_code() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject('Object Repository/AddProductGroup/Page_Root Config/div_Code is required'),
				"Code is required"
				)
	}

	@And("I enter the same name (.*) and enter valid code (.*), enter valid description (.*)")
	def enter_same_name(String name, String code, String description) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Add_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description],
				FailureHandling.STOP_ON_FAILURE)
	}
	@And("I enter valid name (.*), enter the same code (.*) and enter valid description (.*)")
	def empty_require_code(String name, String code, String description) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Add_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description],
				FailureHandling.STOP_ON_FAILURE)
	}
	@Then("I receive exist name notification")
	def receive_unique_name() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/AddProductGroup/Page_Root Config/div_ErrorName is unique"),
				"Name is unique"
				)
	}

	@Then("I receive exist code notification")
	def receive_unique_code() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/AddProductGroup/Page_Root Config/div_ErrorCode is unique"),
				"Code is unique"
				)
	}
}