package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexAsset;
import com.SCM.IndexDto.IndexRetailer;
import com.SCM.dto.AssetDto;
import com.SCM.mapper.AssetMapper;
import com.SCM.model.Asset;
import com.SCM.repository.AssetRepository;
import com.SCM.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

	private static final String FOLDER_PATH = "D:/ImgPath/";

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	private final Path fileStorageLocation;

	public AssetServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir9", "/assets/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}

	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private AssetMapper mapper;

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
	public AssetDto save(AssetDto dto, MultipartFile inscfile) throws IOException {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Asset entiy = mapper.toEntiy(dto);
		entiy.setCreatedby(uid);
		entiy.setCreatedbyname(uname);
		entiy.setRole(role);
		entiy.setCreateddate(LocalDate.now());
		entiy.setCreatedtime(LocalTime.now());
		entiy.setDateofpurchase(LocalDate.now());
		entiy.setStatus("Available");
		Asset save = assetRepository.save(entiy);

		String filepath = "asset_" + save.getId() + "_" + inscfile.getOriginalFilename();

		if (inscfile.isEmpty()) {
			entiy.setInsurancefilename(null);
			entiy.setInsurancefilelocation(null);

			assetRepository.save(entiy);
		}
		if (!inscfile.isEmpty()) {

			entiy.setInsurancefilename(inscfile.getOriginalFilename());
			entiy.setInsurancefilelocation("assets/" + filepath);

			Files.copy(inscfile.getInputStream(), fileStorageLocation.resolve(filepath),StandardCopyOption.REPLACE_EXISTING);

			assetRepository.save(entiy);
		}

		AssetDto dto2 = mapper.toDto(save);
		return dto2;
	}

	@Override
	public String update(long id, AssetDto assetDto, MultipartFile inscfile) throws IOException {

		System.out.println("Enter ++++++++++++++++++++ ............");
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Asset asset = assetRepository.findById(id).get();

		Asset entity = mapper.toEntiy(assetDto);
		Asset entity2 = entity;
		entity.setId(id);
		entity.setSupplier(assetDto.getSupplier());
		entity.setUpdatedby(uid);
		entity.setUpdatedbyname(uname);
		entity.setUpdateddate(LocalDate.now());
		entity.setUpdatedtime(LocalTime.now());
		entity.setUpdatedrole(role);
		
		Asset update = assetRepository.save(entity2);
		
		System.out.println(update.getAssetsname());

		String filepath = "asset_" + update.getId() + "_" + inscfile.getOriginalFilename();

		String insurancefilelocation = asset.getInsurancefilelocation();

		if (inscfile.isEmpty()) {

			entity.setInsurancefilename(entity.getInsurancefilename());
			entity.setInsurancefilelocation(entity.getInsurancefilelocation());

			assetRepository.save(entity);
		}
		if (!inscfile.isEmpty()) {
			try {
				Path path = Paths.get(insurancefilelocation);

				System.out.println(path + "+++++++++++++++");

				Files.delete(path);
			} catch (Exception e) {
				e.printStackTrace();
			}

			entity.setInsurancefilename(inscfile.getOriginalFilename());
			entity.setInsurancefilelocation("assets/" + filepath);

			Files.copy(inscfile.getInputStream(), fileStorageLocation.resolve(filepath),
					StandardCopyOption.REPLACE_EXISTING);

			assetRepository.save(entity);
		}

		assetRepository.save(entity2);

		return "succesfully updated assets with id " + id;

	}

	@Override
	public String delete(long id) {
		if (assetRepository.existsById(id)) {
			assetRepository.deleteById(id);
			return "succesfully deleted the asset with id " + id;
		}
		return "no asset is present with id " + id;
	}

	@Override
	public List<AssetDto> getAll() {
		List<Asset> asset = assetRepository.findAll();
		List<AssetDto> collect = asset.stream().map((as) -> mapper.toDto(as)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public Optional<AssetDto> getById(long id) {
		Optional<Asset> optional = assetRepository.findById(id);
		Optional<AssetDto> map = optional.map((as) -> mapper.toDto(as));
		return map;
	}

	@Override
	public Map<String, Object> IndexAssetAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = assetRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexAsset> indexassets = assetRepository.indexassets(p);

		response.put("Index", indexassets);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexAssetDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = assetRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<IndexAsset> indexassets = assetRepository.indexassets(p);

		response.put("Index", indexassets);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchAsset(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		long countpages = assetRepository.count();
		long pages = countpages / pagesize;

		List<IndexAsset> ipo = assetRepository.searchassets(search, p);

		long searchcount = ipo.size();

		response.put("data", ipo);
		response.put("SearchCount", searchcount);

		return response;
	}

//	 update

	@Override
	public String update1(long id, AssetDto assetDto, MultipartFile inscfile) throws IOException {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		String pdffolderpath = "asset_" + assetDto.getId() + inscfile.getOriginalFilename();
		Asset asset = assetRepository.findById(id).get();

		asset.setAssetsname(assetDto.getAssetsname());
		asset.setInsuranceprovidername(assetDto.getInsuranceprovidername());
		asset.setInsurancepolicyno(assetDto.getInsurancepolicyno());
		asset.setRemarks(assetDto.getRemarks());
		asset.setMrp(assetDto.getMrp());
		asset.setSrno(assetDto.getSrno());
		asset.setExpiryDate(assetDto.getExpiryDate());
		asset.setSupplier(assetDto.getSupplier());
		asset.setUpdatedby(uid);
		asset.setUpdatedbyname(uname);
		asset.setUpdateddate(LocalDate.now());
		asset.setUpdatedtime(LocalTime.now());
		asset.setUpdatedrole(role);

		Asset save = assetRepository.save(asset);

		if (inscfile.isEmpty()) {

			asset.setInsurancefilename(asset.getInsurancefilename());
			asset.setInsurancefilelocation(asset.getInsurancefilelocation());

			assetRepository.save(asset);
		}
		if (!inscfile.isEmpty()) {

			try {

				Path docpath = Paths.get(FILE_PATH + save.getInsurancefilelocation());
				Files.delete(docpath);

			} catch (Exception e) {
				e.printStackTrace();
			}

			asset.setInsurancefilename(inscfile.getOriginalFilename());
			asset.setInsurancefilelocation("assets/" + pdffolderpath);

			Files.copy(inscfile.getInputStream(), fileStorageLocation.resolve(pdffolderpath),
					StandardCopyOption.REPLACE_EXISTING);

			assetRepository.save(asset);
		}

		return null;
	}

}
