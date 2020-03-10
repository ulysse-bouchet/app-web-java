package services;

import mediatheque.Mediatheque;
import mediatheque.items.Document;
import mediatheque.items.EmpruntException;
import mediatheque.items.Utilisateur;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = "/borrow")
public class Borrow extends AbsServlet {

    @Override
    protected void serve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("user");

        if (u == null || u.isBibliothecaire())
            response.sendRedirect("/Projet/login");
        else {
            String id = request.getParameter("id");
            if(id == null) {
                List<Document> docs = Mediatheque.getInstance().tousLesDocuments();

                request.setAttribute("user", u);
                request.setAttribute("docs", docs);
                request.getRequestDispatcher("/WEB-INF/jsp/borrow.jsp").forward(request, response);
            } else {
                Document d = getMediatheque().getDocument(Integer.parseInt(id));
                String msg;
                try {
                    getMediatheque().emprunter(d, u);
                    msg = "Le document a bien été emprunté.";
                } catch (EmpruntException e) {
                    e.printStackTrace();
                    msg = "Une erreur est survenue lors de l'emprunt. Emprunt annulé.";
                }
                request.setAttribute("user", u);
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/WEB-INF/jsp/task_done.jsp").forward(request, response);
            }
        }
    }
}