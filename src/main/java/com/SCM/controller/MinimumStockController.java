package com.SCM.controller; 

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SCM.dto.MinimumStockDto;
import com.SCM.dtoReports.MinimumStockReport;
import com.SCM.model.MinimumStock;
import com.SCM.projection.MinimumStockReportProjection;
import com.SCM.service.MinimumStockService;


@RestController
@RequestMapping("/api/mininmumstock")
public class MinimumStockController {

	@Autowired
	public MinimumStockService minimumStockService; 

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@PostMapping("/")
	public ResponseEntity<?> saveMinimumStock(@RequestBody MinimumStockDto minimumStockDto)
	{
		return new ResponseEntity<>(minimumStockService.savedminimumStock(minimumStockDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> showMinimumStock()
	{
		return new ResponseEntity<>(minimumStockService.showminimumStocks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showMinimumStockId(@PathVariable("id") int id)
	{
		return new ResponseEntity<>(minimumStockService.showminimumStocksId(id),HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMinimumStockId(@PathVariable("id") int id,@RequestBody MinimumStock minimumStock)
	{
		return new ResponseEntity<>(minimumStockService.updateMinimumStocks(minimumStock,id),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMinimumStockId(@PathVariable("id") int id)
	{
		minimumStockService.deleteMinimumStock(id);
		
		return new ResponseEntity<>("delete minimum stock",HttpStatus.OK);
	}
	
	
	
	
	//---------- Stock Report
	
	
//	@GetMapping("/reports")
//	public ResponseEntity<?> MinimumStockReports()
//	{
//		return new ResponseEntity<>(minimumStockService.MinimumStockReports(),HttpStatus.OK);
//	}

	
	@GetMapping("/reports")
	public List<MinimumStockReport> StockReportWithwarehouseid() {
		
		String sql = "CALL MinimumStockReport()";
		List<MinimumStockReport> dto = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MinimumStockReport.class));
		return dto;
	}
	
	@GetMapping("/reports/minimumStockReport/{wid}")
	public List<MinimumStockReportProjection> getMinimumStockReport(@PathVariable("wid") int wid) {
		
		 return minimumStockService.MinimumStockReports(wid);
	}
	
	
	
	@GetMapping("/reports/{w_id}/{fromdate}/{todate}")
	public List<MinimumStockReport> StockReport(@PathVariable("w_id") int w_id,
			                                    @PathVariable("fromdate") String fromdate, 
			                                    @PathVariable("todate") String todate) {
		String sql = "CALL MinimumStockParam(?,?,?)";
		List<MinimumStockReport> dto = jdbcTemplate.query(sql, new PreparedStatementSetter() 
		{
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, w_id);
				ps.setString(2, fromdate);
				ps.setString(3, todate);
			}
		}, new BeanPropertyRowMapper<MinimumStockReport>(MinimumStockReport.class));
		return dto;
	}
	

	@GetMapping("/pages/{wid}/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> indexReport(@PathVariable("pageno")int pageno,
                                          @PathVariable("pagesize") int pagesize,
                                          @PathVariable("sort")String sort,
                                          @PathVariable("field")String field,
                                          @PathVariable("search")String search,
                                          @PathVariable("wid")int wid){
		
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(minimumStockService.ascMinimumStockReport(wid,pageno, pagesize, field,sort),HttpStatus.OK); 
		}
		else if("asc".equals(sort)){
			
			return new ResponseEntity<>(minimumStockService.ascMinimumStockReport(wid,pageno, pagesize, field, sort),HttpStatus.OK);
		}
		else if("desc".equals(sort)) {
			
			return new ResponseEntity<>(minimumStockService.ascMinimumStockReport(wid,pageno, pagesize, field, sort),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Invalid Route",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	
	
	
	
   //-------------------   index Minimum stock
    
    
    @GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(minimumStockService.SearchMinimumStock(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(minimumStockService.IndexMinimumStockAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(minimumStockService.IndexMinimumStockDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	

	@GetMapping("/page/{wid}/{pageno}/{pagesize}/{sort}/{field}/{search}")
	public ResponseEntity<?> indexPage(@PathVariable("pageno")int pageno,
                                          @PathVariable("pagesize") int pagesize,
                                          @PathVariable("sort")String sort,
                                          @PathVariable("field")String field,
                                          @PathVariable("search")String search,
                                          @PathVariable("wid")int wid){
		
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(minimumStockService.ascMinimumStockReport(wid,pageno, pagesize, field,sort),HttpStatus.OK); 
		}
		else if("asc".equals(sort)){
			
			return new ResponseEntity<>(minimumStockService.ascMinimumStockReport(wid,pageno, pagesize, field, sort),HttpStatus.OK);
		}
		else if("desc".equals(sort)) {
			
			return new ResponseEntity<>(minimumStockService.ascMinimumStockReport(wid,pageno, pagesize, field, sort),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Invalid Route",HttpStatus.BAD_REQUEST);
		}
//		return null;
	}

	
	//-------------minimum stock pagination
	
    
	@GetMapping("/ms/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexMinimumStock(
												@PathVariable("pageno") int pageno,
			                                   @PathVariable("pagesize") int pagesize,
			                                   @PathVariable("sortby") String sortby,
			                                   @PathVariable("field") String field,
			                                   @PathVariable("search")String search
			                                   ) 
	{	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(minimumStockService.SearchMinimumStock(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(minimumStockService.IndexMinimumStockAsc(pageno,pagesize,field), HttpStatus.OK);
		} 
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(minimumStockService.IndexMinimumStockDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
  

}
