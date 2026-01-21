package com.SCM.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndesSalesReturn;
import com.SCM.IndexDto.IndexTemporaryRetailer;
import com.SCM.model.RetailerTemporary;
import com.SCM.projection.SalesReturnProjection;
import com.SCM.projection.TemporaryRetailerProjection;


@Repository
public interface TempRetailerRepo extends JpaRepository<RetailerTemporary, Integer> {
  
    @Modifying
    @Transactional
    @Query(value = "delete from retailer_temporary where id = ?1",nativeQuery = true)
    public void deleteTempByRetailer(Integer id); 
    
    
	@Query(value = "SELECT re.id,re.trade_name,re.transporter_name,re.delivery_location,re.state,re.gst_number,DATE_FORMAT(re.createddate,'%d-%m-%Y') AS createddate,re.createdtime,re.createbyname,re.updatedbyname,DATE_FORMAT(re.updateddate,'%d-%m-%Y') as updateddate,re.updatedtime from retailer_temporary re",nativeQuery = true)
	List<TemporaryRetailerProjection> indexTemporaryRetailer(Pageable p);
    
    
	@Query(value = "SELECT re.id,re.trade_name,re.transporter_name,re.delivery_location,re.state,re.gst_number,DATE_FORMAT(re.createddate,'%d-%m-%Y') AS createddate,re.createdtime,re.createbyname,re.updatedbyname,DATE_FORMAT(re.updateddate,'%d-%m-%Y') as updateddate,re.updatedtime from retailer_temporary re"
			+ " where re.id Like CONCAT('%',:search, '%')\r\n"
			+ "    or REGEXP_LIKE(REGEXP_REPLACE(re.trade_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "    or REGEXP_LIKE(REGEXP_REPLACE(re.transporter_name,'[^a-zA-Z0-9]','') , REGEXP_REPLACE (CONCAT('%',:search, '%'), '[^a-zA-Z0-9]', ''))"
			+ "	  or re.delivery_location Like CONCAT('%',:search, '%')\r\n"
			+ "	  or re.state Like CONCAT('%',:search, '%')\r\n"
			+ "	  or re.gst_number Like CONCAT('%',:search, '%')", nativeQuery = true)
	List<TemporaryRetailerProjection> SearchByTemporaryRetailer(String search, Pageable p);
    
}
