package com.SCM.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGroup1;
import com.SCM.model.Group1;

public interface Group1Repository extends JpaRepository<Group1, Long>{

	@Query(value = "select id,title,DATE_FORMAT(createddate,'%d-%m-%Y') as createddate,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime,createbyname,updatedbyname "
			+ " from group1\r\n",nativeQuery = true)
	List<IndexGroup1> indexGroup1();
	
	@Query(value = "select id,title,DATE_FORMAT(createddate,'%d-%m-%Y') as createddate,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime,createbyname,updatedbyname "
			+ " from group1\r\n",nativeQuery = true)
	List<IndexGroup1> indexGroup1(Pageable p);
	
	@Query(value =  "select id,title,DATE_FORMAT(createddate,'%d-%m-%Y') as createddate,createdtime,DATE_FORMAT(updateddate,'%d-%m-%Y') as updateddate,updatedtime,createbyname,updatedbyname "
			+ " from group1\r\n "
			+ " where title Like CONCAT('%', :search, '%')"
			+ "     or createddate Like CONCAT('%', :search, '%')" 
			+ "     or createdtime Like CONCAT('%', :search, '%')" 
			+ "     or updateddate Like CONCAT('%', :search, '%')" 
			+ "     or updatedtime Like CONCAT('%', :search, '%')" 
			+ "     or createbyname Like CONCAT('%', :search, '%')"
			+ "     or updatedbyname Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexGroup1> indexGroup1(String search,Pageable p);
	
	
	@Modifying
	@Transactional
	@Query(value = "	update group1 set title =:title , updatedby =:updatedby , updatedbyname=:updatedbyname,\r\n"
			+ "			updateddate=:updateddate  ,updatedrole=:updatedrole,updatedtime=:updatedtime\r\n"
			+ "			where id =:id ",nativeQuery = true)
	public void update(String title,Long updatedby,String updatedbyname,LocalDate updateddate,
			String updatedrole,LocalTime updatedtime,Long id);
	
	
	

}
