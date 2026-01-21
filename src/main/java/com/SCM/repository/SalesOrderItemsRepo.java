package com.SCM.repository;  


import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.SalesOrderItems;

@Repository
public interface SalesOrderItemsRepo extends JpaRepository<SalesOrderItems, Integer> {
	
	
	@Modifying
	@Transactional
	@Query(value = "update sales_order_items soi set soi.dcitemspending = ?1, soi.dcitemsplaced= ?2, soi.dcstatus = ?3 where soi.salesorderitem_id = ?4",nativeQuery = true)
	void updatedcstatus(int dcitemspending, int dcitemsplaced, String dcstatus,int soitemsid);
		
	
	public List<SalesOrderItems> findBySalesorderId(int soid);

	
	List<SalesOrderItems> findBySalesorderitemId(int salesorderId);
	
	
	@Query(value = "select * from sales_order_items where salesorderitem_id = ?1",nativeQuery = true)
	SalesOrderItems getSoItemsbyId(int salesorderiemsid);
	
	
	// -------- get so items by dist id

	@Query(value = " SELECT soi.* FROM sales_order_items soi"
			+ "  left JOIN sales_order so ON soi.salesorderid = so.id "
			+ "  left JOIN  product  p on soi.product_id = p.id "
			+ "  WHERE so.distributor_id = ?1", nativeQuery = true)
	public List<SalesOrderItems> fetchsalesOrderbyDistributorid(int distid);

	// ---------- get so items by retailer id
	
	@Query(value = " SELECT soi.* FROM sales_order_items soi\r\n"
			+ "left JOIN sales_order so ON soi.salesorderid = so.id\r\n"
			+ "left JOIN  product  p on soi.product_id = p.id\r\n"
			+ "WHERE so.retailer_id = ?1", nativeQuery = true)
	public List<SalesOrderItems> fetchsalesOrderbyRetailerid(int retid);
	
	
	// -------- get so items by soitems id
	
	@Query(value = " SELECT soi.* FROM sales_order_items soi left JOIN sales_order so ON soi.salesorderid = so.id JOIN  product  p on soi.product_id = p.id WHERE soi.salesorderitem_id = ?1", nativeQuery = true)
	public List<SalesOrderItems> getSalesOrderItemsBySoitemsId(int soitemsid);
	
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE sales_order_items soi SET soi.cancelpcsqty = :cancelqty WHERE soi.salesorderid = :soid",nativeQuery = true)
	void updateSalesOrderItemsBySalesOrder(int cancelqty,int soid);
	
	
}
