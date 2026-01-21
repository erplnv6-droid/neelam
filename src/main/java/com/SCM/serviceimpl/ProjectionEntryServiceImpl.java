package com.SCM.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexProjectionEntry;
import com.SCM.dto.CustomPageResponse;
import com.SCM.dto.ProjectionEntryDto;
import com.SCM.mapper.ProjectionEntryMapper;
import com.SCM.model.ProjectionEntry;
import com.SCM.projectionDto.ProjectionReportDto;
import com.SCM.repository.ProjectionEntryRepository;
import com.SCM.service.ProjectionEntryService;
 
@Service
public class ProjectionEntryServiceImpl implements ProjectionEntryService{

	
	@Autowired
	private ProjectionEntryRepository projectionEntryRepository;
	
	@Autowired
	private ProjectionEntryMapper mapper;
	
	@Override
	public ProjectionEntryDto saveProjectionEntry(ProjectionEntryDto dto) {
		ProjectionEntry entity = mapper.toEntity(dto);
		ProjectionEntry save = projectionEntryRepository.save(entity);
		
		return mapper.toDto(save);
	}

	@Override
	public Optional<ProjectionEntryDto> getById(long id) {
	
		projectionEntryRepository.findById(id).orElseThrow(()-> new RuntimeException("Projection not found with id "+id));
		java.util.Optional<ProjectionEntry> entity = projectionEntryRepository.findById(id);
		Optional<ProjectionEntryDto> dto = entity.map(r->mapper.toDto(r));
		
		return dto;
	}

	@Override
	public ProjectionEntryDto updateProjectionEntryDto(ProjectionEntryDto dto, long id) {
		
		projectionEntryRepository.findById(id).orElseThrow(()-> new RuntimeException("Projection not found with id "+id));
		ProjectionEntry entity = mapper.toEntity(dto);
		entity.setId(id);
		
		ProjectionEntry save = projectionEntryRepository.save(entity);

		return mapper.toDto(save);
	}

	@Override
	public String deleteProjectionEntry(long id) {
		
		projectionEntryRepository.findById(id).orElseThrow(()-> new RuntimeException("Projection not found with id "+id));
		projectionEntryRepository.deleteById(id);
		return "Succesfully deleted the projection with id " + id;
	}

	@Override
	public CustomPageResponse<IndexProjectionEntry> FindAllIndexProjection(int pageNumber, int pageSize, String field,
			String direction) {
 

		Sort sort = Sort.by(field);
	    if ("asc".equalsIgnoreCase(direction)) {
	        sort = sort.ascending();
	    } else if ("desc".equalsIgnoreCase(direction)) {
	        sort = sort.descending();
	    } else {
	        sort = sort.ascending(); // Default sorting
	    }
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		
		Page<IndexProjectionEntry> repoPage = projectionEntryRepository.getAllProjectionEntry(pageable);
		List<IndexProjectionEntry> content=repoPage.getContent();
		
		CustomPageResponse<IndexProjectionEntry> response=new CustomPageResponse<>();
		response.setContent(content);
	    response.setPageNumber(repoPage.getNumber());
	    response.setPageSize(repoPage.getSize());
	    response.setLast(repoPage.isLast());
	    response.setTotalElements(repoPage.getTotalElements());
	    response.setTotalPages(repoPage.getTotalPages());

		return response;
	}

