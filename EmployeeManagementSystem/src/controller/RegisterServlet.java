package controller;




import utils.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /*PrintWriter writer=response.getWriter();
        DBUtil util=DBUtil.getIstance();
        String user=request.getParameter("userName");
        String psd1=request.getParameter("password1");
        String psd2=request.getParameter("password2");
        if(user.length()!=0&&psd1.length()!=0&&psd2.length()!=0){
            *//*try {
                if(psd1.equals(psd2)) {
                    ResultSet rs=utils.select("select * from admin where account='"+user+"'");
                    if(rs.next()&&user.equals(rs.getString("account"))) {
                        writer.write("已存在该账号！<a href='Register.html'>点击跳转到注册页面</a>");
                    }else {
                        utils.upDate("insert into admin(account,password) values('" + user + "','" + psd1 + "')");
                        response.sendRedirect("index.html");
                    }
                }else
                    response.sendRedirect("Register.html");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*//*
        }else{
            response.sendRedirect("Register.html");
        }*/

    }
}
