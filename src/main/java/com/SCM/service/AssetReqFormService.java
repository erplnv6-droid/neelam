package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.AssetReqFormDto;

public interface AssetReqFormService {

	public AssetReqFormDto save(AssetReqFormDto assetReqFormDto);

	public List<AssetReqFormDto> all();

	public Optional<AssetReqFormDto> getById(long id);

	public String updateAsset(AssetReqFormDto dto, long id);

	public String deleteAsset(long id);

	public Map<String, Object> IndexAssetReqFormAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexAssetReqFormDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchAssetReqForm(int pageno, int pagesize, String search);
}
