package com.SCM.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SCM.IndexDto.IndexVoucherMaster;
import com.SCM.model.VoucherMaster;

@Repository
public interface VoucherMasterRepo extends JpaRepository<VoucherMaster, Integer> {	
	
	
	@Query(value="select vm.id,v.voucherseries,vm.subtype,vm.startingnumber,vm.width,vm.prefil,\r\n"
			+ "            vm.prefixapplicabledate,vm.prefixparticular,vm.suffixapplicabledate,vm.suffixparticular,vm.restartapplicabledate,\r\n"
			+ "            vm.restartnumber,vm.status\r\n"
			+ "            from voucher_master as vm left join voucher as v on vm.voucher_id=v.id",nativeQuery = true)
	List<IndexVoucherMaster> getIndexVoucherMaster(Pageable p);
	
	
	@Query(value="select vm.id,v.voucherseries,vm.subtype,vm.startingnumber,vm.width,vm.prefil,\r\n"
			+ "            vm.prefixapplicabledate,vm.prefixparticular,vm.suffixapplicabledate,vm.suffixparticular,vm.restartapplicabledate,\r\n"
			+ "            vm.restartnumber,vm.status\r\n"
			+ "            from voucher_master as vm left join voucher as v on vm.voucher_id=v.id"
			+ " where vm.id like CONCAT('%',:search,'%') or v.voucherseries like CONCAT('%',:search,'%') or vm.subtype like CONCAT('%',:search,'%') or vm.status like CONCAT('%',:search,'%')",nativeQuery = true)
	List<IndexVoucherMaster> getIndexVoucherMasterSearch(String search,Pageable p);
	
	
	

	@Modifying
	@Transactional
	@Query(value = "UPDATE voucher_master vm JOIN (SELECT MAX(id) AS max_id FROM voucher_master WHERE subtype = 'sale') AS latest_sale ON vm.id < latest_sale.max_id AND vm.subtype = 'sale'\r\n"
			+ "left join voucher as v on vm.voucher_id=v.id \r\n"
			+ "SET vm.status = 'Inactive'", nativeQuery = true)
	int updatePreviousSaleToInactive();
	
	
//	@Modifying
//	@Transactional
//	@Query(value = "UPDATE voucher_master vm JOIN (SELECT MAX(id) AS max_id FROM voucher_master WHERE subtype = 'po') AS latest_sale ON vm.id < latest_sale.max_id AND vm.subtype = 'po'\r\n"
//			+ "left join voucher as v on vm.voucher_id=v.id \r\n"
//			+ "SET vm.status = 'Inactive'", nativeQuery = true)
//	int updatePreviousPoToInactive();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE voucher_master vm\r\n"
			+ "JOIN (\r\n"
			+ "    SELECT v.voucherseries, v.id AS voucher_id, MAX(vm.id) AS max_id\r\n"
			+ "    FROM voucher_master vm\r\n"
			+ "    JOIN voucher v ON vm.voucher_id = v.id\r\n"
			+ "    GROUP BY v.voucherseries, v.id\r\n"
			+ ") AS latest_voucher \r\n"
			+ "ON vm.id < latest_voucher.max_id\r\n"
			+ "AND vm.voucher_id = latest_voucher.voucher_id\r\n"
			+ "JOIN voucher v ON vm.voucher_id = v.id\r\n"
			+ "SET vm.status = 'Inactive'\r\n"
			+ "", nativeQuery = true)
	int updatePreviousPoToInactive();
	
	
	
	
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='PO'",nativeQuery = true)
	List<VoucherMaster> getAll();

	@Query(value="select * from voucher_master where status = 'Active' and subtype='SDN'",nativeQuery = true)
	List<VoucherMaster> getAllSdn();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='MRN'",nativeQuery = true)
	List<VoucherMaster> getAllMrn();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='SO'",nativeQuery = true)
	List<VoucherMaster> getAllSo();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='PR'",nativeQuery = true)
	List<VoucherMaster> getAllPr();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='PURCHASE'",nativeQuery = true)
	List<VoucherMaster> getAllPurchase();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='DC'",nativeQuery = true)
	List<VoucherMaster> getAllDc();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='SALE'",nativeQuery = true)
	List<VoucherMaster> getAllSale();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='SR'",nativeQuery = true)
	List<VoucherMaster> getAllSr();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='DO'",nativeQuery = true)
	List<VoucherMaster> getAllDo();
	
	@Query(value="select * from voucher_master where status = 'Active' and subtype='DI'",nativeQuery = true)
	List<VoucherMaster> getAllDi();
	
	
	
	
	

}
