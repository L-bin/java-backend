package com.developer.test.controller;

import com.developer.test.dto.UserRequest;
import com.developer.test.dto.UsersResponse;
import com.developer.test.model.User;
import com.developer.test.service.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Validated
public class UserController {
    
    private final DataStore dataStore;
    
    public UserController(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    @GetMapping
    public ResponseEntity<UsersResponse> getUsers() {
        List<User> users = dataStore.getUsers();
        UsersResponse response = new UsersResponse(users, users.size());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = dataStore.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequest userRequest){
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        user = dataStore.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
