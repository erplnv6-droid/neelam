package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
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

import com.SCM.IndexDto.IndexAssetAssignToStaff;

import com.SCM.dto.AssetAssignToStaffDto;
import com.SCM.mapper.AssetAssignToStaffMapper;
import com.SCM.model.Asset;
import com.SCM.model.AssetAssignToStaff;
import com.SCM.repository.AssetAssignToStaffRepo;
import com.SCM.repository.AssetRepository;
import com.SCM.repository.AssetReqFormRepository;
import com.SCM.service.AssetAssignToStaffService;

@Service
public class AssetAssignToStaffServiceImpl implements AssetAssignToStaffService {

	@Autowired
	private AssetAssignToStaffRepo assignToStaffRepo;
	
	@Autowired
	private AssetReqFormRepository assetrepo;

	@Autowired
	private AssetAssignToStaffMapper mapper;

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
	public AssetAssignToStaffDto save(AssetAssignToStaffDto assetAssignToStaffDto) {
		
		long assetid = assetAssignToStaffDto.getAsset().getId();
		Asset asset = assetRepository.findById(assetid).get();
		
		if (asset.getStatus().equals("Available")) {
			
			Long uid = getUserId();
			String uname = getUserName();
			String role = getRolename();

			AssetAssignToStaff entity = mapper.toEntity(assetAssignToStaffDto);
			entity.setCreatebyname(uname);
			entity.setCreatedby(uid);
			entity.setRole(role);

			entity.setCreateddate(LocalDate.now());
			entity.setCreatedtime(LocalTime.now());
			entity.setRemarks(assetAssignToStaffDto.getRemarks());
			AssetAssignToStaff save = assignToStaffRepo.save(entity);
			AssetAssignToStaffDto dto = mapper.toDto(save);

			Asset adto = asset;
			asset.setStatus("Locked");
			assetRepository.save(adto);

			return dto;
		}

		throw new RuntimeException("asset already assign to some else staff please choose diff asset");
	}

	
	@Override
	public List<AssetAssignToStaffDto> all() {
		// TODO Auto-generated method stub
		List<AssetAssignToStaff> findAll = assignToStaffRepo.findAll();
		List<AssetAssignToStaffDto> collect = findAll.stream().map((as) -> mapper.toDto(as))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public Optional<AssetAssignToStaffDto> getById(long id) {
		// TODO Auto-generated method stub
		Optional<AssetAssignToStaff> findById = assignToStaffRepo.findById(id);
		Optional<AssetAssignToStaffDto> map = findById.map((as) -> mapper.toDto(as));
		return map;
	}

	@Autowired
	private AssetRepository assetRepository;

	@Override
	public String updateAsset(AssetAssignToStaffDto assetAssignToStaffDto, long id) {
		// TODO Auto-generated method stub

		long assetid = assetAssignToStaffDto.getAsset().getId();
		Asset asset = assetRepository.findById(assetid).get();
		AssetAssignToStaff assetAssignToStaff = assignToStaffRepo.findById(id).get();
		long id3 = assetAssignToStaff.getAsset().getId();
		System.out.println(asset.getId());
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println(id3);
		if (asset.getId() == id3) {

			AssetAssignToStaff entity = assignToStaffRepo.findById(id).get();

			AssetAssignToStaff dto = entity;
//			here 
//			entity.set.
			entity.setStaffid(assetAssignToStaffDto.getStaffid());
			entity.setRemarks(assetAssignToStaffDto.getRemarks());
			assignToStaffRepo.save(dto);
			return "updated succesfully same asset id ";

		}

		if (asset.getId() != id3 && asset.getStatus().equals("Available")) {

			AssetAssignToStaff assetr = assignToStaffRepo.findById(id).get();
			long id2 = assetr.getAsset().getId();

			Asset asset2 = assetRepository.findById(id2).get();
			Asset ast3 = asset2;
			asset2.setStatus("Available");
			assetRepository.save(ast3);

			AssetAssignToStaff entity = assignToStaffRepo.findById(id).get();
			AssetAssignToStaff dto = entity;

//			here
//			entity.setcre.....
			entity.setRemarks(assetAssignToStaffDto.getRemarks());
			entity.setAsset(assetAssignToStaffDto.getAsset());
			assignToStaffRepo.save(entity);

			Asset adto = asset;
			asset.setStatus("Locked");
			assetRepository.save(adto);
			return "Succesfully updated" + id;

		}

//		if (asset.getStatus().equals("Available")) {
//			if (assignToStaffRepo.existsById(id)) {
//				
//				
////				
//				AssetAssignToStaff assetr = assignToStaffRepo.findById(id).get();
//				long id2 = assetr.getAsset().getId();
//				if (id2!=assetAssignToStaffDto.getAsset().getId()) {
//					Asset asset2 = assetRepository.findById(id2).get();
//					Asset ast3=asset2;
//					asset2.setStatus("Available");
//					assetRepository.save(ast3);
//					AssetAssignToStaff entity = assignToStaffRepo.findById(id).get();
//					AssetAssignToStaff dto=entity;
//					entity.setRemarks(assetAssignToStaffDto.getRemarks());
//					entity.setAsset(assetAssignToStaffDto.getAsset());
//					assignToStaffRepo.save(entity);
//					
//					Asset adto=asset;
//					asset.setStatus("Locked");
//					assetRepository.save(adto);
//					return "Succesfully updated"+id;
//				}
//				
////				
//				AssetAssignToStaff entity = assignToStaffRepo.findById(id).get();
//				AssetAssignToStaff dto=entity;
//				entity.setRemarks(assetAssignToStaffDto.getRemarks());
//				entity.setAsset(assetAssignToStaffDto.getAsset());
//				assignToStaffRepo.save(entity);
//				
//				Asset adto=asset;
//				asset.setStatus("Locked");
//				assetRepository.save(adto);
//				return "Succesfully updated"+id;
//					
//				}
//		}

		return "some error occured while updating the data " + id;
	}

	@Override
	public String deleteAsset(long id) {
		if (assignToStaffRepo.existsById(id)) {
			assignToStaffRepo.deleteById(id);
			return "deleted succesfully with id " + id;
		}
		// TODO Auto-generated method stub
		return "no asset is present with this is " + id;
	}

	@Override
	public Map<String, Object> IndexAssetAssignToStaffAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = assignToStaffRepo.count();
		long pages = countpages / pagesize;

		List<IndexAssetAssignToStaff> ipo = assignToStaffRepo.indexAssetAssignToStaff(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexAssetAssignToStaffDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = assignToStaffRepo.count();
		long pages = countpages / pagesize;

		List<IndexAssetAssignToStaff> ipo = assignToStaffRepo.indexAssetAssignToStaff(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchAssetAssignToStaff(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = assignToStaffRepo.count();
		long pages = countpages / pagesize;

		List<IndexAssetAssignToStaff> ipo = assignToStaffRepo.indexAssetAssignToStaff(p,search);

		long searchcount = ipo.size();

		response.put("data", ipo);
		response.put("SearchCount", searchcount);

		return response;
	}

}
