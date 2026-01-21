package com.SCM.repository;

import com.SCM.IndexDto.IndexSupplier;
import com.SCM.model.Supplier;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	
	
	Optional<Supplier> findByEmail(String email);
	boolean existsByEmail(String email);
	
	
	@Query(value = "select s.id,s.suppliername,s.companyname,s.cities,s.gstno,sz.state_name,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createbyname,s.updatedbyname,s.createdtime,DATE_FORMAT(s.updateddate,'%d-%m-%Y') AS updateddate,s.updatedtime from supplier s"
			+ " left join state_zone sz on s.states_id = sz.id",nativeQuery = true)
	List<IndexSupplier> indexSupplier(Pageable p);
	
	
	@Query(value = "select s.id,s.suppliername,s.companyname,s.cities,s.gstno,sz.state_name,DATE_FORMAT(s.createddate,'%d-%m-%Y') AS createddate,s.createbyname,s.updatedbyname,s.createdtime,DATE_FORMAT(s.updateddate,'%d-%m-%Y') AS updateddate,s.updatedtime from supplier s"
			+ "     left join state_zone sz on s.states_id = sz.id"
			+ "     where s.id Like CONCAT('%', :search, '%')"
			+ "     or s.suppliername Like CONCAT('%', :search, '%')"
			+ "     or REGEXP_LIKE(REGEXP_REPLACE(s.companyname,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))" 
			+ "     or s.cities Like CONCAT('%', :search, '%')"
			+ "     or s.createbyname Like CONCAT('%', :search, '%')"
			+ "     or s.updatedbyname Like CONCAT('%', :search, '%')"  
			+ "     or sz.state_name Like CONCAT('%', :search, '%')", nativeQuery = true)
	List<IndexSupplier> SearchBySupplier(String search, Pageable p);
	
	
	@Query(value = "call SupplierGetAll()",nativeQuery = true)
	List<Supplier> findAllSupplier();
}
