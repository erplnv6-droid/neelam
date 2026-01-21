package com.SCM.repository;


import com.SCM.IndexDto.IndexBrand;
import com.SCM.model.Brand;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepo extends JpaRepository<Brand,Long> {
	
	
	@Query(value = "SELECT b.id,b.brand_name,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.createbyname,b.updatedbyname,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime\r\n"
			+ "			from brand b ",nativeQuery = true)
	List<IndexBrand> indexBrand(Pageable p);
	
	@Query(value = "SELECT b.id,b.brand_name,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.createbyname,b.updatedbyname,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime\r\n"
			+ "			from brand b ",nativeQuery = true)
	List<Map<String, Object>> indexBrand1(Pageable p);
	
	

//	@Query(value = "select b.id,b.brand_name,b.createddate,b.createdtime FROM brand b where b.brand_name Like CONCAT('%', :search, '%')"
//			+ "or b.id Like CONCAT('%', :search, '%')", nativeQuery = true)
//	List<Brand> SearchByBrand(String search, Pageable p);

//	
//	@Query(value = "SELECT b.id, b.brand_name\r\n"
//			+ "FROM brand b\r\n"
//			+ "WHERE REGEXP_LIKE(REGEXP_REPLACE(b.brand_name, '[^a-zA-Z0-9]', ''), REGEXP_REPLACE(CONCAT('%', :search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
//			+ "OR b.id Like CONCAT('%', :search, '%')\r\n"
//			+ "OR b.createbyname Like CONCAT('%', :search, '%')\r\n"
//			+ "OR b.updatedbyname Like CONCAT('%', :search, '%')\r\n"
//			+ "OR b.updateddate Like CONCAT('%', :search, '%')\r\n"
//			+ "OR b.createddate Like CONCAT('%', :search, '%')", nativeQuery = true)
//	List<IndexBrand> SearchByBrand(String search, Pageable p);
	
	@Query(value = "SELECT b.id,b.brand_name,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.createbyname,b.updatedbyname,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime"
			+ " FROM brand b "
			+ " WHERE REGEXP_LIKE(REGEXP_REPLACE(b.brand_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " OR b.id Like CONCAT('%',:search, '%')"
			+ " OR b.createbyname Like CONCAT('%', :search, '%')"
			+ " OR b.updatedbyname Like CONCAT('%', :search, '%')"
			+ " OR b.updateddate Like CONCAT('%', :search, '%')"
			+ " OR b.createddate Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexBrand> SearchByBrand(String search, Pageable p);

	
	@Query(value = "select b.id,b.brand_name,DATE_FORMAT(b.createddate,'%d-%m-%Y') AS createddate,b.createdtime,b.createbyname,b.updatedbyname,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime FROM brand b "
			+ " where b.brand_name Like CONCAT('%', :search, '%')"
			+ " or b.id Like CONCAT('%', :search, '%')"
			+ " or b.createbyname Like CONCAT('%', :search, '%')"
			+ " or b.updatedbyname Like CONCAT('%', :search, '%')"
			+ " or b.updateddate Like CONCAT('%', :search, '%')"
			+ " or b.createddate Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<Map<String, Object>> SearchByBrandMap(String search, Pageable p);

}
