package com.SCM.controller;

import com.SCM.IndexDto.IndexRendingSalesOrderByDistributor;
import com.SCM.IndexDto.IndexRendingSalesOrderByRetailer;
import com.SCM.dto.SalesOrderCancelRequest;
import com.SCM.dto.SalesOrderDto;
import com.SCM.dtoReports.PendingSalesOrderByRetailerWithoutDistributor;
import com.SCM.projectionDto.SalesOrderReportDto;
import com.SCM.repository.SalesOrderRepo;
import com.SCM.service.SalesOrderService;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/so"})
@CrossOrigin(origins = {"*"})
public class SOController {
  @Autowired
  private SalesOrderService salesOrderService;
  
  @Autowired
  private SalesOrderRepo salesOrderRepo;
  
  @PostMapping({"/"})
  public ResponseEntity<?> savedSO(@RequestBody SalesOrderDto salesOrderDto) {
    return new ResponseEntity(this.salesOrderService.saveSalesOrder(salesOrderDto), HttpStatus.CREATED);
  }
  
  @PostMapping({"/{primaryorderId}"})
  public ResponseEntity<?> ConvertPrimaryOrdertoSalesOrder(@RequestBody SalesOrderDto salesOrderDto, @PathVariable("primaryorderId") int primaryorderId) {
    return new ResponseEntity(this.salesOrderService.convertPrimaryOrdertoSalesOrder(salesOrderDto, primaryorderId), HttpStatus.CREATED);
  }
  
  @PostMapping({"/wo/{workorderId}"})
  public ResponseEntity<?> ConvertWorkOrdertoSalesOrder(@RequestBody SalesOrderDto salesOrderDto, @PathVariable("workorderId") int workorderId) {
    return new ResponseEntity(this.salesOrderService.convertWorkordertoSalesorder(salesOrderDto, workorderId), HttpStatus.CREATED);
  }
  
  @GetMapping({"/"})
  public ResponseEntity<?> showSO() {
    return new ResponseEntity(this.salesOrderService.getAllSalesOrder(), HttpStatus.OK);
  }
  
  @GetMapping({"/{id}"})
  public ResponseEntity<?> showSOById(@PathVariable("id") int id) {
    return new ResponseEntity(this.salesOrderService.getSalesOrderById(id), HttpStatus.OK);
  }
  
  @PutMapping({"/{id}"})
  public ResponseEntity<?> updateSO(@PathVariable("id") int id, @RequestBody SalesOrderDto salesOrderDto) {
    return new ResponseEntity(this.salesOrderService.updateSalesOrder1(salesOrderDto, id), HttpStatus.ACCEPTED);
  }
  
  @DeleteMapping({"/{id}"})
  public ResponseEntity<?> deleteSO(@PathVariable("id") int id) {
    this.salesOrderService.deleteSalesOrder(id);
    return new ResponseEntity("deleted Sales Order success", HttpStatus.OK);
  }
  
  @GetMapping({"dist/{distid}"})
  public ResponseEntity<?> showSoitemsBydistIdandproductid(@PathVariable("distid") int distid) {
    return new ResponseEntity(this.salesOrderService.fetchsalesitemsbydistandproduct(distid), HttpStatus.OK);
  }
  
  @GetMapping({"ret/{retid}"})
  public ResponseEntity<?> showSoitemsByRetIdandproductid(@PathVariable("retid") int retid) {
    return new ResponseEntity(this.salesOrderService.fetchsalesitemsbyretandproduct(retid), HttpStatus.OK);
  }
  
  @GetMapping({"soitems/{soitemsid}"})
  public ResponseEntity<?> getSoitemsById(@PathVariable("soitemsid") int soitemsid) {
    return new ResponseEntity(this.salesOrderService.getSalesorderitemsoitemsID(soitemsid), HttpStatus.OK);
  }
  
  @GetMapping({"/page/{pageno}/{pagesize}/{sortby}/{field}/{search}"})
  public ResponseEntity<?> indexMaterialReceipt(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize, @PathVariable("sortby") String sortby, @PathVariable("field") String field, @PathVariable("search") String search) {
    if (!search.equals(" "))
      return new ResponseEntity(this.salesOrderService.searchBySalesOrder(pageno, pagesize, search), HttpStatus.OK); 
    if ("asc".equals(sortby))
      return new ResponseEntity(this.salesOrderService.indexSalesOrderAsc(pageno, pagesize, field), HttpStatus.OK); 
    if ("desc".equals(sortby))
      return new ResponseEntity(this.salesOrderService.indexSalesOrderDesc(pageno, pagesize, field), HttpStatus.OK); 
    return new ResponseEntity("Invalid Route", HttpStatus.BAD_REQUEST);
  }
  
