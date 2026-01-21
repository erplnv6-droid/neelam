package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexJobworkInward;
import com.SCM.model.JobworkInward;
import com.SCM.model.Jobworkoutward;

@Repository
public interface JobworkoutwardRepo extends JpaRepository<Jobworkoutward, Integer> {

	
	@Query(value = "SELECT jo.id,jo.grandtotal,DATE_FORMAT(jo.jobsheetdate,'%d-%m-%Y') AS jobsheetdate,jo.jobsheetno,jo.jobtype,jo.remarks,jo.createdtime,s.suppliername,w.name,DATE_FORMAT(jo.createddate,'%d-%m-%Y') AS createddate,jo.createbyname,jo.updatedbyname,DATE_FORMAT(jo.updateddate,'%d-%m-%Y') as updateddate,jo.updatedtime"
			+ " FROM jobworkoutward jo"
			+ " left join supplier s on jo.supplier_id = s.id"
			+ " left join warehouse w on jo.warehouse_id = w.id",nativeQuery = true)
	public List<IndexJobworkInward> IndexJobWorkOutward(Pageable p);
	
	

	@Query(value = "SELECT jo.id,jo.grandtotal,DATE_FORMAT(jo.jobsheetdate,'%d-%m-%Y') AS jobsheetdate,jo.jobsheetno,jo.jobtype,jo.remarks,jo.createdtime,s.suppliername,w.name,DATE_FORMAT(jo.createddate,'%d-%m-%Y') AS createddate,jo.createbyname,jo.updatedbyname,DATE_FORMAT(jo.updateddate,'%d-%m-%Y') as updateddate,jo.updatedtime"
			+ " FROM jobworkoutward jo"
			+ " left join supplier s on jo.supplier_id = s.id"
			+ " left join warehouse w on jo.warehouse_id = w.id"
			+ " where jo.id Like CONCAT('%', :search, '%')"
			+ " or jo.jobsheetdate Like CONCAT('%', :search, '%')"
			+ " or jo.jobsheetno Like CONCAT('%', :search, '%')"
			+ " or jo.jobtype Like CONCAT('%', :search, '%')"
			+ " or jo.remarks Like CONCAT('%', :search, '%')"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.suppliername,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(w.name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))", nativeQuery = true)
	List<IndexJobworkInward> SearchByJobWorkOutward(String search, Pageable p);
	
	
	Jobworkoutward findTopByOrderByIdDesc();
	
}
