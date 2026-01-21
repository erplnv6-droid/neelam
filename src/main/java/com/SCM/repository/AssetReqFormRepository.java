package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexAssetReqForm;
import com.SCM.model.AssetRequestForm;

public interface AssetReqFormRepository extends JpaRepository<AssetRequestForm, Long>{

	@Query(value = " select id as id,date as date,remarks as remarks,role as role,staffid as staffid,staffname as staffname,\r\n "
			+ " status as status from assetreqform ",
			nativeQuery = true)
	List<IndexAssetReqForm> indexAssetReqForm();
	
	
	@Query(value = " select id as id,date as date,remarks as remarks,role as role,staffid as staffid,staffname as staffname,createbyname as createbyname,DATE_FORMAT(createddate,'%d-%m-%Y') AS createddate,createdtime as createdtime\r\n "
			+ " ,status as status,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime as updatedtime,updatedbyname as updatedbyname from assetreqform ",
			nativeQuery = true)
	List<IndexAssetReqForm> indexAssetReqForm(Pageable p);
	

	@Query(value = " select id as id,date as date,remarks as remarks,role as role,staffid as staffid,staffname as staffname,\r\n "
			+ " status as status from assetreqform "
			+ " where id Like CONCAT('%', :search, '%')"
			+ "     or date Like CONCAT('%', :search, '%')" 
			+ "     or remarks Like CONCAT('%', :search, '%')" 
			+ "     or role Like CONCAT('%', :search, '%')" 
			+ "     or staffid Like CONCAT('%', :search, '%')" 
			+ "     or staffname Like CONCAT('%', :search, '%')"
			+ "     or status Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexAssetReqForm> indexAssetReqForm(Pageable p,String search);
	
	

	@Query(value = "SELECT arf.id,arf.date,arf.remarks,arf.role,arf.staffid,arf.staffname,arf.status \r\n"
			+ "FROM assetreqform arf\r\n"
			+ "WHERE arf.role = 'ROLE_RSM'",nativeQuery = true)
	List<IndexAssetReqForm> indexAssetReqFormByRsm(Pageable p);
	
	
	@Query(value = "SELECT arf.id,arf.date,arf.remarks,arf.role,arf.staffid,arf.staffname,arf.status \r\n"
			+ "FROM assetreqform arf\r\n"
			+ "WHERE arf.role = 'ROLE_ASM'",nativeQuery = true)
	List<IndexAssetReqForm> indexAssetReqFormByAsm(Pageable p);
	
	
	@Query(value = "SELECT arf.id,arf.date,arf.remarks,arf.role,arf.staffid,arf.staffname,arf.status \r\n"
			+ "FROM assetreqform arf\r\n"
			+ "WHERE arf.role = 'ROLE_ASE'",nativeQuery = true)
	List<IndexAssetReqForm> indexAssetReqFormByAse(Pageable p);
	
}
