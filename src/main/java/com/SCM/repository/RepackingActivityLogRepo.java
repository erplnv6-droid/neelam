package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.RepackingActivityLog;

@Repository
public interface RepackingActivityLogRepo extends JpaRepository<RepackingActivityLog, Integer> {

}
