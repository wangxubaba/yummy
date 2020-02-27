package com.wx.yummy.daoImpl;

import com.wx.yummy.bean.dish.CanteenCheck;
import com.wx.yummy.bean.order.CanteenOrder;
import com.wx.yummy.dao.CanteenDao;
import com.wx.yummy.utility.DateFormat;
import com.wx.yummy.utility.enums.CanteenCheckState;
import com.wx.yummy.utility.enums.CanteenType;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CanteenDaoImpl implements CanteenDao {
    @Autowired
    JdbcTemplate template;
    @Override
    public ArrayList<CanteenCheck> getWaitingCheckCanteens(Date date) {
        String day=DateFormat.getFormatDate(date).substring(0,10);
        String sql1="select * from canteen where canteen.check='waiting' and applyDate Like '"+day+"%'";
        List list1=getCanteenCheckList(sql1);
        String sql2="select * from updatecanteen where updatecanteen.check='modify' and applyDate Like '"+day+"%'";
        List list2=getCanteenCheckList(sql2);
        ArrayList<CanteenCheck> result=new ArrayList<>(list1);
        for(int i=0;i<list2.size();i++){
            result.add((CanteenCheck) list2.get(i));
        }
        return result;
    }

    @Override
    public ArrayList<CanteenCheck> getAlreadyCheckedCanteens(Date date) {
        String day=DateFormat.getFormatDate(date).substring(0,10);
        String sql1="select * from canteen where applyDate Like '"+day+"%' and canteen.check<>'waiting'";
        List list1=getCanteenCheckList(sql1);
        String sql2="select * from updatecanteen where applyDate Like '"+day+"%' and updatecanteen.check<>'modify'";
        List list2=getCanteenCheckList(sql2);
        ArrayList<CanteenCheck> result=new ArrayList<>(list1);
        for(int i=0;i<list2.size();i++){
            result.add((CanteenCheck) list2.get(i));
        }
        return result;
    }

    @Override
    public ResultMessage changeCanteenCheckState(String canteenID, CanteenCheckState state) {
        String sql="update canteen set canteen.check=? where cid=?";
        template.update(sql,state.name(),canteenID);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage changeCanteenUpdateState(String canteenID, CanteenCheckState state) {
        String sql="update updatecanteen set updatecanteen.check=? where cid=?";
        template.update(sql,state.name(),canteenID);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage createCanteenDishTable(String canteenID) {
        String sql1="CREATE TABLE Dish"+canteenID+" (\n" +
                "  `did` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `dname` VARCHAR(45) NULL,\n" +
                "  `dprice` DOUBLE NULL,\n" +
                "  `damount` INT NULL,\n" +
                "  `dtype` VARCHAR(45) NULL,\n" +
                "  `dbegin` INT NULL,\n" +
                "  `dend` INT NULL,\n" +
                "  PRIMARY KEY (`did`))";
        template.update(sql1);
        String sql2="CREATE TABLE Group"+canteenID+" (\n" +
                "  `gid` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `gname` VARCHAR(45) NULL,\n" +
                "  `gamount` INT NULL,\n" +
                "  `gprice` DOUBLE NULL,\n" +
                "  `gdishes` VARCHAR(45) NULL,\n" +
                "  `gbegin` INT NULL,\n" +
                "  `gend` INT NULL,\n" +
                "  PRIMARY KEY (`gid`))";
        template.update(sql2);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ArrayList<CanteenOrder> getNearCanteens(String province, String city, String area) {
        String sql="select * from canteen where province=? and city=? and area=? and canteen.check=?";
        List list=template.query(sql, new RowMapper<CanteenOrder>() {
            @Override
            public CanteenOrder mapRow(ResultSet resultSet, int i) throws SQLException {
                CanteenOrder canteen=new CanteenOrder();
                canteen.setId(resultSet.getInt("cid"));
                canteen.setProvince(province);
                canteen.setCity(city);
                canteen.setArea(area);
                canteen.setLat(resultSet.getDouble("lat"));
                canteen.setLng(resultSet.getDouble("lng"));
                canteen.setAddress(resultSet.getString("caddress"));
                canteen.setBeginTime(resultSet.getString("cbegin"));
                canteen.setEndTime(resultSet.getString("cend"));
                canteen.setName(resultSet.getString("cname"));
                canteen.setType(CanteenType.valueOf(resultSet.getString("ctype")));
                return canteen;
            }
        }, province, city, area,CanteenCheckState.pass.name());
        return new ArrayList<>(list);
    }



    private List getCanteenCheckList(String sql){
        List list=template.query(sql, new RowMapper<CanteenCheck>() {
            @Override
            public CanteenCheck mapRow(ResultSet resultSet, int i) throws SQLException {
                CanteenCheck check=new CanteenCheck();
                check.setId(resultSet.getInt("cid"));
                check.setHostName(resultSet.getString("hostName"));
                check.setIdNumber(resultSet.getString("idNumber"));
                check.setEmail(resultSet.getString("email"));
                check.setPassword(resultSet.getString("password"));
                check.setName(resultSet.getString("cname"));
                check.setAddress(resultSet.getString("caddress"));
                check.setType(CanteenType.valueOf(resultSet.getString("ctype")));
                check.setBeginTime(resultSet.getString("cbegin"));
                check.setEndTime(resultSet.getString("cend"));
                check.setCheckState(CanteenCheckState.valueOf(resultSet.getString("check")));
                check.setApplyDate(resultSet.getString("applyDate"));
                check.setProvince(resultSet.getString("province"));
                check.setCity(resultSet.getString("city"));
                check.setArea(resultSet.getString("area"));
                check.setAccount(resultSet.getString("account"));
                check.setLat(resultSet.getDouble("lat"));
                check.setLng(resultSet.getDouble("lng"));
                return check;
            }
        });
        return list;
    }
}
