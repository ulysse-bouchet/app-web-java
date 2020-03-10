package services;

import mediatheque.Mediatheque;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbsServlet extends HttpServlet {
    private static Mediatheque m;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serve (request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        serve (request, response);
    }

    protected abstract void serve (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected static Mediatheque getMediatheque () {
        if (m == null)
            m = Mediatheque.getInstance();
        return m;
    }

}
