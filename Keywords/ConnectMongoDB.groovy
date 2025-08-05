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

// B5: Truy vấn và in kết quả
for (Document doc : collection.find()) {
	println(doc.toJson())
}

// Đóng kết nối
mongoClient.close()
