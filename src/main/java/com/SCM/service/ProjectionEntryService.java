package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.IndexDto.IndexProjectionEntry;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.ProjectionEntryDto;
import com.SCM.projectionDto.ProjectionReportDto;

public interface ProjectionEntryService {

// projection changes

public ProjectionEntryDto saveProjectionEntry(ProjectionEntryDto dto);
	public Optional<ProjectionEntryDto> getById(long id);
	public ProjectionEntryDto updateProjectionEntryDto(ProjectionEntryDto dto,long id);
	public String deleteProjectionEntry(long id);
	
	public CustomPageResponse<IndexProjectionEntry> FindAllIndexProjection(int pageNumber,int pageSize,String field,String direction);
	public CustomPageResponse<IndexProjectionEntry> searchIndexProjection(int pageNumber,int pageSize,String field,String direction,String search);
	
	
	public Map<String, Object> indexProjectionReportByRetailerlistAndDistributorlistGroupAsc(int pageno, int pagesize,String field,
			long wid, String g1,String g2,String g3,String g4,String g5,String fromdate,String todate,
	        List<Long> retids, List<Long> distids,long count);
	public Map<String, Object> indexProjectionReportByRetailerlistAndDistributorlistGroupDesc(int pageno, int pagesize,String field,long wid, String g1,String g2,String g3,String g4,String g5,String fromdate,String todate,
	        List<Long> retids, List<Long> distids,long count);
	
	public List<ProjectionReportDto> indexProjectionReportAsc( String g1,String g2,String g3,String g4,String g5,String fromdate,String todate,long count);
}
