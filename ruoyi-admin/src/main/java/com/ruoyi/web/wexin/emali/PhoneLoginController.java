package com.ruoyi.web.wexin.emali;

import com.ruoyi.web.JsonResult;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.jwt.TokenserviceImpl;
import com.ruoyi.web.wexin.jwt.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/phone_login")
public class PhoneLoginController  extends  BaseController{
@Autowired
phoneLoginService phoneLoginService;
@Autowired
    TokenserviceImpl tokenservice;

    @ResponseBody
    @PostMapping("/p_login")
    public JsonResult<String> phone_login(@RequestBody EmailRexPopj emailRexPopj){

            String phoneAccount = emailRexPopj.getPhoneAccount();
            String password = emailRexPopj.getPassword();
             EmailRexPopj user =phoneLoginService.phone_login(phoneAccount,password);

         String token =tokenservice.getToken(user);
        return new JsonResult<String>(200,token);
    }

    @UserLoginToken
    @ResponseBody
    @PostMapping("/change")
    public JsonResult<Void> change_password(HttpServletRequest request, @RequestBody EmailRexPopj emailRexPopj){
                    phoneLoginService.change_password(emailRexPopj);


        return  new JsonResult(200);
    }






}
