package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.IndexDto.IndexVoucher;
import com.SCM.dto.VoucherModelDto;
import com.SCM.model.Voucher;

public interface VoucherService {

	Voucher saveVoucher( VoucherModelDto voucher);
	List<Voucher> getAll();
	Optional<Voucher> getById(long id);
	String updateVoucher(Voucher voucher,long id);
	String deleteVoucher(long id);
	
    public Map<String, Object> IndexVoucherAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexVoucherDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchVoucher(int pageno,int pagesize,String search);
    
    public List<IndexVoucher> getAllVoucher();

}
