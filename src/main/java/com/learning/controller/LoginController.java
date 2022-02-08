package com.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("pwd") String pwd,
            Model model,
            HttpSession session) {

        if (!StringUtils.isEmpty(username) && "123456".equals(pwd)) {
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        }else {
            model.addAttribute("msg", "用户名或密码错误");
            return "index";
        }
    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
