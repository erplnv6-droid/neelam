package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.dtoReports.ContractMgmtReport;
import com.SCM.model.ContractManagement;

@Repository
public interface ContractManagementRepo extends JpaRepository<ContractManagement, Integer> {
    
	
	@Query(value = "SELECT cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') AS createddate,cm.createdtime,cm.createbyname,cm.updatedbyname,cm.id,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.remarks,cm.taxableamount,cm.taxamount,cm.totalamount,s.companyname,DATE_FORMAT(cm.updateddate,'%d-%m-%Y') as updateddate,cm.updatedtime\r\n"
			+ "FROM contract_management cm\r\n"
			+ "LEFT JOIN supplier s on cm.supplier_id = s.id",nativeQuery = true)
	public List<com.SCM.IndexDto.IndexContractManagement> IndexContractManagement(Pageable p);
	
	
	@Query(value = "SELECT cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') AS createddate,cm.createdtime,cm.createbyname,cm.updatedbyname,cm.id,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.remarks,cm.taxableamount,cm.taxamount,cm.totalamount,s.companyname,DATE_FORMAT(cm.updateddate,'%d-%m-%Y') as updateddate,cm.updatedtime\r\n"
			+ "FROM contract_management cm\r\n"
			+ "LEFT JOIN supplier s on cm.supplier_id = s.id"
			+" where cm.supplier_id=:sid",nativeQuery = true)
	public List<com.SCM.IndexDto.IndexContractManagement> IndexContractManagementSupplier(int sid,Pageable p);
	
	@Query(value = "SELECT cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') AS createddate,cm.createdtime,cm.createbyname,cm.updatedbyname,cm.id,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.remarks,cm.taxableamount,cm.taxamount,cm.totalamount,s.companyname,DATE_FORMAT(cm.updateddate,'%d-%m-%Y') as updateddate,cm.updatedtime\r\n"
			+ "FROM contract_management cm\r\n"
			+ "LEFT JOIN supplier s on cm.supplier_id = s.id"
			+" where cm.supplier_id=:sid",nativeQuery = true)
	public List<com.SCM.IndexDto.IndexContractManagement> IndexContractManagementSupplier(int sid);
	
	
	@Query(value = "SELECT cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') AS createddate,cm.createdtime,cm.createbyname,cm.updatedbyname,cm.id,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.remarks,cm.taxableamount,cm.taxamount,cm.totalamount,s.companyname,DATE_FORMAT(cm.updateddate,'%d-%m-%Y') as updateddate,cm.updatedtime\r\n"
			+ "FROM contract_management cm\r\n"
			+ "LEFT JOIN supplier s on cm.supplier_id = s.id\r\n"
			+ "WHERE cm.id Like CONCAT('%', :search, '%')"
			+ " OR    cm.fromdate Like CONCAT('%', :search, '%')"
			+ " OR    cm.todate Like CONCAT('%', :search, '%')"
			+ " OR    cm.remarks Like CONCAT('%', :search, '%')"
			+ " OR    cm.taxableamount Like CONCAT('%', :search, '%')"
			+ " OR    cm.contracttype Like CONCAT('%', :search, '%')"
			+ " OR    cm.taxamount Like CONCAT('%', :search, '%')"
			+ " OR    cm.totalamount Like CONCAT('%', :search, '%')"
			+ " OR    cm.createbyname Like CONCAT('%', :search, '%')"
			+ " OR    cm.updatedbyname Like CONCAT('%', :search, '%')"
			+ " OR    s.companyname Like CONCAT('%', :search, '%')",nativeQuery = true)
	public List<com.SCM.IndexDto.IndexContractManagement> SearchByContractManagement(String search, Pageable p);
	
	
	@Query(value = "SELECT cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') AS createddate,cm.createdtime,cm.createbyname,cm.updatedbyname,cm.id,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.remarks,cm.taxableamount,cm.taxamount,cm.totalamount,s.companyname,DATE_FORMAT(cm.updateddate,'%d-%m-%Y') as updateddate,cm.updatedtime\r\n"
			+ "FROM contract_management cm\r\n"
			+ "LEFT JOIN supplier s on cm.supplier_id = s.id\r\n"
			+ "WHERE (cm.id Like CONCAT('%', :search, '%')"
			+ " OR    cm.fromdate Like CONCAT('%', :search, '%')"
			+ " OR    cm.todate Like CONCAT('%', :search, '%')"
			+ " OR    cm.remarks Like CONCAT('%', :search, '%')"
			+ " OR    cm.taxableamount Like CONCAT('%', :search, '%')"
			+ " OR    cm.contracttype Like CONCAT('%', :search, '%')"
			+ " OR    cm.taxamount Like CONCAT('%', :search, '%')"
			+ " OR    cm.totalamount Like CONCAT('%', :search, '%')"
			+ " OR    cm.createbyname Like CONCAT('%', :search, '%')"
			+ " OR    cm.updatedbyname Like CONCAT('%', :search, '%')"
			+ " OR    s.companyname Like CONCAT('%', :search, '%')) " 
			+ " and cm.supplier_id=:sid",nativeQuery = true)
	public List<com.SCM.IndexDto.IndexContractManagement> SearchByContractManagement(String search, Pageable p,int sid);
	
	
	@Query(value = "SELECT cm.id,s.companyname,cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') as approvaldate,s.gstno,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.taxableamount,cm.taxamount,cm.totalamount,cm.remarks,abs(datediff(fromdate,todate)) as remaningdays FROM contract_management cm\r\n"
			+ " LEFT JOIN supplier s ON  cm.supplier_id = s.id",nativeQuery = true)
	public List<ContractMgmtReport> ContractManagementReport();
	
	
	@Query(value = "SELECT cm.id,s.companyname,cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') as approvaldate,s.gstno,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.taxableamount,cm.taxamount,cm.totalamount,cm.remarks,abs(datediff(fromdate,todate)) as remaningdays FROM contract_management cm\r\n"
			+ " LEFT JOIN supplier s ON  cm.supplier_id = s.id",nativeQuery = true)
	public List<ContractMgmtReport> ContractManagementReport(Pageable p);
	
	
	@Query(value = "SELECT cm.id,s.companyname,cm.contracttype,cm.contracttype,DATE_FORMAT(cm.createddate,'%d-%m-%Y') as approvaldate,s.gstno,DATE_FORMAT(cm.fromdate,'%d-%m-%Y') AS fromdate,DATE_FORMAT(cm.todate,'%d-%m-%Y') AS todate,cm.taxableamount,cm.taxamount,cm.totalamount,cm.remarks,abs(datediff(fromdate,todate)) as remaningdays FROM contract_management cm\r\n"
			+ " LEFT JOIN supplier s ON  cm.supplier_id = s.id"
			+ " WHERE cm.id Like CONCAT('%',:search, '%')"
			+ " OR cm.id Like CONCAT('%',:search, '%')"
			+ " OR cm.contracttype Like CONCAT('%',:search, '%')"
			+ " OR cm.contracttype Like CONCAT('%',:search, '%')"
			+ " OR cm.createddate Like CONCAT('%',:search, '%')"
			+ " OR cm.fromdate Like CONCAT('%',:search, '%')"
			+ " OR cm.todate Like CONCAT('%',:search, '%')"
			+ " OR cm.remarks Like CONCAT('%',:search, '%')"
			+ " OR s.companyname Like CONCAT('%',:search, '%')"
			+ " OR s.gstno Like CONCAT('%',:search, '%')",nativeQuery = true)
	public List<ContractMgmtReport> SearchContractManagementReport(Pageable p,String search);
	
}
