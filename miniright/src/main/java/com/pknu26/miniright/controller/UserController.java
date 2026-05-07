package com.pknu26.miniright.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pknu26.miniright.dto.LoginUser;
import com.pknu26.miniright.dto.User;
import com.pknu26.miniright.service.UserService;
import com.pknu26.miniright.validation.UserJoinForm;
import com.pknu26.miniright.validation.UserLoginForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 화면
    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("userJoinForm", new UserJoinForm());
        return "/user/join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("userJoinForm") UserJoinForm form,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/user/join";
        }

        try {
            this.userService.join(form);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("error", e.getMessage());
            return "/user/join";
        }

        return "redirect:/user/login";
    }

    // 로그인 화면
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginForm", new UserLoginForm());
        return "/user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLoginForm") UserLoginForm form,
                        BindingResult bindingResult,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "/user/login";
        }

        User user = this.userService.login(form);

        if (user == null) {
            bindingResult.reject("error", "아이디 또는 패스워드가 올바르지 않습니다.");
            return "/user/login";
        }

        LoginUser loginUser = new LoginUser(
                user.getUserId(),
                user.getLoginId(),
                user.getName(),
                user.getRole()
        );

        session.setAttribute("loginUser", loginUser);

        return "redirect:/bboard/list";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}