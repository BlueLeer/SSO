package com.lee.sso.controller;

import com.lee.sso.domain.ClientInfo;
import com.lee.sso.util.MockDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with : IntelliJ IDEA
 * User : KingIsHappy
 * Date : 2018/10/7
 * Time : 23:08
 * Description :
 */

@Controller
public class SSOController {
    /**
     * 校验客户端的登录状态
     *
     * @param redirectUrl
     * @param session
     * @param model
     * @return
     */
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

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param redirectUrl
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, String redirectUrl, HttpSession session, Model model) {
        System.out.println("redirectUrl:" + redirectUrl);
        if ("lee".equals(username) && "123".equals(password)) {
            // 1. 创建令牌
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            // 2. 创建全局回话,将令牌信息放入全局会话中
            session.setAttribute("token", token);
            // 3.需要将令牌放入到数据库中
            MockDatabase.TOKEN_CENTER.add(token);
            // 4.重向到以前客户端请求的地址
            model.addAttribute("token", token);
            return "redirect:" + redirectUrl;
        }

        // 如果账号密码有误,重新回到登录页面,同时应该讲客户端的地址放进去
        model.addAttribute("redirectUrl", redirectUrl);
        model.addAttribute("errorMsg", "账号或者密码有误!");
        return "login";
    }

    /**
     * 校验token(令牌)
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/verify")
    public String verify(ClientInfo clientInfo, String token) {
        // 判断token是否合法
        // :合法
        if (MockDatabase.TOKEN_CENTER.contains(token)) {

            // 记录登录的客户端的信息
            List<ClientInfo> clientInfos = MockDatabase.CLIENT_INFO.get(token);
            if (clientInfos == null) {
                clientInfos = new ArrayList<>();
                MockDatabase.CLIENT_INFO.put(token, clientInfos);
            }

            // 将当前登录的客户端的信息存进数据库中(这里是模拟数据库)
            clientInfos.add(clientInfo);
            return "true";
        }

        return "false";
    }

    /**
     * 登出系统
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 让session过期
        session.invalidate();
        return "logOut";
    }
}
