package com.chat.dao;


import com.chat.model.User;

import java.util.List;

/**
 * You can implement this interface if you want to
 * declare method which will work with messages
 *
 */
public interface IUserDAO {

    /**
     *
     * @param user user you want to save
     */
    public void save(User user);

    /**
     *
     * @param username name of user you want to load
     * @return instance of user
     */
    public User loadUserByUsername(String username);

    /**
     *
     * @return list of users
     */
    public List<User> getAllOnlineUser();

    /**
     *
     * @return list of users
     */
    public List<User> getAllUsers();

    /**
     * Delete all users
     */
    public void deleteAllUsers();
}
