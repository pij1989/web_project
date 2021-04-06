package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.connector.ConnectionPool;
import com.pozharsky.dmitri.model.entity.User;
import com.pozharsky.dmitri.model.service.impl.UserServiceImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.*;

public class UserServiceTest {
    static final int COUNT = 3;
    UserService userService;
    User user;

    @BeforeTest
    public void setUp() {
        userService = UserServiceImpl.getInstance();
    }

    @BeforeGroups("findUser")
    public void setUpUser() {
        user = new User();
        user.setId(2L);
        user.setFirstName("Ivan");
        user.setLastName("Ivanov");
        user.setUsername("vaness");
        user.setEmail("ivan@gmail.com");
        user.setRoleType(User.RoleType.USER);
        user.setStatusType(User.StatusType.ACTIVE);
    }

    @AfterGroups
    public void tearDownUser() {
        user = null;
    }

    @Test(enabled = false)
    public void testLoginUserWithCorrectData() {
        String email = "ivan@gmail.com";
        String password = "12345678";
        AtomicInteger atomicInteger = new AtomicInteger(COUNT);
        Optional<User> expected = Optional.of(user);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.loginUser(email, password, atomicInteger);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testLoginUserWithIncorrectData() {
        String email = "ivan@gmail.com.";
        String password = "12345678";
        AtomicInteger atomicInteger = new AtomicInteger(COUNT);
        Optional<User> expected = Optional.empty();
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.loginUser(email, password, atomicInteger);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test(enabled = false)
    public void testFindUserByIdWithCorrectData() {
        long id = 2L;
        Optional<User> expected = Optional.of(user);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findUserById(id);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testFindUserByIdWithIncorrectData() {
        long id = 1000L;
        Optional<User> expected = Optional.empty();
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findUserById(id);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }

    @Test(enabled = false)
    public void testFindUserByEmailWithCorrectData() {
        String email = "ivan@gmail.com";
        Optional<User> expected = Optional.of(user);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findUserByEmail(email);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }

    @Test
    public void testFindUserByEmailWithIncorrectData() {
        String email = "ivan@gmail.com.";
        Optional<User> expected = Optional.empty();
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findUserByEmail(email);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
        assertEquals(actual, expected);
    }

    @AfterTest
    public void tearDown() {
        userService = null;
        ConnectionPool.getInstance().destroyPool();
    }
}