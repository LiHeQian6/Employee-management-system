package dao;

import utils.DBUtil;

import java.sql.ResultSet;

public class Pay_levelDao {

    public ResultSet QuaryPay_level(){
        DBUtil dbUtil=DBUtil.getIstance();
        return dbUtil.query("select * from pay_level");
    }
}
