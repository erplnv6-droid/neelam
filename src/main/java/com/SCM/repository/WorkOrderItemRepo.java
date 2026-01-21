package com.SCM.repository;

import com.SCM.IndexDto.IndexWorkorderItemByRetailerId;
import com.SCM.model.WorkOrderItem;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface WorkOrderItemRepo extends JpaRepository<WorkOrderItem, Integer> {
	
    @Modifying
    @Query(value = "delete from work_order_item wi where wi.wid = ?1", nativeQuery = true)
    @Transactional
    void deleteWorkOrderItemsByWorkorderId(Integer id);
    
    @Modifying
    @Query(value = "update work_order_item wi set wi.estimated_days = ?1 where wi.w_item_id = ?2", nativeQuery = true)
    @Transactional
    public void updateWorkOrderEstimatedDaysById(Date days,int id, int pid);
    
    
    
    
    
    
//    @Query(value="select wi.amount as amount,wi.cgst_ledger as cgstledger,wi.discount as discount,wi.discount1 as discount1,wi.dlp\r\n"
//    		+ "as dlp,wi.grossamount as grossamount,wi.gstvalue as gstvalue,wi.igst as igst,wi.igst_ledger as igstledger\r\n"
//    		+ ", wi.measurement as measurement,wi.mrp as mrp,wi.product_id as productid,wi.product_name as productname,\r\n"
//    		+ "wi.product_type as producttype,wi.qty as qty,wi.sgst_ledger as sgstledger,wi.standard_qty_per_box as stdqtyperbox,\r\n"
//    		+ "wi.text as text,wi.total as total,wi.unitofmeasurement as unitomeasurement,wi.uom_secondary as uomsecondary,\r\n"
//    		+ "wi.wid as witemid,wi.estimated_days as estimateddays,\r\n"
//    		+ "wi.disributor_cancel_qty as distcancelqty,wi.retailer_cancel_qty as retcancelqty,wi.staff_cancel_qty as staffcancelqty\r\n"
//    		+ ",w.retailer_id as retid\r\n"
//    		+ "from work_order_item wi \r\n"
//    		+ "left join work_order w on wi.wid=w.id \r\n"
//    		+ "where w.date_created between '2022-01-01' and '2024-01-01' and w.retailer_id=2"
//    		, nativeQuery =true)
//    public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDate();
    
    
//    @Query(value="  select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount,\r\n"
//    		+ "			wi.gstvalue as taxvalue,wi.amount as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname,\r\n"
//    		+ "            group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup,\r\n"
//    		+ "            wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt,\r\n"
//    		+ "            wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime\r\n"
//    		+ "			,wi.product_name as productname\r\n"
//    		+ "			from work_order_item as wi left join\r\n"
//    		+ "			work_order as w on wi.wid=w.id\r\n"
//    		+ "            left join retailer as r on w.ret_id=r.id\r\n"
//    		+ "            left join zone as z on r.zonesid=z.id\r\n"
//    		+ "            left join states as st on r.stateid=st.id\r\n"
//    		+ "            left join product as pt on wi.product_id=pt.id\r\n"
//    		+ "            left join retailer_to_staff as rs on r.id=rs.retialer_id\r\n"
//    		+ "			left join staff as s on rs.rsmid=s.id\r\n"
//    		+ "            left join staff as s1 on rs.aseid=s1.id\r\n"
//    		+ "            where w.date_created between ?1 and ?2 and w.ret_id=?3 and COALESCE(p.createddate, p.date_created) IS NOT NULL  group by w.id ,wi.amount,wi.grossamount,\r\n"
//    		+ "			wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name,\r\n"
//    		+ "            pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty,\r\n"
//    		+ "            wi.discount,wi.discount1,w.createbyname,w.createdtime\r\n"
//    		+ "			,wi.product_name", nativeQuery =true)
//    public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDate(String startDate,String endDate,int retid,org.springframework.data.domain.Pageable p);
    
//    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount,\r\n"
//    		+ "			wi.gstvalue as taxvalue,wi.amount as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname,\r\n"
//    		+ "            group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup,\r\n"
//    		+ "            wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt,\r\n"
//    		+ "            wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime\r\n"
//    		+ "			,wi.product_name as productname\r\n"
//    		+ "			from work_order_item as wi left join\r\n"
//    		+ "			work_order as w on wi.wid=w.id\r\n"
//    		+ "            left join retailer as r on w.ret_id=r.id\r\n"
//    		+ "            left join zone as z on r.zonesid=z.id\r\n"
//    		+ "            left join states as st on r.stateid=st.id\r\n"
//    		+ "            left join product as pt on wi.product_id=pt.id\r\n"
//    		+ "            left join retailer_to_staff as rs on r.id=rs.retialer_id\r\n"
//    		+ "			left join staff as s on rs.rsmid=s.id\r\n"
//    		+ "            left join staff as s1 on rs.aseid=s1.id\r\n"
//    		+ "WHERE w.ret_id = :retid "
//    		+"AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " 
//    		+ "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate "
//    		+ "  group by w.id ,wi.amount,wi.grossamount,\r\n"
//    		+ "			wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name,\r\n"
//    		+ "            pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty,\r\n"
//    		+ "            wi.discount,wi.discount1,w.createbyname,w.createdtime\r\n"
//    		+ "			,wi.product_name", nativeQuery =true)
//    public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePagination(String startdate,String enddate,int retid,Pageable p);
    
    
    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePagination(String startdate, String enddate, int retid, Pageable p);

    
    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "left join distributor as d on r.distributor_id=d.id " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePaginationDistributor(String startdate, String enddate, int retid, Pageable p);

    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "left join distributor as d on r.distributor_id=d.id " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePaginationDistributor(String startdate, String enddate, int retid);  
    
    
    
 
    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "left join distributor as d on r.distributor_id=d.id " +
            "left join distributor_to_staff as ds on d.id=ds.distributor_id and s.id=ds.rsmid " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePaginationRsm(String startdate, String enddate, int retid, Pageable p);
    
 
    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "left join distributor as d on r.distributor_id=d.id " +
            "left join distributor_to_staff as ds on d.id=ds.distributor_id and s.id=ds.asmid " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePaginationAsm(String startdate, String enddate, int retid, Pageable p);   
    
 

    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "left join distributor as d on r.distributor_id=d.id " +
            "left join distributor_to_staff as ds on d.id=ds.distributor_id and s.id=ds.aseid " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDatePaginationAse(String startdate, String enddate, int retid, Pageable p);   
    
    

    
    @Query(value="select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount, " +
            "wi.gstvalue as taxvalue,w.gross_total as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname, " +
            "group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup, " +
            "wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt, " +
            "wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime " +
            ",wi.product_name as productname " +
            "from work_order_item as wi left join " +
            "work_order as w on wi.wid=w.id " +
            "left join retailer as r on w.ret_id=r.id " +
            "left join zone as z on r.zonesid=z.id " +
            "left join states as st on r.stateid=st.id " +
            "left join product as pt on wi.product_id=pt.id " +
            "left join retailer_to_staff as rs on r.id=rs.retialer_id " +
            "left join staff as s on rs.rsmid=s.id " +
            "left join staff as s1 on rs.aseid=s1.id " +
            "WHERE w.ret_id = :retid " +
            "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
            "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate " +
            "group by w.id ,w.gross_total,wi.grossamount, " +
            "wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name, " +
            "pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty, " +
            "wi.discount,wi.discount1,w.createbyname,w.createdtime " +
            ",wi.product_name", nativeQuery =true)
public List<IndexWorkorderItemByRetailerId> getWorkOrderItemsByRetailerIdWithinDate(String startdate, String enddate, int retid);

    
    
//    @Query(value=" select w.id as orderid,wi.total as taxablevalue ,wi.grossamount as grossamount,\r\n"
//    		+ "			wi.gstvalue as taxvalue,wi.amount as totalvalue,w.createddate as ordercreateddate,w.date_created as orderdate,r.trade_name as tradename,r.city as city,st.name as state,z.zone_name as zone,group_concat(s.staff_name) as rsmname,\r\n"
//    		+ "            group_concat(s1.staff_name) as asename,pt.ean_code as eancode,pt.product_group as productgroup,\r\n"
//    		+ "            wi.dlp as rate,wi.measurement as qtyprm,wi.qty as qtyalt,\r\n"
//    		+ "            wi.discount as 1stdis,wi.discount1 as 2nddis,w.createbyname as createdby,w.createdtime as createdtime\r\n"
//    		+ "			,wi.product_name as productname\r\n"
//    		+ "			from work_order_item as wi left join\r\n"
//    		+ "			work_order as w on wi.wid=w.id\r\n"
//    		+ "            left join retailer as r on w.ret_id=r.id\r\n"
//    		+ "            left join zone as z on r.zonesid=z.id\r\n"
//    		+ "            left join states as st on r.stateid=st.id\r\n"
//    		+ "            left join product as pt on wi.product_id=pt.id\r\n"
//    		+ "            left join retailer_to_staff as rs on r.id=rs.retialer_id\r\n"
//    		+ "			left join staff as s on rs.rsmid=s.id\r\n"
//    		+ "            left join staff as s1 on rs.aseid=s1.id\r\n"
//    		+ "            where w.date_created between ?1 and ?2 and w.ret_id=?3 "
//    		+ " and (w.id Like CONCAT('%', :search, '%') "
//    		+ " or wi.total Like CONCAT('%', :search, '%') "
//    		+ " or pt.product_group Like CONCAT('%', :search, '%') "
//    		+ " or r.trade_name Like CONCAT('%', :search, '%') "
//    		 + "OR s.staff_name LIKE CONCAT('%', :search, '%') "
// 	        + "OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
//    		+ " group by w.id,wi.grossamount,\r\n"
//    	    		+ "			wi.gstvalue,wi.total,w.createddate,w.date_created,r.trade_name,r.city,st.name,z.zone_name,\r\n"
//    	    		+ "            pt.ean_code,pt.product_group,wi.dlp,wi.measurement,wi.qty,\r\n"
//    	    		+ "            wi.discount,wi.discount1,w.createbyname,w.createdtime\r\n"
//    	    		+ "			,wi.product_name"
//    		,nativeQuery =true)
//    public List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDate(String startDate,String endDate,int retid,String search,org.springframework.data.domain.Pageable p);
    
    
    
//    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
//            + "wi.gstvalue AS taxvalue, wi.amount AS totalvalue, w.createddate AS ordercreateddate, "
//            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
//            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
//            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
//            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
//            + "w.createdtime AS createdtime, wi.product_name AS productname "
//            + "FROM work_order_item AS wi "
//            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
//            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
//            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
//            + "LEFT JOIN states AS st ON r.stateid = st.id "
//            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
//            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
//            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
//            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
//            + "WHERE w.date_created BETWEEN :startDate AND :endDate "
//            + "AND w.ret_id = :retid "
//            + "AND ((replace(w.id,' ','') LIKE CONCAT('%', :search,'%') or w.id LIKE CONCAT('%', :search, '%')) "
//            + "OR ((replace(wi.total,' ','') LIKE CONCAT('%', :search,'%') or  wi.total LIKE CONCAT('%', :search, '%')) "
//            + "OR ((replace(pt.product_group,' ','') LIKE CONCAT('%', :search,'%') or pt.product_group LIKE CONCAT('%', :search, '%')) "
//            + "OR ((replace(r.trade_name,' ','') LIKE CONCAT('%', :search,'%') or r.trade_name LIKE CONCAT('%', :search, '%')) "
//            + "OR ((replace(s.staff_name,' ','') LIKE CONCAT('%', :search,'%') or s.staff_name LIKE CONCAT('%', :search, '%')) "
//            + "OR ((replace(s1.staff_name,' ','') LIKE CONCAT('%', :search,'%') or s1.staff_name LIKE CONCAT('%', :search, '%')) "
//            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
//            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
//            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
//            nativeQuery = true)
//    List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDate(
//           String startDate, 
//          String endDate, 
//            int retid, 
//       String search, 
//            Pageable pageable);
    
    
//    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
//            + "wi.gstvalue AS taxvalue, wi.amount AS totalvalue, w.createddate AS ordercreateddate, "
//            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
//            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
//            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
//            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
//            + "w.createdtime AS createdtime, wi.product_name AS productname "
//            + "FROM work_order_item AS wi "
//            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
//            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
//            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
//            + "LEFT JOIN states AS st ON r.stateid = st.id "
//            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
//            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
//            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
//            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
//            + " WHERE w.ret_id = ?1 "
//    		+" AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) " +
//    		"AND COALESCE(w.createddate, w.date_created) BETWEEN ?2 AND ?3 "
//            + "AND ("
//            + "   (REPLACE(w.id, ' ', '') LIKE CONCAT('%', :search, '%') OR w.id LIKE CONCAT('%', :search, '%')) "
//            + "   OR (REPLACE(wi.total, ' ', '') LIKE CONCAT('%', :search, '%') OR wi.total LIKE CONCAT('%', :search, '%')) "
//            + "   OR (REPLACE(pt.product_group, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_group LIKE CONCAT('%', :search, '%')) "
//            + "  OR (REPLACE(pt.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_name LIKE CONCAT('%', :search, '%')) "
//            + "   OR (REPLACE(r.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR r.trade_name LIKE CONCAT('%', :search, '%')) "
//            + "   OR (REPLACE(s.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s.staff_name LIKE CONCAT('%', :search, '%')) "
//            + "   OR (REPLACE(s1.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
//            + ") "
//            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
//            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
//            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
//            nativeQuery = true)
//List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDate(
//       String startDate, 
//       String endDate, 
//       int retid, 
//       String search, 
//       Pageable pageable);


    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
            + "wi.gstvalue AS taxvalue, w.gross_total AS totalvalue, w.createddate AS ordercreateddate, "
            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
            + "w.createdtime AS createdtime, wi.product_name AS productname "
            + "FROM work_order_item AS wi "
            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
            + "LEFT JOIN states AS st ON r.stateid = st.id "
            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
            + "WHERE w.ret_id = :retid "
            + "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) "
            + "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate "
            + "AND ("
            + "   (REPLACE(w.id, ' ', '') LIKE CONCAT('%', :search, '%') OR w.id LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(wi.total, ' ', '') LIKE CONCAT('%', :search, '%') OR wi.total LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_group, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_group LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(r.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR r.trade_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s.staff_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s1.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
            + ") "
            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
            nativeQuery = true)
    List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDate(
           @Param("startdate") String startdate, 
           @Param("enddate") String enddate, 
           @Param("retid") int retid, 
           @Param("search") String search, 
           Pageable pageable);

    
    
    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
            + "wi.gstvalue AS taxvalue, w.gross_total AS totalvalue, w.createddate AS ordercreateddate, "
            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
            + "w.createdtime AS createdtime, wi.product_name AS productname "
            + "FROM work_order_item AS wi "
            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
            + "LEFT JOIN states AS st ON r.stateid = st.id "
            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
            + "LEFT JOIN distributor as d on r.distributor_id=d.id " 
            + "WHERE w.ret_id = :retid "
            + "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) "
            + "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate "
            + "AND ("
            + "   (REPLACE(w.id, ' ', '') LIKE CONCAT('%', :search, '%') OR w.id LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(wi.total, ' ', '') LIKE CONCAT('%', :search, '%') OR wi.total LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_group, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_group LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(r.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR r.trade_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s.staff_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s1.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
            + ") "
            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
            nativeQuery = true)
    List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDateDistributor(
           @Param("startdate") String startdate, 
           @Param("enddate") String enddate, 
           @Param("retid") int retid, 
           @Param("search") String search, 
           Pageable pageable);

    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
            + "wi.gstvalue AS taxvalue, w.gross_total AS totalvalue, w.createddate AS ordercreateddate, "
            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
            + "w.createdtime AS createdtime, wi.product_name AS productname "
            + "FROM work_order_item AS wi "
            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
            + "LEFT JOIN states AS st ON r.stateid = st.id "
            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
            + "LEFT JOIN distributor as d on r.distributor_id=d.id " 
            + "LEFT JOIN distributor_to_staff as ds on d.id=ds.distributor_id and s.id=ds.rsmid " 
            + "WHERE w.ret_id = :retid "
            + "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) "
            + "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate "
            + "AND ("
            + "   (REPLACE(w.id, ' ', '') LIKE CONCAT('%', :search, '%') OR w.id LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(wi.total, ' ', '') LIKE CONCAT('%', :search, '%') OR wi.total LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_group, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_group LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(r.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR r.trade_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s.staff_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s1.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
            + ") "
            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
            nativeQuery = true)
    List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDateRsm(
           @Param("startdate") String startdate, 
           @Param("enddate") String enddate, 
           @Param("retid") int retid, 
           @Param("search") String search, 
           Pageable pageable);

    
    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
            + "wi.gstvalue AS taxvalue, w.gross_total AS totalvalue, w.createddate AS ordercreateddate, "
            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
            + "w.createdtime AS createdtime, wi.product_name AS productname "
            + "FROM work_order_item AS wi "
            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
            + "LEFT JOIN states AS st ON r.stateid = st.id "
            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
            + "LEFT JOIN distributor as d on r.distributor_id=d.id " 
            + "LEFT JOIN distributor_to_staff as ds on d.id=ds.distributor_id and s.id=ds.asmid " 
            + "WHERE w.ret_id = :retid "
            + "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) "
            + "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate "
            + "AND ("
            + "   (REPLACE(w.id, ' ', '') LIKE CONCAT('%', :search, '%') OR w.id LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(wi.total, ' ', '') LIKE CONCAT('%', :search, '%') OR wi.total LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_group, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_group LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(r.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR r.trade_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s.staff_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s1.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
            + ") "
            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
            nativeQuery = true)
    List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDateAsm(
           @Param("startdate") String startdate, 
           @Param("enddate") String enddate, 
           @Param("retid") int retid, 
           @Param("search") String search, 
           Pageable pageable);
    
    
    @Query(value = "SELECT w.id AS orderid, wi.total AS taxablevalue, wi.grossamount AS grossamount, "
            + "wi.gstvalue AS taxvalue, w.gross_total AS totalvalue, w.createddate AS ordercreateddate, "
            + "w.date_created AS orderdate, r.trade_name AS tradename, r.city AS city, st.name AS state, "
            + "z.zone_name AS zone, GROUP_CONCAT(s.staff_name) AS rsmname, GROUP_CONCAT(s1.staff_name) AS asename, "
            + "pt.ean_code AS eancode, pt.product_group AS productgroup, wi.dlp AS rate, wi.measurement AS qtyprm, "
            + "wi.qty AS qtyalt, wi.discount AS 1stdis, wi.discount1 AS 2nddis, w.createbyname AS createdby, "
            + "w.createdtime AS createdtime, wi.product_name AS productname "
            + "FROM work_order_item AS wi "
            + "LEFT JOIN work_order AS w ON wi.wid = w.id "
            + "LEFT JOIN retailer AS r ON w.ret_id = r.id "
            + "LEFT JOIN zone AS z ON r.zonesid = z.id "
            + "LEFT JOIN states AS st ON r.stateid = st.id "
            + "LEFT JOIN product AS pt ON wi.product_id = pt.id "
            + "LEFT JOIN retailer_to_staff AS rs ON r.id = rs.retialer_id "
            + "LEFT JOIN staff AS s ON rs.rsmid = s.id "
            + "LEFT JOIN staff AS s1 ON rs.aseid = s1.id "
            + "LEFT JOIN distributor as d on r.distributor_id=d.id " 
            + "LEFT JOIN distributor_to_staff as ds on d.id=ds.distributor_id and s.id=ds.aseid " 
            + "WHERE w.ret_id = :retid "
            + "AND (w.createddate IS NOT NULL OR w.date_created IS NOT NULL) "
            + "AND COALESCE(w.createddate, w.date_created) BETWEEN :startdate AND :enddate "
            + "AND ("
            + "   (REPLACE(w.id, ' ', '') LIKE CONCAT('%', :search, '%') OR w.id LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(wi.total, ' ', '') LIKE CONCAT('%', :search, '%') OR wi.total LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_group, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_group LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(pt.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR pt.product_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(r.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR r.trade_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s.staff_name LIKE CONCAT('%', :search, '%')) "
            + "   OR (REPLACE(s1.staff_name, ' ', '') LIKE CONCAT('%', :search, '%') OR s1.staff_name LIKE CONCAT('%', :search, '%')) "
            + ") "
            + "GROUP BY w.id, wi.grossamount, wi.gstvalue, wi.total, w.createddate, w.date_created, "
            + "r.trade_name, r.city, st.name, z.zone_name, pt.ean_code, pt.product_group, wi.dlp, wi.measurement, "
            + "wi.qty, wi.discount, wi.discount1, w.createbyname, w.createdtime, wi.product_name", 
            nativeQuery = true)
    List<IndexWorkorderItemByRetailerId> searchWorkOrderItemsByRetailerIdWithinDateAse(
           @Param("startdate") String startdate, 
           @Param("enddate") String enddate, 
           @Param("retid") int retid, 
           @Param("search") String search, 
           Pageable pageable);
    
}
