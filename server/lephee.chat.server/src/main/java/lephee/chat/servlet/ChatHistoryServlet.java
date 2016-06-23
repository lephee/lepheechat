package lephee.chat.servlet;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import lephee.chat.persistence.record.Record;
import lephee.chat.persistence.record.RecordManager;

public class ChatHistoryServlet extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (CookieCacheManager.getInstance().contains(cookie)) {
				ModelAndView mv = new ModelAndView("chatHistory.jsp");
				List<Record> allHistory = RecordManager.getInstance().getAllHistory();
				StringBuilder b = new StringBuilder();
				for (Record record : allHistory) {
					b.append(record.getSendTime()).append(", ").append(record.getRoleId()).append(": ").append(record.getContent()).append("\n");
				}
				String msg = new String(b.toString().getBytes("UTF-8"),"UTF-8");
				mv.addObject("msg", msg);
				System.out.println(msg);
				return mv;
			}
		}
		return new ModelAndView("login.html");
	}

}
