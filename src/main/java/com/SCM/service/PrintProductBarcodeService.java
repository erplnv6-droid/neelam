package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.PrintProductBarcodeDto;
import com.SCM.model.PrintProductBarcode;


public interface PrintProductBarcodeService {
	
	public PrintProductBarcodeDto createPrintBarcode(PrintProductBarcodeDto barcodeDto);
	
	public List<PrintProductBarcodeDto> getAllPrintBarcode();
	
	public Optional<PrintProductBarcodeDto> findByPrintBarcode(long id);
	
	public PrintProductBarcodeDto updatePrintProductBarcodeDto(PrintProductBarcodeDto barcodeDto,long id);
	
	public void deletById(long id);
	
	public PrintProductBarcodeDto convertToDto(PrintProductBarcode printProductBarcode);
	
	public PrintProductBarcode convertToEntity(PrintProductBarcodeDto printProductBarcodeDto);

    public Map<String, Object> IndexPrintBarcodeAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexPrintBarcodeDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchPrintBarcode(int pageno,int pagesize,String search);



}
