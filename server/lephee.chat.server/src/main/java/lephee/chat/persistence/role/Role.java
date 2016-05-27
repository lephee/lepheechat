package lephee.chat.persistence.role;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import lephee.chat.util.ChatUtil;

@Entity("Role")
public class Role {
	@Id
	private int id;
	
	private String nickname;
	
	private String username;
	
	private String password;
	
	private Date createDate;
	
//	public Role(int id, String name) {
//		this.id = id;
//		this.nickname = name;
//		createDate = new Date();
//	}
	
	public static Role create(String username, String password) {
		Role role = new Role();
		role.id = ChatUtil.getNewId();
		role.username = username;
		role.password = password;
		role.nickname = username;
		role.createDate = new Date();
		return role;
	}
	
	public Role() {
		
	}

	
	public int getId() {
		return this.id;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Role["+nickname+"]";
	}
}
