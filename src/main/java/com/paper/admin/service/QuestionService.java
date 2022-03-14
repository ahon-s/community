package com.paper.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paper.admin.dto.PaginationDTO;
import com.paper.admin.dto.QuestionDTO;
import com.paper.admin.model.Question;

import java.util.List;


public interface QuestionService extends IService<Question> {

    PaginationDTO QuestionDTOList(Long id, Integer page, Integer size);
    PaginationDTO QuestionDTOList(Integer page, Integer size);
    //通过id获得questionDTO
    QuestionDTO getDTOById(Long id);

    Question getById(Long id);

    void createOrUpdate(Question question);
    //更新阅读数
    void upViewCount(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
