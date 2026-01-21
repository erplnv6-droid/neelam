package com.SCM.repository;

import com.SCM.ExportDto.ExportPrimaryWorkOrder;
import com.SCM.IndexDto.IndexPrimaryOrder;
import com.SCM.model.PrimaryOrder;
import com.SCM.model.PurchaseOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PrimaryOrderRepository extends JpaRepository<PrimaryOrder,Integer> {

    //ase asm rsm nsm zone id

    @Query(value = "select * from primary_order po where po.aseid = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderByAseID(int aseId);


    @Query(value = "select * from primary_order po where po.asmid = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderByAsmID(int asmId);


    @Query(value = "select * from primary_order po where po.rsmid = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderByRsmID(int rsmId);


    @Query(value = "select * from primary_order po where po.nsmid = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderByNsmID(int nsmId);


    @Query(value = "select * from primary_order po where po.zonesid = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderByZoneId(int zoneId);


    @Query(value = "select * from primary_order po where po.stateid = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderBystateId(int stateId);


    @Query(value = "select * from primary_order po where  po.distrubator_id = ?1",nativeQuery = true)
    public List<PrimaryOrder> getPrimaryOrderByDistributorId(int distId);
    
    
    @Query(value = "CALL fetchAllPrimaryOrder()",nativeQuery = true)
    public List<PrimaryOrder> getAllPrimaryWorkOrder();
    
    
    public PrimaryOrder findFirstByOrderByIdDesc();
    
    
    @Query(value = "select * from purchase_order where purchase_id = ?1",nativeQuery = true)
    public List<PurchaseOrder> findByPurchase_id(int purchaseId);
    
    //----------  index primary order -----------------------------------------------------
    
    
        @Query(value = "SELECT po.id,po.work_order_id,po.remarks,DATE_FORMAT(po.work_order_date,'%d-%m-%Y') AS work_order_date ,po.date_created,DATE_FORMAT(po.createddate,'%d-%m-%Y') AS createddate,po.createdtime,po.gross_total,po.createbyname,po.updatedbyname,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,GROUP_CONCAT(s.staff_name) as Rsmstaffname,GROUP_CONCAT(s1.staff_name) as Asestaffname,DATE_FORMAT(po.updateddate,'%d-%m-%Y') as updateddate,po.updatedtime\r\n"
        		+ "        		 from primary_order po\r\n"
        		+ "        		 left join distributor d on po.distrubator_id = d.id\r\n"
        		+ "        		  left join state_zone sz on po.stateid = sz.id\r\n"
        		+ "left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
        		+ "        		  left join staff s on s.id = ds.rsmid\r\n"
        		+ "        		  left join staff s1 on s1.id = ds.aseid\r\n"
        		+ "                  group by po.id,po.work_order_id,po.createdtime,po.gross_total,po.createbyname,po.updatedbyname,po.net_value,po.tax_amount,po.status,d.trade_name" ,nativeQuery = true)
  	    List<IndexPrimaryOrder> indexPrimaryOrder(Pageable p);

      
      
//        @Query(value = "SELECT po.id,po.work_order_id,po.remarks,DATE_FORMAT(po.work_order_date,'%d-%m-%Y') AS work_order_date,po.date_created,DATE_FORMAT(po.createddate,'%d-%m-%Y') AS createddate,po.createdtime,po.gross_total,po.createbyname,po.updatedbyname,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,DATE_FORMAT(po.updateddate,'%d-%m-%Y') as updateddate,po.updatedtime"
//        		+ "  from primary_order po"
//        		+ "    	LEFT JOIN state_zone sz on po.stateid = sz.id"
//        		+ "    	left join distributor d on d.id = po.distrubator_id"
//        		+ "     left join staff s on s.id = po.rsmid"
//        		+ "    	left join staff s1 on s1.id = po.aseid"
//        		+ "    	where po.work_order_id Like CONCAT('%', :search, '%')"
//        		+ "       or po.net_value Like CONCAT('%', :search, '%')"
//        		+ "       or po.id Like CONCAT('%', :search, '%')"
//                + "       or po.work_order_date Like CONCAT('%', :search, '%')"
//                + "       or po.createddate Like CONCAT('%', :search, '%')"
//                + "       or po.date_created Like CONCAT('%', :search, '%')"      		
//        		+ "       or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
//        		+ "         or REGEXP_LIKE(REGEXP_REPLACE(sz.state_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
//        		+ "         or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
//        		+ "         or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
//        		+ "         or po.createbyname Like CONCAT('%', :search, '%')"
//        		+ "         or po.createbyname Like CONCAT('%', :search, '%')" ,nativeQuery = true)
//        List<IndexPrimaryOrder> SearchByPrimaryOrder(String search,Pageable p);
        
        
        @Query(value = "SELECT po.id,po.work_order_id,po.remarks,DATE_FORMAT(po.work_order_date,'%d-%m-%Y') AS work_order_date,po.date_created,DATE_FORMAT(po.createddate,'%d-%m-%Y') AS createddate,po.createdtime,po.gross_total,po.createbyname,po.updatedbyname,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,GROUP_CONCAT(s.staff_name) as Rsmstaffname,GROUP_CONCAT(s1.staff_name) as Asestaffname,DATE_FORMAT(po.updateddate,'%d-%m-%Y') as updateddate,po.updatedtime"
        		+ "  from primary_order po"
        		+ "    	LEFT JOIN state_zone sz on po.stateid = sz.id"
        		+ "    	left join distributor d on d.id = po.distrubator_id"
        		+ "     left join staff s on s.id = po.rsmid"
        		+ "    	left join staff s1 on s1.id = po.aseid"
        		+ "    	where po.work_order_id Like CONCAT('%', :search, '%')"
        		+ "       or po.net_value Like CONCAT('%', :search, '%')"
        		+ "       or po.id Like CONCAT('%', :search, '%')"
                + "       or po.work_order_date Like CONCAT('%', :search, '%')"
                + "       or po.createddate Like CONCAT('%', :search, '%')"
                + "       or po.date_created Like CONCAT('%', :search, '%')"      		
        		+ "       or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
        		+ "         or REGEXP_LIKE(REGEXP_REPLACE(sz.state_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
        		+ "         or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
        		+ "         or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
        		+ "         or po.createbyname Like CONCAT('%', :search, '%')"
        		+ "         or po.createbyname Like CONCAT('%', :search, '%')"
        		+ "group by po.id,po.work_order_id,po.createdtime,po.gross_total,po.createbyname,po.updatedbyname,po.net_value,po.tax_amount,po.status,d.trade_name" ,nativeQuery = true)
        List<IndexPrimaryOrder> SearchByPrimaryOrder(String search,Pageable p);
      
   
  	    
	    @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.gross_total,po.net_value,po.tax_amount,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,po.createddate,po.createdtime,po.createbyname,po.updatedbyname,po.updateddate,po.updatedtime\r\n"
	    		+ "from primary_order po\r\n"
	    		+ "left join distributor d on po.distrubator_id = d.id\r\n"
	    		+ "left join state_zone sz on po.stateid = sz.id\r\n"
	    		+ "left join staff s on s.id = po.rsmid\r\n"
	    		+ "left join staff s1 on s1.id = po.aseid",nativeQuery = true)
  	    List<ExportPrimaryWorkOrder> ExportPrimaryWorkOrder();
  	    
  	    
  	    
  	    // --------------------------primary order by distributor login 
  	    
	    
  	  @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
        		+ " from primary_order po "
        		+ "  left join distributor d on po.distrubator_id = d.id"
        		+ "  left join state_zone sz on po.stateid = sz.id"
        		+ "  left join staff s on s.id = po.rsmid"
        		+ "  left join staff s1 on s1.id = po.aseid"
        		+ " where po.distrubator_id =:distrubator_id" ,nativeQuery = true)
  	    List<IndexPrimaryOrder> findBydistId( Integer distrubator_id);
  	    
  	  
  	  @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
      		+ " from primary_order po "
      		+ "  left join distributor d on po.distrubator_id = d.id"
      		+ "  left join state_zone sz on po.stateid = sz.id"
      		+ "  left join staff s on s.id = po.rsmid"
      		+ "  left join staff s1 on s1.id = po.aseid"
      		+ " where po.distrubator_id =:distrubator_id" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByDistId( Integer distrubator_id , Pageable p);

    
    
      @Query(value = "select po.id,po.work_order_id,po.work_order_date,po.date_created,po.remarks,po.gross_total,po.net_value,po.tax_amount,sz.state_name,d.trade_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,po.createddate,po.createdtime FROM primary_order po "
      		+ "  left join state_zone sz on po.stateid = sz.id"
      		+ "  left join distributor d on d.id = po.distrubator_id"
      		+ "  left join staff s on s.id = po.rsmid"
      		+ "  left join staff s1 on s1.id = po.aseid"
      		+ "  where (po.distrubator_id =:distrubator_id)"
      		+ "  and (po.work_order_id Like CONCAT('%', :search, '%')"
      		+ "  or po.net_value Like CONCAT('%', :search, '%')"
      		+ "  or po.id Like CONCAT('%', :search, '%')"
      	    + "  or po.work_order_date Like CONCAT('%', :search, '%')"
            + "  or po.createddate Like CONCAT('%', :search, '%')"
            + "  or po.date_created Like CONCAT('%', :search, '%')"      
        	+ "  or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ "  or REGEXP_LIKE(REGEXP_REPLACE(sz.state_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ "  or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ "  or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
      		,nativeQuery = true)
      List<IndexPrimaryOrder> SearchByDistId(Integer distrubator_id , String search,Pageable p);
      
      
   // --------------------------primary order by ASE login
      
      
      @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
      		+ " from primary_order po "
      		+ "  left join distributor d on po.distrubator_id = d.id"
      		+ "  left join state_zone sz on po.stateid = sz.id"
      		+ "  left join staff s on s.id = po.rsmid"
      		+ "  left join staff s1 on s1.id = po.aseid"
      		+ " where po.aseid =:aseid" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByaseId( Integer aseid);
	    
      
	  @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
    		+ " from primary_order po "
    		+ "  left join distributor d on po.distrubator_id = d.id"
    		+ "  left join state_zone sz on po.stateid = sz.id"
    		+ "  left join staff s on s.id = po.rsmid"
    		+ "  left join staff s1 on s1.id = po.aseid"
    		+ " where po.aseid =:aseid" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByAseId( Integer aseid , Pageable p);

  
  
    @Query(value = "select po.id,po.work_order_id,po.work_order_date,po.date_created,po.remarks,po.gross_total,po.net_value,po.tax_amount,sz.state_name,d.trade_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,po.createddate,po.createdtime FROM primary_order po "
    		+ " left join state_zone sz on po.stateid = sz.id"
    		+ " left join distributor d on d.id = po.distrubator_id"
    		+ " left join staff s on s.id = po.rsmid"
    		+ " left join staff s1 on s1.id = po.aseid"
    		+ " where (po.aseid =:aseid)"
    		+ " and (po.work_order_id Like CONCAT('%', :search, '%')"
    		+ " or po.net_value Like CONCAT('%', :search, '%')"
    		+ " or po.id Like CONCAT('%', :search, '%')"
    		+ " or po.work_order_date Like CONCAT('%', :search, '%')"
            + " or po.createddate Like CONCAT('%', :search, '%')"
            + " or po.date_created Like CONCAT('%', :search, '%')"      
        	+ " or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(sz.state_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
    		,nativeQuery = true)
    List<IndexPrimaryOrder> SearchByAseId(Integer aseid , String search,Pageable p);
    
    
    
    // --------------------------primary order by ASM login
      
      
    @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
      		+ " from primary_order po "
      		+ "  left join distributor d on po.distrubator_id = d.id"
      		+ "  left join state_zone sz on po.stateid = sz.id"
      		+ "  left join staff s on s.id = po.rsmid"
      		+ "  left join staff s1 on s1.id = po.aseid"
      		+ " where po.asmid =:asmid" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByasmId( Integer asmid);
	    
    
	  @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
    		+ " from primary_order po "
    		+ "  left join distributor d on po.distrubator_id = d.id"
    		+ "  left join state_zone sz on po.stateid = sz.id"
    		+ "  left join staff s on s.id = po.rsmid"
    		+ "  left join staff s1 on s1.id = po.aseid"
    		+ " where po.asmid =:asmid" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByAsmId( Integer asmid , Pageable p);

  
  
    @Query(value = "select po.id,po.work_order_id,po.work_order_date,po.date_created,po.remarks,po.gross_total,po.net_value,po.tax_amount,sz.state_name,d.trade_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,po.createddate,po.createdtime FROM primary_order po "
    		+ " left join state_zone sz on po.stateid = sz.id"
    		+ " left join distributor d on d.id = po.distrubator_id"
    		+ " left join staff s on s.id = po.rsmid"
    		+ " left join staff s1 on s1.id = po.aseid"
    		+ " where (po.asmid =:asmid)"
    		+ " and (po.work_order_id Like CONCAT('%', :search, '%')"
    		+ " or po.net_value Like CONCAT('%', :search, '%')"
    		+ " or po.id Like CONCAT('%', :search, '%')"
    		+ " or po.work_order_date Like CONCAT('%', :search, '%')"
            + " or po.createddate Like CONCAT('%', :search, '%')"
            + " or po.date_created Like CONCAT('%', :search, '%')"     
        	+ " or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(sz.state_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
    		,nativeQuery = true)
    List<IndexPrimaryOrder> SearchByAsmId(Integer asmid , String search,Pageable p);
    
    
 // --------------------------primary order by RSM login
    
    
    @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
      		+ " from primary_order po "
      		+ "  left join distributor d on po.distrubator_id = d.id"
      		+ "  left join state_zone sz on po.stateid = sz.id"
      		+ "  left join staff s on s.id = po.rsmid"
      		+ "  left join staff s1 on s1.id = po.aseid"
      		+ " where po.rsmid =:rsmid" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByrsmId( Integer rsmid);
	    
    
	  @Query(value = "SELECT po.id,po.work_order_id,po.remarks,po.work_order_date,po.date_created,po.createddate,po.createdtime,po.gross_total,po.net_value,po.tax_amount,po.status,d.trade_name,sz.state_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname"
    		+ " from primary_order po "
    		+ "  left join distributor d on po.distrubator_id = d.id"
    		+ "  left join state_zone sz on po.stateid = sz.id"
    		+ "  left join staff s on s.id = po.rsmid"
    		+ "  left join staff s1 on s1.id = po.aseid"
    		+ " where po.rsmid =:rsmid" ,nativeQuery = true)
	    List<IndexPrimaryOrder> findByRsmId( Integer rsmid , Pageable p);

  
  
    @Query(value = "select po.id,po.work_order_id,po.work_order_date,po.date_created,po.remarks,po.gross_total,po.net_value,po.tax_amount,po.status,sz.state_name,d.trade_name,s.staff_name as Rsmstaffname,s1.staff_name as Asestaffname,po.createddate,po.createdtime FROM primary_order po "
    		+ " left join state_zone sz on po.stateid = sz.id"
    		+ " left join distributor d on d.id = po.distrubator_id"
    		+ " left join staff s on s.id = po.rsmid"
    		+ " left join staff s1 on s1.id = po.aseid"
    		+ " where (po.rsmid =:rsmid)"
    		+ " and (po.work_order_id Like CONCAT('%', :search, '%')"
    		+ " or po.net_value Like CONCAT('%', :search, '%')"
    		+ " or po.id Like CONCAT('%', :search, '%')"
    		+ " or po.work_order_date Like CONCAT('%', :search, '%')"
            + " or po.createddate Like CONCAT('%', :search, '%')"
            + " or po.date_created Like CONCAT('%', :search, '%')"     
        	+ " or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(sz.state_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ " or REGEXP_LIKE(REGEXP_REPLACE(s.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
    		+ "  or REGEXP_LIKE(REGEXP_REPLACE(s1.staff_name,'[^a-zA-Z0-9]',''),REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')))"
    		,nativeQuery = true)
    List<IndexPrimaryOrder> SearchByRsmId(Integer rsmid , String search,Pageable p);
    
  	   
}
