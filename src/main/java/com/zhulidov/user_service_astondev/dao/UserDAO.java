package com.zhulidov.user_service_astondev.dao;


import com.zhulidov.user_service_astondev.config.AppComponent;
import com.zhulidov.user_service_astondev.model.User;
import com.zhulidov.user_service_astondev.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

import java.util.List;
@AppComponent
public class UserDAO {
    public UserDAO() {
        System.out.println("created");
    }

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }

        }
    }


    public User getUserById(Long id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(User.class, id);
        }
    }


    public List<User> getAllUsers() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM User", User.class).list();
        }
    }


    public void updateUser(User user) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();

        } catch (Exception e) {
           if (transaction != null){
               transaction.rollback();
           }
           e.getMessage();
        }

    }


    public void deleteUser(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null){
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            throw new TransactionException("Ошибка при удалении пользователя");
        }
    }
}
