package com.example.my.domain.todo.service;

import com.example.my.common.dto.ResDTO;
import com.example.my.common.exception.BadRequestException;
import com.example.my.config.security.auth.CustomUserDetails;
import com.example.my.domain.todo.dto.req.ReqTodoPostDTOApiV1;
import com.example.my.domain.todo.dto.req.ReqTodoPutDTOApiV1;
import com.example.my.domain.todo.dto.res.ResTodoGetDTOApiV1;
import com.example.my.domain.todo.dto.res.ResTodoGetWithIdDTOApiV1;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV1 {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public ResponseEntity<ResDTO<ResTodoGetDTOApiV1>> get(CustomUserDetails customUserDetails) {
        List<TodoEntity> todoEntityList = todoRepository
                .findByUserEntity_IdAndDeleteDateIsNull(customUserDetails.getUser().getId());
        return new ResponseEntity<>(
                ResDTO.<ResTodoGetDTOApiV1>builder()
                        .code(0)
                        .message("할 일 목록 조회에 성공하였습니다.")
                        .data(ResTodoGetDTOApiV1.of(todoEntityList))
                        .build(),
                HttpStatus.OK);
    }

    public ResponseEntity<ResDTO<ResTodoGetWithIdDTOApiV1>> getWithId(Long id, CustomUserDetails customUserDetails) {
        Optional<TodoEntity> todoEntityOptional = todoRepository.findByIdAndDeleteDateIsNull(id);
        if (todoEntityOptional.isEmpty()) {
            throw new BadRequestException("존재하지 않는 할 일입니다.");
        }
        TodoEntity todoEntity = todoEntityOptional.get();
        if (!todoEntity.getUserEntity().getId().equals(customUserDetails.getUser().getId())) {
            throw new BadRequestException("해당 할 일을 조회할 권한이 없습니다.");
        }
        return new ResponseEntity<>(
                ResDTO.<ResTodoGetWithIdDTOApiV1>builder()
                        .code(0)
                        .message("할 일 조회에 성공하였습니다.")
                        .data(ResTodoGetWithIdDTOApiV1.of(todoEntity))
                        .build(),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResDTO<Object>> post(ReqTodoPostDTOApiV1 dto, CustomUserDetails customUserDetails) {
        if (dto == null || dto.getTodo() == null || dto.getTodo().getContent() == null
                || dto.getTodo().getContent().isBlank()) {
            throw new BadRequestException("할 일을 입력해주세요.");
        }
        Optional<UserEntity> userEntityOptional = userRepository
                .findByIdAndDeleteDateIsNull(customUserDetails.getUser().getId());
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("존재하지 않는 사용자입니다.");
        }
        TodoEntity todoEntity = TodoEntity.builder()
                .userEntity(userEntityOptional.get())
                .content(dto.getTodo().getContent())
                .doneYn("N")
                .createDate(LocalDateTime.now())
                .build();

        todoRepository.save(todoEntity);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 추가에 성공하였습니다.")
                        .build(),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResDTO<Object>> put(Long id, ReqTodoPutDTOApiV1 dto, CustomUserDetails customUserDetails) {
        Optional<TodoEntity> todoEntityOptional = todoRepository.findByIdAndDeleteDateIsNull(id);
        if (todoEntityOptional.isEmpty()) {
            throw new BadRequestException("존재하지 않는 할 일입니다.");
        }
        TodoEntity todoEntity = todoEntityOptional.get();
        if (!todoEntity.getUserEntity().getId().equals(customUserDetails.getUser().getId())) {
            throw new BadRequestException("권한이 없습니다.");
        }
        todoEntity.setDoneYn(dto.getTodo().getDoneYn());
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResDTO<Object>> delete(Long id, CustomUserDetails customUserDetails) {
        Optional<TodoEntity> todoEntityOptional = todoRepository.findByIdAndDeleteDateIsNull(id);
        if (todoEntityOptional.isEmpty()) {
            throw new BadRequestException("존재하지 않는 할 일입니다.");
        }
        TodoEntity todoEntity = todoEntityOptional.get();
        if (!todoEntity.getUserEntity().getId().equals(customUserDetails.getUser().getId())) {
            throw new BadRequestException("권한이 없습니다.");
        }
        todoEntity.setDeleteDate(LocalDateTime.now());
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("할 일 삭제에 성공하였습니다.")
                        .build(),
                HttpStatus.OK);
    }

}
