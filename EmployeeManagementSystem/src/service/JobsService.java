package service;

import bean.Jobs;
import dao.JobsDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobsService {


    /**
     * 查询职位信息
     * @return
     */
    public List QuaryJobs(){
        JobsDao jobsDao=new JobsDao();
        List<Jobs> list=new ArrayList<>();
        ResultSet rs=jobsDao.QuaryJobs();
        try {
            while(rs.next()){
                Jobs jobs=new Jobs(rs.getInt("job_id"),rs.getString("name"),rs.getString("description"));
                list.add(jobs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 更新职位信息
     * @param change
     * @return
     */
    public boolean updateJobs(String change){
        List list = new ArrayList();
        try {
            JSONArray array=new JSONArray(change);
            for (int i = 0; i < array.length(); i++) {
                JSONObject Object=array.getJSONObject(i);
                Jobs jobs=new Jobs();
                jobs.setId(Object.getInt("id"));
                jobs.setName(Object.getString("name"));
                jobs.setDescription(Object.getString("description"));
                list.add(jobs);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JobsDao jobsDao=new JobsDao();
        return jobsDao.updateJobs(list);
    }
    public boolean deleteJobs(String delete){
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
        JobsDao jobsDao=new JobsDao();
        return jobsDao.deleteJob(list);
    }
}
