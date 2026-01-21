package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexMaterialNote;
import com.SCM.IndexDto.MaterialReceiptProjection;
import com.SCM.model.MaterialRecepitNote;
import com.SCM.model.Sales;
import com.SCM.model.SupplierDeliveryNote;
import com.SCM.model.VoucherMaster;

@Repository
public interface MaterialReceiptNoteRepo  extends JpaRepository<MaterialRecepitNote, Integer> {

	
	@Query(value = "select * from material_recepit_note where material_recepit_note.voucherseries like  CONCAT('%', :search, '%') order by id desc limit 1 ",nativeQuery = true)
	MaterialRecepitNote searchByVoucher(String search);
	
	
	@Query(value = "select * from material_recepit_note where supplierdeliverynote_id = ?1",nativeQuery = true)
	public List<MaterialRecepitNote>  fetchMRNBySDN(int supplierdeliverynoteId);
	

	@Query(value = "select m.id,s.cities,m.igst,m.sgst,m.cgst,m.vouchermaster_series,DATE_FORMAT(m.mrndate,'%d-%m-%Y') AS mrndate,s.companyname,m.grossamount,m.grandtotal,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.status,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN m.igst = 0 THEN (m.sgst+m.cgst) \r\n"
			+ "ELSE m.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from material_recepit_note m\r\n"
			+ "left join supplier s on m.supplier_id=s.id"
			  , nativeQuery = true)
    public List<IndexMaterialNote> indexMaterialReceipt(Pageable p);
	
	
	@Query(value = "select m.id,s.cities,m.igst,m.sgst,m.cgst,m.vouchermaster_series,DATE_FORMAT(m.mrndate,'%d-%m-%Y') AS mrndate,s.companyname,m.grossamount,m.grandtotal,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.status,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN m.igst = 0 THEN (m.sgst+m.cgst) \r\n"
			+ "ELSE m.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from material_recepit_note m\r\n"
			+ "left join supplier s on m.supplier_id=s.id "
			 + " where m.supplier_id = :sid", nativeQuery = true)
    public List<IndexMaterialNote> indexMaterialReceiptSupplier(Pageable p,int sid);
	
	
	@Query(value = "select m.id,s.cities,m.igst,m.sgst,m.cgst,m.vouchermaster_series,DATE_FORMAT(m.mrndate,'%d-%m-%Y') AS mrndate,s.companyname,m.grossamount,m.grandtotal,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.status,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime,\r\n"
			+ "(CASE \r\n"
			+ "WHEN m.igst = 0 THEN (m.sgst+m.cgst) \r\n"
			+ "ELSE m.igst\r\n"
			+ "END) AS gstvalue\r\n"
			+ "from material_recepit_note m\r\n"
			+ "left join supplier s on m.supplier_id=s.id "
			 + " where m.supplier_id = :sid", nativeQuery = true)
    public List<IndexMaterialNote> indexMaterialReceiptSupplier(int sid);
	
	
	
//	@Query(value = "select id,mrndate,supplier_id,grossamount,igst,grandtotal,branch_id,companyid from material_recepit_note " ,nativeQuery = true )
//    public List<MaterialRecepitNote> indexMaterialReceipt(Pageable p);
	
	
	
	@Query(value = "select m.id,s.cities,m.igst,m.sgst,m.cgst,m.vouchermaster_series,DATE_FORMAT(m.mrndate,'%d-%m-%Y') AS mrndate,m.status,s.companyname,m.grossamount,m.grandtotal,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime,"
			+ " (CASE \r\n"
			+ " WHEN m.igst = 0 THEN (m.sgst+m.cgst) \r\n"
			+ " ELSE m.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " from material_recepit_note m"
			+ " left join supplier s on m.supplier_id=s.id "
			+ " where m.id Like CONCAT('%', :search, '%')"
			+ " or m.mrndate Like CONCAT('%', :search, '%') "
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or m.grossamount Like CONCAT('%', :search, '%') "
			+ " or m.igst Like CONCAT('%', :search, '%')"
			+ " or m.status Like CONCAT('%', :search, '%')"
			+ " or s.companyname Like CONCAT('%', :search, '%')"
			+ " or m.grandtotal Like CONCAT('%', :search, '%') " , nativeQuery = true)
	List<IndexMaterialNote> searchByMaterialReceipt(String search, Pageable p);
	
	
	
	
	
	
	
	@Query(value = "select m.id,s.cities,m.igst,m.sgst,m.cgst,m.vouchermaster_series,DATE_FORMAT(m.mrndate,'%d-%m-%Y') AS mrndate,m.status,s.companyname,m.grossamount,m.grandtotal,DATE_FORMAT(m.createddate,'%d-%m-%Y') AS createddate,m.createdtime,m.createbyname,m.updatedbyname,DATE_FORMAT(m.updateddate,'%d-%m-%Y') AS updateddate,m.updatedtime,"
			+ " (CASE \r\n"
			+ " WHEN m.igst = 0 THEN (m.sgst+m.cgst) \r\n"
			+ " ELSE m.igst\r\n"
			+ " END) AS gstvalue\r\n"
			+ " from material_recepit_note m"
			+ " left join supplier s on m.supplier_id=s.id "
			+ " where (m.id Like CONCAT('%', :search, '%')"
			+ " or m.mrndate Like CONCAT('%', :search, '%') "
			+ " or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ " or m.grossamount Like CONCAT('%', :search, '%') "
			+ " or m.igst Like CONCAT('%', :search, '%')"
			+ " or m.status Like CONCAT('%', :search, '%')"
			+ " or s.companyname Like CONCAT('%', :search, '%')"
			+ " or m.grandtotal Like CONCAT('%', :search, '%')) "
			+ "and m.supplier_id=:sid", nativeQuery = true)
	List<IndexMaterialNote> searchByMaterialReceiptSupplier(String search, Pageable p,int sid);
	
	
//	------------- voucher
	
    @Query(value = "SELECT * FROM material_recepit_note mrn order by id desc limit 1",nativeQuery = true)
    MaterialRecepitNote lastrowstatus();
    
    MaterialRecepitNote findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);


    MaterialRecepitNote findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
	
    MaterialRecepitNote findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	
	
}
