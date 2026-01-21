package com.SCM.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.Loc2;

@Repository
public interface LocRepository extends JpaRepository<Loc2, Integer> {

	
	Loc2 findBystaffid(int staffid);
	
	boolean existsBystaffid(int staffid);
	
	@Modifying
	@Transactional
	@Query(value = "delete from loc2 where staffid = ?1",nativeQuery = true)
	void deletebystaffid(int staffid);
}
