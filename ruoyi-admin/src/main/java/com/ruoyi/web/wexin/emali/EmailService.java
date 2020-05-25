package com.ruoyi.web.wexin.emali;

import com.ruoyi.web.wexin.emali.domain.EmailPopj;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;

public interface EmailService {
    //发送邮件
//    EmailPopj sendEmail(EmailPopj email);
    //注册邮箱
//    EmailRexPopj emailRex(EmailRexPopj emailRexPopj);
    //注册手机
    EmailRexPopj phoneRex(EmailRexPopj emailRexPopj);
    EmailRexPopj forgetPassword(EmailRexPopj emailRexPopj);
    //发送短信
    String sendPhone(EmailPopj emailPopj);

    //保存用户身份证
    void savaIdCard(String phone,String idPath);
    void savaIdCard2(String phone,String idPath);

    void savaIdCard3(String phone,String idPath);

    //保存手机用户的信息

    void savaPhoneInfo(EmailRexPopj emailRexPopj);
    //查询个人信息
    EmailRexPopj selectInfo(EmailRexPopj emailRexPopj);
    //审核身份证或者合同
    void updateUserStatusForInfo(Integer type, EmailRexPopj emailPopj,Integer status);
    //审核作品
    void updateProduct(EmailRexPopj emailPopj);
}
