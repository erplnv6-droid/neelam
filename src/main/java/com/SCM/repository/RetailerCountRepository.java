package com.SCM.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.SCM.model.Retailer;
import com.SCM.projection.RetailerCountProjection;


public interface RetailerCountRepository extends JpaRepository<Retailer, Integer> {

	
	@Query(value="select count(case when retailer.authorized='0' THEN 1 ELSE NULL END) as total_inactive_retailer,\r\n"
			+ "count(case when retailer.authorized='1' THEN 1 ELSE NULL END) as total_active_retailer,\r\n"
			+ "count(*) as total_retailer\r\n"
		    + "from retailer",nativeQuery = true)
	List<RetailerCountProjection> getCountReatiler();
	
	
	
	
	@Query(value="select count(case when retailer.authorized='0' THEN 1 ELSE NULL END) as total_inactive_retailer,\r\n"
			+ "count(case when retailer.authorized='1' THEN 1 ELSE NULL END) as total_active_retailer,\r\n"
			+ "count(*) as total_retailer\r\n"		
            + "from retailer where retailer.distributor_id=?",nativeQuery = true)
	List<RetailerCountProjection> getCountReatilerByDistributorId(@Param ("distributor_id") int distributor_id);
	

	
	
	
	@Query(value="select count(case when retailer.authorized='0' THEN 1 ELSE NULL END) as total_inactive_retailer,\r\n"
			+ "count(case when retailer.authorized='1' THEN 1 ELSE NULL END) as total_active_retailer,\r\n"
			+ "count(*) as total_retailer\r\n"
		
		    + "from retailer left join staff on staff.id=retailer.nsmid or staff.id=retailer.rsmid\r\n"
		    + "or staff.id=retailer.asmid or staff.id=retailer.aseid where staff.id=?",nativeQuery = true)
	List<RetailerCountProjection> getCountReatilerByStaff(@Param ("id") int id);
	


}
