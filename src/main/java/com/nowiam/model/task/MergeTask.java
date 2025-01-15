package com.nowiam.model.task;

import com.nowiam.conf.MyConfig;
import com.nowiam.mapper.FileMapper;
import com.nowiam.utils.MyFileWriter;

import java.io.File;
import java.io.IOException;

public class MergeTask implements Runnable{
    private MyConfig myConfig;
    private Integer userId;
    private String fid;
    private FileMapper fileMapper;
    private MyFileWriter myFileWriter;
    @Override
    public void run() {
        File source=new File(myConfig.tmp_path+myConfig.separator+
                userId+myConfig.separator+
                fid);
        if(!source.exists())
        {
            System.out.println("合并文件异常：文件夹不存在");
            return;
        }
        File targetDir=new File(myConfig.file_path+myConfig.separator+userId);
        if(!targetDir.exists()) targetDir.mkdir();
        //获取原文件名
        com.nowiam.model.pojo.File file=fileMapper.selectById(fid);
        if(file.getTotalChunks()!=source.list().length){
            System.out.println("合并文件异常：文件分片缺失");
            //TODO 重发送
            return;
        }
        File target=new File(targetDir.getPath()+myConfig.separator+file.getFileName());
        if(target.exists()){
            System.out.println("合并文件异常：文件已存在");
            return;
        }
        try {
            target.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //将tmp中的文件合并到data中
        int totalChunks = file.getTotalChunks();
        try{
            for(int i=0;i<totalChunks;i++)
            {
                File chunk=new File(source.getPath()+myConfig.separator+i);
                myFileWriter.write(chunk,target);
                chunk.delete();
            }
            source.delete();
        }catch (Exception e){
            System.out.println("合并文件异常：合并异常");
        }
        file.setFileStatus(1);
        fileMapper.updateById(file);
        //文件状态变为1
        //线程池优化
    }

    public MergeTask(MyConfig myConfig, Integer userId, String fid, FileMapper fileMapper, MyFileWriter myFileWriter) {
        this.myConfig = myConfig;
        this.userId = userId;
        this.fid = fid;
        this.fileMapper = fileMapper;
        this.myFileWriter = myFileWriter;
    }
}
