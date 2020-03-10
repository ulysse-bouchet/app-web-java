package services;

import mediatheque.Mediatheque;
import mediatheque.items.Document;
import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = "/book")
public class Book extends AbsServlet {

    @Override
    protected void serve(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("user");

        if (u == null || u.isBibliothecaire())
            response.sendRedirect("/Projet/login");
        else {
            request.setAttribute("user", u);
            request.getRequestDispatcher("/WEB-INF/jsp/book.jsp").forward(request, response);
        }
    }
}
