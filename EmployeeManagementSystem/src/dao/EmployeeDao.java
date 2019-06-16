package dao;

import utils.DBUtil;

import java.sql.ResultSet;

public class EmployeeDao {
    public ResultSet QuaryEmployee(){
        DBUtil dbUtil=DBUtil.getIstance();
        return dbUtil.query("select id,employees.name,jobs.name,pay_level.name,pay_level.base_pay from employees,jobs,pay_level where employees.job_id=jobs.job_id and employees.level_id=pay_level.level_id;");
    }

    public boolean updateEmployee(){
        return false;
    }

}

