package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.JobworkOutwardDto;
import com.SCM.model.Jobworkoutward;

public interface JobworkoutwardService {

	  public Jobworkoutward saveJobWorkOutward(JobworkOutwardDto jobworkOutwardDto);

	  public List<Jobworkoutward> getAllJobWorkOutward();

      public Jobworkoutward getJobWorkOutwardById(int id);

      public String deleteJobWorkOutward(int id);

      public Jobworkoutward updateJobWorkOutward(JobworkOutwardDto jobworkOutwardDto, int id);
	    
	    
	 //  index JobWorkOutward
	    
	    
	    public Map<String, Object> IndexJobWorkOutwardAsc(int pageno,int pagesize,String field);
	    
	    public Map<String, Object> IndexJobWorkOutwardDesc(int pageno,int pagesize,String field);
	    
	    public Map<String, Object> SearchByJobWorkOutward(int pageno,int pagesize,String search);
}