  @GetMapping({"/so/{id}"})
  public ResponseEntity<?> SOById(@PathVariable("id") int id) {
    return new ResponseEntity(this.salesOrderRepo.getSoById(id), HttpStatus.OK);
  }
  
  @GetMapping({"withoutdistributor/pendingSoByRetailerId/{retid}/{fromdate}/{todate}"})
  public ResponseEntity<?> fetchpendingSoByWithoutsdist(@PathVariable int retid, @PathVariable String fromdate, @PathVariable String todate) throws ParseException {
    List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailerwithoutdistributor = this.salesOrderService.pendingsobyretailerwithoutdistributor(retid, fromdate, todate);
    return new ResponseEntity(pendingsobyretailerwithoutdistributor, HttpStatus.OK);
  }
  
  @GetMapping({"customer/pendingSoByRetailerId/{retid}/{fromdate}/{todate}"})
  public ResponseEntity<?> fetchpendingSoByCustomer(@PathVariable int retid, @PathVariable String fromdate, @PathVariable String todate) throws ParseException {
    List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailerwithoutdistributor = this.salesOrderService.pendingsobyretailercustomer(retid, fromdate, todate);
    return new ResponseEntity(pendingsobyretailerwithoutdistributor, HttpStatus.OK);
  }
  
  @GetMapping({"/item/{id}"})
  public ResponseEntity<?> SoItemsById(@PathVariable("id") int id) {
    return new ResponseEntity(this.salesOrderService.getSalesOrderItemsById(id), HttpStatus.OK);
  }
  
  @GetMapping({"/item1/{id}"})
  public ResponseEntity<?> SoItemsById1(@PathVariable("id") int id) {
    return new ResponseEntity(this.salesOrderService.getSalesOrderItemsById1(id), HttpStatus.OK);
  }
  
  @GetMapping({"/pendingSoByRetailerId/{retid}/{startdate}/{enddate}"})
  public ResponseEntity<?> fetchpenidingsalesorderbyretailer(@PathVariable int retid, @PathVariable String startdate, @PathVariable String enddate) {
    List<IndexRendingSalesOrderByRetailer> pendingsalesorderbyretailerid = this.salesOrderService.Pendingsalesorderbyretailerid(retid, startdate, enddate);
    return new ResponseEntity(pendingsalesorderbyretailerid, HttpStatus.OK);
  }
  
  @GetMapping({"/pendingSoByDistributorId/{distid}/{startDate}/{endDate}"})
  public ResponseEntity<?> getAllPendingSoByDistributorId(@PathVariable int distid, @PathVariable String startDate, @PathVariable String endDate) throws ParseException {
    List<IndexRendingSalesOrderByDistributor> pendingsalesorderbydistributorid = this.salesOrderService.Pendingsalesorderbydistributorid(distid, startDate, endDate);
    return new ResponseEntity(pendingsalesorderbydistributorid, HttpStatus.OK);
  }
  
  @PutMapping({"cancel/{soitemid}"})
  public ResponseEntity<?> CancelSalesOrderItemsBySalesOrderID(@RequestBody SalesOrderCancelRequest salesOrderCancelRequest, @PathVariable int soitemid) {
    String cancelItems = this.salesOrderService.CancelItems(salesOrderCancelRequest, soitemid);
    return new ResponseEntity(cancelItems, HttpStatus.OK);
  }
  
  @PutMapping({"cancel1/{soitemid}"})
  public ResponseEntity<?> CancelSalesOrderItemsBySalesOrderID1(@RequestBody SalesOrderCancelRequest salesOrderCancelRequest, @PathVariable int soitemid) {
    String cancelItems = this.salesOrderService.CancelItemsbydistributor(salesOrderCancelRequest, soitemid);
    return new ResponseEntity(cancelItems, HttpStatus.OK);
  }
  
  @GetMapping({"/projectionreport"})
  public ResponseEntity<?> findAllSoByRetAndDistId(@RequestParam(value = "rid", defaultValue = "0") List<Long> rid, @RequestParam(value = "did", defaultValue = "0") List<Long> did, @RequestParam("fromdate") String fromdate, @RequestParam("todate") String todate) {
    List<SalesOrderReportDto> salesOrderByRetailerListAndDistList = this.salesOrderService.salesOrderByRetailerListAndDistList(rid, did, fromdate, todate);
    return new ResponseEntity(salesOrderByRetailerListAndDistList, HttpStatus.OK);
  }
}
