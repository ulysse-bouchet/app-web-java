package services;

import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet (urlPatterns = "/add")
public class AddDocument extends AbsServlet {

    @Override
    protected void serve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("user");

        if (u == null || !u.isBibliothecaire())
            response.sendRedirect("/Projet/login");
        else {
            String type = request.getParameter("type");
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String ageMin = request.getParameter("ageMin");
            if(title == null || title.equals("") || author == null || author.equals("") || type == null || (type.equals("dvd") && ageMin == null)) {
                request.setAttribute("user", u);
                request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
            } else {
                int t = type.equals("dvd") ? 1 : 0;
                getMediatheque().nouveauDocument(t, title, author, ageMin);
                request.setAttribute("user", u);
                request.setAttribute("msg", "Document créé.");
                request.getRequestDispatcher("/WEB-INF/jsp/task_done.jsp").forward(request, response);
            }
        }
    }
}