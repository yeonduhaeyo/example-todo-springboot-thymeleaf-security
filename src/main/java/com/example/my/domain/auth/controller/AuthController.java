package com.example.my.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @GetMapping("/auth/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @GetMapping("/auth/join")
    public ModelAndView joinPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/join");
        return modelAndView;
    }

    // 시큐리티 없을 때의 코드
//    @GetMapping("/auth/logout")
//    public ModelAndView logout(HttpSession session) {
//
//        session.invalidate();
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/auth/login");
//        return modelAndView;
//    }
}
