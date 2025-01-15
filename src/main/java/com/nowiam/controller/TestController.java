package com.nowiam.controller;

import com.nowiam.conf.MyConfig;
import com.nowiam.utils.MyFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    MyConfig myConfig;
    @Autowired
    MyFileWriter myFileWriter;
    @GetMapping
    public String test() throws Exception {
        myFileWriter.write(myConfig.file_path+"3122004787邱列圻数据库课设.rar",myConfig.file_path+"target.rar");
//        FileOutputStream fileOutputStream=new FileOutputStream(new File(myConfig.file_path+"tem.txt"),false);
//        fileOutputStream.close();
        return myConfig.file_path;
    }
}
