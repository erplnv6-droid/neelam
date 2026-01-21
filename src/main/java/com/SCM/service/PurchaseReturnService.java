package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.PurchaseReturnDto;
import com.SCM.model.PurchaseReturn;

public interface PurchaseReturnService {

	public PurchaseReturn savePurchaseReturn(PurchaseReturnDto purchaseReturnDto);

	public List<PurchaseReturn> getAllPurchaseReturn();

	public PurchaseReturn getPurchaseReturnById(int id);

	void deletePurchaseReturn(int id);

	public PurchaseReturnDto updatePurchaseReturn(PurchaseReturnDto purchaseReturnDto, int id);

	public Map<String, Object> indexPurchaseReturnAsc(int pageno, int pagesize, String field);

	public Map<String, Object> indexPurchaseReturnDesc(int pageno, int pagesize, String field);

	public Map<String, Object> searchByPurchaseReturn(int pageno, int pagesize, String search);
}
