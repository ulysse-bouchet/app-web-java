package persistance;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * During the server startup, inject dependency to the datas
 * @author Bouchet Ulysse & Tadjer Badr
 * @see javax.servlet.ServletContextListener
 */
@WebListener
public class Listener implements ServletContextListener {

    /**
     * Called during startup
     * @param servletContextEvent The context of the application
     * @see persistance.LibraryData
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            //Dependency injection
            Class.forName("persistance.LibraryData");
        } catch (ClassNotFoundException ignored) { }
    }

    /**
     * Called during shutdown - does nothing
     * @param servletContextEvent The context of the application
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
