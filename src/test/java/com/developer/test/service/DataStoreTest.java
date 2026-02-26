package com.developer.test.service;

import com.developer.test.model.Task;
import com.developer.test.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

public class DataStoreTest {

    private DataStore dataStore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataStore = new DataStore();
    }

    @Test
    void testSaveUserSuccess() {
        User newUser = new User();
        newUser.setName("Test User");
        newUser.setEmail("test@example.com");
        newUser.setRole("tester");

        User savedUser = dataStore.saveUser(newUser);

        assertEquals(4, savedUser.getId()); // The initial number of users is 3, and the ID for the new user should be 4

        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("tester", savedUser.getRole());

        User retrievedUser = dataStore.getUserById(savedUser.getId());
        assertNotNull(retrievedUser);
        assertEquals(savedUser.getId(), retrievedUser.getId());
    }

    @Test
    void testSaveTaskSuccess() {
        Task newTask = new Task();
        newTask.setTitle("New Task");
        newTask.setStatus("pending");
        newTask.setUserId(1);

        Task savedTask = dataStore.saveTask(newTask);

        assertEquals(4, savedTask.getId()); // The initial number of users is 3, and the ID for the new user should be 4

        assertEquals("New Task", savedTask.getTitle());
        assertEquals("pending", savedTask.getStatus());
        assertEquals(1, savedTask.getUserId());

        Task retrievedTask = dataStore.getTaskById(savedTask.getId());
        assertNotNull(retrievedTask);
        assertEquals(savedTask.getId(), retrievedTask.getId());
    }

    @Test
    void testUpdateTaskSuccess() {
        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTitle("Updated Task");
        updatedTask.setStatus("completed");
        updatedTask.setUserId(2);

        Task result = dataStore.updateTask(updatedTask);

        assertEquals(1, result.getId());
        assertEquals("Updated Task", result.getTitle());
        assertEquals("completed", result.getStatus());
        assertEquals(2, result.getUserId());

        Task retrievedTask = dataStore.getTaskById(1);
        assertEquals("Updated Task", retrievedTask.getTitle());
        assertEquals("completed", retrievedTask.getStatus());
        assertEquals(2, retrievedTask.getUserId());
    }

    @Test
    void testUpdateTaskWithNonExistentId() {
        Task updatedTask = new Task();
        updatedTask.setId(999);
        updatedTask.setTitle("Non-existent Task");
        updatedTask.setStatus("pending");
        updatedTask.setUserId(1);

        Task result = dataStore.updateTask(updatedTask);
        assertNull(result);

        Task retrievedTask = dataStore.getTaskById(999);
        assertNull(retrievedTask);

    }

    @Test
    void testUpdateTaskPartialUpdate() {
        Task originalTask = dataStore.getTaskById(1);

        Task updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTitle("Partially Updated Task");
        updatedTask.setStatus(originalTask.getStatus());
        updatedTask.setUserId(originalTask.getUserId());

        Task result = dataStore.updateTask(updatedTask);

        assertEquals("Partially Updated Task", result.getTitle());
        assertEquals(originalTask.getStatus(), result.getStatus());
        assertEquals(originalTask.getUserId(), result.getUserId());
    }

    @Test
    void testSaveUserIdIncrement() {
        User user1 = new User();
        user1.setName("User 1");
        User savedUser1 = dataStore.saveUser(user1);

        User user2 = new User();
        user2.setName("User 2");
        User savedUser2 = dataStore.saveUser(user2);

        assertEquals(savedUser1.getId() + 1, savedUser2.getId());
    }

    @Test
    void testSaveTaskIdIncrement() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        Task savedTask1 = dataStore.saveTask(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        Task savedTask2 = dataStore.saveTask(task2);

        assertEquals(savedTask1.getId() + 1, savedTask2.getId());
    }
}

