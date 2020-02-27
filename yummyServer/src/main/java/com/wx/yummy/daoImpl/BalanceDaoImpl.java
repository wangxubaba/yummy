package com.wx.yummy.daoImpl;

import com.wx.yummy.bean.order.Balance;
import com.wx.yummy.dao.BalanceDao;
import com.wx.yummy.utility.enums.PayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class BalanceDaoImpl implements BalanceDao {
    @Autowired
    JdbcTemplate template;
    @Override
    public PayMessage pay(String accountID, String password, double cash) {
        Balance customerBalance=getBalanceByID(accountID);
        if(customerBalance==null){
            return PayMessage.WrongAccount;
        }
        else if(!customerBalance.getPassword().equals(password)){
            return PayMessage.WrongPassword;
        }
        else if(customerBalance.getBalance()<cash){
            return PayMessage.BalanceNotEnough;
        }
        else{
            Balance adminBalance=getBalanceByID("admin");
            String sql2="update balance set balance.balance=? where account='admin'";
            template.update(sql2,adminBalance.getBalance()+cash);
            charge(accountID,0-cash);
            return PayMessage.PaySuccess;
        }
    }

    @Override
    public PayMessage charge(String accountID, double cash) {
        Balance balance=getBalanceByID(accountID);
        String sql="update balance set balance.balance=? where account=?";
        template.update(sql,balance.getBalance()+cash,accountID);
        return PayMessage.UpdateSuccess;
    }

    @Override
    public PayMessage transfer(String fromID, String toID, double cash) {
        charge(fromID,-cash);
        charge(toID,cash);
        return PayMessage.UpdateSuccess;
    }

    private Balance getBalanceByID(String accountID){
        String sql1="select * from balance where account=?";
        Balance balance=null;
        try {
            balance=template.queryForObject(sql1, new RowMapper<Balance>() {
                @Override
                public Balance mapRow(ResultSet resultSet, int i) throws SQLException {
                    Balance temp=new Balance();
                    temp.setId(resultSet.getInt("id"));
                    temp.setAccount(resultSet.getString("account"));
                    temp.setPassword(resultSet.getString("password"));
                    temp.setBalance(resultSet.getDouble("balance"));
                    return temp;
                }
            }, accountID);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
        return balance;
    }
}
