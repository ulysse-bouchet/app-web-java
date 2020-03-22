package services;

import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * An implementation of services.Servlet, which handles the access to the personal space of the user
 * @author Bouchet Ulysse & Tadjer Badr
 * @see services.Servlet
 * @see javax.servlet.http.HttpServlet
 * @see javax.servlet.annotation.WebServlet
 */
@WebServlet (urlPatterns = "/home")
public class Home extends Servlet {

    /**
     * Handles the acces to the personal space of the user
     * @param request The request made by the client
     * @param response The response to send to the client
     * @throws ServletException may be thrown
     * @throws IOException may be thrown
     */
    @Override
    protected void serve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Gets the session of the user
        HttpSession session = request.getSession();

        //Tries to get the user from the session, finds it if he's connected
        Utilisateur u = (Utilisateur) session.getAttribute("user");

        //If he's not connected
        if (u == null)
            //Redirects him to the login service
            response.sendRedirect("/Projet/login");
        else {
            //Adds an attribute to the request, which will be used in the JSP
            request.setAttribute("user", u);

            //Forwards the request to the JSP, which contains the personal space interface for both types of users
            request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
        }
    }
}
