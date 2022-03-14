package com.paper.admin.advice;

import com.alibaba.fastjson.JSON;
import com.paper.admin.dto.ResultDTO;
import com.paper.admin.exception.CustomizeErrorCode;
import com.paper.admin.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//处理抛出的异常
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Object handle(HttpServletRequest request,
                         Throwable e, Model model,
                         HttpServletResponse response){

        String contentType = request.getContentType();
        //判断请求格式
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO;
            if (e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);

            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);

            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;

        }else {
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());

            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());

            }
        }

        return new ModelAndView("error");

    }

}