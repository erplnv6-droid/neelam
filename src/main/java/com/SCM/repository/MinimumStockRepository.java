package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.SCM.IndexDto.IndexDistributorOpeningStock;
import com.SCM.IndexDto.IndexDistributor;
import com.SCM.IndexDto.IndexMinimumStock;
import com.SCM.dto.MinimumStockReportDto;
import com.SCM.dtoReports.MinimumStockReport;
import com.SCM.model.MinimumStock;
import com.SCM.projection.MinimumStockReportProjection;

@Repository
public interface MinimumStockRepository extends JpaRepository<MinimumStock, Integer> {

	
//	@Query("select new com.SCM.dtoReports.MinimumStockReport(prd.productName,prd.mrp,ms.stockqty,"
//			+ " ((pi.puquantity + sri.srquantity) - (dci.dcquantity + pri.purquantity)) + os.qty As closingstock,"
//			+ "  CASE WHEN ms.stockqty < ((pi.puquantity + sri.srquantity) - (dci.dcquantity + pri.purquantity)) + os.qty THEN 'max' ELSE 'stock is minimum create po' END As stockstatus)"
//			+ "  FROM Product prd"
//			+ "  JOIN OpeningStock os ON prd.id = os.product.id"
//			+ "  JOIN DeliveryChargeItems dci ON prd.id = dci.product.id"
//			+ "  JOIN PurchaseItems pi ON prd.id = pi.product.id"
//			+ "  JOIN SalesReturnItems sri ON prd.id = pi.product.id"
//			+ "  JOIN PurchaseReturnItems pri ON prd.id = pi.product.id"
//			+ "  JOIN MinimumStock ms on prd.id = ms.product.id"
//			+ "  WHERE ms.wid = ?1")
//	List<MinimumStockReport> minimumstockreports();
	
	
	
//	@Query("SELECT "
//			+ "  prd.product_name,prd.mrp,ms.stockqty,"
//			+ "  (pi.puquantity + sri.srquantity) - (dci.dcquantity + pri.purquantity) + os.qty AS closingstock,\r\n"
//			+ "  IF( ms.stockqty > (pi.puquantity + sri.srquantity) - (dci.dcquantity + pri.purquantity), 'stock is minimum','max') AS stockstatus\r\n"
//			+ "  FROM product prd"
//			+ "  LEFT JOIN  minimum_stock ms ON prd.id = ms.product_id"
//			+ "  LEFT JOIN  opening_stock os ON prd.id = os.product_id"
//			+ "  LEFT JOIN  purchase_items pi ON prd.id = pi.product_id\r\n"
//			+ "  LEFT JOIN  sales_return_items sri ON prd.id = sri.product_id\r\n"
//			+ "  LEFT JOIN  purchase_return_items pri ON prd.id = pri.product_id\r\n"
//			+ "  LEFT JOIN  delivery_charge_items dci ON prd.id = dci.product_id\r\n"
//			+ "  WHERE ms.stockqty > (pi.puquantity + sri.srquantity) - (dci.dcquantity + pri.purquantity)\r\n"
//			+ "  And ms.wid = 1")
//	
//	@Query(value = "CALL minimumStockReport(:wid,:p)",nativeQuery = true)
//	public List<MinimumStockReportProjection> getMinimumStockReport(@Param("wid") Integer wid, Pageable p);
	
	
	@Query(value = "SELECT \r\n"
			+ "	p.id AS product_id,\r\n"
			+ "    p.product_name AS product_name,\r\n"
			+ "    os.date,\r\n"
			+ "    SUM(os.qty) AS totalosqty,\r\n"
			+ "    COALESCE(ms.stockqty, 0) as msqty,\r\n"
			+ "    COALESCE(SUM(mrni.qty), 0) AS totalmrniqty,\r\n"
			+ "    COALESCE(SUM(sri.qty), 0) AS totalsriqty,\r\n"
			+ "    COALESCE(SUM(dci.qty), 0) AS totaldciqty,\r\n"
			+ "    COALESCE(SUM(pri.qty), 0) AS totalpriqty,\r\n"
			+ "    COALESCE(SUM(os.qty),0) + COALESCE(SUM(mrni.qty),0) + COALESCE(SUM(sri.qty), 0) AS inwardqty,\r\n"
			+ "    COALESCE(SUM(dci.qty),0) + COALESCE(SUM(pri.qty), 0) AS outwardqty,\r\n"
			+ "    COALESCE(SUM(os.qty),0) + COALESCE(SUM(mrni.qty),0) + COALESCE(SUM(sri.qty), 0) - (COALESCE(SUM(dci.qty),0) + COALESCE(SUM(pri.qty), 0)) as closingstock\r\n"
			+ "FROM \r\n"
			+ "	product p \r\n"
			+ "LEFT JOIN opening_stock os ON p.id = os.product_id\r\n"
			+ "LEFT JOIN minimum_stock ms ON ms.product_id = p.id\r\n"
			+ "LEFT JOIN \r\n"
			+ "	(SELECT \r\n"
			+ "		mrni.product_id,\r\n"
			+ "        SUM(mrni.mrnquantity) AS qty \r\n"
			+ "    FROM \r\n"
			+ "		material_receipt_note_items mrni\r\n"
			+ "    JOIN \r\n"
			+ "		material_recepit_note mrn ON mrni.mrn_id = mrn.id \r\n"
			+ "    WHERE \r\n"
			+ "		mrn.warehouse_id = ?1\r\n"
			+ "    GROUP BY \r\n"
			+ "		mrni.product_id\r\n"
			+ "	) mrni ON mrni.product_id = p.id\r\n"
			+ "LEFT JOIN\r\n"
			+ "	(SELECT \r\n"
			+ "		sri.product_id,\r\n"
			+ "		SUM(sri.srquantity) AS qty\r\n"
			+ "	FROM \r\n"
			+ "		sales_return_items sri\r\n"
			+ "    JOIN \r\n"
			+ "		sales_return sr ON sr.id = sri.sales_returnid\r\n"
			+ "	WHERE \r\n"
			+ "		sr.warehouse_id = ?1\r\n"
			+ "	GROUP BY \r\n"
			+ "		sri.product_id\r\n"
			+ "    ) sri ON sri.product_id = p.id\r\n"
			+ "LEFT JOIN \r\n"
			+ "	(SELECT \r\n"
			+ "		dci.product_id , \r\n"
			+ "        SUM(dci.dcquantity_placed) AS qty\r\n"
			+ "    FROM \r\n"
			+ "		delivery_charge_items dci\r\n"
			+ "	JOIN \r\n"
			+ "		delivery_charge dc ON dc.id = dci.dc_id\r\n"
			+ "	WHERE \r\n"
			+ "		dc.warehouse_id = ?1\r\n"
			+ "    GROUP BY\r\n"
			+ "		dci.product_id\r\n"
			+ "	) dci on dci.product_id = p.id\r\n"
			+ "    LEFT JOIN \r\n"
			+ "	(SELECT \r\n"
			+ "		pri.product_id , \r\n"
			+ "        SUM(pri.purquantity) AS qty\r\n"
			+ "    FROM \r\n"
			+ "		purchase_return_items pri\r\n"
			+ "	JOIN \r\n"
			+ "		purchase_return pr ON pr.id = pri.purchasereturn_id\r\n"
			+ "	WHERE \r\n"
			+ "		pr.warehouse_id = ?1\r\n"
			+ "    GROUP BY\r\n"
			+ "		pri.product_id\r\n"
			+ "	) pri on pri.product_id = p.id\r\n"
			+ "    \r\n"
			+ "WHERE \r\n"
			+ "	os.wid = ?1\r\n"
			+ "GROUP BY \r\n"
			+ "	p.id, p.product_name, ms.stockqty, os.date\r\n"
			+ "HAVING \r\n"
			+ "    COALESCE(SUM(os.qty),0) + COALESCE(SUM(mrni.qty),0) + COALESCE(SUM(sri.qty), 0) - (COALESCE(SUM(dci.qty),0) + COALESCE(SUM(pri.qty), 0)) <= COALESCE(ms.stockqty, 0)",nativeQuery = true)
	public List<MinimumStockReportProjection> getMinimumStockReport(@Param("wid") Integer wid, Pageable p);
	
	
	
