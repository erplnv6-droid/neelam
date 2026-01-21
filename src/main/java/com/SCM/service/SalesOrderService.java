package com.SCM.service;

import com.SCM.IndexDto.IndexRendingSalesOrderByDistributor;
import com.SCM.IndexDto.IndexRendingSalesOrderByRetailer;
import com.SCM.dto.SalesOrderCancelRequest;
import com.SCM.dto.SalesOrderDto;
import com.SCM.dtoReports.PendingSalesOrderByRetailerWithoutDistributor;
import com.SCM.dtoReports.SalesOrderReportIndex;
import com.SCM.model.SalesOrder;
import com.SCM.model.SalesOrderItems;
import com.SCM.projectionDto.SalesOrderReportDto;
import java.util.List;
import java.util.Map;

public interface SalesOrderService {
  SalesOrder saveSalesOrder(SalesOrderDto paramSalesOrderDto);
  
  SalesOrder convertPrimaryOrdertoSalesOrder(SalesOrderDto paramSalesOrderDto, int paramInt);
  
  SalesOrder convertWorkordertoSalesorder(SalesOrderDto paramSalesOrderDto, int paramInt);
  
  List<SalesOrder> getAllSalesOrder();
  
  SalesOrder getSalesOrderById(int paramInt);
  
  void deleteSalesOrder(int paramInt);
  
  SalesOrderDto updateSalesOrder(SalesOrderDto paramSalesOrderDto, int paramInt);
  
  SalesOrder updateSalesOrder1(SalesOrderDto paramSalesOrderDto, int paramInt);
  
  Map<String, Object> indexSalesOrderAsc(int paramInt1, int paramInt2, String paramString);
  
  Map<String, Object> indexSalesOrderDesc(int paramInt1, int paramInt2, String paramString);
  
  Map<String, Object> searchBySalesOrder(int paramInt1, int paramInt2, String paramString);
  
  List<SalesOrderItems> fetchsalesitemsbydistandproduct(int paramInt);
  
  List<SalesOrderItems> fetchsalesitemsbyretandproduct(int paramInt);
  
  List<SalesOrderItems> getSalesorderitemsoitemsID(int paramInt);
  
  List<SalesOrderReportIndex> fetchSalesOrderByDistId(long paramLong, String paramString1, String paramString2);
  
  List<SalesOrderReportIndex> fetchSalesOrderByRetailerId(long paramLong, String paramString1, String paramString2);
  
  List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailerwithoutdistributor(int paramInt, String paramString1, String paramString2);
  
  List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailercustomer(int paramInt, String paramString1, String paramString2);
  
  List<IndexRendingSalesOrderByRetailer> Pendingsalesorderbyretailerid(int paramInt, String paramString1, String paramString2);
  
  String CancelItems(SalesOrderCancelRequest paramSalesOrderCancelRequest, int paramInt);
  
  List<IndexRendingSalesOrderByRetailer> getSalesOrderItemsById(int paramInt);
  
  List<IndexRendingSalesOrderByDistributor> getSalesOrderItemsById1(int paramInt);
  
  List<IndexRendingSalesOrderByDistributor> Pendingsalesorderbydistributorid(int paramInt, String paramString1, String paramString2);
  
  String CancelItemsbydistributor(SalesOrderCancelRequest paramSalesOrderCancelRequest, int paramInt);
  
  Map<String, Object> indexSalesOrderByRetailerGroupAsc(int paramInt1, int paramInt2, String paramString, Long paramLong);
  
  Map<String, Object> indexSalesOrderByRetailerGroupDesc(int paramInt1, int paramInt2, String paramString, Long paramLong);
  
  Map<String, Object> searchBySalesOrderByRetailerGroup(int paramInt1, int paramInt2, String paramString, Long paramLong);
  
  Map<String, Object> indexSalesOrderByDistributorGroupAsc(int paramInt1, int paramInt2, String paramString, Long paramLong);
  
  Map<String, Object> indexSalesOrderByDistributorGroupDesc(int paramInt1, int paramInt2, String paramString, Long paramLong);
  
  Map<String, Object> searchBySalesOrderByDistributorGroup(int paramInt1, int paramInt2, String paramString, Long paramLong);
  
  Map<String, Object> indexSalesOrderByRetailerAndDistributorGroupAsc(int paramInt1, int paramInt2, String paramString, Long paramLong1, Long paramLong2);
  
  Map<String, Object> indexSalesOrderByRetailerAndDistributorGroupDesc(int paramInt1, int paramInt2, String paramString, Long paramLong1, Long paramLong2);
  
  Map<String, Object> indexSalesOrderByRetailerlistAndDistributorlistGroupAsc(int paramInt1, int paramInt2, String paramString, List<Long> paramList1, List<Long> paramList2);
  
  Map<String, Object> indexSalesOrderByRetailerlistAndDistributorlistGroupDesc(int paramInt1, int paramInt2, String paramString, List<Long> paramList1, List<Long> paramList2);
  
  Map<String, Object> searchSalesOrderByRetailerlistAndDistributorlistGroupDesc(int paramInt1, int paramInt2, String paramString1, List<Long> paramList1, List<Long> paramList2, String paramString2);
  
  List<SalesOrderReportDto> salesOrderByRetailerListAndDistList(List<Long> paramList1, List<Long> paramList2, String paramString1, String paramString2);
}
