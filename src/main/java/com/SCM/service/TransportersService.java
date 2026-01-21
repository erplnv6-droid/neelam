package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.TransportersDto;
import com.SCM.model.Transporters;

public interface TransportersService {

	
	public Transporters saveTransporters(TransportersDto transportersDto);
	
	public List<Transporters> getAllTransporters();
	
	public Optional<Transporters> getById(long id);
	
	public Transporters updateTransporters(TransportersDto transportersDto,long id);
	
	public void deleteById(long id);

	Map<String, Object> indexTransporterAsc(int pageno, int pagesize, String field);

	Map<String, Object> indexTransporterDesc(int pageno, int pagesize, String field);

	Map<String, Object> searchByTransporetes(int pageno, int pagesize, String search);
}
