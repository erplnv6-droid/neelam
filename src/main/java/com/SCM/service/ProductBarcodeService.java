package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.IndexDto.SetBarcodePrintIndex;
import com.SCM.dto.ProductBarcodeDto;
import com.SCM.model.ProductBarcode;
import com.google.zxing.WriterException;

public interface ProductBarcodeService {

	ProductBarcodeDto saveProductBarcode(ProductBarcodeDto dto) throws WriterException, IOException;
	
	List<ProductBarcodeDto> getAllProductBarcode();
		
	Optional<ProductBarcodeDto> getProductBarcodeById(long id);
	
	String updateProductBarcode(ProductBarcodeDto barcodeDto,long id) throws IOException, WriterException ;
	
	String deleteProductBarcode(long id);
	
	public byte[] getBarCodeById(long id) throws IOException;
	
	public ProductBarcode generateQrCode(ProductBarcodeDto dto) throws WriterException, IOException;
	
	public byte[] getBarCodeByName(String name) throws IOException;
	
	
//---------------------- index 
	
	public Map<String, Object> IndexAllProductBarcodeAsc(int pageno, int pagesize, String field);
	
	public Map<String, Object> IndexAllProductBarcodeDesc(int pageno, int pagesize, String field);
	
	public Map<String, Object> SearchAllProductBarcode(int pageno, int pagesize, String search);
	
	ProductBarcode getByProductAndBrand(Long productid,Long brandid);
	
	public List<SetBarcodePrintIndex> fetchProductBarcodeProduct(int bid);
}
