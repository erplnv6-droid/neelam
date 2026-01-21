package com.SCM.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexSalesExpense;
import com.SCM.IndexDto.SalesExpenseReport;
import com.SCM.IndexDto.SalesExpenseReportItems;
import com.SCM.model.SalesExpense;
import com.SCM.model.Staff;

@Repository
public interface SalesExpRepo extends JpaRepository<SalesExpense, Integer> {

	
	@Query(value = "select staff_name from staff s where s.id= ?1",nativeQuery = true)
	Staff getstaffnameAnddesignation(int staffid);
	
	
	@Query(value = "SELECT se.id,se.staffname,date_format(se.expdate,'%d-%m-%Y') AS expdate,se.dailyallownces,se.hometown,se.designation,se.status,se.createbyadminname,se.createbyrsmname,se.createdbyadminid,se.createdbyrsmid"
			+ " FROM sales_expense se"
			+ " LEFT JOIN staff s ON se.staffid = s.id",nativeQuery = true)
	List<IndexSalesExpense> indexsalesexp(Pageable p);
	
	
	@Query(value = "SELECT DISTINCT se.id,se.staffname,se.expdate,se.dailyallownces,se.hometown,se.designation,se.status,se.createbyadminname,se.createbyrsmname \r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE (s.id = :rid AND r.name= 'ROLE_RSM') OR (mts.rsmid IN (:rid)) AND (sr.role_id = 4 OR sr.role_id = 5)",nativeQuery = true)
	List<IndexSalesExpense> indexsalesASEandASMByRSMid(Pageable p,Long rid);
	

