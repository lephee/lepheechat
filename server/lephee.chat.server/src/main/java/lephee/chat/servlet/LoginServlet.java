package lephee.chat.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import lephee.chat.persistence.role.Role;
import lephee.chat.persistence.role.RoleManager;
import lephee.chat.server.ClientConnectorManager;

public class LoginServlet extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("u");
		String password = request.getParameter("p");
		if (!username.matches("^[a-zA-Z][a-zA-Z0-9_]{4,15}") || !password.matches("^[a-zA-Z]\\w{5,17}$")) {
			return new ModelAndView("login.html");
		}
		String value = username+password+System.currentTimeMillis();
		Cookie cookie = new Cookie("chatcookie", value);
//		Role role = RoleManager.getInstance().checkRole(username, password);
//		if (role != null) {
//			ClientConnectorManager.getInstance().addClient(role.getId(), ctx);
//		}
		response.addCookie(cookie);
		CookieCacheManager.getInstance().addCookie(cookie);
		return new ModelAndView("admin.jsp");
	}

}
