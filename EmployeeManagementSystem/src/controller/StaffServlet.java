package controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.EmployeesService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer=response.getWriter();
        String change=request.getParameter("change");
        String delete=request.getParameter("delete");
        if(change==null&&delete==null){
            EmployeesService employeesService = new EmployeesService();
            List list = employeesService.QuaryEmployee();
            JSONArray jsonArray = new JSONArray(list);
            writer.write(jsonArray.toString());
        }else if(delete==null&&change.equals("true")) {
            InputStreamReader insr = new InputStreamReader(request.getInputStream(),"utf-8");
            String result = "";
            int respInt = insr.read();
            while(respInt!=-1) {
                 result +=(char)respInt;
                 respInt = insr.read();
             }
            EmployeesService employeesService = new EmployeesService();
            if(employeesService.updateEmployee(result))
                writer.write("上传成功");
            else
                writer.write("上传失败");
        }else if(change==null&&delete.equals("true")){
            InputStreamReader insr = new InputStreamReader(request.getInputStream(),"utf-8");
            String result = "";
            int respInt = insr.read();
            while(respInt!=-1) {
                result +=(char)respInt;
                respInt = insr.read();
            }
            EmployeesService employeesService = new EmployeesService();
            if(employeesService.deleteEmployee(result))
                writer.write("数据删除成功");
            else
                writer.write("数据删除失败");
        }
    }
}
