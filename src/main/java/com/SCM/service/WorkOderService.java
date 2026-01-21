package com.SCM.service;

import com.SCM.ExportDto.ExportWorkOrder;
import com.SCM.dto.WorkOrderDto;
import com.SCM.model.WorkOrder;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkOderService {

	public WorkOrder saveWorkOder(WorkOrderDto workOderDto) throws FirebaseMessagingException;

	public List<WorkOrder> getAllWorkOrderWithRetailer();

	public WorkOrderDto getWorkOderById(int id);

	public String deleteWorkOder(int id);

	public WorkOrderDto updateWorkOder(WorkOrderDto workOrderDto, int id);

	public List<WorkOrderDto> getWorkOrdersbyId(int d_id, String from_date, String to_date);

	// ase asm rsm in workorder

	public List<WorkOrderDto> getWorkOrderByASE(int aseId);

	public List<WorkOrderDto> getWorkOrderByASM(int asmId);

	public List<WorkOrderDto> getWorkOrderByRSM(int rsmId);

	public List<WorkOrderDto> getWorkOrderByNSM(int nsmId);

	public List<WorkOrderDto> getWorkOrderByZONE(int zonesId);

	public List<WorkOrderDto> getWorkOrderBySTATE(int stateId);

	public List<WorkOrderDto> getWorkOrderByDistributor(int distId);

	public List<WorkOrderDto> getWorkOrderByRetailer(int retId);

	public WorkOrder getWorkOrderByPrimaryOrder(int id);

	public void updateEstimatedDays(int id, int pid, Date days, int primaryItemId);

	// ------ index Work order

	public Map<String, Object> IndexWorkOrderAsc(int pagno, int pagesize, String field);

	public Map<String, Object> IndexWorkOrderDesc(int pagno, int pagesize, String field);

	public Map<String, Object> SearchWorkOrder(int pageno, int pagesize, String search);

	// --------- export work order

	public List<ExportWorkOrder> exportWo();

//    --orderachievementreport

//	public Map<String, Object> ascMinimumStockReport(int wid,int pageno,int pagesize,String field, String sort);

	public Map<String, Object> orderAchievementByRetailerAsc(int retid, Date startDate, Date endDate, int pagno,
			int pagesize, String field);

	public Map<String, Object> orderAchievementByRetailerDesc(int retid, Date startDate, Date endDate, int pagno,
			int pagesize, String field);

//    public List<WorkOrder> findByRet_id();

	public Map<String, Object> ascworkorderItemByRetailerId(String startdate, String enddate, int retid, int pageno,int pagesize, String field);

	public Map<String, Object> descworkorderItemByRetailerId(String startdate, String enddate, int retid, int pageno,int pagesize, String field);

	public Map<String, Object> searchworkorderItemByRetailerId(String startdate, String enddate, int retid, int pageno,int pagesize, String search);

}
