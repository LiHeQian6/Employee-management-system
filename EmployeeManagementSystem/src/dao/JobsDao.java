package dao;

import utils.DBUtil;

import java.sql.ResultSet;

public class JobsDao {

    public ResultSet QuaryJobs(){
        DBUtil dbUtil=DBUtil.getIstance();
        return dbUtil.query("select * from jobs");
    }

}
