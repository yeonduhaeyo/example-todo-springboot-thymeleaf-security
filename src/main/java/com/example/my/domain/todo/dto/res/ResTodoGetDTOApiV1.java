package com.example.my.domain.todo.dto.res;

import com.example.my.model.todo.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResTodoGetDTOApiV1 {

    private List<Todo> todoList;
    private List<Todo> doneList;

    public static ResTodoGetDTOApiV1 of(List<TodoEntity> todoEntityList) {
        return ResTodoGetDTOApiV1.builder()
                .todoList(
                        todoEntityList.stream()
                                .filter(todoEntity -> todoEntity.getDoneYn().equals("N"))
                                .map(todoEntity -> Todo.fromEntity(todoEntity))
                                .toList())
                .doneList(
                        todoEntityList.stream()
                                .filter(todoEntity -> todoEntity.getDoneYn().equals("Y"))
                                .map(todoEntity -> Todo.fromEntity(todoEntity))
                                .toList())
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
