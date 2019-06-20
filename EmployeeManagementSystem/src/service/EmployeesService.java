package service;

import bean.Employee;
import dao.EmployeeDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
                Employee employee=new Employee(rs.getInt("id"),rs.getString("name"),rs.getInt("num"),rs.getString(4),rs.getString(5),rs.getDouble("base_pay"));
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 更新员工信息
     * @param change
     * @return
     */
    public boolean updateEmployee(String change){
        List list = new ArrayList();
        try {
            JSONArray array=new JSONArray(change);
            for (int i = 0; i < array.length(); i++) {
                JSONObject Object=array.getJSONObject(i);
                Employee employee=new Employee();
                employee.setId(Object.getInt("id"));
                employee.setName(Object.getString("name"));
                employee.setNum(Object.getInt("num"));
                employee.setJob(Object.getString("job"));
                employee.setPay_level(Object.getString("pay_level"));
                employee.setBase_pay(Object.getDouble("base_pay"));
                list.add(employee);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EmployeeDao employeeDao=new EmployeeDao();
        return employeeDao.updateEmployee(list);
    }
    public boolean deleteEmployee(String delete){
        List list = new ArrayList();
        try {
            JSONArray array=new JSONArray(delete);
            for (int i = 0; i < array.length(); i++) {
                JSONObject Object=array.getJSONObject(i);
                list.add(Object.getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EmployeeDao employeeDao=new EmployeeDao();
        return employeeDao.deleteEmployee(list);
    }
}
