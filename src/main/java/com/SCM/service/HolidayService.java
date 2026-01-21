package com.SCM.service;

import java.util.Map;

import com.SCM.dto.HolidayDto;
import com.SCM.model.Holiday;
import com.google.common.base.Optional;


public interface HolidayService {
	public HolidayDto create(HolidayDto holiday);

	public HolidayDto update(HolidayDto holidayDto, int id);
	
	public java.util.Optional<Holiday> findById(int id);
	
	public String deleteById(int id);
	
	
	  public Map<String, Object> IndexAllHolidayAsc(int pageno,int pagesize,String field);
	    
	    public Map<String, Object> IndexAllHolidayDesc(int pageno,int pagesize,String field);
	    
	    public Map<String, Object> SearchAllHoliday(int pageno,int pagesize,String search);
	
	
	
}
