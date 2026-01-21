
package com.SCM.repository;

import com.SCM.ExportDto.ExportWorkOrder;
import com.SCM.IndexDto.IndexWorkOrder;
import com.SCM.model.WorkOrder;
import com.SCM.projection.OrderAchievementReportForRetailer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public interface WorkOrderRepo extends JpaRepository<WorkOrder,Integer> {

	
    @Query(value = "CALL GetWorkItemByWorkIds(:primary_key)", nativeQuery = true)
    List<Map<String, Object>> GetWorkItemByWorkIds(@Param("primary_key") int primary_key);

    
    @Query(value = "SELECT * from work_order wo " +
                  "join retailer r on r.id = wo.ret_id " +
                  "where wo.dist_id = ?1 AND (wo.work_order_date BETWEEN ?2 AND ?3) and wo.converted_to_po = false and wo.order_status != false",nativeQuery = true)
    public List<WorkOrder> getWorkOrder(@Param("d_id")Integer d_id, 
    		                            @Param("from_date")String from_date,
                                        @Param("to_date")String to_date);


    @Query(value = "CALL FetchAllWorkOrder()",nativeQuery = true)
    public List<WorkOrder> getAllWorkorderWithRetailer();


    //ase asm rsm nsm zone id

    @Query(value = "select * from work_order wo where wo.aseid = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByAseID(int aseId);

    @Query(value = "select * from work_order wo where wo.asmid = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByAsmID(int asmId);

    @Query(value = "select * from work_order wo where wo.rsmid = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByRsmID(int rsmId);

    @Query(value = "select * from work_order wo where wo.nsmid = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByNsmID(int nsmId);

    @Query(value = "select * from work_order wo where wo.zonesid = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByZoneId(int zoneId);

    @Query(value = "select * from work_order wo where wo.stateid = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderBystateId(int stateId);

    @Query(value = "select * from work_order wo where  wo.dist_id = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByDistributorId(int distId);

    @Query(value = "select * from work_order wo where  wo.retailer_id = ?1",nativeQuery = true)
    public List<WorkOrder> getWorkOrderByRetailerId(int retId);
    
    @Query(value = "select * from work_order wo where  wo.primaryorder_id= ?1",nativeQuery = true)
    public WorkOrder getWorkOrderByPrimaryOrder(int id);
    
    
    public WorkOrder findFirstByOrderByIdDesc();
    
    
    @Query(value = "select wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,DATE_FORMAT(wo.work_order_date,'%d-%m-%Y') AS work_order_date,DATE_FORMAT(wo.createddate,'%d-%m-%Y') AS createddate,wo.createbyname,wo.updatedbyname,wo.createdtime,wo.date_created,CASE\r\n"
    		+ "    WHEN \r\n"
    		+ "        wo.box_product_totalprice = 0 AND\r\n"
    		+ "        wo.kg_product_totalprice = 0 AND\r\n"
    		+ "        wo.corporate_product_totalprice = 0 AND\r\n"
    		+ "        cooker_product_totalprice = 0 AND\r\n"
    		+ "        nosh_product_totalprice = 0\r\n"
    		+ "    THEN\r\n"
    		+ "        wo.gross_total\r\n"
    		+ "    ELSE\r\n"
    		+ "        (wo.box_product_totalprice + wo.kg_product_totalprice + wo.corporate_product_totalprice + cooker_product_totalprice + nosh_product_totalprice)\r\n"
    		+ "END AS Gross_Total\r\n"
    		+ ",wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,GROUP_CONCAT(s.staff_name) as Rsmstaffname,GROUP_CONCAT(s1.staff_name) as Asestaffname,wo.remarks,DATE_FORMAT(wo.updateddate,'%d-%m-%Y') as updateddate,wo.updatedtime from work_order wo\r\n"
    		+ "    		 left join retailer r on wo.ret_id = r.id\r\n"
    		+ "    		  left join state_zone sz on wo.stateid = sz.id\r\n"
    		+ "    		  left join distributor d on d.id = wo.dist_id\r\n"
    		+ "              left join retailer_to_staff rs on r.id=rs.retialer_id\r\n"
    		+ "    		 left join staff s on s.id = rs.rsmid\r\n"
    		+ "    		  left join staff s1 on s1.id = rs.aseid\r\n"
    		+ "              group by  wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,r.trade_name,sz.state_name",nativeQuery = true)
    List<IndexWorkOrder> indexWorkorder(Pageable p);


    @Query(value = "select wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,DATE_FORMAT(wo.work_order_date,'%d-%m-%Y') AS work_order_date,DATE_FORMAT(wo.createddate,'%d-%m-%Y') AS createddate,wo.createbyname,wo.updatedbyname,wo.createdtime,wo.date_created,CASE\r\n"
    		+ "    WHEN \r\n"
    		+ "        wo.box_product_totalprice = 0 AND\r\n"
    		+ "        wo.kg_product_totalprice = 0 AND\r\n"
    		+ "        wo.corporate_product_totalprice = 0 AND\r\n"
    		+ "        cooker_product_totalprice = 0 AND\r\n"
    		+ "        nosh_product_totalprice = 0\r\n"
    		+ "    THEN\r\n"
    		+ "        wo.gross_total\r\n"
    		+ "    ELSE\r\n"
    		+ "        (wo.box_product_totalprice + wo.kg_product_totalprice + wo.corporate_product_totalprice + cooker_product_totalprice + nosh_product_totalprice)\r\n"
    		+ "END AS Gross_Total\r\n"
    		+ ",wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,GROUP_CONCAT(s.staff_name) as Rsmstaffname,GROUP_CONCAT(s1.staff_name) as Asestaffname,wo.remarks,DATE_FORMAT(wo.updateddate,'%d-%m-%Y') as updateddate,wo.updatedtime from work_order wo"
    		+ "   left join retailer r on wo.ret_id = r.id"
    		+ "   left join state_zone sz on wo.stateid = sz.id"
    		+ "   left join distributor d on d.id = wo.dist_id"
    		+ "   left join staff s on s.id = wo.rsmid"
    		+ "   left join staff s1 on s1.id = wo.aseid"
    	    + "   where wo.work_order_id Like CONCAT('%', :search, '%')"
            + "     or wo.net_value Like CONCAT('%', :search, '%')"
            + "     or wo.id Like CONCAT('%', :search, '%')"
            + "     or wo.work_order_date Like CONCAT('%', :search, '%')"
            + "     or wo.createddate Like CONCAT('%', :search, '%')"
            + "     or wo.date_created Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(r.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "  group by  wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,r.trade_name,sz.state_name"
            ,nativeQuery = true)
    List<IndexWorkOrder> SearchByWorkOrderOrder(String search,Pageable p);


    @Query(value = "select wo.id,wo.work_order_id,wo.work_order_date,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks,wo.createddate,wo.createdtime,wo.createbyname,wo.updatedbyname,wo.updateddate,wo.updatedtime\r\n"
    		+ "from work_order wo\r\n"
    		+ "left join retailer r on wo.ret_id = r.id\r\n"
    		+ "left join state_zone sz on wo.stateid = sz.id\r\n"
    		+ "left join distributor d on d.id = wo.dist_id\r\n"
    		+ "left join staff s on s.id = wo.rsmid\r\n"
    		+ "left join staff s1 on s1.id = wo.aseid",nativeQuery = true)
    List<ExportWorkOrder> ExcelexportfromWorkOrder();
    
    
    @Query(value = "CALL WorkorderById(?)",nativeQuery = true)
    List<WorkOrder> getWorkorderById(int wid);
    
    
    @Query(value="select sum(wo.total_qty_kg) as primaryorderqty,\r\n"
			+ "sum(wo.total_qty_pcs) as secondaryorderqty,\r\n"
			+ "sum(wo.staff_cancel_qty) as staffcancelqty,\r\n"
			+ "sum(wo.retailer_cancel_qty) as retailercancelqty,\r\n"
			+ "(sum(wo.total_qty_kg)-sum(wo.staff_cancel_qty))as primarypendingqty,\r\n"
			+ "(sum(wo.total_qty_pcs)-sum(wo.retailer_cancel_qty)) as secondarypendingqty,\r\n"
			+ "woi.product_id as productid\r\n"
			+ " from work_order as wo  left join work_order_item as woi \r\n"
			+ " on wo.id=woi.work_order_itemsid\r\n"
			+ " where wo.retailer_id=?1 \r\n"
			+ " and\r\n"
			+ " wo.date_created between ?2 and  ?3 \r\n"
			+ " group by woi.product_id;",
			nativeQuery = true)
    List<OrderAchievementReportForRetailer> orderAchievementReport(Integer retid,Date startDate,Date endDate);
    
    
    @Query(value="select \r\n"
    		+ "product.product_name as pname\r\n"
    		+ ",sum(wo.total_qty_kg) as primaryorderqty,\r\n"
    		+ "sum(wo.total_qty_pcs) as secondaryorderqty,\r\n"
    		+ "sum(woi.staff_cancel_qty) as staffcancelqty,\r\n"
    		+ "sum(woi.retailer_cancel_qty) as retailercancelqty,\r\n"
    		+ "sum(woi.disributor_cancel_qty) as distributorcancelqty,\r\n"
    		+ "((sum(wo.total_qty_kg))-(sum(woi.staff_cancel_qty))) as primarypendingqty,\r\n"
    		+ "((sum(wo.total_qty_pcs))-(sum(woi.retailer_cancel_qty))) as secondarypendingqty,\r\n"
    		+ "woi.product_id as productid\r\n"
    		+ " from work_order as wo  \r\n"
    		+ " left join work_order_item as woi \r\n"
    		+ " on wo.id=woi.work_order_itemsid\r\n"
    		+ " left join product\r\n"
    		+ " on product.id=woi.product_id\r\n"
    		+ " where wo.retailer_id=:retid \r\n"
    		+ " and\r\n"
    		+ " wo.date_created between:startDate and :endDate\r\n"
    		+ " group by woi.product_id",
			nativeQuery = true)
    List<OrderAchievementReportForRetailer> orderAchievementReportByPagination(int retid,Date startDate,Date endDate,Pageable p);
    

    @Query(value = "select * from work_order where ret_id = ? " , nativeQuery = true)
    public List<WorkOrder> findByret_id(Integer ret_id);
       


    
//    @Query(value = "select * from work_order where ret_id = ? " , nativeQuery = true)
//    public List<WorkOrder> findByret_id(Integer ret_id);
    
    // Retailer can see only retailer part
    
    @Query(value = "select wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo"
    		+ " left join retailer r on wo.ret_id = r.id"
    		+ " left join state_zone sz on wo.stateid = sz.id"
    		+ " left join distributor d on d.id = wo.dist_id"
    		+ " left join staff s on s.id = wo.rsmid"
    		+ " left join staff s1 on s1.id = wo.aseid"
    		+ " where wo.ret_id =:ret_id " , nativeQuery = true)
    public List<IndexWorkOrder> findByRet_id(Integer ret_id);
    
    
    @Query(value = "select wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo"
    		+ " left join retailer r on wo.ret_id = r.id"
    		+ " left join state_zone sz on wo.stateid = sz.id"
    		+ " left join distributor d on d.id = wo.dist_id"
    		+ " left join staff s on s.id = wo.rsmid"
    		+ " left join staff s1 on s1.id = wo.aseid"
    		+ " where wo.ret_id =:ret_id " , nativeQuery = true)
    public List<IndexWorkOrder> findByret_id(Integer ret_id , Pageable p);

   
    @Query(value = "select wo.id,wo.work_order_id,wo.work_order_date,wo.date_created,wo.createddate,wo.createdtime,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname FROM work_order wo "
    		+ "   left join retailer r on wo.ret_id = r.id"
    		+ "   left join state_zone sz on wo.stateid = sz.id"
    		+ "   left join distributor d on d.id = wo.dist_id"
    		+ "   left join staff s on s.id = wo.rsmid"
    		+ "   left join staff s1 on s1.id = wo.aseid"
    	    + "  where (wo.ret_id =:ret_id) and (wo.work_order_id Like CONCAT('%', :search, '%')"
            + "     or wo.net_value Like CONCAT('%', :search, '%')"
            + "     or wo.id Like CONCAT('%', :search, '%')"
            + "     or wo.work_order_date Like CONCAT('%', :search, '%')"
            + "     or wo.createddate Like CONCAT('%', :search, '%')"
            + "     or wo.date_created Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or r.trade_name Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(r.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))",nativeQuery = true)
    List<IndexWorkOrder> SearchByret_id(Integer ret_id,String search,Pageable p);
    
    
    
 // Distributor can see number of retailer 
    
    @Query(value = "select wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo"
    		+ " left join retailer r on wo.ret_id = r.id"
    		+ " left join state_zone sz on wo.stateid = sz.id"
    		+ " left join distributor d on d.id = wo.dist_id"
    		+ " left join staff s on s.id = wo.rsmid"
    		+ " left join staff s1 on s1.id = wo.aseid"
    		+ " where wo.dist_id =:dist_id" , nativeQuery = true)
    public List<IndexWorkOrder> findByDist_id(Integer dist_id);
    
    
    @Query(value = "select wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo"
    		+ " left join retailer r on wo.ret_id = r.id"
    		+ " left join state_zone sz on wo.stateid = sz.id"
    		+ " left join distributor d on d.id = wo.dist_id"
    		+ " left join staff s on s.id = wo.rsmid"
    		+ " left join staff s1 on s1.id = wo.aseid"
    		+ " where wo.dist_id =:dist_id" , nativeQuery = true)
    public List<IndexWorkOrder> findBydist_id(Integer dist_id , Pageable p);
    
    
    @Query(value = "select wo.id,wo.work_order_id,wo.work_order_date,wo.date_created,wo.createddate,wo.createdtime,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname FROM work_order wo "
    		+ "   left join retailer r on wo.retailer_id = r.id"
    		+ "   left join state_zone sz on wo.stateid = sz.id"
    		+ "   left join distributor d on d.id = wo.dist_id"
    		+ "   left join staff s on s.id = wo.rsmid"
    		+ "   left join staff s1 on s1.id = wo.aseid"
    	    + "  where (wo.dist_id =:dist_id) and (wo.work_order_id Like CONCAT('%', :search, '%')"
            + "     or wo.net_value Like CONCAT('%', :search, '%')"
            + "     or wo.id Like CONCAT('%', :search, '%')"
            + "     or wo.work_order_date Like CONCAT('%', :search, '%')"
            + "     or wo.createddate Like CONCAT('%', :search, '%')"
            + "     or wo.date_created Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"   
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(r.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))",nativeQuery = true)
    List<IndexWorkOrder> SearchBydist_id(Integer dist_id , String search,Pageable p);
    
    
    //ASE can see number of retailer and distributor
    
    
    @Query(value = "SELECT wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo\r\n"
    		+ "LEFT JOIN retailer r ON wo.ret_id = r.id\r\n"
    		+ "LEFT JOIN state_zone sz ON wo.stateid = sz.id\r\n"
    		+ "LEFT JOIN distributor d ON d.id = wo.dist_id\r\n"
    		+ "LEFT JOIN staff s ON s.id = wo.rsmid\r\n"
    		+ "LEFT JOIN staff s1 ON s1.id = wo.aseid\r\n"
    		+ "LEFT JOIN retailer_to_staff rts ON wo.ret_id = rts.retialer_id\r\n"
    		+ "WHERE rts.aseid = :aseid", nativeQuery = true)
    public List<IndexWorkOrder> findByaseid(Integer aseid);
    
    
    @Query(value = "SELECT wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo\r\n"
    		+ "LEFT JOIN retailer r ON wo.ret_id = r.id\r\n"
    		+ "LEFT JOIN state_zone sz ON wo.stateid = sz.id\r\n"
    		+ "LEFT JOIN distributor d ON d.id = wo.dist_id\r\n"
    		+ "LEFT JOIN staff s ON s.id = wo.rsmid\r\n"
    		+ "LEFT JOIN staff s1 ON s1.id = wo.aseid\r\n"
    		+ "LEFT JOIN retailer_to_staff rts ON wo.ret_id = rts.retialer_id\r\n"
    		+ "WHERE rts.aseid =:aseid", nativeQuery = true)
    public List<IndexWorkOrder> findByAseid(Integer aseid , Pageable p);
    
    
    @Query(value = "select wo.id,wo.work_order_id,wo.work_order_date,wo.date_created,wo.createddate,wo.createdtime,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname FROM work_order wo "
    		+ "   left join retailer r on wo.ret_id = r.id"
    		+ "   left join state_zone sz on wo.stateid = sz.id"
    		+ "   left join distributor d on d.id = wo.dist_id"
    		+ "   left join staff s on s.id = wo.rsmid"
    		+ "   left join staff s1 on s1.id = wo.aseid"
    	    + "  where (wo.aseid =:aseid) and (wo.work_order_id Like CONCAT('%', :search, '%')"
            + "     or wo.net_value Like CONCAT('%', :search, '%')"
            + "     or wo.id Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(r.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
            + " " ,nativeQuery = true)
    List<IndexWorkOrder> SearchByAseid(Integer aseid , String search,Pageable p);
    
    
    
    //ASM can see number of retailer , distributor and ASE
    
    @Query(value = "SELECT wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo\r\n"
    		+ "LEFT JOIN retailer r ON wo.ret_id = r.id\r\n"
    		+ "LEFT JOIN state_zone sz ON wo.stateid = sz.id\r\n"
    		+ "LEFT JOIN distributor d ON d.id = wo.dist_id\r\n"
    		+ "LEFT JOIN staff s ON s.id = wo.rsmid\r\n"
    		+ "LEFT JOIN staff s1 ON s1.id = wo.aseid\r\n"
    		+ "LEFT JOIN retailer_to_staff rts ON wo.ret_id = rts.retialer_id\r\n"
    		+ "WHERE rts.asmid = :asmid" , nativeQuery = true)
    public List<IndexWorkOrder> findByasmid(Integer asmid);
    
    
    @Query(value = "SELECT wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo\r\n"
    		+ "LEFT JOIN retailer r ON wo.ret_id = r.id\r\n"
    		+ "LEFT JOIN state_zone sz ON wo.stateid = sz.id\r\n"
    		+ "LEFT JOIN distributor d ON d.id = wo.dist_id\r\n"
    		+ "LEFT JOIN staff s ON s.id = wo.rsmid\r\n"
    		+ "LEFT JOIN staff s1 ON s1.id = wo.aseid\r\n"
    		+ "LEFT JOIN retailer_to_staff rts ON wo.ret_id = rts.retialer_id\r\n"
    		+ "WHERE rts.asmid = :asmid" , nativeQuery = true)
    public List<IndexWorkOrder> findByAsmid(Integer asmid , Pageable p);
    
    
    @Query(value = "select wo.id,wo.work_order_id,wo.work_order_date,wo.date_created,wo.createddate,wo.createdtime,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname FROM work_order wo "
    		+ "   left join retailer r on wo.ret_id = r.id"
    		+ "   left join state_zone sz on wo.stateid = sz.id"
    		+ "   left join distributor d on d.id = wo.dist_id"
    		+ "   left join staff s on s.id = wo.rsmid"
    		+ "   left join staff s1 on s1.id = wo.aseid"
    	    + "  where (wo.asmid =:asmid) and (wo.work_order_id Like CONCAT('%', :search, '%')"
            + "     or wo.net_value Like CONCAT('%', :search, '%')"
            + "     or wo.id Like CONCAT('%', :search, '%')"
            + "     or wo.work_order_date Like CONCAT('%', :search, '%')"
            + "     or wo.createddate Like CONCAT('%', :search, '%')"
            + "     or wo.date_created Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(r.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
            + " " ,nativeQuery = true)
    List<IndexWorkOrder> SearchByAsmid(Integer asmid , String search,Pageable p);
    
    
    
    //RSM can see number of retailer , distributor , ASE and ASM
    
    @Query(value = "SELECT wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo\r\n"
    		+ "LEFT JOIN retailer r ON wo.ret_id = r.id\r\n"
    		+ "LEFT JOIN state_zone sz ON wo.stateid = sz.id\r\n"
    		+ "LEFT JOIN distributor d ON d.id = wo.dist_id\r\n"
    		+ "LEFT JOIN staff s ON s.id = wo.rsmid\r\n"
    		+ "LEFT JOIN staff s1 ON s1.id = wo.aseid\r\n"
    		+ "LEFT JOIN retailer_to_staff rts ON wo.ret_id = rts.retialer_id\r\n"
    		+ "WHERE rts.rsmid = :rsmid" , nativeQuery = true)
    public List<IndexWorkOrder> findByrsmid(Integer rsmid);
    
    
    @Query(value = "SELECT wo.id,wo.converted_to_po,wo.order_status,wo.work_order_id,wo.work_order_date,wo.createddate,wo.createdtime,wo.date_created,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,wo.remarks from work_order wo\r\n"
    		+ "LEFT JOIN retailer r ON wo.ret_id = r.id\r\n"
    		+ "LEFT JOIN state_zone sz ON wo.stateid = sz.id\r\n"
    		+ "LEFT JOIN distributor d ON d.id = wo.dist_id\r\n"
    		+ "LEFT JOIN staff s ON s.id = wo.rsmid\r\n"
    		+ "LEFT JOIN staff s1 ON s1.id = wo.aseid\r\n"
    		+ "LEFT JOIN retailer_to_staff rts ON wo.ret_id = rts.retialer_id\r\n"
    		+ "WHERE rts.rsmid = :rsmid" , nativeQuery = true)
    public List<IndexWorkOrder> findByRsmid(Integer rsmid , Pageable p);
    
    
    @Query(value = "select wo.id,wo.work_order_id,wo.work_order_date,wo.date_created,wo.createddate,wo.createdtime,wo.gross_total,wo.net_value,wo.tax_amount,r.trade_name,sz.state_name,d.trade_name as distname,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname FROM work_order wo "
    		+ "   left join retailer r on wo.ret_id = r.id"
    		+ "   left join state_zone sz on wo.stateid = sz.id"
    		+ "   left join distributor d on d.id = wo.dist_id"
    		+ "   left join staff s on s.id = wo.rsmid"
    		+ "   left join staff s1 on s1.id = wo.aseid"
    	    + "  where (wo.rsmid =:rsmid) and (wo.work_order_id Like CONCAT('%', :search, '%')"
            + "     or wo.net_value Like CONCAT('%', :search, '%')"
            + "     or wo.id Like CONCAT('%', :search, '%')"
            + "     or wo.work_order_date Like CONCAT('%', :search, '%')"
            + "     or wo.createddate Like CONCAT('%', :search, '%')"
            + "     or wo.date_created Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or sz.state_name Like CONCAT('%', :search, '%')"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(r.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
            + "     or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
            + " " ,nativeQuery = true)
    List<IndexWorkOrder> SearchByRsmid(Integer rsmid , String search, Pageable p);

    
}

