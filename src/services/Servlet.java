package services;

import mediatheque.Mediatheque;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstraction of a Servlet
 * We want the app to behave the same way if the request is a POST request or a GET request
 * @author Bouchet Ulysse & Tadjer Badr
 */
public abstract class Servlet extends HttpServlet {
    private static Mediatheque m;

    /**
     * Delegates the service to the serve() method
     * @param request The request made by the client
     * @param response The response to send to the client
     * @throws ServletException may be thrown
     * @throws IOException may be thrown
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serve (request, response);
    }

    /**
     * Delegates the service to the serve() method
     * @param request The request made by the client
     * @param response The response to send to the client
     * @throws ServletException may be thrown
     * @throws IOException may be thrown
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serve (request, response);
    }

    /**
     * The service handled by the request will be implemented by this class implementations
     * @param request The request made by the client
     * @param response The response to send to the client
     * @throws ServletException may be thrown
     * @throws IOException may be thrown
     */
    protected abstract void serve (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * Get access to the library
     * @return The instance of the library
     */
    protected static Mediatheque getLibrary() {
        if (m == null)
            m = Mediatheque.getInstance();
        return m;
    }

}
