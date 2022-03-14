package com.paper.admin.controller;

import com.paper.admin.dto.CommentDTO;
import com.paper.admin.dto.QuestionDTO;
import com.paper.admin.enums.CommentTypeEnum;
import com.paper.admin.service.CommentService;
import com.paper.admin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    public QuestionService questionService;

    @Autowired
    public CommentService commentService;
    //通过id获得questionDTO
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO questionDTO = questionService.getDTOById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加
//        questionService.incView(id);
        questionService.upViewCount(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
