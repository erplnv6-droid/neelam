package com.SCM.serviceimpl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexStaffCheckinAndVisitData;
import com.SCM.IndexDto.IndexVisitData;
import com.SCM.IndexDto.LocationIndex;
import com.SCM.IndexDto.LocationWithAttendanceIndex;
import com.SCM.dto.LocationDto;
import com.SCM.dto.LocationDto1;
import com.SCM.model.ActivityLog;
import com.SCM.model.Attendence;
import com.SCM.model.Location;
import com.SCM.model.Staff;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.LocationRepository;
import com.SCM.repository.StaffRepo;
import com.SCM.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private StaffRepo staffRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Override
	public Location saveLocation(LocationDto locationDto) {

		Optional<Staff> s = staffRepo.findById((long) locationDto.getStaffId());
		Staff staff = s.get();

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		Location location = new Location();
		location.setCheckinLocationDate(dateTimeFormatter.format(localDateTime));
		location.setCheckinLocation(locationDto.getCheckinLocation());
		location.setCheckinLocationLatitude(locationDto.getCheckinLocationLatitude());
		location.setCheckinLocationLongitude(locationDto.getCheckinLocationLongitude());
		location.setRetailer(locationDto.getRetailer());
		location.setDistributor(locationDto.getDistributor());
		location.setCheckinLocationTime(LocalTime.now());

		location.setCreateddate(LocalDate.now());
		location.setCreatedtime(LocalTime.now());

		location.setStaff(staff);

		Location savelocation = locationRepository.save(location);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setLocationid((long) savelocation.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return savelocation;
	}

	@Override
	public Location getLocationById(int id) {

		Optional<Location> l = locationRepository.findById(id);

		Location location = l.get();

		return location;
	}

	@Override
	public void updatecheckout(int id, LocationDto locationDto) {

		Optional<Location> l = locationRepository.findById(id);

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Location location = l.get();
		location.setCheckoutLocation(locationDto.getCheckoutLocation());
		location.setCheckoutLocationDate(dateFormat.format(date));
		location.setCheckoutLocationLatitude(locationDto.getCheckoutLocationLatitude());
		location.setCheckoutLocationLongitude(locationDto.getCheckoutLocationLongitude());
		location.setCheckoutLocationTime(LocalTime.now());

		System.out.println(id);
		System.out.println(locationDto.getId());

		Location loc = locationRepository.save(location);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setLocationid((long) loc.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		locationRepository.save(location);
	}

	@Override
	public List<Location> getLocationByStaffId(int staffid) {

		return locationRepository.getLocationByStaffId(staffid);
	}

	@Override
	public void deleteLocation(int id) {
		locationRepository.deleteById(id);
	}

	@Override
	public void deleteLocationByStaffId(int staffid) {
		locationRepository.deleteByStaffId(staffid);

	}

	@Override 
	public List<LocationDto1> fetchlocationbystaffanddate(int staffid, String currentdate) {

		List<LocationDto1> locationdatabystaffanddate = locationRepository.Locationdatabystaffanddate(staffid, currentdate);

		return locationdatabystaffanddate;
	}
	
	
	

	@Override
	public List<LocationIndex> fetchspecificlocationbystaffanddate(int staffid, String currentdate) {

		List<LocationIndex> locationspecificbystaffanddate = locationRepository.Locationspecificbystaffanddate(staffid,currentdate);

		return locationspecificbystaffanddate;
	}
	
	
	

//	@Override
//	public List<com.SCM.dto.IndexStaffCheckinAndVisitData> getAllAttendanceandlocationdata(int staffid,String fromdate,String todate) {
//	
//		List<IndexStaffCheckinAndVisitData> staffCheckinAndVisitData = locationRepository.getStaffCheckinAndVisitData(staffid,fromdate,todate);
//		
//		List<com.SCM.dto.IndexStaffCheckinAndVisitData> list = new ArrayList<>();
//		
//		com.SCM.dto.IndexStaffCheckinAndVisitData i = new com.SCM.dto.IndexStaffCheckinAndVisitData();
//		
////		locationRepository.getStaffAndVisitById(staffCheckinAndVisitData.get(staffid).getstaffid());
//		
//		i.setStaffid((long) staffCheckinAndVisitData.get(0).getstaffid());
//		i.setStaffName(staffCheckinAndVisitData.get(0).getstaff_name());
//		i.setDayintime(staffCheckinAndVisitData.get(0).getdayintime());
////		i.setVisit(staffAndVisitById);
//		
//		list.add(i);
//		
//		return list;
//	}

	
	@Override
	public List<com.SCM.dto.IndexStaffCheckinAndVisitData> getAllAttendanceandlocationdata(int staffid,String fromdate,String todate) {

		List<IndexStaffCheckinAndVisitData> staffCheckinAndVisitData = locationRepository.getStaffCheckinAndVisitData(staffid,fromdate,todate);

		List<com.SCM.dto.IndexStaffCheckinAndVisitData> list = new ArrayList<>();

		com.SCM.dto.IndexStaffCheckinAndVisitData i = new com.SCM.dto.IndexStaffCheckinAndVisitData();

		for (IndexStaffCheckinAndVisitData ii : staffCheckinAndVisitData) {

			List<IndexVisitData> staffAndVisitById = locationRepository.getStaffAndVisitById(ii.getstaffid(),fromdate,todate);

			i.setStaffid((long) ii.getstaffid());
			i.setStaffName(ii.getstaff_name());
			i.setDayintime(ii.getdayintime());
			i.setVisit(staffAndVisitById);
		}

		list.add(i);

		return list;
	}

	
	
//	@Override
//	public List<LocationWithAttendanceIndex> fetchlocationbystaffanddate1(int staffid, String currentdate) {
//		
//		List<LocationWithAttendanceIndex> locationdatabystaffanddate1 = locationRepository.Locationdatabystaffanddate1(staffid, currentdate);
//		
//		
//		return locationdatabystaffanddate1;
//	}
	
	@Override
	public List<LocationWithAttendanceIndex> fetchlocationbystaffanddate1(int staffid, String currentdate) {

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    LocalDate date = LocalDate.parse(currentdate, formatter);

	    String mysqlDate = date.toString(); // yyyy-MM-dd

	    return locationRepository.Locationdatabystaffanddate1(staffid, mysqlDate);
	}
//	@Override
//	  public List<Location> getLocationByStaffAndDate(int staffId, String currentDate) {
//	        // currentDate expected in "dd-MM-yyyy HH:mm:ss" format
//	        return locationRepository.findLocationByStaffAndDate(staffId, currentDate);
//	    }
	
	
	@Override
	public Optional<Location> getLocationByStaffAndDate(int staffId, String currentDate) {
	    return locationRepository.findTopLocationByStaffAndDate(staffId, currentDate);
	}


	@Override
	public Location getCurrentLocationByStaffId(int sid, String dayin) {
		 return locationRepository.findTopLocationByStaffAndDate(sid, dayin)
		            .orElse(null);
	}


}
