package com.SCM.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGroupn2;
import com.SCM.model.Groupn1;
import com.SCM.model.Groupn2;

public interface Groupn2Repository extends JpaRepository<Groupn2, Long>{

	
	
	@Query(value="select g.id as id,g.title as title from groupn2 g",nativeQuery = true)
	Page<IndexGroupn2> getAllGroupn2(Pageable p);
	
	
	@Query(value=" select g.id as id,g.title as title from groupn2 g where"
			+ " g.id  LIKE CONCAT('%',:search, '%')"
			+ " or g.title  LIKE CONCAT('%',:search, '%')",nativeQuery = true)
	Page<IndexGroupn2> searchGroupn2(Pageable p,String search);
	
	

	
}
