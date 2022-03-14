package com.paper.admin.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paper.admin.dto.PaginationDTO;
import com.paper.admin.dto.QuestionDTO;
import com.paper.admin.exception.CustomizeErrorCode;
import com.paper.admin.exception.CustomizeException;
import com.paper.admin.mapper.QuestionMapper;
import com.paper.admin.model.Question;
import com.paper.admin.model.User;
import com.paper.admin.service.QuestionService;
import com.paper.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    UserService userService;

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public PaginationDTO QuestionDTOList(Integer page, Integer size) {

        Integer offset = size*(page-1);
//        List<Question> questions = this.list();

        List<Question> questions = questionMapper.PageList(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        //数据库逻辑判断 防止越界
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) page = 1;
        paginationDTO.setPagination(totalPage,page);
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();

        for (Question question : questions){
            User user = userService.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    @Override
    public PaginationDTO QuestionDTOList(Long userId, Integer page, Integer size) {
        Integer offset = size*(page-1);
//        List<Question> questions = this.list();

        List<Question> questions = questionMapper.ListByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        //数据库逻辑判断 防止越界
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) page = 1;
        paginationDTO.setPagination(totalPage,page);
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();

        for (Question question : questions){
            User user = userService.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    @Override
    public QuestionDTO getDTOById(Long id) {
        Question question = questionMapper.getById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userService.getById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
    //获得question的id
    @Override
    public Question getById(Long id) {
        return questionMapper.getById(id);
    }

    @Override
    //创建或更新question
    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            this.save(question);
        }else {
            //更新

            question.setGmtModified(System.currentTimeMillis());
            question.setTitle(question.getTitle());
            question.setDescription(question.getDescription());
            question.setTag(question.getTag());
            boolean updated = this.updateById(question);
            if (updated != true){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
    @Override
    public void upViewCount(Long id) {
        questionMapper.upViewCount(id);
    }

    //获得标签相关问题
    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;

    }


}
