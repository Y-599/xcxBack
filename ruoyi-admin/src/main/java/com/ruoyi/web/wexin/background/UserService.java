package com.ruoyi.web.wexin.background;

import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map getData(Integer page, Integer limit);
    //获得作品审核进度
    Map getProductPre(String accountPhone,Integer page,Integer limit);
    Map getShowTime(int page,int limit,String production,  String beginDate,  String beginEnd);

    Map getxcxData(String phoneAccount);
    //保存用户名称
    void savaproduct(String product,String phoneAccount);

    //获取收益表
    Map readEarn(Integer page, Integer limit,String accountPhone);

        //为用户的作品加入数据
    void addEarns(String product,String phoneAccount);
        //小程序获得作品收益明细
        List addxcxEarns(String phoneAccount);
    //小程序结算收益
    void crealXcxMoney(String production);
    //后台同意小程序结算收益
    void agreeCrealXcxmoney(String production,String phoneAccount);

    List getproduct(String phoneAccount);

    List checkhistry( String production,String beginDate,String beginEnd);
    Integer getjifen( String phoneAccount);




}
