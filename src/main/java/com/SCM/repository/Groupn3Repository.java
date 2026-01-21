package com.SCM.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.IndexDto.IndexGroupn3;
import com.SCM.model.Groupn3;

public interface Groupn3Repository extends JpaRepository<Groupn3, Long>{

	@Query(value="select g.id as id,g.title as title from groupn3 g",nativeQuery = true)
	Page<IndexGroupn3> getAllGroupn3(Pageable p);
	
	
	@Query(value=" select g.id as id,g.title as title from groupn3 g where"
			+ " g.id  LIKE CONCAT('%',:search, '%')"
			+ " or g.title  LIKE CONCAT('%',:search, '%')",nativeQuery = true)
	Page<IndexGroupn3> searchGroupn3(Pageable p,String search);
	
	
}
