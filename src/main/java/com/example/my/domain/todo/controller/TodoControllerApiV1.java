package com.example.my.domain.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my.config.security.auth.CustomUserDetails;
import com.example.my.domain.todo.dto.req.ReqTodoPostDTOApiV1;
import com.example.my.domain.todo.dto.req.ReqTodoPutDTOApiV1;
import com.example.my.domain.todo.service.TodoServiceApiV1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoControllerApiV1 {

    private final TodoServiceApiV1 todoServiceApiV1;

    @GetMapping
    public ResponseEntity<?> get(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.get(customUserDetails);
    }

    @PostMapping
    public ResponseEntity<?> post(
            @Valid @RequestBody ReqTodoPostDTOApiV1 dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.post(dto, customUserDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(
            @PathVariable Long id,
            @Valid @RequestBody ReqTodoPutDTOApiV1 dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.put(id, dto, customUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.delete(id, customUserDetails);
    }

}
