package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexMasterCarton;
import com.SCM.model.Brand;
import com.SCM.model.MasterCartoon;
import com.SCM.model.Product;

public interface MasterCartoonRepo extends JpaRepository<MasterCartoon, Long> {

	List<MasterCartoon> findByBrand(Brand brand);
	
	
	boolean existsByBrand(Brand brand);
	boolean existsByProduct(Product product);

	
	boolean existsByProductAndBrand(Product product,Brand brand);
	Optional<MasterCartoon> findByProductAndBrand(Product product,Brand brand);

	@Query(value="select * from mastercartoon where productid =?1 and brandid=?2",nativeQuery = true)
	Optional<MasterCartoon> byBrandAndProductId(long pid,long bid);
	
	
	@Query(value="select p.product_name as pname,b.brand_name as bname,m.id,m.productid,m.brandid,m.qty,m.minweight,m.maxweight,m.height,m.width,m.eancode,m.pcs,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime"
			+ " from mastercartoon m left join product p\r\n"
			+ " on p.id=m.productid \r\n"
			+ " left join brand b\r\n"
			+ " on b.id=m.brandid"
			,nativeQuery = true)
	List<IndexMasterCarton> IndexMasterCarton();
	
	
	@Query(value="select p.product_name as pname,b.brand_name as bname,m.id,m.productid,m.brandid,m.qty,m.minweight,m.maxweight,m.height,m.width,m.eancode,m.pcs,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime"
			+ " from mastercartoon m left join product p\r\n"
			+ " on p.id=m.productid \r\n"
			+ " left join brand b\r\n"
			+ " on b.id=m.brandid"
			,nativeQuery = true)
	List<IndexMasterCarton> IndexMasterCarton(Pageable p);
	
	
	@Query(value="select p.product_name as pname,b.brand_name as bname,m.id,m.productid,m.brandid,m.qty,m.minweight,m.maxweight,m.height,m.width,m.eancode,m.pcs,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime"
			+ " from mastercartoon m left join product p\r\n"
			+ " on p.id=m.productid \r\n"
			+ " left join brand b\r\n"
			+ " on b.id=m.brandid"
			+ " where m.id Like CONCAT('%', :search, '%')"
			+ "     or m.productid Like CONCAT('%', :search, '%')" 
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(p.product_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ "     or b.brand_name Like CONCAT('%', :search, '%')" 
			+ "     or m.brandid Like CONCAT('%', :search, '%')" 
			+ "     or m.qty Like CONCAT('%', :search, '%')" 
			+ "     or  m.eancode Like CONCAT('%', :search, '%')" 
			+ "     or m.createddate Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexMasterCarton> IndexMasterCarton(Pageable p,String search);
	
	

}
