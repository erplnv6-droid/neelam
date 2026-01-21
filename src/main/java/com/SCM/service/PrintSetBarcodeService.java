package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.SCM.dto.PrintSetBarcodeDto;

import com.SCM.model.PrintSetBarcode;

public interface PrintSetBarcodeService {
	
	public PrintSetBarcodeDto createPrintBarcode(PrintSetBarcodeDto barcodeDto);
	
	public List<PrintSetBarcodeDto> getAllPrintBarcode();
	
	public Optional<PrintSetBarcodeDto> findByPrintBarcode(long id);
	
	public PrintSetBarcodeDto updatePrintSetBarcodeDto(PrintSetBarcodeDto barcodeDto,long id);
	
	public void deletById(long id);
	
	public PrintSetBarcodeDto convertToDto(PrintSetBarcode printSetBarcode);
	
	public PrintSetBarcode convertToEntity(PrintSetBarcodeDto printSetBarcodeDto);

    public Map<String, Object> IndexPrintBarcodeAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> IndexPrintBarcodeDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> SearchPrintBarcode(int pageno,int pagesize,String search);

}
