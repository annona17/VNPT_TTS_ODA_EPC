import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import mongodb.MongoUtils
import mongodb.MongoConnection
import org.bson.Document

def docs1 = MongoUtils.findDocuments("ProductPropertyManagement", "ProductGroup", new Document(), 10)

for (doc in docs1) {
	println(doc.toJson())
}

def docs2 = MongoUtils.findDocuments("ProductPropertyManagement", "ProductProperty", new Document(), 10)

for (doc in docs2) {
	println(doc.toJson())
}

def docs3 = MongoUtils.findDocuments("ProductPropertyManagement", "ProductSpecification", new Document(), 10)

for (doc in docs3) {
	println(doc.toJson())
}

def docs4 = MongoUtils.findDocuments("ProductPropertyManagement", "TmfEvent", new Document(), 10)

for (doc in docs4) {
	println(doc.toJson())
}

// Đóng kết nối nếu cần
MongoConnection.closeConnection()
