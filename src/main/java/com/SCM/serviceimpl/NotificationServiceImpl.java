package com.SCM.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.model.Notification;
import com.SCM.repository.NotificationRepository;
import com.SCM.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Override
	public Notification saveNotification(Notification notification) {
		// TODO Auto-generated method stub
	
		
		try
		{
			return notificationRepository.save(notification);
		}
		catch(Exception e)
		{
			logger.error("Exception Occur While save Notification",e);
		}
		return null;
	}

}
