package lephee.chat.persistence.role;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class RoleDaoImpl implements RoleDao{
	Datastore datastore;
	
	public RoleDaoImpl() {
		Morphia morphia = new Morphia();
		morphia.mapPackage("lephee.chat.persistence.role");

		// create the Datastore connecting to the default port on the local host
//		MongoCredential cred = MongoCredential.createCredential("wpf", "mydb", "123456".toCharArray());
//		List<MongoCredential> creds = Arrays.asList(cred);
//		ServerAddress addr = new ServerAddress();
		datastore = morphia.createDatastore(new MongoClient(), "chatserver");
		datastore.ensureIndexes();
	}
	
	public void insert(Role role) {
		Role query = queryById(role.getId());
		if (query != null) {
			System.err.println("Already exist!");
			return;
		}
		Key<Role> key = datastore.save(role);
		System.out.println("insert: " + key.toString());
	}
	
	public void updateOrInsert(Role role) {
		Key<Role> key = datastore.save(role);
		System.out.println("update: " + key);
	}
	
	public void remove(Role role) {
		WriteResult re = datastore.delete(role);
		System.out.println("delete: " + re);
	}
	
	public Role queryByName(String name) {
		Query<Role> query = datastore.createQuery(Role.class).field("name").equal(name);
		List<Role> list = query.asList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public Role queryById(int id) {
		Query<Role> query = datastore.createQuery(Role.class).field("id").equal(id);
		List<Role> list = query.asList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public int getMaxRoleId() {
		return (int) datastore.getCount(Role.class);
	}

	public Role queryByUsername(String username) {
		Query<Role> query = datastore.createQuery(Role.class).field("username").equal(username);
		List<Role> list = query.asList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<Role> queryAll() {
		return datastore.createQuery(Role.class).order("createDate").asList();
	}

}
