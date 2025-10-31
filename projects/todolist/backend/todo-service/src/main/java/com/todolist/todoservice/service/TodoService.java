package com.todolist.todoservice.service;

import com.todolist.todoservice.entity.Todo;
import com.todolist.todoservice.dto.TodoRequest;
import com.todolist.todoservice.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * 获取用户的所有任务
     */
    public List<Todo> getTodosByUserId(String userId) {
        return todoRepository.findByUserId(userId);
    }

    /**
     * 根据完成状态获取任务
     */
    public List<Todo> getTodosByUserIdAndCompleted(String userId, Boolean completed) {
        if (completed == null) {
            return getTodosByUserId(userId);
        }
        return todoRepository.findByUserIdAndCompleted(userId, completed);
    }

    /**
     * 根据ID获取任务
     */
    public Optional<Todo> getTodoById(String id, String userId) {
        return todoRepository.findByIdAndUserId(id, userId);
    }

    /**
     * 创建新任务
     */
    public Todo createTodo(TodoRequest request, String userId) {
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID().toString());
        todo.setUserId(userId);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setPriority(request.getPriority());
        todo.setDueDate(request.getDueDate());
        todo.setCompleted(false);

        return todoRepository.save(todo);
    }

    /**
     * 更新任务
     */
    public Optional<Todo> updateTodo(String id, String userId, TodoRequest request) {
        Optional<Todo> todoOpt = todoRepository.findByIdAndUserId(id, userId);

        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setTitle(request.getTitle());
            todo.setDescription(request.getDescription());
            todo.setPriority(request.getPriority());
            todo.setDueDate(request.getDueDate());

            return Optional.of(todoRepository.save(todo));
        }

        return Optional.empty();
    }

    /**
     * 切换任务完成状态
     */
    public Optional<Todo> toggleTodo(String id, String userId) {
        Optional<Todo> todoOpt = todoRepository.findByIdAndUserId(id, userId);

        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setCompleted(!todo.getCompleted());
            return Optional.of(todoRepository.save(todo));
        }

        return Optional.empty();
    }

    /**
     * 删除任务
     */
    @Transactional
    public boolean deleteTodo(String id, String userId) {
        Optional<Todo> todoOpt = todoRepository.findByIdAndUserId(id, userId);

        if (todoOpt.isPresent()) {
            todoRepository.deleteByIdAndUserId(id, userId);
            return true;
        }

        return false;
    }
}
