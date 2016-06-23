package lephee.chat.persistence.role;

import java.util.List;

public interface RoleDao {
	
	public void insert(Role role);
	
	public void updateOrInsert(Role role);
	
	public void remove(Role role);
	
	public Role queryByName(String name);

	public Role queryByUsername(String username);
	
	public Role queryById(int id);
	
	public int getMaxRoleId();

	public List<Role> queryAll();

	
}
