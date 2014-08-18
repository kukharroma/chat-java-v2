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
     * @param user user you want to update
     */
    public void update(User user);

    /**
     *
     * @param user user you want to update
     */
    public void updateOnline(User user, Boolean online);
    /**
     *
     *
     * @param name name of user you want to load
     * @return instance of user
     */
    public Object loadUserByUsername(String name);

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
