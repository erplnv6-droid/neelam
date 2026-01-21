package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.dto.PrintSetBarcodeDto;
import com.SCM.model.PrintSetBarcode;

import com.SCM.projection.PrintSetProjection;
import com.SCM.repository.PrintSetBarcodeRepository;
import com.SCM.service.PrintSetBarcodeService;

@Service
public class PrintSetBarcodeServiceImpl implements PrintSetBarcodeService {
	
	@Autowired
	private PrintSetBarcodeRepository printSetBarcodeRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public long getUserId()
	{
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl detailsImpl=(UserDetailsImpl) authentication.getPrincipal();
		Long uid=detailsImpl.getId();
		return uid;
		
	}
	
	public String getUserName()
	{
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl detailsImpl=(UserDetailsImpl) authentication.getPrincipal();
		String username=detailsImpl.getUsername();
		return username;
	}
	
	
	

	@Override
	public PrintSetBarcodeDto createPrintBarcode(PrintSetBarcodeDto barcodeDto) {

		
		PrintSetBarcode convertToEntity = this.convertToEntity(barcodeDto);
		
		convertToEntity.setCreatebyname(getUserName());
		convertToEntity.setCreatedby(getUserId());
		convertToEntity.setCreateddate(LocalDate.now());
		convertToEntity.setCreatedtime(LocalTime.now());
		
		PrintSetBarcode save = printSetBarcodeRepository.save(convertToEntity);
		
		PrintSetBarcodeDto convertToDto = this.convertToDto(save);
		
		return convertToDto;
	}

	@Override
	public List<PrintSetBarcodeDto> getAllPrintBarcode() {
	
	List<PrintSetBarcode> collect = printSetBarcodeRepository.findAll();
	
	List<PrintSetBarcodeDto> collect2 = collect.stream().map(this::convertToDto).collect(Collectors.toList());
	
return collect2;
	}

	@Override
	public Optional<PrintSetBarcodeDto> findByPrintBarcode(long id) {
  
		PrintSetBarcode findById = printSetBarcodeRepository.findById(id).get();
		
		
		PrintSetBarcodeDto convertToDto = this.convertToDto(findById);
		
		
		return Optional.of(convertToDto);
	}

	@Override
	public PrintSetBarcodeDto updatePrintSetBarcodeDto(PrintSetBarcodeDto barcodeDto, long id) {
		// TODO Auto-generated method stub
		
		Long uid = getUserId();
		String uname = getUserName();
		
		PrintSetBarcode findById = printSetBarcodeRepository.findById(id).get();
		
		findById.setBrand(barcodeDto.getBrand());
		findById.setProduct(barcodeDto.getProduct());
		findById.setNoprint(barcodeDto.getNoprint());
		findById.setUpdatedbyname(uname);
		findById.setUpdatedby(uid);
		findById.setUpdateddate(LocalDate.now());
		findById.setUpdatedtime(LocalTime.now());
		
		
		PrintSetBarcode save = printSetBarcodeRepository.save(findById);
		
		PrintSetBarcodeDto convertToDto = convertToDto(save);
		
		return convertToDto;
	}

	@Override
	public void deletById(long id) {
		// TODO Auto-generated method stub
		printSetBarcodeRepository.deleteById(id);
		
	}

	@Override
	public PrintSetBarcodeDto convertToDto(PrintSetBarcode printSetBarcode) {
		// TODO Auto-generated method stub
		PrintSetBarcodeDto map = this.modelMapper.map(printSetBarcode, PrintSetBarcodeDto.class);
		return map;
	}

	@Override
	public PrintSetBarcode convertToEntity(PrintSetBarcodeDto printSetBarcodeDto) {
		// TODO Auto-generated method stub
		PrintSetBarcode map = this.modelMapper.map(printSetBarcodeDto, PrintSetBarcode.class);
		return map;
	}

	@Override
	public Map<String, Object> IndexPrintBarcodeAsc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = printSetBarcodeRepository.count();
		long pages = countpages / pagesize;
        long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

List<PrintSetProjection> ipo = printSetBarcodeRepository.getPrintIndex(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> IndexPrintBarcodeDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = printSetBarcodeRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

List<PrintSetProjection> ipo = printSetBarcodeRepository.getPrintIndex(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchPrintBarcode(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		List<PrintSetProjection> receipt = printSetBarcodeRepository.getPrintSearch(search, p);

		int size = receipt.size();

		response.put("data", receipt);

		response.put("SearchCount", size);

		return response;
		
	}

	
	
}
