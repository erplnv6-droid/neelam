package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexDistributorOpeningStock;
import com.SCM.model.DistributorOpeningStock;



public interface DistributorOpeningRepository extends JpaRepository<DistributorOpeningStock,Long> {

	
//
//	@Query(value = "select dos.id,dos.quantity ,p.product_name,p.ean_code from distributor_opening_stock as dos left join  product as p \r\n"
//			+ "on dos.product_id=p.id",nativeQuery = true)
//	List<IndexDistributorOpeningStock> indexDistributorOpeningStocks(Pageable p);
//	
//	
//	@Query(value ="select dos.id,dos.quantity ,p.product_name,p.ean_code from distributor_opening_stock as dos left join  product as p \r\n"
//			+ "on dos.product_id=p.id"
//			+ " where dos.id Like CONCAT('%', :search, '%')"
//			+ " or dos.quantity Like CONCAT('%', :search, '%')"
//			+ " or p.product_name Like CONCAT('%', :search, '%')"  
//			+ " or p.ean_code Like CONCAT('%', :search, '%')"
//			,nativeQuery = true)
//	List<IndexDistributorOpeningStock> searchByIndexDistributorOpeningStock(String search,Pageable p);
	
	
	

	@Query(value = "SELECT dos.id,d.trade_name as distname,DATE_FORMAT(dos.user_date,'%d-%m-%Y') AS user_date,dos.distributor_id,dos.quantity as quantity,dos.product_id,p.product_name as pname,p.ean_code as peancode,DATE_FORMAT(dos.createddate,'%d-%m-%Y') AS createddate,dos.createdtime,dos.createbyname,dos.updatedbyname,DATE_FORMAT(dos.updateddate,'%d-%m-%Y') AS updateddate,dos.updatedtime \r\n"
			+ "FROM distributor_opening_stock dos\r\n"
			+ "LEFT join product p ON p.id=dos.product_id\r\n"
			+ "LEFT JOIN distributor d ON dos.distributor_id=d.id",nativeQuery = true)
	List<IndexDistributorOpeningStock> indexDistributorOpeningStocks(Pageable p);
	

	
	@Query(value = "SELECT dos.id,d.trade_name as distname,dos.distributor_id,DATE_FORMAT(dos.user_date,'%d-%m-%Y') AS user_date,dos.quantity as quantity,dos.product_id,p.product_name as pname,p.ean_code as peancode,DATE_FORMAT(dos.createddate,'%d-%m-%Y') AS createddate,dos.createdtime,dos.createbyname,dos.updatedbyname\r\n"
			+ "FROM distributor_opening_stock dos\r\n"
			+ "LEFT join product p ON p.id=dos.product_id\r\n"
			+ "LEFT JOIN distributor d ON dos.distributor_id=d.id\r\n"
			+ "WHERE d.id = ?1",nativeQuery = true)
	List<IndexDistributorOpeningStock> indexDistributorOpeningStocksByDistributorId(Pageable p,long distid);
	
	



