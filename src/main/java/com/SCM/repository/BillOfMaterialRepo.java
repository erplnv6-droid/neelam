package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.FetchBomProductIndex;
import com.SCM.IndexDto.IndexBillOfMaterial;
import com.SCM.model.BillOfMaterial;
import com.SCM.model.MasterCartoon;
import com.SCM.model.Product;
@Repository
public interface BillOfMaterialRepo extends JpaRepository<BillOfMaterial, Integer> {

	@Query(value = "select * from bill_of_material", nativeQuery = true)
	public List<BillOfMaterial> getForIndex(Pageable p);
	

	Optional<BillOfMaterial> findByProduct(Product id);

	
	@Query(value="SELECT b.id,b.bom_no,b.description,b.rate,b.product_id,p.product_name as productname "
			+ "FROM bill_of_material b\r\n"
			+ "LEFT JOIN product p on b.product_id= p.id ",nativeQuery = true)
	List<IndexBillOfMaterial> IndexAllBillOfMaterial();
	
	
	@Query(value="SELECT b.id,b.bom_no,b.description,b.rate,b.product_id,p.product_name as productname "
			+ "FROM bill_of_material b\r\n"
			+ "LEFT JOIN product p on b.product_id= p.id",nativeQuery = true)
	List<IndexBillOfMaterial> IndexAllBillOfMaterial(Pageable p);
	
	
	@Query(value="select b.id,b.amount,b.bom_no,b.description,b.rate,b.product_id,p.product_name as productname,b.createddate,b.createdtime,b.createbyname,b.updatedbyname,DATE_FORMAT(b.updateddate,'%d-%m-%Y') as updateddate,b.updatedtime "
			+ " from bill_of_material b\r\n"
			+ " left join product p on b.product_id= p.id"
			+ " where b.id Like CONCAT('%', :search, '%')"
			+ "    or b.amount Like CONCAT('%', :search, '%')" 
			+ "    or b.bom_no Like CONCAT('%', :search, '%')" 
			+ "    or b.description Like CONCAT('%', :search, '%')" 
			+ "    or b.rate Like CONCAT('%', :search, '%')" 
			+ "    or b.product_id Like CONCAT('%', :search, '%')"
			+ "    or REGEXP_LIKE(REGEXP_REPLACE(p.product_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "    or b.createbyname Like CONCAT('%', :search, '%')"
			+ "    or b.updatedbyname Like CONCAT('%', :search, '%')"
			+ "    or b.updateddate Like CONCAT('%', :search, '%')"
			+ "    or b.createddate Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexBillOfMaterial> IndexAllBillOfMaterial(Pageable p,String search);
	
	
	public BillOfMaterial findFirstByOrderByIdDesc();
	
	
	@Query(value = "select distinct(p.id),p.product_name from bill_of_material bom\r\n"
			+ "LEFT JOIN product p on bom.product_id = p.id\r\n"
			+ "WHERE p.brand= ?1",nativeQuery = true)
	List<FetchBomProductIndex> fetchbomproduct(String bid);
	
//	@Query(name = "select bom.id,distinct(bom.product_id) from bill_of_material bom\r\n"
//			  + " LEFT JOIN product p on bom.product_id = p.id",nativeQuery = true)
//	List<FetchBomProductIndex> fetchProductfromBom();
}
