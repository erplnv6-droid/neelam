package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.model.RetailerTemporaryAddress;

@Repository
public interface RetailerTemporaryAddressRepository extends JpaRepository<RetailerTemporaryAddress, Long>{
	
	@Query(value = "select * from retailer_temp_address where temp_retailer_id=?" , nativeQuery = true)
	public List<RetailerTemporaryAddress> findRetailerTempAddByRTId(Long id);
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from retailer_temp_address where temp_retailer_id is null" , nativeQuery = true)
	public void deleteByTempRetailerId();
	
	
	
	@Transactional
	@Modifying
	@Query(value = "delete from retailer_temp_address where temp_retailer_id = ?1" , nativeQuery = true)
	public void deleteTempAddressBytempId(int tempretid);

}
