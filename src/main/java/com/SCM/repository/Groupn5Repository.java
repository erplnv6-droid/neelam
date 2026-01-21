package com.SCM.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGroupn4;
import com.SCM.IndexDto.IndexGroupn5;
import com.SCM.model.Groupn5;

public interface Groupn5Repository extends JpaRepository<Groupn5, Long>{

	
	@Query(value="select g.id as id,g.title as title from groupn5 g",nativeQuery = true)
	Page<IndexGroupn5> getAllGroupn5(Pageable p);
	
	
	@Query(value=" select g.id as id,g.title as title from groupn5 g where"
			+ " g.id  LIKE CONCAT('%',:search, '%')"
			+ " or g.title  LIKE CONCAT('%',:search, '%')",nativeQuery = true)
	Page<IndexGroupn5> searchGroupn5(Pageable p,String search);
	
	
}
