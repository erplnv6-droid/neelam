package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.model.PrintProductBarcode;
import com.SCM.projection.PrintProductProjection;

public interface PrintProductBarcodeRepository extends JpaRepository<PrintProductBarcode, Long> {

	
	@Query(value="select pb.id,b.brand_name,p.product_name,pb.createbyname,pb.createddate,pb.createdtime,pb.updatedbyname,pb.updateddate,pb.updatedtime\r\n"
			+ " from print_product_barcode pb left join brand b on\r\n"
			+ "pb.brand_id=b.id left join product p on pb.product_id=p.id",nativeQuery = true)
	List<PrintProductProjection> getPrintIndex(Pageable p);
	
	
	
	@Query(value="select pb.id,b.brand_name,p.product_name,pb.createbyname,pb.createddate,pb.createdtime,pb.updatedbyname,pb.updateddate,pb.updatedtime\r\n"
			+ " from print_product_barcode pb left join brand b on\r\n"
			+ "pb.brand_id=b.id left join product p on pb.product_id=p.id where pb.id like CONCAT('%',:search,'%') or b.brand_name like CONCAT('%',:search,'%') or p.product_name like CONCAT('%',:search,'%') ",nativeQuery = true)
	List<PrintProductProjection> getPrintSearch(String search,Pageable p);
}
