package com.chat.dao.impl;


import com.chat.dao.IUserDAO;
import com.chat.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Provides methods that allow you to interact with users
 * Has one constructor which consists of three objects (instance
 * of Mongo, of Morphia and name of database in  String)
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
//        Query query = sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO user (name, password)\n" +
//                " VALUES (user.name, user.password)");
//        query.setParameter("name", user.getName());
    }

    /**
     * Returns a user by name
     * @param name name of user you want to load
     * @return instance of User
     */
    @Override
    public User loadUserByUsername(String name) {
//        Query query = sessionFactory.getCurrentSession().createQuery("");
        return null;
    }

    /**
     * Returns all users which are online (online is true)
     * @return list of users
     */
    @Override
    public List<User> getAllOnlineUser() {
//        return getDatastore().createQuery(User.class).filter("online", Boolean.TRUE).asList();
        return null;
    }

    /**
     * Returns all users
     * @return list of users
     */
    @Override
    public List<User> getAllUsers() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("");
//        return getDatastore().createQuery(User.class).asList();
        return null;
    }

    /**
     * Delete all users
     */
    @Override
    public void deleteAllUsers() {
        sessionFactory.getCurrentSession().createSQLQuery("delete from user");
//        deleteByQuery(getDatastore().createQuery(User.class));
    }
}
