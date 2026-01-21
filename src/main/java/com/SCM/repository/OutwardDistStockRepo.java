package com.SCM.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.OutwardDistributorIndexDto;
import com.SCM.ReportDto.DistributorStockReport;
import com.SCM.model.OutwardDistributorStock;
import com.SCM.model.Sales;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.OutwardDistributorStockProjection;

@Repository
public interface OutwardDistStockRepo extends JpaRepository<OutwardDistributorStock, Integer> {


	// ------------------------------------- reports ----------------------------------------------
	

	@Query(value = "SELECT \r\n"
			+ "p.id,p.product_name AS product_name,\r\n"
			+ "(COALESCE(SUM(dos.qty),0) + COALESCE(SUM(idsi.qty),0) + COALESCE(SUM(dci.qty),0)) AS inwardqty,\r\n"
			+ "(COALESCE(SUM(odsi.qty),0)) AS outwardqty,\r\n"
			+ "(COALESCE(SUM(closingdos.closingqty),0)+COALESCE(SUM(closingidsi.closingqty),0)+COALESCE(SUM(closingdci.closingqty),0))  -  (COALESCE(SUM(closingodsi.closingqty),0)) as previousclosing,\r\n"
			+ "(COALESCE(SUM(closingdos.closingqty),0)+COALESCE(SUM(closingidsi.closingqty),0)+COALESCE(SUM(closingdci.closingqty),0))  -  (COALESCE(SUM(closingodsi.closingqty),0))  +  (COALESCE(SUM(dos.qty),0)+COALESCE(SUM(idsi.qty),0)+COALESCE(SUM(dci.qty),0)-COALESCE(SUM(odsi.qty),0)) as closingstock,\r\n"
			+ "(COALESCE(SUM(closingdos.closingqty * p.mrp),0)+COALESCE(SUM(closingidsi.closingqty * closingidsi.closingrate),0)+COALESCE(SUM(closingdci.closingqty * closingdci.closingrate),0))  -  (COALESCE(SUM(closingodsi.closingqty * closingodsi.closingrate),0))  +  (COALESCE(SUM(dos.qty * p.mrp),0)+COALESCE(SUM(idsi.qty * idsi.rate),0)+COALESCE(SUM(dci.qty * dci.rate),0)-COALESCE(SUM(odsi.qty * odsi.rate),0)) as amount\r\n"
			+ "FROM\r\n"
			+ "product p\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT dos.product_id, SUM(dos.quantity) AS qty  FROM distributor_opening_stock dos \r\n"
			+ "JOIN product p on dos.product_id = p.id\r\n"
			+ "WHERE dos.distributor_id = :distid AND dos.date BETWEEN :fromdate AND :todate  GROUP BY dos.product_id) dos on dos.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT dci.product_id, SUM(dci.dlp) AS rate,SUM(dci.dcquantity_placed) AS qty FROM delivery_charge_items dci\r\n"
			+ "JOIN delivery_charge dc ON dci.dc_id = dc.id\r\n"
			+ "WHERE dc.distributor_id = :distid AND dc.dcdate BETWEEN :fromdate and :todate  GROUP BY dci.product_id,dci.rate) dci ON dci.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT idsi.product_id,SUM(idsi.rate) AS rate,SUM(idsi.inwardqty) as qty FROM inward_distributor_stock_items idsi\r\n"
			+ "JOIN inward_distributor_stock ids ON  idsi.inwarddistid = ids.id\r\n"
			+ "WHERE ids.distributorid = :distid AND ids.indate BETWEEN :fromdate AND :todate GROUP BY idsi.product_id) idsi ON idsi.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT odsi.productid, SUM(odsi.rate) AS rate, SUM(odsi.outwardqty) AS qty FROM outward_distributor_stock_items odsi\r\n"
			+ "JOIN outward_distributor_stock ods ON odsi.outwarddisid = ods.id\r\n"
			+ "WHERE ods.distributoroid = :distid and ods.outdisdate BETWEEN :fromdate and :todate GROUP BY odsi.productid) odsi ON odsi.productid = p.id\r\n"
			+ "\r\n"
			+ "LEFT join (SELECT closingdos.product_id, SUM(closingdos.quantity) as closingqty FROM distributor_opening_stock closingdos \r\n"
			+ "JOIN product p on closingdos.product_id = p.id\r\n"
			+ "WHERE closingdos.distributor_id = :distid  AND closingdos.date < :fromdate  GROUP BY closingdos.product_id) closingdos on closingdos.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingdci.product_id, SUM(closingdci.dlp) AS closingrate, SUM(closingdci.dcquantity_placed) AS closingqty FROM delivery_charge_items closingdci\r\n"
			+ "JOIN delivery_charge closingdc on closingdci.dc_id = closingdc.id\r\n"
			+ "WHERE closingdc.distributor_id = :distid AND closingdc.dcdate < :fromdate  GROUP BY closingdci.product_id) closingdci ON closingdci.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingidsi.product_id,SUM(closingidsi.rate) AS closingrate,SUM(closingidsi.inwardqty) as closingqty FROM inward_distributor_stock_items closingidsi\r\n"
			+ "JOIN inward_distributor_stock closingids ON  closingidsi.inwarddistid = closingids.id\r\n"
			+ "WHERE closingids.distributorid = :distid AND closingids.indate < :fromdate GROUP BY closingidsi.product_id) closingidsi ON closingidsi.product_id = p.id\r\n"
			+ "\r\n"
			+ "LEFT JOIN (SELECT closingodsi.productid, SUM(closingodsi.rate) AS closingrate, SUM(closingodsi.outwardqty) AS closingqty FROM outward_distributor_stock_items closingodsi \r\n"
			+ "JOIN outward_distributor_stock closingods ON closingodsi.outwarddisid = closingods.id\r\n"
			+ "WHERE closingods.distributoroid = :distid AND closingods.outdisdate < :fromdate GROUP BY closingodsi.productid) closingodsi ON closingodsi.productid = p.id\r\n"
			+ "\r\n"
			+ "WHERE dci.qty IS NOT NULL OR dos.qty IS NOT NULL OR odsi.qty IS NOT NULL\r\n"
			+ "\r\n"
			+ "GROUP BY\r\n"
			+ "p.id, p.product_name",nativeQuery = true)
	public List<DistributorStockReport> distributorstockreport(@Param("fromdate")String fromdate,
			                                                   @Param("todate") String todate,
			                                                   @Param("distid")int distid);
	
	
	// index -----------------------------------------------------------------------------
	
	
	@Query(value = "        SELECT \r\n"
			+ "  ods.id AS id,\r\n"
			+ "  MAX(ods.outdisdate) AS outwardDate,\r\n"
			+ "  MAX(ods.invoiceno) AS invoiceno,\r\n"
			+ "  MAX(r.trade_name) AS retailer,\r\n"
		     + "  MAX(ods.grandtotal) AS grandtotal,\r\n"
            + "  MAX(ods.igst) AS igst,\r\n"
			+ "  MAX(ods.cgst) AS cgst,\r\n"
			+ "  MAX(ods.sgst) AS sgst,\r\n"
			+ "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n"
			+ "  MAX(ods.grossamount) AS grossamount,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n"
			+ "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n"
			+ "  MAX(ods.createbyname) AS createbyname,\r\n"
			+ "  MAX(ods.updatedbyname) AS updatedbyname,\r\n"
			+ "  MAX(ods.createdtime) AS createdtime,\r\n"
			+ "  MAX(d.trade_name) AS distributor,\r\n"
			+ "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n"
			+ "  MAX(ods.updatedtime) AS updatedtime,\r\n"
			+ "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n"
	        + "FROM outward_distributor_stock ods\r\n"
            + "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n"
        	+ "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n"
			+ "LEFT JOIN product p ON odsi.productid = p.id\r\n"
			+ "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n"
			+ "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n"
			+ "GROUP BY ods.id\r\n"
			, nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtor(Pageable p);
	
	


	
	
	
	@Query(value =  "        SELECT \r\n"
			+ "  ods.id AS id,\r\n"
			+ "  MAX(ods.outdisdate) AS outwardDate,\r\n"
			+ "  MAX(ods.invoiceno) AS invoiceno,\r\n"
			+ "  MAX(r.trade_name) AS retailer,\r\n"
		     + "  MAX(ods.grandtotal) AS grandtotal,\r\n"
            + "  MAX(ods.igst) AS igst,\r\n"
			+ "  MAX(ods.cgst) AS cgst,\r\n"
			+ "  MAX(ods.sgst) AS sgst,\r\n"
			+ "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n"
			+ "  MAX(ods.grossamount) AS grossamount,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n"
			+ "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n"
			+ "  MAX(ods.createbyname) AS createbyname,\r\n"
			+ "  MAX(ods.updatedbyname) AS updatedbyname,\r\n"
			+ "  MAX(ods.createdtime) AS createdtime,\r\n"
			+ "  MAX(d.trade_name) AS distributor,\r\n"
			+ "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n"
			+ "  MAX(ods.updatedtime) AS updatedtime,\r\n"
			+ "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n"
	        + "FROM outward_distributor_stock ods\r\n"
            + "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n"
           + "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n"
			+ "LEFT JOIN product p ON odsi.productid = p.id\r\n"
			+ "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n"
			+ "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n"
			+ " where ods.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ods.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or ods.outdisdate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(ods.outdisdate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or d.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(d.trade_name , ' ' ,'') Like CONCAT('%',:search,'%') "
			+ "GROUP BY ods.id\r\n"
		, nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorSearch(Pageable p , String search);

	//end
	//get outstock for distributor by dist id
	
	
	@Query(value = 
		    "SELECT \r\n" +
		    	    "  ods.id AS id,\r\n" +
		    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
		    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
		    	    "  MAX(r.trade_name) AS retailer,\r\n" +
		    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
		    	    "  MAX(ods.igst) AS igst,\r\n" +
		    	    "  MAX(ods.cgst) AS cgst,\r\n" +
		    	    "  MAX(ods.sgst) AS sgst,\r\n" +
		    	     "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
		    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
		    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
		    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
		    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
		    	    "  MAX(d.trade_name) AS distributor,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
		    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
		    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
		    	    "FROM outward_distributor_stock ods\r\n" +
		    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
		    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
		    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
		    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
		    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
		    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
		    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
		    	    "WHERE ds.distributor_id= :id\r\n" +
		    	    "GROUP BY ods.id"
, nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtor(long id);
	
	
	@Query(value =  "SELECT \r\n" +
    	    "  ods.id AS id,\r\n" +
    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
    	    "  MAX(r.trade_name) AS retailer,\r\n" +
    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
    	    "  MAX(ods.igst) AS igst,\r\n" +
    	    "  MAX(ods.cgst) AS cgst,\r\n" +
    	    "  MAX(ods.sgst) AS sgst,\r\n" +
    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
    	    "  MAX(d.trade_name) AS distributor,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
    	    "FROM outward_distributor_stock ods\r\n" +
    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
    	    "WHERE ds.distributor_id= :id\r\n" +
    	    "GROUP BY ods.id" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtor(long id,Pageable p);
	
	
	@Query(value = "SELECT \r\n"
			+ "  ods.id AS id,\r\n"
			+ "  MAX(ods.outdisdate) AS outwardDate,\r\n"
			+ "  MAX(ods.invoiceno) AS invoiceno,\r\n"
			+ "  MAX(r.trade_name) AS retailer,\r\n"
			+ "  MAX(ods.grandtotal) AS grandtotal,\r\n"
			+ "  MAX(ods.igst) AS igst,\r\n"
			+ "  MAX(ods.cgst) AS cgst,\r\n"
			+ "  MAX(ods.sgst) AS sgst,\r\n"
			+  "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" 
			+ "  MAX(ods.grossamount) AS grossamount,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n"
			+ "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n"
			+ "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n"
			+ "  MAX(ods.createdtime) AS createdtime,\r\n"
			+ "  MAX(ods.createbyname) AS createbyname,\r\n"
			+ "  MAX(ods.updatedbyname) AS updatedbyname,\r\n"
			+ "  MAX(d.trade_name) AS distributor,\r\n"
			+ "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n"
			+ "  MAX(ods.updatedtime) AS updatedtime,\r\n"
			+ "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n"
	        + "FROM outward_distributor_stock ods\r\n"
			+ "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n"
			+ "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n"
			+ "LEFT JOIN product p ON odsi.productid = p.id\r\n"
			+ "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n"
			+ "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n"
			+ "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n"
			+ "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n"
			+ "WHERE ds.rsmid = :id\r\n"
			+ "GROUP BY ods.id\r\n"
			, nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorRsm(long id,Pageable p);
	
	@Query(value = "SELECT \\r\\n\"\r\n"
			+ "			+ \"  ods.id AS id,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.outdisdate) AS outwardDate,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.invoiceno) AS invoiceno,\\r\\n\"\r\n"
			+ "			+ \"  MAX(r.trade_name) AS retailer,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.grandtotal) AS grandtotal,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.igst) AS igst,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.cgst) AS cgst,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.sgst) AS sgst,\\r\\n\"\r\n"
			+  "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" 
			+ "			+ \"  MAX(ods.grossamount) AS grossamount,\\r\\n\"\r\n"
			+ "			+ \"  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\\r\\n\"\r\n"
			+ "			+ \"  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\\r\\n\"\r\n"
			+ "			+ \"  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.createdtime) AS createdtime,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.createbyname) AS createbyname,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.updatedbyname) AS updatedbyname,\\r\\n\"\r\n"
			+ "			+ \"  MAX(d.trade_name) AS distributor,\\r\\n\"\r\n"
			+ "			+ \"  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\\r\\n\"\r\n"
			+ "			+ \"  MAX(ods.updatedtime) AS updatedtime,\\r\\n\"\r\n"
			+ "			+ \"  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\\r\\n\"\r\n"
			+ "	        + \"FROM outward_distributor_stock ods\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN distributor d ON ods.distributoroid = d.id\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN product p ON odsi.productid = p.id\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN retailer r ON ods.retailerid = r.id\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN staff s1 ON ds.rsmid = s1.id\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN staff s2 ON ds.aseid = s2.id\\r\\n\"\r\n"
			+ "			+ \"WHERE ds.rsmid = :id\\r\\n\"\r\n"
			+ "			+ \"GROUP BY ods.id\\r\\n" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorRsm(long id);
	
	@Query(value =
		    "SELECT \r\n" +
		    	    "  ods.id AS id,\r\n" +
		    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
		    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
		    	    "  MAX(r.trade_name) AS retailer,\r\n" +
		    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
		    	    "  MAX(ods.igst) AS igst,\r\n" +
		    	    "  MAX(ods.cgst) AS cgst,\r\n" +
		    	    "  MAX(ods.sgst) AS sgst,\r\n" +
		    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
		    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
		    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
		    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
		    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
		    	    "  MAX(d.trade_name) AS distributor,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
		    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
		    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
		    	    "FROM outward_distributor_stock ods\r\n" +
		    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
		    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
		    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
		    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
		    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
		    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
		    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
		    	    "WHERE ds.asmid = :id\r\n" +
		    	    "GROUP BY ods.id"
, nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorAsm(long id,Pageable p);
	
	@Query(value =  "SELECT \r\n" +
    	    "  ods.id AS id,\r\n" +
    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
    	    "  MAX(r.trade_name) AS retailer,\r\n" +
    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
    	    "  MAX(ods.igst) AS igst,\r\n" +
    	    "  MAX(ods.cgst) AS cgst,\r\n" +
    	    "  MAX(ods.sgst) AS sgst,\r\n" +
    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
    	    "  MAX(d.trade_name) AS distributor,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
    	    "FROM outward_distributor_stock ods\r\n" +
    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
    	    "WHERE ds.asmid = :id\r\n" +
    	    "GROUP BY ods.id" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorAsm(long id);
	
	
	@Query(value =
		    "SELECT \r\n" +
		    	    "  ods.id AS id,\r\n" +
		    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
		    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
		    	    "  MAX(r.trade_name) AS retailer,\r\n" +
		    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
		    	    "  MAX(ods.igst) AS igst,\r\n" +
		    	    "  MAX(ods.cgst) AS cgst,\r\n" +
		    	    "  MAX(ods.sgst) AS sgst,\r\n" +
		    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
		    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
		    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
		    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
		    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
		    	    "  MAX(d.trade_name) AS distributor,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
		    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
		    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
		    	    "FROM outward_distributor_stock ods\r\n" +
		    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
		    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
		    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
		    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
		    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
		    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
		    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
		    	    "WHERE ds.aseid = :id\r\n" +
		    	    "GROUP BY ods.id"
 , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorAse(long id,Pageable p);
	
	@Query(value = 
		    "SELECT \r\n" +
		    	    "  ods.id AS id,\r\n" +
		    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
		    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
		    	    "  MAX(r.trade_name) AS retailer,\r\n" +
		    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
		    	    "  MAX(ods.igst) AS igst,\r\n" +
		    	    "  MAX(ods.cgst) AS cgst,\r\n" +
		    	    "  MAX(ods.sgst) AS sgst,\r\n" +
		    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
		    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
		    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
		    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
		    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
		    	    "  MAX(d.trade_name) AS distributor,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
		    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
		    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
		    	    "FROM outward_distributor_stock ods\r\n" +
		    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
		    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
		    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
		    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
		    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
		    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
		    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
		    	    "WHERE ds.aseid = :id\r\n" +
		    	    "GROUP BY ods.id"
 , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorAse(long id);
	
	
	

	
	
	
	@Query(value =
		    "SELECT \r\n" +
		    	    "  ods.id AS id,\r\n" +
		    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
		    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
		    	    "  MAX(r.trade_name) AS retailer,\r\n" +
		    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
		    	    "  MAX(ods.igst) AS igst,\r\n" +
		    	    "  MAX(ods.cgst) AS cgst,\r\n" +
		    	    "  MAX(ods.sgst) AS sgst,\r\n" +
		    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
		    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
		    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
		    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
		    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
		    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
		    	    "  MAX(d.trade_name) AS distributor,\r\n" +
		    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
		    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
		    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
		    	    "FROM outward_distributor_stock ods\r\n" +
		    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
		    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
		    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
		    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
		    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
		    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
		    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
		    	    "WHERE ds.distributor_id = :id \r\n" +
		    	    "AND (\r\n" +
		    	    "  ods.id LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(ods.id, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  ods.outdisdate LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(ods.outdisdate, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  d.trade_name LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(d.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  p.product_name LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(p.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  odsi.outwardqty LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(odsi.outwardqty, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  odsi.amount LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(odsi.amount, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  odsi.rate LIKE CONCAT('%', :search, '%') OR \r\n" +
		    	    "  REPLACE(odsi.rate, ' ', '') LIKE CONCAT('%', :search, '%')\r\n" +
		    	    ")\r\n" +
		    	    "GROUP BY ods.id"
 , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorSearch(long id,Pageable p ,String search);
	
	
	@Query(value =    "SELECT \r\n" +
    	    "  ods.id AS id,\r\n" +
    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
    	    "  MAX(r.trade_name) AS retailer,\r\n" +
    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
    	    "  MAX(ods.igst) AS igst,\r\n" +
    	    "  MAX(ods.cgst) AS cgst,\r\n" +
    	    "  MAX(ods.sgst) AS sgst,\r\n" +
    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
    	    "  MAX(d.trade_name) AS distributor,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
    	    "FROM outward_distributor_stock ods\r\n" +
    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
    	    "WHERE ds.rsmid = :id \r\n" +
    	    "AND (\r\n" +
    	    "  ods.id LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(ods.id, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  ods.outdisdate LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(ods.outdisdate, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  d.trade_name LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(d.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  p.product_name LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(p.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.outwardqty LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.outwardqty, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.amount LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.amount, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.rate LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.rate, ' ', '') LIKE CONCAT('%', :search, '%')\r\n" +
    	    ")\r\n" +
    	    "GROUP BY ods.id" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorSearchRsm(long id,Pageable p ,String search);
	
	
	@Query(value =   "SELECT \r\n" +
    	    "  ods.id AS id,\r\n" +
    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
    	    "  MAX(r.trade_name) AS retailer,\r\n" +
    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
    	    "  MAX(ods.igst) AS igst,\r\n" +
    	    "  MAX(ods.cgst) AS cgst,\r\n" +
    	    "  MAX(ods.sgst) AS sgst,\r\n" +
    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
    	    "  MAX(d.trade_name) AS distributor,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
    	    "FROM outward_distributor_stock ods\r\n" +
    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
    	    "WHERE ds.asmid = :id \r\n" +
    	    "AND (\r\n" +
    	    "  ods.id LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(ods.id, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  ods.outdisdate LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(ods.outdisdate, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  d.trade_name LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(d.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  p.product_name LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(p.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.outwardqty LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.outwardqty, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.amount LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.amount, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.rate LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.rate, ' ', '') LIKE CONCAT('%', :search, '%')\r\n" +
    	    ")\r\n" +
    	    "GROUP BY ods.id" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorSearchAsm(long id,Pageable p ,String search);
	
	@Query(value =   "SELECT \r\n" +
    	    "  ods.id AS id,\r\n" +
    	    "  MAX(ods.outdisdate) AS outwardDate,\r\n" +
    	    "  MAX(ods.invoiceno) AS invoiceno,\r\n" +
    	    "  MAX(r.trade_name) AS retailer,\r\n" +
    	    "  MAX(ods.grandtotal) AS grandtotal,\r\n" +
    	    "  MAX(ods.igst) AS igst,\r\n" +
    	    "  MAX(ods.cgst) AS cgst,\r\n" +
    	    "  MAX(ods.sgst) AS sgst,\r\n" +
    	    "  MAX(ods.vouchermaster_series) AS vouchermaster_series,\r\n" +
    	    "  MAX(ods.grossamount) AS grossamount,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s1.staff_name) AS rsmname,\r\n" +
    	    "  GROUP_CONCAT(DISTINCT s2.staff_name) AS asename,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.createddate), '%d-%m-%Y') AS createddate,\r\n" +
    	    "  MAX(ods.createbyname) AS createbyname,\r\n" +
    	    "  MAX(ods.updatedbyname) AS updatedbyname,\r\n" +
    	    "  MAX(ods.createdtime) AS createdtime,\r\n" +
    	    "  MAX(d.trade_name) AS distributor,\r\n" +
    	    "  DATE_FORMAT(MAX(ods.updateddate), '%d-%m-%Y') AS updateddate,\r\n" +
    	    "  MAX(ods.updatedtime) AS updatedtime,\r\n" +
    	    "  GROUP_CONCAT(p.product_name SEPARATOR ', ') AS products\r\n" +
    	    "FROM outward_distributor_stock ods\r\n" +
    	    "LEFT JOIN distributor d ON ods.distributoroid = d.id\r\n" +
    	    "LEFT JOIN retailer r ON ods.retailerid = r.id\r\n" +
    	    "LEFT JOIN distributor_to_staff ds ON ds.distributor_id = d.id\r\n" +
    	    "LEFT JOIN outward_distributor_stock_items odsi ON ods.id = odsi.outwarddisid\r\n" +
    	    "LEFT JOIN product p ON odsi.productid = p.id\r\n" +
    	    "LEFT JOIN staff s1 ON ds.rsmid = s1.id\r\n" +
    	    "LEFT JOIN staff s2 ON ds.aseid = s2.id\r\n" +
    	    "WHERE ds.aseid = :id \r\n" +
    	    "AND (\r\n" +
    	    "  ods.id LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(ods.id, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  ods.outdisdate LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(ods.outdisdate, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  d.trade_name LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(d.trade_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  p.product_name LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(p.product_name, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.outwardqty LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.outwardqty, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.amount LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.amount, ' ', '') LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  odsi.rate LIKE CONCAT('%', :search, '%') OR \r\n" +
    	    "  REPLACE(odsi.rate, ' ', '') LIKE CONCAT('%', :search, '%')\r\n" +
    	    ")\r\n" +
    	    "GROUP BY ods.id" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> indexOutwardDistribtorSearchAse(long id,Pageable p ,String search);
	//end
	// report by distributor
	
	
	@Query(value = "SELECT p.id,\\r\\n\"\r\n"
			+ "			+ \"p.product_name AS product_name,\\r\\n\"\r\n"
			+ "			+ \"COALESCE(SUM(dci.qty), 0) AS dcquantity,\\r\\n\"\r\n"
			+ "			+ \"COALESCE(SUM(odsi.qty), 0) AS outwarddistributorstockqty,\\r\\n\"\r\n"
			+ "			+ \"COALESCE(SUM(dos.quantity),0) + COALESCE(SUM(dci.qty),0) AS inwardqty,\\r\\n\"\r\n"
			+ "			+ \"COALESCE(SUM(odsi.qty),0) AS outwardqty,\\r\\n\"\r\n"
			+ "			+ \"COALESCE(SUM(dci.rate),0) AS dcrate,\\r\\n\"\r\n"
			+ "			+ \"COALESCE(SUM(closingdos.closingqty),0) + COALESCE(SUM(closingdci.closingdcqty),0) - COALESCE(SUM(closingodsi.closingodsiqty),0) as previousclosing,\\r\\n\"\r\n"
			+ "			+ \"(COALESCE(SUM(closingdos.closingqty),0) + COALESCE(SUM(closingdci.closingdcqty),0) - COALESCE(SUM(closingodsi.closingodsiqty),0) - COALESCE(SUM(odsi.qty),0) + COALESCE(SUM(dci.qty),0) + COALESCE(SUM(dos.quantity),0)) as closingstock,\\r\\n\"\r\n"
			+ "			+ \"((COALESCE(SUM(closingdos.closingqty),0) + COALESCE(SUM(closingdci.closingdcqty),0) - COALESCE(SUM(closingodsi.closingodsiqty),0) - COALESCE(SUM(odsi.qty),0) + COALESCE(SUM(dci.qty), 0) + COALESCE(SUM(dos.quantity),0)) * COALESCE(SUM(p.mrp),0)) as amount\\r\\n\"\r\n"
			+ "			+ \"FROM\\r\\n\"\r\n"
			+ "			+ \"product p\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN distributor_opening_stock dos ON p.id = dos.product_id\\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN (SELECT dci.rate,dci.product_id,SUM(dci.dcquantity) AS qty FROM delivery_charge_items dci JOIN delivery_charge dc ON dci.dc_id = dc.id \\r\\n\"\r\n"
			+ "			+ \"WHERE dc.distributor_id = :did and dc.dcdate between  :fromdate and :todate  GROUP BY dci.product_id,dci.rate) dci ON dci.product_id = p.id\\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN (SELECT odsi.productid, SUM(odsi.outwardqty) AS qty FROM outward_distributor_stock_items odsi JOIN outward_distributor_stock ods ON odsi.outwarddisid = ods.id \\r\\n\"\r\n"
			+ "			+ \"WHERE ods.distributoroid = :did and ods.outdisdate between :fromdate and :todate GROUP BY odsi.productid) odsi ON odsi.productid = p.id\\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"LEFT join (select sum(closingdos.quantity) as closingqty, closingdos.product_id from distributor_opening_stock closingdos join product p on closingdos.product_id = p.id\\r\\n\"\r\n"
			+ "			+ \"where closingdos.date < :fromdate group by closingdos.product_id) closingdos on closingdos.product_id = p.id \\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN (select SUM(closingdci.dcquantity) AS closingdcqty, closingdci.product_id from delivery_charge_items closingdci join delivery_charge closingdc on closingdci.dc_id = closingdc.id\\r\\n\"\r\n"
			+ "			+ \"WHERE closingdc.distributor_id = :did and closingdc.dcdate < :fromdate  GROUP BY closingdci.product_id) closingdci ON closingdci.product_id = p.id\\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"LEFT JOIN (SELECT closingodsi.productid, SUM(closingodsi.outwardqty) AS closingodsiqty FROM outward_distributor_stock_items closingodsi JOIN outward_distributor_stock closingods ON closingodsi.outwarddisid = closingods.id\\r\\n\"\r\n"
			+ "			+ \"WHERE closingods.distributoroid = :did and closingods.outdisdate < :fromdate GROUP BY closingodsi.productid) closingodsi ON closingodsi.productid = p.id\\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"WHERE dos.distributor_id = :did\\r\\n\"\r\n"
			+ "			+ \"\\r\\n\"\r\n"
			+ "			+ \"GROUP BY \\r\\n\"\r\n"
			+ "			+ \"p.id, p.product_name,dos.date" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> reportOutwardDistribtor(long did , Pageable p);
	
	
	
	
	@Query(value = "select outward_distributor_stock_items.id as id , outward_distributor_stock.outdisdate as outwardDate , distributor.trade_name as distributor ,\r\n"
			+ "product.product_name as product , outward_distributor_stock_items.outwardqty as quantity , outward_distributor_stock_items.amount as amount,\r\n"
			+ "outward_distributor_stock_items.rate as rate from outward_distributor_stock left join outward_distributor_stock_items\r\n"
			+ "on outward_distributor_stock.id = outward_distributor_stock_items.outwarddisid \r\n"
			+ "left join distributor on outward_distributor_stock.distributoroid = distributor.id\r\n"
			+ "left join product on outward_distributor_stock_items.productid = product.id"
			+ " where outward_distributor_stock.distributoroid =:did and (outward_distributor_stock_items.id Like CONCAT('%',:search,'%')"
			+ " or REPLACE(outward_distributor_stock_items.id , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or outward_distributor_stock.outdisdate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(outward_distributor_stock.outdisdate , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or distributor.trade_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(distributor.trade_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or product.product_name Like CONCAT('%',:search,'%')"
			+ " or REPLACE(product.product_name , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or outward_distributor_stock_items.outwardqty Like CONCAT('%',:search,'%')"
			+ " or REPLACE(outward_distributor_stock_items.outwardqty , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or outward_distributor_stock_items.amount Like CONCAT('%',:search,'%')"
			+ " or REPLACE(outward_distributor_stock_items.amount , ' ' ,'') Like CONCAT('%',:search,'%')"
			+ " or outward_distributor_stock_items.rate Like CONCAT('%',:search,'%')"
			+ " or REPLACE(outward_distributor_stock_items.rate , ' ' ,'') Like CONCAT('%',:search,'%'))" , nativeQuery = true)
	public List<OutwardDistributorStockProjection> reportOutwardDistribtorSearch(long did ,Pageable p,String search);
	
	
	@Query(value="select r.id,r.trade_name from retailer as r left join distributor as d on r.distributor_id=d.id where d.id=?1",nativeQuery = true)
	List<OutwardDistributorIndexDto> getAllRetailer(int id);
	
	  @Query(value = "SELECT * FROM outward_distributor_stock os order by id desc limit 1",nativeQuery = true)
	  OutwardDistributorStock lastrowstatus();
	  
	  OutwardDistributorStock findTopByVoucherMasterOrderByIdDesc(VoucherMaster voucher);
	  
	//
	//
	  OutwardDistributorStock findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(VoucherMaster voucher);
		
	  OutwardDistributorStock findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(VoucherMaster voucher);
	
	
	
	
}
