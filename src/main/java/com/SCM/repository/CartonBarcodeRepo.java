package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexCartonBarcode;
import com.SCM.model.Brand;
import com.SCM.model.CartonBarcode;
import com.SCM.model.MasterCartoon;
import com.SCM.model.Product;
import com.SCM.model.ProductBarcode;

public interface CartonBarcodeRepo extends JpaRepository< CartonBarcode, Long>{

	Optional<CartonBarcode> findByGalaname(String galaname);
	
	@Query(value = "select * from cartonbarcode c where c.galaseriesname like CONCAT('%', :galaname, '%') order by id desc limit 1"
			,nativeQuery =  true)
	Optional<CartonBarcode> getLastGalaInfo(String galaname);

	
	@Query(value = "select cb.id,cb.createddate,cb.eancode,cb.eancodeimagename,cb.eancodeimagepath,cb.emptymasterctnweight,cb.galaname,max(cb.galanumber)as galanumber,cb.galaseriesimagename,cb.galaseriesimagepath,cb.height,cb.length,cb.maxweight,cb.minweight,cb.pcs,cb.pcsimagename,cb.pcsimagepath,cb.stdqty,cb.width,cb.brandid,cb.galaid,cb.mastercid,cb.productid,cb.grossweight,cb.netweight,cb.operatorname,cb.loosepacking,cb.createbyname,cb.createdby,cb.createdtime,cb.role,cb.updatedby,cb.updatedbyname,cb.updateddate,cb.updatedrole,cb.updatedtime,max(cb.galaseriesname) as galaseriesname from cartonbarcode cb",nativeQuery = true)
	CartonBarcode LastMaxGalaSeries(String galaseriesname);
	
	Optional<CartonBarcode> findByMasterCartoon(MasterCartoon masterCartoon);
	
	CartonBarcode findByGalaseriesname(String galaseriesname);
	
	@Query(value = "select * from cartonbarcode cb where mastercid = ?1",nativeQuery = true)
	List<CartonBarcode> fetchGalaAndMasterCarton(long mscid);
	
	boolean existsByMasterCartoon(MasterCartoon masterCartoon);
	
	
	Optional<CartonBarcode> findByProductAndBrand(Product product,Brand brand);
	
	
	@Query(value = 
			"select p.product_name as pname,b.brand_name as bname,c.id,c.productid,c.brandid,c.eancode,c.pcs,c.galaseriesname,c.stdqty,c.length,c.width,c.height,DATE_FORMAT(c.createddate,'%d-%m-%Y') AS createddate,c.createdtime,c.createbyname,c.updatedbyname,DATE_FORMAT(c.updateddate,'%d-%m-%Y') AS updateddate,c.updatedtime from"
			+ " cartonbarcode c left join\r\n"
			+ " product p on p.id=c.productid\r\n"
			+ " left join brand b\r\n"
			+ " on b.id=c.brandid"
			,nativeQuery = true)
	List<IndexCartonBarcode> IndexAllCartonBarcode();
	
	
	@Query(value =
			"select p.product_name as pname,b.brand_name as bname,c.id,c.productid,c.brandid,c.eancode,c.pcs,c.galaseriesname,c.stdqty,c.length,c.width,c.height,DATE_FORMAT(c.createddate,'%d-%m-%Y') AS createddate,c.createdtime,c.createbyname,c.updatedbyname,DATE_FORMAT(c.updateddate,'%d-%m-%Y') AS updateddate,c.updatedtime from"
			+ " cartonbarcode c left join\r\n"
			+ " product p on p.id=c.productid\r\n"
			+ " left join brand b\r\n"
			+ " on b.id=c.brandid"
			,nativeQuery = true)
	List<IndexCartonBarcode> IndexAllCartonBarcode(Pageable p);
	
	
	@Query(value = "select p.product_name as pname,b.brand_name as bname,c.id,c.productid,c.brandid,c.eancode,c.pcs,c.galaseriesname,c.stdqty,c.length,c.width,c.height,c.createddate from\r\n"
			+ " cartonbarcode c left join\r\n"
			+ " product p on p.id=c.productid\r\n"
			+ " left join brand b\r\n"
			+ " on b.id=c.brandid\r\n"
			+ " where c.productid Like CONCAT('%', :search, '%')"
			+ "     or c.brandid Like CONCAT('%', :search, '%')" 
			+ "     or p.product_name Like CONCAT('%', :search, '%')" 
			+ "     or b.brand_name Like CONCAT('%', :search, '%')" 
			+ "     or c.id Like CONCAT('%', :search, '%')" 
			+ "     or c.eancode Like CONCAT('%', :search, '%')" 
			+ "     or c.pcs Like CONCAT('%', :search, '%')" 
			+ "     or c.galaseriesname Like CONCAT('%', :search, '%')" 
			+ "     or c.stdqty Like CONCAT('%', :search, '%')"
			+ "     or c.createddate Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexCartonBarcode> IndexAllCartonBarcode(Pageable p,String search);
}
