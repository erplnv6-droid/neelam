package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.model.VoucherMaster;

public interface VoucherMasterService {

	public VoucherMaster save(VoucherMaster voucherMaster);
	
	public List<VoucherMaster> get();
	
	public List<VoucherMaster> getSdn();
	
	public List<VoucherMaster> getMrn();
	
	public List<VoucherMaster> getPurchase();
	
	public List<VoucherMaster> getPr();
	
	public List<VoucherMaster> getSo();
	
	public List<VoucherMaster> getDc();
	
	public List<VoucherMaster> getSale();
	
	public List<VoucherMaster> getSr();
	
	public List<VoucherMaster> getDo();
	
	public List<VoucherMaster> getDi();
	
	
	
	public Optional<VoucherMaster> getById(int id);
	
	public void deleteById(int id);
	
	Map<String, Object> indexVoucherAsc(int pageno, int pagesize, String field);

	Map<String, Object> indexVoucherDesc(int pageno, int pagesize, String field);

	Map<String, Object> searchByVouchers(int pageno, int pagesize, String search);
}
