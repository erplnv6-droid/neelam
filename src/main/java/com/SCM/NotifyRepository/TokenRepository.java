package com.SCM.NotifyRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.SCM.NotificationRequest.Tokens;
import com.SCM.model.Staff;


public interface TokenRepository extends JpaRepository<Tokens,Long>{
	
	

	@Transactional
	@Query(value="select t.id,t.rolename,t.staffid,t.staffname,t.token,r.id as rid,r.distributor_id as did from\r\n"
			+ "			tokens t left join retailer r on t.staffid=r.id\r\n"
			+ "		left join tokens t1 on t1.staffid=r.distributor_id\r\n"
			+ "		left join tokens t2 on t2.staffid=r.nsmid\r\n"
			+ "			left join tokens t3 on t3.staffid=r.rsmid\r\n"
			+ "			left join tokens t4 on t4.staffid=r.asmid\r\n"
			+ "			left join tokens t5 on t5.staffid=r.aseid\r\n"
			+"            left join distributor d on t.staffid=d.id\r\n"
			+"            left join distributor d1 on d1.id=r.distributor_id\r\n"
			+ "			where r.id=?",nativeQuery = true)
	Optional<Tokens> getNotificationRetailer(Integer id);
	
	
	
	@Modifying
	@Transactional
	@Query(value="select t.id,t.rolename,t.staffid,t.staffname,t.token,r.id as rid,r.distributor_id as did from\r\n"
			+ "			tokens t left join retailer r on t.staffid=r.id\r\n"
			+ "		left join tokens t1 on t1.staffid=r.distributor_id\r\n"
			+ "		left join tokens t2 on t2.staffid=r.nsmid\r\n"
			+ "			left join tokens t3 on t3.staffid=r.rsmid\r\n"
			+ "			left join tokens t4 on t4.staffid=r.asmid\r\n"
			+ "			left join tokens t5 on t5.staffid=r.aseid\r\n"
			+"            left join distributor d on t.staffid=d.id\r\n"
			+ "			where r.distributor_id=?",nativeQuery = true)
	List<Tokens> getNotificationDistributorStaff(Integer distributor_id);
	
	
	
	
	

	@Transactional
	@Query(value="select t.id,t.rolename,t.staffid,t.staffname,t.token from\r\n"
			+ "	tokens t left join distributor d on t.staffid=d.id \r\n"
			+ " left join tokens t2 on t2.staffid=d.nsmid\r\n"
			+ " left join tokens t3 on t3.staffid=d.rsmid\r\n"
			+ "	left join tokens t4 on t4.staffid=d.asmid\r\n"
			+ "	left join tokens t5 on t5.staffid=d.aseid\r\n"
			+ "	where d.id=?",nativeQuery = true)
	Optional<Tokens> getNotificationDistributor(Integer dist_id);
	
	
Optional<Tokens> findByStaffid(long staffid);



@Query(value="select t.id,t.rolename,t.staffid,t.staffname,t.token from\r\n"
		+ "		tokens t left join staff st on t.staffid=st.id where t.rolename=\"ROLE_ADMIN\" ",nativeQuery = true)
Optional<Tokens> getNotificationAdminWork();




	
	@Modifying
	@Transactional
	@Query(value="delete from tokens where staffid=? order by id desc limit 1",nativeQuery = true)
  public void deletNotification (long staffid);
	
	
	
	
	
	
//	@Modifying
//	@Transactional
//	@Query(value="select t.id as id,t.rolename as rolename,t.staff_id as staffid,t.staffname as staffname,t.token as token,r.id as rid,r.distributor_id as did from\r\n"
//			+ " tokens t left join retailer r on t.staff_id=r.id \r\n"
//			+ " left join tokens t1 on t1.staff_id=r.distributor_id\r\n"
//			+ " where r.id=?1",nativeQuery = true)
//	public TokenProjection getNotificationRetailer(Integer ret_id);

}
