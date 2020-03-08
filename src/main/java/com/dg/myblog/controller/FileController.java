package com.dg.myblog.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.dg.myblog.global.utils.ContentTypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @description: 图片和静态资源
 * @author: lij
 * @create: 2020-03-05 00:55
 */
@RequestMapping("/file")
@Controller
@Slf4j
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JSONObject upload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {
        JSONObject jsonObject=new JSONObject();
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String rootPath = uploadDir + File.separator + "my-blog";
            log.info("editormd上传图片：{}", rootPath);

            File filePath = new File(rootPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            // 最终文件名
            String finalFileName = System.currentTimeMillis() + "." + FileUtil.extName(attach.getOriginalFilename());
            String finalFilePath = rootPath + File.separator + finalFileName;
            File realFile = new File(finalFilePath);
            attach.transferTo(realFile);
            //访问图片路径
            String contextPath = request.getRequestURL().toString();
            contextPath = contextPath.replace(request.getRequestURI(), "");
            String picPath = contextPath + "/file/" + finalFileName;

            jsonObject.put("success", 1);
            jsonObject.put("message", "上传成功");
            jsonObject.put("url", picPath);

        } catch (IOException e) {
            jsonObject.put("success", 0);
        }
        return jsonObject;
    }


    @GetMapping(value = "/{fileName}")
    @ResponseBody
    public byte[] getImage(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        if(StrUtil.isBlank(fileName)){
            return null;
        }
        String rootPath = uploadDir + File.separator + "my-blog";
        String finalFilePath = rootPath + File.separator + fileName;
        File pic = new File(finalFilePath);
        if(!pic.exists()){
            log.info("图片{}不存在", finalFilePath);
            return null;
        }
        FileInputStream inputStream = new FileInputStream(pic);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        response.setContentType(ContentTypeUtils.getContentType(FileUtil.extName(fileName)));
        return bytes;
    }
}
