package com.chat.dao.impl;


import com.chat.dao.IUserDAO;
import com.chat.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Provides methods that allow you to interact with users
 */
@Repository("userDAO")
public class UserDAO implements IUserDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;


    private static final Logger log = Logger.getLogger(UserDAO.class);

    /**
     * Saves a user
     * @param user user you want to save
     */
    public void save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    /**
     * Updates a user
     * @param user user you want to save
     */
    public void update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        sessionFactory.getCurrentSession().update(user);
        session.getTransaction().commit();
    }

    /**
     * Updates a user
     *
     * @param user user you want to update
     */
    @Override
    public void updateOnline(User user, Boolean online) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String name = user.getName();
        Query query = session.createSQLQuery("update user set user.online = :online where name = :name").addEntity(User.class);
        query.setParameter("name", name);
        query.setParameter("online", online);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    /**
     * Returns a user by name
     * @param name name of user you want to load
     * @return instance of User
     */
    @Override
    public User loadUserByUsername(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("select * from user where user.name = :name").addEntity(User.class);
        query.setParameter("name", name);
        session.getTransaction().commit();
        return (User) query.list().get(0);
    }

    /**
     * Returns all users which are online (online is true)
     * @return list of users
     */
    @Override
    public List<User> getAllOnlineUser() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("select * from user where user.online = true ").addEntity(User.class);
        session.getTransaction().commit();
        return query.list();
    }

    /**
     * Returns all users
     * @return list of users
     */
    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("select * from user ").addEntity(User.class);
        session.getTransaction().commit();
        return query.list();
    }

    /**
     * Delete all users
     */
    @Override
    public void deleteAllUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("delete from user");
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
