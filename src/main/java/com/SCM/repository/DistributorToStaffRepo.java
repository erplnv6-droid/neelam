package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.DistributorToStaffDto;
import com.SCM.model.DistributorToStaff;

@Repository
public interface DistributorToStaffRepo extends JpaRepository<DistributorToStaff, Integer> {

	
	@Query(value = "SELECT dts.id,dts.aseid,dts.asmid,dts.rsmid,dts.nsmid,s.staff_name as asestaffname,s1.staff_name as asmstaffname,s2.staff_name as rsmstaffname,s3.staff_name as nsmstaffname     \r\n"
			+ "FROM distributor_to_staff dts\r\n"
			+ "LEFT join staff s on dts.aseid = s.id\r\n"
			+ "left join staff s1 on dts.asmid = s1.id\r\n"
			+ "left join staff s2 on dts.rsmid = s2.id\r\n"
			+ "left join staff s3 on dts.nsmid = s3.id\r\n"
			+ "WHERE distributor_id = ?1",nativeQuery = true)
	List<DistributorToStaffDto> getDistributorTostaffByDistributor(int id);
}
