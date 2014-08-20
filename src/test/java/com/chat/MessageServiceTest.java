package com.chat;


import com.chat.model.Message;
import com.chat.model.User;
import com.chat.services.impl.MessageService;
import com.chat.services.impl.UserService;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Represents methods which execute tests
 * on class MessageService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/all-spring-config.xml")
public class MessageServiceTest extends Assert {

    @Resource(name = "messageService")
    private MessageService messageService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "&sessionFactory")
    private LocalSessionFactoryBean sessionFactory;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    private static Configuration cfg;

    private Connection conn;

    @Before
    public void setup() throws Exception {
        if (null == cfg) {
            cfg = sessionFactory.getConfiguration();
        }

        conn = dataSource.getConnection();
        SchemaExport exporter = new SchemaExport(cfg, conn);
        exporter.execute(true, true, false, true);
    }

    @After
    public void teardown() throws Exception {
        if (null != conn) {
            conn.createStatement().close();
            conn.close();
            conn = null;
        }
    }

    /**
     * Tests saving a message
     */
    @Test
    public void testMessageSave() {
        User user = createUser("name", "password", false);
        Message message = createMessage(user, "text", new Date());
        List<Message> list = messageService.getAllMessages();
        assertTrue(list.isEmpty());
        userService.save(user);
        messageService.save(message);
        list = messageService.getAllMessages();
        assertTrue(!list.isEmpty());
        assertTrue(equalsMessages(message, list.get(0)));
    }

    /**
     * Tests saving a message with empty text
     */
    @Test(expected = NullPointerException.class)
    public void testMessageSaveWithEmptyMessage() {
        User user = createUser("name", "password", false);
        Message message = createMessage(user, null, new Date());
        List<Message> list = messageService.getAllMessages();
        assertTrue(list.isEmpty());
        userService.save(user);
        messageService.save(message);
        list = messageService.getAllMessages();
        assertTrue(!list.isEmpty());
        assertTrue(equalsMessages(message, list.get(0)));
    }

    /**
     * Tests getting all messages from database
     */
    @Test
    public void testGetAllMessages() {
        User user = createUser("name", "password", false);
        Message message1 = createMessage(user, "message1", new Date());
        Message message2 = createMessage(user, "message2", new Date());
        Message message3 = createMessage(user, "message3", new Date());
        Message message4 = createMessage(user, "message4", new Date());
        Message message5 = createMessage(user, "message5", new Date());
        Message message6 = createMessage(user, "message6", new Date());
        userService.save(user);
        messageService.save(message1);
        messageService.save(message2);
        messageService.save(message3);
        messageService.save(message4);
        messageService.save(message5);
        messageService.save(message6);

        List<Message> list = messageService.getAllMessages();

        assertTrue(equalsMessages(message1, list.get(0)));
        assertTrue(equalsMessages(message2, list.get(1)));
        assertTrue(equalsMessages(message3, list.get(2)));
        assertTrue(equalsMessages(message4, list.get(3)));
        assertTrue(equalsMessages(message5, list.get(4)));
        assertTrue(equalsMessages(message6, list.get(5)));

    }

    /**
     * Tests getting last hundred messages from database
     */
    @Test
    public void testGetLastHundredMessages() {
        User user = createUser("name", "password", true);
        userService.save(user);
        for (int k = 0; k < 125; k++) {
            Message message = createMessage(user, "mess" + k, new Date());
            messageService.save(message);
        }

        List<Message> list = messageService.getLasHundredMessages();
        assertEquals(100, list.size());
        assertTrue(list.get(0).getMessage().equals("mess25"));
        assertTrue(list.get(99).getMessage().equals("mess124"));
    }

    /**
     * Tests deleting all messages from database
     */
    @Test
    public void testDeleteAllMessages() {
        User user = createUser("name", "password", false);
        Message message1 = createMessage(user, "message1", new Date());
        Message message2 = createMessage(user, "message2", new Date());
        Message message3 = createMessage(user, "message3", new Date());
        messageService.save(message1);
        messageService.save(message2);
        messageService.save(message3);

        List<Message> list = messageService.getAllMessages();
        assertTrue(!list.isEmpty());
        messageService.deleteAllMessages();
        list = messageService.getAllMessages();
        assertTrue(list.isEmpty());
    }


    /**
     * Equals two instance of Message. If objects are equal
     * returns true else returns false.
     *
     * @param first  message you want to compare
     * @param second message with whom you want to compare
     * @return If objects are equal returns true else returns false.
     */
    public boolean equalsMessages(Message first, Message second) {
        boolean result = true;
        result = result && first.getId() == second.getId();
        result = result && first.getDate().equals(second.getDate());
        result = result && first.getMessage().equals(second.getMessage());
        result = result && equalsUsers(first.getSender(), second.getSender());
        return result;
    }

    /**
     * Creates an instance of Message
     *
     * @param user user who created a message
     * @param mess text of message
     * @param date date when message was created
     * @return instance of message
     */
    private Message createMessage(User user, String mess, Date date) {
        Message message = new Message();
        message.setDate(date);
        message.setSender(user);
        message.setMessage(mess);
        return message;
    }

    /**
     * Creates an instance of User
     *
     * @param name     name of user
     * @param password user's password
     * @param online   true if user is online, false if user is offline.
     * @return created instance of User
     */
    private User createUser(String name, String password, Boolean online) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setOnline(online);
        return user;
    }

    /**
     * Equals two instance of User. If objects are equal
     * returns true else returns false.
     *
     * @param firstUser  user you want to compare
     * @param secondUser user with whom you want to compare
     * @return If objects are equal returns true else returns false.
     */
    private boolean equalsUsers(User firstUser, User secondUser) {
        boolean result = true;
        result = result && firstUser.getId() == (secondUser.getId());
        result = result && firstUser.getUsername().equals(secondUser.getUsername());
        result = result && firstUser.getPassword().equals(secondUser.getPassword());
        result = result && firstUser.getName().equals(secondUser.getName());
        return result;
    }
}
