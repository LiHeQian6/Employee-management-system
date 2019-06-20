package controller;

import org.json.JSONArray;
import service.EmployeesService;
import service.Pay_levelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/SalaryServlet")
public class SalaryServlet extends HttpServlet {
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
            Pay_levelService pay_levelService=new Pay_levelService();
            List list=pay_levelService.QuaryPay_level();
            JSONArray jsonArray=new JSONArray(list);
            writer.write(jsonArray.toString());
        }else if(delete==null&&change.equals("true")) {
            InputStreamReader insr = new InputStreamReader(request.getInputStream(),"utf-8");
            String result = "";
            int respInt = insr.read();
            while(respInt!=-1) {
                result +=(char)respInt;
                respInt = insr.read();
            }
            Pay_levelService pay_levelService = new Pay_levelService();
            if(pay_levelService.updatePay_level(result))
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
            Pay_levelService pay_levelService = new Pay_levelService();
            String r=pay_levelService.deletePay_level(result);
            if(r.equals(""))
                writer.write("数据删除成功");
            else
                writer.write("数据"+r+"删除失败,请确认没有员工属于该薪资水平！");
        }
    }
}
