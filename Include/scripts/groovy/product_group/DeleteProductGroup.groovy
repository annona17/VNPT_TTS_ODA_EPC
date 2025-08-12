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

class DeleteProductGroup {
	WebDriver driver = DriverFactory.getWebDriver()

	@When("I delete a product group with code (.*)")
	def delete_a_product_group(String code) {
		TestObject trashIcon = findTestObject("Object Repository/EPC/Property_Management/Common/icon_trash_itemObject Repository/EPC/Property_Management/Common/icon_trash_item",
				[('code'): code])
		WebUI.waitForElementClickable(trashIcon, 10)
		WebUI.click(trashIcon)
	}

	@And("I click Yes in confirm delete popup")
	def click_yes_to_confirm_delete() {
		WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Common/button_Yes_confirm_delete"))
	}

	@When ("I tick 2 product groups with code1 (.*) and code2 (.*)")
	def click_2_product_group(String code1, String code2) {
		TestObject checkox1 = findTestObject("Object Repository/Page_Root Config/input_checkbox", [('code'): code1])
		WebUI.waitForElementClickable(checkox1, 10)
		WebUI.click(checkox1)

		TestObject checkbox2 = findTestObject("Object Repository/Page_Root Config/input_checkbox", [('code'): code2])
		WebUI.waitForElementClickable(checkbox2, 10)
		WebUI.click(checkbox2)
	}

	@And("I click icon button delete")
	def click_button_delete() {
		WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Common/icon_trash_many"))
	}

	@When("I tick checkbox all")
	def tick_all() {
		WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Common/input_Checkbox_all"))
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody/tr"))
		ArrayList<String> tmp = new ArrayList<String>()
		for (WebElement row : rows) {
			String code = row.findElement(By.xpath("./td[2]")).getText().trim()
			tmp.add(code)
		}
		GlobalVariable.deleteCode = tmp
		println("Cac product group se bi xoa: " + GlobalVariable.deleteCode)
	}


	@And("I click No in confirm delete popup")
	def click_No_in_popup_confirm() {
		WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Common/button_No_confirm_delete"))
	}

	@Then("Popup Confirm delete disappear")
	def disappear_popup_confirm() {
		WebUI.verifyElementNotVisible(findTestObject('Object Repository/EPC/Property_Management/Common/div_Confirm_delete'))
	}

	@When("I click delete button")
	def click_buttom_delete() {
		GlobalVariable.cntRecord = MongoUtils.countDocuments("ProductPropertyManagement", "ProductGroup")
		WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Common/icon_trash_many"))
	}

}