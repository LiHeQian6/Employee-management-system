package dao;

import bean.Jobs;
import utils.DBUtil;

import java.sql.ResultSet;
import java.util.List;

public class JobsDao {

    public ResultSet QuaryJobs(){
        DBUtil dbUtil=DBUtil.getIstance();
        return dbUtil.query("select * from jobs");
    }
    public boolean updateJobs(List<Jobs> list){
        int n=0;
        DBUtil dbUtil=DBUtil.getIstance();
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getId()==-1){
                n=dbUtil.upDate("insert into jobs (name, description) values ('"+list.get(i).getName()+"','"+list.get(i).getDescription()+"');");
            }else{
                n=dbUtil.upDate("update jobs set name='"+list.get(i).getName()+"',description='"+list.get(i).getDescription()+"' where job_id="+list.get(i).getId()+";");
            }
            if(n==0)
                return false;
        }
        return true;
    }
    public boolean deleteJob(List id){
        int n=0;
        DBUtil dbUtil=DBUtil.getIstance();
        for (int i = 0; i <id.size() ; i++) {
            n=dbUtil.upDate("delete from jobs where job_id="+id.get(i)+";");
            if(n==0)
                return false;
        }
        return true;
    }
}
