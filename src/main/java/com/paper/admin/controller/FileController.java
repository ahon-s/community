package com.paper.admin.controller;


import com.paper.admin.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;


@Controller
@Slf4j
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String path = uploadImage(file,file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(path);
            return fileDTO;
        } catch (Exception e) {
            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }
    }
    //图片上传设置
    public String uploadImage(MultipartFile file, String fileName) throws IOException {
        String resourcePath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        String path =  "/images/TempImages/" + UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
        String realPath = resourcePath + path;
        File dest = new File(realPath);
        File test = new File("I:\\java_idea\\admin\\src\\main\\resources\\static\\images\\TempImages\\"+fileName);
        InputStream inputStream = file.getInputStream();
        OutputStream out = new FileOutputStream(test);
        int count = 0;
        byte[] buf = new byte[8 * 1024];
        while( (count=inputStream.read(buf)) != -1 ) {
            out.write(buf, 0, count);
        }
        out.close();
        // 判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        // 保存文件
        file.transferTo(dest);
        return path;
    }
}


