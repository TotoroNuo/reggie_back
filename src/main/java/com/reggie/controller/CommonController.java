package com.reggie.controller;

import com.reggie.common.R;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author TotoroNuo
 * @date 2022/8/30 19:18
 * @function :
 */

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     *上传部分
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String org = file.getOriginalFilename();  //原始文件名
        String suffix = org.substring(org.lastIndexOf("."));  // .以后的后缀留下
        String fileName = UUID.randomUUID().toString() + suffix;
        //查看当前目录是否存在
        File dir = new File(basePath);
        if(!dir.exists()){
            //如果不存在就创建他
            dir.mkdir();
        }
        try {
            file.transferTo(new File(basePath+ fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 下载部分
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name , HttpServletResponse response){
        try {
            //输入流，从本地读取图片
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            //输出流，返回给浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            //IO流copy
            IOUtils.copy(fileInputStream,outputStream);
            outputStream.flush();
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
