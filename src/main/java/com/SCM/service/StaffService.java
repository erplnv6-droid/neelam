package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.ExportDto.ExportStaff;
import com.SCM.IndexDto.IndexStaffByStates;
import com.SCM.IndexDto.IndexStaffDto;
import com.SCM.IndexDto.IndexStatesByZones;
import com.SCM.IndexDto.MultipleToStaffDto;
import com.SCM.dto.StaffDto;
import com.SCM.model.Staff;
import com.SCM.payload.StaffRequest;

public interface StaffService {

	public Staff updateStaff(Staff staff, long id);

	public Staff updateStaff(StaffRequest staffRequest, long id);

	public List<StaffDto> getAllStaff();

	public String deleteStaff(long id);

	public Staff getStaffById(long id);

	public List<StaffDto> getStaffByAseId(int aseId);

	public List<StaffDto> getStaffByAsmId(int asmId);

	public List<StaffDto> getStaffByRsmId(int rsmId);

	public List<StaffDto> getStaffByNsmId(int nsmId);

	public List<StaffDto> getASMByAsmId(int id);

	public List<StaffDto> getASEByAseId(int id);

	public List<StaffDto> getRsmZoneId(int zoneId);

	public List<StaffDto> getAsmByZoneId(int zoneId);

	public List<StaffDto> getAseByZoneId(int zoneId);

	public List<StaffDto> getStaffByZoneId(int zoneId);

	public List<StaffDto> getStaffAsmByRsmId(int rsmId);

	public List<StaffDto> getStaffAseByRsmId(int rsmId);

	public List<StaffDto> getStaffAseByASMId(int asmId);

	public List<StaffDto> getStaffByNsm();

	// -------------- Index Fetch

	public Map<String, Object> IndexStaffAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexStaffDSC(int pageno, int pagesize, String field);

	public Map<String, Object> SearchStaff(int pageno, int pagesize, String search);

	// ------------- export product

	public List<ExportStaff> exportStaff();

	public List<IndexStaffDto> getStaffByStateId(int stateId);

//    public List<IndexStatesByZones> fetchStatesByZones(ZoneRequest zoneRequest);

	public List<IndexStatesByZones> fetchStatesByZones(List<Integer> zoneid);

	public List<IndexStatesByZones> getASEByMultipleZones(List<Integer> stateid);

	public List<IndexStatesByZones> getASMByMultipleZones(List<Integer> stateid);

	public List<IndexStatesByZones> getRSMByMultipleZones(List<Integer> stateid);

	public List<IndexStatesByZones> getASEByMultipleRSMId(List<Integer> rsmid);

	public List<IndexStatesByZones> getASMByMultipleRSMId(List<Integer> rsmid);

	public List<IndexStatesByZones> getASEByMultipleASMId(List<Integer> asmid);

//    

	public List<MultipleToStaffDto> multipleToStaff(int sid);
	
	
	public List<IndexStaffByStates> getStaffByMultipleStates(int stateid);


}
