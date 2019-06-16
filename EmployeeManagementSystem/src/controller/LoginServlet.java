package controller;

import bean.Admin;
import service.AdminService;
import utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String user=request.getParameter("name");
        String pwd=request.getParameter("pwd");
        PrintWriter writer=response.getWriter();
        AdminService adminService=new AdminService();
        if(adminService.isLogin(user,pwd))
            writer.println(true);
        else
            writer.println(false);
    }
}
