package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.SCM.model.Product;
import com.SCM.model.ProductImage;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {
	
	Optional<ProductImage> findByName(String filename);
	
	Optional<ProductImage> findByLocation(String location);

 	ProductImage findByProductId(int id);
 	
 	@Query(value = "delete from product_image pi where pi.id = ?1",nativeQuery = true)
 	void deleteById(int id);
 	
 	@Query(value = "select * from product_image pi where product_id = ?1",nativeQuery = true)
 	List<ProductImage> findByProductId1(int id);

	Optional<Product> findByProduct(Product  product_id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from product_image pi where pi.product_id = ?1",nativeQuery = true)
	void deleteAllByProductId(int id); 

}
