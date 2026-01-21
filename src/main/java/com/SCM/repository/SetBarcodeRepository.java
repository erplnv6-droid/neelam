package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexSetBarcode;
import com.SCM.IndexDto.SetBarcodePrintIndex;
import com.SCM.model.Brand;
import com.SCM.model.Product;
import com.SCM.model.SetBarcode;

public interface SetBarcodeRepository extends JpaRepository<SetBarcode, Long>{
	
	boolean existsByProduct(Product product);
	boolean existsByBrand(Brand brand);
	
	@Query(value="select * from setbarcode where productid=?1 and brandid=?2",nativeQuery = true)
	Optional<SetBarcode> byProductAndBrandId(long pid,long bid);
	
	@Query(value = "select sb.id,sb.eancode,sb.quantity,sb.brandid,sb.productid,DATE_FORMAT(sb.createddate,'%d-%m-%Y') AS createddate,sb.createdtime,sb.createbyname,sb.updatedbyname,DATE_FORMAT(sb.updateddate,'%d-%m-%Y') AS updateddate,sb.updatedtime from setbarcode sb",nativeQuery = true)
	List<IndexSetBarcode> IndexSetBarcode();
	
	@Query(value = "select sb.id,sb.eancode,sb.quantity,sb.brandid,sb.productid,DATE_FORMAT(sb.createddate,'%d-%m-%Y') AS createddate,sb.createdtime,sb.createbyname,sb.updatedbyname,DATE_FORMAT(sb.updateddate,'%d-%m-%Y') AS updateddate,sb.updatedtime from setbarcode sb",nativeQuery = true)
	List<IndexSetBarcode> IndexSetBarcode(Pageable p);
	
	Optional<SetBarcode> findByProductAndBrand(Product product,Brand brand);
	
	
	@Query(value = "select sb.id,sb.eancode,sb.quantity,sb.brandid,sb.productid,DATE_FORMAT(sb.createddate,'%d-%m-%Y') AS createddate,sb.createdtime,sb.createbyname,sb.updatedbyname,DATE_FORMAT(sb.updateddate,'%d-%m-%Y') AS updateddate,sb.updatedtime from setbarcode sb"
			+ " where id Like CONCAT('%', :search, '%')"
			+ "     or eancode Like CONCAT('%', :search, '%')"
			+ "     or quantity Like CONCAT('%', :search, '%')"
			+ "     or productid Like CONCAT('%', :search, '%')"
			+ "     or brandid Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexSetBarcode> IndexSetBarcode(Pageable p,String search);
	
	
	@Query(value = "SELECT p.id,p.product_name FROM setbarcode sb\r\n"
			+ "LEFT JOIN product p on sb.productid = p.id\r\n"
			+ "WHERE sb.brandid = ?1",nativeQuery = true)
	List<SetBarcodePrintIndex> FetchSetBarCodeProduct(int bid);
}
