package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.model.BillOfMaterialItems;

public interface BillOfMaterialItemRepo extends JpaRepository<BillOfMaterialItems, Integer> {

	@Query(value="select * from bill_of_material_items where bill_of_material_id = ?1",nativeQuery = true)
	List<BillOfMaterialItems> getAllBillOfmaterialItems(int id);
}
