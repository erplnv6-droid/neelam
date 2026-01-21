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

import com.SCM.IndexDto.IndexAsset;
import com.SCM.IndexDto.IndexAssetReqForm;
import com.SCM.dto.AssetReqFormDto;
import com.SCM.mapper.AssetReqFormMapper;
import com.SCM.model.AssetRequestForm;
import com.SCM.repository.AssetReqFormRepository;
import com.SCM.service.AssetReqFormService;

@Service
public class AssetReqFormServiceImpl implements AssetReqFormService {

	@Autowired
	private AssetReqFormRepository assetrepo;

	@Autowired
	private AssetReqFormMapper mapper;

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
	public AssetReqFormDto save(AssetReqFormDto assetReqFormDto) {

		AssetRequestForm entity = mapper.toEntity(assetReqFormDto);
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		entity.setStaffid(uid);
		entity.setStaffname(uname);
		entity.setDate(LocalDate.now());
		entity.setStatus("pending");
		entity.setStaff(assetReqFormDto.getStaff());
		entity.setRole(role);

		entity.setCreatebyname(uname);
		entity.setCreatedby(uid);
		entity.setRole(role);
		entity.setCreateddate(LocalDate.now());
		entity.setCreatedtime(LocalTime.now());

		AssetRequestForm save = assetrepo.save(entity);
		AssetReqFormDto dto = mapper.toDto(save);
		return dto;
	}

	@Override
	public List<AssetReqFormDto> all() {

		List<AssetRequestForm> findAll = assetrepo.findAll();
		List<AssetReqFormDto> collect = findAll.stream().map((ar) -> mapper.toDto(ar)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public Optional<AssetReqFormDto> getById(long id) {

		Optional<AssetRequestForm> findById = assetrepo.findById(id);
		Optional<AssetReqFormDto> dto = findById.map((as) -> mapper.toDto(as));
		return dto;
	}

	@Override
	public String updateAsset(AssetReqFormDto dto, long id) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		if (assetrepo.existsById(id)) {
			AssetRequestForm entity1 = assetrepo.findById(id).get();
			AssetRequestForm dto1 = entity1;
			dto1.setRemarks(dto.getRemarks());
			dto1.setFormdate(dto.getFormdate());
			dto1.setStaff(dto.getStaff());

			dto1.setUpdatedbyname(uname);
			dto1.setUpdatedby(uid);
			dto1.setUpdatedrole(role);
			dto1.setUpdateddate(LocalDate.now());
			dto1.setUpdatedtime(LocalTime.now());

			assetrepo.save(entity1);
			return "succesfully update assetreqform with id " + id;
		}
		return "no asset req form is present with id " + id;
	}

	@Override
	public String deleteAsset(long id) {

		if (assetrepo.existsById(id)) {
			assetrepo.deleteById(id);
			return "Succesfully deleted the asset with id " + id;
		}
		return "no asset req form is present with id " + id;
	}

	@Override
	public Map<String, Object> IndexAssetReqFormAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqForm(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}
			if (s.equals("ROLE_RSM")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqFormByRsm(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}
			if (s.equals("ROLE_ASM")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqFormByAsm(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}
			if (s.equals("ROLE_ASE")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqFormByAse(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}		
	  }
		return response;
	}

	
	@Override
	public Map<String, Object> IndexAssetReqFormDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqForm(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}
			if (s.equals("ROLE_RSM")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqFormByRsm(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}
			if (s.equals("ROLE_ASM")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqFormByAsm(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}
			if (s.equals("ROLE_ASE")) {

				long countpages = assetrepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqFormByAse(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);
				return response;
			}		
	  }
		return response;

	}

	@Override
	public Map<String, Object> SearchAssetReqForm(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = assetrepo.count();
		long pages = countpages / pagesize;

		List<IndexAssetReqForm> ipo = assetrepo.indexAssetReqForm(p);

		long searchcount = ipo.size();

		response.put("data", ipo);
		response.put("SearchCount", searchcount);

		return response;
	}

}
