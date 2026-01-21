package com.SCM.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.SCM.ExportDto.ExportWorkOrder;
import com.SCM.config.UserId;
import com.SCM.dto.WorkOrderDto;
import com.SCM.model.Notification;
import com.SCM.model.OpeningStock;
import com.SCM.model.WorkOrder;
import com.SCM.projection.WorkItemProjection;
import com.SCM.repository.WarehouseRepository;
import com.SCM.repository.WorkOrderRepo;
import com.SCM.service.NotificationService;
import com.SCM.service.WorkOderService;
import com.google.firebase.messaging.FirebaseMessagingException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workorder")
public class WorkOrderController {

    @Autowired
    private UserId user;
    
    @Autowired
    private WorkOderService workOderService;
    
    @Autowired
    private WorkOrderRepo workOrderRepo;
    
    @Autowired
    private WarehouseRepository warehouseRepository;
    
    @Autowired
    private NotificationService notificationService;
    
	@Autowired
	private JdbcTemplate jdbcTemplate;



    @GetMapping("/getAll")
    public ResponseEntity<?> getAll()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RETAILER")))
        {
        	System.out.println(authentication.getAuthorities());
            System.out.println("success");
        }
        if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
        {
            return  new ResponseEntity<>(workOderService.getAllWorkOrderWithRetailer(),HttpStatus.OK);
        }
        else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_NSM"))) {

            return new ResponseEntity<>(workOderService.getAllWorkOrderWithRetailer(),HttpStatus.OK);

        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"))) {

            return new ResponseEntity<>(workOderService.getWorkOrderByRSM(user.getId().intValue()),HttpStatus.OK);

        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"))) {

            return new ResponseEntity<>(workOderService.getWorkOrderByASM(user.getId().intValue()),HttpStatus.OK);

        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"))) {

