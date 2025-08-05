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

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import org.bson.Document

// B1: Chuẩn bị URI kết nối MongoDB
String mongoUri = "mongodb://mongodba:ODA%232025@10.165.11.194:27017,10.165.11.194:27018,10.165.11.194:27019/?replicaSet=rs0&authSource=admin"

// B2: Kết nối tới MongoDB
MongoClientURI uri = new MongoClientURI(mongoUri)
MongoClient mongoClient = new MongoClient(uri)

// B3: Lấy database
MongoDatabase database = mongoClient.getDatabase("StockManagement")

// B4: Lấy collection
MongoCollection<Document> collection = database.getCollection("StockItem")

// B5: Truy vấn 10 bản ghi đầu tiên
List<Document> docs = collection.find().limit(10).into(new ArrayList<>())

// B4: In ra dữ liệu
println("==== Dữ liệu 10 bản ghi đầu tiên trong StockItem ====")
for (Document doc : docs) {
    println(doc.toJson())
}
// Đóng kết nối
mongoClient.close()
