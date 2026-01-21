package com.SCM.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.config.UserId;
import com.SCM.model.Notification;
import com.SCM.repository.NotificationRepository;
import com.SCM.service.NotificationLoginService;
import com.SCM.serviceimpl.UserDetailsImpl;

@RestController
@RequestMapping("/api/notification/login")
@CrossOrigin(origins = "*")
public class NotificationControllerLogin {

	@Autowired
	private NotificationLoginService notificationLoginService;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserId user;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getAuthorities());	

		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
			return new ResponseEntity<>(notificationLoginService.getAllNotification(), HttpStatus.OK);
		}

		else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RETAILER"))) {
			return new ResponseEntity<>(notificationLoginService.getNotificationByRetailer(user.getId().intValue()),
					HttpStatus.OK);
		} else if (authentication.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_DISTRIBUTOR"))) {
			return new ResponseEntity<>(notificationLoginService.getNotificationByDistributor(user.getId().intValue()),
					HttpStatus.OK);
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"))) {
			return new ResponseEntity<>(notificationLoginService.getNotificationByRsm(user.getId().intValue()),
					HttpStatus.OK);
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"))) {
			return new ResponseEntity<>(notificationLoginService.getNotificationByAsm(user.getId().intValue()),
					HttpStatus.OK);
		} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"))) {
			return new ResponseEntity<>(notificationLoginService.getNotificationByAse(user.getId().intValue()),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>("WorkOrder Not!!!! Found", HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping("/get/{id}")
//	public ResponseEntity<Notification>findByOwner(@PathVariable int id)
//	{
//		Optional<Notification> quo=notificationLoginService.getByNotificationId(id);
//		if(quo.isPresent())
//		{
//			return new ResponseEntity<Notification>(quo.get(),HttpStatus.FOUND);
//		}
//		else {
//			return new ResponseEntity<Notification>(HttpStatus.NOT_FOUND);
//		}
//	}

	@PutMapping("/put/{id}")

	public String updateNotification(@PathVariable int id) {
//    	  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//          System.out.println(authentication.getAuthorities());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		Notification notification = notificationLoginService.getByNotificationId(id).get();

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {

				notification.setAdmin_read("read");
				notification.setRetailer_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				notification.setDistributor_read("read");
				notification.setRetailer_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			}

			else if (s.equals("ROLE_RETAILER")) {

				notification.setRetailer_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			} else if (s.equals("ROLE_NSM")) {

				notification.setNsm_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			} else if (s.equals("ROLE_RSM")) {

				notification.setRsm_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			} else if (s.equals("ROLE_ASM")) {

				notification.setAsm_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			} else if (s.equals("ROLE_ASE")) {

				notification.setAse_read("read");
				notificationLoginService.updateByNotification(notification);
				notificationRepository.getNotificationByDelete();

			}

		}

		return "Data Deleted Successfully";

	}
}


