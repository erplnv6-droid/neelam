package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.PurchasePageDtoProjection;
import com.SCM.IndexDto.TranporterProjection;
import com.SCM.model.Transporters;

public interface TanspoertRepository extends JpaRepository<Transporters, Long> {

	
	@Query(value = "select t.id,t.transporter_gstin,t.transporter_name from transporters as t" , nativeQuery = true)
    public List<TranporterProjection> indexTransporter(Pageable p);
	
	@Query(value = "select t.id,t.transporter_gstin,t.transporter_name from transporters as t where t.id like CONCAT('%',:search,'%') or t.transporter_gstin Like concat('%',:search,'%') or t.transporter_name Like concat('%',:search,'%')" , nativeQuery = true)
	List<TranporterProjection> searchByTransporters(String search, Pageable p);
}