	@Override
	public CustomPageResponse<IndexProjectionEntry> searchIndexProjection(int pageNumber, int pageSize, String field,
			String direction, String search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> indexProjectionReportByRetailerlistAndDistributorlistGroupAsc(int pageno, int pagesize,String field,
			long wid,  String g1,String g2,String g3,String g4,String g5, String fromdate, String todate, List<Long> retids,
			List<Long> distids,long count) {
		
//		Map<String, Object> response = new HashMap<>();
//		Sort sort = Sort.by(Sort.Direction.ASC, field);
//		Pageable p = PageRequest.of(pageno, pagesize, sort);
//		
//		long countpages = 0 ;
//		String neelam="neelam";
//		List<ProjectionIndexReport> ipo=null;
//		
// //		search with group1
//		if (!g1.equals(neelam) && g2.equals(neelam)&&g3.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1(wid, g1, fromdate, todate, retids, distids);
//			
//			countpages=projectionEntryRepository.getAllNewProjectionEntryWithGroupn1(p, wid, g1, fromdate, todate, retids, distids).size();
//		}
////		searach with group2
//		if (g1.equals(neelam) && !g2.equals(neelam)&&g3.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
//			System.out.println("inside g2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2(wid, g2, fromdate, todate, retids, distids);
//			
//			countpages=projectionEntryRepository.getAllNewProjectionEntryWithGroupn2(p, wid, g2, fromdate, todate, retids, distids).size();
//		}
////		
////		search with group3
//		if (g1.equals(neelam) && g2.equals(neelam)&& !g3.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3(wid, g3, fromdate, todate, retids, distids);
//			
//			countpages=projectionEntryRepository.getAllNewProjectionEntryWithGroupn3(p, wid, g3, fromdate, todate, retids, distids).size();
//		}	
////		searach with group4
//		if (g1.equals(neelam) && g2.equals(neelam)&& g3.equals(neelam)&& !g4.equals(neelam)&& g5.equals(neelam)) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn4(wid, g4, fromdate, todate, retids, distids);
//			
//			countpages=projectionEntryRepository.getAllNewProjectionEntryWithGroupn4(p, wid, g4, fromdate, todate, retids, distids).size();
//		}	
//		
////		searach with group5
//		if (g1.equals(neelam) && g2.equals(neelam)&& g3.equals(neelam)&& g4.equals(neelam)&& !g5.equals(neelam)) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn5(wid, g5, fromdate, todate, retids, distids);
//			
//			countpages=projectionEntryRepository.getAllNewProjectionEntryWithGroupn5(p, wid, g5, fromdate, todate, retids, distids).size();
//		}	
////		 search with g1 and g2
//		if (count!=0 && count==2) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2(wid, g1, g2, fromdate, todate, retids, distids);
//			countpages = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2(p, wid, g1, g2, fromdate, todate, retids, distids).size();
//		}
////		 search with g1 and g2 and g3
//		if (count!=0 && count==3) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3(wid, g1, g2, g3, fromdate, todate, retids, distids);
//			countpages = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3(p,wid, g1, g2, g3, fromdate, todate, retids, distids).size();
//		}
////		 search with g1 and g2 and g3 and g4
//		if (count!=0 && count==4) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4(wid, g1, g2, g3, g4, fromdate, todate, retids, distids);
//			countpages = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4(p, wid, g1, g2, g3, g4, fromdate, todate, retids, distids).size();
//		}
////		 search with g1 and g2 and g3 and g4 and g5
//		if (count!=0 && count==5) {
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5(wid, g1, g2, g3, g4,g5, fromdate, todate, retids, distids);
//			countpages = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5(p, wid, g1, g2, g3, g4,g5, fromdate, todate, retids, distids).size();
//		}
//		
////		with g2 and g3
//		if (!g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
//			System.out.println("+++++++++++++ g2 and g3 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3(wid, g2, g3, fromdate, todate, retids, distids);
//			count = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3(p,wid, g2, g3, fromdate, todate, retids, distids).size();
//
//				}
//			
////		with g2 and g3 and g4
//		if (!g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&&g5.equals(neelam)) {
//			System.out.println("+++++++++++++ g2 and g3  and g4++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3and g4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4(wid, g2, g3,g4, fromdate, todate, retids, distids);
//			count = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4(p,wid, g2, g3,g4, fromdate, todate, retids, distids).size();
//
//				}
//		
////		with g2 and g3 and g4 and g5
//		if (!g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& !g5.equals(neelam)) {
//			System.out.println("+++++++++++++ g2 and g3 and g4 and g5++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4 and g5++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4 and g5++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4 and g5++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g2 and g3 and g4 and g5++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4AndGroupn5(wid, g2, g3,g4,g5, fromdate, todate, retids, distids);
//			count = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4AndGroupn5(p,wid, g2, g3,g4,g5, fromdate, todate, retids, distids).size();
//
//			}
//		
////		with  g3 and g4 
//		if (g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& g5.equals(neelam)) {
//			System.out.println("+++++++++++++ g3 and g4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3AndGroupn4(wid,  g3,g4, fromdate, todate, retids, distids);
//			count = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3AndGroupn4(p,wid, g3,g4, fromdate, todate, retids, distids).size();
//
//			}
////		with  g3 and g4 and g5
//		if (g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& !g5.equals(neelam)) {
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3AndGroupn4AndGroupn5(wid,  g3,g4,g5, fromdate, todate, retids, distids);
//			count = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3AndGroupn4AndGroupn5(p,wid, g3,g4,g5, fromdate, todate, retids, distids).size();
//
//			}
////		with g4 and g5
//		if (g2.equals(neelam) && g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& !g5.equals(neelam)) {
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			System.out.println("+++++++++++++ g3 and g4 and g5 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//
//			ipo = projectionEntryRepository.getAllNewProjectionEntryWithGroupn4AndGroupn5(wid,g4,g5, fromdate, todate, retids, distids);
//			count = projectionEntryRepository.getAllNewProjectionEntryWithGroupn4AndGroupn5(p,wid,g4,g5, fromdate, todate, retids, distids).size();
//
//			}
//		
//		long pages = countpages / pagesize;
//
//
//		long rem = countpages % pagesize;
//		if (rem > 0) {
//			pages++;
//		}
//		response.put("Index", ipo);
//		response.put("Enteries", countpages);
//		response.put("Pages", pages);
//
//		return response;
//	
	
		return null;
	}

	@Override
	public Map<String, Object> indexProjectionReportByRetailerlistAndDistributorlistGroupDesc(int pageno, int pagesize,String field,
			long wid,  String g1,String g2,String g3,String g4,String g5, String fromdate, String todate, List<Long> retids,
			List<Long> distids,long count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProjectionReportDto>  indexProjectionReportAsc( String g1,String g2,String g3,String g4,String g5,String fromdate,String todate,long count) {
		String neelam="neelam";

		
		// //		search with group1
		if (!g1.equals(neelam) && g2.equals(neelam)&&g3.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn1 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1(g1, fromdate, todate);
		 System.out.println("from g1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");		
		 System.out.println("from g1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		 System.out.println("from g1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		 System.out.println("from g1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		 	
			return withGroupn1;
			
		}
//		searach with group2
		if (g1.equals(neelam) && !g2.equals(neelam)&&g3.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn2 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2(g2, fromdate, todate);
		 
			return withGroupn2;
 		}
 		
//		search with group3
		if (g1.equals(neelam) && g2.equals(neelam)&& !g3.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn3 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3(g3, fromdate, todate);
		 
			return withGroupn3;
 		}	
//		searach with group4
		if (g1.equals(neelam) && g2.equals(neelam)&& g3.equals(neelam)&& !g4.equals(neelam)&& g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn4 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn4(g4, fromdate, todate);
		 
			return withGroupn4;
		}	
	
//		searach with group5
		if (g1.equals(neelam) && g2.equals(neelam)&& g3.equals(neelam)&& g4.equals(neelam)&& !g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn5 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn5(g5, fromdate, todate);
 			return withGroupn5;
 		}	
		
//		 search with g1 and g2
		if (count!=0 && count==2) {
			 List<ProjectionReportDto> withGroupn1And2 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1And2(g1, g2, fromdate, todate);
	            return withGroupn1And2;
		}
//		 search with g1 and g2 and g3
		if (count!=0 && count==3) {
			List<ProjectionReportDto> withGroupn1AndGroupn2AndGroupn3 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3(g1, g2, g3, fromdate, todate);
	        return withGroupn1AndGroupn2AndGroupn3;
 		}
////		 search with g1 and g2 and g3 and g4
		if (count!=0 && count==4) {
			 List<ProjectionReportDto> withGroupn1AndGroupn2AndGroupn3AndGroupn4 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4(g1, g2, g3, g4, fromdate, todate);
		        return withGroupn1AndGroupn2AndGroupn3AndGroupn4;
  		}
////		 search with g1 and g2 and g3 and g4 and g5
		if (count!=0 && count==5) {
			List<ProjectionReportDto> withGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5(g1, g2, g3, g4, g5, fromdate, todate);
	        return withGroupn1AndGroupn2AndGroupn3AndGroupn4AndGroupn5;
 		}
		
//		with g2 and g3
		if (!g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&&g4.equals(neelam)&&g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn2AndGroupn3 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3(g2, g3, fromdate, todate);
	        return withGroupn2AndGroupn3;
			}
			
//		with g2 and g3 and g4
		if (!g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&&g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn2AndGroupn3AndGroupn4 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4(g2, g3, g4, fromdate, todate);
	        return withGroupn2AndGroupn3AndGroupn4;
			}
//		
//		with g2 and g3 and g4 and g5
		if (!g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& !g5.equals(neelam)) {
		     List<ProjectionReportDto> withGroupn2AndGroupn3AndGroupn4AndGroupn5 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn2AndGroupn3AndGroupn4AndGroupn5(g2, g3, g4, g5, fromdate, todate);
		        return withGroupn2AndGroupn3AndGroupn4AndGroupn5;
			}
		
		
//		with  g3 and g4 
		if (g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& g5.equals(neelam)) {
			  List<ProjectionReportDto> withGroupn3AndGroupn4 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3AndGroupn4(g3, g4, fromdate, todate);
		        return withGroupn3AndGroupn4;
			}
//		with  g3 and g4 and g5
		if (g2.equals(neelam) && !g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& !g5.equals(neelam)) {
			 List<ProjectionReportDto> withGroupn3AndGroupn4AndGroupn5 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn3AndGroupn4AndGroupn5(g3, g4, g5, fromdate, todate);
		        return withGroupn3AndGroupn4AndGroupn5;
			}
//		with g4 and g5
		if (g2.equals(neelam) && g3.equals(neelam) &&g1.equals(neelam)&& !g4.equals(neelam)&& !g5.equals(neelam)) {
			List<ProjectionReportDto> withGroupn4AndGroupn5 = projectionEntryRepository.getAllNewProjectionEntryWithGroupn4AndGroupn5(g4, g5, fromdate, todate);
	        return withGroupn4AndGroupn5;
			}
				return null;
	}

	
}
