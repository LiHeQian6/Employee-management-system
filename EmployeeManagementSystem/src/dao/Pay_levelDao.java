package dao;

import bean.Pay_level;
import utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Pay_levelDao {

    public ResultSet QuaryPay_level(){
        DBUtil dbUtil=DBUtil.getIstance();
        return dbUtil.query("select * from pay_level");
    }
    public boolean updatePay_level(List<Pay_level> list){
        int n=0;
        DBUtil dbUtil=DBUtil.getIstance();
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getLevel_id()==-1){
                n=dbUtil.upDate("insert into pay_level (name, base_pay) values ('"+list.get(i).getName()+"',"+list.get(i).getBase_pay()+");");
            }else{
                n=dbUtil.upDate("update pay_level set name='"+list.get(i).getName()+"',base_pay="+list.get(i).getBase_pay()+" where level_id="+list.get(i).getLevel_id()+";");
            }
            if(n==0)
                return false;
        }
        return true;
    }
    public String deletePay_level(List id){
        int n=0;
        String r="";
        DBUtil dbUtil=DBUtil.getIstance();
        for (int i = 0; i <id.size() ; i++) {
            n=dbUtil.upDate("delete from pay_level where level_id="+id.get(i)+";");
            if(n==0) {
                ResultSet rs=dbUtil.query("select * from pay_level where level_id="+id.get(i)+";");
                try {
                    rs.next();
                    r=r+rs.getString("name")+" ";
                    continue;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return r;
    }
}
