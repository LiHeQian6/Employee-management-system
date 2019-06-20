package service;


import bean.Pay_level;
import dao.Pay_levelDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * 更新薪资水平信息
     * @param change
     * @return
     */
    public boolean updatePay_level(String change){
        List list = new ArrayList();
        try {
            JSONArray array=new JSONArray(change);
            for (int i = 0; i < array.length(); i++) {
                JSONObject Object=array.getJSONObject(i);
                Pay_level pay_level=new Pay_level();
                pay_level.setLevel_id(Object.getInt("id"));
                pay_level.setName(Object.getString("name"));
                pay_level.setBase_pay(Object.getDouble("base_pay"));
                list.add(pay_level);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Pay_levelDao jobsDao=new Pay_levelDao();
        return jobsDao.updatePay_level(list);
    }
    public String deletePay_level(String delete){
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
        Pay_levelDao jobsDao=new Pay_levelDao();
        return jobsDao.deletePay_level(list);
    }

}
