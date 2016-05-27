package lephee.chat.persistence.record;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class RecordDaoImpl implements RecordDao{
	Datastore datastore;
	
	public RecordDaoImpl() {
		Morphia morphia = new Morphia();
		morphia.mapPackage("lephee.chat.persistence.record");

		// create the Datastore connecting to the default port on the local host
//		MongoCredential cred = MongoCredential.createCredential("wpf", "mydb", "123456".toCharArray());
//		List<MongoCredential> creds = Arrays.asList(cred);
//		ServerAddress addr = new ServerAddress();
		datastore = morphia.createDatastore(new MongoClient(), "chatserver");
		datastore.ensureIndexes();
	}
	
	public void insert(Record record) {
		Key<Record> key = datastore.save(record);
		System.out.println("insert: " + key.toString());
	}
	
	public void updateOrInsert(Record record) {
		Key<Record> key = datastore.save(record);
		System.out.println("update: " + key);
	}
	
	public void remove(Record record) {
		WriteResult re = datastore.delete(record);
		System.out.println("delete: " + re);
	}
	
	public Record queryByName(String name) {
		Query<Record> query = datastore.createQuery(Record.class).field("name").equal(name);
		List<Record> list = query.asList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public Record queryById(int id) {
		Query<Record> query = datastore.createQuery(Record.class).field("_id").equal(id);
		List<Record> list = query.asList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public int getMaxRecordId() {
		return (int) datastore.getCount(Record.class);
	}

}
