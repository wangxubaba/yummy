package com.wx.yummy.serviceImpl;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.father.Canteen;
import com.wx.yummy.bean.manager.DailyRegisterCanteen;
import com.wx.yummy.bean.order.ManagerPay;
import com.wx.yummy.bean.user.CanteenUser;
import com.wx.yummy.dao.CanteenDao;
import com.wx.yummy.dao.OrderDao;
import com.wx.yummy.dao.UserDao;
import com.wx.yummy.service.ManagerService;
import com.wx.yummy.utility.enums.CanteenCheckState;
import com.wx.yummy.utility.enums.PayMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    CanteenDao canteenDao;
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public ArrayList<DailyRegisterCanteen> getCheckCanteens() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<DailyRegisterCanteen> result=new ArrayList<>();
        for(int i=0;i<7;i++){
            calendar.add(Calendar.DATE, -i);
            Date date = calendar.getTime();
            DailyRegisterCanteen daily=new DailyRegisterCanteen();
            //1.设置日期
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
            daily.setDate(simpleDateFormat.format(date));

            //2.设置等待和已审核队列
            daily.setWaitingCheckCanteens(canteenDao.getWaitingCheckCanteens(date));
            daily.setAlreadyCheckedCanteens(canteenDao.getAlreadyCheckedCanteens(date));

            //3.设置计数值
            daily.setCounts();

            result.add(daily);
        }
        return result;
    }

    @Override
    public ResultMessage checkCanteen(CanteenCheck canteen) {
        //是餐厅修改信息还是餐厅注册
        CanteenCheck checkCanteen= (CanteenCheck) userDao.getCanteen(canteen.getId());
        if(checkCanteen.getCheckState()!=CanteenCheckState.waiting){
            //更新
            if(canteen.getCheckState()==CanteenCheckState.refuse){
                canteenDao.changeCanteenUpdateState(canteen.getId(),CanteenCheckState.refuse);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setFrom("yummy_official@126.com");
                mailMessage.setTo(canteen.getEmail());
                mailMessage.setSubject("Yummy餐厅信息修改失败");
                mailMessage.setText("我们很抱歉的通知您，您修改的餐厅信息未审核通过！您可以尝试重新修改！");
                javaMailSender.send(mailMessage);
                System.out.println("餐厅邮件确认已发送成功");
                return ResultMessage.SUCCESS;
            }
            else{
                userDao.updateCanteen(canteen);
                canteenDao.changeCanteenUpdateState(canteen.getId(),CanteenCheckState.pass);
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setFrom("yummy_official@126.com");
                mailMessage.setTo(canteen.getEmail());
                mailMessage.setSubject("Yummy餐厅信息修改成功");
                mailMessage.setText("恭喜您，您修改的餐厅信息已审核通过！");
                javaMailSender.send(mailMessage);
                System.out.println("餐厅邮件确认已发送成功");
                return ResultMessage.SUCCESS;
            }
        }
        else{
            //新增
            //1.更新数据库内容
            canteenDao.changeCanteenCheckState(canteen.getId(),canteen.getCheckState());

            //为该餐厅建立对应的Dish和Group数据表
            canteenDao.createCanteenDishTable(canteen.getId());

            //3.给餐厅的申请者发送邮件通知
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom("yummy_official@126.com");
            mailMessage.setTo(canteen.getEmail());
            mailMessage.setSubject("欢迎您在Yummy注册餐厅");
            if(canteen.getCheckState()==CanteenCheckState.pass){
                mailMessage.setText("欢迎您注册Yummy!恭喜您已成功注册！您的登陆id为"+canteen.getId()+"。现在就去登陆试试吧！");
            }
            else{
                mailMessage.setText("欢迎您注册Yummy!我们很抱歉的通知您，您注册的餐厅未审核通过！您可以尝试重新注册！");
            }

            javaMailSender.send(mailMessage);
            System.out.println("餐厅邮件确认已发送成功");

            return ResultMessage.SUCCESS;
        }


    }

    @Override
    public ArrayList<ManagerPay> getNotPayedOrders() {
        return orderDao.getNotPayedManagerOrder();
    }

    @Override
    public ArrayList<ManagerPay> getPayedOrders() {
        return orderDao.getAlreadyPayedManagerOrder();
    }

    @Override
    public PayMessage payOrder(Date ordersDate) {
        return orderDao.managerPay(ordersDate);
    }
}
