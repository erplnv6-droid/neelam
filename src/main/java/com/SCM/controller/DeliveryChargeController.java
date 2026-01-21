package com.SCM.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.SCM.dto.DeliveryChargeDto;
import com.SCM.model.DeliveryCharge;
import com.SCM.service.DeliveryChargeService;

@RestController
@RequestMapping("/api/dc")
@CrossOrigin(origins = "*")
public class DeliveryChargeController {

	@Autowired
	private DeliveryChargeService deliveryChargeService;

	@PostMapping("/")
	public ResponseEntity<?> savedDC(@RequestBody DeliveryChargeDto deliveryChargeDto) {
		
		return new ResponseEntity<>(deliveryChargeService.saveDC(deliveryChargeDto), HttpStatus.CREATED);	
	}

	@PostMapping("/{salesorderId}")
	public ResponseEntity<?> ConvertSOtoDC(@RequestBody DeliveryChargeDto deliveryChargeDto,@PathVariable("salesorderId") int salesorderId) {
		
		return new ResponseEntity<>(deliveryChargeService.convertSOtoDC(deliveryChargeDto,salesorderId), HttpStatus.CREATED);
	}

	@PostMapping("/updatedc")
	public ResponseEntity<?> updateDCinSoItems(@RequestBody DeliveryChargeDto deliveryChargeDto) 
	{
		DeliveryCharge deliveryCharge = deliveryChargeService.statusDC1(deliveryChargeDto);

		return new ResponseEntity<>(deliveryCharge, HttpStatus.CREATED);
	}

	
	@GetMapping("/")
	public ResponseEntity<?> showDC() {
		
		return new ResponseEntity<>(deliveryChargeService.getAllDC(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> showDCById(@PathVariable("id") int id) {
		
		return new ResponseEntity<>(deliveryChargeService.getDCById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateDC(@PathVariable("id") int id, @RequestBody DeliveryChargeDto deliveryChargeDto) {
		
		return new ResponseEntity<>(deliveryChargeService.updateDC(deliveryChargeDto, id), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteupdatedDc(@PathVariable("id") int id) {
		
		deliveryChargeService.deleteDCWithStatus(id);
		
		return new ResponseEntity<>("deleted Delivery Charge updated success", HttpStatus.OK);
	}
	
	// ------------------------------------


	
	@GetMapping("/page/{pageno}/{pagesize}/{sortby}/{field}/{search}")
	public ResponseEntity<?> IndexDistributor(@PathVariable("pageno") int pageno,
			                                  @PathVariable("pagesize") int pagesize,
			                                  @PathVariable("sortby") String sortby,
			                                  @PathVariable("field") String field,
			                                  @PathVariable("search")String search) 
	{
    	
    	if(!search.equals(" "))
		{
			return new ResponseEntity<>(deliveryChargeService.SearchDeliveryChallan(pageno, pagesize,search),HttpStatus.OK) ; 
		}
    	else if("asc".equals(sortby))
		{ 
			return new ResponseEntity<>(deliveryChargeService.IndexDeliveryChallanAsc(pageno,pagesize,field), HttpStatus.OK);
		}
		else if("desc".equals(sortby))
		{
			return new ResponseEntity<>(deliveryChargeService.IndexDeliveryChallanDesc(pageno,pagesize,field), HttpStatus.OK);
		}
		else {			
			return new ResponseEntity<>("Invalid Route", HttpStatus.BAD_REQUEST);
		}
	}

}
