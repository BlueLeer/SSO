package com.lee.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created with : IntelliJ IDEA
 * User : KingIsHappy
 * Date : 2018/10/7
 * Time : 23:08
 * Description :
 */

@Controller
public class SSOController {

    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model) {
        // 检查是否有全局会话
        // :没有全局会话
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            model.addAttribute("redirectUrl", redirectUrl);
            return "login";
        }

        // :有全局会话
        model.addAttribute("token", token);
        return "redirect:" + redirectUrl;
    }
}
