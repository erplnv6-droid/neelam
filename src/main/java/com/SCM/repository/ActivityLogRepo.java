package com.SCM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.model.ActivityLog;

@Repository
public interface ActivityLogRepo extends JpaRepository<ActivityLog, Long> {

	
	@Query(value = "select * from activity_log where loggeduser = :activitylogid",nativeQuery = true)
	List<ActivityLog> fetchActivitylogdetails(int activitylogid);
}
