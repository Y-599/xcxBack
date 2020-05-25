package com.ruoyi.web.wexin.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.emali.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenserviceImpl  {
@Autowired
EmailMapper emailMapper;
    public String getToken(EmailRexPopj user) {

        Date date = new Date();
        date.setTime(date.getTime()+1000*60*60*24);
        String id = user.getPhoneAccount();
        String token ="";
        token = JWT.create().
                withAudience(id).withExpiresAt(date).
                sign(Algorithm.HMAC256(user.getPassword()));
                return  token;
    }

    public EmailRexPopj getUserInfo(String account_phone) {

        EmailRexPopj userInfo=emailMapper.selectAllByPhone(account_phone);
        return userInfo;
    }
}
