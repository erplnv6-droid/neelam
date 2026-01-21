package com.SCM.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.model.Notification;
import com.SCM.repository.NotificationRepository;
import com.SCM.service.NotificationLoginService;


@Service
public class NotificationServiceLoginImpl implements NotificationLoginService {
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<Notification> getNotificationByRetailer(int ret_id) {
	List<Notification> ret=notificationRepository.getNotificationByRetailer(ret_id);
		return ret;
	}

	@Override
	public List<Notification> getNotificationByDistributor(int dist_id) {
		// TODO Auto-generated method stub
		List<Notification> dist=notificationRepository.getNotificationByDistributor(dist_id);
		return dist;
	}

	@Override
	public List<Notification> getNotificationByRsm(int rsmid) {
		// TODO Auto-generated method stub
		List<Notification> rsm=notificationRepository.getNotificationByRsm(rsmid);
		return rsm;
	}

	@Override
	public List<Notification> getNotificationByAsm(int asmid) {
		// TODO Auto-generated method stub
		List<Notification> asm=notificationRepository.getNotificationByAsm(asmid);
		return asm;
	}

	@Override
	public List<Notification> getNotificationByAse(int aseid) {
		// TODO Auto-generated method stub
		List<Notification> ase=notificationRepository.getNotificationByAse(aseid);
		return ase;
	}

	@Override
	public List<Notification> getAllNotification() {
		// TODO Auto-generated method stub
		List<Notification> admin=notificationRepository.getNotificationByAdmin();
		return admin;
	
	}

	public Optional<Notification> getByNotificationId(int id)
	{
		return notificationRepository.findById(id);
	}
	
	public Notification updateByNotification(Notification notification)
	{
		return notificationRepository.save(notification);
	}
	
	
	public String deleteNotificationById(int id)
	{
		 notificationRepository.deleteById(id);
		 
		 return "Data Deleted Successfully";
	}
	
	

}