	@Query(value = "SELECT \r\n"
			+ "	p.id AS product_id,\r\n"
			+ "    p.product_name AS product_name,\r\n"
			+ "    os.date,\r\n"
			+ "    SUM(os.qty) AS totalosqty,\r\n"
			+ "    COALESCE(ms.stockqty, 0) as msqty,\r\n"
			+ "    COALESCE(SUM(mrni.qty), 0) AS totalmrniqty,\r\n"
			+ "    COALESCE(SUM(sri.qty), 0) AS totalsriqty,\r\n"
			+ "    COALESCE(SUM(dci.qty), 0) AS totaldciqty,\r\n"
			+ "    COALESCE(SUM(pri.qty), 0) AS totalpriqty,\r\n"
			+ "    COALESCE(SUM(os.qty),0) + COALESCE(SUM(mrni.qty),0) + COALESCE(SUM(sri.qty), 0) AS inwardqty,\r\n"
			+ "    COALESCE(SUM(dci.qty),0) + COALESCE(SUM(pri.qty), 0) AS outwardqty,\r\n"
			+ "    COALESCE(SUM(os.qty),0) + COALESCE(SUM(mrni.qty),0) + COALESCE(SUM(sri.qty), 0) - (COALESCE(SUM(dci.qty),0) + COALESCE(SUM(pri.qty), 0)) as closingstock\r\n"
			+ "FROM \r\n"
			+ "	product p \r\n"
			+ "LEFT JOIN opening_stock os ON p.id = os.product_id\r\n"
			+ "LEFT JOIN minimum_stock ms ON ms.product_id = p.id\r\n"
			+ "LEFT JOIN \r\n"
			+ "	(SELECT \r\n"
			+ "		mrni.product_id,\r\n"
			+ "        SUM(mrni.mrnquantity) AS qty \r\n"
			+ "    FROM \r\n"
			+ "		material_receipt_note_items mrni\r\n"
			+ "    JOIN \r\n"
			+ "		material_recepit_note mrn ON mrni.mrn_id = mrn.id \r\n"
			+ "    WHERE \r\n"
			+ "		mrn.warehouse_id = ?1\r\n"
			+ "    GROUP BY \r\n"
			+ "		mrni.product_id\r\n"
			+ "	) mrni ON mrni.product_id = p.id\r\n"
			+ "LEFT JOIN\r\n"
			+ "	(SELECT \r\n"
			+ "		sri.product_id,\r\n"
			+ "		SUM(sri.srquantity) AS qty\r\n"
			+ "	FROM \r\n"
			+ "		sales_return_items sri\r\n"
			+ "    JOIN \r\n"
			+ "		sales_return sr ON sr.id = sri.sales_returnid\r\n"
			+ "	WHERE \r\n"
			+ "		sr.warehouse_id = ?1\r\n"
			+ "	GROUP BY \r\n"
			+ "		sri.product_id\r\n"
			+ "    ) sri ON sri.product_id = p.id\r\n"
			+ "LEFT JOIN \r\n"
			+ "	(SELECT \r\n"
			+ "		dci.product_id , \r\n"
			+ "        SUM(dci.dcquantity_placed) AS qty\r\n"
			+ "    FROM \r\n"
			+ "		delivery_charge_items dci\r\n"
			+ "	JOIN \r\n"
			+ "		delivery_charge dc ON dc.id = dci.dc_id\r\n"
			+ "	WHERE \r\n"
			+ "		dc.warehouse_id = ?1\r\n"
			+ "    GROUP BY\r\n"
			+ "		dci.product_id\r\n"
			+ "	) dci on dci.product_id = p.id\r\n"
			+ "    LEFT JOIN \r\n"
			+ "	(SELECT \r\n"
			+ "		pri.product_id , \r\n"
			+ "        SUM(pri.purquantity) AS qty\r\n"
			+ "    FROM \r\n"
			+ "		purchase_return_items pri\r\n"
			+ "	JOIN \r\n"
			+ "		purchase_return pr ON pr.id = pri.purchasereturn_id\r\n"
			+ "	WHERE \r\n"
			+ "		pr.warehouse_id = ?1\r\n"
			+ "    GROUP BY\r\n"
			+ "		pri.product_id\r\n"
			+ "	) pri on pri.product_id = p.id\r\n"
			+ "    \r\n"
			+ "WHERE \r\n"
			+ "	os.wid = ?1\r\n"
			+ "GROUP BY \r\n"
			+ "	p.id, p.product_name, ms.stockqty, os.date\r\n"
			+ "HAVING \r\n"
			+ "    COALESCE(SUM(os.qty),0) + COALESCE(SUM(mrni.qty),0) + COALESCE(SUM(sri.qty), 0) - (COALESCE(SUM(dci.qty),0) + COALESCE(SUM(pri.qty), 0)) <= COALESCE(ms.stockqty, 0)\r\n"
			+ "",nativeQuery = true)
	public List<MinimumStockReportProjection> getMinimumStockReportForCount(@Param("wid") Integer wid);
	
	
	@Query(value = "select ms.id as msid,DATE_FORMAT(ms.stockdate,'%d-%m-%Y') AS stockdate,ms.stockqty,ms.wid,p.id,p.product_name,p.ean_code,p.hsn_code,p.product_type,DATE_FORMAT(ms.createddate,'%d-%m-%Y') AS createddate,ms.createbyname,ms.updatedbyname,p.createdtime p.short_name,DATE_FORMAT(ms.updateddate,'%d-%m-%Y') as updateddate,ms.updatedtime"
			+ " from minimum_stock as ms inner join product as p\r\n"
			+ " on ms.product_id=p.id",nativeQuery = true)
	List<IndexMinimumStock> indexMinimumStocks(Pageable p);
	
	
	@Query(value ="select ms.id as msid,ms.stockdate,ms.stockqty,ms.wid,p.id,p.product_name,p.ean_code,p.hsn_code,p.product_type,DATE_FORMAT(ms.createddate,'%d-%m-%Y') AS createddate,ms.createbyname,ms.updatedbyname,p.createdtime p.short_name,DATE_FORMAT(ms.updateddate,'%d-%m-%Y') as updateddate,ms.updatedtime"
			+ " from minimum_stock as ms inner join product as p\r\n"
			+ "  on ms.product_id=p.id "
			+ " where ms.id Like CONCAT('%', :search, '%')"
			+ " or ms.stockdate Like CONCAT('%', :search, '%')"
			+ " or ms.stockqty Like CONCAT('%', :search, '%')"  
			+ " or ms.wid Like CONCAT('%', :search, '%')"
			+ " or p.id Like CONCAT('%', :search, '%')"
			+ " or p.product_name Like CONCAT('%', :search, '%')"
			+ " or p.ean_code Like CONCAT('%', :search, '%')"
			+ " or p.hsn_code Like CONCAT('%', :search, '%')"
			+ " or p.product_type Like CONCAT('%', :search, '%')"
			+ " or p.short_name Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexMinimumStock> searchByIndexMinimumStock(String search,Pageable p);



	@Query(value = "select ms.id as msid,DATE_FORMAT(ms.stockdate,'%d-%m-%Y') AS stockdate,ms.stockqty,ms.wid,p.id,p.product_name,p.ean_code,p.hsn_code,p.product_type,DATE_FORMAT(ms.createddate,'%d-%m-%Y') AS createddate,ms.createbyname,ms.updatedbyname,p.createdtime,p.short_name,DATE_FORMAT(ms.updateddate,'%d-%m-%Y') as updateddate,ms.updatedtime"
			+ "  from minimum_stock ms \r\n"
			+ "  left join warehouse w on ms.wid = w.id\r\n"
			+ "  left join product  p on ms.product_id = p.id",nativeQuery = true)
	public List<IndexMinimumStock> indexMinimumStock(Pageable p);
	
	
	@Query(value = "select ms.id as msid,DATE_FORMAT(ms.stockdate,'%d-%m-%Y') AS stockdate,ms.stockqty,ms.wid,p.id,p.product_name,p.ean_code,p.hsn_code,p.product_type,DATE_FORMAT(ms.createddate,'%d-%m-%Y') AS createddate,ms.createbyname,ms.updatedbyname,p.createdtime,p.short_name,DATE_FORMAT(ms.updateddate,'%d-%m-%Y') as updateddate,ms.updatedtime"
			+ "   left join warehouse w on ms.wid = w.id\r\n"
			+ "   left join product  p on ms.product_id = p.id"
			+ "   where ms.id Like CONCAT('%', :search, '%')"
			+ "   or ms.stockqty Like CONCAT('%', :search, '%')"
			+ "   or ms.stockdate Like CONCAT('%', :search, '%')"  
			+ "   or w.name Like CONCAT('%', :search, '%')"
			+ "   or p.product_name Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexMinimumStock> SearchByMinimumStock(String search, Pageable p);
	
}
