package dao;

import bean.Admin;
import utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    /**
     * 是否存在该管理员
     * @param admin
     * @return
     */
    public boolean isExists(Admin admin){
        DBUtil dbUtil=DBUtil.getIstance();
        ResultSet rs=dbUtil.isExists(Admin.class,admin,"admin");
        try {
            if(rs.next()&&rs.getString("password").equals(admin.getPassword()))
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean addAdmin(Admin admin){
        DBUtil dbUtil=DBUtil.getIstance();
        if(dbUtil.queryDate("account",admin.getAccount(),"admin","String"))
            return false;
        int n=dbUtil.upDate("insert into admin (account,password) values('"+admin.getAccount()+"','"+admin.getPassword()+"');");
        if(n>0)
            return true;
        else
            return false;
    }
}
