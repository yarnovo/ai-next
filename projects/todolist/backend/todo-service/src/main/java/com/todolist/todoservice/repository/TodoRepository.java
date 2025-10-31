package com.todolist.todoservice.repository;

import com.todolist.todoservice.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {

    /**
     * 根据用户ID查找所有任务
     */
    List<Todo> findByUserId(String userId);

    /**
     * 根据用户ID和完成状态查找任务
     */
    List<Todo> findByUserIdAndCompleted(String userId, Boolean completed);

    /**
     * 根据ID和用户ID查找任务
     */
    Optional<Todo> findByIdAndUserId(String id, String userId);

    /**
     * 根据ID和用户ID删除任务
     */
    void deleteByIdAndUserId(String id, String userId);
}
