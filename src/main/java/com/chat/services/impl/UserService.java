package com.chat.services.impl;

import com.chat.dao.IUserDAO;
import com.chat.model.User;
import com.chat.services.IUserService;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Represents methods for work with users
 */
@Service
public class UserService implements IUserService, UserDetailsService {

    private static final Logger log = Logger.getLogger(UserService.class);

    @Resource(name = "userDAO")
    IUserDAO userDAO;


    /**
     * Returns a user by name
     *
     * @param username name of user you want to get
     * @return instance of User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userDAO.loadUserByUsername(username);
    }

    /**
     * @param user user you want to save
     */
    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    /**
     * @param user user you want to update
     */
    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    /**
     * @param user user you want to update
     */
    @Override
    public void updateOnline(User user, Boolean online) {
        userDAO.updateOnline(user, online);
    }

    /**
     * @return list of users
     */
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * @return list of users
     */
    public List<User> getAllOnlineUser(){
       return userDAO.getAllOnlineUser();
    }

    /**
     * Deletes all users
     */
    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }
}
