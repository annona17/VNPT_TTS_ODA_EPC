package product_group
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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
import notification.NotificationKeywords

import mongodb.MongoUtils
import mongodb.MongoConnection
import org.bson.Document


class EditProductGroup {
	@When("I edit product group form with oldCode (.*)")
	def click_edit_item (String oldCode) {
		TestObject editIcon = findTestObject("Object Repository/EPC/Property_Management/Common/icon_trash_item", [('code'): oldCode])
		WebUI.waitForElementClickable(editIcon, 10)
		WebUI.click(editIcon)
	}

	@And("I edit valid name (.*), code (.*), description (.*), startDate (.*), endDate (.*), status (.*), version (.*)")
	def edit_product_group(String name, String code, String description, String startDate, String endDate, String status, String version ) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Edit_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description, ('startDate'): startDate, ('endDate'): endDate, ('status'): status, ('version'): version] ,
				FailureHandling.STOP_ON_FAILURE)
	}

	@And("I click save edit button")
	def click_Save_Edit() {
		WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Product_Group/svg_Other_toggle"))
		TestObject saveBtn = findTestObject("Object Repository/EPC/Property_Management/Common/button_Save_edit")
		WebUI.scrollToElement(saveBtn, 5)
		WebUI.click(saveBtn)
	}

	@And("I empty name")
	def empty_name() {
		WebUI.setText(findTestObject("Object Repository/EPC/Property_Management/Product_Group/input__name_edit"), "")
	}

	@And("I empty code")
	def empty_code() {
		WebUI.setText(findTestObject("Object Repository/EPC/Property_Management/Product_Group/input__code_edit"), "")
	}

	@And("I edit startDate (.*) < today")
	def enter_startDate_small(String startDate) {
		TestObject startDateField = findTestObject('Object Repository/EPC/Property_Management/Product_Group/input_ValidFor_startDate')

		// Xóa trước rồi gán giá trị ngày mới cho startDate
		WebUI.executeJavaScript(
				"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
				"arguments[0].value='" + startDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
				Arrays.asList(WebUI.findWebElement(startDateField))
				)
	}

	@And("I edit name (.*) same other product group")
	def edit_same_name(String name) {
		WebUI.setText(findTestObject("Object Repository/EPC/Property_Management/Product_Group/input__name_edit"), name)
	}

	@And("I edit code (.*) same other product group")
	def edit_same_code(String code) {
		WebUI.setText(findTestObject("Object Repository/EPC/Property_Management/Product_Group/input__code_edit"), code)
	}

	@And("I edit startDate (.*) < endDate (.*)")
	def edit_date(String startDate, String endDate) {
		TestObject startDateField = findTestObject('Object Repository/EPC/Property_Management/Product_Group/input_ValidFor_startDate')
		TestObject endDateField = findTestObject('Object Repository/EPC/Property_Management/Product_Group/input_ValidFor_endDate')

		// Xóa trước rồi gán giá trị ngày mới cho startDate
		WebUI.executeJavaScript(
				"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
				"arguments[0].value='" + startDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
				Arrays.asList(WebUI.findWebElement(startDateField))
				)

		// Tương tự cho endDate
		WebUI.executeJavaScript(
				"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
				"arguments[0].value='" + endDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
				Arrays.asList(WebUI.findWebElement(endDateField))
				)
	}
}