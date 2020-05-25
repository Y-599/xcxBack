package com.ruoyi.web.wexin.emali;

import com.ruoyi.web.wexin.emailException.CheckNotFoundException;
import com.ruoyi.web.wexin.emailException.UserNotFoundException;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.emali.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class phoneLoginServiceImpl  implements  phoneLoginService{

    @Autowired
    EmailMapper emailMapper;

    /**
     * 用户修改密码
     * @param emailRexPopj
     */
    @Override
    public void change_password(EmailRexPopj emailRexPopj) {
        if(emailRexPopj==null){
            throw new UserNotFoundException("密码修改上传信息失败");
        }
        String phoneAccount = emailRexPopj.getPhoneAccount();
        String password = emailRexPopj.getPassword();
        String newPassword = emailRexPopj.getNewPassword();
        EmailRexPopj mysql_sult =  emailMapper.selectAllByPhone(phoneAccount);
        if (mysql_sult==null){
            throw new UserNotFoundException("用户不存在");
        }
        String salt =  mysql_sult.getSalt();
        password = DigestUtils.md5DigestAsHex((password+salt).getBytes());//用户之前得密码

        if(!password.equals(mysql_sult.getPassword())){
            throw new CheckNotFoundException("用户填写密码不正确");
        }
            newPassword = DigestUtils.md5DigestAsHex((newPassword+salt).getBytes());//修改后的密码
            Integer index = emailMapper.chagePassword(phoneAccount,newPassword);
            if (index!=1){
                throw new UserNotFoundException("密码修改失败");
            }else{
                System.out.println("修改成功");
            }
    }

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */


    @Override
    public EmailRexPopj phone_login(String account, String password) {
        if ("".equals(account)||"".equals(password)){
            throw  new UserNotFoundException("登录信息不存在");
        }
        EmailRexPopj mysql_result= emailMapper.selectAllByPhone(account);
        if(mysql_result==null){
            throw  new UserNotFoundException("账号不存在");
        }
        String salt= mysql_result.getSalt();
        String old_password= mysql_result.getPassword();
        String newPassWord = DigestUtils.md5DigestAsHex((password+salt).getBytes());
        if(!old_password.equals(newPassWord)){
            throw  new UserNotFoundException("密码输入错误");
        }
        return mysql_result;
    }
}
