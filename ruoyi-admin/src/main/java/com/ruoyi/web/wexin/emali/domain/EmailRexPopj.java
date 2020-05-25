package com.ruoyi.web.wexin.emali.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRexPopj extends  BasePopj {
   private Integer id;
   private String username;
   private String password;
   private String newPassword;//修改密码
   private String salt;
   private String check;//查询验证码
   private String phoneAccount;//手机账号
   private String mailCode;//用户传递的code
   private String bankCard;//银行卡
   private String mailAccount;//邮箱账号
   private String justCard;//正
   private String versaCard;//反
   private String address;//用户地址
   private String contract;//
   private String detail;//合同审核结果
   private String auditing;//身份证审核结果
   private String contractFlase; //合同审核失败原因
   private String idCartFlase;//身份证审核失败原因

   private String production;//作品名称
   private Integer earnYear;//作品昨日收益
   private Integer addearnYear;//作品累计收益
   private String datalineTime;//作品昨日收益时间
   private String applyClear;//用户申请结算

   private String productCheck;//用户作品审核状态
   private String productResult;//用户作品审核结果





}
