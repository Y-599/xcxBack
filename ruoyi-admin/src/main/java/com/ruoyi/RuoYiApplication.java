package com.ruoyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@Controller
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.ruoyi.web.wexin.email.mapper")
public class RuoYiApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____                                                                                                     \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
    @GetMapping("/phone_login/token")
    public  String  token(){
        return "main_v1";
    }
    @GetMapping("/phone_login/main")
    public  String  tokens(){
        return "main";
    }
    @GetMapping("/phone_login/user")
    public  String  user(){

        return "main";
    }
//身份证
    @GetMapping("/phone_login/idCard")
    public  String  idCard(){
        return "idCard";
    }

    //合同
    @GetMapping("/phone_login/contract")
    public  String  contract(){

        return "contract";
    }
    //qian
    @GetMapping("/phone_login/money")
    public  String  money(){
        return "money";
    }
    @GetMapping("/phone_login/addMoney")
    public  String  addMoney(){
        return "addmoney";
    }

    @GetMapping("/phone_login/product")
    public  String  product(){

        return "product";
    }
    @GetMapping("/phone_login/showMornTime")
    public  String  showMornTime(){
        return "showMornTime";
    }
    @GetMapping("/phone_login/showTime")
    public  String  showTime(){
        return "showTime";
    }
     @GetMapping("/phone_login/showProductPree")
    public  String  showProductPree(){
        return "showProductPree";
    }
    @GetMapping("/phone_login/prouct_press")
    public  String  prouct_press(){
        return "prouct_press";
    }



}