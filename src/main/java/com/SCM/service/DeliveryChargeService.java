package com.SCM.service;

import java.util.List;
import java.util.Map;
import com.SCM.dto.DeliveryChargeDto;
import com.SCM.model.DeliveryCharge;



public interface DeliveryChargeService {

	//---- manual Dc
	
	public DeliveryCharge saveDC(DeliveryChargeDto deliverychargedto);

	//--- convert So to DC
	
	public DeliveryCharge convertSOtoDC(DeliveryChargeDto deliverychargedto, int salesorderId);
	
	//---- pending so items status Dc
	
	public DeliveryCharge statusDC1(DeliveryChargeDto deliverychargedto);
	
	
	void deleteDCWithStatus(int id);

	public List<DeliveryCharge> getAllDC();

	public DeliveryCharge getDCById(int id);

	void deleteDeliveryCharge(int id);

	public DeliveryChargeDto updateDC(DeliveryChargeDto deliverychargedto, int id);
	
	public Map<String,Object> IndexDeliveryChallanAsc(int pageno,int pagesize,String field);
	
	public Map<String,Object> IndexDeliveryChallanDesc(int pageno,int pagesize,String field);
	


	 public Map<String, Object> SearchDeliveryChallan(int pageno,int pagesize,String search);



	



	
}
