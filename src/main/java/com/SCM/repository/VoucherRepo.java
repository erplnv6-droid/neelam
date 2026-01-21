package com.SCM.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SCM.IndexDto.IndexVoucher;
import com.SCM.dto.VoucherModelDto;
import com.SCM.model.Voucher;


public interface VoucherRepo extends JpaRepository<Voucher, Long>{

	@Query(value = "select id,voucherseries from voucher",nativeQuery = true)
	List<IndexVoucher> indexvoucher();
	
	@Query(value = "select id,voucherseries from voucher",nativeQuery = true)
	List<IndexVoucher> indexvoucher(Pageable p);
	
	@Query(value = "select id,voucherseries from voucher where id Like CONCAT('%', :search, '%')"
			+ " or voucherseries Like CONCAT('%', :search, '%')",nativeQuery = true)
	List<IndexVoucher> indexvoucher(String search,Pageable p);
	
	
	@Query(value = "SELECT v.voucherseries\r\n"
			+ "FROM voucher AS v\r\n"
			+ "LEFT JOIN voucher_master AS vm ON v.id = vm.voucher_id\r\n"
			+ "LEFT JOIN purchase_order AS p ON vm.id = p.vouchermaster_id\r\n"
			+ "WHERE p.vouchermaster_id IS NULL",nativeQuery = true)
	List<IndexVoucher> getAll();
}
