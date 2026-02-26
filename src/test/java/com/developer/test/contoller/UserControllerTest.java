
package com.developer.test.contoller;

import com.developer.test.controller.UserController;
import com.developer.test.dto.UserRequest;
import com.developer.test.model.User;
import com.developer.test.service.DataStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataStore dataStore;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser_ValidRequest_ReturnsCreated() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setRole("admin");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("John Doe");
        savedUser.setEmail("john.doe@example.com");
        savedUser.setRole("admin");

        when(dataStore.saveUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.role").value("admin"));
    }

    @Test
    void createUser_NullName_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName(null);
        request.setEmail("john.doe@example.com");
        request.setRole("admin");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_EmptyName_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("");
        request.setEmail("john.doe@example.com");
        request.setRole("admin");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_BlankName_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("   ");
        request.setEmail("john.doe@example.com");
        request.setRole("admin");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_NullEmail_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail(null);
        request.setRole("admin");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_EmptyEmail_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("");
        request.setRole("admin");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_InvalidEmailFormat_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("invalid-email");
        request.setRole("admin");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_NullRole_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setRole(null);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_EmptyRole_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setRole("");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_AllFieldsNull_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName(null);
        request.setEmail(null);
        request.setRole(null);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_AllFieldsEmpty_ReturnsBadRequest() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("");
        request.setEmail("");
        request.setRole("");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_DataStoreException_ReturnsInternalServerError() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setRole("admin");

        when(dataStore.saveUser(any(User.class))).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createUser_LongName_ReturnsCreated() throws Exception {
        String longName = "A".repeat(1000);
        UserRequest request = new UserRequest();
        request.setName(longName);
        request.setEmail("john.doe@example.com");
        request.setRole("admin");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName(longName);
        savedUser.setEmail("john.doe@example.com");
        savedUser.setRole("admin");

        when(dataStore.saveUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(longName));
    }

    @Test
    void createUser_SpecialCharacters_ReturnsCreated() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("John O'Doe");
        request.setEmail("john.o'doe+test@example.com");
        request.setRole("admin/user");

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setName("John O'Doe");
        savedUser.setEmail("john.o'doe+test@example.com");
        savedUser.setRole("admin/user");

        when(dataStore.saveUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John O'Doe"))
                .andExpect(jsonPath("$.email").value("john.o'doe+test@example.com"))
                .andExpect(jsonPath("$.role").value("admin/user"));
    }
}
