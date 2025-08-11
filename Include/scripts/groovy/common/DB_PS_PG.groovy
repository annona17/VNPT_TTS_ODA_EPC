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

import mongodb.MongoUtils
import mongodb.MongoConnection
import org.bson.Document


class DB_PS_PG {

	//1. Add new
	// Them moi thanh cong
	@And("Record in collection (.*) with name (.*) and code (.*) is in database")
	def checkDB(String collectionName, String name, String code ) {
		def query = new Document("name", name).append("code", code)
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", collectionName, query, 1)
		if (!docs.isEmpty()) {
			KeywordUtil.markPassed("Record in collection $collectionName with name $name and code $code is saved in database")
			println(docs[0].toJson())
		}
	}
	// Them moi khong thanh cong
	@And("Record in collection (.*) with name (.*) and code (.*) is NOT in database")
	def checkDB_invalid(String collectionName, String name, String code) {
		def query = new Document("name", name).append("code", code)
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", collectionName, query, 1)
		if (docs.isEmpty()) {
			KeywordUtil.markPassed("Record in collection $collectionName with name $name and code $code is not saved in database")
		}else {
			KeywordUtil.markFailedAndStop("Record in collection $collectionName with $name and code $code is saved in database")
		}
	}

	//2. Edit
	//Lưu snapshot trước khi edit
	@And("I save old record in collection (.*) with oldCode (.*) in snapshot")
	def save_old_record_in_snapshot(String collectionName, String oldCode) {
		def records = MongoUtils.findDocuments("ProductPropertyManagement", collectionName, new Document("code", oldCode),1)
		if (records.isEmpty()) {
			KeywordUtil.markFailed("Không tìm thấy bản ghi trong ${collectionName} với code: ${oldCode} trước khi chỉnh sửa.")
			return
		}
		GlobalVariable.oldDocSnapshot = records[0]
		KeywordUtil.logInfo("Đã lưu snapshot trước khi edit từ collection ${collectionName}: " + GlobalVariable.oldDocSnapshot.toJson())
	}

	//Kiểm tra dữ liệu đã được update thành công trong DB
	@And("Record in collection (.*) with oldCode (.*) is updated with name (.*), code (.*), description (.*), startDate (.*), endDate (.*), status (.*), version (.*) in database")
	def checkDB_Edit_Success(String collectionName, String oldCode, String name, String code, String description, String startDate, String endDate, String status, String version) {
		def dbName = "ProductPropertyManagement"

		if (code == oldCode) {
			def record = MongoUtils.findDocuments(dbName, collectionName, new Document("code", oldCode), 10)
			if (record.isEmpty()) {
				KeywordUtil.markFailed("Không tìm thấy bản ghi trong ${collectionName} với code: ${oldCode}")
				return
			} else if (record.size() > 1) {
				KeywordUtil.markFailed("Đã tạo bản ghi mới thay vì cập nhật trong ${collectionName} với code: ${oldCode}")
				return
			} else {
				verifyRecordFields(record[0], name, code, description, startDate, endDate, status, version)
			}
		} else {
			def oldRecord = MongoUtils.findDocuments(dbName, collectionName, new Document("code", oldCode), 1)
			def record = MongoUtils.findDocuments(dbName, collectionName, new Document("code", code), 1)
			if (!oldRecord.isEmpty() || record.isEmpty()) {
				KeywordUtil.markFailed("Bản ghi chưa được cập nhật trong ${collectionName}")
			} else {
				verifyRecordFields(record[0], name, code, description, startDate, endDate, status, version)
			}
		}
	}

