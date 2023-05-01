package Servlet;

import Entity.City;
import Entity.Grooup;
import Entity.Student;
import Hibernate.CityHibernate;
import Hibernate.GrooupHibernate;
import Hibernate.HibernateUtil;
import Hibernate.StudentHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentHibernate studentHibernate;
    private SessionFactory sessionFactory;
    private GrooupHibernate grooupHibernate;
    private CityHibernate cityHibernate;

    @Override
    public void init() throws ServletException {
        studentHibernate = new StudentHibernate();
        sessionFactory = HibernateUtil.getSessionFactory();
        grooupHibernate = new GrooupHibernate();
        cityHibernate = new CityHibernate();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = studentHibernate.getAll();
        req.setAttribute("studentsList", students);
        req.getRequestDispatcher("/student.jsp").forward(req, resp);
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action != null) {
        if (action.equals("update")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Student student = studentHibernate.getById(id);
                if (student != null) {
                    student.setName(request.getParameter("firstName"));
                    student.setLastname(request.getParameter("lastName"));
                    student.setAge(Integer.parseInt(request.getParameter("age")));

                    int grooupId = request.getParameter("grooupId") != null ? Integer.parseInt(request.getParameter("grooupId")) : 0;
                    Grooup grooup = grooupHibernate.getById(grooupId);
                    student.setGrooup(grooup);

                    int cityId = request.getParameter("cityId") != null ? Integer.parseInt(request.getParameter("cityId")) : 0;
                    City city = cityHibernate.getById(cityId);
                    student.setCity(city);

                    studentHibernate.update(student);
                }
            } catch (NumberFormatException e) {
            }
        } else if (action.equals("delete")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Student student = studentHibernate.getById(id);
                if (student != null) {
                    studentHibernate.delete(student);
                }
            } catch (NumberFormatException e) {
            }
        }
        response.sendRedirect(request.getContextPath() + "/student");
        return;
    }

    Session session = sessionFactory.getCurrentSession();
    session.beginTransaction();

    try {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int age = Integer.parseInt(request.getParameter("age"));
        int grooupId = request.getParameter("grooupId") != null && !request.getParameter("grooupId").isEmpty() ? Integer.parseInt(request.getParameter("grooupId")) : 0;
        int cityId = request.getParameter("cityId") != null && !request.getParameter("cityId").isEmpty() ? Integer.parseInt(request.getParameter("cityId")) : 0;

        Student student = new Student();
        student.setName(firstName);
        student.setLastname(lastName);
        student.setAge(age);

        Grooup grooup = grooupHibernate.getById(grooupId);
        if (grooup != null) {
            student.setGrooup(grooup);
        }

        City city = cityHibernate.getById(cityId);
        if (city != null) {
            student.setCity(city);
        }

        studentHibernate.save(student);
        session.getTransaction().commit();

        response.sendRedirect(request.getContextPath() + "/student");
    } catch (NumberFormatException ex) {
        session.getTransaction().rollback();
    }
}
}