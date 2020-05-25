package com.ruoyi.web.wexin.emali.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailPopj implements Serializable {
    private Integer id;
    //用户的验证码
    private String code;
    //识别用户的验证码
    private String check;
    @Email
    private String  toEmail;

    private String  toPhone;//发送的手机号

    private String phoneCode;//手机验证码
    private String phoneCheck;//手机索引码



}
