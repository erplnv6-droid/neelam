package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexHoliday;
import com.SCM.dto.HolidayDto;
import com.SCM.model.Holiday;
import com.SCM.repository.HolidayRepo;
import com.SCM.service.HolidayService;
import com.google.common.base.Optional;

@Service
public class HolidayServiceImpl implements HolidayService {
	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public HolidayRepo holidayRepo;

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();

		return uid;
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	@Override
	public HolidayDto create(HolidayDto holidayDto) {
		
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		holidayDto.setCreatebyname(uname);
		holidayDto.setCreatedby(uid);
		holidayDto.setRole(role);
		holidayDto.setCreateddate(LocalDate.now());
		holidayDto.setCreatedtime(LocalTime.now());
		
		return this.holidayToHolidayDto(holidayRepo.save(this.holidayDtoToHoliday(holidayDto)));
	}

	
	@Override
	public HolidayDto update(HolidayDto holidayDto, int id) {
		
		Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
		Holiday newHoliday = holidayRepo.findById(id).get();
        newHoliday.setUpdatedby(uid);
        newHoliday.setUpdatedbyname(uname);
        newHoliday.setUpdatedrole(role);
        newHoliday.setUpdatedtime(LocalTime.now());
        newHoliday.setUpdateddate(LocalDate.now());
        
		return this
				.holidayToHolidayDto(holidayRepo.save(this.holidayDtoToHoliday(this.holidayToHolidayDto(newHoliday))));
	}

	public Holiday holidayDtoToHoliday(HolidayDto dto) {
		return modelMapper.map(dto, Holiday.class);
	}

	public HolidayDto holidayToHolidayDto(Holiday holiday) {
		return modelMapper.map(holiday, HolidayDto.class);
	}

	@Override
	public Map<String, Object> IndexAllHolidayAsc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		long countpages = holidayRepo.count();
		long pages = countpages / pagesize;

		List<IndexHoliday> listOfHoliday = holidayRepo.indexHoliday(p);
		response.put("Index", listOfHoliday);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;

	}

	@Override
	public Map<String, Object> IndexAllHolidayDesc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		long countpages = holidayRepo.count();
		long pages = countpages / pagesize;

		List<IndexHoliday> listOfHoliday = holidayRepo.indexHoliday(p);
		response.put("Index", listOfHoliday);
		response.put("Pages", pages);
		response.put("Enteries", countpages);

		return response;
	}

	@Override
	public Map<String, Object> SearchAllHoliday(int pageno, int pagesize, String search) {
		// TODO Auto-generated method stub

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);
		List<IndexHoliday> listOfHoliday = holidayRepo.indexHoliday(search, p);
		long searchcount = listOfHoliday.size();
		response.put("data", listOfHoliday);
		response.put("SearchCount", searchcount);

		return response;

	}

	@Override
	public java.util.Optional<Holiday> findById(int id) {
		// TODO Auto-generated method stub

		java.util.Optional<Holiday> holiday = holidayRepo.findById(id);
		return holiday;
	}

	@Override
	public String deleteById(int id) {
		// TODO Auto-generated method stub
		if (holidayRepo.existsById(id)) {
			holidayRepo.deleteById(id);
			return "Succesfully deleted the holiday with id " + id;
		}

		return " No holiday is present with id " + id;
	}

}
