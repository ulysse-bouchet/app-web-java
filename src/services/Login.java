package services;

import mediatheque.Mediatheque;
import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * An implementation of services.Servlet, which handles the login of the user
 * @author Bouchet Ulysse & Tadjer Badr
 * @see services.Servlet
 * @see javax.servlet.http.HttpServlet
 * @see javax.servlet.annotation.WebServlet
 */
@WebServlet(urlPatterns = {"/login"})
public class Login extends Servlet {

    /**
     * Handles the login of the user
     * @param request The request made by the client
     * @param response The response to send to the client
     * @throws ServletException may be thrown
     * @throws IOException may be thrown
     */
    protected void serve (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Gets the session of the user
        HttpSession session = request.getSession();

        //Tries to get the user from the session, finds it if he's connected
        Utilisateur u = (Utilisateur) session.getAttribute("user");

        //If the user is already connected
        if (u != null) {
            //Redirects him to his personal space
            response.sendRedirect("/Projet/home");
        //If he's not connected yet
        } else {
            //Gets his login and password from the request, given by the form
            String login = request.getParameter("login");
            String pwd = request.getParameter("pwd");

            //If both fields have been entered
            if (login != null || pwd != null) {
                //Get access to the library
                Mediatheque m = getLibrary();

                //Tries to get the user from the login & password
                u = m.getUser(login, pwd);

                //If he exists
                if (u != null) {
                    //Put him in the session
                    request.getSession().setAttribute("user", u);
                }
                /*
                Redirects him to the login page, which will :
                    Redirect him to his personal space if he has been connected
                    Else show the connection form
                 */
                response.sendRedirect("/Projet/login");
            //If at least one field is not inputed
            } else {
                //Forwards the request to the JSP, which contains a login form
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            }
        }
    }
}
