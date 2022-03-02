package com.paper.admin.controller;

import com.paper.admin.bean.User;
import com.paper.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Slf4j
public class loginController {

    @Autowired
    UserService userService;
//    登录get和post控制器
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    //登录
    @PostMapping("/login")
    public String login(User user ,Model model){
        User user1 = userService.GetUserByName(user.username);
        log.info(String.valueOf(user1));
//        登录判断
        if (user1 != null){
            model.addAttribute("msg","登录成功");
            return "index";
        }else {
            model.addAttribute("msg","账户或密码错误");
            return "index";
        }

    }


    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String register( User user,Model model){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        user.rg_time = formatter.format(date);
        boolean save = userService.save(user);
        if (save){
           model.addAttribute("msg","注册成功");
        }else {
            model.addAttribute("msg","注册失败");
        }
        return "index";
    }
}
