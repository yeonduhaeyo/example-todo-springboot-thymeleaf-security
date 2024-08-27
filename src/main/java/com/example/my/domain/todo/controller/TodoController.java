package com.example.my.domain.todo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.my.config.security.auth.CustomUserDetails;
import com.example.my.domain.todo.dto.res.ResTodoDTO;
import com.example.my.domain.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping({"", "/"})
    public ModelAndView todoTablePage(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView modelAndView = new ModelAndView();

//        if (session.getAttribute("loginUserDTO") == null) {
//            modelAndView.setViewName("redirect:/auth/login");
//            return modelAndView;
//        }

//        LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute("loginUserDTO");

        ResTodoDTO dto = todoService.getTodoTableData(customUserDetails);
        modelAndView.addObject("dto", dto);
        modelAndView.setViewName("todo/table");
        return modelAndView;
    }

}
