package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.RetailerToStaffDto;
import com.SCM.model.RetailerToStaff;

@Repository
public interface RetailerToStaffRepo extends JpaRepository<RetailerToStaff, Integer> {

	
	@Query(value = "SELECT rts.id,rts.aseid,rts.asmid,rts.rsmid,rts.nsmid,s.staff_name as asestaffname,s1.staff_name as asmstaffname,s2.staff_name as rsmstaffname,s3.staff_name as nsmstaffname\r\n"       
			+ "FROM retailer_to_staff rts\r\n"
			+ "LEFT join staff s on rts.aseid = s.id\r\n"
			+ "left join staff s1 on rts.asmid = s1.id\r\n"
			+ "left join staff s2 on rts.rsmid = s2.id\r\n"
			+ "left join staff s3 on rts.nsmid = s3.id "
			+ "WHERE retialer_id = ?1",nativeQuery = true)
	List<RetailerToStaffDto> getRetailerTostaffByRetailer(int id);
}
