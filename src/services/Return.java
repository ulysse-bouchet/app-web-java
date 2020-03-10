package services;

import mediatheque.Mediatheque;
import mediatheque.items.Document;
import mediatheque.items.EmpruntException;
import mediatheque.items.RetourException;
import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet (urlPatterns = "/return")
public class Return extends AbsServlet {

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
                List<Document> docsEmpruntes = new LinkedList<>();
                for (Document d : docs) {
                    Utilisateur currentUser = (Utilisateur) d.data()[4];
                    if(currentUser == null)
                        continue;
                    if(currentUser.data()[0] == u.data()[0])
                        docsEmpruntes.add(d);
                }

                request.setAttribute("user", u);
                request.setAttribute("docs", docsEmpruntes);
                request.getRequestDispatcher("/WEB-INF/jsp/return.jsp").forward(request, response);
            } else {
                Document d = getMediatheque().getDocument(Integer.parseInt(id));
                String msg;
                try {
                    getMediatheque().rendre(d, u);
                    msg = "Le document a bien été retourné.";
                } catch (RetourException e) {
                    e.printStackTrace();
                    msg = "Une erreur est survenue lors du retour. Retour annulé.";
                }
                request.setAttribute("user", u);
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/WEB-INF/jsp/task_done.jsp").forward(request, response);
            }
        }
    }
}
