package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.ProductBarcodeActivityLog;

@Repository
public interface ProductBarcodeActivityLogRepo extends JpaRepository<ProductBarcodeActivityLog, Integer> {
	
}
