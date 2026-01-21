package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexPurchaseReutrn;
import com.SCM.IndexDto.PurchaseReturnPageDtoProjection;
import com.SCM.model.Purchase;
import com.SCM.model.PurchaseReturn;
import com.SCM.model.Sales;
import com.SCM.model.VoucherMaster;


@Repository
public interface PurchaseReturnRepo extends JpaRepository<PurchaseReturn, Integer> {
	
	
	@Query(value = "select p.id,p.sdndate,s.cities,p.igst,p.sgst,p.cgst,DATE_FORMAT(p.purchasereturndate,'%d-%m-%Y') AS purchasereturndate,p.vouchermaster_series,s.companyname,p.shippingaddress,p.netamount,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.grandtotal,p.remarks,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ "ELSE p.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from purchase_return p\r\n"
			+ "left join supplier s on p.supplier_id=s.id" , nativeQuery = true)
    public List<IndexPurchaseReutrn> indexPurchaseReturn(Pageable p);
	
	
	@Query(value = "select p.id,p.sdndate,s.cities,p.igst,p.sgst,p.cgst,DATE_FORMAT(p.purchasereturndate,'%d-%m-%Y') AS purchasereturndate,p.vouchermaster_series,s.companyname,p.shippingaddress,p.netamount,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.grandtotal,p.remarks,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ "ELSE p.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from purchase_return p\r\n"
			+ "left join supplier s on p.supplier_id=s.id" 
			+" where p.supplier_id = :sid", nativeQuery = true)
    public List<IndexPurchaseReutrn> indexPurchaseReturnSupplier(int sid,Pageable p);
	
	
	@Query(value = "select p.id,p.sdndate,s.cities,p.igst,p.sgst,p.cgst,DATE_FORMAT(p.purchasereturndate,'%d-%m-%Y') AS purchasereturndate,p.vouchermaster_series,s.companyname,p.shippingaddress,p.netamount,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.grandtotal,p.remarks,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ "ELSE p.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from purchase_return p\r\n"
			+ "left join supplier s on p.supplier_id=s.id" 
			+" where p.supplier_id = :sid", nativeQuery = true)
    public List<IndexPurchaseReutrn> indexPurchaseReturnSupplier(int sid);
	
	
	
	
	
	
	@Query(value = "select p.id,p.sdndate,s.cities,s.companyname,p.shippingaddress,p.netamount,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.grandtotal,p.gross_amount,p.remarks,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,"
			+ " (CASE \r\n"
			+ " WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ " ELSE p.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " from purchase_return p"
			+ " left join supplier s on p.supplier_id=s.id "
			+ " where p.id Like CONCAT('%', :search, '%')"
			+ " or p.sdndate Like CONCAT('%', :search, '%') "
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or p.netAmount Like CONCAT('%', :search, '%') "
			+ " or p.createbyname Like CONCAT('%', :search, '%')"
			+ " or p.updatedbyname Like CONCAT('%', :search, '%')"
			+ " or p.igst Like CONCAT('%', :search, '%')"
			+ " or p.grandtotal Like CONCAT('%', :search, '%') "
			+ " or p.remarks Like CONCAT('%', :search, '%')"
			+ " or p.shippingaddress Like CONCAT('%', :search, '%') " , nativeQuery = true)
	List<IndexPurchaseReutrn> searchByPurchaseReturn(String search, Pageable p);
	
	
	@Query(value = "select p.id,p.sdndate,s.cities,s.companyname,p.shippingaddress,p.netamount,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.grandtotal,p.gross_amount,p.remarks,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,"
			+ " (CASE \r\n"
			+ " WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ " ELSE p.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " from purchase_return p"
			+ " left join supplier s on p.supplier_id=s.id "
			+ " where (p.id Like CONCAT('%', :search, '%')"
			+ " or p.sdndate Like CONCAT('%', :search, '%') "
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or p.netAmount Like CONCAT('%', :search, '%') "
			+ " or p.createbyname Like CONCAT('%', :search, '%')"
			+ " or p.updatedbyname Like CONCAT('%', :search, '%')"
			+ " or p.igst Like CONCAT('%', :search, '%')"
			+ " or p.grandtotal Like CONCAT('%', :search, '%') "
			+ " or p.remarks Like CONCAT('%', :search, '%')"
			+ " or p.shippingaddress Like CONCAT('%', :search, '%')) " 
			+" and p.supplier_id=:sid " , nativeQuery = true)
	List<IndexPurchaseReutrn> searchByPurchaseReturn(String search, Pageable p,int sid);
	
	
	

	
	@Query(value = "select * from purchase_return pr where pr.voucherseries like  CONCAT('%', :search , '%') order by id desc limit 1",nativeQuery = true)
	PurchaseReturn searchByVoucher(String search);
	
//	
	PurchaseReturn findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
	PurchaseReturn findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	
    @Query(value = "SELECT * FROM purchase_return pr order by id desc limit 1",nativeQuery = true)
    PurchaseReturn lastrowstatus();
    
    PurchaseReturn findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
	
	
}
