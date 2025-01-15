package com.nowiam.service;

import com.nowiam.model.dto.FileDto;
import com.nowiam.model.vo.Result;

public interface FileService {
    public void upload(FileDto fileDto)throws Exception;

    Result getFid(String fileName,int totalChunks,int fileSize);

    void merge(String fid)throws Exception;
}
