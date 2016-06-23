package lephee.chat.persistence.role;

import java.util.List;

import lephee.chat.util.ChatUtil;

public class RoleManager {
	
	RoleDao dao = new RoleDaoImpl();
	
	private static RoleManager instance;
	
	private RoleManager() {
		
	}
	
	public static RoleManager getInstance() {
		if (instance == null) {
			instance = new RoleManager();
		}
		return instance;
	}
	
	public void insert(Role entity) {
		dao.insert(entity);
	}
	
	public Role queryById(int id) {
		return dao.queryById(id);
	}
	
	public Role queryByName(String name) {
		return dao.queryByName(name);
	}
	
	public void updateOrInsert(Role role) {
		dao.updateOrInsert(role);
	}
	
	public void delete(Role role) {
		dao.remove(role);
	}
	
	public int getMaxRoleId() {
		return dao.getMaxRoleId();
	}
	
	public List<Role> getAllRole() {
		return dao.queryAll();
	}
	
	public Role checkRole(String username, String password) {
		Role role = dao.queryByUsername(username);
		if (role == null) {
			role = Role.create(username, password);
			insert(role);
		} else {
			String realPwd = role.getPassword();
			if (!realPwd.equals(password)) {
				role = null;
			}
		}
		return role;
	}
	
	public static void main(String[] args) {
		ChatUtil.init();
//		Role role = new Role(ChatUtil.getNewId(), "LePhee");
//		RoleManager.getInstance().updateOrInsert(role);
//		Role role2 = RoleManager.getInstance().queryByName("LePhee");
//		System.out.println(role2);
	}

}
