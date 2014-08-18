package com.chat.services;


import com.chat.model.User;

import java.util.List;

/**
 * Represents methods which you can implements if
 * you want to work with users
 */
public interface IUserService {

    /**
     * Saves a user
     * @param user user you want to save
     */
    public void save(User user);

    /**
     * Updates a user
     * @param user user you want to update
     */
    public void update(User user);

    /**
     * Updates a user param online
     * @param user user you want to save
     */
    public void updateOnline(User user, Boolean online);
    /**
     * Returns all users
     *
     * @return  list of users
     */
    public List<User> getAllUsers();

    /**
     * Returns all users which are online
     *
     * @return list of users
     */
    public List<User> getAllOnlineUser();

    /**
     * Deletes all users
     */
    public void deleteAllUsers();


}
