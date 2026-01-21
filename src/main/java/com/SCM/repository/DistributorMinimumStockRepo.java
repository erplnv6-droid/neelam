package com.SCM.repository;

import com.SCM.IndexDto.DistributorMinimumStockIndex;
import com.SCM.IndexDto.DistributorMinimumStockReportIndex;
import com.SCM.model.DistributorMinimumStock;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorMinimumStockRepo extends JpaRepository<DistributorMinimumStock, Integer> {
	
  @Query(value = "SELECT p.id, p.product_name,\r\n"
  		+ "    COALESCE(SUM(dosi.qty), 0) AS distopeningstockqty,\r\n"
  		+ "    COALESCE(SUM(dci.qty), 0) AS dcquantity,\r\n"
  		+ "    COALESCE(SUM(odsi.qty), 0) AS outwarddistributorstockqty,\r\n"
  		+ "    COALESCE(SUM(dmsi.qty), 0) AS minimumstockqty,\r\n"
  		+ "    (COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0)) AS inwardqty,\r\n"
  		+ "    (COALESCE(SUM(odsi.qty), 0)) AS outwardqty,\r\n"
  		+ "    COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0) - COALESCE(SUM(odsi.qty), 0) AS closingstock,\r\n"
  		+ "    (COALESCE(SUM(dosi.qty * p.mrp), 0) + COALESCE(SUM(dci.qty * dci.rate), 0) - COALESCE(SUM(odsi.qty * odsi.rate), 0)) AS amount\r\n"
  		+ "FROM \r\n"
  		+ "    product p\r\n"
  		+ "LEFT JOIN (SELECT dosi.product_id, SUM(dosi.qty) AS qty FROM distributor_opening_stock_items dosi\r\n"
  		+ "JOIN distributor_opening_stock dos ON dosi.distributor_openingstock_id = dos.id\r\n"
  		+ "WHERE dos.distributor_id = :distid GROUP BY dosi.product_id) dosi ON dosi.product_id = p.id\r\n"
  		+ "     \r\n"
  		+ "LEFT JOIN (SELECT SUM(dci.dlp) AS rate, dci.product_id, SUM(dci.dcquantity_placed) AS qty FROM delivery_charge_items dci\r\n"
  		+ "JOIN delivery_charge dc ON dci.dc_id = dc.id\r\n"
  		+ "WHERE dc.distributor_id = :distid GROUP BY dci.product_id, dci.rate) dci ON dci.product_id = p.id\r\n"
  		+ "\r\n"
  		+ "LEFT JOIN (SELECT SUM(odsi.rate) AS rate, odsi.productid, SUM(odsi.outwardqty) AS qty FROM outward_distributor_stock_items odsi\r\n"
  		+ "JOIN outward_distributor_stock ods ON odsi.outwarddisid = ods.id\r\n"
  		+ "WHERE ods.distributoroid = :distid  GROUP BY odsi.productid) odsi ON odsi.productid = p.id\r\n"
  		+ "\r\n"
  		+ "LEFT JOIN (SELECT dmsi.product_id, SUM(dmsi.stockqty) AS qty FROM distributor_minimum_stock_items dmsi\r\n"
  		+ "JOIN distributor_minimum_stock dms ON dmsi.dms_id = dms.id\r\n"
  		+ "WHERE dms.distributorid = :distid GROUP BY dmsi.product_id) dmsi ON dmsi.product_id = p.id\r\n"
  		+ "\r\n"
  		+ "WHERE dosi.qty IS NOT NULL OR dmsi.qty IS NOT NULL OR odsi.qty IS NOT NULL OR dci.qty IS NOT NULL\r\n"
  		+ "GROUP BY p.id HAVING COALESCE(SUM(dmsi.qty), 0) >= COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0) - COALESCE(SUM(odsi.qty), 0)", nativeQuery = true)
  List<DistributorMinimumStockReportIndex> MinimumStockForDistributor(int distid);
  
  
  @Query(value = "SELECT \r\n"
  		+ "p.product_name,\r\n"
  		+ "COALESCE(SUM(p.id), 0) AS id,\r\n"
  		+ "(COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0)) AS inwardpcsqty,\r\n"
  		+ "(COALESCE(SUM(dosi.kgqty), 0) + COALESCE(SUM(dci.kgqty), 0)) AS inwardkgqty,\r\n"
  		+ "(COALESCE(SUM(odsi.qty), 0)) AS outwardpcsqty,\r\n"
  		+ "(COALESCE(SUM(odsi.kgqty), 0)) AS outwardkgqty,\r\n"
  		+ "(COALESCE(SUM(dosi.qty * p.mrp), 0) + COALESCE(SUM(dci.qty * dci.rate), 0) - COALESCE(SUM(odsi.qty * odsi.rate), 0)) AS pcsamount,\r\n"
  		+ "(COALESCE(SUM(dosi.kgqty * p.mrp), 0) + COALESCE(SUM(dci.kgqty * dci.rate), 0) - COALESCE(SUM(odsi.kgqty * odsi.rate), 0)) AS kgamount,\r\n"
  		+ "COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0) - COALESCE(SUM(odsi.qty), 0) AS closingpcsstock,\r\n"
  		+ "COALESCE(SUM(dosi.kgqty), 0) + COALESCE(SUM(dci.kgqty), 0) - COALESCE(SUM(odsi.kgqty), 0) AS closingkgstock\r\n"
  		+ "FROM product p\r\n"
  		+ "  	\r\n"
  		+ "LEFT JOIN (SELECT dosi.product_id, SUM(dosi.qty) AS qty, SUM(dosi.kgqty) AS kgqty\r\n"
  		+ "FROM distributor_opening_stock_items dosi\r\n"
  		+ "JOIN distributor_opening_stock dos ON dosi.distributor_openingstock_id = dos.id\r\n"
  		+ "WHERE dos.distributor_id = 7 GROUP BY dosi.product_id) dosi ON dosi.product_id = p.id\r\n"
  		+ "  	\r\n"
  		+ "LEFT JOIN (SELECT SUM(dci.dlp) AS rate, dci.product_id, SUM(dci.dcquantity_placed) AS qty, SUM(dci.dcquantity_placed_kg) AS kgqty FROM delivery_charge_items dci\r\n"
  		+ "JOIN delivery_charge dc ON dci.dc_id = dc.id WHERE dc.distributor_id = 7 \r\n"
  		+ "GROUP BY dci.product_id, dci.rate) dci ON dci.product_id = p.id\r\n"
  		+ "  	\r\n"
  		+ "LEFT JOIN (SELECT SUM(odsi.rate) AS rate, odsi.productid, SUM(odsi.outwardqty) AS qty, SUM(odsi.kgoutwardqty) AS kgqty FROM outward_distributor_stock_items odsi\r\n"
  		+ "JOIN outward_distributor_stock ods ON odsi.outwarddisid = ods.id\r\n"
  		+ "WHERE ods.distributoroid = 7  GROUP BY odsi.productid) odsi ON odsi.productid = p.id\r\n"
  		+ "  	\r\n"
  		+ "LEFT JOIN (SELECT dmsi.product_id, SUM(dmsi.stockqty) AS qty, SUM(dmsi.kgstockqty) AS kgqty FROM distributor_minimum_stock_items dmsi\r\n"
  		+ "JOIN distributor_minimum_stock dms ON dmsi.dms_id = dms.id\r\n"
  		+ "WHERE dms.distributorid = 7 GROUP BY dmsi.product_id) dmsi ON dmsi.product_id = p.id\r\n"
  		+ "  		\r\n"
  		+ "WHERE dosi.qty IS NOT NULL OR dmsi.qty IS NOT NULL OR odsi.qty IS NOT NULL OR dci.qty IS NOT NULL \r\n"
  		+ "OR dosi.kgqty IS NOT NULL OR dmsi.kgqty IS NOT NULL OR odsi.kgqty IS NOT NULL OR dci.kgqty IS NOT NULL\r\n"
  		+ "GROUP BY p.id\r\n"
  		+ "HAVING \r\n"
  		+ "	COALESCE(SUM(dmsi.qty), 0) >= COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0) - COALESCE(SUM(odsi.qty), 0)", nativeQuery = true)
  List<DistributorMinimumStockReportIndex> MinimumStockForDistributor1(int distid, Pageable p);
  
  
  @Query(value = "SELECT dms.id,DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate,dms.createbyname,dms.updatedbyname,dms.createdtime,DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate,d.trade_name,DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate,dms.updatedtime "
  		+ " FROM distributor_minimum_stock dms"
  		+ " LEFT JOIN distributor d ON dms.distributorid = d.id", nativeQuery = true)
  List<DistributorMinimumStockIndex> IndexDistributorMinimumStock(Pageable p);
  
  
  @Query(value="SELECT dms.id,DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate,dms.createbyname,dms.updatedbyname,dms.createdtime,DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate,d.trade_name,DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate,dms.updatedtime\r\n"
  		+ "  		FROM distributor_minimum_stock dms\r\n"
  		+ "  		 LEFT JOIN distributor d ON dms.distributorid = d.id where d.id=:id",nativeQuery = true)
  List<DistributorMinimumStockIndex> IndexDistributorMinimumStockDistributor(int id,Pageable p);
  
  @Query(value="SELECT dms.id,DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate,dms.createbyname,dms.updatedbyname,dms.createdtime,DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate,d.trade_name,DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate,dms.updatedtime\r\n"
  		+ "  		FROM distributor_minimum_stock dms\r\n"
  		+ "  		 LEFT JOIN distributor d ON dms.distributorid = d.id\r\n"
  		+ "         left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
  		+ "         left join staff s on ds.rsmid=s.id\r\n"
  		+ "         where ds.rsmid=:id",nativeQuery = true)
  List<DistributorMinimumStockIndex> IndexDistributorMinimumStockRsm(int id,Pageable p);
  
  @Query(value="SELECT dms.id,DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate,dms.createbyname,dms.updatedbyname,dms.createdtime,DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate,d.trade_name,DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate,dms.updatedtime\r\n"
	  		+ "  		FROM distributor_minimum_stock dms\r\n"
	  		+ "  		 LEFT JOIN distributor d ON dms.distributorid = d.id\r\n"
	  		+ "         left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
	  		+ "         left join staff s on ds.asmid=s.id\r\n"
	  		+ "         where ds.asmid=:id",nativeQuery = true)
	  List<DistributorMinimumStockIndex> IndexDistributorMinimumStockAsm(int id,Pageable p);
  
  @Query(value="SELECT dms.id,DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate,dms.createbyname,dms.updatedbyname,dms.createdtime,DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate,d.trade_name,DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate,dms.updatedtime\r\n"
	  		+ "  		FROM distributor_minimum_stock dms\r\n"
	  		+ "  		 LEFT JOIN distributor d ON dms.distributorid = d.id\r\n"
	  		+ "         left join distributor_to_staff ds on d.id=ds.distributor_id\r\n"
	  		+ "         left join staff s on ds.aseid=s.id\r\n"
	  		+ "         where ds.aseid=:id",nativeQuery = true)
	  List<DistributorMinimumStockIndex> IndexDistributorMinimumStockAse(int id,Pageable p);
  
  
  
  
  @Query(value = "SELECT dms.id,DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate,dms.createbyname,dms.updatedbyname,dms.createdtime,DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate,d.trade_name,DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate,dms.updatedtime "
  		+ " FROM distributor_minimum_stock dms "
  		+ " LEFT JOIN distributor d ON dms.distributorid = d.id "
  		+ " WHERE dms.dmsdate Like CONCAT('%', :search, '%') OR dms.id Like CONCAT('%', :search, '%') OR dms.dmsdate Like CONCAT('%', :search, '%') or REGEXP_LIKE(REGEXP_REPLACE(d.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', '')) OR dms.createbyname Like CONCAT('%', :search, '%') OR dms.updatedbyname Like CONCAT('%', :search, '%')", nativeQuery = true)
  List<DistributorMinimumStockIndex> SearchByDistributorMinimumStock(String search, Pageable p);
  
  @Query(value = "SELECT dms.id, DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate, dms.createbyname, dms.updatedbyname, dms.createdtime, " +
          "DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate, d.trade_name, DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate, dms.updatedtime " +
          "FROM distributor_minimum_stock dms " +
          "LEFT JOIN distributor d ON dms.distributorid = d.id " +
          "WHERE d.id = :id AND (" +
          "  CAST(dms.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
          "  DATE_FORMAT(dms.dmsdate, '%d-%m-%Y') LIKE CONCAT('%', :search, '%') OR " +
          "  REGEXP_REPLACE(d.trade_name, '[^a-zA-Z0-9]', '') REGEXP REPLACE(CONCAT('%', :search, '%'), '[^a-zA-Z0-9]', '') OR " +
          "  dms.createbyname LIKE CONCAT('%', :search, '%') OR " +
          "  dms.updatedbyname LIKE CONCAT('%', :search, '%')" +
          ")",
  nativeQuery = true)
List<DistributorMinimumStockIndex> SearchByDistributorMinimumStockDistributor(@Param("search") String search, Pageable p, @Param("id") int id);

  
  @Query(value = "SELECT dms.id, DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate, dms.createbyname, dms.updatedbyname, dms.createdtime, " +
          "DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate, d.trade_name, DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate, dms.updatedtime " +
          "FROM distributor_minimum_stock dms " +
          "LEFT JOIN distributor d ON dms.distributorid = d.id " +
          "left join distributor_to_staff ds on d.id=ds.distributor_id " +
      	  	"left join staff s on ds.rsmid=s.id " +
          "WHERE ds.rsmid = :id AND (" +
          "  CAST(dms.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
          "  DATE_FORMAT(dms.dmsdate, '%d-%m-%Y') LIKE CONCAT('%', :search, '%') OR " +
          "  REGEXP_REPLACE(d.trade_name, '[^a-zA-Z0-9]', '') REGEXP REPLACE(CONCAT('%', :search, '%'), '[^a-zA-Z0-9]', '') OR " +
          "  dms.createbyname LIKE CONCAT('%', :search, '%') OR " +
          "  dms.updatedbyname LIKE CONCAT('%', :search, '%')" +
          ")",
  nativeQuery = true)
List<DistributorMinimumStockIndex> SearchByDistributorMinimumStockRsm(@Param("search") String search, Pageable p, @Param("id") int id);
  
  
  @Query(value = "SELECT dms.id, DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate, dms.createbyname, dms.updatedbyname, dms.createdtime, " +
          "DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate, d.trade_name, DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate, dms.updatedtime " +
          "FROM distributor_minimum_stock dms " +
          "LEFT JOIN distributor d ON dms.distributorid = d.id " +
          "left join distributor_to_staff ds on d.id=ds.distributor_id " +
      	  	"left join staff s on ds.asmid=s.id " +
          "WHERE ds.asmid = :id AND (" +
          "  CAST(dms.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
          "  DATE_FORMAT(dms.dmsdate, '%d-%m-%Y') LIKE CONCAT('%', :search, '%') OR " +
          "  REGEXP_REPLACE(d.trade_name, '[^a-zA-Z0-9]', '') REGEXP REPLACE(CONCAT('%', :search, '%'), '[^a-zA-Z0-9]', '') OR " +
          "  dms.createbyname LIKE CONCAT('%', :search, '%') OR " +
          "  dms.updatedbyname LIKE CONCAT('%', :search, '%')" +
          ")",
  nativeQuery = true)
List<DistributorMinimumStockIndex> SearchByDistributorMinimumStockAsm(@Param("search") String search, Pageable p, @Param("id") int id);
  
  
  @Query(value = "SELECT dms.id, DATE_FORMAT(dms.createddate,'%d-%m-%Y') AS createddate, dms.createbyname, dms.updatedbyname, dms.createdtime, " +
          "DATE_FORMAT(dms.dmsdate,'%d-%m-%Y') AS dmsdate, d.trade_name, DATE_FORMAT(dms.updateddate,'%d-%m-%Y') AS updateddate, dms.updatedtime " +
          "FROM distributor_minimum_stock dms " +
          "LEFT JOIN distributor d ON dms.distributorid = d.id " +
          "left join distributor_to_staff ds on d.id=ds.distributor_id " +
      	  	"left join staff s on ds.aseid=s.id " +
          "WHERE ds.aseid = :id AND (" +
          "  CAST(dms.id AS CHAR) LIKE CONCAT('%', :search, '%') OR " +
          "  DATE_FORMAT(dms.dmsdate, '%d-%m-%Y') LIKE CONCAT('%', :search, '%') OR " +
          "  REGEXP_REPLACE(d.trade_name, '[^a-zA-Z0-9]', '') REGEXP REPLACE(CONCAT('%', :search, '%'), '[^a-zA-Z0-9]', '') OR " +
          "  dms.createbyname LIKE CONCAT('%', :search, '%') OR " +
          "  dms.updatedbyname LIKE CONCAT('%', :search, '%')" +
          ")",
  nativeQuery = true)
List<DistributorMinimumStockIndex> SearchByDistributorMinimumStockAse(@Param("search") String search, Pageable p, @Param("id") int id);
  
  
  
  @Query(value = "SELECT \r\n"
	  		+ "p.product_name,\r\n"
	  		+ "COALESCE(SUM(p.id), 0) AS id,\r\n"
	  		+ "(COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0)) AS inwardpcsqty,\r\n"
	  		+ "(COALESCE(SUM(dosi.kgqty), 0) + COALESCE(SUM(dci.kgqty), 0)) AS inwardkgqty,\r\n"
	  		+ "(COALESCE(SUM(odsi.qty), 0)) AS outwardpcsqty,\r\n"
	  		+ "(COALESCE(SUM(odsi.kgqty), 0)) AS outwardkgqty,\r\n"
	  		+ "(COALESCE(SUM(dosi.qty * p.mrp), 0) + COALESCE(SUM(dci.qty * dci.rate), 0) - COALESCE(SUM(odsi.qty * odsi.rate), 0)) AS pcsamount,\r\n"
	  		+ "(COALESCE(SUM(dosi.kgqty * p.mrp), 0) + COALESCE(SUM(dci.kgqty * dci.rate), 0) - COALESCE(SUM(odsi.kgqty * odsi.rate), 0)) AS kgamount,\r\n"
	  		+ "COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0) - COALESCE(SUM(odsi.qty), 0) AS closingpcsstock,\r\n"
	  		+ "COALESCE(SUM(dosi.kgqty), 0) + COALESCE(SUM(dci.kgqty), 0) - COALESCE(SUM(odsi.kgqty), 0) AS closingkgstock\r\n"
	  		+ "FROM product p\r\n"
	  		+ "  	\r\n"
	  		+ "LEFT JOIN (SELECT dosi.product_id, SUM(dosi.qty) AS qty, SUM(dosi.kgqty) AS kgqty\r\n"
	  		+ "FROM distributor_opening_stock_items dosi\r\n"
	  		+ "JOIN distributor_opening_stock dos ON dosi.distributor_openingstock_id = dos.id\r\n"
	  		+ "WHERE dos.distributor_id = 7 GROUP BY dosi.product_id) dosi ON dosi.product_id = p.id\r\n"
	  		+ "  	\r\n"
	  		+ "LEFT JOIN (SELECT SUM(dci.dlp) AS rate, dci.product_id, SUM(dci.dcquantity_placed) AS qty, SUM(dci.dcquantity_placed_kg) AS kgqty FROM delivery_charge_items dci\r\n"
	  		+ "JOIN delivery_charge dc ON dci.dc_id = dc.id WHERE dc.distributor_id = 7 \r\n"
	  		+ "GROUP BY dci.product_id, dci.rate) dci ON dci.product_id = p.id\r\n"
	  		+ "  	\r\n"
	  		+ "LEFT JOIN (SELECT SUM(odsi.rate) AS rate, odsi.productid, SUM(odsi.outwardqty) AS qty, SUM(odsi.kgoutwardqty) AS kgqty FROM outward_distributor_stock_items odsi\r\n"
	  		+ "JOIN outward_distributor_stock ods ON odsi.outwarddisid = ods.id\r\n"
	  		+ "WHERE ods.distributoroid = 7  GROUP BY odsi.productid) odsi ON odsi.productid = p.id\r\n"
	  		+ "  	\r\n"
	  		+ "LEFT JOIN (SELECT dmsi.product_id, SUM(dmsi.stockqty) AS qty, SUM(dmsi.kgstockqty) AS kgqty FROM distributor_minimum_stock_items dmsi\r\n"
	  		+ "JOIN distributor_minimum_stock dms ON dmsi.dms_id = dms.id\r\n"
	  		+ "WHERE dms.distributorid = 7 GROUP BY dmsi.product_id) dmsi ON dmsi.product_id = p.id\r\n"
	  		+ "  		\r\n"
	  		+ "WHERE dosi.qty IS NOT NULL OR dmsi.qty IS NOT NULL OR odsi.qty IS NOT NULL OR dci.qty IS NOT NULL \r\n"
	  		+ "OR dosi.kgqty IS NOT NULL OR dmsi.kgqty IS NOT NULL OR odsi.kgqty IS NOT NULL OR dci.kgqty IS NOT NULL\r\n"
	  		+ "GROUP BY p.id\r\n"
	  		+ "HAVING \r\n"
	  		+ "	COALESCE(SUM(dmsi.qty), 0) >= COALESCE(SUM(dosi.qty), 0) + COALESCE(SUM(dci.qty), 0) - COALESCE(SUM(odsi.qty), 0)", nativeQuery = true)
  List<DistributorMinimumStockReportIndex> MinimumStockForDistributor100(int distid);
  
}
