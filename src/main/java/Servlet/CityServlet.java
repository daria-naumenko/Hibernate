package Servlet;

import Entity.City;
import Hibernate.CityHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cities")
public class CityServlet extends HttpServlet {
    private CityHibernate cityHibernate;

    @Override
    public void init() throws ServletException {
        cityHibernate = new CityHibernate();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<City> cities = cityHibernate.getAll();
        req.setAttribute("cities", cities);
        req.getRequestDispatcher("/cities.jsp").forward(req, resp);
    }
}