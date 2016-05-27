package lephee.chat.persistence.record;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("Record")
public class Record {
	@Id
	private ObjectId id;
	
	private int roleId;
	
	private String content;
	
	private Date sendTime;
	
	public Record() {
		
	}
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public static Record create(int roleId, String content) {
		Record record = new Record();
		record.setRoleId(roleId);
		record.setContent(content);
		record.setSendTime(new Date());
		return record;
	}

}
