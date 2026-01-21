package com.SCM.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.PreIndex;
import com.SCM.model.WarehouseUpload;
import com.SCM.repository.WarehouseUploadRepo;
import com.SCM.service.WarehouseUploadService;


@RestController
@RequestMapping("/api/warehouseupload")
public class WarehouseUploadController {

	@Autowired
	private WarehouseUploadService warehouseUploadService;
	
	@Autowired
	private WarehouseUploadRepo warehouseUploadRepo;
	
	
	@PostMapping("/")
	public ResponseEntity<?> save(@ModelAttribute WarehouseUpload warehouseUpload,@RequestParam("excel") MultipartFile excel) throws IOException
	{
		warehouseUploadService.savePrimary1(warehouseUpload,excel);
		
		return new ResponseEntity<>("file upload",HttpStatus.CREATED);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<?> show()
	{
		return new ResponseEntity<>(warehouseUploadService.showWarehouseUpload(),HttpStatus.OK);
	}
	
	
//	======================== Index Warehouse
	
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	 public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("search")String search) 
		{   	
	    	if(!search.equals(" "))
			{
				return new ResponseEntity<>(warehouseUploadService.SearchWarehouseUpload(pageno, pagesize,search),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{
				return new ResponseEntity<>(warehouseUploadService.IndexWarehouseUploadAsc(pageno,pagesize,field), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				return new ResponseEntity<>(warehouseUploadService.IndexWarehouseUploadDesc(pageno,pagesize,field), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
	 
//	 ================== get Warehouse By Id
	 
	 
//		@GetMapping("/{wid}/{wdate}")
//		public ResponseEntity<?> showByWarehouseAndDate(@PathVariable("wid")int wid,
//				                                        @PathVariable("wdate")String wdate)
//		{
//			List<PreIndex> warehouseUploadById = warehouseUploadService.getWarehouseUploadById(wid,wdate);
//			
//			return new ResponseEntity<>(warehouseUploadById,HttpStatus.OK);
//		}
	 
// ======================= index Warehouse By warehouse id and date 
	 
//	 @GetMapping("/page1/{pageno}/{pagesize}/{sortby}/{field}")
//		public ResponseEntity<?> IndexDistributor1(@PathVariable("pageno") int pageno,
//				                                  @PathVariable("pagesize") int pagesize,
//				                                  @PathVariable("sortby") String sortby,
//				                                  @PathVariable("field") String field) 
//		{   	
//	    
//	    	if("asc".equals(sortby))
//			{
//				return new ResponseEntity<>(warehouseUploadService.getWarehouseUploadByIdWithPaginationASC(pageno, pagesize, field), HttpStatus.OK);
//			}
//			else if("desc".equals(sortby))
//			{
//				return new ResponseEntity<>(warehouseUploadService.getWarehouseUploadByIdWithPaginationDESC(pageno, pagesize, field), HttpStatus.OK);
//			}
//			else {			
//				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
//			}
//		}
	 
		 
	 
	 
	 @GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{wid}/{search}")
	 public ResponseEntity<?> IndexWarehouseByIdandDate(@PathVariable("pageno") int pageno,
				                                  @PathVariable("pagesize") int pagesize,
				                                  @PathVariable("sortby") String sortby,
				                                  @PathVariable("field") String field,
				                                  @PathVariable("wid")int wid,				                                  
				                                  @PathVariable("search")String search) 
		{   	
	    	if(!search.equals(" "))
			{
				return new ResponseEntity<>(warehouseUploadService.SearchWarehouseUploadDescByWarehouseId(pageno,pagesize,search,wid),HttpStatus.OK) ; 
			}
	    	else if("asc".equals(sortby))
			{	
	    		Sort sort = Sort.by(Sort.Direction.ASC, field);
	    		Pageable p = PageRequest.of(pageno, pagesize, sort);
	    		
	    		List<PreIndex> ipo = warehouseUploadRepo.IndexExcelByWarehouseIdandDate(p, wid);
	    		
	    		if(ipo.isEmpty())
	    		{
	    		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" No records");
	    		}
	    		
				return new ResponseEntity<>(warehouseUploadService.IndexWarehouseUploadAscByWarehouseId(pageno,pagesize,field, wid), HttpStatus.OK);
			}
			else if("desc".equals(sortby))
			{
				Sort sort = Sort.by(Sort.Direction.ASC, field);
	    		Pageable p = PageRequest.of(pageno, pagesize, sort);
	    		
	    		List<PreIndex> ipo = warehouseUploadRepo.IndexExcelByWarehouseIdandDate(p, wid);
	    		
	    		if(ipo.isEmpty())
	    		{
	    		    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records");
	    		}
	    		
				return new ResponseEntity<>(warehouseUploadService.IndexWarehouseUploadDescByWarehouseId(pageno,pagesize,field,wid), HttpStatus.OK);
			}
			else {			
				return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
			}
		}
		
}
