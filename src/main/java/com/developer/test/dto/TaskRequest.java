package com.developer.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TaskRequest {

    @NotBlank(message = "Title cannot be blank.")
    @JsonProperty("title")
    private String title;

    @Pattern(regexp = "pending|in-progress|completed",
            message = "Status must one of: \"pending\", \"in-progress\", \"completed\"")
    @JsonProperty("status")
    private String status;

    @NotNull(message = "UserId cannot be null.")
    @JsonProperty("userId")
    private Integer userId;

    public TaskRequest() {
    }

    public TaskRequest(String title, String status, Integer userId) {
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
