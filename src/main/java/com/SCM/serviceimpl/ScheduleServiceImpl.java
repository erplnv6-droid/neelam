package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.SCM.dto.SchedulerDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Schedule;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.ScheduleRepo;
import com.SCM.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleRepo scheduleRepo;
	
	@Autowired
	private ActivityLogRepo activityLogRepo;
	
	@Override
	public Schedule schedule(SchedulerDto schedulerDto) {
		
		Schedule s = new Schedule();
		s.setMeetingtitle(schedulerDto.getMeetingtitle());
		s.setLocation(schedulerDto.getLocation());
		s.setMeetingdateandtime(schedulerDto.getMeetingdateandtime());
		s.setStatus("pending");
		s.setCreateddate(LocalDate.now());
		s.setCreatedtime(LocalTime.now());
		
		Schedule sch = scheduleRepo.save(s);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setScheduleid((long) sch.getId());
		activityLog.setLoggeduser(loggeduser);
		
		activityLogRepo.save(activityLog);
		
		return sch;
	}


	@Override
	public List<Schedule> fetchSchedule() {
	
		return scheduleRepo.findAll();
	}

}
