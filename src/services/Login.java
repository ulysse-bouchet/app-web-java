package services;

import mediatheque.Mediatheque;
import mediatheque.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class Login extends AbsServlet {

    protected void serve (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("user");

        if (u != null) {
            //Si l'utilisateur est déjà connecté, on le redirige vers son espace personnel.
            response.sendRedirect("/Projet/home");
        } else {
            String login = request.getParameter("login");
            String pwd = request.getParameter("pwd");

            if (login != null || pwd != null) {
                //Si l'utilisateur essaye de se connecter, on vérifie s'il existe
                Mediatheque m = Mediatheque.getInstance();
                u = m.getUser(login, pwd);
                if (u != null) {
                    //S'il existe, on l'enregistre dans la session
                    request.getSession().setAttribute("user", u);
                }
                /*  On le redirige vers la même page
                *   Elle le redirigera vers son espace s'il a réussi à se connecter
                *   Elle affichera le formulaire de connexion sinon
                */
                response.sendRedirect("/Projet/login");
            } else {
                //On affiche un formulaire de connexion
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            }
        }
    }
}
