package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.IndexDto.SetBarcodePrintIndex;
import com.SCM.dto.SetBarcodeDto;
import com.SCM.model.SetBarcode;
import com.google.zxing.WriterException;

public interface SetBarcodeService {

	SetBarcode saveSetBarcode(SetBarcodeDto setBarcodeDto) throws WriterException, IOException;

	Optional<SetBarcode> getById(long id);

	List<SetBarcode> getAllSetBarcode();

	String updateSetBarcode(SetBarcodeDto barcodeDto, long id) throws WriterException, IOException;

	String deleteSetBarcode(long id);

	SetBarcode getByProductAndBarcode(Long product, Long brand);

	public Map<String, Object> IndexSetBarcodeAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexSetBarcodeDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchSetBarcode(int pageno, int pagesize, String search);
	
	
	public List<SetBarcodePrintIndex> fetchSetbarcodeProduct(int bid);
}
