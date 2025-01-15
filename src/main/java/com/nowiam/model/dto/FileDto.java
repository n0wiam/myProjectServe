package com.nowiam.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileDto {
    private String fileId;
    private MultipartFile multipartFile;
    private String fileName;
    private int chunkIndex;
    private int totalChunks;

    //construct
    public FileDto(){}

    public FileDto(String fileId, MultipartFile multipartFile, String fileName, int chunkIndex, int totalChunks) {
        this.fileId = fileId;
        this.multipartFile = multipartFile;
        this.fileName = fileName;
        this.chunkIndex = chunkIndex;
        this.totalChunks = totalChunks;
    }


    //get&set

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public int getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(int totalChunks) {
        this.totalChunks = totalChunks;
    }
}
