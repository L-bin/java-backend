package com.developer.test.controller;

import com.developer.test.dto.TaskRequest;
import com.developer.test.dto.TasksResponse;
import com.developer.test.dto.UpdateTaskRequest;
import com.developer.test.model.Task;
import com.developer.test.model.User;
import com.developer.test.service.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private static final List<String> VALID_STATUSES = Arrays.asList("pending", "in-progress", "completed");

    private final DataStore dataStore;
    
    public TaskController(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    @GetMapping
    public ResponseEntity<TasksResponse> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String userId) {
        List<com.developer.test.model.Task> tasks = dataStore.getTasks(status, userId);
        TasksResponse response = new TasksResponse(tasks, tasks.size());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest taskRequest){
        User user = dataStore.getUserById(taskRequest.getUserId());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setStatus(taskRequest.getStatus());
        task.setUserId(taskRequest.getUserId());
        task = dataStore.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id,@RequestBody UpdateTaskRequest taskRequest){
        Task task = dataStore.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        if (!StringUtils.isEmpty(taskRequest.getTitle())){
            task.setTitle(taskRequest.getTitle());
        }
        if (!StringUtils.isEmpty(taskRequest.getStatus())){
            if (!VALID_STATUSES.contains(taskRequest.getStatus())){
                return ResponseEntity.badRequest().build();
            }
            task.setStatus(taskRequest.getStatus());
        }
        if (taskRequest.getUserId()!=null){
            User user = dataStore.getUserById(taskRequest.getUserId());
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            task.setUserId(taskRequest.getUserId());
        }
        task = dataStore.updateTask(task);
        return  ResponseEntity.ok(task);
    }

}
