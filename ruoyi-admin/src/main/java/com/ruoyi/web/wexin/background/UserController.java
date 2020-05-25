package com.ruoyi.web.wexin.background;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ruoyi.web.JsonResult;
import com.ruoyi.web.wexin.emali.BaseController;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.jwt.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class UserController extends BaseController {
    @Autowired
    UserService userService;
//分页查询
    @GetMapping("/background")
    public  Map getUserInfo(int page,int limit){
       Map a = userService.getData(page,limit);
       return  a;
    }
    //查询历史纪律
    @GetMapping("/background/backcheckhistry")
    @ResponseBody
    public Map backcheckhistry( int page,int limit,String production,  String beginDate,  String beginEnd){

        Map suflt = userService.getShowTime(page,limit,production,beginDate,beginEnd);

        return  suflt;
    }
    @GetMapping("/savaproduct")
    public  JsonResult savaproduct(String product,String phoneAccount){
        userService.savaproduct(product,phoneAccount);

        return  new JsonResult(200);
    }
    //分页查询
    @GetMapping("/getaduite")
    @ResponseBody
    public JsonResult getaduite(String phoneAccount){

        Map a = userService.getxcxData( phoneAccount);

               JSONObject jsonObject = (JSONObject) JSONObject.toJSON(a);


        return  new JsonResult(200,jsonObject);
    }


    //分页查询收益
    @GetMapping("/background/getmoney")
    @ResponseBody
    public Map getmoney(int page,int limit, String accountPhone){
        Map a=  userService.readEarn(page,limit,accountPhone);

        return  a;
    }
    //分页查询收益
    @GetMapping("/background/addEarn")
    @ResponseBody
    public JsonResult addEarn(String phoneAccount,String product){

        userService.addEarns(product,phoneAccount);
        return  new JsonResult(200);
    }
    //小程序收益分页查询
    @GetMapping("/background/addxcxEarn")
    @ResponseBody
    public JsonResult getxcxEaarn(String phoneAccount){

          List list =userService.addxcxEarns(phoneAccount);
        return  new JsonResult(200,list);
    }

    //小程序结算收益
    @GetMapping("/background/crealXcxmoney")
    @ResponseBody
    @UserLoginToken
    public JsonResult crealXcxmoney(String pruoduction){

        userService.crealXcxMoney(pruoduction);
        return  new JsonResult(200);
    }
    //获得作品查询
    @GetMapping("/background/getproduct")
    @ResponseBody
    public JsonResult getproduct(String phoneAccount){

          List<EmailRexPopj> mysql= userService.getproduct(phoneAccount);
        return  new JsonResult(200,mysql);
    }

    //后台同意小程序结算收益
    @GetMapping("/background/agreeCrealXcxmoney")
    @ResponseBody
    public JsonResult agreeCrealXcxmoney( String production,String phoneAccount){
        userService.agreeCrealXcxmoney(production,phoneAccount);
        return  new JsonResult(200);
    }

    //查询历史纪律
    @GetMapping("/background/checkhistry")
    @ResponseBody
    @UserLoginToken
    public JsonResult checkhistry( String production,  String beginDate,  String beginEnd){
        List suflt = userService.checkhistry(production,beginDate,beginEnd);
        return  new JsonResult(200,suflt);
    }



    //查询历史纪律
    @GetMapping("/background/getjifen")
    @ResponseBody
    public JsonResult getjifen( String phoneAccount){
        Integer suflt = userService.getjifen(phoneAccount);
        if(suflt==null||"".equals(suflt)){
            suflt=0;
        }
        return  new JsonResult(200,suflt);
    }



    //小程序查询作品进度
    @GetMapping("/background/product_schedule")
    @ResponseBody
    public Map schedule(String phoneAccount,Integer page,Integer limit){
        Map check_press= userService.getProductPre(phoneAccount,page,limit);
        return  check_press;
    }

}
