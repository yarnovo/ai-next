package com.todolist.todoservice.controller;

import com.todolist.todoservice.client.UserServiceClient;
import com.todolist.todoservice.dto.TodoRequest;
import com.todolist.todoservice.dto.TodoResponse;
import com.todolist.todoservice.dto.UserInfo;
import com.todolist.todoservice.entity.Todo;
import com.todolist.todoservice.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;
    private final UserServiceClient userServiceClient;

    public TodoController(TodoService todoService, UserServiceClient userServiceClient) {
        this.todoService = todoService;
        this.userServiceClient = userServiceClient;
    }

    /**
     * 从请求头中获取并验证用户身份
     */
    private UserInfo authenticate(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid authorization header");
        }

        String token = authorization.substring(7);
        UserInfo userInfo = userServiceClient.verifyToken(token);

        if (!userInfo.getValid()) {
            throw new RuntimeException("Invalid token");
        }

        return userInfo;
    }

    /**
     * 获取任务列表
     */
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodos(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) Boolean completed
    ) {
        UserInfo userInfo = authenticate(authorization);

        List<Todo> todos = todoService.getTodosByUserIdAndCompleted(userInfo.getUserId(), completed);
        List<TodoResponse> response = todos.stream()
                .map(TodoResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * 获取单个任务
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodo(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id
    ) {
        UserInfo userInfo = authenticate(authorization);

        return todoService.getTodoById(id, userInfo.getUserId())
                .map(todo -> ResponseEntity.ok(TodoResponse.from(todo)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 创建任务
     */
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody TodoRequest request
    ) {
        UserInfo userInfo = authenticate(authorization);

        Todo todo = todoService.createTodo(request, userInfo.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(TodoResponse.from(todo));
    }

    /**
     * 更新任务
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id,
            @Valid @RequestBody TodoRequest request
    ) {
        UserInfo userInfo = authenticate(authorization);

        return todoService.updateTodo(id, userInfo.getUserId(), request)
                .map(todo -> ResponseEntity.ok(TodoResponse.from(todo)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 切换任务完成状态
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoResponse> toggleTodo(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id
    ) {
        UserInfo userInfo = authenticate(authorization);

        return todoService.toggleTodo(id, userInfo.getUserId())
                .map(todo -> ResponseEntity.ok(TodoResponse.from(todo)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 删除任务
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id
    ) {
        UserInfo userInfo = authenticate(authorization);

        boolean deleted = todoService.deleteTodo(id, userInfo.getUserId());
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
