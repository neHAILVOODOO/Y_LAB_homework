package ru.ylab.in.controllers;

import org.junit.jupiter.api.Test;
import ru.ylab.in.models.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionControllerTest {

    @Test
    public void testAddAdmin_whenAdminAlreadyExists() {
        List<User> userList = List.of(
                new User(1L, "user1", "password1", "ROLE_USER"),
                new User(2L, "admin", "password2", "ROLE_ADMIN")
        );

        SessionController sessionController = mock(SessionController.class);
        when(sessionController.addAdmin(2L, userList)).thenReturn(null);
        User result = sessionController.addAdmin(2L, userList);


        assertNull(result);
    }

    @Test
    public void testAddAdmin_whenAdminDoesNotExist() {

        List<User> userList = List.of(
                new User(1L, "user1", "password1", "ROLE_USER"),
                new User(2L, "user2", "password2", "ROLE_USER")
        );

        SessionController sessionController = mock(SessionController.class);

        when(sessionController.addAdmin(3L, userList)).thenReturn(new User(3L, "admin", "root", "ROLE_ADMIN"));

        User result = sessionController.addAdmin(3L, userList);

        assertNotNull(result);

        assertEquals("admin", result.getLogin());
        assertEquals("root", result.getPassword());
        assertEquals("ROLE_ADMIN", result.getRole());
    }

    @Test
    public void testRegister_whenUserAlreadyExists() {
        List<User> userList = List.of(
                new User(1L, "user1", "password1", "ROLE_USER"),
                new User(2L, "user2", "password2", "ROLE_USER"),
                new User(3L, "existingUser", "password3", "ROLE_USER")
        );

        SessionController sessionController = mock(SessionController.class);

        when(sessionController.register(4L, userList)).thenReturn(null);

        User result = sessionController.register(4L, userList);

        assertNull(result);
    }


    @Test
    public void testRegister_whenUserDoesNotExist() {
        // Создаем список пользователей без существующего пользователя с заданным логином
        List<User> userList = List.of(
                new User(1L, "user1", "password1", "ROLE_USER"),
                new User(2L, "user2", "password2", "ROLE_USER")
        );

        SessionController sessionController = mock(SessionController.class);

        when(sessionController.register(3L, userList)).thenReturn(new User(3L, "newUser", "password3", "ROLE_USER"));

        User result = sessionController.register(3L, userList);

        assertNotNull(result);

        assertEquals("newUser", result.getLogin());
        assertEquals("password3", result.getPassword());
        assertEquals("ROLE_USER", result.getRole());
    }

    @Test
    public void testLogin_whenUserExists() {

        List<User> userList = List.of(
                new User(1L, "user1", "password1", "ROLE_USER"),
                new User(2L, "user2", "password2", "ROLE_USER"),
                new User(3L, "existingUser", "password3", "ROLE_USER")
        );

        SessionController sessionController = mock(SessionController.class);

        when(sessionController.login(userList)).thenReturn(new User(3L, "existingUser", "password3", "ROLE_USER"));

        User result = sessionController.login(userList);

        assertNotNull(result);

        assertEquals("existingUser", result.getLogin());
        assertEquals("password3", result.getPassword());
        assertEquals("ROLE_USER", result.getRole());
    }

    @Test
    public void testLogin_whenUserDoesNotExist() {
        List<User> userList = List.of();

        SessionController sessionController = mock(SessionController.class);

        when(sessionController.login(userList)).thenReturn(null);

        User result = sessionController.login(userList);

        assertNull(result);
    }

}
