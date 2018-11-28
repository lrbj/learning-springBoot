package com.example.fileopera.controller;

import com.example.fileopera.constant.ErrorEnum;
import com.example.fileopera.util.FileUtil;
import com.example.fileopera.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 11:00 AM 11/28/2018
 */
//@RequestMapping("/api/file")
//@RestController  //该注解是ResponseBody和@Controller的集合体，使用@Restcontroller注释会默认返回数据，而不会请求到页面
@Controller
@Api(tags = "file")
public class FileOperaController {

    //获取file.html页面
    @RequestMapping("file")
    public String file(){
        return "/file";
    }


    @PostMapping("/fileUpload")
    @ResponseBody
    @ApiOperation(value = "上传单个文件")
    public ResponseObject upload(@RequestParam("file")MultipartFile file) throws  Exception{
        if(file.isEmpty()) {
            return ResponseObject.fail(ErrorEnum.FILEEMPTY_ERROR);
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传文件名字：" + fileName);

        //获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("后缀名如下："+ suffixName);


        //设置文件存储路径
        String filePath = "./data/";
        String destPath =  FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        System.out.println("destPath:" + destPath);

        return  ResponseObject.success(null);

    }

    //获取multifile页面
    @RequestMapping("multifile")
    public String multifile(){
        return "/multifile";
    }

    @PostMapping("/multifileUpload")
    @ResponseBody
    @ApiOperation(value = "上传多个文件")
    public ResponseObject uploadMore(MultipartHttpServletRequest request) throws Exception {
        List<MultipartFile> fileList = request.getFiles("file");
        if(fileList.isEmpty()){
            return  ResponseObject.fail(ErrorEnum.FILEEMPTY_ERROR);
        }
        MultipartFile file = null;
        String filePath = "./multifile/";
        for( int i = 0; i < fileList.size(); i++){
            file = fileList.get(i);
            String fileName = file.getOriginalFilename();
            String destPath = FileUtil.uploadFile(file.getBytes(),filePath,fileName );
            System.out.println("destPath:"+destPath);
        }

        return  ResponseObject.success(null);
    }



    @RequestMapping("/download")
    @ApiOperation(value = "下载文件")
    public String downLoad(HttpServletResponse response) throws Exception{
        String filename="debug.log";
        String filePath = "./data/" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {

                    bis.close();
                    fis.close();
            }
            System.out.println("----------file download" + filename);

        }
        return null;
    }


}
