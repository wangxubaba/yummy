package com.wx.yummy.serviceImpl;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.bean.user.Customer;
import com.wx.yummy.bean.user.CheckCode;
import com.wx.yummy.bean.user.VIP;
import com.wx.yummy.dao.BalanceDao;
import com.wx.yummy.dao.UserDao;
import com.wx.yummy.service.UserService;
import com.wx.yummy.utility.ConstantValues;
import com.wx.yummy.utility.DateFormat;
import com.wx.yummy.utility.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserDao UserDao;

    @Autowired
    private BalanceDao balanceDao;

    @Override
    public RegisterMessage customerFirstRegister(String email) {
        //1.设置邮箱api
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("yummy_official@126.com");
        mailMessage.setTo(email);

        //2.设置邮件主题及内容（包括验证码）并发送邮件
        int checkCode= (int) (Math.random()*1000000);
        mailMessage.setSubject("欢迎Yummy注册");
        mailMessage.setText("欢迎您注册Yummy!您的验证码是"+checkCode);
        javaMailSender.send(mailMessage);

        //3.将checkCode保存到后台
        UserDao.addNewCheckCode(new CheckCode(email,checkCode+"",new Date()));

        System.out.println("邮件验证码已发送成功");
        return RegisterMessage.SendCheckCodeSuccess;
    }

    @Override
    public RegisterMessage customerConfirmCheck(String email, String checkCode) {
        CheckCode code= UserDao.getCheckCode(email);
        if(code==null){
            return RegisterMessage.emailCheckFail;
        }
        else if(!checkCode.equals(code.getCheckCode())){
            return RegisterMessage.checkFail;
        }
        else if(code.getIntervalSeconds(new Date())>180){
            return RegisterMessage.timeCheckFail;
        }
        else{
            UserDao.removeCheckCode(email);
            System.out.println("邮件验证码已检验成功");
            return RegisterMessage.checkSuccess;
        }
    }

    @Override
    public RegisterMessage customerReviseRegister(Customer customer) {
        ResultMessage result= UserDao.registerUser(customer);
        if(result==ResultMessage.SUCCESS){
            return RegisterMessage.CustomerRegisterSuccess;
        }
        else{
            return RegisterMessage.CustomerRegisterFail;
        }
    }

    @Override
    public RegisterMessage canteenRegister(CanteenCheck canteen) {
        UserDao.registerCanteen(canteen);
        return RegisterMessage.CanteenRegisterSuccess;
    }

    @Override
    public ResultMessage managerCheck(CanteenUser canteenUser, ResultMessage result) {
        //审核是否通过都通过邮件的形式告知
        //1.设置邮箱api
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("yummy_official@126.com");
        mailMessage.setTo(canteenUser.getEmail());

        //2.设置邮件主题及内容并发送邮件
        mailMessage.setSubject("欢迎您在Yummy注册餐厅");
        if(result==ResultMessage.SUCCESS){
            mailMessage.setText("欢迎您在Yummy注册餐厅!\n您的注册已审核完毕，恭喜您成为Yummy餐厅的一员\n您的账户id:"+ canteenUser.getId()+"\n您的密码："+ canteenUser.getPassword());
            UserDao.changeCanteenCheckType(canteenUser.getId()+"",CanteenCheckState.pass);
        }
        else{
            mailMessage.setText("欢迎您在Yummy注册餐厅!\n您的注册已审核完毕，很抱歉的通知您，您的申请并未通过审核，您可以尝试重新注册。");
            UserDao.removeInvalidCanteen(canteenUser.getId()+"");
        }

        javaMailSender.send(mailMessage);

        System.out.println("邮件审核结果已发送成功");
        return ResultMessage.SUCCESS;
    }

    @Override
    public LoginMessage login(String username, String password) {
        //管理员登陆
        if(username.equals("admin")){
            return UserDao.adminLogin(username,password);
        }
        //用户登陆
        else if(username.contains("@")){
            return UserDao.customerLogin(username,password);
        }
        //餐厅登陆
        else{
            return UserDao.canteenLogin(username,password);
        }
    }

    @Override
    public Customer getCustomer(String username) {
        return UserDao.getCustomer(username);
    }

    @Override
    public CanteenUser getCanteen(String username) {
        return UserDao.getCanteen(username);
    }

    @Override
    public ResultMessage updateCustomer(Customer customer) {
        return UserDao.updateCustomer(customer);
    }

    @Override
    public ResultMessage updateCanteen(CanteenUser canteenUser) {
        return UserDao.updateCanteen(canteenUser);
    }

    @Override
    public PayMessage PayVIPByAccount(Customer customer,String password) {
        PayMessage message=balanceDao.pay(customer.getPay(),password,ConstantValues.getVipFees());
        if(message==PayMessage.PaySuccess){
            chargeVIP((int)customer.getId());
        }
        return message;
    }

    @Override
    public PayMessage PayVIPByCredits(Customer customer) {
        Customer newCustomer=UserDao.getCustomer(customer.getEmail());
        if(newCustomer.getCredits()<ConstantValues.getChangeCredits()){
            return PayMessage.CreditsNotEnough;
        }
        else{
            customer.setCredits(newCustomer.getCredits()-ConstantValues.getChangeCredits());
            UserDao.updateCustomer(customer);
            chargeVIP((int)customer.getId());
        }
        return PayMessage.PaySuccess;
    }

    @Override
    public VIP getVIP(String email) {
        Customer customer=UserDao.getCustomer(email);
        return UserDao.getVIP((int)customer.getId());
    }

    @Override
    public ResultMessage modifyCanteen(CanteenCheck canteen) {
        CanteenUser origin=UserDao.getCanteen(canteen.getId());
        //若修改了则将信息发送至经理处审核
        if(!isEqual(canteen,origin)){
            UserDao.insertModifiedCanteen(canteen);
            return ResultMessage.SUCCESS;
        }
        return ResultMessage.FAIL;
    }

    @Override
    public ResultMessage cancelUser(String username) {
        ResultMessage message=UserDao.cancelRegister(username);
        return message;
    }

    private ResultMessage chargeVIP(int customerID){
        VIP vip=UserDao.getVIP(customerID);
        Date now=new Date();
        if((vip==null)||(vip.getEnd().getTime()<now.getTime())){
            vip=new VIP();
            vip.setUid(customerID);
            vip.setBegin(new Date());
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.MONTH,1);
            vip.setEnd(calendar.getTime());
            vip.setValid(true);
        }
        else{
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(vip.getEnd());
            calendar.add(Calendar.MONTH,1);
            vip.setEnd(calendar.getTime());
        }
        UserDao.setVIP(vip);
        return ResultMessage.SUCCESS;
    }

    private boolean isEqual(CanteenUser canteen1, CanteenUser canteen2){
        if(!canteen1.getEmail().equals(canteen2.getEmail())){
            return false;
        }
        if(!canteen1.getHostName().equals(canteen2.getHostName())){
            return false;
        }
        if(!canteen1.getPassword().equals(canteen2.getPassword())){
            return false;
        }
        if(!canteen1.getIdNumber().equals(canteen2.getIdNumber())){
            return false;
        }
        if(!canteen1.getAccount().equals(canteen2.getAccount())){
            return false;
        }
        if(!canteen1.getName().equals(canteen2.getName())){
            return false;
        }
        if(!canteen1.getProvince().equals(canteen2.getProvince())){
            return false;
        }
        if(!canteen1.getCity().equals(canteen2.getCity())){
            return false;
        }
        if(!canteen1.getArea().equals(canteen2.getArea())){
            return false;
        }
        if(!canteen1.getAddress().equals(canteen2.getAddress())){
            return false;
        }
        if(!canteen1.getBeginTime().equals(canteen2.getBeginTime())){
            return false;
        }
        if(!canteen1.getEndTime().equals(canteen2.getEndTime())){
            return false;
        }
        if(canteen1.getType()!=canteen2.getType()){
            return false;
        }
        if(canteen1.getLat()!=canteen2.getLat()){
            return false;
        }
        if(canteen1.getLng()!=canteen2.getLng()){
            return false;
        }
        return true;
    }

}
