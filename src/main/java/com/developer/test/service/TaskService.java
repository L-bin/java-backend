package com.developer.test.service;

import com.developer.test.model.Task;
import com.developer.test.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @CachePut(value = "tasks", key = "#task.id")
    public Task saveOrUpdateTask(Task task) {
        return taskRepository.save(task);
    }

}
