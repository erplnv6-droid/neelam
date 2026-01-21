package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexProductBarcode;
import com.SCM.IndexDto.SetBarcodePrintIndex;
import com.SCM.model.Brand;
import com.SCM.model.Product;
import com.SCM.model.ProductBarcode;

@Repository
public interface ProductBarcodeRepo extends JpaRepository<ProductBarcode, Long> {

	Optional<ProductBarcode> findByBarcodename(String barcodename);

	boolean existsByBarcodename(String barcodename);

	boolean existsByProduct(Product product);

	boolean existsByBrand(Brand brand);

	Optional<ProductBarcode> findByProductAndBrand(Product product, Brand brand);
	
	

	@Query(value = "select * from productbarcode where brandid=?1 and productid=?2 ", nativeQuery = true)
	Optional<ProductBarcode> barcodeByBrandAndProductid(long brandid, long productid);

	@Query(value = "select id,productname,size,qty,mrp,capacity,brandname,eancode,packed_on from productbarcode ", nativeQuery = true)
	List<IndexProductBarcode> indexProductBarcode();

	@Query(value = "select id,productname,size,qty,mrp,capacity,brandname,eancode,packed_on,DATE_FORMAT(createddate,'%d-%m-%Y') AS createddate,createbyname,updatedbyname,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') AS updateddate,updatedtime"
			+ "  from productbarcode ", nativeQuery = true)
	List<IndexProductBarcode> IndexProductBarcode(Pageable p);

	@Query(value = "select id,productname,size,qty,mrp,capacity,brandname,eancode,packed_on,DATE_FORMAT(createddate,'%d-%m-%Y') AS createddate,createbyname,updatedbyname,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') AS updateddate,updatedtime from productbarcode "
			+ " where productname Like CONCAT('%', :search, '%')" + "     or id Like CONCAT('%', :search, '%')"
			+ "     or size Like CONCAT('%', :search, '%')" + "     or qty Like CONCAT('%', :search, '%')"
			+ "     or mrp Like CONCAT('%', :search, '%')" + "     or capacity Like CONCAT('%', :search, '%')"
			+ "     or brandname Like CONCAT('%', :search, '%')"
			+ "     or eancode Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexProductBarcode> IndexProductBarcode(String search, Pageable p);

	ProductBarcode findFirstByOrderByIdDesc();
	
	
	boolean existsByProductAndBrand(Product product,Brand brand);
	
	
	@Query(value = "SELECT p.id,p.product_name FROM productbarcode pb\r\n"
			+ "LEFT JOIN product p on pb.productid = p.id\r\n"
			+ "WHERE pb.brandid = ?1",nativeQuery = true)
	List<SetBarcodePrintIndex> FetchSetBarCodeProduct(int bid);
	
//	@Query(value = "",nativeQuery = true)
//	List<ProductBarcode> pidandbid();

//	@Query(value = "select id,productname,size,qty,mrp,capacity,brandname,eancode,packed_on from productbarcode "
//	+ " where eancode Like CONCAT('%', :search, '%')",nativeQuery = true)
//List<IndexProductBarcode> IndexProductBarcode(String search,Pageable p);
}