	@Query(value ="  SELECT dos.id,d.trade_name as distname,dos.distributor_id,dos.quantity as quantity,dos.product_id,p.product_name as pname,p.ean_code as peancode,DATE_FORMAT(dos.createddate,'%d-%m-%Y') AS createddate,dos.createdtime,dos.createbyname,dos.updatedbyname,DATE_FORMAT(dos.updateddate,'%d-%m-%Y') AS updateddate,dos.updatedtime"
			+ "	FROM distributor_opening_stock dos"
			+ "	LEFT join product p ON p.id=dos.product_id"
			+ "	LEFT JOIN distributor d ON dos.distributor_id=d.id"
			+ " WHERE REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ " or dos.quantity Like CONCAT('%', :search, '%')"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(p.product_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ " or p.ean_code Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexDistributorOpeningStock> searchByIndexDistributorOpeningStock(String search,Pageable p);
	
	
	@Query(value="SELECT \r\n"
			+ "    dos.id,\r\n"
			+ "    d.trade_name AS distname,\r\n"
			+ "    DATE_FORMAT(dos.user_date, '%d-%m-%Y') AS user_date,\r\n"
			+ "    dos.distributor_id,\r\n"
			+ "    dos.quantity AS quantity,\r\n"
			+ "    dos.product_id,\r\n"
			+ "    p.product_name AS pname,\r\n"
			+ "    p.ean_code AS peancode,\r\n"
			+ "    DATE_FORMAT(dos.createddate, '%d-%m-%Y') AS createddate,\r\n"
			+ "    dos.createdtime,\r\n"
			+ "    dos.createbyname,\r\n"
			+ "    dos.updatedbyname,\r\n"
			+ "    DATE_FORMAT(dos.updateddate, '%d-%m-%Y') AS updateddate,\r\n"
			+ "    dos.updatedtime\r\n"
			+ "FROM distributor_opening_stock dos\r\n"
			+ "LEFT JOIN product p ON p.id = dos.product_id\r\n"
			+ "LEFT JOIN distributor d ON dos.distributor_id = d.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON  d.id = ds.distributor_id \r\n"
			+ "left join staff s on ds.rsmid=s.id where ds.rsmid=:distid",nativeQuery = true)
	List<IndexDistributorOpeningStock> indexDistributorOpeningStocksByRsmId(Pageable p,long distid);
	
	
	@Query(value="SELECT \r\n"
			+ "    dos.id,\r\n"
			+ "    d.trade_name AS distname,\r\n"
			+ "    DATE_FORMAT(dos.user_date, '%d-%m-%Y') AS user_date,\r\n"
			+ "    dos.distributor_id,\r\n"
			+ "    dos.quantity AS quantity,\r\n"
			+ "    dos.product_id,\r\n"
			+ "    p.product_name AS pname,\r\n"
			+ "    p.ean_code AS peancode,\r\n"
			+ "    DATE_FORMAT(dos.createddate, '%d-%m-%Y') AS createddate,\r\n"
			+ "    dos.createdtime,\r\n"
			+ "    dos.createbyname,\r\n"
			+ "    dos.updatedbyname,\r\n"
			+ "    DATE_FORMAT(dos.updateddate, '%d-%m-%Y') AS updateddate,\r\n"
			+ "    dos.updatedtime\r\n"
			+ "FROM distributor_opening_stock dos\r\n"
			+ "LEFT JOIN product p ON p.id = dos.product_id\r\n"
			+ "LEFT JOIN distributor d ON dos.distributor_id = d.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON  d.id = ds.distributor_id \r\n"
			+ "left join staff s on ds.asmid=s.id where ds.asmid=:distid",nativeQuery = true)
	List<IndexDistributorOpeningStock> indexDistributorOpeningStocksByAsmId(Pageable p,long distid);
	
	
	@Query(value="SELECT \r\n"
			+ "    dos.id,\r\n"
			+ "    d.trade_name AS distname,\r\n"
			+ "    DATE_FORMAT(dos.user_date, '%d-%m-%Y') AS user_date,\r\n"
			+ "    dos.distributor_id,\r\n"
			+ "    dos.quantity AS quantity,\r\n"
			+ "    dos.product_id,\r\n"
			+ "    p.product_name AS pname,\r\n"
			+ "    p.ean_code AS peancode,\r\n"
			+ "    DATE_FORMAT(dos.createddate, '%d-%m-%Y') AS createddate,\r\n"
			+ "    dos.createdtime,\r\n"
			+ "    dos.createbyname,\r\n"
			+ "    dos.updatedbyname,\r\n"
			+ "    DATE_FORMAT(dos.updateddate, '%d-%m-%Y') AS updateddate,\r\n"
			+ "    dos.updatedtime\r\n"
			+ "FROM distributor_opening_stock dos\r\n"
			+ "LEFT JOIN product p ON p.id = dos.product_id\r\n"
			+ "LEFT JOIN distributor d ON dos.distributor_id = d.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON  d.id = ds.distributor_id \r\n"
			+ "left join staff s on ds.aseid=s.id where ds.aseid=:distid",nativeQuery = true)
	List<IndexDistributorOpeningStock> indexDistributorOpeningStocksByAseId(Pageable p,long distid);
	
	
	

}
