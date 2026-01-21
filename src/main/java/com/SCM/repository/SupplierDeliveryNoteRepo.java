package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexSupplierDelivery;
import com.SCM.model.Sales;
import com.SCM.model.SupplierDeliveryNote;
import com.SCM.model.VoucherMaster;

@Repository
public interface SupplierDeliveryNoteRepo extends JpaRepository<SupplierDeliveryNote, Integer>{


	@Query(value = "select * from supplier_delivery_note where supplier_delivery_note.voucherseries like  CONCAT('%', :search, '%') order by id desc limit 1 ",nativeQuery = true)
	SupplierDeliveryNote searchByVoucher(String search);
	
	
	@Query(value = "SELECT sdn.id,s.cities,sdn.igst,sdn.sgst,sdn.cgst,sdn.grossamount,sdn.vouchermaster_series,sdn.status,DATE_FORMAT(sdn.sdndate, '%d-%m-%Y') AS sdndate,sdn.remarks,sdn.grandtotal,s.companyname,DATE_FORMAT(sdn.createddate, '%d-%m-%Y') AS createddate, sdn.createbyname,sdn.updatedbyname,sdn.createdtime,DATE_FORMAT(sdn.updateddate, '%d-%m-%Y') AS updateddate,sdn.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN sdn.igst = 0 THEN (sdn.sgst+sdn.cgst) \r\n"
			+ "ELSE sdn.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "FROM supplier_delivery_note sdn\r\n"
			+ "LEFT JOIN supplier s ON sdn.supplier_id = s.id",nativeQuery = true)
	List<IndexSupplierDelivery> indexSupplierDelivery(Pageable p);
	
	
	@Query(value = "SELECT sdn.id,s.cities,sdn.igst,sdn.sgst,sdn.cgst,sdn.grossamount,sdn.vouchermaster_series,sdn.status,DATE_FORMAT(sdn.sdndate, '%d-%m-%Y') AS sdndate,sdn.remarks,sdn.grandtotal,s.companyname,DATE_FORMAT(sdn.createddate, '%d-%m-%Y') AS createddate, sdn.createbyname,sdn.updatedbyname,sdn.createdtime,DATE_FORMAT(sdn.updateddate, '%d-%m-%Y') AS updateddate,sdn.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ " WHEN sdn.igst = 0 THEN (sdn.sgst+sdn.cgst) \r\n"
			+ " ELSE sdn.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " FROM supplier_delivery_note sdn\r\n"
			+ " LEFT JOIN supplier s ON sdn.supplier_id = s.id\r\n"
			+ " where sdn.id Like CONCAT('%', :search, '%')"
			+ " or sdn.sdndate Like CONCAT('%', :search, '%')"
			+ " or sdn.remarks Like CONCAT('%', :search, '%')"  
			+ " or sdn.status Like CONCAT('%', :search, '%')"  
			+ " or sdn.igst Like CONCAT('%', :search, '%')"
			+ " or sdn.grandtotal Like CONCAT('%', :search, '%')"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or sdn.createbyname Like CONCAT('%', :search, '%')"
			+ " or sdn.updatedbyname Like CONCAT('%', :search, '%')"
			+ " or s.cities Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexSupplierDelivery> searchByIndexSupplierDelivery(String search,Pageable p);
	
	
	
	//--------------------------- sdn by supplier id
	
	
	@Query(value = "SELECT sdn.id,s.cities,sdn.igst,sdn.sgst,sdn.cgst,sdn.grossamount,sdn.vouchermaster_series,sdn.status,DATE_FORMAT(sdn.sdndate, '%d-%m-%Y') AS sdndate,sdn.remarks,sdn.grandtotal,s.companyname,DATE_FORMAT(sdn.createddate, '%d-%m-%Y') AS createddate, sdn.createbyname,sdn.updatedbyname,sdn.createdtime,DATE_FORMAT(sdn.updateddate, '%d-%m-%Y') AS updateddate,sdn.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "  WHEN sdn.igst = 0 THEN (sdn.sgst+sdn.cgst) \r\n"
			+ "  ELSE sdn.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "  FROM supplier_delivery_note sdn\r\n"
			+ "  LEFT JOIN supplier s ON sdn.supplier_id = s.id"
			+ "  Where sdn.supplier_id = :sid", nativeQuery = true)
	List<IndexSupplierDelivery> indexSupplierDeliveryBySupplier(Pageable p,@Param("sid") int sid);
	
	
	
	@Query(value = "SELECT sdn.id,s.cities,sdn.igst,sdn.sgst,sdn.cgst,sdn.grossamount,sdn.vouchermaster_series,sdn.status,DATE_FORMAT(sdn.sdndate, '%d-%m-%Y') AS sdndate,sdn.remarks,sdn.grandtotal,s.companyname,DATE_FORMAT(sdn.createddate, '%d-%m-%Y') AS createddate, sdn.createbyname,sdn.updatedbyname,sdn.createdtime,DATE_FORMAT(sdn.updateddate, '%d-%m-%Y') AS updateddate,sdn.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "  WHEN sdn.igst = 0 THEN (sdn.sgst+sdn.cgst) \r\n"
			+ "  ELSE sdn.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "  FROM supplier_delivery_note sdn\r\n"
			+ "  LEFT JOIN supplier s ON sdn.supplier_id = s.id"
			+ "  Where sdn.supplier_id = :sid",nativeQuery = true)
	List<IndexSupplierDelivery> indexSupplierDeliveryBySupplier(int sid);
	
	
	@Query(value = "SELECT sdn.id,s.cities,sdn.igst,sdn.sgst,sdn.cgst,sdn.vouchermaster_series,sdn.grossamount,sdn.status,DATE_FORMAT(sdn.sdndate, '%d-%m-%Y') AS sdndate,sdn.remarks,sdn.grandtotal,s.companyname,DATE_FORMAT(sdn.createddate, '%d-%m-%Y') AS createddate, sdn.createbyname,sdn.updatedbyname,sdn.createdtime,DATE_FORMAT(sdn.updateddate, '%d-%m-%Y') AS updateddate,sdn.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ " WHEN sdn.igst = 0 THEN (sdn.sgst+sdn.cgst) \r\n"
			+ " ELSE sdn.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " FROM supplier_delivery_note sdn\r\n"
			+ " LEFT JOIN supplier s ON sdn.supplier_id = s.id\r\n"
			+ " where (sdn.id Like CONCAT('%', :search, '%')"
			+ " or sdn.sdndate Like CONCAT('%', :search, '%')"
			+ " or sdn.remarks Like CONCAT('%', :search, '%')"  
			+ " or sdn.status Like CONCAT('%', :search, '%')"  
			+ " or sdn.igst Like CONCAT('%', :search, '%')"
			+ " or sdn.grandtotal Like CONCAT('%', :search, '%')"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or sdn.createbyname Like CONCAT('%', :search, '%')"
			+ " or sdn.updatedbyname Like CONCAT('%', :search, '%')"
			+ " or s.cities Like CONCAT('%', :search, '%')) "
			+ "AND sdn.supplier_id = :sid",nativeQuery = true)
	List<IndexSupplierDelivery> searchByIndexSupplierDeliveryBySupplier(String search,Pageable p,int sid);
	
//	------------- voucher
	
    @Query(value = "SELECT * FROM supplier_delivery_note sdn order by id desc limit 1",nativeQuery = true)
    SupplierDeliveryNote lastrowstatus();
    
    SupplierDeliveryNote findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);


    SupplierDeliveryNote findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
    SupplierDeliveryNote findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	
}
