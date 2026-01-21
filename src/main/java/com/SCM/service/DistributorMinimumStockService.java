package com.SCM.service;

import com.SCM.IndexDto.DistributorMinimumStockReportIndex;
import com.SCM.dto.DistributorMinimumStockDto;
import com.SCM.model.DistributorMinimumStock;
import java.util.List;
import java.util.Map;

public interface DistributorMinimumStockService {
  DistributorMinimumStock saveDistributorMinimumStock(DistributorMinimumStockDto paramDistributorMinimumStockDto);
  
  List<DistributorMinimumStock> getAllDistributorMinimumStock();
  
  DistributorMinimumStock getDistributorMinimumStockById(int paramInt);
  
  String deleteDistributorMinimumStock(int paramInt);
  
  DistributorMinimumStock updateDistributorMinimumStock(DistributorMinimumStockDto paramDistributorMinimumStockDto, int paramInt);
  
  Map<String, Object> IndexDMSAsc(int paramInt1, int paramInt2, String paramString);
  
  Map<String, Object> IndexDMSDesc(int paramInt1, int paramInt2, String paramString);
  
  Map<String, Object> SearchDMS(int paramInt1, int paramInt2, String paramString);
  
  List<DistributorMinimumStockReportIndex> minimumstockfordistributor(int paramInt);
  
  Map<String, Object> IndexDMSReportAsc(int pageno, int pagesize, String field, int distid);
  
  Map<String, Object> IndexDMSReportDesc(int pagno, int pagesize, String field, int distid);
}
