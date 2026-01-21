package com.SCM.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexGalaPrefix;

import com.SCM.IndexDto.IndexGroup10;

import com.SCM.model.GalaPrefix;
import com.SCM.model.Staff;

public interface GalaPrefixRepo extends JpaRepository<GalaPrefix, Long>{

    Optional<GalaPrefix> findByStaff(Staff staff);

	@Query(value = "select g.id,s.staff_name,g.gname,g.staffid,DATE_FORMAT(g.createddate,'%d-%m-%Y') as createddate,\r\n"
			+ "g.createdtime,\r\n"
			+ "DATE_FORMAT(g.updateddate,'%d-%m-%Y') as updateddate,\r\n"
			+ "g.updatedtime,\r\n"
			+ "g.createbyname,\r\n"
			+ "g.updatedbyname\r\n"
			+ "from galaprefix g left join \r\n"
			+ "staff s on g.staffid=s.id",nativeQuery = true)
	List<IndexGalaPrefix> indexGala();


	@Query(value = "select g.id,s.staff_name,g.gname,g.staffid,DATE_FORMAT(g.createddate,'%d-%m-%Y') as createddate,\r\n"
			+ "g.createdtime,\r\n"
			+ "DATE_FORMAT(g.updateddate,'%d-%m-%Y') as updateddate,\r\n"
			+ "g.updatedtime,\r\n"
			+ "g.createbyname,\r\n"
			+ "g.updatedbyname\r\n"
			+ "from galaprefix g left join \r\n"
			+ "staff s on g.staffid=s.id",nativeQuery = true)
	List<IndexGalaPrefix> indexGala(Pageable p);


	@Query(value =  "select g.id,s.staff_name,g.gname,g.staffid,DATE_FORMAT(g.createddate,'%d-%m-%Y') as createddate,\r\n"
			+ " g.createdtime,\r\n"
			+ " DATE_FORMAT(g.updateddate,'%d-%m-%Y') as updateddate,\r\n"
			+ " g.updatedtime,\r\n"
			+ " g.createbyname,\r\n"
			+ " g.updatedbyname\r\n"
			+ "from galaprefix g left join \r\n"
			+ "staff s on g.staffid=s.id "
			+ " where s.staff_name Like CONCAT('%', :search, '%')"
			+ "     or g.gname Like CONCAT('%', :search, '%')" 
			+ "     or g.id Like CONCAT('%', :search, '%')" 
			+ "     or g.createddate Like CONCAT('%', :search, '%')" 
			+ "     or g.createdtime Like CONCAT('%', :search, '%')" 
			+ "     or g.updateddate Like CONCAT('%', :search, '%')" 
			+ "     or g.updatedtime Like CONCAT('%', :search, '%')" 
			+ "     or g.createbyname Like CONCAT('%', :search, '%')"
			+ "     or g.updatedbyname Like CONCAT('%', :search, '%')"
			,nativeQuery = true)
	List<IndexGalaPrefix> indexGala(String search,Pageable p);
	
}
