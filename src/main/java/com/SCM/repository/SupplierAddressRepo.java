package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.model.SupplierAddress;

@Repository
public interface SupplierAddressRepo extends JpaRepository<SupplierAddress, Integer> {

	
	@Query(value = "select * from supplier_address where supplier_id=? " , nativeQuery = true)
	public List<SupplierAddress> findAllAddressBySupplierId(long id);
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from supplier_address where supplier_id is null" , nativeQuery = true)
	public void deleteBySupplierId(long id);
}
