package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.model.DistributorAddress;

@Repository
public interface DistributorAddressRepository extends JpaRepository<DistributorAddress, Long>{

	@Query(value = "select * from distributor_address where distributor_id=?" , nativeQuery = true)
	public List<DistributorAddress> findDistributorAddressByDistributor(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "delete from distributor_address where distributor_id is null" , nativeQuery = true)
	public void deleteBydistributorId();
	
	
	
}
