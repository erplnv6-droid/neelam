package com.SCM.repository; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.LoosepackingActivityLog;

@Repository
public interface LoosepackingActivityLogRepo extends JpaRepository<LoosepackingActivityLog, Integer> {

}
