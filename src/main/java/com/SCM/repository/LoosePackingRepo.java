package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexBranch;
import com.SCM.IndexDto.IndexLoosePacking;
import com.SCM.IndexDto.IndexLoosePackingPagination;
import com.SCM.model.LoosePacking;

@Repository
public interface LoosePackingRepo  extends JpaRepository<LoosePacking, Long> {

	@Query(value = "SELECT lp.id,p.product_name from loose_packing lp\r\n"
			+ "LEFT JOIN product p on lp.product_id = p.id\r\n"
			+ " where lp.bid = ?1",nativeQuery = true)
	List<IndexLoosePacking> fetchProductByBrand1(int bid);
	
	
	@Query(value = "SELECT p.id,p.product_name from product p WHERE p.brand = ?1",nativeQuery = true)
	List<IndexLoosePacking> fetchProductByBrand(int bid);
	
	
	
	
	@Query(value = "select lp.id,lp.pcs,lp.qty,lp.operatorname,lp.createdtime,DATE_FORMAT(lp.createddate,'%d-%m-%Y') AS createddate,p.product_name,b.brand_name\r\n"
			+ "from loose_packing lp\r\n"
			+ "LEFT JOIN product p on lp.product_id = p.id\r\n"
			+ "LEFT JOIN brand b on lp.bid = b.id",nativeQuery = true)
	List<IndexLoosePackingPagination> IndexLoosePacking(Pageable p);
	
	
	
	@Query(value = "select lp.id,lp.pcs,lp.qty,lp.operatorname,lp.createdtime,DATE_FORMAT(lp.createddate,'%d-%m-%Y') AS createddate,p.product_name,b.brand_name\r\n"
			+ " from loose_packing lp\r\n"
			+ " LEFT JOIN product p on lp.product_id = p.id\r\n"
			+ " LEFT JOIN brand b on lp.bid = b.id"
			+ "  WHERE lp.id Like CONCAT('%', :search, '%')"
			+ "     or lp.qty Like CONCAT('%', :search, '%')"
			+ "     or lp.operatorname Like CONCAT('%', :search, '%')"  
			+ "     or lp.createddate Like CONCAT('%', :search, '%')"
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(p.product_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or b.brand_name Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexLoosePackingPagination> SearchByLoosePacking(String search, Pageable p);
}
