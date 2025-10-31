package com.todolist.todoservice.dto;

import com.todolist.todoservice.entity.Todo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoRequest {

    @NotBlank(message = "标题不能为空")
    @Size(min = 1, max = 100, message = "标题长度必须在1-100之间")
    private String title;

    @Size(max = 500, message = "描述长度不能超过500")
    private String description;

    private Todo.Priority priority = Todo.Priority.MEDIUM;

    private LocalDateTime dueDate;
}
