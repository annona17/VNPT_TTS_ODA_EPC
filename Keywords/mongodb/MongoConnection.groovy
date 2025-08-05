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
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import internal.GlobalVariable

public class MongoConnection {
	private static MongoClient client = null
	static MongoClient getClient() {
		if (client == null) {
			String uri = "mongodb://mongodba:ODA%232025@10.165.11.194:27017,10.165.11.194:27018,10.165.11.194:27019/?replicaSet=rs0&authSource=admin"
			MongoClientURI mongoUri = new MongoClientURI(uri)
			client = new MongoClient(mongoUri)
		}
		return client
	}

	static void closeConnection() {
		if (client != null) {
			client.close();
			client = null
		}
	}
}