	@Query(value = "SELECT DISTINCT se.id,se.staffname,se.expdate,se.dailyallownces,se.hometown,se.designation,se.status,se.createbyadminname,se.createbyrsmname\r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_ASM' AND s.id = ?1",nativeQuery = true)
	List<IndexSalesExpense> indexsalesExpenseByASM(Pageable p,Long aid);
	
	
	@Query(value = "SELECT DISTINCT se.id,se.staffname,se.expdate,se.dailyallownces,se.hometown,se.designation,se.status,se.createbyadminname,se.createbyrsmname\r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_ASE' AND s.id = ?1",nativeQuery = true)
	List<IndexSalesExpense> indexsalesExpenseByASE(Pageable p,Long aid);

	
	@Query(value = "SELECT se.id,se.staffname,date_format(se.expdate,'%d-%m-%Y') AS expdate,se.dailyallownces,se.hometown,se.designation,se.status,se.createbyadminname,se.createbyrsmname\r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "WHERE REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))\r\n"
			+ "or se.id Like CONCAT('%',:search, '%')"
			+ "or se.expdate Like CONCAT('%',:search, '%')"
			+ "or se.staffname Like CONCAT('%',:search, '%')"
			+ "or se.hometown Like CONCAT('%',:search, '%')", nativeQuery = true)
	List<IndexSalesExpense> SearchBysalesexp(String search, Pageable p);
	
	
	@Query(value = "SELECT se.id,se.staffname,se.expdate,se.dailyallownces,se.hometown,se.designation,se.status\r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN multiple_staff mts ON s.id = mts.staff_id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE (s.id = :rid AND r.name= 'ROLE_RSM') OR (mts.rsmid IN (:rid)) AND (sr.role_id = 4 OR sr.role_id = 5)"
			+ "AND REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''), REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "OR se.id Like CONCAT('%',:search, '%')", nativeQuery = true)
	List<IndexSalesExpense> SearchBysalesexpRSM(Long rid,String search,Pageable p);
	
	
	@Query(value = "SELECT se.id,se.staffname,se.expdate,se.dailyallownces,se.hometown,se.designation,se.status\r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_ASM' AND s.id = :sid"
			+ "AND REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "OR se.id Like CONCAT('%',:search, '%')"
			+ "OR se.expdate Like CONCAT('%',:search, '%')"
			+ "or se.staffname Like CONCAT('%',:search, '%')"
			+ "OR se.hometown Like CONCAT('%',:search, '%')", nativeQuery = true)
	List<IndexSalesExpense> SearchBysalesexpASM(Long sid,String search, Pageable p);
	
	
	@Query(value = "SELECT se.id,se.staffname,se.expdate,se.dailyallownces,se.hometown,se.designation,se.status\r\n"
			+ "FROM sales_expense se\r\n"
			+ "LEFT JOIN staff s ON se.staffid = s.id\r\n"
			+ "LEFT JOIN staff_roles sr ON sr.staff_id = s.id\r\n"
			+ "LEFT JOIN roles r ON sr.role_id = r.id\r\n"
			+ "WHERE r.name = 'ROLE_ASE' AND s.id = :sid"
			+ "AND REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "OR se.id Like CONCAT('%',:search, '%')"
			+ "OR se.expdate Like CONCAT('%',:search, '%')"
			+ "or se.staffname Like CONCAT('%',:search, '%')"
			+ "OR se.hometown Like CONCAT('%',:search, '%')", nativeQuery = true)
	List<IndexSalesExpense> SearchBysalesexpASE(Long sid,String search, Pageable p);
	
	
	SalesExpense getById(int id);
	
	
//	============================
	
	@Modifying
	@Transactional 
	@Query(value = "UPDATE sales_expense_images sei SET sei.salesexpitems_id = NULL WHERE sei.id = ?1",nativeQuery = true)
	void updatesalesexpitems(int id);
	
	
	@Modifying
	@Transactional 
	@Query(value = "DELETE FROM sales_expense_images sei where sei.id = ?1",nativeQuery = true)
	void deletesalesexpimage(int id);
	

//	======================== sales expense report
	
	
//	@Query(value = "SELECT se.id, s.staff_name,se.designation,se.status,se.hometown,se.expdate,se.dailyallownces,se.totalexp,se.otherexpamounttotal,v.countvisit,ROUND(o.secondaryordervalue) AS secondaryordervalue"
//			+ " FROM sales_expense se\r\n"
//			+ " LEFT JOIN staff s ON se.staffid = s.id\r\n"
//			+ " LEFT JOIN (SELECT l.staff_id, COUNT(l.checkin_location_date) AS countvisit \r\n"
//			+ " FROM location l WHERE l.staff_id = :sid AND DATE(str_to_date(l.checkin_location_date, '%d-%m-%Y')) BETWEEN :fromdate AND :todate\r\n"
//			+ " GROUP BY l.staff_id) v ON s.id = v.staff_id\r\n"
//			+ " LEFT JOIN (SELECT wo.created_by, SUM(wo.gross_total) AS secondaryordervalue\r\n"
//			+ " FROM scm.work_order wo WHERE wo.created_by = :sid AND wo.work_order_date BETWEEN :fromdate AND :todate\r\n"
//			+ " GROUP BY wo.created_by) o ON s.id = o.created_by\r\n"
//			+ " WHERE se.staffid = :sid AND se.expdate BETWEEN :fromdate AND :todate",nativeQuery = true)
//	List<SalesExpenseReport> salesexpensereport(int sid,String fromdate,String todate);
	
	@Query(value = "SELECT se.id, s.staff_name, se.designation, se.status, se.hometown, se.expdate, " +
	        "se.dailyallownces, se.totalexp, se.otherexpamounttotal, " +
	        "COALESCE(v.countvisit, 0) AS countvisit, " +
	        "COALESCE(ROUND(o.secondaryordervalue,0), 0) AS secondaryordervalue, " +
	        "COALESCE(SUM(COALESCE(v.countvisit, 0)) OVER (PARTITION BY se.staffid,se.expdate), 0) AS totalcountvisit, " +
	        "COALESCE(round(SUM(COALESCE(o.secondaryordervalue, 0)) OVER (PARTITION BY se.staffid,se.expdate), 0),2) AS totalsecondaryvalue, " +
	        "COALESCE(se.dailyallownces + se.totalexp) AS daytotalexpense " +
	        "FROM sales_expense se " +
	        "LEFT JOIN staff s ON se.staffid = s.id " +
            "LEFT JOIN ( " +
	        "   SELECT l.staff_id, DATE(STR_TO_DATE(l.checkin_location_date, '%d-%m-%Y')) AS visit_date, " +
	        "          COUNT(*) AS countvisit " +
	        "   FROM location l " +
	        "   WHERE l.staff_id = :sid " + 
	        "     AND DATE(STR_TO_DATE(l.checkin_location_date, '%d-%m-%Y')) BETWEEN :fromdate AND :todate " +
	        "   GROUP BY l.staff_id, DATE(STR_TO_DATE(l.checkin_location_date, '%d-%m-%Y')) " +
	        ") v ON se.staffid = v.staff_id AND se.expdate = v.visit_date " +
            "LEFT JOIN ( " +
	        "   SELECT wo.created_by, wo.work_order_date, CASE " +
	         "    	   WHEN "+
	         "    		    wo.box_product_totalprice = 0 AND "+
	         "    		      wo.kg_product_totalprice = 0 AND "+
	         "    		      wo.corporate_product_totalprice = 0 AND "+
	         "    		       cooker_product_totalprice = 0 AND "+
	         "    		       nosh_product_totalprice = 0 "+
	         "    	   THEN "+
	         "    		      wo.gross_total "+
	         "    	  ELSE "+
	         "         (wo.box_product_totalprice + wo.kg_product_totalprice + wo.corporate_product_totalprice + cooker_product_totalprice + nosh_product_totalprice)END AS secondaryordervalue " +
	   "   FROM scm.work_order wo " +
	        "   WHERE wo.created_by = :sid " +
	        "     AND wo.work_order_date BETWEEN :fromdate AND :todate " +
	        "   GROUP BY wo.created_by, wo.work_order_date " +
	        ") o ON s.id = o.created_by AND se.expdate = o.work_order_date " +
            "WHERE se.staffid = :sid " +
	        "  AND se.expdate BETWEEN :fromdate AND :todate and se.status='Complete' " 
, nativeQuery = true)
	List<SalesExpenseReport> salesexpensereport(int sid, String fromdate, String todate);



    @Query(value = "SELECT sei.approvedexpensebyadmin,sei.approvedexpensebyrsm,sei.travelfrom,sei.travelto,sei.modeoftravel,sei.travelfare,sei.otherexpamount,sei.otherexp\r\n"
			+ "FROM sales_expense_items sei\r\n"
			+ "LEFT JOIN sales_expense se ON sei.salesexp_id = se.id\r\n"
			+ "WHERE sei.salesexp_id = ?1",nativeQuery = true)
	List<SalesExpenseReportItems> salesexpensereportItems(int seid);

	
	
}
