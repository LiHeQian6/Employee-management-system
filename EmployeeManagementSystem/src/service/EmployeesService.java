package service;

import bean.Employee;
import bean.Pay_level;
import dao.EmployeeDao;
import dao.Pay_levelDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesService {
    /**
     * 查询员工信息
     * @return
     */
    public List QuaryEmployee(){
        EmployeeDao employeeDao=new EmployeeDao();
        List<Employee> list=new ArrayList<>();
        ResultSet rs=employeeDao.QuaryEmployee();
        try {
            while(rs.next()){
                Employee employee=new Employee(rs.getInt("id"),rs.getString("name"),rs.getString(3),rs.getString(4),rs.getDouble("base_pay"));
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
