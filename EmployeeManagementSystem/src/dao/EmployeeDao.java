package dao;

import bean.Employee;
import utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDao {
    public ResultSet QuaryEmployee(){
        DBUtil dbUtil=DBUtil.getIstance();
        return dbUtil.query("select id,employees.name,employees.num,jobs.name,pay_level.name,pay_level.base_pay from employees,jobs,pay_level where employees.job_id=jobs.job_id and employees.level_id=pay_level.level_id;");
    }

    public boolean updateEmployee(List<Employee> list){
        DBUtil dbUtil=DBUtil.getIstance();
        int n=0;
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getId()==-1){
                try {
                    ResultSet rs1=dbUtil.query("select job_id from jobs where name='"+list.get(i).getJob()+"';");
                    rs1.next();
                    ResultSet rs2=dbUtil.query("select level_id from pay_level where name='"+list.get(i).getPay_level()+"';");
                    rs2.next();
                    n=dbUtil.upDate("insert into employees (name, job_id, level_id, num) VALUES ('"+list.get(i).getName()+"',"
                            +rs1.getInt("job_id")+","+rs2.getInt(1)+","+list.get(i).getNum()+");");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                ResultSet rs1=dbUtil.query("select job_id from jobs where name='"+list.get(i).getJob()+"';");
                ResultSet rs2=dbUtil.query("select level_id from pay_level where name='"+list.get(i).getPay_level()+"';");
                try {
                    rs1.next();
                    rs2.next();
                    n=dbUtil.upDate("update employees set name='"+list.get(i).getName()+"',job_id="+rs1.getInt(1)+",level_id="+rs2.getInt(1)+",num="+list.get(i).getNum()+" where id="+list.get(i).getId()+";");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if(n==0)
                return false;
        }
        return true;
    }
    public boolean deleteEmployee(List id){
        int n=0;
        DBUtil dbUtil=DBUtil.getIstance();
        for (int i = 0; i <id.size() ; i++) {
            n=dbUtil.upDate("delete from employees where id="+id.get(i)+";");
            if(n==0)
                return false;
        }
        return true;
    }
}

