package com.wx.yummy.daoImpl;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.user.*;
import com.wx.yummy.dao.UserDao;
import com.wx.yummy.utility.DateFormat;
import com.wx.yummy.utility.enums.CanteenCheckState;
import com.wx.yummy.utility.enums.CanteenType;
import com.wx.yummy.utility.enums.LoginMessage;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate template;
    @Override
    public ResultMessage addNewCheckCode(CheckCode checkCode) {
        //1.先判断这个email以前有没有注册
        String sql1="select email from checkcodes where email=?";
        String backEmail="";
        try{
            backEmail=template.queryForObject(sql1,String.class,checkCode.getEmail());
        }catch (EmptyResultDataAccessException e){
            backEmail=null;
        }
        //新建
        if(backEmail==null){
            String sql="insert into checkcodes values(?,?,?)";
            template.update(sql,checkCode.getEmail(),checkCode.getCheckCode(),checkCode.getConstructTime());
            return ResultMessage.SUCCESS;
        }
        //更新
        else{
            String sql="update checkcodes set checkCode=?, constructTime=? where email=?";
            template.update(sql,checkCode.getCheckCode(),checkCode.getConstructTime(),checkCode.getEmail());
            return ResultMessage.SUCCESS;
        }


    }

    @Override
    public ResultMessage removeCheckCode(String email) {
        String sql="delete from checkCodes where email=?";
        template.update(sql,email);
        return ResultMessage.SUCCESS;
    }

    @Override
    public CheckCode getCheckCode(String email) {
        String sql="select * from checkCodes where email=?";
        CheckCode code=template.queryForObject(sql, new RowMapper<CheckCode>() {
            @Override
            public CheckCode mapRow(ResultSet resultSet, int i) throws SQLException {
                String checkCode=resultSet.getString("checkCode");
                String date=resultSet.getString("constructTime");
                CheckCode result=new CheckCode(email,checkCode,date);
                return result;
            }
        },email);
        return code;
    }

    @Override
    public ResultMessage registerUser(Customer customer) {
        Customer tempCustomer=getCustomer(customer.getEmail());
        if(tempCustomer!=null){
            return ResultMessage.FAIL;
        }
        //先添加基本信息
        String sql1="insert into user values(?,?,?,?,?,?,?,?,?,?)";
        template.update(sql1,null,customer.getName(),customer.getSex(),customer.getEmail(),customer.getPassword(),customer.getCell_phone(),0,customer.getPay(),true,DateFormat.getFormatDate(new Date()));

        String sql2="select uid from user where uemail=?";
        String uid=template.queryForObject(sql2,Integer.class,customer.getEmail())+"";

        //再添加地址信息
        String sql3="insert into address values(?,?,?,?,?,?,?,?,?)";
        Address temp=customer.getAddresses().get(0);
        template.update(sql3,null,temp.getProvince(),temp.getCity(),temp.getArea(),temp.getDetail(),uid,1,temp.getLat(),temp.getLng());
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage registerCanteen(CanteenCheck canteen) {
        String sql="insert into canteen values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,null, canteen.getHostName(), canteen.getIdNumber(), canteen.getEmail(), canteen.getPassword(), canteen.getName(), canteen.getAddress(), canteen.getType().name(), canteen.getBeginTime(), canteen.getEndTime(),CanteenCheckState.waiting.name(),DateFormat.getFormatDate(new Date()),canteen.getProvince(),canteen.getCity(),canteen.getArea(),canteen.getAccount(),canteen.getLat(),canteen.getLng());
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage changeCanteenCheckType(String canteenID, CanteenCheckState state) {
        String sql="update canteen set check=? where cid=?";
        template.update(sql,state,canteenID);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage removeInvalidCanteen(String canteenID) {
        String sql="delete from canteen where cid=?";
        template.update(sql,canteenID);
        return ResultMessage.SUCCESS;
    }

    @Override
    public LoginMessage customerLogin(String username, String password) {
        Customer customer=getCustomer(username);
        if(customer==null){
            return LoginMessage.CustomerUserNameWrong;
        }
        else if(!customer.getPassword().equals(password)){
            return LoginMessage.CustomerPasswordWrong;
        }
        else if (customer.isValid()==false){
            return LoginMessage.CustomerCancelRegister;
        }
        else{
            return LoginMessage.CustomerLoginSuccess;
        }
    }

    @Override
    public LoginMessage canteenLogin(String username, String password) {
        CanteenCheck canteen =getCanteen(username);
        if(canteen ==null || canteen.getCheckState()!=CanteenCheckState.pass){
            return LoginMessage.CanteenUserNameWrong;
        }
        else if(!canteen.getPassword().equals(password)){
            return LoginMessage.CanteenPasswordWrong;
        }
        else{
            return LoginMessage.CanteenLoginSuccess;
        }
    }

    @Override
    public LoginMessage adminLogin(String username, String password) {
        if(password.equals("admin")){
            return LoginMessage.AdminLoginSuccess;
        }
        else{
            return LoginMessage.AdminPasswordWrong;
        }
    }

    @Override
    public Customer getCustomer(String username) {
        String sql="select * from user where uemail=?";
        Customer customer=null;
        try {
            customer=template.queryForObject(sql, new RowMapper<Customer>() {
                @Override
                public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                    Customer person=new Customer();
                    person.setId(resultSet.getLong("uid"));
                    person.setName(resultSet.getString("uname"));
                    person.setSex(resultSet.getString("usex"));
                    person.setEmail(resultSet.getString("uemail"));
                    person.setPassword(resultSet.getString("upassword"));
                    person.setCell_phone(resultSet.getString("uphone"));
                    person.setCredits(resultSet.getInt("ucredits"));
                    person.setPay(resultSet.getString("upay"));
                    person.setValid(resultSet.getBoolean("valid"));
                    String innerSql="select * from address where uid=? order by address.order";
                    List list=template.query(innerSql, new RowMapper<Address>() {
                        @Override
                        public Address mapRow(ResultSet resultSet, int i) throws SQLException {
                            String province=resultSet.getString("province");
                            String city=resultSet.getString("city");
                            String area=resultSet.getString("area");
                            String detail=resultSet.getString("detail");
                            double lat=resultSet.getDouble("lat");
                            double lng=resultSet.getDouble("lng");
                            Address address=new Address(province,city,area,detail);
                            address.setLat(lat);
                            address.setLng(lng);
                            return address;
                        }
                    }, resultSet.getInt("uid"));
                    person.setAddresses(new ArrayList<>(list));

                    return person;
                }
            }, username);
        }catch (EmptyResultDataAccessException e){
            customer=null;
        }
        return customer;
    }

    @Override
    public CanteenCheck getCanteen(String username) {
        String sql="select * from canteen where cid=?";
        CanteenCheck canteen=null;
        try {
            canteen=template.queryForObject(sql, new RowMapper<CanteenCheck>() {
                @Override
                public CanteenCheck mapRow(ResultSet resultSet, int i) throws SQLException {
                    CanteenCheck temp=new CanteenCheck();
                    temp.setId(resultSet.getInt("cid"));
                    temp.setHostName(resultSet.getString("hostName"));
                    temp.setIdNumber(resultSet.getString("idNumber"));
                    temp.setEmail(resultSet.getString("email"));
                    temp.setPassword(resultSet.getString("password"));
                    temp.setName(resultSet.getString("cname"));
                    temp.setAddress(resultSet.getString("caddress"));
                    temp.setType(CanteenType.valueOf(resultSet.getString("ctype")));
                    temp.setBeginTime(resultSet.getString("cbegin"));
                    temp.setEndTime(resultSet.getString("cend"));
                    temp.setCheckState(CanteenCheckState.valueOf(resultSet.getString("check")));
                    temp.setProvince(resultSet.getString("province"));
                    temp.setCity(resultSet.getString("city"));
                    temp.setArea(resultSet.getString("area"));
                    temp.setAccount(resultSet.getString("account"));
                    temp.setLat(resultSet.getDouble("lat"));
                    temp.setLng(resultSet.getDouble("lng"));
                    return temp;
                }
            }, username);
        }catch (EmptyResultDataAccessException e){
            canteen=null;
        }
        return canteen;
    }

    @Override
    public ResultMessage updateCanteen(CanteenUser canteen) {
        String sql="update canteen set hostName=?, idNumber=?, email=?, password=?, cname=?, caddress=?, ctype=?, cbegin=?, cend=?, province=?, city=?, area=?, account=?, lat=?, lng=? where cid=?";
        template.update(sql,canteen.getHostName(), canteen.getIdNumber(), canteen.getEmail(), canteen.getPassword(), canteen.getName(), canteen.getAddress(), canteen.getType().name(), canteen.getBeginTime(), canteen.getEndTime(),canteen.getProvince(),canteen.getCity(),canteen.getArea(),canteen.getAccount(),canteen.getLat(),canteen.getLng(),Integer.parseInt(canteen.getId()));
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage updateCustomer(Customer customer) {
        //先添加基本信息
        String sql1="update user set uname=?, usex=?, uemail=?, upassword=?, uphone=?, ucredits=?, upay=? where uid=?";
        template.update(sql1,customer.getName(),customer.getSex(),customer.getEmail(),customer.getPassword(),customer.getCell_phone(),customer.getCredits(),customer.getPay(),customer.getId());

        //再添加地址信息
        for(int i=0;i<5;i++){
            String sql2="select id from address where uid=? and address.order=?";
            int id=0;
            try{
                id=template.queryForObject(sql2,Integer.class,customer.getId(),i+1);
                if(customer.getAddresses().size()>i){
                    Address temp=customer.getAddresses().get(i);
                    String sql3="update address set province=?, city=?, area=?, detail=?, address.order=?, lat=?, lng=? where id=?";
                    template.update(sql3,temp.getProvince(),temp.getCity(),temp.getArea(),temp.getDetail(),i+1,temp.getLat(),temp.getLng(),id);
                }
                else{
                    String sql4="delete from address where id=?";
                    template.update(sql4,id);
                }
            }catch (EmptyResultDataAccessException e){
                if(customer.getAddresses().size()>i){
                    Address temp=customer.getAddresses().get(i);
                    String sql5="insert into address values(?,?,?,?,?,?,?,?,?)";
                    template.update(sql5,null,temp.getProvince(),temp.getCity(),temp.getArea(),temp.getDetail(),customer.getId(),i+1,temp.getLat(),temp.getLng());
                }
                else {
                    break;
                }
            }
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public VIP getVIP(int uid) {
        String sql="select * from vip where uid=?";
        VIP vip=null;
        try {
            vip=template.queryForObject(sql, new RowMapper<VIP>() {
                @Override
                public VIP mapRow(ResultSet resultSet, int i) throws SQLException {
                    VIP temp=new VIP();
                    temp.setId(resultSet.getInt("id"));
                    temp.setUid(resultSet.getInt("uid"));
                    temp.setBegin(DateFormat.getRelatedDate(resultSet.getString("beginTime")));
                    temp.setEnd(DateFormat.getRelatedDate(resultSet.getString("endTime")));
                    temp.setValid(resultSet.getBoolean("valid"));
                    return temp;
                }
            }, uid);

            if(vip.getEnd().getTime()<new Date().getTime()){
                vip.setValid(false);
                setVIP(vip);
                vip=null;
            }
        }catch (EmptyResultDataAccessException e){
            vip=null;
        }
        return vip;
    }

    @Override
    public ResultMessage setVIP(VIP vip) {
        VIP stored=getVIP(vip.getUid());
        if(stored==null){
            String sql="insert into vip values(?,?,?,?,?)";
            template.update(sql,null,vip.getUid(),DateFormat.getFormatDate(vip.getBegin()),DateFormat.getFormatDate(vip.getEnd()),vip.isValid());
        }
        else{
            String sql="update vip set beginTime=? and endTime=? and valid=? where id=?";
            template.update(sql,DateFormat.getFormatDate(vip.getBegin()),DateFormat.getFormatDate(vip.getEnd()),vip.isValid(),stored.getId());
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage insertModifiedCanteen(CanteenCheck canteen) {
        String sql="insert into updateCanteen values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        template.update(sql,null,Integer.parseInt(canteen.getId()),canteen.getHostName(),canteen.getIdNumber(),canteen.getEmail(),canteen.getPassword(),canteen.getAccount(),canteen.getName(),canteen.getProvince(),canteen.getCity(),canteen.getArea(),canteen.getAddress(),canteen.getType().name(),canteen.getBeginTime(),canteen.getEndTime(),DateFormat.getFormatDate(new Date()),CanteenCheckState.modify.name(),canteen.getLat(),canteen.getLng());
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelRegister(String username) {
        String sql="update user set valid=? where uemail=?";
        template.update(sql,false,username);
        return ResultMessage.SUCCESS;
    }

}
