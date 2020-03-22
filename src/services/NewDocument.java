package services;

import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * An implementation of services.Servlet, which handles adding a new document
 * @author Bouchet Ulysse & Tadjer Badr
 * @see services.Servlet
 * @see javax.servlet.http.HttpServlet
 * @see javax.servlet.annotation.WebServlet
 */
@WebServlet (urlPatterns = "/add")
public class NewDocument extends Servlet {

    /**
     * Handles adding a new document
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

        //If he's not connected or if he's not a librarian
        if (u == null || !u.isBibliothecaire())
            //Redirects him to the login service
            response.sendRedirect("/Projet/login");
        else {
            //Tries to get the document datas from the request, given by the form
            String type = request.getParameter("type");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String ageMin = request.getParameter("ageMin");

            //If some datas are missing
            if(title == null || title.equals("") || author == null || author.equals("") || type == null || (type.equals("dvd") && ageMin.equals(""))) {
                //Sets the user as attribute to the request
                request.setAttribute("user", u);

                //Forwards the request to the JSP, which will display a form to add a document
                request.getRequestDispatcher("/WEB-INF/jsp/new_document.jsp").forward(request, response);
            } else {
                //Determines the document type
                int t = type.equals("dvd") ? 1 : 0;

                //Add the document to the library
                getLibrary().nouveauDocument(t, title, author, ageMin);

                //Sets the user and the message as attributes to the request
                request.setAttribute("user", u);
                request.setAttribute("msg", "Document créé.");

                //Forwards the request to a JSP that will display the message to the user
                request.getRequestDispatcher("/WEB-INF/jsp/display_message.jsp").forward(request, response);
            }
        }
    }
}