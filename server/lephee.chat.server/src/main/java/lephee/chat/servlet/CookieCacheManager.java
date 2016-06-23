package lephee.chat.servlet;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

public class CookieCacheManager {
	
	private static CookieCacheManager instance;
	
	private Map<String, Date> cookieMap = new HashMap<String, Date>();
	
	public static CookieCacheManager getInstance() {
		if (instance == null) {
			instance = new CookieCacheManager();
		}
		return instance;
	}
	
	private CookieCacheManager() {
		
	}
	
	public boolean contains(Cookie cookie) {
		if (cookieMap.containsKey(cookie.getValue())) {
			Date date = cookieMap.get(cookie.getValue());
			if (System.currentTimeMillis() - date.getTime() > 60 * 60 * 1000L) {
				cookieMap.remove(cookie.getValue());
				return false;
			}
			return true;
		}
		return false;
	}
	
	public void addCookie(Cookie cookie) {
		cookieMap.put(cookie.getValue(), new Date());
	}

}
