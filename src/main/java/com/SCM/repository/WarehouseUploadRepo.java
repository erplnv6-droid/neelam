package com.SCM.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.PreIndex;
import com.SCM.model.Warehouse;
import com.SCM.model.WarehouseUpload;


@Repository
public interface WarehouseUploadRepo extends JpaRepository<WarehouseUpload, Integer> {

	
	WarehouseUpload findFirstByOrderByWarehouseIdDesc();
	
//	================= index excel warehouse 
	
	@Query(value = "select weu.id,wu.pdate,w.name,weu.productname,weu.dlpkg,weu.dlppcs\r\n"
			+ "from warehouse_excel_upload weu\r\n"
			+ "LEFT JOIN warehouse_upload wu on weu.warehouseupload_id = wu.id\r\n"
			+ "LEFT JOIN warehouse w on wu.warehouse_id = w.id",nativeQuery = true)
	List<PreIndex> IndexPrimary(Pageable p);
	
	
	@Query(value = "select weu.id,wu.pdate,w.name,weu.productname,weu.dlpkg,weu.dlppcs\r\n"
			+ "from warehouse_excel_upload weu\r\n"
			+ "LEFT JOIN warehouse_upload wu on weu.warehouseupload_id = wu.id\r\n"
			+ "LEFT JOIN warehouse w on wu.warehouse_id = w.id"
			+ " WHERE weu.id Like CONCAT('%', :search, '%')"
			+ " OR wu.pdate Like CONCAT('%', :search, '%')"
			+ " OR w.name Like CONCAT('%', :search, '%')"
			+ " OR weu.productname Like CONCAT('%', :search, '%')"
			+ " OR weu.dlpkg Like CONCAT('%', :search, '%')"
			+ " OR weu.dlppcs Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<PreIndex> SearchByPrimary(String search, Pageable p);
	
	
	
	@Query(value = "SELECT weu.id,wu.pdate,w.name,weu.productname,weu.dlpkg,weu.dlppcs\r\n"
			+ "FROM warehouse_excel_upload weu\r\n"
			+ "LEFT JOIN warehouse_upload wu ON weu.warehouseupload_id = wu.id\r\n"
			+ "LEFT JOIN warehouse w ON wu.warehouse_id = w.id\r\n"
			+ "WHERE wu.warehouse_id = :wid AND wu.pdate = :wdate",nativeQuery = true)
	List<PreIndex> GetByWarehouseIdandDate(int wid,String wdate);
	
	
//	================= index excel warehouse  by warehouse id
	
	@Query(value = "SELECT weu.id,wu.pdate,w.name,weu.productname,weu.dlpkg,weu.dlppcs\r\n"
			+ "FROM warehouse_excel_upload weu\r\n"
			+ "LEFT JOIN warehouse_upload wu ON weu.warehouseupload_id = wu.id\r\n"
			+ "LEFT JOIN warehouse w ON wu.warehouse_id = w.id\r\n"
			+ "WHERE wu.warehouse_id = :wid",nativeQuery = true)
	List<PreIndex> IndexExcelByWarehouseIdandDate(Pageable p,int wid);
	
	
	@Query(value = "select weu.id,wu.pdate,w.name,weu.productname,weu.dlpkg,weu.dlppcs\r\n"
			+ "from warehouse_excel_upload weu\r\n"
			+ "LEFT JOIN warehouse_upload wu on weu.warehouseupload_id = wu.id\r\n"
			+ "LEFT JOIN warehouse w on wu.warehouse_id = w.id"
			+ " WHERE(wu.warehouse_id = :wid)"
			+ " AND (weu.id Like CONCAT('%', :search, '%')"
			+ " OR wu.pdate Like CONCAT('%', :search, '%')"
			+ " OR w.name Like CONCAT('%', :search, '%')"
			+ " OR weu.productname Like CONCAT('%', :search, '%')"
			+ " OR weu.dlpkg Like CONCAT('%', :search, '%')"
			+ " OR weu.dlppcs Like CONCAT('%', :search, '%'))", nativeQuery = true)
	List<PreIndex> SearchByExcelByWarehouseIdandDate(String search,Pageable p,int wid);
		

	@Modifying
	@Transactional
	@Query(value = "DELETE weu FROM warehouse_excel_upload weu\r\n"
			+ "LEFT JOIN warehouse_upload wu on weu.warehouseupload_id = wu.id\r\n"
			+ "WHERE wu.warehouse_id = ?1",nativeQuery = true)
	void deleteExcelUploadByWarehouseId(int wid);
	
	
	@Query(value = "select wu.id,wu.warehouse_id,max(wu.pdate) as pdate from warehouse_upload wu group by wu.id order by wu.id desc limit 1;",nativeQuery = true)
	List<WarehouseUpload> fetchLastModifieddate();
	
	
	@Query(value = "select * from warehouse_upload where warehouse_id = ?1",nativeQuery = true)
	List<WarehouseUpload> fetchWarehouseIdbyexcel(int wid);
	
	boolean existsByWarehouseId(int wid);
 	
}
