package com.chat;

import com.chat.model.User;
import com.chat.services.impl.RegistrationService;
import com.chat.services.impl.UserService;
import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;
import org.bson.types.ObjectId;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

/**
 * Represents methods which execute tests
 * on class RegistrationService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/all-spring-config.xml")
public class RegistrationServiceTest extends Assert {

    @Resource(name = "registrationService")
    private RegistrationService registrationService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "&sessionFactory")
    private LocalSessionFactoryBean sessionFactory;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    private static Configuration cfg;

    private Connection conn;

    @Before
    public  void setup() throws Exception {
        if (null == cfg) {
            cfg = sessionFactory.getConfiguration();
        }

        conn = dataSource.getConnection();
        SchemaExport exporter = new SchemaExport(cfg, conn);
        exporter.execute(true, true, false, true);
    }

    @After
    public  void teardown() throws Exception {
        if (null != conn) {
            conn.createStatement().execute("SHUTDOWN");
            conn.close();
            conn = null;
        }
    }

    /**
     * Tests registration a user who is valid
     */
    @Test
    public void testRegistrationUser() {
        User user = createUser("name", "password", false);
        Map<String, Object> map = registrationService.registration(user);
        assertNull(map);
        User testUser = (User) userService.loadUserByUsername("name");
        assertTrue(equalsUsers(user, testUser));
    }

    /**
     * Tests registration a user who is not valid.
     * User has empty mame.
     */
    @Test
    public void testRegistrationUserEmptyName() {
        User user = createUser("", "password", false);
        Map<String, Object> map = registrationService.registration(user);
        assertNotNull(map);
        assertTrue(map.size() == 1);
        assertTrue(map.containsKey("nameFailed"));
    }

    /**
     * Tests registration a user who is not valid.
     * User has empty password.
     */
    @Test
    public void testRegistrationUserEmptyPassword() {
        User user = createUser("name", "", false);
        Map<String, Object> map = registrationService.registration(user);
        assertNotNull(map);
        assertTrue(map.size() == 1);
        assertTrue(map.containsKey("passwordFailed"));
    }

    /**
     * Tests registration a user who is not valid.
     * User has empty password and name.
     */
    @Test
    public void testRegistrationUserEmptyPasswordAndName() {
        User user = createUser("", "", false);
        Map<String, Object> map = registrationService.registration(user);
        assertNotNull(map);
        assertTrue(map.size() == 2);
        assertTrue(map.containsKey("passwordFailed"));
        assertTrue(map.containsKey("nameFailed"));
    }

    /**
     * Tests registration a user into database
     * User is valid.
     */
    @Test
    public void testRegisterUser() {
        User user = createUser("name", "password", false);
        registrationService.registerUser(user);
        User testUser = (User) userService.loadUserByUsername("name");
        assertTrue(equalsUsers(user, testUser));
    }

    /**
     * Tests registration a user into database
     * when user has empty name
     */
    @Test
    public void testRegisterUserEmptyName() {
        User user = createUser("", "password", false);
        registrationService.registerUser(user);
        User testUser = (User) userService.loadUserByUsername("");
        assertTrue(equalsUsers(user, testUser));
    }

    /**
     * Tests registration a user into database
     * when user has empty password
     */
    @Test
    public void testRegisterUserEmptyPassword() {
        User user = createUser("name", "", false);
        registrationService.registerUser(user);
        User testUser = (User) userService.loadUserByUsername("name");
        assertTrue(equalsUsers(user, testUser));
    }

    /**
     * Tests registration a user into database
     * when user has empty password and name
     */
    @Test
    public void testRegisterUserEmptyPasswordAndName() {
        User user = createUser("", "", false);
        registrationService.registerUser(user);
        User testUser = (User) userService.loadUserByUsername("");
        assertTrue(equalsUsers(user, testUser));
    }

    /**
     * Tests setting authority for user
     */
    @Test
    public void testSetAuthority() {
        User user = createUser("name", "password", false);
        registrationService.setAuthority(user);
        assertNotNull(user.getAuthorities());
    }

    /**
     * Tests setting authority if user is null
     */
    @Test(expected = NullPointerException.class)
    public void testSetAuthorityForNull() {
        registrationService.setAuthority(null);
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

}
