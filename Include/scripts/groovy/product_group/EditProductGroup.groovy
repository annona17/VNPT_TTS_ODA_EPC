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

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


class EditProductGroup {
	@When("I edit product group form with oldCode (.*)")
	def click_edit_item (String oldCode) {
		TestObject editIcon = findTestObject("Object Repository/Page_Root Config/icon_edit_item", [('code'): oldCode])
		WebUI.waitForElementClickable(editIcon, 10)
		WebUI.click(editIcon)
	}

	@And("I edit valid name (.*), code (.*), description (.*), startDate (.*), endDate (.*), status (.*), version (.*)")
	def edit_product_group(String name, String code, String description, String startDate, String endDate, String status, String version ) {
		WebUI.callTestCase(findTestCase("Test Cases/ProductGroup/Edit_Product_Group"),
				[('name'): name, ('code'): code, ('description'): description, ('startDate'): startDate, ('endDate'): endDate, ('version'): version] ,
				FailureHandling.STOP_ON_FAILURE)
	}

	@And("I click button Save")
	def click_Save_Edit() {
		WebUI.click(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/svg_Other_toggle"))
		TestObject saveBtn = findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/button_Save")
		WebUI.scrollToElement(saveBtn, 5)
		WebUI.click(saveBtn)
		WebUI.delay(3)
	}

	@Then("I receive edit successfully notification")
	def receive_success_edit_noti() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject('Object Repository/Edit_Product_Group/Page_Root Config/div_Noti_Product Group updated successfully'),
				"Product Group updated successfully"
				)
	}

	@And("This product group with oldCode (.*) is updated with name (.*), code (.*), description (.*), startDate (.*), endDate (.*), status (.*), version (.*) in database")
	def checkDB_Edit_Success(String oldCode, String name, String code, String description, String startDate, String endDate, String status, String version) {
		if (code == oldCode) {
			def record = MongoUtils.findDocument("ProductPropertyManagement", "ProductGroup", new Document("code":oldCode), 10)
			if (record.isEmpty()) {
				KeywordUtil.markFailed("Không tìm thấy product group với code: ${oldCode}")
				return
			}else if (record.size() > 1) {
				KeywordUtil.markFailed("Đã tạo bản ghi mới thay vì cập nhật với code: ${oldCode}")
				return
			}
			else {
				def result = record[0]
				assert result.getString("name") == name : "Tên không khớp. Expected: ${name}, Found: ${result.getString("name")}"
				assert result.getString("code") == code : "Code không khớp. Expected: ${code}, Found: ${result.getString("code")}"
				assert result.getString("description") == description
				assert result.getString("startDate") == startDate
				assert result.getString("endDate") == endDate
				assert result.getString("status") == status
				assert result.getString("version") == version

				KeywordUtil.markPassed("Dữ liệu đã được cập nhật chính xác trong MongoDB.")
			}
		}else {
			def oldRecord = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", new Document("code":oldCode), 1)
			def record = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", new Document("code":code), 1)
			if (!oldRecord.isEmpty() || record.isEmpty()) {
				KeywordUtil.markFailed("Ban ghi chua duoc cap nhat")
			}else {
				def result = record[0]
				assert result.getString("name") == name : "Tên không khớp. Expected: ${name}, Found: ${result.getString("name")}"
				assert result.getString("code") == code : "Code không khớp. Expected: ${code}, Found: ${result.getString("code")}"
				assert result.getString("description") == description
				assert result.getString("startDate") == startDate
				assert result.getString("endDate") == endDate
				assert result.getString("status") == status
				assert result.getString("version") == version
				KeywordUtil.markPassed("Dữ liệu đã được cập nhật chính xác trong MongoDB.")
			}
		}
	}

	@And("I save old record with oldCode (.*) in snapshot")
	def save_old_record_in_snapshot(String oldCode) {
		def records = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", new Document("code": oldCode), 1)
		if (records.isEmpty()) {
			KeywordUtil.markFailed("Không tìm thấy product group với code: ${code} trước khi chỉnh sửa.")
			return
		}
		GlobalVariable.oldDocSnapshot = record[0]
		KeywordUtil.logInfo("Đã lưu snapshot trước khi edit: " + GlobalVariable.oldDocSnapshot.toJson())
	}

	@And("I empty name")
	def empty_name() {
		WebUI.setText(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/input__name"), "")
	}
	@Then("I receive require name notification")
	def require_name_noti(){
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div__Name is required"), "Name is required")
	}
	@And("This product group with oldCode (.*) is not updated in database")
	def checkDB_not_update(String oldCode) {
		def oldDoc = GlobalVariable.oldDocSnapshot
		if (oldDoc== null) {
			KeywordUtil.markFailed("Chưa lưu snapshot trước khi edit. Vui lòng gọi bước lưu trước.")
			return
		}
		def query = new Document("code", oldCode)
		def record = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", query, 1)

		if (record.isEmpty()) {
			KeywordUtil.markFailed("Không tìm thấy product group với code: ${oldCode}")
			return
		}

		def newDoc = record[0]

		// So sánh tất cả các trường cần thiết
		def fieldsToCompare = [
			"name",
			"code",
			"description",
			"startDate",
			"endDate",
			"status",
			"version"
		]
		def differences = []

		fieldsToCompare.each { field ->
			def oldVal = oldDoc.getString(field)?.trim() ?: ""
			def newVal = newDoc.getString(field)?.trim() ?: ""
			if (oldVal != newVal) {
				differences << "❌ Trường '${field}' bị thay đổi: OLD='${oldVal}' | NEW='${newVal}'"
			}
		}

		if (differences.isEmpty()) {
			KeywordUtil.markPassed("✅ Dữ liệu trong DB không thay đổi sau khi nhấn Save.")
		} else {
			KeywordUtil.markFailed("❌ DB đã bị thay đổi:\n" + differences.join("\n"))
		}
	}

	@And("I empty code")
	def empty_code() {
		WebUI.setText(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/input__code"), "")
	}
	@Then("I receive require code notification")
	def require_code_noti(){
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div__Code is required"), "Code is required")
	}

	@And("This product group with oldCode (.*) is not updated in database when empty code")
	def check_DB_empty_code() {
		def record = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", new Document("code":""), 10)
		if (record.isEmpty()) {
			KeywordUtil.markPassed("Khong co ban ghi nao bi bo trong code")
		}else {
			KeywordUtil.markFailed("Co ban ghi nao bi bo trong code")
		}
	}

	@And("I edit startDate (.*) < today")
	def enter_startDate_small(String startDate) {
		TestObject startDateField = findTestObject('Object Repository/Edit_Product_Group/Page_Root Config/input_ValidFor_startDate')

		// Xóa trước rồi gán giá trị ngày mới cho startDate
		WebUI.executeJavaScript(
				"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
				"arguments[0].value='" + startDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
				Arrays.asList(WebUI.findWebElement(startDateField))
				)
	}

	@And("I edit name (.*) same other product group")
	def edit_same_name(String name) {
		WebUI.setText(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/input__name"), name)
	}

	@Then("I receive unique name notification")
	def unique_name_noti(){
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div_Name is unique"), "Name is unique")
	}

	@Then("I receive unique code notification")
	def unique_code_noti(){
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div_Code is unique"), "Code is unique")
	}
	@And("I edit code (.*) same other product group")
	def edit_same_code(String code) {
		WebUI.setText(findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/input__code"), code)
	}

	@And("This product group with oldCode (.*) is not updated code (.*) in database")
	def checkDB_unique_code(String oldCode, String code) {
		def record = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", new Document("code": code), 10)
		if (record.size() >= 2) {
			KeywordUtil.markFailed("Đã cập nhật thành code bị trùng")
		}else {
			KeywordUtil.markPassed("Khong co ban ghi nao bi trung code")
		}
	}

	@Then("I receive invalid start date notification")
	def invalid_start_date_noti() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div_The start date must be greater than today"), "The start date must be greater than today")
	}

	@Then("I receive invalid date notification")
	def invalid_date_noti() {
		new NotificationKeywords().verifyNotificationText(
				findTestObject("Object Repository/Edit_Product_Group/Page_Root Config/div_The start date must be smaller than the end date"), "The start date must be smaller than the end date")
	}
}