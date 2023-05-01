package Hibernate;

import Entity.Grooup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GrooupHibernate {
        private SessionFactory sessionFactory;

        public GrooupHibernate() {
            sessionFactory = HibernateUtil.getSessionFactory();
        }

        public List<Grooup> getAll() {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM Grooup", Grooup.class).list();
            }
        }

        public Grooup getById(int id) {
            try (Session session = sessionFactory.openSession()) {
                return session.get(Grooup.class, id);
            }
        }

        public void save(Grooup group) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(group);
                transaction.commit();
            }
        }

        public void update(Grooup group) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.update(group);
                transaction.commit();
            }
        }

        public void delete(Grooup group) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.delete(group);
                transaction.commit();
            }
        }
    }