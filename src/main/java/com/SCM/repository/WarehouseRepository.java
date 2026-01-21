package com.SCM.repository;

import com.SCM.IndexDto.IndexWarehouse;
import com.SCM.dtoReports.GodownStockReport;
import com.SCM.dtoReports.PendingPoVsGodownReports;
import com.SCM.model.OpeningStock;
import com.SCM.model.Warehouse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("SELECT o FROM OpeningStock  o join Warehouse w on o.wid =w.id where w.type = :type")
    List<OpeningStock> findByType(@Param("type") String  type);
    
    
    @Query(value = "SELECT w.id,w.name,w.location,w.type,DATE_FORMAT(w.createddate,'%d-%m-%Y') AS createddate,w.createdtime,w.createbyname,w.updatedbyname,DATE_FORMAT(w.updateddate,'%d-%m-%Y') as updateddate,w.updatedtime from warehouse w",nativeQuery = true)
	List<IndexWarehouse> indexWarehouse(Pageable p);
    
    @Query(value = "select * FROM warehouse w "
			+ " where REGEXP_LIKE(REGEXP_REPLACE(w.name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "     or w.id Like CONCAT('%', :search, '%')"
			+ "     or w.location Like CONCAT('%', :search, '%')"  
			+ "     or w.type Like CONCAT('%', :search, '%')"
			+ "     or w.createbyname Like CONCAT('%', :search, '%')"
			+ "     or w.updatedbyname Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexWarehouse> SearchByWarehouse(String search, Pageable p);
	
	
	
	//-------------- Godown stock Report
	
	
	@Query(value = "select * from warehouse w where w.id = :wid",nativeQuery = true)
	List<Warehouse> getwarehouseid(Collection<Integer> wid);
	
	

//	@Query(value = "CALL GodownStockReport(?,?,?)",nativeQuery = true)
//	List<GodownStockReport> GodownStockReport(@Param("wid")int wid,
//			                                  @Param("fromdate")String fromdate,
//			                                  @Param("todate")String todate);
	
	
	
	

	@Query(value = "\r\n"
			+ "SELECT p.product_name,p.id,\r\n"
			+ "COALESCE(sum(os.qty),0) As opening,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(closingmrni.closingpcsqty),0)+COALESCE(SUM(closingjii.closingpcsqty),0)+COALESCE(SUM(closingmsc.closingstdqty),0)+COALESCE(SUM(closingsri.closingpcsqty),0))  -  (COALESCE(SUM(closingsi.closingpcsqty),0)+COALESCE(SUM(closingjoi.closingpcsqty),0)+COALESCE(SUM(closingdci.closingpcsqty),0)+COALESCE(SUM(closingcbi.closingpcsqty),0)+COALESCE(SUM(closingpri.closingpcsqty),0)) AS previousclosingpcsqty,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(closingmrni.closingkgqty),0)+COALESCE(SUM(closingjii.closingkgqty),0)+COALESCE(SUM(closingsri.closingkgqty),0))  -  (COALESCE(SUM(closingdci.closingkgqty),0)+COALESCE(SUM(closingjoi.closingkgqty),0)+COALESCE(SUM(closingpri.closingkgqty),0)) as previousclosingkgqty,\r\n"
			+ "(COALESCE(SUM(closingmrni.closingrate),0)+COALESCE(SUM(closingjii.closingpcsqty),0)+COALESCE(SUM(closingsri.closingrate),0)) - (COALESCE(SUM(closingdci.closingrate),0)+COALESCE(SUM(closingsi.closingrate),0)+COALESCE(SUM(closingjoi.closingrate),0)+COALESCE(SUM(closingpri.closingrate),0)) as previousclosingrate,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(sum(closingmrni.closingpcsqty * closingmrni.closingrate),0)+COALESCE(sum(closingjii.closingpcsqty * closingjii.closingrate),0)+COALESCE(sum(closingsri.closingpcsqty * closingsri.closingrate),0))  -  (COALESCE(sum(closingdci.closingpcsqty * closingdci.closingrate),0)+COALESCE(sum(closingjoi.closingpcsqty * closingjoi.closingrate),0)+COALESCE(sum(closingpri.closingpcsqty * closingpri.closingrate),0)) previousclosingamount,\r\n"
			+ "\r\n"
			+ "(COALESCE(SUM(mrni.pcsqty),0)+COALESCE(SUM(msc.stdqty),0)+COALESCE(SUM(jii.pcsqty),0)+COALESCE(SUM(sri.pcsqty),0)) AS inwardpcsqty,\r\n"
			+ "(COALESCE(SUM(mrni.kgqty),0)+COALESCE(SUM(jii.kgqty),0)+COALESCE(SUM(sri.kgqty),0)) AS inwardkgsqty,\r\n"
			+ "(COALESCE(SUM(mrni.rate),0)+COALESCE(SUM(jii.rate),0)+COALESCE(SUM(sri.rate),0)) AS inwardrate,\r\n"
			+ "(COALESCE(SUM(mrni.pcsqty * mrni.rate),0)+COALESCE(SUM(jii.pcsqty * jii.rate),0)+COALESCE(SUM(sri.pcsqty * sri.rate),0)) AS inwardamount,\r\n"
			+ "\r\n"
			+ "(COALESCE(SUM(dci.pcsqty),0)+COALESCE(SUM(cbi.pcsqty),0)+COALESCE(SUM(joi.pcsqty),0)+COALESCE(SUM(si.pcsqty),0)+COALESCE(SUM(pri.pcsqty),0)) AS outwardpcsqty,\r\n"
			+ "(COALESCE(SUM(dci.kgqty),0)+COALESCE(SUM(joi.kgqty),0)+COALESCE(SUM(si.kgqty),0)+COALESCE(SUM(pri.kgqty),0)) AS outwardkgqty,\r\n"
			+ "(COALESCE(SUM(dci.rate),0)+COALESCE(SUM(joi.rate),0)+COALESCE(SUM(si.rate),0)+COALESCE(SUM(pri.rate),0)) AS outwardrate,\r\n"
			+ "(COALESCE(SUM(dci.pcsqty * dci.rate),0)+COALESCE(SUM(si.pcsqty * si.rate),0)+COALESCE(SUM(joi.pcsqty * joi.rate),0)+COALESCE(SUM(pri.pcsqty * pri.rate),0)) as outwardamount,\r\n"
			+ "\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(closingmrni.closingpcsqty),0)+COALESCE(SUM(closingjii.closingpcsqty),0)+COALESCE(SUM(closingmsc.closingstdqty),0)+COALESCE(SUM(closingsri.closingpcsqty),0))  -  (COALESCE(SUM(closingsi.closingpcsqty),0)+COALESCE(SUM(closingjoi.closingpcsqty),0)+COALESCE(SUM(closingdci.closingpcsqty),0)+COALESCE(SUM(closingcbi.closingpcsqty),0)+COALESCE(SUM(closingpri.closingpcsqty),0)) - (COALESCE(SUM(os.qty),0))+(COALESCE(SUM(mrni.pcsqty),0)+COALESCE(SUM(msc.stdqty),0)+COALESCE(SUM(jii.pcsqty),0)+COALESCE(SUM(sri.pcsqty),0))  -  (COALESCE(SUM(dci.pcsqty),0)+COALESCE(SUM(cbi.pcsqty),0)+COALESCE(SUM(joi.pcsqty),0)+COALESCE(SUM(si.pcsqty),0)+COALESCE(SUM(pri.pcsqty),0)) AS closingpcsqty,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(closingmrni.closingkgqty),0)+COALESCE(SUM(closingjii.closingkgqty),0)+COALESCE(SUM(closingsri.closingkgqty),0))  -  (COALESCE(SUM(closingsi.closingkgqty),0)+COALESCE(SUM(closingjoi.closingkgqty),0)+COALESCE(SUM(closingdci.closingkgqty),0)+COALESCE(SUM(closingpri.closingkgqty),0)) - (COALESCE(SUM(os.qty),0))+(COALESCE(SUM(mrni.kgqty),0)+COALESCE(SUM(jii.kgqty),0)+COALESCE(SUM(sri.kgqty),0))  -  (COALESCE(SUM(dci.kgqty),0)+COALESCE(SUM(joi.kgqty),0)+COALESCE(SUM(si.kgqty),0)+COALESCE(SUM(pri.kgqty),0)) AS closingkgqty,\r\n"
			+ "(COALESCE(SUM(mrni.rate),0)+COALESCE(SUM(jii.rate),0)+COALESCE(SUM(sri.rate),0)) -  (COALESCE(SUM(dci.rate),0)+COALESCE(SUM(joi.rate),0)+COALESCE(SUM(si.rate),0)+COALESCE(SUM(pri.rate),0)) as closingrate,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(sum(closingmrni.closingpcsqty * closingmrni.closingrate),0)+COALESCE(sum(closingjii.closingpcsqty * closingjii.closingrate),0)+COALESCE(sum(closingsri.closingpcsqty * closingsri.closingrate),0))  -  (COALESCE(sum(closingdci.closingpcsqty * closingdci.closingrate),0)+COALESCE(sum(closingjoi.closingpcsqty * closingjoi.closingrate),0)+COALESCE(sum(closingpri.closingpcsqty * closingpri.closingrate),0)) + (COALESCE(SUM(mrni.pcsqty * mrni.rate),0)+COALESCE(SUM(jii.pcsqty * jii.rate),0)+COALESCE(SUM(sri.pcsqty * sri.rate),0)) - (COALESCE(SUM(dci.pcsqty * dci.rate),0)+COALESCE(SUM(si.pcsqty * si.rate),0)+COALESCE(SUM(joi.pcsqty * joi.rate),0)+COALESCE(SUM(pri.pcsqty * pri.rate),0)) as closingamount\r\n"
			+ "\r\n"
			+ "FROM product p\r\n"
			+ "LEFT JOIN opening_stock os on p.id = os.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT mrni.product_id,sum(mrni.dlp) as rate,SUM(mrni.mrnquantity) as pcsqty,sum(mrni.mrnquantitykgs) as kgqty FROM material_receipt_note_items mrni \r\n"
			+ "JOIN material_recepit_note mrn ON mrni.mrn_id = mrn.id\r\n"
			+ "WHERE mrn.warehouse_id = :wid and mrn.mrndate between :fromdate and :todate  GROUP BY mrni.product_id) AS mrni ON p.id = mrni.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT sri.product_id,sum(sri.dlp) As rate ,SUM(sri.srquantity) as pcsqty,sum(sri.srquantitykgs) as kgqty FROM sales_return_items sri\r\n"
			+ "JOIN sales_return sr ON sri.sales_returnid = sr.id\r\n"
			+ "WHERE sr.warehouse_id = :wid  and sr.salesreturndate between :fromdate and :todate GROUP BY sri.product_id) AS sri ON p.id = sri.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT msc.productid, SUM(msc.stdqty) as stdqty  FROM mastercartoon msc \r\n"
			+ "JOIN product p ON msc.productid = p.id\r\n"
			+ "where msc.createddate between :fromdate and :todate GROUP BY msc.productid) AS msc ON msc.productid = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT jii.product_id, SUM(jii.dlp) as rate, SUM(jii.jobsheet_qty) as pcsqty, SUM(jii.jobsheet_qty_kg) as kgqty FROM jobwork_inward_items jii\r\n"
			+ "JOIN jobwork_inward ji ON jii.jobsheet_id = ji.id\r\n"
			+ "WHERE ji.warehouse_id = :wid and ji.jobsheetdate between :fromdate and :todate  GROUP BY jii.product_id) AS jii ON p.id = jii.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT dc.status, dci.product_id, sum(dci.dlp) as rate, SUM(dci.dcquantity_placed) as pcsqty, sum(dci.dcquantity_placed_kg) as kgqty FROM delivery_charge_items dci \r\n"
			+ "JOIN delivery_charge dc ON dci.dc_id = dc.id\r\n"
			+ "WHERE dc.warehouse_id = :wid  and dc.dcdate between :fromdate and :todate and dc.status= 'pending' GROUP BY dci.product_id,dc.status) AS dci ON p.id = dci.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT pri.product_id, sum(pri.dlp) as rate, SUM(pri.purquantity) as pcsqty, SUM(pri.purquantitykgs) as kgqty FROM purchase_return_items pri \r\n"
			+ "JOIN purchase_return pr ON pri.purchasereturn_id = pr.id\r\n"
			+ "WHERE pr.warehouse_id = :wid  and pr.purchasereturndate  between :fromdate and :todate GROUP BY pri.product_id) AS pri ON p.id = pri.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT s.status, si.product_id,SUM(si.dlp)as rate, SUM(si.salesquantity) AS pcsqty, SUM(si.salesquantitykgs) AS kgqty FROM sales_items si\r\n"
			+ "JOIN sales s ON si.sales_id = s.id\r\n"
			+ "WHERE s.warehouse_id = :wid and s.invoicedate between :fromdate and :todate GROUP BY si.product_id, s.status) AS si ON p.id = si.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT joi.product_id, sum(joi.dlp) as rate, SUM(joi.jobsheet_qty) as pcsqty, SUM(joi.jobsheet_qty_kg) as kgqty FROM jobworkoutwarditems joi\r\n"
			+ "JOIN jobworkoutward jo ON joi.jobsheetoutward_id = jo.id\r\n"
			+ "WHERE jo.warehouse_id = :wid and jo.jobsheetdate between :fromdate and :todate GROUP BY joi.product_id) AS joi ON p.id = joi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT cbi.product_id, SUM(cbi.qty) as pcsqty FROM cartonbarcodeitems cbi \r\n"
			+ "JOIN cartonbarcode cb ON cbi.cartonbarcodeid = cb.id\r\n"
			+ "where cb.createddate between :fromdate and :todate  GROUP BY cbi.product_id) AS cbi ON p.id = cbi.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT join (select sum(closingos.qty) as closingqty, closingos.product_id from opening_stock closingos\r\n"
			+ "join product p on closingos.product_id = p.id \r\n"
			+ "where closingos.date < :fromdate  group by closingos.product_id) closingos on closingos.product_id = p.id \r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingmrni.product_id,sum(closingmrni.dlp) as closingrate,SUM(closingmrni.mrnquantity) AS closingpcsqty,SUM(closingmrni.mrnquantitykgs) AS closingkgqty FROM material_receipt_note_items closingmrni \r\n"
			+ "JOIN material_recepit_note closingmrn ON closingmrni.mrn_id = closingmrn.id\r\n"
			+ "WHERE closingmrn.warehouse_id = :wid and closingmrn.mrndate < :fromdate GROUP BY closingmrni.product_id) closingmrni ON closingmrni.product_id = p.id \r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingsri.product_id, sum(closingsri.dlp) as closingrate,SUM(closingsri.srquantity) AS closingpcsqty,SUM(closingsri.srquantitykgs) AS closingkgqty FROM sales_return_items closingsri\r\n"
			+ "JOIN sales_return closingsr ON closingsri.sales_returnid = closingsr.id\r\n"
			+ "WHERE closingsr.warehouse_id = :wid and closingsr.salesreturndate < :fromdate GROUP BY closingsri.product_id) closingsri ON closingsri.product_id = p.id \r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingmsc.productid, sum(closingmsc.stdqty) as closingstdqty FROM mastercartoon closingmsc \r\n"
			+ "JOIN product p ON closingmsc.productid = p.id \r\n"
			+ "where closingmsc.createddate < :fromdate GROUP BY closingmsc.productid) AS closingmsc ON closingmsc.productid = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingjii.product_id,sum(closingjii.dlp) As closingrate ,SUM(closingjii.jobsheet_qty) as closingpcsqty, sum(closingjii.jobsheet_qty_kg) as closingkgqty FROM jobwork_inward_items closingjii\r\n"
			+ "JOIN jobwork_inward closingji ON closingjii.jobsheet_id = closingji.id\r\n"
			+ "WHERE closingji.warehouse_id = :wid  and closingji.jobsheetdate < :fromdate GROUP BY closingjii.product_id) AS closingjii ON p.id = closingjii.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN (select closingdc.status,closingdci.product_id,sum(closingdci.dlp) as closingrate ,SUM(closingdci.dcquantity_placed) AS closingpcsqty,SUM(closingdci.dcquantity_placed_kg) AS closingkgqty from delivery_charge_items closingdci\r\n"
			+ "join delivery_charge closingdc on closingdci.dc_id = closingdc.id \r\n"
			+ "WHERE closingdc.warehouse_id = :wid and closingdc.dcdate < :fromdate and closingdc.status= 'pending'  GROUP BY closingdci.product_id,closingdc.status) closingdci ON closingdci.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingpri.product_id,sum(closingpri.dlp) as closingrate,  SUM(closingpri.purquantity) AS closingpcsqty,SUM(closingpri.purquantitykgs) AS closingkgqty FROM purchase_return_items closingpri \r\n"
			+ "JOIN purchase_return closingpr ON closingpri.purchasereturn_id = closingpr.id\r\n"
			+ "WHERE closingpr.warehouse_id = :wid and closingpr.purchasereturndate < :fromdate GROUP BY closingpri.product_id) closingpri ON closingpri.product_id = p.id \r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingcbi.product_id,SUM(closingcbi.qty) as closingpcsqty FROM cartonbarcodeitems closingcbi \r\n"
			+ "JOIN cartonbarcode closingcb ON closingcbi.cartonbarcodeid = closingcb.id\r\n"
			+ "where closingcb.createddate < :fromdate  GROUP BY closingcbi.product_id) AS closingcbi ON p.id = closingcbi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingsi.product_id,sum(closingsi.dlp) as closingrate,SUM(closingsi.salesquantity) as closingpcsqty,SUM(closingsi.salesquantitykgs) as closingkgqty FROM sales_items closingsi \r\n"
			+ "JOIN sales closings ON closingsi.sales_id = closings.id\r\n"
			+ "where closings.warehouse_id = :wid  and closings.invoicedate < :fromdate  GROUP BY closingsi.product_id) AS closingsi ON p.id = closingsi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingjoi.product_id, sum(closingjoi.dlp) as closingrate, SUM(closingjoi.jobsheet_qty) as closingpcsqty, SUM(closingjoi.jobsheet_qty_kg) as closingkgqty FROM jobworkoutwarditems closingjoi\r\n"
			+ "JOIN jobworkoutward closingjo ON closingjoi.jobsheetoutward_id = closingjo.id\r\n"
			+ "WHERE closingjo.warehouse_id = :wid and closingjo.jobsheetdate < :fromdate  GROUP BY closingjoi.product_id) AS closingjoi ON p.id = closingjoi.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "WHERE mrni.pcsqty IS NOT NULL OR dci.pcsqty IS NOT NULL OR pri.pcsqty IS NOT NULL OR sri.pcsqty IS NOT NULL\r\n"
			+ "\r\n"
			+ "group by p.id,p.product_name\r\n"
			+ "",nativeQuery = true)
	List<GodownStockReport> GodownStockReport(@Param("wid")int wid,
			                                  @Param("fromdate")String fromdate,
			                                  @Param("todate")String todate,
			                                  Pageable p);
	
	
	
	
	@Query(value = "CALL GodownStockReports(?,?,?)", nativeQuery = true)
	List<GodownStockReport> GodownStockReport1(int wid,String fromdate,String todate);

	
	
	
	
	//---------------------------------------------------------------------------
	
	
	
	
	
	@Query(value = "SELECT p.product_name,\r\n"
			+ "       p.ean_code,\r\n"
			+ "       p.id,\r\n"
			+ "	   COALESCE(sum(pwoi.pcsqty),0) as primaryorderpcsqty,\r\n"
			+ "       COALESCE(sum(pwoi.kgqty),0) as primaryorderkgqty,\r\n"
			+ "	   COALESCE(sum(pwoi.rate),0) as rate,\r\n"
			+ "	   COALESCE(sum(pwoi.pcsqty * pwoi.rate),0) as pcsamount,\r\n"
			+ "       COALESCE(sum(pwoi.kgqty * pwoi.rate),0) as kgamount,\r\n"
			+ "       \r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(closingmrni.closingpcsqty),0)+COALESCE(SUM(closingjii.closingpcsqty),0)+COALESCE(SUM(closingmsc.closingstdqty),0)+COALESCE(SUM(closingsri.closingpcsqty),0))  -  (COALESCE(SUM(closingsi.closingpcsqty),0)+COALESCE(SUM(closingjoi.closingpcsqty),0)+COALESCE(SUM(closingdci.closingpcsqty),0)+COALESCE(SUM(closingcbi.closingpcsqty),0)+COALESCE(SUM(closingpri.closingpcsqty),0))     +     (COALESCE(SUM(mrni.pcsqty),0)+COALESCE(SUM(msc.stdqty),0)+COALESCE(SUM(jii.pcsqty),0)+COALESCE(SUM(sri.pcsqty),0))  -  (COALESCE(SUM(dci.pcsqty),0)+COALESCE(SUM(cbi.pcsqty),0)+COALESCE(SUM(joi.pcsqty),0)+COALESCE(SUM(si.pcsqty),0)+COALESCE(SUM(pri.pcsqty),0)) AS closingpcsqty,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(closingmrni.closingkgqty),0)+COALESCE(SUM(closingjii.closingkgqty),0)+COALESCE(SUM(closingsri.closingkgqty),0))  -  (COALESCE(SUM(closingsi.closingkgqty),0)+COALESCE(SUM(closingjoi.closingkgqty),0)+COALESCE(SUM(closingdci.closingkgqty),0)+COALESCE(SUM(closingpri.closingkgqty),0))   +     (COALESCE(SUM(mrni.kgqty),0)+COALESCE(SUM(jii.kgqty),0)+COALESCE(SUM(sri.kgqty),0)) - (COALESCE(SUM(dci.kgqty),0)+COALESCE(SUM(joi.kgqty),0)+COALESCE(SUM(si.kgqty),0)+COALESCE(SUM(pri.kgqty),0)) AS closingkgqty,\r\n"
			+ "\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(totalclosingmrni.totalclosingpcsqty),0)+COALESCE(SUM(totalclosingjii.totalclosingpcsqty),0)+COALESCE(SUM(totalclosingmsc.totalclosingstdqty),0)+COALESCE(SUM(totalclosingsri.totalclosingpcsqty),0))  -  (COALESCE(SUM(totalclosingdci.totalclosingpcsqty),0)+COALESCE(SUM(totalclosingjoi.totalclosingpcsqty),0)+(COALESCE(SUM(totalclosingsi.totalclosingpcsqty),0))+(COALESCE(SUM(totalclosingcbi.totalclosingpcsqty),0))+COALESCE(SUM(totalclosingpri.totalclosingpcsqty),0))   +   (COALESCE(SUM(totalmrni.totalpcsqty),0)+COALESCE(SUM(totalmsc.totalstdqty),0)+COALESCE(SUM(totaljii.totalpcsqty),0)+COALESCE(SUM(totalsri.totalpcsqty),0))  -  (COALESCE(SUM(totaldci.totalpcsqty),0)+COALESCE(SUM(totalcbi.totalpcsqty),0)+COALESCE(SUM(totaljoi.totalpcsqty),0)+COALESCE(SUM(totalsi.totalpcsqty),0)+COALESCE(SUM(totalpri.totalpcsqty),0)) as totalpcsclosingstock,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(totalclosingmrni.totalclosingkgqty),0)+COALESCE(SUM(totalclosingjii.totalclosingkgqty),0)+COALESCE(SUM(totalclosingsri.totalclosingkgqty),0))  -  (COALESCE(SUM(totalclosingdci.totalclosingkgqty),0)+COALESCE(SUM(totalclosingjoi.totalclosingkgqty),0)+(COALESCE(SUM(totalclosingsi.totalclosingkgqty),0))+COALESCE(SUM(totalclosingpri.totalclosingkgqty),0))   +   (COALESCE(SUM(totalmrni.totalkgqty),0)+COALESCE(SUM(totaljii.totalkgqty),0)+COALESCE(SUM(totalsri.totalkgqty),0))  -  (COALESCE(SUM(totaldci.totalkgqty),0)+COALESCE(SUM(totaljoi.totalkgqty),0)+COALESCE(SUM(totalsi.totalkgqty),0)+COALESCE(SUM(totalpri.totalkgqty),0)) as totalkgclosingstock,\r\n"
			+ "\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(totalclosingmrni.totalclosingpcsqty),0)+COALESCE(SUM(totalclosingjii.totalclosingpcsqty),0)+COALESCE(SUM(totalclosingmsc.totalclosingstdqty),0)+COALESCE(SUM(totalclosingsri.totalclosingpcsqty),0))  -  (COALESCE(SUM(totalclosingdci.totalclosingpcsqty),0)+COALESCE(SUM(totalclosingjoi.totalclosingpcsqty),0)+(COALESCE(SUM(totalclosingsi.totalclosingpcsqty),0))+(COALESCE(SUM(totalclosingcbi.totalclosingpcsqty),0))+COALESCE(SUM(totalclosingpri.totalclosingpcsqty),0))   +   (COALESCE(SUM(totalmrni.totalpcsqty),0)+COALESCE(SUM(totalmsc.totalstdqty),0)+COALESCE(SUM(totaljii.totalpcsqty),0)+COALESCE(SUM(totalsri.totalpcsqty),0))  -  (COALESCE(SUM(totaldci.totalpcsqty),0)+COALESCE(SUM(totalcbi.totalpcsqty),0)+COALESCE(SUM(totaljoi.totalpcsqty),0)+COALESCE(SUM(totalsi.totalpcsqty),0)+COALESCE(SUM(totalpri.totalpcsqty),0)) - (COALESCE(sum(pwoi.pcsqty),0)) as totalpcsstock,\r\n"
			+ "(COALESCE(SUM(closingos.closingqty),0))+(COALESCE(SUM(totalclosingmrni.totalclosingkgqty),0)+COALESCE(SUM(totalclosingjii.totalclosingkgqty),0)+COALESCE(SUM(totalclosingsri.totalclosingkgqty),0))  -  (COALESCE(SUM(totalclosingdci.totalclosingkgqty),0)+COALESCE(SUM(totalclosingjoi.totalclosingkgqty),0)+(COALESCE(SUM(totalclosingsi.totalclosingkgqty),0))+COALESCE(SUM(totalclosingpri.totalclosingkgqty),0))   +   (COALESCE(SUM(totalmrni.totalkgqty),0)+COALESCE(SUM(totaljii.totalkgqty),0)+COALESCE(SUM(totalsri.totalkgqty),0))  -  (COALESCE(SUM(totaldci.totalkgqty),0)+COALESCE(SUM(totaljoi.totalkgqty),0)+COALESCE(SUM(totalsi.totalkgqty),0)+COALESCE(SUM(totalpri.totalkgqty),0))   -  (COALESCE(sum(pwoi.kgqty),0)) as totalkgstock\r\n"
			+ "FROM product p\r\n"
			+ "LEFT JOIN opening_stock os on p.id = os.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT pwoi.product_id, SUM(pwoi.quantity_placed) as pcsqty, SUM(pwoi.quantity_placed_kg) as kgqty,SUM(pwoi.dlp) as rate FROM primary_order_items pwoi \r\n"
			+ "JOIN primary_order pwo ON pwoi.primaryorder_id = pwo.id\r\n"
			+ "WHERE pwo.status = 'pending'  GROUP BY pwoi.product_id) AS pwoi ON p.id = pwoi.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT mrni.product_id,sum(mrni.dlp) as rate,SUM(mrni.mrnquantity) as pcsqty,sum(mrni.mrnquantitykgs) as kgqty FROM material_receipt_note_items mrni \r\n"
			+ "JOIN material_recepit_note mrn ON mrni.mrn_id = mrn.id\r\n"
			+ "WHERE mrn.warehouse_id = :wid and mrn.mrndate between :fromdate and :todate GROUP BY mrni.product_id) AS mrni ON p.id = mrni.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT sri.product_id,sum(sri.dlp) As rate ,SUM(sri.srquantity) as pcsqty,sum(sri.srquantitykgs) as kgqty FROM sales_return_items sri \r\n"
			+ "JOIN sales_return sr ON sri.sales_returnid = sr.id\r\n"
			+ "WHERE sr.warehouse_id = :wid  and sr.salesreturndate between :fromdate and :todate GROUP BY sri.product_id) AS sri ON p.id = sri.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT msc.productid, SUM(msc.stdqty) as stdqty  FROM mastercartoon msc \r\n"
			+ "JOIN product p ON msc.productid = p.id\r\n"
			+ "where msc.createddate between :fromdate and :todate GROUP BY msc.productid) AS msc ON msc.productid = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT jii.product_id, SUM(jii.dlp) as rate, SUM(jii.jobsheet_qty) as pcsqty, SUM(jii.jobsheet_qty_kg) as kgqty FROM jobwork_inward_items jii\r\n"
			+ "JOIN jobwork_inward ji ON jii.jobsheet_id = ji.id\r\n"
			+ "WHERE ji.warehouse_id = :wid and ji.jobsheetdate between :fromdate and :todate  GROUP BY jii.product_id) AS jii ON p.id = jii.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT dci.product_id,sum(dci.dlp) as rate, SUM(dci.dcquantity_placed) as pcsqty,sum(dci.dcquantity_placed_kg) as kgqty FROM delivery_charge_items dci \r\n"
			+ "JOIN delivery_charge dc ON dci.dc_id = dc.id\r\n"
			+ "WHERE dc.warehouse_id = :wid  and dc.dcdate  between :fromdate and :todate and dc.status= 'pending' GROUP BY dci.product_id) AS dci ON p.id = dci.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT pri.product_id,sum(pri.dlp) as rate ,SUM(pri.purquantity) as pcsqty,SUM(pri.purquantitykgs) as kgqty FROM purchase_return_items pri \r\n"
			+ "JOIN purchase_return pr ON pri.purchasereturn_id = pr.id\r\n"
			+ "WHERE pr.warehouse_id = :wid  and pr.purchasereturndate  between :fromdate and :todate GROUP BY pri.product_id) AS pri ON p.id = pri.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT cbi.product_id, SUM(cbi.qty) as pcsqty FROM cartonbarcodeitems cbi \r\n"
			+ "JOIN cartonbarcode cb ON cbi.cartonbarcodeid = cb.id\r\n"
			+ "where cb.createddate between :fromdate and :todate  GROUP BY cbi.product_id) AS cbi ON p.id = cbi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT s.status, si.product_id,SUM(si.dlp)as rate, SUM(si.salesquantity) AS pcsqty,SUM(si.salesquantitykgs) AS kgqty FROM sales_items si\r\n"
			+ "JOIN sales s ON si.sales_id = s.id\r\n"
			+ "WHERE s.warehouse_id = :wid and s.invoicedate between :fromdate and :todate GROUP BY si.product_id, s.status) AS si ON p.id = si.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT joi.product_id, sum(joi.dlp) as rate, SUM(joi.jobsheet_qty) as pcsqty, SUM(joi.jobsheet_qty_kg) as kgqty FROM jobworkoutwarditems joi\r\n"
			+ "JOIN jobworkoutward jo ON joi.jobsheetoutward_id = jo.id\r\n"
			+ "WHERE jo.warehouse_id = :wid and jo.jobsheetdate between :fromdate and :todate GROUP BY joi.product_id) AS joi ON p.id = joi.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT join(select sum(closingos.qty) as closingqty, closingos.product_id from opening_stock closingos\r\n"
			+ "join product p on closingos.product_id = p.id \r\n"
			+ "where closingos.date < :fromdate group by closingos.product_id) closingos on closingos.product_id = p.id \r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT closingmrni.product_id,sum(closingmrni.dlp) as closingrate,SUM(closingmrni.mrnquantity) AS closingpcsqty,SUM(closingmrni.mrnquantitykgs) AS closingkgqty FROM material_receipt_note_items closingmrni \r\n"
			+ "JOIN material_recepit_note closingmrn ON closingmrni.mrn_id = closingmrn.id\r\n"
			+ "WHERE closingmrn.warehouse_id = :wid and closingmrn.mrndate < :fromdate GROUP BY closingmrni.product_id) closingmrni ON closingmrni.product_id = p.id \r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT closingsri.product_id,sum(closingsri.dlp) as closingrate,SUM(closingsri.srquantity) AS closingpcsqty,SUM(closingsri.srquantitykgs) AS closingkgqty FROM sales_return_items closingsri\r\n"
			+ "JOIN sales_return closingsr ON closingsri.sales_returnid = closingsr.id\r\n"
			+ "WHERE closingsr.warehouse_id = :wid and closingsr.salesreturndate < :fromdate GROUP BY closingsri.product_id) closingsri ON closingsri.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingmsc.productid, sum(closingmsc.stdqty) as closingstdqty FROM mastercartoon closingmsc \r\n"
			+ "JOIN product p ON closingmsc.productid = p.id \r\n"
			+ "where closingmsc.createddate < :fromdate GROUP BY closingmsc.productid) AS closingmsc ON closingmsc.productid = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingjii.product_id,sum(closingjii.dlp) As closingrate ,SUM(closingjii.jobsheet_qty) as closingpcsqty, sum(closingjii.jobsheet_qty_kg) as closingkgqty FROM jobwork_inward_items closingjii\r\n"
			+ "JOIN jobwork_inward closingji ON closingjii.jobsheet_id = closingji.id\r\n"
			+ "WHERE closingji.warehouse_id = :wid  and closingji.jobsheetdate < :fromdate GROUP BY closingjii.product_id) AS closingjii ON p.id = closingjii.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN(select closingdci.product_id,sum(closingdci.dlp) as closingrate,SUM(closingdci.dcquantity_placed) AS closingpcsqty,SUM(closingdci.dcquantity_placed_kg) AS closingkgqty from delivery_charge_items closingdci\r\n"
			+ "join delivery_charge closingdc on closingdci.dc_id = closingdc.id \r\n"
			+ "WHERE closingdc.warehouse_id = :wid and closingdc.dcdate < :fromdate and closingdc.status= 'pending' GROUP BY closingdci.product_id) closingdci ON closingdci.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN(SELECT closingpri.product_id,sum(closingpri.dlp) as closingrate,SUM(closingpri.purquantity) AS closingpcsqty,SUM(closingpri.purquantitykgs) AS closingkgqty FROM purchase_return_items closingpri \r\n"
			+ "JOIN purchase_return closingpr ON closingpri.purchasereturn_id = closingpr.id\r\n"
			+ "WHERE closingpr.warehouse_id = :wid and closingpr.purchasereturndate < :fromdate GROUP BY closingpri.product_id) closingpri ON closingpri.product_id = p.id \r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingcbi.product_id,SUM(closingcbi.qty) as closingpcsqty FROM cartonbarcodeitems closingcbi \r\n"
			+ "JOIN cartonbarcode closingcb ON closingcbi.cartonbarcodeid = closingcb.id\r\n"
			+ "where closingcb.createddate < :fromdate  GROUP BY closingcbi.product_id) AS closingcbi ON p.id = closingcbi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingsi.product_id,sum(closingsi.dlp) as closingrate,SUM(closingsi.salesquantity) as closingpcsqty,SUM(closingsi.salesquantitykgs) as closingkgqty FROM sales_items closingsi \r\n"
			+ "JOIN sales closings ON closingsi.sales_id = closings.id\r\n"
			+ "where closings.warehouse_id = :wid  and closings.invoicedate < :fromdate  GROUP BY closingsi.product_id) AS closingsi ON p.id = closingsi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingjoi.product_id, sum(closingjoi.dlp) as closingrate, SUM(closingjoi.jobsheet_qty) as closingpcsqty, SUM(closingjoi.jobsheet_qty_kg) as closingkgqty FROM jobworkoutwarditems closingjoi\r\n"
			+ "JOIN jobworkoutward closingjo ON closingjoi.jobsheetoutward_id = closingjo.id\r\n"
			+ "WHERE closingjo.warehouse_id = :wid and closingjo.jobsheetdate < :fromdate  GROUP BY closingjoi.product_id) AS closingjoi ON p.id = closingjoi.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalmrni.product_id,sum(totalmrni.dlp) as rate,SUM(totalmrni.mrnquantity) as totalpcsqty,sum(totalmrni.mrnquantitykgs) as totalkgqty FROM material_receipt_note_items totalmrni \r\n"
			+ "JOIN material_recepit_note totalmrn ON totalmrni.mrn_id = totalmrn.id\r\n"
			+ "                               and totalmrn.mrndate between :fromdate and :todate GROUP BY totalmrni.product_id) AS totalmrni ON p.id = totalmrni.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalsri.product_id,sum(totalsri.dlp) As rate ,SUM(totalsri.srquantity) as totalpcsqty,sum(totalsri.srquantitykgs) as totalkgqty FROM sales_return_items totalsri \r\n"
			+ "JOIN sales_return totalsr ON totalsri.sales_returnid = totalsr.id\r\n"
			+ "                               and totalsr.salesreturndate between :fromdate and :todate GROUP BY totalsri.product_id) AS totalsri ON p.id = totalsri.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalmsc.productid, SUM(totalmsc.stdqty) as totalstdqty  FROM mastercartoon totalmsc \r\n"
			+ "JOIN product p ON totalmsc.productid = p.id\r\n"
			+ "                              where totalmsc.createddate between :fromdate and :todate GROUP BY totalmsc.productid) AS totalmsc ON totalmsc.productid = p.id\r\n"
			+ " \r\n"
			+ " LEFT JOIN (SELECT totaljii.product_id, SUM(totaljii.dlp) as rate, SUM(totaljii.jobsheet_qty) as totalpcsqty, SUM(totaljii.jobsheet_qty_kg) as totalkgqty FROM jobwork_inward_items totaljii\r\n"
			+ "JOIN jobwork_inward totalji ON totaljii.jobsheet_id = totalji.id\r\n"
			+ "                             WHERE totalji.jobsheetdate between :fromdate and :todate  GROUP BY totaljii.product_id) AS totaljii ON p.id = totaljii.product_id\r\n"
			+ "                      \r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN(select totaldci.product_id,sum(totaldci.dlp) as totalclosingrate,SUM(totaldci.dcquantity_placed) AS totalpcsqty,SUM(totaldci.dcquantity_placed_kg) AS totalkgqty from delivery_charge_items totaldci\r\n"
			+ "join delivery_charge totaldc on totaldci.dc_id = totaldc.id \r\n"
			+ "                         and totaldc.dcdate < :fromdate and totaldc.status= 'pending' GROUP BY totaldci.product_id) totaldci ON totaldci.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalpri.product_id,sum(totalpri.dlp) as totalclosingrate ,SUM(totalpri.purquantity) as totalpcsqty,SUM(totalpri.purquantitykgs) as totalkgqty FROM purchase_return_items totalpri \r\n"
			+ "JOIN purchase_return totalpr ON totalpri.purchasereturn_id = totalpr.id\r\n"
			+ "						AND totalpr.purchasereturndate  between :fromdate and :todate GROUP BY totalpri.product_id) AS totalpri ON p.id = totalpri.product_id\r\n"
			+ "                              \r\n"
			+ "LEFT JOIN (SELECT totalcbi.product_id,SUM(totalcbi.qty) as totalpcsqty FROM cartonbarcodeitems totalcbi \r\n"
			+ "JOIN cartonbarcode totalcb ON totalcbi.cartonbarcodeid = totalcb.id\r\n"
			+ "                      WHERE totalcb.createddate < :fromdate  GROUP BY totalcbi.product_id) AS totalcbi ON p.id = totalcbi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalsi.product_id,sum(totalsi.dlp) as totalclosingrate,SUM(totalsi.salesquantity) as totalpcsqty,SUM(totalsi.salesquantitykgs) as totalkgqty FROM sales_items totalsi\r\n"
			+ "JOIN sales totals ON totalsi.sales_id = totals.id\r\n"
			+ "		            AND totals.invoicedate between :fromdate and :todate GROUP BY totalsi.product_id) AS totalsi ON p.id = totalsi.product_id\r\n"
			+ "     \r\n"
			+ " LEFT JOIN (SELECT totaljoi.product_id, sum(totaljoi.dlp) as rate, SUM(totaljoi.jobsheet_qty) as totalpcsqty, SUM(totaljoi.jobsheet_qty_kg) as totalkgqty FROM jobworkoutwarditems totaljoi\r\n"
			+ "JOIN jobworkoutward totaljo ON totaljoi.jobsheetoutward_id = totaljo.id\r\n"
			+ "                     AND totaljo.jobsheetdate between :fromdate and :todate GROUP BY totaljoi.product_id) AS totaljoi ON p.id = totaljoi.product_id    \r\n"
			+ "  \r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalclosingmrni.product_id, SUM(totalclosingmrni.mrnquantity) as totalclosingpcsqty,sum(totalclosingmrni.mrnquantitykgs) as totalclosingkgqty FROM material_receipt_note_items totalclosingmrni \r\n"
			+ "JOIN material_recepit_note totalclosingmrn ON totalclosingmrni.mrn_id = totalclosingmrn.id\r\n"
			+ "                    and totalclosingmrn.mrndate < :fromdate GROUP BY totalclosingmrni.product_id) totalclosingmrni ON totalclosingmrni.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalclosingsri.product_id,SUM(totalclosingsri.srquantity) as totalclosingpcsqty, sum(totalclosingsri.srquantitykgs) as totalclosingkgqty FROM sales_return_items totalclosingsri\r\n"
			+ "JOIN sales_return totalclosingsr ON totalclosingsri.sales_returnid = totalclosingsr.id\r\n"
			+ "                   and totalclosingsr.salesreturndate < :fromdate GROUP BY totalclosingsri.product_id) totalclosingsri ON totalclosingsri.product_id = p.id\r\n"
			+ " \r\n"
			+ "LEFT JOIN (SELECT totalclosingmsc.productid, sum(totalclosingmsc.stdqty) as totalclosingstdqty FROM mastercartoon totalclosingmsc \r\n"
			+ "JOIN product p ON totalclosingmsc.productid = p.id \r\n"
			+ "			     WHERE totalclosingmsc.createddate < :fromdate GROUP BY totalclosingmsc.productid) AS totalclosingmsc ON totalclosingmsc.productid = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalclosingjii.product_id, SUM(totalclosingjii.dlp) as rate, SUM(totalclosingjii.jobsheet_qty) as totalclosingpcsqty, SUM(totalclosingjii.jobsheet_qty_kg) as totalclosingkgqty FROM jobwork_inward_items totalclosingjii\r\n"
			+ "JOIN jobwork_inward totalclosingji ON totalclosingjii.jobsheet_id = totalclosingji.id\r\n"
			+ "				WHERE totalclosingji.jobsheetdate < :fromdate  GROUP BY totalclosingjii.product_id) AS totalclosingjii ON p.id = totalclosingjii.product_id\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "LEFT JOIN (select  totalclosingdci.product_id, SUM(totalclosingdci.dcquantity_placed) as totalclosingpcsqty,sum(totalclosingdci.dcquantity_placed_kg) as totalclosingkgqty from delivery_charge_items totalclosingdci\r\n"
			+ "join delivery_charge totalclosingdc on totalclosingdci.dc_id = totalclosingdc.id \r\n"
			+ "				AND totalclosingdc.dcdate < :fromdate and  totalclosingdc.status= 'pending'  GROUP BY totalclosingdci.product_id) totalclosingdci ON totalclosingdci.product_id = p.id\r\n"
			+ "                    \r\n"
			+ "LEFT JOIN (SELECT totalclosingpri.product_id, SUM(totalclosingpri.purquantity) as totalclosingpcsqty, SUM(totalclosingpri.purquantitykgs) as totalclosingkgqty FROM purchase_return_items totalclosingpri \r\n"
			+ "JOIN purchase_return totalclosingpr ON totalclosingpri.purchasereturn_id = totalclosingpr.id\r\n"
			+ "				AND totalclosingpr.purchasereturndate < :fromdate  GROUP BY totalclosingpri.product_id) totalclosingpri ON totalclosingpri.product_id = p.id \r\n"
			+ "                   \r\n"
			+ "LEFT JOIN (SELECT totalclosingcbi.product_id,SUM(totalclosingcbi.qty) as totalclosingpcsqty FROM cartonbarcodeitems totalclosingcbi\r\n"
			+ "JOIN cartonbarcode totalclosingcb ON totalclosingcbi.cartonbarcodeid = totalclosingcb.id\r\n"
			+ "				WHERE totalclosingcb.createddate < :fromdate  GROUP BY totalclosingcbi.product_id) AS totalclosingcbi ON p.id = totalclosingcbi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalclosingsi.product_id,sum(totalclosingsi.dlp) as totalclosingrate,SUM(totalclosingsi.salesquantity) as totalclosingpcsqty, SUM(totalclosingsi.salesquantitykgs) as totalclosingkgqty FROM sales_items totalclosingsi\r\n"
			+ "JOIN sales totalclosings ON totalclosingsi.sales_id = totalclosings.id\r\n"
			+ "		         AND totalclosings.invoicedate < :fromdate GROUP BY totalclosingsi.product_id) AS totalclosingsi ON p.id = totalclosingsi.product_id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT totalclosingjoi.product_id, sum(totalclosingjoi.dlp) as totalclosingrate, SUM(totalclosingjoi.jobsheet_qty) as totalclosingpcsqty, SUM(totalclosingjoi.jobsheet_qty_kg) as totalclosingkgqty FROM jobworkoutwarditems totalclosingjoi\r\n"
			+ "JOIN jobworkoutward totalclosingjo ON totalclosingjoi.jobsheetoutward_id = totalclosingjo.id\r\n"
			+ "                AND totalclosingjo.jobsheetdate < :fromdate GROUP BY totalclosingjoi.product_id) AS totalclosingjoi ON p.id = totalclosingjoi.product_id\r\n"
			+ "            \r\n"
			+ "WHERE mrni.pcsqty IS NOT NULL OR pri.pcsqty is NOT NULL OR dci.pcsqty IS NOT NULL OR sri.pcsqty is NOT NULL  OR pwoi.pcsqty IS NOT NULL\r\n"
			+ "\r\n"
			+ "group by p.id,p.product_name", nativeQuery = true)
	List<PendingPoVsGodownReports> pendingOrderVsGodownReport(@Param("wid")int wid,
			                                                  @Param("fromdate")String fromdate,
			                                                  @Param("todate") String todate);
	
		
		
//	for count
	
	
	
	
	@Query(value = "CALL pendingordervsgodownstockreport(?,?,?)", nativeQuery = true)
	List<PendingPoVsGodownReports> pendingOrderVsGodownReportCount(@Param("wid")int wid,
			                                                      @Param("fromdate")String fromdate,
			                                                      @Param("todate") String todate);
	
	
	
	@Query(value = "CALL PendingPo()", nativeQuery = true)
	List<PendingPoVsGodownReports> PendingPo();
	
	
	
	@Query(value = "CALL vsgodownreport(?,?,?)", nativeQuery = true)
	List<PendingPoVsGodownReports> VsGodown(@Param("wid")int wid,
			                                @Param("fromdate")String fromdate,
			                                @Param("todate") String todate);
}
