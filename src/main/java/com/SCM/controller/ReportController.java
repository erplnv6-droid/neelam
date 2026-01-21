package com.SCM.controller;

import com.SCM.ReportDto.DistributorStockReport;
import com.SCM.dto.WarehouseDto;
import com.SCM.dtoReports.GodownStockReport;
import com.SCM.dtoReports.PendingPoVsGodownReports;
import com.SCM.dtoReports.PurchaseProductWiseReports;
import com.SCM.dtoReports.StockCheckingReports;
import com.SCM.model.Distributor;
import com.SCM.model.Warehouse;
import com.SCM.repository.OutwardDistStockRepo;
import com.SCM.repository.WarehouseRepository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/reports"})
@CrossOrigin(origins = {"*"})
public class ReportController {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Autowired
  private WarehouseRepository warehouseRepository;
  
  @Autowired
  private OutwardDistStockRepo outwardDistStockRepo;
  
  @GetMapping({"/stock/{wId}/{fromdate}/{todate}"})
  public List<StockCheckingReports> StockCheckingReports(@PathVariable("wId") final int wId, @PathVariable("fromdate") final String fromdate, @PathVariable("todate") final String todate) {
    String sql = "CALL StockCheckingReport(?,?,?)";
    List<StockCheckingReports> stockCheckingReports = this.jdbcTemplate.query(sql, 
        new PreparedStatementSetter() {
          public void setValues(PreparedStatement ps) throws SQLException {
            ps.setInt(1, wId);
            ps.setString(2, fromdate);
            ps.setString(3, todate);
          }
        },new BeanPropertyRowMapper(StockCheckingReports.class));
    return stockCheckingReports;
  }
  
  
  @GetMapping({"/purchase/product/"})
  public List<PurchaseProductWiseReports> purchaseProductWiseReports() {
    String sql = "CALL PurchaseProductWiseReport()";
    List<PurchaseProductWiseReports> purchaseProductWiseReports = this.jdbcTemplate.query(sql, 
        new BeanPropertyRowMapper(PurchaseProductWiseReports.class));
    return purchaseProductWiseReports;
  }
  
  @GetMapping({"/outward/{fromdate}/{todate}/{distid}"})
  public List<DistributorStockReport> outwarddistributorstockreport(@PathVariable("distid") int distid, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
    List<DistributorStockReport> outdiststock = this.outwardDistStockRepo.distributorstockreport(fromdate, todate, distid);
    return outdiststock;
  }
  
  @GetMapping({"/godown/{wid}/{fromdate}/{todate}/{pageno}/{pagesize}"})
  public Map<String, Object> godownstockreport(@PathVariable("wid") int wid, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate, @PathVariable("pageno") Integer pageno, @PathVariable("pagesize") Integer pagesize) {
    Map<String, Object> response = new HashMap<>();
    PageRequest pageRequest = PageRequest.of(pageno.intValue(), pagesize.intValue());
    int size = this.warehouseRepository.GodownStockReport1(wid, fromdate, todate).size();
    double size1 = size;
    double pagesize1 = pagesize.intValue();
    double pages = size1 / pagesize1;
    int pages1 = (int)Math.ceil(pages);
    List<GodownStockReport> gd = this.warehouseRepository.GodownStockReport(wid, fromdate, todate, (Pageable)pageRequest);
    response.put("Index", gd);
    response.put("CountPages", Integer.valueOf(size));
    response.put("Pages", Integer.valueOf(pages1));
    return response;
  }
  
  @GetMapping({"/godown/{wid}/{fromdate}/{todate}"})
  public List<GodownStockReport> godownstockreport1000(@PathVariable("wid") int wid, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
    List<GodownStockReport> gd = this.warehouseRepository.GodownStockReport1(wid, fromdate, todate);
    return gd;
  }
  
  @GetMapping({"/distributor"})
  public List<Distributor> dist() {
    String sql = "CALL DistributorGetAll()";
    List<Distributor> d = this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(Distributor.class));
    return d;
  }
  
  @PostMapping({"/pend/{fromdate}/{todate}"})
  public List<Map<String, Object>> getPendingOrderVsGodwonStock2(@RequestBody WarehouseDto warehouseDto, @PathVariable("fromdate") String fromdate, @PathVariable("todate") String todate) {
    List<PendingPoVsGodownReports> pendingOrders = new ArrayList<>();
    List<Integer> w = warehouseDto.getWareid();
    List<String> warehouseNames = new ArrayList<>();
    for (Iterator<Integer> iterator = w.iterator(); iterator.hasNext(); ) {
      int wwid = ((Integer)iterator.next()).intValue();
      Warehouse warehouse = this.warehouseRepository.findById(Integer.valueOf(wwid)).get();
      List<PendingPoVsGodownReports> pendingOrderVsGodownReport = this.warehouseRepository.pendingOrderVsGodownReport(wwid, fromdate, todate);
      pendingOrders.addAll(pendingOrderVsGodownReport);
      warehouseNames.add(warehouse.getName());
      System.out.println(warehouseNames);
    } 
    Map<String, List<PendingPoVsGodownReports>> productMap = new HashMap<>();
    for (PendingPoVsGodownReports order : pendingOrders) {
      String productName = order.getProduct_Name();
      if (!productMap.containsKey(productName))
        productMap.put(productName, new ArrayList<>()); 
      ((List<PendingPoVsGodownReports>)productMap.get(productName)).add(order);
    } 
    List<Map<String, Object>> finalResponse = new ArrayList<>();
    for (Map.Entry<String, List<PendingPoVsGodownReports>> entry : productMap.entrySet()) {
      String productName = entry.getKey();
      List<PendingPoVsGodownReports> productOrders = entry.getValue();
      Map<String, Object> productInfo = new HashMap<>();
      productInfo.put("product_Name", productName);
      productInfo.put("ean_code", ((PendingPoVsGodownReports)productOrders.get(0)).getEan_Code());
      productInfo.put("primaryorderkgqty", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).getprimaryorderkgqty()));
      productInfo.put("primaryorderpcsqty", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).getprimaryorderpcsqty()));
      productInfo.put("rate", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).getrate()));
      productInfo.put("kgamount", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).getkgamount()));
      productInfo.put("pcsamount", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).getpcsamount()));
      productInfo.put("totalpcsclosingstock", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).gettotalpcsclosingstock()));
      productInfo.put("totalkgclosingstock", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).gettotalkgclosingstock()));
      productInfo.put("totalpcsstock", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).gettotalpcsstock()));
      productInfo.put("totalkgstock", Float.valueOf(((PendingPoVsGodownReports)productOrders.get(0)).gettotalkgstock()));
      List<Map<String, Object>> closingStockList = new ArrayList<>();
      
      for (PendingPoVsGodownReports order : productOrders) {
    	  
        Map<String, Object> closingStock = new HashMap<>();
        int orderIndex = productOrders.indexOf(order);
        String warehouseName = warehouseNames.get(orderIndex);
        closingStock.put("warehousename", warehouseName);
        closingStock.put("closingkgqty", Float.valueOf(order.getclosingkgqty()));
        closingStock.put("closinpcsqty", Float.valueOf(order.getclosingpcsqty()));
        closingStockList.add(closingStock);
      } 
      productInfo.put("closingStock", closingStockList);
      finalResponse.add(productInfo);
    } 
    return finalResponse;
  }
}
