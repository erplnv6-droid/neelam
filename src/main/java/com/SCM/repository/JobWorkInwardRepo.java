package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexJobworkInward;
import com.SCM.model.JobworkInward;

@Repository
public interface JobWorkInwardRepo extends JpaRepository<JobworkInward, Integer> {

	
	@Query(value = "SELECT ji.id,ji.grandtotal,DATE_FORMAT(ji.jobsheetdate,'%d-%m-%Y') AS jobsheetdate,ji.jobsheetno,ji.jobtype,ji.remarks,DATE_FORMAT(ji.createddate,'%d-%m-%Y') AS createddate,ji.createbyname,ji.createdtime,ji.updatedbyname,s.suppliername,w.name,DATE_FORMAT(ji.updateddate,'%d-%m-%Y') as updateddate,ji.updatedtime FROM jobwork_inward ji"
			+ " left join supplier s on ji.supplier_id = s.id"
			+ " left join warehouse w on ji.warehouse_id = w.id",nativeQuery = true)
	public List<IndexJobworkInward> IndexJobWorkInward(Pageable p);
	
	
	@Query(value = "SELECT ji.id,ji.grandtotal,DATE_FORMAT(ji.jobsheetdate,'%d-%m-%Y') AS jobsheetdate,ji.jobsheetno,ji.jobtype,ji.remarks,DATE_FORMAT(ji.createddate,'%d-%m-%Y') AS createddate,ji.createbyname,ji.createdtime,ji.updatedbyname,s.suppliername,w.name,DATE_FORMAT(ji.updateddate,'%d-%m-%Y') as updateddate,ji.updatedtime FROM jobwork_inward ji"
			+ "     left join supplier s on ji.supplier_id = s.id"
			+ "	    left join warehouse w on ji.warehouse_id = w.id"
			+ "     where ji.id Like CONCAT('%', :search, '%')"
			+ "     or ji.jobsheetdate Like CONCAT('%', :search, '%')"  
			+ "     or ji.jobsheetno Like CONCAT('%', :search, '%')"
			+ "     or ji.jobtype Like CONCAT('%', :search, '%')"
			+ "     or ji.remarks Like CONCAT('%', :search, '%')"
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.suppliername,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(w.name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))", nativeQuery = true)
	List<IndexJobworkInward> SearchByJobWorkInward(String search, Pageable p);
	
	
	JobworkInward findTopByOrderByIdDesc();
}
