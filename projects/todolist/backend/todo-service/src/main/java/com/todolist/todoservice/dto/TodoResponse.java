package com.todolist.todoservice.dto;

import com.todolist.todoservice.entity.Todo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoResponse {

    private String id;
    private String userId;
    private String title;
    private String description;
    private Boolean completed;
    private Todo.Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TodoResponse from(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setUserId(todo.getUserId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.getCompleted());
        response.setPriority(todo.getPriority());
        response.setDueDate(todo.getDueDate());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        return response;
    }
}
