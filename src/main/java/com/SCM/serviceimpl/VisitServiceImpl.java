package com.SCM.serviceimpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.dto.VisitDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Schedule;
import com.SCM.model.Visit;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.ScheduleRepo;
import com.SCM.repository.VisitRepo;
import com.SCM.service.VisitService;

@Service
public class VisitServiceImpl implements VisitService {

	@Autowired
	private VisitRepo visitRepo;

	@Autowired
	private ScheduleRepo scheduleRepo;
	
	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Override
	public Visit visitIn(VisitDto visitDto) {

		Visit v = new Visit();
		v.setVisitin(LocalDateTime.now());
		v.setSchedule(visitDto.getSchedule());

		Schedule schedule = scheduleRepo.findById(visitDto.getSchedule().getId()).get();
		System.out.println(schedule);

		schedule.setStatus("ongoing");
		scheduleRepo.save(schedule);
		
		Visit vis = visitRepo.save(v);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setVisitid((long) vis.getId());
		activityLog.setLoggeduser(loggeduser);
		
		activityLogRepo.save(activityLog);

		return vis;
	}

	@Override
	public Visit visitOut(VisitDto visitDto, int id) {

		Visit v = visitRepo.findById(id).get();

		v.setVisitout(LocalDateTime.now());

		Schedule schedule = scheduleRepo.findById(visitDto.getSchedule().getId()).get();
		System.out.println(schedule);

		schedule.setStatus("visited");
		scheduleRepo.save(schedule);
		
		Visit vis = visitRepo.save(v);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setVisitid((long) vis.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return vis;
	}

	@Override
	public List<Visit> fetchVisit() {
			
		return visitRepo.findAll();
	}

}
