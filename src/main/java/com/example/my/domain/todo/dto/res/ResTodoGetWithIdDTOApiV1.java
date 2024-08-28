package com.example.my.domain.todo.dto.res;

import com.example.my.model.todo.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResTodoGetWithIdDTOApiV1 {

    private Todo todo;

    public static ResTodoGetWithIdDTOApiV1 of(TodoEntity todoEntity) {
        return ResTodoGetWithIdDTOApiV1.builder()
                .todo(Todo.fromEntity(todoEntity))
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Todo {

        private Long id;
        private String content;
        private String doneYn;

        public static Todo fromEntity(TodoEntity todoEntity) {
            return Todo.builder()
                    .id(todoEntity.getId())
                    .content(todoEntity.getContent())
                    .doneYn(todoEntity.getDoneYn())
                    .build();
        }

    }

}
