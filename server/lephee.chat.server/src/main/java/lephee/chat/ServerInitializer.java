package lephee.chat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerInitializer implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		new ChatServer().run();
	}

}
