package common
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


class Common {
	//	Given I login ODA successful
	//	When I navigate to add product group form
	//	And I enter valid name <name>, code <code> and description<description>
	//	And I click save button
	//	Then I receive successful notification
	//	And I navigate to edit this product group
	//	And This product group is in database
	@Given("I login ODA successful")
	def login_ODA_successfull() {
		WebUI.callTestCase(findTestCase('Test Cases/Common/Login_ODA_Success'), [:], FailureHandling.STOP_ON_FAILURE)
	}

	@When("I navigate to add product group form")
	def open_add_product_group_form() {
		WebUI.callTestCase(findTestCase('Test Cases/Common/Navigate_To_Add_Product_Group_Form'), [:], FailureHandling.STOP_ON_FAILURE)
	}
	@And("I navigate to product group")
	def navigate_to_product_group() {
		WebUI.callTestCase(findTestCase("Test Cases/Common/Navigate_To_Product_Group"), [:], FailureHandling.STOP_ON_FAILURE)
	}

//	@When("I navigate to edit product group form with oldCode (.*)")
//	def navigae_to_edit_product_group() {
//		WebUI.callTestCase(findTestCase(""))
//	}
}