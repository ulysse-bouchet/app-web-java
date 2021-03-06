package services;

import mediatheque.items.Document;
import mediatheque.items.EmpruntException;
import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of services.Servlet, which handles the borrowing of a document by the user
 * @author Bouchet Ulysse & Tadjer Badr
 * @see services.Servlet
 * @see javax.servlet.http.HttpServlet
 * @see javax.servlet.annotation.WebServlet
 */
@WebServlet (urlPatterns = "/borrow")
public class Borrow extends Servlet {

    /**
     * Handles the borrowing of a document by the user
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
            //Tries to get the document id from the request, given by the form
            String id = request.getParameter("id");

            //If the id is undefined
            if(id == null) {
                //Gets all the documents
                List<Document> docs = getLibrary().tousLesDocuments();

                //Gets all the available documents
                List<Document> docsAvailable = new LinkedList<>();
                for (Document d : docs)
                    if(d.data()[3] == null)
                        docsAvailable.add(d);

                //Sets the user and the documents as attributes to the request
                request.setAttribute("user", u);
                request.setAttribute("docs", docsAvailable);

                //Forwards the request to the JSP, which will show the list of documents to the user
                request.getRequestDispatcher("/WEB-INF/jsp/borrow.jsp").forward(request, response);
            //If the id is defined
            } else {
                //Get the document from the Library
                Document d = getLibrary().getDocument(Integer.parseInt(id));

                //Initializes a response message
                String msg;

                //Tries to borrow the document by the user
                try {
                    getLibrary().emprunter(d, u);

                    //Sets a successful message
                    msg = "Le document a bien été emprunté.";
                //If the borrowing is unsuccessful
                } catch (EmpruntException | NullPointerException e) {
                    //Prints the error into the tomcat console, for debugging
                    e.printStackTrace();
                    //Sets an unsuccessful message
                    msg = "Une erreur est survenue lors de l'emprunt. Emprunt annulé.";
                }

                //Sets the user and the message as attributes to the request
                request.setAttribute("user", u);
                request.setAttribute("msg", msg);

                //Forwards the request to a JSP that will display the message to the user
                request.getRequestDispatcher("/WEB-INF/jsp/display_message.jsp").forward(request, response);
            }
        }
    }
}