package com.SCM.service; 

import java.util.List;
import java.util.Optional;

import com.SCM.IndexDto.LocationIndex;
import com.SCM.IndexDto.LocationWithAttendanceIndex;
import com.SCM.dto.LocationDto;
import com.SCM.dto.LocationDto1;
import com.SCM.model.Attendence;
import com.SCM.model.Location;

public interface LocationService {
    
	public Location saveLocation(LocationDto locationDto);
	
	public void updatecheckout(int id,LocationDto locationDto);
	
	public Location getLocationById(int id);
	
	public List<Location> getLocationByStaffId(int staffid);
	
	public void deleteLocation(int id);
	
	public void deleteLocationByStaffId(int staffid);
	
	public List<LocationDto1> fetchlocationbystaffanddate(int staffid,String currentdate);
	
	public List<LocationIndex> fetchspecificlocationbystaffanddate(int staffid,String currentdate);
	
//	public Map<String, Object> getAllAttendanceandlocationdata(int pagno,int pagesize,String field,int staffid);

	public List<com.SCM.dto.IndexStaffCheckinAndVisitData> getAllAttendanceandlocationdata(int staffid,String fromdate,String todate);
	
	
//	----
	
	public List<LocationWithAttendanceIndex> fetchlocationbystaffanddate1(int staffid,String currentdate);

	public Optional<Location> getLocationByStaffAndDate(int staffId, String currentDate);
	public Location getCurrentLocationByStaffId(int sid,String dayin);

//	public Attendence getCurrentLocationByStaffId(int sid,String dayin);	
}
