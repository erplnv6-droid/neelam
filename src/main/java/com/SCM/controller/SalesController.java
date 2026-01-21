package com.SCM.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.GstModel.EinvoiceData;
import com.SCM.GstRepository.EinvoiceRepository;
import com.SCM.GstRepository.EwayBillAuthRepository;
import com.SCM.dto.NewVoucherName;
import com.SCM.dto.SalesDto;
import com.SCM.mapper.GstMapper;
import com.SCM.projectionDto.ClosingStockReport;
import com.SCM.projectionDto.SalesReportDto;
import com.SCM.repository.SalesRepo;
import com.SCM.service.EwayBillService;
import com.SCM.service.GstService;
import com.SCM.service.SalesService;
import com.SCM.serviceimpl.EinvoiceAuth;

@RestController
@RequestMapping({"/api/sales"})
@CrossOrigin(origins = {"*"})
public class SalesController {
  @Autowired
  private SalesService salesService;
  
  @Autowired
  private GstService gstService;
  
  @Autowired
  private EwayBillService billService;
  
  @Autowired
  private EwayBillAuthRepository ewayBillAuthRepository;
  
  @Autowired
  private EinvoiceAuth einvoiceAuth;
  
  @Autowired
  private GstMapper mapper;
  
  @Autowired
  private EinvoiceRepository einvoiceRepository;
  
  @Autowired
  private SalesRepo salesRepo;
  

  private final Path fileStorageLocation1;

  public SalesController(Environment env) {
      this.fileStorageLocation1 = Paths.get(env.getProperty("app.file.upload-dir11", "/invoiceAttachmentPdf/"))
              .toAbsolutePath().normalize();
  }

  @GetMapping("/pdf/{filename:.+}")
  public void viewPdf(@PathVariable String filename, HttpServletResponse response) throws IOException {
      Path filePath = fileStorageLocation1.resolve(filename).normalize();

      if (!Files.exists(filePath)) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          return;
      }

      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");

