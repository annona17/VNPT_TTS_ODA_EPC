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


class EditPropertySpecification {
	@When("I edit Property specification form with oldCode (.*)")
	def click_edit_item (String oldCode) {
		TestObject editIcon = findTestObject("Object Repository/PropertySpecification/Page_Root Config/span_edit_item", [('code'): oldCode])
		WebUI.waitForElementClickable(editIcon, 10)
		WebUI.click(editIcon)
	}

	@And("I edit name (.*), code (.*), description (.*), startDate (.*), endDate (.*), status (.*), version (.*)")
	def enter_edit_infor(String name, String code, String description, String startDate, String endDate, String status, String version) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Edit_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description, ('startDate'): startDate, ('endDate'): endDate, ('status'): status, ('version'): version] ,
				FailureHandling.STOP_ON_FAILURE)
	}

	@And("I edit name (.*) same other Property specification")
	def enter_same_name(String name) {
		WebUI.setText(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/input__name"), name)
	}

	@And("I edit code (.*) same other Property specification")
	def enter_same_code(String code) {
		WebUI.setText(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/input__code"), code)
	}
}