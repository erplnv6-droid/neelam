package com.SCM.service;

import java.util.List;

import com.SCM.dto.SchedulerDto;
import com.SCM.model.Schedule;

public interface ScheduleService {

	public Schedule schedule(SchedulerDto schedulerDto);
	
	public List<Schedule> fetchSchedule();
	
}