	//Kiểm tra dữ liệu không thay đổi
	@And("Record in collection (.*) with oldCode (.*) is not updated in database")
	def checkDB_not_update(String collectionName, String oldCode) {
		Document oldDoc = GlobalVariable.oldDocSnapshot
		if (oldDoc == null) {
			KeywordUtil.markFailed("Chưa lưu snapshot trước khi edit. Vui lòng gọi bước lưu trước.")
			return
		}

		def record = MongoUtils.findDocuments("ProductPropertyManagement",collectionName, new Document("code", oldCode),1)

		if (record.isEmpty()) {
			KeywordUtil.markFailed("Không tìm thấy bản ghi trong ${collectionName} với code: ${oldCode}")
			return
		}

		def newDoc = record[0]
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
			KeywordUtil.markPassed("✅ Dữ liệu trong ${collectionName} không thay đổi sau khi nhấn Save.")
		} else {
			KeywordUtil.markFailed("❌ DB đã bị thay đổi trong ${collectionName}:\n" + differences.join("\n"))
		}
	}

	//Hàm phụ để so sánh các field
	def verifyRecordFields(Document record, String name, String code, String description, String startDate, String endDate, String status, String version) {
		assert record.getString("name") == name : "Tên không khớp. Expected: ${name}, Found: ${record.getString("name")}"
		assert record.getString("code") == code : "Code không khớp. Expected: ${code}, Found: ${record.getString("code")}"
		assert record.getString("description") == description : "Description không khớp"
		assert record.getString("startDate") == startDate : "StartDate không khớp"
		assert record.getString("endDate") == endDate : "EndDate không khớp"
		assert record.getString("status") == status : "Status không khớp"
		assert record.getString("version") == version : "Version không khớp"

		KeywordUtil.markPassed("✅ Dữ liệu đã được cập nhật chính xác trong MongoDB.")
	}

	//3. Delete
	@And("Record in collection (.*) with code (.*) is not in database")
	def checkDB_Success_Item(String collectionName, String code) {
		def query = new Document("code", code)
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", collectionName, query, 1)
		if (docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code + " đã được xóa trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi " + code + " vẫn còn trong DB" + docs[0].toJson())
		}
	}

	@And("Record in collection (.*) with code1 (.*) and code2 (.*) are not in database")
	def checkDB_Sucess_Many(String collectionName, String code1, String code2) {
		def query = new Document("\$or", Arrays.asList(
				new Document("code", code1),
				new Document("code", code2)
				))
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", collectionName, query, 2)

		if (docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code1 + " và " + code2 + " đã được xóa trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi vẫn còn trong DB")
		}
	}

	@And("Selected record in collection (.*) are not in database")
	def checkDB_DeleteAll(String collectionName) {
		ArrayList<String> tmp = GlobalVariable.deleteCode
		def query = ['code': [ '$in' : tmp]]
		def remain = MongoUtils.findDocuments("ProductPropertyManagement", collectionName , query, tmp.size())
		if (remain.isEmpty()) {
			KeywordUtil.markPassed(tmp.size() + " bản ghi đã được xóa trong DB")
		}else {
			KeywordUtil.markFailed("Cac bản ghi vẫn còn trong DB: \n")
			for (doc in remain) {
				println(doc.toJson())
			}
		}
	}

	@And("Record in collection (.*) with code (.*) is still in database")
	def checkDB_No_1(String collectionName, String code) {
		def query = new Document("code", code)
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", collectionName , query, 1)
		if (!docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code + " vẫn còn trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi " + code + " đã được xóa trong DB" )
		}
	}

	@And("Record in collection (.*) with code1 (.*) and code2 (.*) is still in database")
	def checkDB_No_2(String collectionName, String code1, String code2) {
		def query = new Document("\$or", Arrays.asList(
				new Document("code", code1),
				new Document("code", code2)
				))
		def docs = MongoUtils.findDocuments("ProductPropertyManagement", collectionName, query, 2)

		if (!docs.isEmpty()) {
			KeywordUtil.markPassed("Bản ghi " + code1 + " và " + code2 + " vẫn còn trong DB")
		}else {
			KeywordUtil.markFailed("Bản ghi đã được xóa trong DB")
		}
	}

	@And("No record in collection (.*) is deleted")
	def checkDB_not_selected(String collectionName) {
		int cntRecordAfter = MongoUtils.countDocuments("ProductPropertyManagement", collectionName)
		if (cntRecordAfter == GlobalVariable.cntRecord) {
			KeywordUtil.markPassed("Khong co ban ghi nao bi xoa")
		}else {
			KeywordUtil.markFailed("Da co ban ghi bi xoa")
		}
	}
}