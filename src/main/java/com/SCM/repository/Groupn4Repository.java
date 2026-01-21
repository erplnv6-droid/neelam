package com.SCM.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGroupn4;
import com.SCM.model.Groupn4;

public interface Groupn4Repository extends JpaRepository<Groupn4, Long>{

	
	@Query(value="select g.id as id,g.title as title from groupn4 g",nativeQuery = true)
	Page<IndexGroupn4> getAllGroupn4(Pageable p);
	
	
	@Query(value=" select g.id as id,g.title as title from groupn4 g where"
			+ " g.id  LIKE CONCAT('%',:search, '%')"
			+ " or g.title  LIKE CONCAT('%',:search, '%')",nativeQuery = true)
	Page<IndexGroupn4> searchGroupn4(Pageable p,String search);
	
	
}
