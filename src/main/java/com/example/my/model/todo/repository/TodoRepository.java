package com.example.my.model.todo.repository;

import com.example.my.model.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findByUserEntity_IdAndDeleteDateIsNull(Long userId);

    Optional<TodoEntity> findByIdAndDeleteDateIsNull(Long id);

}
