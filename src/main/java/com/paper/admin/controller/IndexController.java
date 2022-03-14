package com.paper.admin.controller;


import com.paper.admin.dto.PaginationDTO;
import com.paper.admin.dto.QuestionDTO;
import com.paper.admin.model.Question;
import com.paper.admin.model.User;
import com.paper.admin.service.QuestionService;
import com.paper.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String Index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size){


        PaginationDTO pagination = questionService.QuestionDTOList(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
