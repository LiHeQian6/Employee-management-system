package service;

import bean.Jobs;
import dao.JobsDao;

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
}
