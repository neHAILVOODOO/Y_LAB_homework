package ru.ylab.in.controllers;

import org.junit.jupiter.api.Test;
import ru.ylab.in.models.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {

    AdminController adminController = new AdminController();

    @Test
    void updateID_shouldUpdateIDs() {

        List<User> userList = new ArrayList<>();
        userList.add(new User(null, "user1", "password1", "admin"));
        userList.add(new User(null, "user2", "password2", "user"));
        userList.add(new User(null, "user3", "password3", "user"));


        AdminController adminController = new AdminController();
        adminController.updateID(userList);

        List<Long> expectedIDs = new ArrayList<>();
        expectedIDs.add(1L);
        expectedIDs.add(2L);
        expectedIDs.add(3L);

        for (int i = 0; i < userList.size(); i++) {
            assertEquals(expectedIDs.get(i), userList.get(i).getId()+1);
        }
    }

    @Test
    void returnCurrentId_shouldReturnCurrentId() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "user1", "password1", "admin"));
        userList.add(new User(2L, "user2", "password2", "user"));
        userList.add(new User(3L, "user3", "password3", "user"));

        AdminController adminController = new AdminController();
        Long currentId = adminController.returnCurrentId(userList);

        assertEquals(3L, currentId);
    }

}
