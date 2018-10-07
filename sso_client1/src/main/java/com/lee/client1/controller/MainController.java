package com.lee.client1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with : IntelliJ IDEA
 * User : KingIsHappy
 * Date : 2018/10/7
 * Time : 22:02
 * Description :
 */
@Controller
public class MainController {
    /**
     * 访问主页的方法
     *
     * @return
     */
    @RequestMapping("/main")
    public String main(Model model) {
        // 还需要添加登出的地址
        String serverLogOutUrl = "";
        model.addAttribute("serverLogOutUrl", serverLogOutUrl);
        return "main";
    }
}
