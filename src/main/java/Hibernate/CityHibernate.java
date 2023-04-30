package Hibernate;

import Entity.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CityHibernate {
        private SessionFactory sessionFactory;

        public CityHibernate() {
            sessionFactory = HibernateUtil.getSessionFactory();
        }

        public List<City> getAll() {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM City", City.class).list();
            }
        }

        public City getById(int id) {
            try (Session session = sessionFactory.openSession()) {
                return session.get(City.class, id);
            }
        }

        public void save(City city) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(city);
                transaction.commit();
            }
        }

        public void update(City city) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(city);
                transaction.commit();
            }
        }

        public void delete(City city) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(city);
                transaction.commit();
            }
        }
    }