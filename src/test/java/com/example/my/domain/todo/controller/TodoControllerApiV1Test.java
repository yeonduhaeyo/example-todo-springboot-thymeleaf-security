package com.example.my.domain.todo.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.my.common.constants.Constants;
import com.example.my.domain.todo.dto.req.ReqTodoPostDTOApiV1;
import com.example.my.domain.todo.dto.req.ReqTodoPutDTOApiV1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class TodoControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("test");

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Test
    public void testGetSuccess() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/todo")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("TODO 리스트 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("TODO v1")
                                        .summary("TODO 리스트 조회")
                                        .description("""
                                                ## TODO 리스트 조회 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testGetWithIdSuccess() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/todo/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("TODO 개별 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("TODO v1")
                                        .summary("TODO 개별 조회")
                                        .description("""
                                                ## TODO 개별 조회 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testPostSuccess() throws Exception {
        ReqTodoPostDTOApiV1 reqDto = ReqTodoPostDTOApiV1.builder()
                .todo(
                        ReqTodoPostDTOApiV1.Todo.builder()
                                .content("자바 공부하기")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/todo")
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("TODO 추가 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("TODO v1")
                                        .summary("TODO 추가")
                                        .description("""
                                                ## TODO 추가 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .requestFields(
                                                fieldWithPath("todo.content").type(JsonFieldType.STRING).description("할 일")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testPutSuccess() throws Exception {
        ReqTodoPutDTOApiV1 reqDto = ReqTodoPutDTOApiV1.builder()
                .todo(
                        ReqTodoPutDTOApiV1.Todo.builder()
                                .doneYn("N")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.put("/v1/todo/{id}", 1L)
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("TODO 수정 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("TODO v1")
                                        .summary("TODO 수정")
                                        .description("""
                                                ## TODO 수정 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .requestFields(
                                                fieldWithPath("todo.doneYn").type(JsonFieldType.STRING).description("완료 여부")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/v1/todo/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("TODO 삭제 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("TODO v1")
                                        .summary("TODO 삭제")
                                        .description("""
                                                ## TODO 삭제 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .build()
                                )
                        )
                );
    }

}
