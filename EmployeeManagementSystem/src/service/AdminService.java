package service;

import bean.Admin;
import dao.AdminDao;

public class AdminService {

    /**
     * 是否能登录
     * @param accont
     * @param pwd
     * @return
     */
    public boolean isLogin(String accont,String pwd){
        Admin admin=new Admin(accont,pwd);
        AdminDao adminDao=new AdminDao();
        if(adminDao.isExists(admin))
            return true;
        return false;
    }

    public boolean Register(String accont,String pwd){
        Admin admin=new Admin(accont,pwd);
        AdminDao adminDao=new AdminDao();
        if(!adminDao.addAdmin(admin))
            return false;
        return true;
    }
}
