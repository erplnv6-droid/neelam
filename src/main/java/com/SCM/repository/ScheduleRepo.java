package com.SCM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SCM.model.Schedule;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {

}
