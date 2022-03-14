package com.paper.admin.controller;

import com.paper.admin.dto.PaginationDTO;
import com.paper.admin.model.User;
import com.paper.admin.service.NotificationService;
import com.paper.admin.service.QuestionService;
import com.paper.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public  String profile(@PathVariable(name = "action") String action, Model model,
                           HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1") Integer page,
                           @RequestParam(value = "size",defaultValue = "5") Integer size){


        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "index";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的发布");
            PaginationDTO paginationDTO = questionService.QuestionDTOList(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("pagination",paginationDTO);
        }


        return "profile";
    }
}
