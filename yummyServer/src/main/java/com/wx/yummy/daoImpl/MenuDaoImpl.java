package com.wx.yummy.daoImpl;

import com.wx.yummy.bean.dish.Discount;
import com.wx.yummy.bean.dish.Dish;
import com.wx.yummy.bean.dish.DishType;
import com.wx.yummy.bean.dish.Menu;
import com.wx.yummy.dao.MenuDao;
import com.wx.yummy.utility.enums.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MenuDaoImpl implements MenuDao {
    @Autowired
    private JdbcTemplate template;

    //初始化下一次菜单的时间为上一次设置menu的后一天
    @Override
    public Menu initialNewMenuTime(String canteenID) {
        String sql="select max(dend) as end from dish"+canteenID;
        Menu menu=null;
        try {
            menu=template.queryForObject(sql, new RowMapper<Menu>() {
                @Override
                public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
                    Menu temp=new Menu();
                    String endStr=resultSet.getString("end");
                    String tempDateStr=endStr.substring(0,4)+"/"+endStr.substring(4,6)+"/"+endStr.substring(6);
                    Date endDate=new Date(tempDateStr);
                    Calendar calendar=Calendar.getInstance();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DATE,1);
                    Date newBegin=calendar.getTime();
                    calendar.add(Calendar.DATE,1);
                    Date newEnd=calendar.getTime();
                    SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
                    temp.setBeginTime(format.format(newBegin));
                    temp.setEndTime(format.format(newEnd));
                    return temp;
                }
            });
        }catch (NullPointerException e){
            menu=new Menu();
            menu.setTypes(new ArrayList<>());
            menu.setDishDiscount(new ArrayList<>());
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE,1);
            Date newBegin=calendar.getTime();
            calendar.add(Calendar.DATE,1);
            Date newEnd=calendar.getTime();
            SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
            menu.setBeginTime(format.format(newBegin));
            menu.setEndTime(format.format(newEnd));
        }
        menu.setCanteenID(canteenID);
        return menu;
    }

    //初始化今天菜单的起讫时间
    @Override
    public Menu initialTodayMenuTime(String canteenID, int today){
        String sql="select distinct dbegin, dend from dish"+canteenID+" where dbegin<=? and dend>=?";
        Menu menu=null;
        try {
            menu=template.queryForObject(sql, new RowMapper<Menu>() {
                @Override
                public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
                    Menu temp=new Menu();
                    temp.setBeginTime(resultSet.getString("dbegin"));
                    temp.setEndTime(resultSet.getString("dend"));
                    return temp;
                }
            }, today, today);
        }catch (EmptyResultDataAccessException e){
            menu=new Menu();
            menu.setBeginTime("");
            menu.setEndTime("");
        }
        menu.setCanteenID(canteenID);
        return menu;
    }

    @Override
    public ArrayList<DishType> getDishTypes(String canteenID, int day) {
        String sql="select * from dish"+canteenID+" where dbegin<=? and dend>=? order by dtype";
        List list=template.query(sql, new RowMapper<Dish>() {
            @Override
            public Dish mapRow(ResultSet resultSet, int i) throws SQLException {
                Dish dish=new Dish();
                dish.setId(resultSet.getInt("did"));
                dish.setName(resultSet.getString("dname"));
                dish.setPrice(resultSet.getDouble("dprice"));
                dish.setAmount(resultSet.getInt("damount"));
                dish.setType(resultSet.getString("dtype"));
                return dish;
            }
        }, day, day);
        ArrayList<Dish> allDishes=new ArrayList<>(list);
        if(allDishes.size()>0){
            String typeName=allDishes.get(0).getType();
            int i=0;
            ArrayList<DishType> dishTypes=new ArrayList<>();
            while(i<allDishes.size()){
                DishType dishType=new DishType();
                dishType.setName(typeName);
                ArrayList<Dish> dishes=new ArrayList<>();
                for(int j=i;j<allDishes.size();j++){
                    if(allDishes.get(j).getType().equals(typeName)){
                        dishes.add(allDishes.get(j));
                        if(j==allDishes.size()-1){
                            i=j+1;
                        }
                    }
                    else{
                        typeName=allDishes.get(j).getType();
                        i=j;
                        break;
                    }
                }
                dishType.setDishes(dishes);
                dishTypes.add(dishType);
            }
            return dishTypes;
        }
        else {
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Discount> getDiscounts(String canteenID, int day) {
        String sql="select * from group"+canteenID+" where gbegin<=? and gend>=?";
        List list=template.query(sql, new RowMapper<Discount>() {
            @Override
            public Discount mapRow(ResultSet resultSet, int i) throws SQLException {
                Discount discount=new Discount();
                discount.setId(resultSet.getInt("gid"));
                discount.setAmount(resultSet.getInt("gamount"));
                discount.setName(resultSet.getString("gname"));
                discount.setDiscount_price(resultSet.getDouble("gprice"));
                ArrayList<Dish> dishes=new ArrayList<>();
                String[] dishStr=resultSet.getString("gdishes").split(",");
                for(int j=0;j<dishStr.length;j++){
                    String sql2="select * from dish"+canteenID+" where did=?";
                    Dish dish=template.queryForObject(sql2, new RowMapper<Dish>() {
                        @Override
                        public Dish mapRow(ResultSet resultSet, int i) throws SQLException {
                            Dish temp=new Dish();
                            temp.setId(resultSet.getInt("did"));
                            temp.setName(resultSet.getString("dname"));
                            temp.setType(resultSet.getString("dtype"));
                            temp.setPrice(resultSet.getDouble("dprice"));
                            temp.setAmount(1);
                            return temp;
                        }
                    },dishStr[j]);
                    dishes.add(dish);
                }

                if(dishes.size()>0){
                    for(int j=1;j<dishes.size();j++){
                        if(dishes.get(j).getName().equals(dishes.get(j-1).getName())){
                            dishes.get(j-1).setAmount(dishes.get(j-1).getAmount()+1);
                            dishes.remove(j);
                            j--;
                        }
                    }
                }
                discount.setDishes(dishes);
                return discount;
            }
        },  day, day);
        return new ArrayList<>(list);
    }

    @Override
    public int getLastEndTime(String canteenID) {
        String sql="select max(dend) as end from dish"+canteenID;
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("YYYYMMdd");
        int day=0;
        try {
            day=template.queryForObject(sql,Integer.class);
        }catch (NullPointerException e){
            day=Integer.parseInt(format.format(date));
        }
        return day;
    }

    @Override
    public ResultMessage setDishes(String canteenID, ArrayList<DishType> types, int begin, int end) {
        for(int i=0;i<types.size();i++){
            ArrayList<Dish> dishes=types.get(i).getDishes();
            for(int j=0;j<dishes.size();j++){
                Dish dish=dishes.get(j);
                String sql1="insert into dish"+canteenID+" values(?,?,?,?,?,?,?)";
                template.update(sql1,null,dish.getName(),dish.getPrice(),dish.getAmount(),types.get(i).getName(),begin,end);
            }
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage setDiscounts(String canteenID, ArrayList<Discount> discounts, int begin, int end) {
        for(int i=0;i<discounts.size();i++){
            Discount discount=discounts.get(i);
            ArrayList<Dish> dishes=discount.getDishes();
            String ids=getDishIDS(dishes,canteenID,begin+"",end+"");
            String sql2="insert into group"+canteenID+" values(?,?,?,?,?,?,?)";
            template.update(sql2,null,discount.getName(),discount.getAmount(),discount.getDiscount_price(),ids,begin,end);
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public Dish getDishByID(String canteenID, int dishID) {
        String sql="select * from dish"+canteenID+" where did=?";
        Dish dish=template.queryForObject(sql, new RowMapper<Dish>() {
            @Override
            public Dish mapRow(ResultSet resultSet, int i) throws SQLException {
                Dish temp= new Dish();
                temp.setId(resultSet.getLong("did"));
                temp.setAmount(resultSet.getInt("damount"));
                temp.setPrice(resultSet.getDouble("dprice"));
                temp.setName(resultSet.getString("dname"));
                temp.setType(resultSet.getString("dtype"));
                return temp;
            }
        }, dishID);
        return dish;
    }

    @Override
    public Discount getDiscountByID(String canteenID, int dishID) {
        String sql="select * from group"+canteenID+" where gid=?";
        Discount discount=template.queryForObject(sql, new RowMapper<Discount>() {
            @Override
            public Discount mapRow(ResultSet resultSet, int i) throws SQLException {
                Discount temp=new Discount();
                temp.setAmount(resultSet.getInt("gamount"));
                temp.setDiscount_price(resultSet.getDouble("gprice"));
                temp.setId(resultSet.getInt("gid"));
                temp.setName(resultSet.getString("gname"));
                return temp;
            }
        }, dishID);
        return discount;
    }

    private String getDishIDS(ArrayList<Dish> dishes,String canteenID, String beginTime, String endTime){
        String result="";
        for(int i=0;i<dishes.size();i++){
            Dish dish=dishes.get(i);
            String sql="select did from dish"+canteenID+" where dname=? and dprice=? and dbegin=? and dend=?";
            int id=template.queryForObject(sql, Integer.class, dish.getName(), dish.getPrice(), beginTime, endTime);
            int amount=dish.getAmount();
            for(int j=0;j<amount;j++){
                result=result+","+id;
            }
        }
        return result.substring(1);
    }



}
