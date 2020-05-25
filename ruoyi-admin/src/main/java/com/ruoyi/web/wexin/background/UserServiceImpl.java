package com.ruoyi.web.wexin.background;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.web.wexin.emailException.UserNotFoundException;
import com.ruoyi.web.wexin.emali.domain.EmailRexPopj;
import com.ruoyi.web.wexin.emali.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements  UserService{


    final  static String APPLY="申请结算收益";
    @Autowired
    EmailMapper emailMapper;
    @Override
    public Map getData(Integer page, Integer limit) {
      Page<Object> a =  PageHelper.startPage(page,limit,true);
        List mysql_user = emailMapper.selcectUser();
        System.out.println(a.getPages()+"sssssssssssssssssssssssssssssssssss");
        System.out.println(a.getTotal());
        Map map  = new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",a.getTotal());
        map.put("data",mysql_user);
        return map;
    }
    //深黑作品进度
    @Override
    public Map getProductPre(String accountPhone,Integer page,Integer limit) {
        if(accountPhone.isEmpty()){
            throw  new UserNotFoundException("作品审核异常");
        }
        if(page==null||limit==null){
            List<EmailRexPopj> mysql_press =emailMapper.getProPress(accountPhone);
            Map map  = new HashMap();
            map.put("data",mysql_press);
            return map;
        }else{


            Page<Object> a =  PageHelper.startPage(page,limit,true);
            List<EmailRexPopj> mysql_press =emailMapper.getProPress(accountPhone);
            Map map  = new HashMap();
            map.put("code",0);
            map.put("msg","");
            map.put("count",a.getTotal());
            map.put("data",mysql_press);
            return map;
        }

    }

    @Override
    public Map getShowTime(int page, int limit, String production, String beginDate, String beginEnd) {
        Page<Object> a =  PageHelper.startPage(page,limit,true);
        List mysql_user = emailMapper.selcectBackUser(production,beginDate,beginEnd);
        Map map  = new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",a.getTotal());
        map.put("data",mysql_user);
        return map;
    }
    /**
     * 保存用户的作品
     * @param product
     * @param phoneAccount
     * @return
     */
    @Override
    public void savaproduct(String product, String phoneAccount) {

        if (product.isEmpty()||phoneAccount.isEmpty()){
            throw  new UserNotFoundException("并未填写作品");
        }
        int index = emailMapper.selectProduct(product);
        if (index==1){
            throw  new UserNotFoundException("此作品名称已存在，请重新输入");
        }

        emailMapper.savaproduct(product,phoneAccount);
//        emailMapper.savaproduct2(product);
    }

    @Override
    public Map<String,String> getxcxData(String phoneAccount) {
        if (phoneAccount.isEmpty()){
            throw  new UserNotFoundException("账户异常请重新登录");
        }
          EmailRexPopj mysql_slut = emailMapper.selcectXCXUser(phoneAccount);
            String auditing=  mysql_slut.getAuditing();//身份证结果
            String detail=  mysql_slut.getDetail();//合同结果
            String contract_false=  mysql_slut.getContractFlase();//合同审核失败原因
            String id_cart_false=  mysql_slut.getIdCartFlase();//身份证审核失败原因
            Map result =new HashMap();
            result.put("1",auditing);
            result.put('2',detail);
            result.put("3",contract_false);
            result.put("4",id_cart_false);

        return result;
    }


    @Override
    public Map readEarn(Integer page, Integer limit,String accountPhone) {
                if(page==null||page.equals("")){
                    throw  new UserNotFoundException("获取信息异常");
                }
        if(limit==null||limit.equals("")){
            throw  new UserNotFoundException("获取信息异常");
        }
        if(accountPhone==null||accountPhone.equals("")){
            throw  new UserNotFoundException("获取信息异常");
        }

        Page<Object> a =  PageHelper.startPage(page,limit,true);

        List emailRexPopj =emailMapper.selectReadEarn(accountPhone);
        Map map  = new HashMap();
        map.put("code",0);
        map.put("msg","");
        map.put("count",a.getTotal());
        map.put("data",emailRexPopj);
        return  map;
    }



    /**
     * 为用户作品加钱
     * @param product
     * @param phoneAccount
     */
    @Override
    public void addEarns(String product, String phoneAccount) {
        if(product.isEmpty()||phoneAccount.isEmpty()){
            throw  new UserNotFoundException("输入的金额异常，请重新输入");
        }


        Date date = new Date();

        emailMapper.addEarns(product,phoneAccount,date);

        //查询每天的收益情况，并累计起来

        int allmoney = emailMapper.selectAllEarn(phoneAccount);

        emailMapper.insersAllMoney(allmoney,phoneAccount);

    }

    /**
     * 小程序获取作品的收益明细
     * @param phoneAccount
     */
    @Override
    public List addxcxEarns(String phoneAccount) {
            if(phoneAccount.isEmpty()){
                throw new UserNotFoundException("当前账号登录状态异常，请重新登录试试");
            }
            List<EmailRexPopj>  sulft_mysql = emailMapper.getxcxEarns(phoneAccount);

            Map map  = new HashMap();
            for  (int i=0;i<sulft_mysql.size();i++){
                map.put("code",sulft_mysql.get(i).getProduction());
                map.put("text",sulft_mysql.get(i).getEarnYear());
                map.put("type",sulft_mysql.get(i).getAddearnYear());
            }
                return  sulft_mysql;
    }



    /**
     * 小程序结算收益
     * @param
     */
    @Override
    public void crealXcxMoney(String production) {
        if(production.isEmpty()){
            throw new UserNotFoundException("当前账号登录状态异常，请重新登录试试");
        }
          emailMapper.delectXcxMoney(production,APPLY);
    }
    /**
     * 后台同意小程序结算收益
     * @param
     */
    @Override
    public void agreeCrealXcxmoney(String production,String phoneAccount) {
        if(production.isEmpty()||phoneAccount.isEmpty()){
            throw new UserNotFoundException("为获得需要被结算的作品名称，请重新登录试试");
        }

         int index = emailMapper.agreeDelectXcxMoney(production,APPLY);
                if (index<1){
                    throw new UserNotFoundException("收益结算失败");
                }
          int index2 =emailMapper.savaproduct2(production);
        if (index2!=1){
            throw new UserNotFoundException("作品新创失败，请重新手动创写");
        }
    }



    //作品查询
    @Override
    public List getproduct(String phoneAccount) {
        if(phoneAccount.isEmpty()){
            throw new UserNotFoundException("当前登录状态失效");
        }
           List mysql_product=emailMapper.getproducts(phoneAccount);
            if (mysql_product==null){
                throw new UserNotFoundException("目前还未上传作品");
            }
        return mysql_product;
    }



    //历史查询
    @Override
    public List checkhistry(String production, String beginDate, String beginEnd) {
        if (production.isEmpty()||beginDate.isEmpty()||beginEnd.isEmpty()){
            throw new UserNotFoundException("输入的信息有误");
        }


        List mysql_check=emailMapper.checkHistry(production,beginDate,beginEnd);
        if (mysql_check==null){
            throw new UserNotFoundException("查询结果为空");
        }
        return mysql_check;
    }
    //获取积分
    @Override
    public Integer getjifen(String phoneAccount) {
        if(phoneAccount.isEmpty()){
            throw  new UserNotFoundException("登录异常，请重新登录");
        }

        Integer sum = emailMapper.getjifen(phoneAccount);
        return sum;
    }
}
