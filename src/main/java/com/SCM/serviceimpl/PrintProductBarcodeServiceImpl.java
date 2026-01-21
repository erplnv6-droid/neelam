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

import com.SCM.IndexDto.TranporterProjection;
import com.SCM.dto.PrintProductBarcodeDto;
import com.SCM.model.PrintProductBarcode;
import com.SCM.projection.PrintProductProjection;
import com.SCM.repository.PrintProductBarcodeRepository;
import com.SCM.service.PrintProductBarcodeService;


@Service
public class PrintProductBarcodeServiceImpl implements PrintProductBarcodeService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PrintProductBarcodeRepository printProductBarcodeRepository;
	
	
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
	public PrintProductBarcodeDto createPrintBarcode(PrintProductBarcodeDto barcodeDto) {

		PrintProductBarcode convertToEntity = this.convertToEntity(barcodeDto);
		
		convertToEntity.setCreatebyname(getUserName());
		convertToEntity.setCreatedby(getUserId());
		convertToEntity.setCreateddate(LocalDate.now());
		convertToEntity.setCreatedtime(LocalTime.now());
		
		PrintProductBarcode save = printProductBarcodeRepository.save(convertToEntity);
		
		PrintProductBarcodeDto convertToDto = this.convertToDto(save);
		
		return convertToDto;
	}

	@Override
	public List<PrintProductBarcodeDto> getAllPrintBarcode() {
		// TODO Auto-generated method stub
	List<PrintProductBarcode> barcodeDtos=printProductBarcodeRepository.findAll();
	
	List<PrintProductBarcodeDto> collect = barcodeDtos.stream().map(this::convertToDto).collect(Collectors.toList());
	
        return collect;
	}

	@Override
	public Optional<PrintProductBarcodeDto> findByPrintBarcode(long id) {
		// TODO Auto-generated method stub
		
PrintProductBarcode findById = printProductBarcodeRepository.findById(id).get();

PrintProductBarcodeDto convertToDto = this.convertToDto(findById);
		
	 return Optional.of(convertToDto);
	}

	@Override
	public PrintProductBarcodeDto updatePrintProductBarcodeDto(PrintProductBarcodeDto barcodeDto, long id) {
		Long uid = getUserId();
		String uname = getUserName();
		
		PrintProductBarcode printProductBarcode = printProductBarcodeRepository.findById(id).get();
		
		
       printProductBarcode.setBrand(barcodeDto.getBrand());
		printProductBarcode.setProduct(barcodeDto.getProduct());
		printProductBarcode.setNoprint(barcodeDto.getNoprint());
		printProductBarcode.setUpdatedbyname(uname);
		printProductBarcode.setUpdatedby(uid);
		printProductBarcode.setUpdateddate(LocalDate.now());
		printProductBarcode.setUpdatedtime(LocalTime.now());
		
		PrintProductBarcode save = printProductBarcodeRepository.save(printProductBarcode);
		
		PrintProductBarcodeDto convertToDto = this.convertToDto(save);

		
        return convertToDto;
	}

	@Override
	public void deletById(long id) {
		// TODO Auto-generated method stub
		
	printProductBarcodeRepository.deleteById(id);
		
	}

	@Override
	public PrintProductBarcodeDto convertToDto(PrintProductBarcode printProductBarcode) {
		// TODO Auto-generated method stub
		PrintProductBarcodeDto map = this.modelMapper.map(printProductBarcode, PrintProductBarcodeDto.class);
		return map;
	}

	@Override
	public PrintProductBarcode convertToEntity(PrintProductBarcodeDto printProductBarcodeDto) {
		// TODO Auto-generated method stub
		PrintProductBarcode map = this.modelMapper.map(printProductBarcodeDto, PrintProductBarcode.class);
		return map;
	}

	@Override
	public Map<String, Object> IndexPrintBarcodeAsc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		long countpages = printProductBarcodeRepository.count();
		long pages = countpages / pagesize;
        long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

List<PrintProductProjection> ipo = printProductBarcodeRepository.getPrintIndex(p);

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

		long countpages = printProductBarcodeRepository.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

List<PrintProductProjection> ipo = printProductBarcodeRepository.getPrintIndex(p);

		response.put("Index", ipo);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> SearchPrintBarcode(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		List<PrintProductProjection> receipt = printProductBarcodeRepository.getPrintSearch(search, p);

		int size = receipt.size();

		response.put("data", receipt);

		response.put("SearchCount", size);

		return response;
		
	}



}
