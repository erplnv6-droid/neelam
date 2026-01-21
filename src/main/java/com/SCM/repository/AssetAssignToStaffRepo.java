package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexAssetAssignToStaff;
import com.SCM.model.AssetAssignToStaff;

public interface AssetAssignToStaffRepo extends JpaRepository<AssetAssignToStaff, Long>{

	
	@Query(value="select id as id,createdbyid as createdbyid,createbyname as createbyname,createddate as createddate,\r\n"
			+ "remarks as remarks,staffid as staffid,assetid as assetid from assetassigntostaff",
			nativeQuery = true)
	List<IndexAssetAssignToStaff> indexAssetAssignToStaff();
	
	
//	@Query(value="select id as id,createdbyid as createdbyid,createbyname as createbyname,createddate as createddate,\r\n"
//			+ "remarks as remarks,staffid as staffid,assetid as assetid from assetassigntostaff",
//			nativeQuery = true)
//	List<IndexAssetAssignToStaff> indexAssetAssignToStaffNew();
	
	
	@Query(value="SELECT ats.id ,DATE_FORMAT(ats.createddate,'%d-%m-%Y') AS createddate,ats.createdtime,ats.createbyname,ats.updatedbyname,DATE_FORMAT(ats.updateddate,'%d-%m-%Y') as updateddate,ats.updatedtime,s.staff_name,\r\n"
			+ "ats.remarks,a.assetsname,a.expiry_date,a.insuranceprovidername,sp.suppliername,sp.companyname from assetassigntostaff ats\r\n"
			+ "LEFT JOIN staff s ON ats.staffid = s.id\r\n"
			+ "LEFT JOIN asset a ON ats.assetid = a.id\r\n"
			+ "LEFT JOIN supplier sp ON a.supplier_id = sp.id",
			nativeQuery = true)
	List<IndexAssetAssignToStaff> indexAssetAssignToStaff(Pageable p);
	
	
//	@Query(value="SELECT ats.id,DATE_FORMAT(ats.createddate,'%d-%m-%Y') AS createddate,ats.createdtime,ats.createbyname,ats.updatedbyname,DATE_FORMAT(ats.updateddate,'%d-%m-%Y') as updateddate,ats.updatedtime,s.staff_name,"
//			+ "	ats.remarks,a.assetsname,a.expiry_date,a.insuranceprovidername,sp.suppliername from assetassigntostaff ats"
//			+ "	LEFT JOIN staff s ON ats.staffid = s.id"
//			+ "	LEFT JOIN asset a ON ats.assetid = a.id"
//			+ "	LEFT JOIN supplier sp ON a.supplier_id = sp.id"
//			+ " WHERE ats.id Like CONCAT('%', :search, '%')"
//			+ "   or ats.createdbyname Like CONCAT('%', :search, '%')" 
//			+ "   or ats.createddate Like CONCAT('%', :search, '%')" 
//			+ "   or ats.remarks Like CONCAT('%', :search, '%')"
//			+ "   or a.assetsname Like CONCAT('%', :search, '%')"
//			+ "   or a.expiry_date Like CONCAT('%', :search, '%')" 
//			+ "   or s.staff_name Like CONCAT('%', :search, '%')"
//			+ "   or a.insuranceprovidername Like CONCAT('%', :search, '%')"
//			+ "   or sp.suppliername Like CONCAT('%', :search, '%')",
//			nativeQuery = true)
//	List<IndexAssetAssignToStaff> indexAssetAssignToStaff(Pageable p,String search);

	
	@Query(value="SELECT ats.id,DATE_FORMAT(ats.createddate,'%d-%m-%Y') AS createddate,ats.createdtime,ats.createbyname,ats.updatedbyname,DATE_FORMAT(ats.updateddate,'%d-%m-%Y') as updateddate,ats.updatedtime,s.staff_name,"
			+ "	ats.remarks,a.assetsname,a.expiry_date,a.insuranceprovidername,sp.suppliername,sp.companyname from assetassigntostaff ats"
			+ "	LEFT JOIN staff s ON ats.staffid = s.id"
			+ "	LEFT JOIN asset a ON ats.assetid = a.id"
			+ "	LEFT JOIN supplier sp ON a.supplier_id = sp.id"
			+ " WHERE ats.id Like CONCAT('%', :search, '%')"
			+ "   or ats.createdbyname Like CONCAT('%', :search, '%')" 
			+ "   or ats.createddate Like CONCAT('%', :search, '%')" 
			+ "   or ats.remarks Like CONCAT('%', :search, '%')"
			+ "   OR REGEXP_LIKE(REGEXP_REPLACE(a.assetsname,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   or a.expiry_date Like CONCAT('%', :search, '%')" 
			+ "   or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "   or a.insuranceprovidername Like CONCAT('%', :search, '%')"
			+ "   or sp.suppliername Like CONCAT('%', :search, '%')"
			+ "   or sp.companyname Like CONCAT('%', :search, '%')",
			nativeQuery = true)
	List<IndexAssetAssignToStaff> indexAssetAssignToStaff(Pageable p,String search);
	
}
