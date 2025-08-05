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
	List<String> deleteCode = [];
	WebDriver driver = DriverFactory.getWebDriver()

	@When("I delete a product group not active with code (.*)")
	def delete_item_not_active(String code) {
		TestObject trashIcon = findTestObject("Object Repository/Page_Root Config/icon_trash_item", [('code'): code])
		WebUI.waitForElementClickable(trashIcon, 10)
		WebUI.click(trashIcon)
	}

	@And("I click Yes in confirm delete popup")
	def click_yes_to_confirm_delete() {
		WebUI.click(findTestObject("Object Repository/Page_Root Config/button_Yes"))
	}

	@Then("I receive a delete successfully notification")
	def receive_delete_success_noti() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject('Object Repository/Page_Root Config/div_NotificationDelete selected product group successfully'),
				"Delete selected product group successfully"
				)
	}


	@And("This product with code (.*) is not in database")
	def checkDB_Success_Item(String code) {
		def query = new Document("code", code)
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", query, 1)
		if (docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code + " đã được xóa trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi " + code + " vẫn còn trong DB" + docs[0].toJson())
		}
	}

	@When("I tick 2 product groups not active with code1 (.*) and code2 (.*)")
	def tick_2_product_group(String code1, String code2) {
		TestObject checkox1 = findTestObject("Object Repository/Page_Root Config/input_checkbox", [('code'): code1])
		WebUI.waitForElementClickable(checkox1, 10)
		WebUI.click(checkox1)

		TestObject checkbox2 = findTestObject("Object Repository/Page_Root Config/input_checkbox", [('code'): code2])
		WebUI.waitForElementClickable(checkbox2, 10)
		WebUI.click(checkbox2)
	}

	@And("I click icon button delete")
	def click_button_delete() {
		WebUI.click(findTestObject("Object Repository/Page_Root Config/icon_trash_many"))
	}

	@And("These product code1 (.*) and code2 (.*) are not in database")
	def checkDB_Sucess_Many(String code1, String code2) {
		def query = new Document("\$or", Arrays.asList(
				new Document("code", code1),
				new Document("code", code2)
				))
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", query, 2)

		if (docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code1 + " và " + code2 + " đã được xóa trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi vẫn còn trong DB")
		}
	}

	@When("I tick checkbox all")
	def tick_all() {
		WebUI.click(findTestObject("Object Repository/Page_Root Config/input_checkbox_all"))
		List<WebElement> rows = driver.findElements(By.xpath("//table//tbody/tr"))
		deleteCode.clear()
		for (WebElement row : rows) {
			String code = row.findElement(By.xpath("./td[2]")).getText().trim()
			deleteCode.add(code)
		}
		println("Cac product group se bi xoa: " + deleteCode)
	}

	@And("Selected product groups are not in database")
	def checkDB_DeleteAll() {
		def query = ['code': [ '$in' : deleteCode]]
		def remain = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", query, deleteCode.size())
		if (remain.isEmpty()) {
			KeywordUtil.markPassed(deleteCode.size() + " bản ghi đã được xóa trong DB")
		}else {
			KeywordUtil.markFailed("Cac bản ghi vẫn còn trong DB: \n")
			for (doc in remain) {
				println(doc.toJson())
				println()
			}
		}
	}

	@When("I delete a product group with code (.*)")
	def delete_a_product_group(String code) {
		TestObject trashIcon = findTestObject("Object Repository/Page_Root Config/icon_trash_item", [('code'): code])
		WebUI.waitForElementClickable(trashIcon, 10)
		WebUI.click(trashIcon)
	}

	@And("I click No in confirm delete popup")
	def click_No_in_popup_confirm() {
		WebUI.click(findTestObject("Object Repository/Page_Root Config/button_No"))
	}

	@Then("Popup Confirm delete disappear")
	def disappear_popup_confirm() {
		WebUI.verifyElementNotVisible(findTestObject('Object Repository/Page_Root Config/div_ConfirmationAre you sure you want to delete this recordYesNo'))
	}

	@And("This product with code (.*) is still in database")
	def checkDB_No_1(String code) {
		def query = new Document("code", code)
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", query, 1)
		if (!docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code + " vẫn còn trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi " + code + " đã được xóa trong DB" )
		}
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

	@And("These product with code1 (.*) and code2 (.*) is still in database")
	def checkDB_No_2(String code1, String code2) {
		def query = new Document("\$or", Arrays.asList(
				new Document("code", code1),
				new Document("code", code2)
				))
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", query, 2)

		if (!docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code1 + " và " + code2 + " vẫn còn trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi đã được xóa trong DB")
		}
	}
	int cntRecord = 0
	@When("I click delete button")
	def click_buttom_delete() {
		cntRecord = MongoUtils.countDocuments("ProductPropertyManagement", "ProductGroup")
		WebUI.click(findTestObject("Object Repository/Page_Root Config/icon_trash_many"))
	}

	@Then("I receive a no product group selected notification")
	def receive_no_pg_selected_noti() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject('Object Repository/Page_Root Config/div_No records selected yet'),
				"No records selected yet"
				)
	}


	@And("No product group is deleted")
	def checkDB_not_selected() {
		int cntRecordAfter = MongoUtils.countDocuments("ProductPropertyManagement", "ProductGroup")
		if (cntRecordAfter == cntRecord) {
			KeywordUtil.markPassed("Khong co ban ghi nao bi xoa")
		}else {
			KeywordUtil.markFailed("Da co ban ghi bi xoa")
		}
	}
}