package com.SCM.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexHoliday;
import com.SCM.model.Holiday;

public interface HolidayRepo extends JpaRepository<Holiday, Integer> {
	
	@Query(value = "select * from holiday where date between ?1 and last_day(?1)", nativeQuery = true)
	List<Holiday> getAllHolidaysByDate(String date);
	
	@Query(value="select h.id as id,h.date as date,h.occasion,DATE_FORMAT(h.createddate,'%d-%m-%Y') AS createddate,h.createdtime,h.createbyname,h.updatedbyname,DATE_FORMAT(h.updateddate,'%d-%m-%Y') AS updateddate,h.updatedtime from holiday h",nativeQuery = true)
	List<IndexHoliday> indexHoliday();
	
	@Query(value="select h.id as id,h.date as date,h.occasion,DATE_FORMAT(h.createddate,'%d-%m-%Y') AS createddate,h.createdtime,h.createbyname,h.updatedbyname,DATE_FORMAT(h.updateddate,'%d-%m-%Y') AS updateddate,h.updatedtime from holiday h",nativeQuery = true)
	List<IndexHoliday> indexHoliday(org.springframework.data.domain.Pageable p);
	
	
	@Query(value="select h.id as id,h.date as date,h.occasion  from holiday h"
			+ " where h.id Like CONCAT('%', :search, '%')"
			+ " or h.date Like CONCAT('%', :search, '%')"
			+ " or h.occasion Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexHoliday> indexHoliday(String search, org.springframework.data.domain.Pageable p);
	
	
}





