package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.MultipleToStaffDto;
import com.SCM.model.MultipleStaff;

@Repository
public interface MultipleToStaffRepo extends JpaRepository<MultipleStaff, Integer> {

	
	@Query(value = "select mts.id,mts.nsmid,mts.rsmid,mts.asmid,mts.aseid,s.staff_name as rsmstaffname,s1.staff_name as asmstaffname,s2.staff_name as asestaffname,s3.staff_name as nsmstaffname\r\n"
			+ "from multiple_staff mts\r\n"
			+ "LEFT JOIN staff s on mts.rsmid = s.id\r\n"
			+ "LEFT JOIN staff s1 ON mts.asmid = s1.id\r\n"
			+ "LEFT JOIN staff s2 ON mts.aseid = s2.id\r\n"
			+ "LEFT JOIN staff s3 ON mts.nsmid = s3.id\r\n"
			+ "WHERE mts.staff_id = ?1",nativeQuery = true)
	List<MultipleToStaffDto> multipleToStaffDtos(int sid);
}
