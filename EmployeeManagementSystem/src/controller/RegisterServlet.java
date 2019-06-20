package controller;




import service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer=response.getWriter();
        String user=request.getParameter("name");
        String pwd=request.getParameter("pwd");
        AdminService adminService=new AdminService();
        if(user.length()!=0&&pwd.length()!=0){
            if(!adminService.Register(user,pwd))
                writer.write("已存在该账号！");
            else
                writer.write("注册成功！");
        }else
            writer.write("账号或密码不能为空！");
    }
}
