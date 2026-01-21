package com.SCM.repository;

import com.SCM.model.Zone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepo extends JpaRepository<Zone,Integer> {
	
	
	@Query(value = "SELECT z FROM Zone z JOIN z.staff s WHERE s.id = ?1")
    public Zone getZoneBystaffId(Long id);
	
	@Query(value = "select * from zone" , nativeQuery = true)
	public List<Zone> getAll();
	
}
