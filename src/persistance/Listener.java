package persistance;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("persistance.MediathequeData");
        } catch (ClassNotFoundException ignored) { }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
