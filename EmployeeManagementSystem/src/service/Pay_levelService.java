package service;


import bean.Pay_level;
import dao.Pay_levelDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pay_levelService {

    /**
     * 查询薪资信息
     * @return
     */
    public List QuaryPay_level(){
        Pay_levelDao pay_levelDao=new Pay_levelDao();
        List<Pay_level> list=new ArrayList<>();
        ResultSet rs=pay_levelDao.QuaryPay_level();
        try {
            while(rs.next()){
                Pay_level pay_level=new Pay_level(rs.getInt("level_id"),rs.getString("name"),rs.getDouble("base_pay"));
                list.add(pay_level);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
