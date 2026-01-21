package com.SCM.service;

import java.util.List;
import java.util.Optional;

import com.SCM.dto.WorkOrderDto;
import com.SCM.model.Notification;


public interface NotificationLoginService {
	
	public List<Notification> getAllNotification();

	public List<Notification> getNotificationByRetailer(int ret_id);
	
	public List<Notification> getNotificationByDistributor(int dist_id);
	
	public List<Notification> getNotificationByRsm(int rsmid);
	
	public List<Notification> getNotificationByAsm(int asmid);
	
	public List<Notification> getNotificationByAse(int aseid);
	
	public Optional<Notification> getByNotificationId(int id);
	
	public Notification updateByNotification(Notification notification);
	
	public String deleteNotificationById(int id);
	
}
