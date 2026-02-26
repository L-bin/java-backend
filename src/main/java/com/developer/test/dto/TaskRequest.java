package com.developer.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TaskRequest {

    @NotNull
    @JsonProperty("title")
    private String title;

    @Pattern(regexp = "pending|in-progress|completed",
            message = "status must one of: \"pending\", \"in-progress\", \"completed\"")
    @JsonProperty("status")
    private String status;

    @NotNull
    @JsonProperty("userId")
    private int userId;

    public TaskRequest() {
    }

    public TaskRequest(String title, String status, int userId) {
        this.title = title;
        this.status = status;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
