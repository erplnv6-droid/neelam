package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexAsset;
import com.SCM.model.Asset;


public interface AssetRepository extends JpaRepository<Asset, Long>{

	
	@Query(value = "select a.createddate,a.createdtime,a.createdbyname,a.id,a.assetsname,a.mrp,a.remarks,a.srno,a.status from asset a",nativeQuery = true)
	List<IndexAsset> indexassets();

	
	@Query(value = "select DATE_FORMAT(createddate,'%d-%m-%Y') AS createddate,createdtime as createdtime,createdbyname as createdbyname,updatedbyname as updatedbyname,id as id,assetsname as assetsname,mrp as mrp,remarks as remarks,srno as srno,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime as updatedtime,status as status  from asset \r\n",nativeQuery = true)
	List<IndexAsset> indexassets(Pageable p);
	
	
//	@Query(value = "select DATE_FORMAT(a.createddate,'%d-%m-%Y') AS createddate,a.createdtime,a.createdbyname,a.updatedbyname,a.id,a.assetsname,a.mrp,a.remarks,a.srno,DATE_FORMAT(a.updateddate,'%d-%m-%Y') as updateddate,a.updatedtime,a.status from asset a\r\n"
//			+ "	  where a.id Like CONCAT('%', :search, '%')"
//			+ "   or a.assetsname Like CONCAT('%', :search, '%')" 
//			+ "   or a.mrp Like CONCAT('%', :search, '%')" 
//			+ "   or a.remarks Like CONCAT('%', :search, '%')" 
//			+ "   or a.srno Like CONCAT('%', :search, '%')"
//			+ "   or a.createdbyname Like CONCAT('%', :search, '%')"
//			+ "   or a.updatedbyname Like CONCAT('%', :search, '%')"
//			+ "   or a.createddate Like CONCAT('%', :search, '%')"
//			+ "   or a.updateddate Like CONCAT('%', :search, '%')"
//			+ "   or a.status Like CONCAT('%', :search, '%')" 
//			,nativeQuery = true)
//	List<IndexAsset> searchassets(String search,Pageable p);
	
	
	@Query(value = "select DATE_FORMAT(a.createddate,'%d-%m-%Y') AS createddate,a.createdtime,a.createdbyname,a.updatedbyname,a.id,a.assetsname,a.mrp,a.remarks,a.srno,DATE_FORMAT(a.updateddate,'%d-%m-%Y') as updateddate,a.updatedtime,a.status from asset a\r\n"
			+ "	  where a.id Like CONCAT('%', :search, '%')"
			+ "   or REGEXP_LIKE(REGEXP_REPLACE(a.assetsname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ "   or a.mrp Like CONCAT('%', :search, '%')" 
			+ "   or a.remarks Like CONCAT('%', :search, '%')" 
			+ "   or a.srno Like CONCAT('%', :search, '%')"
			+ "   or a.createdbyname Like CONCAT('%', :search, '%')"
			+ "   or a.updatedbyname Like CONCAT('%', :search, '%')"
			+ "   or a.createddate Like CONCAT('%', :search, '%')"
			+ "   or a.updateddate Like CONCAT('%', :search, '%')"
			+ "   or a.status Like CONCAT('%', :search, '%')" 
			,nativeQuery = true)
	List<IndexAsset> searchassets(String search,Pageable p);
}
