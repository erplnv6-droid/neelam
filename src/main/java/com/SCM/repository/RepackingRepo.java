package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexRepackingPagination;
import com.SCM.model.Repacking;

@Repository
public interface RepackingRepo extends JpaRepository<Repacking, Long> {
	
	
	@Query(value = "select rp.id,rp.brandname,rp.operatorname,rp.createdtime,DATE_FORMAT(rp.createddate,'%d-%m-%Y') AS createddate,rpi.productname\r\n"
			+ "from repacking rp\r\n"
			+ "LEFT JOIN repacking_items rpi on rpi.repackid = rp.id", nativeQuery = true)
	List<IndexRepackingPagination> indexRepackingPaginations(Pageable p);
	
	
	
	@Query(value = "select rp.id,rp.brandname,rp.operatorname,rp.createdtime,DATE_FORMAT(rp.createddate,'%d-%m-%Y') AS createddate,rpi.productname\r\n"
			+ "from repacking rp\r\n"
			+ "LEFT JOIN repacking_items rpi on rpi.repackid = rp.id"
			+ " WHERE rp.id Like CONCAT('%', :search, '%')"
				+ "     or rp.operatorname Like CONCAT('%', :search, '%')"
				+ "     or rp.brandname Like CONCAT('%', :search, '%')"  
				+ "     or rp.createddate Like CONCAT('%', :search, '%')"
				+ "     or REGEXP_LIKE(REGEXP_REPLACE(rpi.productname,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))", nativeQuery = true)
	List<IndexRepackingPagination> indexRepackingPaginations(String search,Pageable p);

}
