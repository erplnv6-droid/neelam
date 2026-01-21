package com.SCM.repository.PagingAndSortingRepo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.SCM.model.MinimumStock;
import com.SCM.projection.MinimumStockReportProjection;

public interface MinimumStockReportRepo extends PagingAndSortingRepository<MinimumStock, Integer> {
	
	@Query(value = "CALL minimumStockReport(:wid)",nativeQuery = true)
	public List<MinimumStockReportProjection> getMinimumStockReport(@Param("wid") Integer wid);
}
