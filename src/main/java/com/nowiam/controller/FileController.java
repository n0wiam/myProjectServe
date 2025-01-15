package com.nowiam.controller;

import com.nowiam.model.dto.FileDto;
import com.nowiam.model.vo.Result;
import com.nowiam.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/upload")
    public void upload(@RequestParam("fileId") String fileId,
                       @RequestParam("multipartFile") MultipartFile multipartFile,
                       @RequestParam("fileName") String fileName,
                       @RequestParam("chunkIndex") int chunkIndex,
                       @RequestParam("totalChunks") int totalChunks) throws Exception {
        FileDto fileDto=new FileDto(fileId,multipartFile,fileName,chunkIndex,totalChunks);
        fileService.upload(fileDto);
    }

    @PostMapping("/fid")
    public Result getFid(@RequestParam("fileName") String fileName,
                         @RequestParam("totalChunks") int totalChunks,
                         @RequestParam("fileSize") int fileSize){
        return fileService.getFid(fileName,totalChunks,fileSize);
    }

    @PostMapping("/show")
    public void show(@RequestBody String fid){
        System.out.println(fid);
        return ;
    }
    @PostMapping("/merge")
    public void merge(@RequestBody String fid)throws Exception{
        fileService.merge(fid);
//        System.out.println(fid);
    }
}
