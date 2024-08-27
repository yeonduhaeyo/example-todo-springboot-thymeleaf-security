package com.example.my.domain.todo.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReqTodoPutDTOApiV1 {

    @Valid
    @NotNull(message = "내용을 양식에 맞게 입력해주세요.")
    private Todo todo;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Todo {
        @NotNull(message = "완료 여부를 입력해주세요.")
        @Pattern(regexp = "^[NY]$", message = "doneYn은 N 또는 Y로 입력해주세요.")
        private String doneYn;
    }

}
