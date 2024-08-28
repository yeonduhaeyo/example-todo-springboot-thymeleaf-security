package com.example.my.domain.auth.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.my.common.constants.Constants;
import com.example.my.domain.auth.dto.req.ReqJoinDTOApiV1;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
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
public class AuthControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(
                        SecurityMockMvcRequestBuilders.formLogin("/v1/auth/login")
                                .user("test")
                                .password("123")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testJoinSuccess() throws Exception {
        ReqJoinDTOApiV1 reqDto = ReqJoinDTOApiV1.builder()
                .user(
                        ReqJoinDTOApiV1.User.builder()
                                .username("temp")
                                .password("123")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/auth/join")
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("AUTH 회원가입 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("AUTH v1")
                                        .summary("AUTH 회원가입")
                                        .description("""
                                                ## AUTH 회원가입 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                username과 password를 받아 회원가입을 진행합니다.
                                                """)
                                        .requestFields(
                                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("사용자 아이디"),
                                                fieldWithPath("user.password").type(JsonFieldType.STRING).description("사용자 비밀번호")
                                        )
                                        .build()
                                )
                        )
                );
    }

}
