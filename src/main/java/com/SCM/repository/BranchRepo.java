package com.SCM.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.SCM.IndexDto.IndexBranch;
import com.SCM.model.Branch;

@Repository
public interface BranchRepo extends JpaRepository<Branch, Integer>{

	
	@Query(value = "select b.id,b.branchname,b.address,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.email,b.gstno,b.createbyname,b.updatedbyname,sz.state_name,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime from branch b"
			+ "   left join state_zone sz on b.states_id = sz.id",nativeQuery = true)
	List<IndexBranch> indexBranch(Pageable p);
	
	
	
//	@Query(value = "select b.id,b.branchname,b.address,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.createbyname,b.updatedbyname,b.email,b.gstno,sz.state_name,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime FROM branch b"
//			+ "   left join state_zone sz on b.states_id = sz.id"
//			+ "   where b.branchname Like CONCAT('%', :search, '%')"
//			+ "     or b.address Like CONCAT('%', :search, '%')"
//			+ "     or b.email Like CONCAT('%', :search, '%')"  
//			+ "     or b.id Like CONCAT('%', :search, '%')"
//			+ "     or b.createbyname Like CONCAT('%', :search, '%')"
//			+ "     or b.updatedbyname Like CONCAT('%', :search, '%')"
//			+ "     or b.gstno Like CONCAT('%', :search, '%')"
//			+ "     or sz.state_name Like CONCAT('%', :search, '%')", nativeQuery = true)
//	List<IndexBranch> SearchByBranch(String search, Pageable p);
	
	@Query(value = "select b.id,b.branchname,b.address,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.createbyname,b.updatedbyname,b.email,b.gstno,sz.state_name,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime FROM branch b\r\n"
			+ " left join state_zone sz on b.states_id = sz.id\r\n"
			+ " where REGEXP_LIKE(REGEXP_REPLACE(b.branchname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or b.address Like CONCAT('%', :search, '%')\r\n"
			+ " or b.email Like CONCAT('%', :search, '%')\r\n"
			+ " or b.id Like CONCAT('%', :search, '%')\r\n"
			+ " or b.createbyname Like CONCAT('%', :search, '%')\r\n"
			+ " or b.updatedbyname Like CONCAT('%', :search, '%')\r\n"
			+ " or b.gstno Like CONCAT('%', :search, '%')\r\n"
			+ " or sz.state_name Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexBranch> SearchByBranch(String search, Pageable p);
}
