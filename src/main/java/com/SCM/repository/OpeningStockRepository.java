package com.SCM.repository;

import com.SCM.IndexDto.IndexOpeningStock;
import com.SCM.IndexDto.IndexOpeningStockEntity;
import com.SCM.dto.OpeningStockResponse;
import com.SCM.model.Distributor;
import com.SCM.model.OpeningStock;
import com.SCM.model.Staff;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpeningStockRepository extends JpaRepository<OpeningStock, Integer> {

	@Query(value = "SELECT new com.SCM.dto.OpeningStockResponse(o.id, o.qty, o.date, p.productName, w.name) FROM OpeningStock o JOIN Product p on p.id = o.pid join Warehouse w on w.id = o.wid")
	List<OpeningStockResponse> findAllOpeningStockWithProdWar();

	@Query(value = "CALL fetchAllProduct()", nativeQuery = true)
	public List<OpeningStock> fetchAllProductandOpeningStock();

	@Query(value = "select * from opening_stock", nativeQuery = true)
	public List<OpeningStock> getAllOsImg();

	Optional<OpeningStock> findAllByWidAndPid(int id, int ids);

	List<OpeningStock> findByProductId(int id);
	
	
	
	@Query(value = "SELECT new com.SCM.IndexDto.IndexOpeningStock(o.id, o.qty, p.productName,p.eanCode) FROM OpeningStock o JOIN Product p on p.id = o.pid")
	List<IndexOpeningStock> indexOpeningStocks(Pageable p);

	
//	@Query(value = "select o.id,o.qty,p.product_name,p.ean_code from opening_stock as o join "
//			+ "product as p"
//			+ " on o.product_id=p.id"
//			+ " where o.qty Like CONCAT('%', :search, '%')"
//			+ " or o.id Like CONCAT('%', :search, '%')"
//			+ " or p.product_name Like CONCAT('%', :search, '%')"  
//			+ " or p.ean_code Like CONCAT('%', :search, '%')", nativeQuery = true)
//	List<OpeningStock> SearchByOpeningStock(String search, Pageable p);
	
	
	@Query(value = "select o.id,o.qty,p.product_name,p.ean_code from opening_stock as o join "
			+ "product as p"
			+ " on o.product_id=p.id"
			+ " where o.qty Like CONCAT('%', :search, '%')"
			+ " or o.id Like CONCAT('%', :search, '%')"
			+ " or p.product_name Like CONCAT('%', :search, '%')"  
			+ " or p.ean_code Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexOpeningStockEntity> SearchByOpeningStock(String search, Pageable p);
	
//	@Query(value = "select o.* "
//			+ "from opening_stock o "
//			+ " where o.id Like CONCAT('%', :search, '%')", nativeQuery = true)
//	List<OpeningStock> SearchByOpeningStock(String search, Pageable p);

}
