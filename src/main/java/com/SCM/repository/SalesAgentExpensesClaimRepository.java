package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.SCM.IndexDto.IndexSalesAgendExpenseClaim;

import com.SCM.IndexDto.IndexSalesAgentExpenseClaim;
import com.SCM.IndexDto.IndexSalesAgentExpenseClaimsByStaffId;
import com.SCM.model.SalesAgentExpenseClaim;

public interface SalesAgentExpensesClaimRepository extends JpaRepository<SalesAgentExpenseClaim, Long>{

	

	@Query(value="select sa.id,sa.created_on_date,sa.created_time,sa.date,sa.filepath,sa.imagename,sa.remark,sa.sales_staff_id,sa.status,sa.time ,staff.staff_name \r\n"
			+ "from staff inner join salesagentexpenseclaim as sa\r\n"
			+ "on staff.id=sa.sales_staff_id where sa.sales_staff_id=?1",nativeQuery = true)
	List<IndexSalesAgendExpenseClaim> findAllSalesAgentClaim(long id);
	
	
	
	@Query(value="select sa.id as id,sa.created_on_date,sa.created_time,sa.date,sa.filepath,sa.imagename,sa.remark,sa.sales_staff_id,sa.status,sa.time ,staff.staff_name \r\n"
			+ "from staff inner join salesagentexpenseclaim as sa\r\n"
			+ "on staff.id=sa.sales_staff_id where sa.sales_staff_id=?1",nativeQuery = true)
	List<IndexSalesAgendExpenseClaim> findAllSalesAgentClaimByPagination(long staffid,Pageable p);
	
	
	
	@Query(value="select sa.id,sa.created_on_date,sa.created_time,sa.date,sa.filepath,sa.imagename,sa.remark,sa.sales_staff_id,sa.status,sa.time ,staff.staff_name \r\n"
			+ " from staff inner join salesagentexpenseclaim as sa\r\n"
			+ " on staff.id=sa.sales_staff_id where sa.sales_staff_id=:staffid  and "
			+ " sa.id like CONCAT('%', :search, '%') "
			+ " or sa.created_on_date Like CONCAT('%', :search, '%')"
			+ " or sa.created_time Like CONCAT('%', :search, '%')"
			+ " or sa.date Like CONCAT('%', :search, '%')"
			+ " or sa.remark Like CONCAT('%', :search, '%')"
			+ " or sa.sales_staff_id Like CONCAT('%', :search, '%')"
			+ " or sa.status Like CONCAT('%', :search, '%')"
			+ " or staff.staff_name Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexSalesAgendExpenseClaim> searchAllSalesAgentClaimByPagination(long staffid,String search,Pageable p);

//	@Query(value = "select s.created_on_date as createdon ,s.remark as remarks, s.status ,s.created_time as createdtime ,s.sales_staff_id as staffid,(select st.staff_name from staff st where st.id=s.sales_staff_id) as staffname from  salesagentexpenseclaim as s;"
//			,nativeQuery = true)
//	List<IndexSalesAgentExpenseClaim> indexSalesAgentExpenseClaims();
//	
	
	@Query(value = "select s.created_on_date as createdon ,s.remark as remarks, s.status ,s.created_time as createdtime ,s.sales_staff_id as staffid,(select st.staff_name from staff st where st.id=s.sales_staff_id) as staffname from  salesagentexpenseclaim as s"
			,nativeQuery = true)
	List<IndexSalesAgentExpenseClaim> indexSalesAgentExpenseClaims(Pageable p);
	
	
	
	@Query(value = "select s.created_on_date as createdon ,s.remark as remarks, s.status ,s.created_time as createdtime ,\r\n"
			+ " s.sales_staff_id as staffid,st.staff_name as staffname \r\n"
			+ " from  salesagentexpenseclaim as s left join \r\n"
			+ " staff as st on s.sales_staff_id=st.id\r\n"
			+ " where replace(s.remark,' ','') like CONCAT('%', :search, '%') or \r\n"
			+ " s.remark like CONCAT('%', :search, '%') \r\n"
			+ " or replace(st.staff_name,' ','') like CONCAT('%', :search, '%') \r\n"
			+ " or st.staff_name like CONCAT('%', :search, '%')\r\n"
			+ " or replace(s.status,' ','') like CONCAT('%', :search, '%') \r\n"
			+ " or s.status like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexSalesAgentExpenseClaim> indexSalesAgentExpenseClaimsSearch(String search,Pageable p);

	
	
	
	
	
	
//	@Query(value="select sc.created_on_date as createddate,sc.approvedexpense as approvedexpense,sc.claimexpense as claimexpense,\r\n"
//			+ "sc.sales_staff_id as staffid,sc.remark as remark,s.staff_name as staffname\r\n"
//			+ "from salesagentexpenseclaim sc \r\n"
//			+ "left join staff s on sc.sales_staff_id=s.id\r\n"
//			+ " where sales_staff_id=2 and sc.created_on_date between '2024-03-10' and '2024-03-30' ",
//			nativeQuery = true)
//	List<IndexSalesAgentExpenseClaimsByStaffId> getsalesAgentExpense();
	
	@Query(value="select sc.created_on_date as createddate,sc.approvedexpense as approvedexpense,sc.claimexpense as claimexpense,\r\n"
			+ "sc.sales_staff_id as staffid,sc.remark as remark,s.staff_name as staffname\r\n"
			+ "from salesagentexpenseclaim sc \r\n"
			+ "left join staff s on sc.sales_staff_id=s.id\r\n"
			+ " where sales_staff_id=?1 and sc.created_on_date between ?2 and ?3 ",
			nativeQuery = true)
	List<IndexSalesAgentExpenseClaimsByStaffId> getsalesAgentExpenseByStaffid(long id,String startDate,String endDate,Pageable p);
	
	
	@Query(value="select sc.created_on_date as createddate,sc.approvedexpense as approvedexpense,sc.claimexpense as claimexpense,\r\n"
			+ "sc.sales_staff_id as staffid,sc.remark as remark,s.staff_name as staffname\r\n"
			+ "from salesagentexpenseclaim sc \r\n"
			+ "left join staff s on sc.sales_staff_id=s.id\r\n"
			+ " where sales_staff_id=?1 and sc.created_on_date between ?2 and ?3 ",
			nativeQuery = true)
	List<IndexSalesAgentExpenseClaimsByStaffId> getsalesAgentExpenseByStaffid(long id,String startDate,String endDate);
	
	
	@Query(value="select sc.created_on_date as createddate,sc.approvedexpense as approvedexpense,sc.claimexpense as claimexpense,\r\n"
			+ " sc.sales_staff_id as staffid,sc.remark as remark,s.staff_name as staffname\r\n"
			+ " from salesagentexpenseclaim sc \r\n"
			+ " left join staff s on sc.sales_staff_id=s.id\r\n"
			+ " where sales_staff_id=?1 and sc.created_on_date between ?2 and ?3 and "
			+ " (sc.approvedexpense like CONCAT('%', ?4, '%') or"
			+ " sc.claimexpense like CONCAT('%', ?4, '%') or"
			+ " sc.remark like CONCAT('%', ?4, '%') or"
			+ " s.staff_name like CONCAT('%', ?4, '%'))",
			nativeQuery = true)
	List<IndexSalesAgentExpenseClaimsByStaffId> searchsalesAgentExpenseByStaffid(long id,String startDate,String endDate,String search,Pageable p);
	
}
