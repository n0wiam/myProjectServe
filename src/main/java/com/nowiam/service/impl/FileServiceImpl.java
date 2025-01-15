package com.nowiam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.nowiam.conf.MyConfig;
import com.nowiam.mapper.FileMapper;
import com.nowiam.mapper.UserMapper;
import com.nowiam.model.dto.FileDto;
import com.nowiam.model.pojo.User;
import com.nowiam.model.vo.Result;
import com.nowiam.service.FileService;
import com.nowiam.utils.MyFileWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    MyConfig myConfig;
    @Autowired
    MyFileWriter myFileWriter;
    @Autowired
    Gson gson;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FileMapper fileMapper;
    @Override
    public void upload(FileDto fileDto)throws Exception {
        //TODO 获取用户信息
        Integer userId=1;
        //查询文件id是否合法
        String json = stringRedisTemplate.opsForValue().get("Fid:" + fileDto.getFileId());
        Integer master=gson.fromJson(json, Integer.class);

        if(!userId.equals(master))
        {
            System.out.println("上传文件fid异常:"+master);
            return;
        }

        //用户目录
        File userDir=new File(myConfig.tmp_path+myConfig.separator+userId);
        if(!userDir.exists()) userDir.mkdir();
        //文件目录
        File fileDir=new File(userDir.getPath()+myConfig.separator+fileDto.getFileId());
        if(!fileDir.exists()) fileDir.mkdir();
        //文件地址
        File file=new File(fileDir.getPath()+myConfig.separator+fileDto.getChunkIndex());

        //保存文件分片
        if(!file.exists()) {
            System.out.println("保存文件分片:"+file.getPath());
            myFileWriter.mulFileWrite(fileDto.getMultipartFile(),file);
        }
    }

    @Override
    public Result getFid(String fileName,int totalChunks,int fileSize) {
        //TODO 获取user
        Integer userId=1;
        //文件储存限制判断
        User user=userMapper.selectById(userId);
        int requireSize=(fileSize+999)/1000;
        if(user.getUserUsed()+requireSize>user.getUserSpace()) return new Result<>().error(400,"用户空间不足");
        if(user.getUserCount()==user.getUserMaxCount()) return new Result<>().error(400,"文件数量达到上限");
        try {
            //检查文件是否重名
            com.nowiam.model.pojo.File check=fileMapper.selectOne(Wrappers.<com.nowiam.model.pojo.File>lambdaQuery()
                    .eq(com.nowiam.model.pojo.File::getFileName,fileName));
            if(check!=null) return new Result<>().error(400,"存在同名文件");
            //先占住空间
            //user.setUserUsed(user.getUserUsed() + requireSize);
            //user.setUserCount(user.getUserCount() + 1);
            //userMapper.updateById(user);
            //获取文件id
            String fid = mesId();
            //添加可上传文件id，超时不予上传
            stringRedisTemplate.opsForValue().set("Fid:" + fid, gson.toJson(userId), 30, TimeUnit.MINUTES);
            //添加文件信息
            com.nowiam.model.pojo.File file=new com.nowiam.model.pojo.File();
            file.setFid(fid);
            file.setFileName(fileName);
            file.setFileSize(fileSize);
            file.setStatus(0);
            file.setUserId(userId);
            file.setTotalChunks(totalChunks);
            file.setCreateTime(new Date());
            fileMapper.insert(file);

            return new Result<>().ok(fid);
        }catch (Exception e)
        {
            System.out.println("获取Fid失败");
        }
        return new Result<>().error(400,"获取Fid失败");
    }

    @Override
    public void merge(String fid)throws Exception {
        //查询分片是否齐全（网络拥塞），但前端是同步的所以可不必
        //TODO 获取user
        Integer userId=1;

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
        File target=new File(targetDir.getPath()+myConfig.separator+file.getFileName());
        if(target.exists()){
            System.out.println("合并文件异常：文件已存在");
            return;
        }
        target.createNewFile();
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
        file.setStatus(1);
        fileMapper.updateById(file);
        //文件状态变为1
        //线程池优化
    }

    private String mesId(){
        //获得唯一id
        Long BEGIN_TIME=1704067200L;
        Long current_time= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long time_stamp=current_time-BEGIN_TIME;
        Long inc=stringRedisTemplate.opsForValue().increment("inc:unique_id");
        if(inc==null) inc=0L;
        long id=(time_stamp<<32)|(inc);
        return Long.toString(id);
    }
}
