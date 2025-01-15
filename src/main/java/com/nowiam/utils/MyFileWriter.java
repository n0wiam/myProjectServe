package com.nowiam.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
public class MyFileWriter {
    public boolean write(String source,String target) throws Exception
    {
        return write(new File(source),new File(target));
    }
    public boolean write(File source,File target) throws Exception{
        //该函数会向目标文件内容追加写入新文件内容，如果目标文件不存在会创建
        if(!source.exists()||!target.exists()||target.isDirectory()) return false;
        if(source.isFile()&& target.isFile())
        {
            //清空目标文件内容
            //FileOutputStream clear_outputStream=new FileOutputStream(target,false);
            //clear_outputStream.close();
            FileInputStream inputStream=new FileInputStream(source);
            FileOutputStream outputStream=new FileOutputStream(target,true);
            try {
                while (true) {
                    byte[] buffer = new byte[1024];//1024只是一个象征值, len才是数组长度
                    int len = inputStream.read(buffer);
                    if (len == -1) {
                        // 如果返回 -1 说明读取完毕了
                        break;
                    }
                    outputStream.write(buffer,0,len);
                }
            }catch (Exception e)
            {
                System.out.println("MyFileWriter:文件读写异常");
                inputStream.close();
                outputStream.close();
                return false;
            }finally {
                inputStream.close();
                outputStream.close();
            }
            return true;
        }
        return false;
    }
    public void mulFileWrite(MultipartFile multipartFile,File target)throws Exception{
        if(target.exists()){
            if(!target.isFile()){
                System.out.println("MyFileWriter:目标不是文件");
                return;
            }
            else if(target.isFile()){
                target.delete();
            }
        }
        try{
            target.createNewFile();
            multipartFile.transferTo(target);
        }catch (Exception e){
            System.out.println("MyFileWriter:文件写入错误");
        }
    }
    public void clear(String target)throws Exception{
        //清空文件内容
        File file=new File(target);
        if(!file.exists()||!file.isFile()) return;
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file,false);
            fileOutputStream.close();
        }catch (Exception e){
            System.out.println("MyFileWriter:清除文件内容异常");
        }
    }
}
