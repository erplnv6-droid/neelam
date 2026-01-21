package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.Cities;

@Repository
public interface CitiesRepo extends JpaRepository<Cities, Integer> {

	
	@Query(value = "select * from cities where state_id = :stateId",nativeQuery = true)
	List<Cities> getCityFromState(int stateId);
}
