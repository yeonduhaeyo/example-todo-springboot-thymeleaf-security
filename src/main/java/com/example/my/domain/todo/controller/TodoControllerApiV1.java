package com.example.my.domain.todo.controller;

import com.example.my.common.dto.ResDTO;
import com.example.my.config.security.auth.CustomUserDetails;
import com.example.my.domain.todo.dto.req.ReqTodoPostDTOApiV1;
import com.example.my.domain.todo.dto.req.ReqTodoPutDTOApiV1;
import com.example.my.domain.todo.dto.res.ResTodoGetDTOApiV1;
import com.example.my.domain.todo.dto.res.ResTodoGetWithIdDTOApiV1;
import com.example.my.domain.todo.service.TodoServiceApiV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo")
public class TodoControllerApiV1 {

    private final TodoServiceApiV1 todoServiceApiV1;

    @GetMapping
    public ResponseEntity<ResDTO<ResTodoGetDTOApiV1>> get(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.get(customUserDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResTodoGetWithIdDTOApiV1>> getWithId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long id
    ) {
        return todoServiceApiV1.getWithId(id, customUserDetails);
    }

    @PostMapping
    public ResponseEntity<ResDTO<Object>> post(
            @Valid @RequestBody ReqTodoPostDTOApiV1 dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.post(dto, customUserDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> put(
            @PathVariable Long id,
            @Valid @RequestBody ReqTodoPutDTOApiV1 dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.put(id, dto, customUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return todoServiceApiV1.delete(id, customUserDetails);
    }

}
