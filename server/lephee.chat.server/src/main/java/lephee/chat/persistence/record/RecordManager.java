package lephee.chat.persistence.record;

import java.util.List;

public class RecordManager {
	
	RecordDao dao = new RecordDaoImpl();
	
	private static RecordManager instance;
	
	private RecordManager() {
		
	}
	
	public static RecordManager getInstance() {
		if (instance == null) {
			instance = new RecordManager();
		}
		return instance;
	}
	
	public void insert(Record entity) {
		dao.insert(entity);
	}
	
	public Record queryByName(String name) {
		return dao.queryByName(name);
	}
	
	public void updateOrInsert(Record role) {
		dao.updateOrInsert(role);
	}
	
	public void delete(Record role) {
		dao.remove(role);
	}
	
	public List<Record> getAllHistory() {
		return dao.queryAll();
	}
	
	public static void main(String[] args) {
	}

}
