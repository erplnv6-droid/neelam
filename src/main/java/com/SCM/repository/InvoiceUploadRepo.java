package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexInvoiceUpload;
import com.SCM.model.InvoiceUpload;

@Repository
public interface InvoiceUploadRepo extends JpaRepository<InvoiceUpload, Integer> {

	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime "
			+ "FROM invoice_upload iu\r\n"
			+ "LEFT JOIN distributor d on iu.distributor_id = d.id",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoice(Pageable p);
	
	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime "
			+ " FROM invoice_upload iu\r\n"
			+ " LEFT JOIN distributor d on iu.distributor_id = d.id"
			+ " WHERE iu.distributor_id = ?1",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByDistributor(Pageable p,int distid);
	
	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime\r\n"
			+ "FROM invoice_upload iu\r\n"
			+ "LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "WHERE iu.distributor_id = ?1",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByDistributor(int distid);
	
	
	@Query(value = " SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime \r\n"
			+ "			 FROM invoice_upload iu\r\n"
			+ "			 LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "             LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "             LEFT JOIN staff s on ds.rsmid=s.id\r\n"
			+ "			 WHERE ds.rsmid = :distid",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByRsm(Pageable p,int distid);
	
	@Query(value = " SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime \r\n"
			+ "			 FROM invoice_upload iu\r\n"
			+ "			 LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "             LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "             LEFT JOIN staff s on ds.rsmid=s.id\r\n"
			+ "			 WHERE ds.rsmid = :distid",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByRsm(int distid);
	
	
	@Query(value = " SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime \r\n"
			+ "			 FROM invoice_upload iu\r\n"
			+ "			 LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "             LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "             LEFT JOIN staff s on ds.asmid=s.id\r\n"
			+ "			 WHERE ds.asmid = :distid",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByAsm(Pageable p,int distid);
	
	@Query(value = " SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime \r\n"
			+ "			 FROM invoice_upload iu\r\n"
			+ "			 LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "             LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "             LEFT JOIN staff s on ds.asmid=s.id\r\n"
			+ "			 WHERE ds.asmid = :distid",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByAsm(int distid);
	
	
	@Query(value = " SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime \r\n"
			+ "			 FROM invoice_upload iu\r\n"
			+ "			 LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "             LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "             LEFT JOIN staff s on ds.aseid=s.id\r\n"
			+ "			 WHERE ds.aseid = :distid",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByAse(Pageable p,int distid);
	
	@Query(value = " SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime \r\n"
			+ "			 FROM invoice_upload iu\r\n"
			+ "			 LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "             LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "             LEFT JOIN staff s on ds.aseid=s.id\r\n"
			+ "			 WHERE ds.aseid = :distid",nativeQuery = true)
	List<IndexInvoiceUpload> IndexInvoiceByAse(int distid);
	
	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime\r\n"
			+ "FROM invoice_upload iu\r\n"
			+ "LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "WHERE iu.id Like CONCAT('%', :search, '%')\r\n"
			+ "OR iu.voucherdate Like CONCAT('%', :search, '%')"
			+ "OR iu.voucherno Like CONCAT('%', :search, '%')"
			+ "OR REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "OR iu.createbyname Like CONCAT('%', :search, '%')"
			+ "OR iu.createddate Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexInvoiceUpload> SearchInvoice(String search, Pageable p);
	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime\r\n"
			+ "FROM invoice_upload iu\r\n"
			+ "LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN staff s on ds.rsmid=s.id\r\n"
			+ "WHERE ds.rsmid=:id Like CONCAT('%', :search, '%')\r\n"
			+ "OR iu.voucherdate Like CONCAT('%', :search, '%')"
			+ "OR iu.voucherno Like CONCAT('%', :search, '%')"
			+ "OR REGEXP_LIKE(LOWER(REGEXP_REPLACE(d.trade_name, '[^a-z0-9]', '')),CONCAT('.*', LOWER(REGEXP_REPLACE(:search, '[^a-z0-9]', '')), '.*'))"
            + "OR iu.createbyname Like CONCAT('%', :search, '%')"
			+ "OR iu.createddate Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexInvoiceUpload> SearchInvoiceRsm(int id,String search, Pageable p);
	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime\r\n"
			+ "FROM invoice_upload iu\r\n"
			+ "LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN staff s on ds.asmid=s.id\r\n"
			+ "WHERE ds.asmid=:id Like CONCAT('%', :search, '%')\r\n"
			+ "OR iu.voucherdate Like CONCAT('%', :search, '%')"
			+ "OR iu.voucherno Like CONCAT('%', :search, '%')"
			+ "OR REGEXP_LIKE(LOWER(REGEXP_REPLACE(d.trade_name, '[^a-z0-9]', '')),CONCAT('.*', LOWER(REGEXP_REPLACE(:search, '[^a-z0-9]', '')), '.*'))"
            + "OR iu.createbyname Like CONCAT('%', :search, '%')"
			+ "OR iu.createddate Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexInvoiceUpload> SearchInvoiceAsm(int id,String search, Pageable p);
	
	@Query(value = "SELECT iu.id,iu.amount,DATE_FORMAT(iu.voucherdate,'%d-%m-%Y') as voucherdate,iu.voucherno,d.trade_name,iu.imglocation,iu.pdflocation,iu.createbyname,DATE_FORMAT(iu.createddate,'%d-%m-%Y') as createddate,iu.createdtime,iu.updatedbyname,DATE_FORMAT(iu.updateddate,'%d-%m-%Y') as updateddate,iu.updatedtime\r\n"
			+ "FROM invoice_upload iu\r\n"
			+ "LEFT JOIN distributor d on iu.distributor_id = d.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds on d.id=ds.distributor_id\r\n"
			+ "LEFT JOIN staff s on ds.aseid=s.id\r\n"
			+ "WHERE ds.aseid=:id Like CONCAT('%', :search, '%')\r\n"
			+ "OR iu.voucherdate Like CONCAT('%', :search, '%')"
			+ "OR iu.voucherno Like CONCAT('%', :search, '%')"
			+ "OR REGEXP_LIKE(LOWER(REGEXP_REPLACE(d.trade_name, '[^a-z0-9]', '')),CONCAT('.*', LOWER(REGEXP_REPLACE(:search, '[^a-z0-9]', '')), '.*'))"
            + "OR iu.createbyname Like CONCAT('%', :search, '%')"
			+ "OR iu.createddate Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexInvoiceUpload> SearchInvoiceAse(int id,String search, Pageable p);
}
