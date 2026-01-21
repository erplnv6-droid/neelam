package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.WarehouseExcelUpload;

@Repository
public interface WarehouseExcelUploadRepo extends JpaRepository<WarehouseExcelUpload, Integer> {

	
}
