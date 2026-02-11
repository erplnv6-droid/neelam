package com.SCM.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SCM.model.StaffLocation;

public interface StaffLocationRepo extends JpaRepository<StaffLocation, Long>{

	
	// ======================= stafflocation update by staffid ===============
	
	@Transactional
	@Modifying
	@Query(value = "update staff_location set latitude=:latitude , longitude=:longitude"
			+ " where staff_id=:staffid",nativeQuery = true)
	public int updateStaffLocationWebSocket(
			@Param("latitude") Double latitude,
			@Param("longitude") Double longitude,
			@Param("staffid") Long staffid
			);
	
	
	Optional<StaffLocation> findByStaffId(Long staffId);

//	get data by staff id and date
	
	
	
	
	
}

