package com.SCM.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.IndexDto.LocationIndex;
import com.SCM.dto.LocationDto;
import com.SCM.dto.LocationDto1;
import com.SCM.model.Attendence;
import com.SCM.model.Location;
import com.SCM.repository.LocationRepository;
import com.SCM.service.LocationService;

@RestController
@RequestMapping("/api/location")
public class LocationController {

	@Autowired
	public LocationService locationService;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveLocation(@RequestBody LocationDto locationDto)
	{
		return new ResponseEntity<>(locationService.saveLocation(locationDto),HttpStatus.CREATED);	
	}
	
	
	@PutMapping("/update/{id}")
	public String updateLocation(@PathVariable("id")int id,@RequestBody LocationDto locationDto)
	{
		locationService.updatecheckout(id,locationDto);
		return "upadte location";

	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getLocationById(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(locationService.getLocationById(id),HttpStatus.OK);	
	}
	
	@GetMapping("/staff/{staffid}")
	public ResponseEntity<?> getLocationByStaffId(@PathVariable("staffid") int staffid)
	{
		return new ResponseEntity<>(locationService.getLocationByStaffId(staffid),HttpStatus.OK);	
	}
	
	@DeleteMapping("/{id}")
	public String deleteLocationById(@PathVariable("id") int id)
	{
		locationService.deleteLocation(id);
		return "delete Location successfully";	
	}
	
	

	@DeleteMapping("/staff/{staffid}")
	public String deleteLocationByStffId(@PathVariable("staffid") int staffid)
	{
		locationService.deleteLocationByStaffId(staffid);
		return "delete Location successfully By Staff";	
	}
	
	
	
	
	@GetMapping("/staff/{staffid}/{currentdate}")
	public ResponseEntity<?> getMapdataByStaffandDate(@PathVariable("staffid") int staffid,@PathVariable("currentdate")String currentdate)
	{
		
		return new ResponseEntity<>(locationService.fetchlocationbystaffanddate(staffid,currentdate),HttpStatus.OK);	
	}
	
	
	@Autowired
	private LocationRepository locationRepository;
	
	
//	@GetMapping("/staff/{staffid}/{currentdate}")
//    public ResponseEntity<List<LocationIndex>> getLocationByStaffAndDate(
//            @PathVariable int staffid,
//            @PathVariable String currentdate) {
//System.out.println("oneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        List<LocationIndex> data =
//                locationRepository.Locationspecificbystaffanddate(staffid, currentdate);
//
////        if (data.isEmpty()) {
////            return ResponseEntity.noContent().build();
////        }
//
//        return ResponseEntity.ok(data);
//    }


	@GetMapping("/staff1/{staffid}/{currentdate}")
	public ResponseEntity<?> getMapSpecificdataByStaffandDate(
			 @PathVariable int staffid,
		     @PathVariable String currentdate   // "01-09-2023"
		  )
	{
		return new ResponseEntity<>(locationService.fetchspecificlocationbystaffanddate(staffid,currentdate),HttpStatus.OK);	
	}

	
	@GetMapping("/getalldata/{staffid}/{fromdate}/{todate}")
	public ResponseEntity<?> getAllStaffWithAttendanceVisit(@PathVariable("staffid")int staffid,
			                                                @PathVariable("fromdate")String fromdate,
			                                                @PathVariable("todate")String todate)
	{
		return new ResponseEntity<>(locationService.getAllAttendanceandlocationdata(staffid,fromdate,todate),HttpStatus.OK);	
	}
	
//	  @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{staffid}/{search}")
//		public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
//				                                  @PathVariable("pagesize") int pagesize,
//				                                  @PathVariable("sortby") String sortby,
//				                                  @PathVariable("field") String field,
//				                                  @PathVariable("staffid")int staffid,
//				                                  @PathVariable("search")String search) 
//		{   	
//	    
//	    	 if("asc".equals(sortby))
//			{ 
//				return new ResponseEntity<>(locationService.getAllAttendanceandlocationdata(pageno,pagesize,field,staffid), HttpStatus.OK);
//			}
//			return null;
//		}
	
	
	@GetMapping("/staff/location/{staffid}/{currentdate}")
	public ResponseEntity<?> getMapdataByStaffandDate1(@PathVariable("staffid") int staffid,
			                                           @PathVariable("currentdate")String currentdate)
	{					
		return new ResponseEntity<>(locationService.fetchlocationbystaffanddate1(staffid, currentdate),HttpStatus.OK);
	}
	
	
	@GetMapping("/locationstaff/staff/{staffId}")
    public ResponseEntity<Location> getLocation(
            @PathVariable int staffId,
            @RequestParam("date") String date) { // "dd-MM-yyyy HH:mm:ss"
System.out.println("helllloooooooooo");
        Optional<Location> locationOpt = locationService.getLocationByStaffAndDate(staffId, date);
        return locationOpt.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }
	
	
	@GetMapping("/currentStaffId/{staffId}/{dayin}")
	public ResponseEntity<?> getCurrentLocationofStaffId(@PathVariable int staffId,@PathVariable String dayin){
		Location location = locationService.getCurrentLocationByStaffId(staffId, dayin);
//		Attendence attendance = attendenceService.getCurrentAttendaceByStaffId(staffId,dayin);
		if (location != null) {
		    return ResponseEntity.ok(location);
		} else {
		    return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                         .body("OOPS!! Location not present !!!!!");
		}
	}
	
	
}
