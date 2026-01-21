package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.model.RetailerAddress;

@Repository
public interface RetailerAddressRepository extends JpaRepository<RetailerAddress, Long>{

	
	@Query(value = "select * from retailer_address where retailer_id=? " , nativeQuery = true)
	public List<RetailerAddress> findAllAddressByRetailerId(Long id);
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from retailer_address where retailer_id is null" , nativeQuery = true)
	public void deleteByRetailerId();
}
