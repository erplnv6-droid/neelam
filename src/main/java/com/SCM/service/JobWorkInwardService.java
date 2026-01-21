package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.JobworkInwardDto;
import com.SCM.model.JobworkInward;


public interface JobWorkInwardService {

    public JobworkInward saveJobWorkInward(JobworkInwardDto jobworkInwardDto);

    public List<JobworkInward> getAllJobWorkInward();

    public JobworkInward getJobWorkInwardById(int id);

    public String deleteJobWorkInward(int id);

    public JobworkInward updateJobWorkInward(JobworkInwardDto jobworkInwardDto, int id);
    
    
 //  index JobWorkInward
    
    
    public Map<String, Object> IndexJobWorkInwardAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexJobWorkInwardDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchByJobWorkInward(int pageno,int pagesize,String search);
}
