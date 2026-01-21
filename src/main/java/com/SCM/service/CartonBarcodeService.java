package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.CartonBarcodeDto;
import com.SCM.model.CartonBarcode;
import com.google.zxing.WriterException;

public interface CartonBarcodeService {

	public CartonBarcodeDto save(CartonBarcodeDto dto) throws WriterException, IOException;

	public List<CartonBarcodeDto> getAll();

	public Optional<CartonBarcodeDto> getById(long id);

	public String updateCarton(CartonBarcodeDto dto, long id) throws WriterException, IOException;

	public String deleteCarton(long id);

	Optional<CartonBarcode> getByProductAndBrand(long productid, long brandid);

	public Map<String, Object> IndexCartonBarcodeAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexCartonBarcodeDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchCartonBarcode(int pageno, int pagesize, String search);

}
