package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.MinutesOfMeetingDto;
import com.SCM.model.MinutesOfMeeting;

public interface MinutesOfMeetingService {

	public MinutesOfMeeting save(MinutesOfMeetingDto minutesOfMeetingDto,String staffjson,String retailerjson,String distributorjson,String supplierjson,MultipartFile docfile) throws IOException;

	public List<MinutesOfMeeting> getAll();

	public MinutesOfMeeting getById(int id);
	
	public String delete(int id);

	public MinutesOfMeeting update(MinutesOfMeetingDto minutesOfMeetingDto,MultipartFile docfile,String staffjson,String retailerjson,String distributorjson,String supplierjson,int id) throws IOException;
	
	
	public byte[] downloadImages(String filename) throws IOException;
	
	public byte[] getImagebyId(int id) throws IOException;
	
//  pagination
    
  public Map<String, Object> IndexMOMAsc(int pageno,int pagesize,String field);
  
  public Map<String, Object> IndexMOMDesc(int pageno,int pagesize,String field);
  
  public Map<String, Object> SearchMOM(int pageno,int pagesize,String search);
}
