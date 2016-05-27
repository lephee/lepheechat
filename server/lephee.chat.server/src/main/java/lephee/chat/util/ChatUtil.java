package lephee.chat.util;

import java.util.concurrent.atomic.AtomicInteger;

import lephee.chat.persistence.role.RoleManager;

public class ChatUtil {

	private static AtomicInteger currentId;
	
	public static void init() {
		currentId = new AtomicInteger(RoleManager.getInstance().getMaxRoleId());
	}

	public static int getNewId() {
		return currentId.incrementAndGet();
	}
}
