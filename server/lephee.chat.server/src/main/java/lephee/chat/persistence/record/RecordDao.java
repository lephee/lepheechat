package lephee.chat.persistence.record;

import java.util.List;

import lephee.chat.persistence.record.Record;

public interface RecordDao {
	
	public void insert(Record record);
	
	public void updateOrInsert(Record record);
	
	public void remove(Record record);
	
	public Record queryByName(String name);
	
	public Record queryById(int id);

	public List<Record> queryAll();
	
}
