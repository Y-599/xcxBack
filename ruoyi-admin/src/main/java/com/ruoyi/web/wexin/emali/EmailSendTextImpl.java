package com.ruoyi.web.wexin.emali;

import com.ruoyi.web.wexin.emailException.*;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.emali.mapper.EmailMapper;
import com.ruoyi.web.wexin.emali.domain.EmailPopj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class EmailSendTextImpl implements EmailService{
    @Value("${spring.mail.username}")
    private   String FROM ;
    private static  final String TITLR ="来自云科兼职的验证码";
    private static  final String CODE ="验证码：";
    @Autowired//发送
    JavaMailSender javaMailSender;
    @Autowired
    EmailMapper emailMapper;

    public EmailPopj sendEmail(EmailPopj email){
        StringBuilder rands =getRand();
        System.out.println(rands);
        try {
            SimpleMailMessage SimpleMailMessage = new SimpleMailMessage();
            //发送者
            SimpleMailMessage.setFrom(FROM);//    发送者邮箱(需要在配置文件中一致)
            System.out.println(email.getToEmail());
            //接收者
            SimpleMailMessage.setTo(email.getToEmail());
            //发送的标题
            SimpleMailMessage.setSubject(TITLR);
            //发送的内容
            SimpleMailMessage.setText(CODE+rands);
            javaMailSender.send(SimpleMailMessage);
        }catch (Exception e){
            throw new MailSendFailException("邮件发送失败");
        }
        String check = UUID.randomUUID().toString();
        email.setCheck(check);
        email.setCode(rands+"");
        emailMapper.insert(email);
        return email;
    }
    //生成验证码
    private StringBuilder getRand(){
        Random random  = new Random();
        StringBuilder rands =new StringBuilder();
        for (int a= 0;a<7;a++ ){
            int rand = random.nextInt(10);
            rands.append(rand);
        }
        return rands;


    }

    /**
     * 邮箱注册
     * @param emailRexPopj
     * @return
     */
//    @Override
//    public EmailRexPopj emailRex(EmailRexPopj emailRexPopj) {
//         if(emailRexPopj==null||emailRexPopj.equals("")){
//             throw  new EmailRexInfoNotException("用户注册信息获取失败");
//         }
//        //判断邮箱账号是否已经注册
//         int index= emailMapper.selcectBymail(emailRexPopj.getMailAccount());
//         if (index==1){
//             throw new EmailRexAleadException("邮箱已经注册过sssssssssssssss");
//         }
//
//         //判断验证码是否正确
//         String check  = emailRexPopj.getCheck();
//         String code =  emailMapper.findByCheck(check);
//         if (!code.equals( emailRexPopj.getMailCode())){
//             throw new MailCodeFailException("用户输入的验证码错误");
//         }
//         String password = emailRexPopj.getPassword();
//         String salts = UUID.randomUUID().toString();
//         String newPassWord = DigestUtils.md5DigestAsHex((password+salts).getBytes());
//         emailRexPopj.setSalt(salts);
//         emailRexPopj.setPassword(newPassWord);
//         emailMapper.insertMailRexInfo(emailRexPopj);
//         return emailRexPopj;
//    }



    /**
     * 手机号注册
     * @param emailRexPopj
     * @return
     */
    @Override
    public EmailRexPopj phoneRex(EmailRexPopj emailRexPopj) {
        if(emailRexPopj==null||emailRexPopj.equals("")){
            throw  new EmailRexInfoNotException("用户注册信息获取失败");
        }
        //判断手机账号是否已经注册
        int index= emailMapper.selcectByphone(emailRexPopj.getPhoneAccount());
        if (index==1){
            throw new EmailRexAleadException("手机号码已经注册过");
        }

        //判断验证码是否正确
        String phoneCheck  = emailRexPopj.getCheck();
        String code =  emailMapper.findPhoneByCheck(phoneCheck);
        String upd_code = emailRexPopj.getMailCode();
        if (!code.equals(upd_code)){
            throw new MailCodeFailException("用户输入的验证码错误");
        }
        String password = emailRexPopj.getPassword();
        String salts = UUID.randomUUID().toString();
        String newPassWord = DigestUtils.md5DigestAsHex((password+salts).getBytes());
        emailRexPopj.setSalt(salts);
        emailRexPopj.setPassword(newPassWord);
        emailMapper.insertPhoneRexInfo(emailRexPopj);
        return emailRexPopj;
    }

    /**
     * 查询用户个人信息
     * @param emailRexPopj
     */
    @Override
    public EmailRexPopj selectInfo(EmailRexPopj emailRexPopj) {

            if (emailRexPopj == null || emailRexPopj.equals("")) {
                throw new EmailRexInfoNotException("暂未登录，无法获取信息");
            }

           EmailRexPopj emailRexPopj1 = emailMapper.selectInfo(emailRexPopj);

            return  emailRexPopj1;
    }

    /**
     * 审核作品
     * @param emailPopj
     */
    @Override
    public void updateProduct(EmailRexPopj emailPopj) {
        if(emailPopj==null){
            throw  new UserNotFoundException("作品审核提交有误");
        }
        String production = emailPopj.getProduction();
        String productResult =emailPopj.getProductResult();
        if (production.isEmpty()){
            throw  new UserNotFoundException("作品审核提交有误");
        }else if(productResult.isEmpty()){
            emailMapper.updataPass(production);
            //作品通过
        }else if(!productResult.isEmpty()){
            //作品未通过，失败原因
            emailMapper.updataNotPass(production,productResult);

        }else{
            throw  new UserNotFoundException("作品审核出现未知异常");

        }

    }

    /**
     * 用户忘记密码
     * @param emailRexPopj
     * @return
     */
    @Override
    public EmailRexPopj forgetPassword(EmailRexPopj emailRexPopj) {
        if(emailRexPopj==null||emailRexPopj.equals("")){
            throw  new EmailRexInfoNotException("用户注册信息获取失败");
        }
        //判断验证码是否正确
        String phoneCheck  = emailRexPopj.getCheck();
        String code =  emailMapper.findPhoneByCheck(phoneCheck);
        String upd_code = emailRexPopj.getMailCode();
        if (!code.equals(upd_code)){
            throw new MailCodeFailException("用户输入的验证码错误");
        }
        String password = emailRexPopj.getPassword();
        String salts = UUID.randomUUID().toString();
        String newPassWord = DigestUtils.md5DigestAsHex((password+salts).getBytes());
        emailRexPopj.setSalt(salts);
        emailRexPopj.setPassword(newPassWord);

        //设置新密码
        emailMapper.UpdataNewPassword(emailRexPopj);
        return null;
    }
    /**
     * 发送手机验证码
     * @param emailPopj
     * @return
     */
    @Override
    public String sendPhone(EmailPopj emailPopj) {
        String code  = emailPopj.getPhoneCode();
        if (code.equals("") || code==null){
            throw  new RuntimeException("手机随机码为空");
        }
        String phoneUUID = UUID.randomUUID().toString();
        emailPopj.setPhoneCheck(phoneUUID);
        int index =emailMapper.savePhoneCode(emailPopj);
        if (index!=1){
            throw  new InsertFailException("手机号码插入数据");
        }

        return phoneUUID;
    }

    /**
     * 保存手机用户的所有信息
     * @param emailRexPopj
     */
    @Override
    public void savaPhoneInfo(EmailRexPopj emailRexPopj) {
        if(emailRexPopj==null){
            throw  new UserNotFoundException("手机注册信息不存在");
        }
         int a = emailMapper.selcectByphone(emailRexPopj.getPhoneAccount());
        if (a!=1){
            throw new UserNotFoundException("当前用户不存在");
        }
        String usernames = emailRexPopj.getUsername();
        String mailAccount = emailRexPopj.getMailAccount();
        String bankCard = emailRexPopj.getBankCard();
        String address =emailRexPopj.getAddress();
        String phoneAccount =emailRexPopj.getPhoneAccount();
        Date data = new Date();

        Integer index  =emailMapper.UInsertPhoneInfo(usernames,mailAccount,bankCard,address,phoneAccount,data);
        if (index!=1){
            throw new  InsertFailException("手机注册个人信息保存失败");

        }

     }

    /**
     * 审核合同或者身份证
     * @param type  1为身份证 2为合同
     * @param emailPopj
     */
    @Override
    public void updateUserStatusForInfo(Integer type, EmailRexPopj emailPopj,Integer status) {
        int i = emailMapper.updateUserStatusForInfo(type,emailPopj,status);
    }




    /**
     * 保存身份证图片
     * @param phone
     * @param idPath
     */

    @Override
    public void savaIdCard(String phone, String idPath) {
        if(phone.isEmpty()||idPath.isEmpty()){
            throw new FileEmptyException("身份证路径为空或则，手机号为空");
        }
           int index_phone =  emailMapper.selcectByphone(phone);
        if (index_phone!=1){
            throw  new UserNotFoundException("手机注册用户不存在");
        }
            emailMapper.UInsertJustId(idPath,phone);

    }

    /**
     * 保存身份证图片
     * @param phone
     * @param idPath
     */
    @Override
    public void savaIdCard2(String phone, String idPath) {
        if(phone.isEmpty()||idPath.isEmpty()){
            throw new FileEmptyException("身份证路径为空或则，手机号为空");
        }
        int index_phone =  emailMapper.selcectByphone(phone);
        if (index_phone!=1){
            throw  new UserNotFoundException("手机注册用户不存在");
        }
        emailMapper.UInsertVerId(idPath,phone);

    }
    /**
     * 保存身份证图片
     * @param phone
     * @param idPath
     */
    @Override
    public void savaIdCard3(String phone, String idPath) {
        if(phone.isEmpty()||idPath.isEmpty()){
            throw new FileEmptyException("身份证路径为空或则，手机号为空");
        }
        int index_phone =  emailMapper.selcectByphone(phone);
        if (index_phone!=1){
            throw  new UserNotFoundException("手机注册用户不存在");
        }
        emailMapper.UcontractId(idPath,phone);

    }
}
