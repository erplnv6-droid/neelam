package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexPurchase;
import com.SCM.IndexDto.PurchasePageDtoProjection;
import com.SCM.model.Purchase;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.Sales;
import com.SCM.model.VoucherMaster;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
	

	@Query(value = "select * from purchase where purchase.voucherseries like  CONCAT('%', :search, '%') order by id desc limit 1 ",nativeQuery = true)
	Purchase searchByVoucher(String search);
	
	
	@Query(value = "select p.id,s.cities,s.companyname,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.purchasedate,'%d-%m-%Y') AS purchasedate,p.shippingaddress,p.invoiceno,p.ewaybillno,p.netamount,p.grandtotal,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,p.status,sb.contactname,\r\n"
			+ "			(CASE \r\n"
			+ "			WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ "			ELSE p.igst\r\n"
			+ "			END) AS gstvalue\r\n"
			+ "			from purchase p\r\n"
			+ "			left join purchase_items pi on p.id=pi.purchase_id\r\n"
			+ "			left join supplier s on p.supplier_id=s.id\r\n"
			+ "            left join supplier_sub_contacts sb on s.id=sb.supplier_id\r\n"
			+ "GROUP BY p.id", nativeQuery = true)
    public List<IndexPurchase> indexPurchase(Pageable p);
	
	
	@Query(value = "select p.id,s.cities,s.companyname,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.purchasedate,'%d-%m-%Y') AS purchasedate,p.shippingaddress,p.invoiceno,p.ewaybillno,p.netamount,p.grandtotal,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,p.status,sb.contactname,\r\n"
			+ "			(CASE \r\n"
			+ "			WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ "			ELSE p.igst\r\n"
			+ "			END) AS gstvalue\r\n"
			+ "			from purchase p\r\n"
			+ "			left join supplier s on p.supplier_id=s.id\r\n"
			+ " left join supplier_sub_contacts sb on s.id=sb.supplier_id"
			 + " where p.supplier_id = :sid \r\n"
			 + "GROUP BY p.id", nativeQuery = true)
    public List<IndexPurchase> indexPurchaseSupplier(int sid,Pageable p);
	
	
	@Query(value = "select p.id,s.cities,s.companyname,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.purchasedate,'%d-%m-%Y') AS purchasedate,p.shippingaddress,p.invoiceno,p.ewaybillno,p.netamount,p.igst,p.grandtotal,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,p.status,sb.contactname,\r\n"
			+ "			(CASE \r\n"
			+ "			WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ "			ELSE p.igst\r\n"
			+ "			END) AS gstvalue\r\n"
			+ "			from purchase p\r\n"
			+ "			left join supplier s on p.supplier_id=s.id\r\n"
			+ " left join supplier_sub_contacts sb on s.id=sb.supplier_id"
			 + " where p.supplier_id = :sid"
			 + "GROUP BY p.id", nativeQuery = true)
    public List<IndexPurchase> indexPurchaseSupplier(int sid);
	
	
	
	@Query(value = "select p.id,s.cities,s.companyname,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.purchasedate,'%d-%m-%Y') AS purchasedate,p.shippingaddress,p.invoiceno,p.ewaybillno,p.netamount,p.grandtotal,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,p.status,sb.contactname,"
			+ "(CASE \r\n"
			+ " WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ " ELSE p.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " from purchase p"
			+ " left join supplier s on p.supplier_id=s.id"
			+ " left join supplier_sub_contacts sb on s.id=sb.supplier_id"
			+ " where p.id Like CONCAT('%', :search, '%')"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+ "or p.purchasedate Like CONCAT('%', :search, '%')"
			+ "or p.shippingaddress Like CONCAT('%', :search, '%') "
			+ "or p.invoiceno Like CONCAT('%', :search, '%')"
			+ "or p.ewaybillno Like CONCAT('%', :search, '%') "
			+ "or p.netamount Like CONCAT('%', :search, '%') "
			+ "or p.igst Like CONCAT('%', :search, '%') "
			+ "or p.grandtotal Like CONCAT('%', :search, '%')"
			+ "or p.createbyname Like CONCAT('%', :search, '%')"
			+ "or p.updatedbyname Like CONCAT('%', :search, '%')"
			 + "GROUP BY p.id", nativeQuery = true)
	List<IndexPurchase> searchByPurchase(String search, Pageable p);
	

	@Query(value = "select p.id,s.cities,s.companyname,p.igst,p.sgst,p.cgst,p.vouchermaster_series,DATE_FORMAT(p.purchasedate,'%d-%m-%Y') AS purchasedate,p.shippingaddress,p.invoiceno,p.ewaybillno,p.netamount,p.grandtotal,p.gross_amount,DATE_FORMAT(p.createddate,'%d-%m-%Y') AS createddate,p.createdtime,p.createbyname,p.updatedbyname,DATE_FORMAT(p.updateddate,'%d-%m-%Y') AS updateddate,p.updatedtime,p.status,sb.contactname,"
			+ "(CASE \r\n"
			+ " WHEN p.igst = 0 THEN (p.sgst+p.cgst)\r\n"
			+ " ELSE p.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " from purchase p"
			+ " left join supplier s on p.supplier_id=s.id"
			+ " left join supplier_sub_contacts sb on s.id=sb.supplier_id"
			+ " where (p.id Like CONCAT('%', :search, '%')"
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) "
			+ "or p.purchasedate Like CONCAT('%', :search, '%')"
			+ "or p.shippingaddress Like CONCAT('%', :search, '%') "
			+ "or p.invoiceno Like CONCAT('%', :search, '%')"
			+ "or p.ewaybillno Like CONCAT('%', :search, '%') "
			+ "or p.netamount Like CONCAT('%', :search, '%') "
			+ "or p.igst Like CONCAT('%', :search, '%') "
			+ "or p.grandtotal Like CONCAT('%', :search, '%')"
			+ "or p.createbyname Like CONCAT('%', :search, '%')"
			+ "or p.updatedbyname Like CONCAT('%', :search, '%')) " 
			+"and p.supplier_id = :sid\r\n"
			 + "GROUP BY p.id", nativeQuery = true)
	List<IndexPurchase> searchByPurchase(String search, Pageable p,int sid);
	
	
	Purchase findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
	Purchase findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	
    @Query(value = "SELECT * FROM purchase p order by id desc limit 1",nativeQuery = true)
    Purchase lastrowstatus();
    
	Purchase findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);

}
