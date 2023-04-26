package Servlet;

import Entity.City;
import Entity.Grooup;
import Entity.Student;
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

    @Override
    public void init() throws ServletException {
        studentHibernate = new StudentHibernate();
        sessionFactory = HibernateUtil.getSessionFactory();
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

                    int id = Integer.parseInt(request.getParameter("id"));
                    Student student = studentHibernate.getById(id);
                    student.setName(request.getParameter("firstName"));
                    student.setLastname(request.getParameter("lastName"));
                    student.setAge(Integer.parseInt(request.getParameter("age")));

                    Grooup grooup = new Grooup();
                    grooup.setTitle(request.getParameter("grooupTitle"));
                    student.setGrooup(grooup);

                    City city = new City();
                    city.setName(request.getParameter("cityName"));
                    student.setCity(city);

                    studentHibernate.update(student);

                } else if (action.equals("delete")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Student student = studentHibernate.getById(id);
                    studentHibernate.delete(student);
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
            String grooupTitle = request.getParameter("grooupTitle");
            String cityName = request.getParameter("cityName");

            Student student = new Student();
            student.setName(firstName);
            student.setLastname(lastName);
            student.setAge(age);

            Grooup grooup = new Grooup();
            grooup.setTitle(grooupTitle);
            session.saveOrUpdate(grooup);
            student.setGrooup(grooup);

            City city = new City();
            city.setName(cityName);
            session.saveOrUpdate(city);
            student.setCity(city);

            studentHibernate.save(student);
            session.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/student");
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
}