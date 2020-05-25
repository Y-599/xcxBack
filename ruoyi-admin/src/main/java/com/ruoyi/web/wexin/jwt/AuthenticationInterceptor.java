package com.ruoyi.web.wexin.jwt;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ruoyi.web.wexin.emailException.UserNotFoundException;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 身份验证器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
   @Autowired
    TokenserviceImpl userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String  token =request.getHeader("token");

        if (!(handler instanceof HandlerMethod)){//instanceof判断某个东西是否属于
            return  true;  //判断这个拦截不属于所验证范围
        }
        HandlerMethod handlerMethod= (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
//检测不需要验证的用户
        if(method.isAnnotationPresent(PassToken.class)){//判断注解passToken是否在method上   Boolean
            PassToken passToken = method.getAnnotation(PassToken.class);//返回指点类型的注释
            if (passToken.required()){
                return  true;
            }
        }
        //检测需要检测的用户
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()){
                if (token==null){
                    response.sendRedirect("/email/emailInfo");
                }
                DecodedJWT jwt =JWT.decode(token);
                Date aa = jwt.getExpiresAt();
                Date date = new Date();
                if (date.after(aa)){
                    throw  new RuntimeException("token过期");

                }
                //获取usrid
                String userId  ;
                try{
                    userId = JWT.decode(token).getAudience().get(0);


                }catch (JWTDecodeException j){
                    throw  new RuntimeException("401");
                }
                EmailRexPopj users =userService.getUserInfo(userId);
                if (users==null){
                    response.sendRedirect("/user/login");
                }
                //验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(users.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    response.sendRedirect("/user/login");
                }
                return true;
            }
        }
        return true;
    }

}
