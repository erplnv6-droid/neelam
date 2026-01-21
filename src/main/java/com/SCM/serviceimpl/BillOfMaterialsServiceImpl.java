package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexBillOfMaterial;
import com.SCM.dto.BillOfMaterialDto;
import com.SCM.dto.BillOfMaterialItemsDto;
import com.SCM.model.BillOfMaterial;
import com.SCM.model.BillOfMaterialItems;
import com.SCM.repository.BillOfMaterialRepo;
import com.SCM.service.BillOfMaterialsService;

@Service
public class BillOfMaterialsServiceImpl implements BillOfMaterialsService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BillOfMaterialRepo bomRepo;

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();
		return uid;
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	
	@Override
	public BillOfMaterialDto saveBom(BillOfMaterialDto bomDto) {
		
		BillOfMaterial bom = this.BomDtoToBom(bomDto);

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

//		List<BillOfMaterialItems> bomItems = this.bomItemsDtoToBomItems(bomDto.getBomItem());
		
		BillOfMaterial lastid = bomRepo.findFirstByOrderByIdDesc();
		
		if(lastid == null)
		{
			int count = 1;
			String bominc= "BOM- ";
			bom.setBomNo(bominc + count);
		}
		else {
			int count = lastid.getId() + 1;
			String bominc = "BOM- ";
			bom.setBomNo(bominc + count);
		}
	
		bom.setCreateddate(LocalDate.now());
		bom.setCreatedtime(LocalTime.now());
		bom.setBomItems(bomDto.getBomItems());
		bom.setCreatebyname(uname);
		bom.setCreatedby(uid);
		bom.setRole(role);

		System.out.println("bom " + bom);
		return this.BomToBomDto(bomRepo.save(bom));
	}

	@Override
	public BillOfMaterialDto getById(int id) {
		
		return null;
	}

	public BillOfMaterialDto BomToBomDto(BillOfMaterial bom) {
		return modelMapper.map(bom, BillOfMaterialDto.class);
	}

	public BillOfMaterial BomDtoToBom(BillOfMaterialDto bomDto) {
		return modelMapper.map(bomDto, BillOfMaterial.class);
	}

	public List<BillOfMaterialItemsDto> bomItemsToBomItemsDto(List<BillOfMaterialItems> bomItemsList) {
		List<BillOfMaterialItemsDto> newBomItemsList = new ArrayList<>();
		bomItemsList.forEach(item -> {
			newBomItemsList.add(modelMapper.map(item, BillOfMaterialItemsDto.class));
		});
		return newBomItemsList;
	}

	public List<BillOfMaterialItems> bomItemsDtoToBomItems(List<BillOfMaterialItemsDto> bomItemsDtoList) {
		
		List<BillOfMaterialItems> newBomItemsList = new ArrayList<>();
		bomItemsDtoList.forEach(item -> {
			newBomItemsList.add(modelMapper.map(item, BillOfMaterialItems.class));
		});
		return newBomItemsList;
	}

	@Override
	public List<BillOfMaterialDto> getBom(int pageNo, int pageSize, String field) {
		List<BillOfMaterialDto> bomDtoList = new ArrayList<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		List<BillOfMaterial> bomList = bomRepo.getForIndex(p);
		bomList.forEach(bom -> {
			bomDtoList.add(this.BomToBomDto(bom));
		});
		return bomDtoList;
	}

	@Override
	public Map<String, Object> IndexBillOfMaterialAsc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = bomRepo.IndexAllBillOfMaterial(p).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexBillOfMaterial> icb = bomRepo.IndexAllBillOfMaterial(p);

		response.put("Index", icb);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexBillOfMaterialDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = bomRepo.IndexAllBillOfMaterial(p).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexBillOfMaterial> icb = bomRepo.IndexAllBillOfMaterial(p);

		response.put("Index", icb);
		response.put("CountPages", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchBillOfMaterial(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = bomRepo.IndexAllBillOfMaterial(p).size();
		long pages = countpages / pagesize;
		List<IndexBillOfMaterial> setbarcode = bomRepo.IndexAllBillOfMaterial(p, search);

		long searchcount = setbarcode.size();

		response.put("data", setbarcode);
		response.put("SearchCount", searchcount);

		return response;
	}

	@Override 
	public BillOfMaterial getById1(int id) {
		BillOfMaterial billOfMaterial = bomRepo.findById(id).get();

		return billOfMaterial;
	}
	
	@Override
	public String deleteById(int id) {
		// TODO Auto-generated method stub
		if (bomRepo.existsById(id)) {
			bomRepo.deleteById(id);
			return"Succesfully deleted the bom with id "+id;
		}
		return"NO bom is present with id "+id;
	}

	@Override
	public BillOfMaterial updateBom(BillOfMaterialDto bomDto,int id) {
		
		BillOfMaterial billOfMaterial = bomRepo.findById(id).get();
		billOfMaterial.setAmount(bomDto.getAmount());
		billOfMaterial.setDescription(bomDto.getDescription());
		billOfMaterial.setRate(bomDto.getRate());
		billOfMaterial.setProduct(bomDto.getProduct());
		billOfMaterial.setBomItems(bomDto.getBomItems());
		
		BillOfMaterial update = bomRepo.save(billOfMaterial);
		
		return update;
	}


}
