package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.IndexDto.FetchBomProductIndex;
import com.SCM.dto.MasterCartoonDto;
import com.SCM.model.BillOfMaterial;
import com.SCM.model.MasterCartoon;
import com.google.zxing.WriterException;

public interface MasterCartoonService {

	public MasterCartoonDto saveMasterCartoon(MasterCartoonDto cartoonDto);

	public Optional<MasterCartoonDto> getById(long id);

	public List<MasterCartoonDto> getAll();

	public String updateNormal(long id, MasterCartoonDto cartoonDto);

	public String updateMasterCartoon(long id, MasterCartoonDto cartoonDto) throws WriterException, IOException;

	public String deleteMasterCartoon(long id);

	public List<MasterCartoon> getByBrand(long id);

	public Map<String, Object> IndexMasterCartonAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexMasterCartonDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchMasterCarton(int pageno, int pagesize, String search);
	
	public List<FetchBomProductIndex> fetchProductfromBom(long bid);
 
}
