package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGroup7;
import com.SCM.IndexDto.IndexGroup8;
import com.SCM.model.Group7;
import com.SCM.model.Group8;

public interface Group8Repository extends JpaRepository<Group8, Long>{

	
	
	@Query(value = "select id as id,title,DATE_FORMAT(createddate,'%d-%m-%Y') as createddate,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime,createbyname,updatedbyname "
			+ " from group8\r\n",nativeQuery = true)
	List<IndexGroup8> indexGroup8();
	
	@Query(value = "select id as id,title,DATE_FORMAT(createddate,'%d-%m-%Y') as createddate,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime,createbyname,updatedbyname "
			+ " from group8\r\n",nativeQuery = true)
	List<IndexGroup8> indexGroup8(Pageable p);
	
	@Query(value =  "select id as id,title,DATE_FORMAT(createddate,'%d-%m-%Y') as createddate,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime,createbyname,updatedbyname "
			+ " from group8\r\n "
			+ " where title Like CONCAT('%', :search, '%')"
			+ "     or createddate Like CONCAT('%', :search, '%')" 
			+ "     or createdtime Like CONCAT('%', :search, '%')" 
			+ "     or updateddate Like CONCAT('%', :search, '%')" 
			+ "     or updatedtime Like CONCAT('%', :search, '%')" 
			+ "     or createbyname Like CONCAT('%', :search, '%')"
			+ "     or updatedbyname Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexGroup8> indexGroup8(String search,Pageable p);
}
