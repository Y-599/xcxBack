package com.ruoyi.web.wexin.emali;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.web.JsonResult;
import com.ruoyi.web.wexin.emailException.*;
import com.ruoyi.web.wexin.emali.domain.EmailPopj;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.jwt.UserLoginToken;
import com.ruoyi.web.wexin.phone.SendCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/email")
public class EmailController extends BaseController
{

    @Autowired
    EmailService emailSendText;
    @Autowired
    private ServerConfig serverConfig;
//    /**
//     * 获取用户的邮箱 向用户发送邮箱，并发送验证识别嘛
//     * @param emailPopj
//     */
//   @GetMapping("/emailInfo")
//   @ResponseBody
//    public JsonResult<EmailPopj> sendMail(EmailPopj emailPopj){
//
//        EmailPopj check = emailSendText.sendEmail(emailPopj);
//        check.setCode("0");
//        return new JsonResult<EmailPopj>(200,check);
//
//    }
//    //邮箱注册
//    @PostMapping("/emailrex")
//    @ResponseBody
//    public  JsonResult<String> emailrex(@RequestBody EmailRexPopj emailRexPopj){
//           EmailRexPopj result=  emailSendText.emailRex(emailRexPopj);
//           return  new JsonResult<String>(SUCCESS,result.getMailAccount());
//    }


    /**
     * 获取用户的手机号码 向用户发送验证，并发送验证识别嘛
     * @param emailPopj
     */
    @RequestMapping(value = "/sendcode",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<String> sendcode (EmailPopj emailPopj) throws ClientException     {
        String code = SendCode.getRand().toString();//手机验证码
        emailPopj.setPhoneCode(code);
             String  phoneUUID =  emailSendText.sendPhone(emailPopj);
     try{
         SendSmsResponse result=   SendCode.sendSms(emailPopj.getToPhone(),code,"SMS_189611734");
         result.getMessage();
         result.getCode();
     }catch (Exception e){
         e.printStackTrace();
     }
        //调用短信发送接口，三个参数，手机号，验证码，短信模板
        return new JsonResult<String>(SUCCESS,phoneUUID);
    }

    //手机号注册
    @PostMapping("phonerex")
    @ResponseBody
    public  JsonResult<String> phonerex(@RequestBody EmailRexPopj emailRexPopj){

        EmailRexPopj result=  emailSendText.phoneRex(emailRexPopj);

        return  new JsonResult<String>(SUCCESS,result.getPhoneAccount());
    }

    @PostMapping("forgetPassword")
    @ResponseBody
    public  JsonResult<String> forgetPassword(@RequestBody EmailRexPopj emailRexPopj){

        emailSendText.forgetPassword(emailRexPopj);

        return  new JsonResult<String>(SUCCESS);
    }
    @GetMapping("selectInfo")
    @ResponseBody
    public  JsonResult<EmailRexPopj> selectInfo( EmailRexPopj emailRexPopj){

      EmailRexPopj emailRexPopj1=  emailSendText.selectInfo(emailRexPopj);

        return  new JsonResult(SUCCESS,emailRexPopj1);
    }


    /**
     * 允许上传的头像的文件类型
     */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();
    /**
     * 头像文件大小的上限值
     */
    public static final int AVATAR_MAX_SIZE = 6000 * 1024;
    /**
     * 初始化允许上传的头像的文件类型
     */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @GetMapping ("/updateUserStatusForInfo")
    @ResponseBody
    public JsonResult updateUserStatusForInfo(EmailRexPopj emailPopj,Integer type,Integer status){
        //类型为1 为身份证审核
        //类型为2 为合同审核
        emailSendText.updateUserStatusForInfo(type,emailPopj,status);
        return  new JsonResult<>(SUCCESS);
    }

    //保存用户的身份证图片
    @PostMapping ("/savaIdCard")
    @ResponseBody
    public  JsonResult<Void> savaIdCard(@RequestParam("file")MultipartFile file , HttpServletRequest request ,EmailRexPopj emailRexPopj){




            savaIDCard(file,request,emailRexPopj.getPhoneAccount());




        return  new JsonResult<>(SUCCESS);
    }
    //保存用户的身份证图片
    @PostMapping ("/savaIdCard2")
    @ResponseBody
    public  JsonResult<Void> savaVerIdCard(@RequestParam("file")MultipartFile file , HttpServletRequest request ,EmailRexPopj emailRexPopj){




        savaIDCard2(file,request,emailRexPopj.getPhoneAccount());

        return  new JsonResult<>(SUCCESS);
    }
    @PostMapping ("/savaIdCard3")
    @ResponseBody
    public  JsonResult<Void> savacontrca(@RequestParam("file")MultipartFile file , HttpServletRequest request ,EmailRexPopj emailRexPopj){




        savaIDCard3(file,request,emailRexPopj.getPhoneAccount());

        return  new JsonResult<>(SUCCESS);
    }




    private void savaIDCard(MultipartFile file,HttpServletRequest request,String phone){
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的身份证文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) {
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的身份证文件");
        }

//        // 判断上传的文件类型是否超出限制
//        String contentType = file.getContentType();
//        if (!AVATAR_TYPES.contains(contentType)) {
//            // 是：抛出异常
//            throw new FileTypeException("不支持使用该类型的文件作为身份证，允许的文件类型：\n" + AVATAR_TYPES);
//        }

        String fileName = "";
        try
        {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            fileName = FileUploadUtils.upload(filePath, file);
        }
        catch (Exception e)
        {
            throw new FileEmptyException("文件上传失败");
        }
        emailSendText.savaIdCard(phone,fileName);


    }
    private void savaIDCard3(MultipartFile file,HttpServletRequest request,String phone){
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的身份证文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) {
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的身份证文件");
        }

//        // 判断上传的文件类型是否超出限制
//        String contentType = file.getContentType();
//        if (!AVATAR_TYPES.contains(contentType)) {
//            // 是：抛出异常
//            throw new FileTypeException("不支持使用该类型的文件作为身份证，允许的文件类型：\n" + AVATAR_TYPES);
//        }

        String fileName = "";
        try
        {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            fileName = FileUploadUtils.upload(filePath, file);
        }
        catch (Exception e)
        {
            throw new FileEmptyException("文件上传失败");
        }
        emailSendText.savaIdCard3(phone,fileName);


    }
    private void savaIDCard2(MultipartFile file,HttpServletRequest request,String phone){
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的身份证文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) {
            // 是：抛出异常
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的身份证文件");
        }

//        // 判断上传的文件类型是否超出限制
//        String contentType = file.getContentType();
//        if (!AVATAR_TYPES.contains(contentType)) {
//            // 是：抛出异常
//            throw new FileTypeException("不支持使用该类型的文件作为身份证，允许的文件类型：\n" + AVATAR_TYPES);
//        }

        String fileName = "";
        try
        {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            fileName = FileUploadUtils.upload(filePath, file);
        }
        catch (Exception e)
        {
            throw new FileEmptyException("文件上传失败");
        }
        emailSendText.savaIdCard2(phone,fileName);


    }















    //用户保存信息
    @PostMapping("/savaInfo")
    @ResponseBody
    public  JsonResult<String> savaInfo(@RequestBody EmailRexPopj emailRexPopj){
         emailSendText.savaPhoneInfo(emailRexPopj);
        return  new JsonResult<String>(SUCCESS);
    }

    //用户保存信息
    @GetMapping("/updateProduct")
    @ResponseBody
    public  JsonResult<String> updateProduct(EmailRexPopj emailRexPopj){
        emailSendText.updateProduct(emailRexPopj);
        return  new JsonResult<String>(SUCCESS);
    }




}