            return new ResponseEntity<>(workOderService.getWorkOrderByASE(user.getId().intValue()),HttpStatus.OK);
            
        }else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RETAILER"))) {

            return new ResponseEntity<>(workOderService.getWorkOrderByRetailer(user.getId().intValue()),HttpStatus.OK);
        }
        else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DISTRIBUTOR"))) {

            return new ResponseEntity<>(workOderService.getWorkOrderByDistributor(user.getId().intValue()),HttpStatus.OK);
        }
        else  {
            return new ResponseEntity<>("WorkOrder Not!!!! Found",HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/create")
    public WorkOrder add(@RequestBody WorkOrderDto workOrderDto) throws FirebaseMessagingException {
        System.out.println(workOrderDto);

      WorkOrder saveWorkOder = workOderService.saveWorkOder(workOrderDto);

      return saveWorkOder;
    }

    @PostMapping("/create/notification")
    public Notification addnotify(@RequestBody Notification notification) {
        System.out.println(notification);
   
        return notificationService.saveNotification(notification);
    }

    @GetMapping("/getById/{id}")
    public WorkOrderDto findWorkOrderById(@PathVariable int id){

    	return 	workOderService.getWorkOderById(id);
    }


    @PutMapping("/update/{id}")
    public WorkOrderDto update(@RequestBody WorkOrderDto workOrder, @PathVariable int id) {

		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        return workOderService.updateWorkOder(workOrder, id);
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id){

        return workOderService.deleteWorkOder(id);
    }

    
    @GetMapping("/warehouse/{id}")
    public List<OpeningStock> primaryWarehouseProducts(@PathVariable String id) {
       return   warehouseRepository.findByType(id);
    }

    
    @GetMapping("/GetWorkItemByWorkIds/{id}")
    public List<Map<String, Object>> GetWorkItemByWorkIds(@PathVariable int id){
    	
       return workOrderRepo.GetWorkItemByWorkIds(id);
    }

    //---------------- Get all workOrders from_date to to_date by distributor_id
    
    
    @GetMapping("{d_id}/{from_date}/{to_date}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByDistributorId(@PathVariable("d_id") int d_id,
                                                                      @PathVariable("from_date") String from_date,
                                                                      @PathVariable("to_date") String to_date){
    	
        List<WorkOrderDto> workOrder =  workOderService.getWorkOrdersbyId(d_id,from_date,to_date);
        
        System.out.println(workOrder + "+++++++++++++");

        return new ResponseEntity<>(workOrder, HttpStatus.OK);
    }


    @GetMapping("ase/{aseId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByAseId(@PathVariable("aseId") int aseId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByASE(aseId);

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }

    @GetMapping("/{asmId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByAsmId(@PathVariable("asmId") int asmId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByASM(asmId);

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }

    @GetMapping("rsm/{rsmId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByRsmId(@PathVariable("rsmId") int rsmId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByRSM(rsmId);

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }

    @GetMapping("nsm/{nsmId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByNsmId(@PathVariable("nsmId") int nsmId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByNSM(nsmId);

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }

    @GetMapping("zone/{zonesId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByZoneId(@PathVariable("zonesId") int zonesId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByZONE(zonesId);

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }

    @GetMapping("state/{stateId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByStateId(@PathVariable("stateId") int stateId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderBySTATE(stateId);

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }

    @GetMapping("dist/{distId}")
     public ResponseEntity<List<WorkOrderDto>> getWorkOrderByDistributorId(@PathVariable("distId")int distId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByDistributor(distId);

        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("ret/{retId}")
    public ResponseEntity<List<WorkOrderDto>> getWorkOrderByRetailerId(@PathVariable("retId")int retId)
    {
        List<WorkOrderDto> dto = workOderService.getWorkOrderByRetailer(retId);

        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }
    
    @GetMapping("woByPo/{id}")
    public ResponseEntity<WorkOrder> getWorkOrderByPrimaryOrder(@PathVariable("id")int id)
    {
        WorkOrder dto = workOderService.getWorkOrderByPrimaryOrder(id);

        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }
    
    @PutMapping("addEstimatedDays/{id}/{pid}/{days}/{primaryItemId}")
    public void addEstimatedDays(@PathVariable("id")int id, @PathVariable("pid")int pid,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date days, @PathVariable("primaryItemId")int  primaryItemId)
    {
       workOderService.updateEstimatedDays(id, pid, days, primaryItemId);
       
       System.out.println("Successfully updated");        
    }
    
    
    
	//-----index work order
	
	@GetMapping("page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexWorkOrder(@PathVariable("pageno") int pageno,
			                                   @PathVariable("pagesize") int pagesize,
			                                   @PathVariable("sortby") String sortby,
			                                   @PathVariable("field") String field,
			                                   @PathVariable("search")String search) 
	{
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(workOderService.SearchWorkOrder(pageno, pagesize, search),HttpStatus.OK);
		}
	    else if("asc".equals(sortby))
		{
			return new ResponseEntity<>(workOderService.IndexWorkOrderAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(workOderService.IndexWorkOrderDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
	
//	orderachievemet report
	

	@GetMapping("/export")
    public ResponseEntity<?> exportWorkOrder()
    {
        List<ExportWorkOrder> dto = workOderService.exportWo();

        return new ResponseEntity<>(dto, HttpStatus.OK) ;
    }
    
	
//	@GetMapping("/export")
//	public List<ExportWorkOrder1> exportWorkorder() {
//
//		String sql = "CALL WorkorderExport()";
//		
//		List<ExportWorkOrder1> exportwo = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(ExportWorkOrder1.class));
//		
//		return exportwo;
//	}
	
	
//	@GetMapping("/orderachievement/{retid}/{startDate}/{endDate}")
//	List<OrderAchievementReportForRetailer> achievementReportForRetailers(
//			
//			@PathVariable int retid,@PathVariable Date startDate,@PathVariable Date endDate
//			
//			){
//		
//		List<OrderAchievementReportForRetailer> achievementReportForRetailers=workOrderRepo.orderAchievementReport(retid, startDate, endDate);
//		
//		return achievementReportForRetailers;
//	}
//	
	
	
//	workordercontroller report
	
	
    @GetMapping("/orderachieve/page/{retid}/{startDate}/{endDate}/{pageno}/{pagesize}/{sortby}/{field}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("retid") int retid,
			@PathVariable("startDate") Date startDate,
			@PathVariable("endDate") Date endDate,
			@PathVariable("pageno") int pageno,
			 @PathVariable("pagesize") int pagesize,
             @PathVariable("sortby") String sortby,
             @PathVariable("field") String field)
    {
    	 if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(workOderService.orderAchievementByRetailerAsc(retid, startDate, endDate, pageno, pagesize, field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(workOderService.orderAchievementByRetailerDesc(retid, startDate, endDate, pageno, pagesize, field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
  	
    }

			
	
	@GetMapping("page/witems/{pageno}/{pagesize}/{sortby}/{field}/{startDate}/{endDate}/{retid}/{search}")
	public ResponseEntity<?> IndexWorkorderByRetailerId(@PathVariable("pageno") int pageno,
			                                   @PathVariable("pagesize") int pagesize,
			                                   @PathVariable("sortby") String sortby,
			                                   @PathVariable("field") String field,
			                                   @PathVariable("startDate")String startDate,
			                                   @PathVariable("endDate")String endDate,
			                                   @PathVariable("retid")int retid,
			                                   @PathVariable("search")String search) 
	{
		if(!search.equals(" "))
		{
			return new ResponseEntity<>(workOderService.searchworkorderItemByRetailerId(startDate,endDate,retid,pageno,pagesize,search), HttpStatus.OK);
		}
	     if("asc".equals(sortby))
		{
			return new ResponseEntity<>(workOderService.ascworkorderItemByRetailerId(startDate,endDate,retid,pageno,pagesize,field),HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(workOderService.descworkorderItemByRetailerId(startDate,endDate,retid,pageno,pagesize,field),HttpStatus.OK);

		}
	     
		else {
			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}
	
    

//    @GetMapping("/findAllByRid")
//    public ResponseEntity<List<WorkOrder>> findByret_id()
//    {
//    	List<WorkOrder> findByRet_id = workOderService.findByRet_id();
//
//      return new ResponseEntity<List<WorkOrder>>(findByRet_id , HttpStatus.OK);
//    	
//    }
//	

    
//    @Autowired
//    private WorkOrderItemRepo orderItemRepo;
//    
//    
//   @GetMapping("/byretid")
//   public List<IndexWorkorderItemByRetailerId>getAllData(){
//	   return orderItemRepo.getWorkOrderItemsByRetailerIdWithinDate();
//   }
	

    
    
    

}
