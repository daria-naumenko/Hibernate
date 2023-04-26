package Servlet;

import Entity.Grooup;
import Hibernate.GrooupHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/groups")
public class GrooupServlet extends HttpServlet {
    private GrooupHibernate groupHibernate;

    @Override
    public void init() throws ServletException {
        groupHibernate = new GrooupHibernate();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Grooup> groups = groupHibernate.getAll();
        req.setAttribute("groups", groups);
        req.getRequestDispatcher("/groups.jsp").forward(req, resp);
    }
}