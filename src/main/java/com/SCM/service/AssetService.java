package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.dto.AssetDto;

public interface AssetService {

	public AssetDto save(AssetDto dto, MultipartFile inscfile) throws IOException;

	public String update(long id, AssetDto assetDto, MultipartFile inscfile) throws IOException;
	
	public String update1(long id, AssetDto assetDto, MultipartFile inscfile) throws IOException;

	public String delete(long id);

	public List<AssetDto> getAll();

	public Optional<AssetDto> getById(long id);

	public Map<String, Object> IndexAssetAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexAssetDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchAsset(int pageno, int pagesize, String search);

}
