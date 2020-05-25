package com.ruoyi.web.wexin.emali.mapper;


import com.ruoyi.web.wexin.emali.domain.EmailPopj;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface EmailMapper {
  Integer insert(EmailPopj emailPopj);
  //看邮箱是否已经注册
  Integer selcectBymail(String mail);
  //查询code
  String findByCheck(String check);
  //保存邮箱注册
  Integer insertMailRexInfo(EmailRexPopj emailRexPopj);
  //查询用户信息
  EmailRexPopj selectInfo(EmailRexPopj emailRexPopj);


  //保存手机验证码
  Integer savePhoneCode(EmailPopj emailPopj);
  //保存用户的作品名称

  Integer savaproduct(@Param("production") String product,@Param("phoneAccount") String phoneAccount);
  Integer savaproduct2(@Param("production") String product);
  Integer selectProduct(@Param("production") String product);

  //查询手机code
  String findPhoneByCheck(String Phonecheck);
    //看手机是否已经注册
    Integer selcectByphone(String phoneAccount);
    //保存手机注册
    Integer insertPhoneRexInfo(EmailRexPopj emailRexPopj);

    //查看手机的身份证是否已经保存
    String selectIdCardByPhone(String Phone);

    //插入正面身份证
    Integer UInsertJustId(@Param("justCard") String justId,@Param("phoneAccount") String phoneAccount);
    //插入反面身份证
    Integer UInsertVerId(@Param("versaCard") String verId,@Param("phoneAccount") String phoneAccount);
    //上传合同
    Integer UcontractId(@Param("contract") String verId,@Param("phoneAccount") String phoneAccount);

    //保存手机注册个人信息
    Integer UInsertPhoneInfo( @Param("usernames") String username,
                             @Param("mailAccount") String mailAccount,
                             @Param("bankCard") String bankCard,
                             @Param("address") String address,
                              @Param("phoneAccount") String phoneAccount,
                              @Param("datalineTime") Date date
    );
    //通过手机号查询该用户的所有的信息
    EmailRexPopj selectAllByPhone(@Param("phoneAccount")String phoneAccount);


    Integer chagePassword(@Param("phoneAccount")String phoneAccount,@Param("password")String newpassword );



    //后台查询用户数据
    List selcectUser();
    //查询深黑结果
    EmailRexPopj selcectXCXUser(@Param("phoneAccount")String phoneAccount);
//获取收益表单
    List selectReadEarn(@Param("phoneAccount")String phoneAccount);

    //为用户添加作品资金
    Integer addEarns(@Param("earnYear") String product, @Param("production") String phoneAccounts,@Param("datalineTime") Date datalineTime);

  //查询每天的收益情况，并累计起来
  Integer selectAllEarn( @Param("production") String product);
  //放入累计收益
  Integer insersAllMoney(@Param("allmoney")Integer allmoney,@Param("phoneAccount")String phoneAccount);
  //小程序用户查询作品收益
  List<EmailRexPopj> getxcxEarns(@Param("phoneAccount")String phoneAccount);


   Integer  UpdataNewPassword (EmailRexPopj emailRexPopj);
  //小程序申请结算
   Integer  delectXcxMoney (@Param("production")String production,@Param("applyClear")String apply);
  //后台同意小程序申请结算
  Integer  agreeDelectXcxMoney (@Param("production")String production,@Param("applyClear")String apply);
//查询作品名称
  List  getproducts (@Param("phoneAccount")String phoneAccount);
  //查询作品的历史收益
  List  checkHistry (@Param("production")String production,@Param("beginDate")String beginDate,@Param("beginEnd")String beginEnd);
  List  selcectBackUser (@Param("production")String production,@Param("beginDate")String beginDate,@Param("beginEnd")String beginEnd);
  //获取总积分
  Integer  getjifen (@Param("phoneAccount") String phoneAccount);
  //通过手机号获得作品审核进度
  List<EmailRexPopj>  getProPress (@Param("phoneAccount") String phoneAccount);



  int updateUserStatusForInfo(@Param("type")Integer type, @Param("emailPopj")EmailRexPopj emailPopj,@Param("status")Integer status);
  Integer updataPass(@Param("production") String product);
  Integer updataNotPass(@Param("production") String product,@Param("productResult") String productResult);
}
