package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexWarehouse;
import com.SCM.dto.WarehouseDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Warehouse;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.WarehouseRepository;
import com.SCM.service.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;
    
	@Autowired
	private ActivityLogRepo activityLogRepo;

	public long getUserId() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
			Long uid = userDetails.getId();

			return uid;
	}
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		String role=list.get(0);
		return role;
	}
	
	
    @Override
    public Warehouse saveWarehouse(WarehouseDto warehouseDto) {
    	
    	Long uid = getUserId();
		String uname=getUserName();
		String role=getRolename();
		
    	Warehouse warehouse = new Warehouse();
    	warehouse.setCreatebyname(uname);
    	warehouse.setCreatedby(uid);
    	warehouse.setRole(role);
    	
    	warehouse.setCreatebyname(uname);
    	warehouse.setCreatedby(uid);
    	warehouse.setRole(role);
    	

    	warehouse.setName(warehouseDto.getName());
    	warehouse.setLocation(warehouseDto.getLocation());
    	warehouse.setType(warehouseDto.getType());
    	warehouse.setBranch(warehouseDto.getBranch());
    	
    	warehouse.setCreateddate(LocalDate.now());
    	warehouse.setCreatedtime(LocalTime.now());
    	
    	Warehouse w = warehouseRepository.save(warehouse);
    	
    	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setWarehouseid((long) w.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

        return w;
    }

    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse getWarehouseById(int id) {
        return warehouseRepository.findById(id).get();
    }

    @Override
    public String deleteWarehouse(int id) {
        warehouseRepository.deleteById(id);

        return "Warehouse Removed !!"+ id;
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse, int id) {

        Warehouse existingWarehouse = warehouseRepository.findById(id).orElse(null);

    	Long uid = getUserId();
		String uname=getUserName();

		String role=getRolename();
		
        existingWarehouse.setUpdatedbyname(uname);
        existingWarehouse.setUpdatedby(uid);
        existingWarehouse.setUpdatedrole(role);
        existingWarehouse.setUpdateddate(LocalDate.now());
        existingWarehouse.setUpdatedtime(LocalTime.now());
        

        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setLocation(warehouse.getLocation());
        existingWarehouse.setType(warehouse.getType());
        
        Warehouse w = warehouseRepository.save(existingWarehouse);
        
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setWarehouseid((long) w.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

        return w;
    }

    
//    --------- sahil------------------------------------------------
    
	@Override
	public Map<String, Object> IndexWarehouseAsc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		long id = userDetails.getId();

		int uid = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		
		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				
				long countpages = warehouseRepository.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

			    List<IndexWarehouse> ipo = Optional.ofNullable(warehouseRepository.indexWarehouse(p))
		                .orElse(Collections.emptyList());

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
		
		
	}
		return response;
	}
	@Override
	public Map<String, Object> IndexWarehouseDesc(int pagno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		long countpages = warehouseRepository.count();
		long pages = countpages / pagesize;

	    List<IndexWarehouse> ipo = Optional.ofNullable(warehouseRepository.indexWarehouse(p))
                .orElse(Collections.emptyList());

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchWarehouse(int pageno, int pagesize, String search) {
	
		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		List<IndexWarehouse> warehouse = warehouseRepository.SearchByWarehouse(search, p);
       long searchcount = warehouse.size();
		
		response.put("data", warehouse);
		response.put("SearchCount", searchcount);
		
		return response;
	}


}
