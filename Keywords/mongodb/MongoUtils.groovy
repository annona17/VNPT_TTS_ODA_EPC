package mongodb

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

import internal.GlobalVariable

public class MongoUtils {
	static MongoDatabase getDatabase(String dbName) {
		return MongoConnection.getClient().getDatabase(dbName)
	}
	static MongoCollection<Document> getCollection(String dbName, String collectionName){
		return getDatabase(dbName).getCollection(collectionName)
	}
	static List<Document> findDocuments(String dbName, String collectionName, Document query, int limit = 10) {
		def collection = getCollection(dbName, collectionName)
		return collection.find(query).limit(limit).into(new ArrayList<>())
	}

	static long countDocuments(String dbName, String collectionName, Document query = new Document()) {
		def collection = getCollection(dbName, collectionName)
		return collection.countDocuments(query)
	}

	static void insertDocument(String dbName, String collectionName, Document doc) {
		def collection = getCollection(dbName, collectionName)
		collection.insertOne(doc)
	}

	static void updateDocument(String dbName, String collectionName, Document filter, Document update) {
		def collection = getCollection(dbName, collectionName)
		collection.updateOne(filter, update)
	}

	static void deleteDocument(String dbName, String collectionName, Document filter) {
		def collection = getCollection(dbName, collectionName)
		collection.deleteOne(filter)
	}
}