      try (InputStream in = Files.newInputStream(filePath)) {
          StreamUtils.copy(in, response.getOutputStream());
          response.flushBuffer();
      }
  }
  
  
  
  
  
  
  
  @GetMapping({"/get/{id}"})
  public EinvoiceData getEinvoiceData(@PathVariable long id) {
    EinvoiceData findById = this.salesService.getEinvoiceData(id);
    return findById;
  }
  
  @PostMapping({"/"})
  public ResponseEntity<?> savedSales(@ModelAttribute SalesDto salesDto, @RequestParam("si") String si, @RequestParam("tdls") String tdls, @RequestParam("doc") String doc, @RequestParam("seller") String seller, @RequestParam("buyer") String buyer, @RequestParam("valdtls") String valdtls, @RequestParam("ewb") String ewb, @RequestParam("img") MultipartFile imgfiles, @RequestParam("pdf") MultipartFile pdffiles) throws IOException {
    salesDto.setInvoice_status("InAct");
    return new ResponseEntity<>(salesService.saveSales(salesDto, si, tdls, doc, seller, buyer, valdtls, ewb, imgfiles, pdffiles), HttpStatus.CREATED);
  }
  
  @PostMapping({"/dc/{dcId}"})
  public ResponseEntity<?> ConvertDCtoSales(@ModelAttribute SalesDto salesDto, @RequestParam("si") String si, @RequestParam("tdls") String tdls, @RequestParam("doc") String doc, @RequestParam("seller") String seller, @RequestParam("buyer") String buyer, @RequestParam("valdtls") String valdtls, @RequestParam("ewb") String ewb, @RequestParam("img") MultipartFile imgfiles, @RequestParam("pdf") MultipartFile pdffiles, @PathVariable("dcId") int dcId) throws IOException {
    salesDto.setInvoice_status("InAct");
    return new ResponseEntity<>(salesService.ConvertDCToSales(salesDto, si, tdls, doc, seller, buyer, valdtls, ewb, imgfiles, pdffiles, dcId), HttpStatus.CREATED);
  }
  
  @GetMapping({"/"})
  public ResponseEntity<?> showSales() {
    return new ResponseEntity<>(salesService.getAllSales(), HttpStatus.OK);
  }
  
  @GetMapping({"/{id}"})
  public ResponseEntity<?> showSalesById(@PathVariable("id") int id) {
    return new ResponseEntity<>(salesService.getSalesById(id), HttpStatus.OK);
  }
  
  @PutMapping({"/{id}"})
  public ResponseEntity<?> updateSales(@PathVariable("id") int id, @ModelAttribute SalesDto salesDto, @RequestParam("tdls") String tdls, @RequestParam("doc") String doc, @RequestParam("seller") String seller, @RequestParam("buyer") String buyer, @RequestParam("valdtls") String valdtls, @RequestParam("ewb") String ewb, @RequestParam("si") String si, @RequestParam("img") MultipartFile imgfiles, @RequestParam("pdf") MultipartFile pdffiles) throws IOException {
    return new ResponseEntity<>(salesService.updateSales(salesDto, id, si, tdls, doc, seller, buyer, valdtls, ewb, imgfiles, pdffiles), 
        HttpStatus.OK);
  }
  
  @DeleteMapping({"/{id}"})
  public ResponseEntity<?> deleteSales(@PathVariable("id") int id) {
    this.salesService.deleteSales(id);
    return new ResponseEntity<>("deleted Sales success", HttpStatus.OK);
  }
  
  @GetMapping({"/page/{pageno}/{pagesize}/{sortby}/{field}/{search}"})
  public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno, @PathVariable("pagesize") int pagesize, @PathVariable("sortby") String sortby, @PathVariable("field") String field, @PathVariable("search") String search) {
    if (!search.equals(" "))
      return new ResponseEntity<>(salesService.SearchSales(pageno, pagesize, search), HttpStatus.OK); 
    if ("asc".equals(sortby))
      return new ResponseEntity<>(salesService.IndexSalesAsc(pageno, pagesize, field), HttpStatus.OK); 
    if ("desc".equals(sortby))
      return new ResponseEntity<>(salesService.IndexSalesDesc(pageno, pagesize, field), HttpStatus.OK); 
    return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
  }
  
  @GetMapping({"/transportReport/{start_date}/{end_date}/{pageno}/{pagesize}/{sortby}/{field}"})
  public ResponseEntity<?> transportDetails(@PathVariable("start_date") String start_date, @PathVariable("end_date") String end_date, @PathVariable("pageno") Integer pageno, @PathVariable("pagesize") Integer pagesize, @PathVariable("field") String field, @PathVariable("sortby") String sortby) throws ParseException {
    if ("asc".equals(sortby)) {
      Map<String, Object> transportReport = this.salesService.transportReportAsc(start_date, end_date, pageno.intValue(), pagesize.intValue(), field);
      return new ResponseEntity<>(transportReport, HttpStatus.OK);
    } 
    if ("desc".equals(sortby)) {
      Map<String, Object> transportReport = this.salesService.transportReportDesc(start_date, end_date, pageno.intValue(), pagesize.intValue(), field);
      return new ResponseEntity<>(transportReport, HttpStatus.OK);
    } 
    return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
  }
  
  @GetMapping({"/newvouchername"})
  public ResponseEntity<?> getNewName(@RequestBody NewVoucherName newVoucherName) {
    return new ResponseEntity<>(salesService.generateVoucher(newVoucherName), HttpStatus.OK);
  }
  
	
	@GetMapping("/projectionreport")
	public ResponseEntity<?> getSales(
			@RequestParam(value = "rid", defaultValue = "0") List<Long> rid,
	        @RequestParam(value = "did", defaultValue = "0") List<Long> did,
	        @RequestParam("fromdate") String fromdate,
	        @RequestParam("todate") String todate
			){
		List<SalesReportDto> salesByRetailerListAndDistList = salesService.salesByRetailerListAndDistList(rid, did, fromdate, todate);
		return new ResponseEntity<>(salesByRetailerListAndDistList,HttpStatus.OK);
	}
	
	
	@GetMapping("/closingprojectionreport")
	public ResponseEntity<?> getclosing(
	        @RequestParam("fromdate") String fromdate,
	        @RequestParam("todate") String todate,
	        @RequestParam("wid") String wid,
	        @RequestParam("pid") Long pid
			){
		List<ClosingStockReport> closingStockByWidFdateTdate = salesService.closingStockByWidFdateTdate(fromdate, todate, wid, pid);
		return new ResponseEntity<>(closingStockByWidFdateTdate,HttpStatus.OK);
	}
	
  
}
