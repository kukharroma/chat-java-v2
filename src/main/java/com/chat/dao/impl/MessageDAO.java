package com.chat.dao.impl;


import com.chat.dao.IMessageDAO;
import com.chat.model.Message;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Provides methods that allow you to interact with messaging
 * Has one constructor which consists of three objects (instance
 * of Mongo, of Morphia and name of database in  String )
 *
 */
@Repository("messageDAO")
public class MessageDAO implements IMessageDAO {

    private static final Logger log = Logger.getLogger(MessageDAO.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Saves a message
     * @param message message you want to save
     */
    @Override
    public void save(Message message) {
        sessionFactory.getCurrentSession().save(message);
    }

    /**
     *
     * @return list of messages
     */
    @Override
    public List<Message> getLasHundredMessages() {
//        List<Message> list = getDatastore().createQuery(Message.class).asList();
//        if (list.size() < 100) {
//            return list;
//        } else {
//            return list.subList((list.size() - 100), list.size());
//        }
        return null;
    }

    /**
     *
     * @return list of messages
     */
    @Override
    public List<Message> getAllMessages() {
//        return getDatastore().createQuery(Message.class).asList();
        return null;
    }

    /**
     *
     * @param dateFrom - time from which you want to get messages
     * @return list of messages
     */
    @Override
    public List<Message> getMessagesFromSecond(String dateFrom) {
        long longDate = Long.valueOf(dateFrom);
        Date date = new Date(longDate);
//        return getDatastore().createQuery(Message.class).field("date").greaterThan(date).asList();
        return null;
    }

    /**
     * Delete all messages from database
     */
    @Override
    public void deleteAllMessages() {
//        deleteByQuery(getDatastore().createQuery(Message.class));

    }
}
