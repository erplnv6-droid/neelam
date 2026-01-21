package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexPurchaseOrder;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.Sales;
import com.SCM.model.VoucherMaster;
import com.SCM.model.WorkOrder;

@Repository
public interface PoRepository extends JpaRepository<PurchaseOrder, Integer> {

	
	@Query(value = "select * from purchase_order where purchase_order.voucherseries like  CONCAT('%', :search, '%') order by id desc limit 1 ",nativeQuery = true)
	PurchaseOrder searchByVoucher(String search);
	
	
	@Query(value = "SELECT po.id,s.cities,po.igst,po.cgst,po.status,po.sgst,po.vouchermaster_series,DATE_FORMAT(po.podate,'%d-%m-%Y') AS podate,po.remarks,po.net_amount,po.grandtotal,s.companyname,DATE_FORMAT(po.createddate,'%d-%m-%Y') AS createddate,po.createbyname,po.updatedbyname,po.createdtime,DATE_FORMAT(po.updateddate,'%d-%m-%Y') AS updateddate,po.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN po.igst = 0 THEN (po.sgst+po.cgst) \r\n"
			+ "ELSE po.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from purchase_order po\r\n"
			+ "Left join supplier s on po.supplier_id = s.id",nativeQuery = true)
	List<IndexPurchaseOrder> indexPurchaseOrder(Pageable p);


	@Query(value = "SELECT po.id,s.cities,po.igst,po.cgst,po.sgst,po.vouchermaster_series,DATE_FORMAT(po.podate,'%d-%m-%Y') AS podate,po.status,po.remarks,po.net_amount,po.grandtotal,s.companyname,DATE_FORMAT(po.createddate,'%d-%m-%Y') AS createddate,po.createbyname,po.updatedbyname,po.createdtime,DATE_FORMAT(po.updateddate,'%d-%m-%Y') AS updateddate,po.updatedtime,"
			+ "(CASE "
			+ "	WHEN po.igst = 0 THEN (po.sgst+po.cgst)\r\n"
			+ "	 ELSE po.igst\r\n"
			+ "	END) AS gstvalue "
			+ " from purchase_order po"
			+ " Left join supplier s on po.supplier_id = s.id"
			+ " where po.podate Like CONCAT('%', :search, '%')"
			+ "     or po.remarks Like CONCAT('%', :search, '%')"
			+ "     or po.net_amount Like CONCAT('%', :search, '%')"  
			+ "     or po.id Like CONCAT('%', :search, '%')"
			+ "     or po.grandtotal Like CONCAT('%', :search, '%')"
			+ "     or po.status Like CONCAT('%', :search, '%')"
			+ "     or po.createbyname Like CONCAT('%', :search, '%')"
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or po.updatedbyname Like CONCAT('%', :search, '%')"
			+ "     or s.cities Like CONCAT('%', :search, '%')"
			+ "     or po.voucherseries Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexPurchaseOrder> SearchByPurchaseOrder(String search, Pageable p);
	
	
	
	
	
	//------------------- po by supplier id
	
	
	
	@Query(value = "SELECT po.id,po.podate,po.igst,po.cgst,po.sgst,po.remarks,po.net_amount,po.grandtotal,s.suppliername,po.createddate,po.createdtime,s.companyname,po.vouchermaster_series,s.cities,po.status,\r\n"
			+ "(CASE \r\n"
			+ "WHEN po.igst = 0 THEN (po.sgst+po.cgst) \r\n"
			+ "ELSE po.igst\r\n"
			+ "END) AS gstvalue\r\n"
            + "from purchase_order po"
			   + " Left join supplier s on po.supplier_id = s.id "
			   + " where po.supplier_id = :sid",nativeQuery = true)
	List<IndexPurchaseOrder> indexPurchaseOrderBySupplierId(int sid,Pageable p);
	   
	@Query(value = "SELECT po.id,po.podate,po.remarks,po.igst,po.cgst,po.sgst,po.net_amount,po.grandtotal,s.suppliername,po.createddate,po.vouchermaster_series,po.createdtime,s.companyname,s.cities,po.status,\r\n"
			+ "(CASE \r\n"
			+ "WHEN po.igst = 0 THEN (po.sgst+po.cgst) \r\n"
			+ "ELSE po.igst\r\n"
			+ "END) AS gstvalue\r\n"
            + "from purchase_order po"
	        + " Left join supplier s on po.supplier_id = s.id "
			   + " where po.supplier_id = :sid",nativeQuery = true)
	List<IndexPurchaseOrder> indexPurchaseOrderBySupplierId(int sid);
	
//	@Query(value = "select po.id,po.podate,po.remarks,po.net_amount,po.grandtotal,s.suppliername,po.createddate,po.createdtime,s.companyname,s.cities,po.status,\r\n"
//			+ "(CASE \r\n"
//			+ "WHEN po.igst = 0 THEN (po.sgst+po.cgst) \r\n"
//			+ "ELSE po.igst\r\n"
//			+ "END) AS gstvalue\r\n"
//            + "from purchase_order po"
//			+ " Left join supplier s on po.supplier_id = s.id"
//			+ " where po.podate Like CONCAT('%', :search, '%')"
//			+ "     or po.remarks Like CONCAT('%', :search, '%')"
//			+ "     or po.net_amount Like CONCAT('%', :search, '%')"
//			+ "     or po.id Like CONCAT('%', :search, '%')"
//			+ "     or po.grandtotal Like CONCAT('%', :search, '%') and po.supplier_id = :sid", nativeQuery = true)
//	List<IndexPurchaseOrder> SearchByPurchaseOrderBySupplierId(String search, Pageable p,int sid);
	
	
	@Query(value = "select po.id, po.podate, po.remarks,po.igst,po.cgst,po.sgst,po.net_amount, po.grandtotal,po.vouchermaster_series, s.suppliername, po.createddate, po.createdtime, s.companyname, s.cities, po.status, "
	        + "(CASE "
	        + "WHEN po.igst = 0 THEN (po.sgst + po.cgst) "
	        + "ELSE po.igst "
	        + "END) AS gstvalue "
	        + "from purchase_order po "
	        + "Left join supplier s on po.supplier_id = s.id "
	        + "where (po.podate Like CONCAT('%', :search, '%') "
	        + "or po.remarks Like CONCAT('%', :search, '%') "
	        + "or po.net_amount Like CONCAT('%', :search, '%') "
	        + "or po.id Like CONCAT('%', :search, '%') "
	        + "or po.grandtotal Like CONCAT('%', :search, '%')) "
	        + "and po.supplier_id = :sid", nativeQuery = true)
	List<IndexPurchaseOrder> SearchByPurchaseOrderBySupplierId(String search, Pageable p, int sid);

	
	
	
    @Query(value = "SELECT * FROM purchase_order po order by id desc limit 1",nativeQuery = true)
    PurchaseOrder lastrowstatus();
    
    PurchaseOrder findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher); 
    
 
    @Query(value = "select * from purchase_order where supplier_id = ?" , nativeQuery = true)
    public List<PurchaseOrder> findBysupplier_id(Integer supplier_id);


	PurchaseOrder findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
	PurchaseOrder findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);   

}
