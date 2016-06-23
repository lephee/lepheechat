package lephee.chat.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import lephee.chat.persistence.record.Record;
import lephee.chat.persistence.record.RecordManager;
import lephee.chat.persistence.role.Role;
import lephee.chat.persistence.role.RoleManager;

public class RoleListServlet extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (CookieCacheManager.getInstance().contains(cookie)) {
				ModelAndView mv = new ModelAndView("roleList.jsp");
				List<Role> allRole = RoleManager.getInstance().getAllRole();
				StringBuilder b = new StringBuilder();
				List<String> rolelist = new ArrayList<String>();
				for (Role role : allRole) {
					b.append(role.getId()).append(",").append(role.getUsername()).append(",").append(role.getNickname()).append("\n");
					rolelist.add(role.getId()+","+role.getUsername()+","+role.getNickname()+"\n");
				}
				String msg = new String(b.toString().getBytes("UTF-8"),"UTF-8");
				mv.addObject("msg", allRole);
				System.out.println(msg);
				return mv;
			}
		}
		return new ModelAndView("login.html");
	}

}
