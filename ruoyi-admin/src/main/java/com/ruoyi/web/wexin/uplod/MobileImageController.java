package com.ruoyi.web.wexin.uplod;


import com.ruoyi.web.JsonResult;
import com.ruoyi.web.wexin.emali.EmailSendTextImpl;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片上传
 */
@Controller
public class MobileImageController {
@Autowired
    EmailSendTextImpl emailSendText;
    private static final String[] imageExtension= new String[]{"jpeg", "jpg", "gif", "bmp", "png"};

    @PostMapping("/fileUploads")
    @ResponseBody
    public JsonResult projectPictureUpload(@RequestParam(value = "file",required = true) MultipartFile file,EmailRexPopj phone) throws Exception {
        //将图片上传到服务器
        if(file.isEmpty()){
            return new JsonResult(-1,"图片不能为空");
        }
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //图片名称为uuid+图片后缀防止冲突
        String fileName = UUID.randomUUID().toString()+"."+suffix;
        String os = System.getProperty("os.name");
        //文件保存路径
        String filePath="";
        if(os.toLowerCase().startsWith("win")){
            //windows下的路径
            filePath ="d:/pictureUpload/project/";
        }else {
            //linux下的路径
            filePath="/root/pictureUpload/project/";
        }
        Boolean writePictureflag = FileUtils.uploadFile(file.getBytes(),filePath,fileName);
        if(writePictureflag == false){
            //上传图片失败
            return new JsonResult(-1,"图片上传失败");
        }
        //上传成功后，将可以访问的完整路径返回
        String fullImgpath = "/mobile/images/pictureUpload/project/"+fileName;
        emailSendText.savaIdCard(phone.getPhoneAccount(),fullImgpath);


        return null;
    }


}
