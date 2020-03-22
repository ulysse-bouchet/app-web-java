package services;

import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * An implementation of services.Servlet, which handles the booking of a document by the user
 * @author Bouchet Ulysse & Tadjer Badr
 * @see services.Servlet
 * @see javax.servlet.http.HttpServlet
 * @see javax.servlet.annotation.WebServlet
 */
@WebServlet (urlPatterns = "/book")
public class Book extends Servlet {

    /**
     * Handles the booking of a document by the user
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

        //If he's not connected or if he's a librarian
        if (u == null || u.isBibliothecaire())
            //Redirects him to the login service
            response.sendRedirect("/Projet/login");
        else {
            //Sets the user and the message as attributes of the request
            request.setAttribute("user", u);
            request.setAttribute("msg", "Cette fonctionnalité n'est pas encore disponible.");

            //As booking is not supported yet, just displays a message
            request.getRequestDispatcher("/WEB-INF/jsp/display_message.jsp").forward(request, response);
        }
    }
}
