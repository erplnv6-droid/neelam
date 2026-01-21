package com.SCM.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.model.Notification;
import com.SCM.projection.WorkOrderCountProjection;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>{

	@Query(value="select * from notification where notification.admin_read=\"unread\" and notification.retailer_read=\"unread\"\r\n"

			+ "and notification.distributor_read=\"unread\"\r\n"
			+ " and notification.nsm_read=\"unread\"  and notification.rsm_read=\"unread\" and notification.asm_read=\"unread\"\r\n"
			+ "  and notification.ase_read=\"unread\" order by id desc" ,nativeQuery = true)


	List<Notification> getNotificationByAdmin();
	
	
	
	
	@Query(value="select * from notification where notification.ret_id=? and notification.admin_read=\"unread\" and notification.retailer_read=\"unread\"\r\n"
			+ " and notification.distributor_read=\"unread\"\r\n"
			+ " and notification.nsm_read=\"unread\"  and notification.rsm_read=\"unread\" and notification.asm_read=\"unread\"\r\n"

	

			+ "  and notification.ase_read=\"unread\" order by id desc",nativeQuery = true)

	List<Notification> getNotificationByRetailer(int ret_id);
	
	
	@Query(value="select * from notification where notification.dist_id=? and notification.admin_read=\"unread\" and notification.retailer_read=\"unread\"\r\n"
			+ "and notification.distributor_read=\"unread\"\r\n"
			+ " and notification.nsm_read=\"unread\"  and notification.rsm_read=\"unread\" and notification.asm_read=\"unread\"\r\n"
			+ "  and notification.ase_read=\"unread\" order by id desc",nativeQuery = true)
	List<Notification> getNotificationByDistributor(int dist_id);
	
	
	@Query(value="select * from notification where notification.rsmid=? and notification.admin_read=\"unread\" and notification.retailer_read=\"unread\"\r\n"
			+ "and notification.distributor_read=\"unread\"\r\n"
			+ " and notification.nsm_read=\"unread\"  and notification.rsm_read=\"unread\" and notification.asm_read=\"unread\"\r\n"
			+ "  and notification.ase_read=\"unread\" order by id desc",nativeQuery = true)
	List<Notification> getNotificationByRsm(int rsmid);
	
	
	@Query(value="select * from notification where notification.asmid=? and notification.admin_read=\"unread\" and notification.retailer_read=\"unread\"\r\n"
			+ "and notification.distributor_read=\"unread\"\r\n"
			+ " and notification.nsm_read=\"unread\"  and notification.rsm_read=\"unread\" and notification.asm_read=\"unread\"\r\n"
			+ "  and notification.ase_read=\"unread\" order by id desc",nativeQuery = true)
	List<Notification> getNotificationByAsm(int asmid);
	



	
	@Query(value="select * from notification where notification.aseid=? and notification.admin_read=\"unread\" and notification.retailer_read=\"unread\"\r\n"

			+ "and notification.distributor_read=\"unread\"\r\n"
			+ " and notification.nsm_read=\"unread\"  and notification.rsm_read=\"unread\" and notification.asm_read=\"unread\"\r\n"
			+ "  and notification.ase_read=\"unread\" order by id desc",nativeQuery = true)
	List<Notification> getNotificationByAse(int aseid);
	
	
	@Modifying
	@Transactional
	@Query(value="delete from notification where notification.admin_read=\"read\" and notification.retailer_read=\"read\"\r\n"
			+ " and notification.distributor_read=\"read\"\r\n"
			+ " and notification.nsm_read=\"read\"  and notification.rsm_read=\"read\" and notification.asm_read=\"read\"\r\n"
			+ " and notification.ase_read=\"read\"",nativeQuery = true)
	public void getNotificationByDelete();
	

}
