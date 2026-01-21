package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.DistributorMinimumStockIndex;
import com.SCM.IndexDto.OutwardDistributorIndexDto;
import com.SCM.dto.OutwardDistributorStockDto;
import com.SCM.model.OutwardDistributorStock;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.OutwardDistributorStockProjection;
import com.SCM.repository.OutwardDistStockRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.OutwardDistStockService;

@Service
public class OutwardDistributorStockServiceImpl implements OutwardDistStockService {
 
	@Autowired
	private OutwardDistStockRepo outwardDistStockRepo;
	
	@Autowired
	private VoucherMasterRepo voucherMasterRepo;

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();
		return uid;
	}

	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return username;
	}

	public String getRolename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	
	@Override
	public OutwardDistributorStock save(OutwardDistributorStockDto distributorStockDto) {
		
		VoucherMaster voucher=voucherMasterRepo.findById(distributorStockDto.getVoucherMaster().getId()).get();

		OutwardDistributorStock distributorStock = new OutwardDistributorStock();
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		distributorStock.setCreatebyname(uname);
		distributorStock.setCreatedby(uid);
		distributorStock.setRole(role);

		distributorStock.setOutdisdate(distributorStockDto.getOutdisdate());
		distributorStock.setDistributoroid(distributorStockDto.getDistributoroid());
		distributorStock.setRetailerid(distributorStockDto.getRetailerid());
		distributorStock.setRetailer(distributorStockDto.getRetailer());
		distributorStock.setInvoiceno(distributorStockDto.getInvoiceno());
		distributorStock.setGrandtotal(distributorStockDto.getGrandtotal());
		distributorStock.setGrossamount(distributorStockDto.getGrossamount());
		distributorStock.setIgst(distributorStockDto.getIgst());
		distributorStock.setSgst(distributorStockDto.getSgst());
		distributorStock.setCgst(distributorStockDto.getCgst());
		distributorStock.setRoundvalue(distributorStockDto.getRoundvalue());
		distributorStock.setUom(distributorStockDto.getUom());
		distributorStock.setRefno(distributorStockDto.getRefno());
		distributorStock.setGrandtotal(distributorStockDto.getGrandtotal());
		distributorStock.setDistributorStockItems(distributorStockDto.getDistributorStockItems());
		distributorStock.setCreateddate(LocalDate.now());
		distributorStock.setCreatedtime(LocalTime.now());
		distributorStock.setVoucherMaster(voucher);
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			OutwardDistributorStock topByVoucherOrderByStartnumberwithprefilnoDesc = outwardDistStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			OutwardDistributorStock topByVoucherOrderByStartnumberwithprefilyesDesc = outwardDistStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			OutwardDistributorStock lastrowstatus = outwardDistStockRepo.lastrowstatus();
			OutwardDistributorStock lastrowstatus = outwardDistStockRepo.findTopByVoucherMasterOrderByIdDesc(voucher);
			


			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				distributorStock.setStartnumberwithprefilno(startingnumber);
				distributorStock.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					distributorStock.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					distributorStock.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					distributorStock.setStartnumberwithprefilno(restartnumber + 1);
					distributorStock.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					distributorStock.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					distributorStock.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					distributorStock.setStartnumberwithprefilyes(formattedStartingNumber);
					distributorStock.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					distributorStock.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					distributorStock.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					distributorStock.setStartnumberwithprefilyes(restartnumberinc);
					distributorStock.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					distributorStock.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					distributorStock.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			OutwardDistributorStock topByVoucherOrderByStartnumberwithprefilnoDesc = outwardDistStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			OutwardDistributorStock topByVoucherOrderByStartnumberwithprefilyesDesc = outwardDistStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			OutwardDistributorStock lastrowstatus = outwardDistStockRepo.lastrowstatus();
			OutwardDistributorStock lastrowstatus = outwardDistStockRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				distributorStock.setStartnumberwithprefilno(startingnumber);
				distributorStock.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				distributorStock.setStartnumberwithprefilyes(formattedStartingNumber);
				distributorStock.setVoucherstatus("startnostatus");
			}
		}

		OutwardDistributorStock save = outwardDistStockRepo.save(distributorStock);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		distributorStock.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		outwardDistStockRepo.save(distributorStock);

		
		

		return outwardDistStockRepo.save(save);
	}

	@Override
	public List<OutwardDistributorStock> getAllOutwardDistributorStock() {

		return outwardDistStockRepo.findAll();
	}
	
	
	
	

	@Override
	public OutwardDistributorStock getOutwardDistributorStockById(int id) {

		OutwardDistributorStock distributorStock = outwardDistStockRepo.findById(id).get();

		return distributorStock;
	}

	@Override
	public OutwardDistributorStock updateOutwardDistributorStock(OutwardDistributorStockDto distributorStockDto,int id) {

		OutwardDistributorStock distributorStock = outwardDistStockRepo.findById(id).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		distributorStock.setUpdatedbyname(uname);
		distributorStock.setUpdatedby(uid);
		distributorStock.setUpdatedrole(role);
		distributorStock.setUpdateddate(LocalDate.now());
		distributorStock.setUpdatedtime(LocalTime.now());

		distributorStock.setOutdisdate(distributorStockDto.getOutdisdate());
		distributorStock.setDistributoroid(distributorStockDto.getDistributoroid());
		distributorStock.setRetailerid(distributorStockDto.getRetailerid());
		distributorStock.setRetailer(distributorStockDto.getRetailer());
		distributorStock.setInvoiceno(distributorStockDto.getInvoiceno());
		distributorStock.setRefno(distributorStockDto.getRefno());
        distributorStock.setGrandtotal(distributorStockDto.getGrandtotal());
		distributorStock.setGrossamount(distributorStockDto.getGrossamount());
		distributorStock.setIgst(distributorStockDto.getIgst());
		distributorStock.setSgst(distributorStockDto.getSgst());
		distributorStock.setCgst(distributorStockDto.getCgst());
		distributorStock.setRoundvalue(distributorStockDto.getRoundvalue());
		distributorStock.setUom(distributorStockDto.getUom());
		distributorStock.setDistributorStockItems(distributorStockDto.getDistributorStockItems());

		return outwardDistStockRepo.save(distributorStock);
	}

	@Override
	public void deleteOutwardDistributorStock(int id) {

		outwardDistStockRepo.deleteById(id);

	}

	@Override
	public Map<String, Object> indexOutwardDistributorStockAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<String, Object>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);

		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long size = outwardDistStockRepo.count();

				long pages = size / pagesize;

				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
						.indexOutwardDistribtor(p);

				response.put("Index", indexOutwardDistribtor);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				int size = outwardDistStockRepo.indexOutwardDistribtor(uid).size();

				int pages = size / pagesize;
				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
						.indexOutwardDistribtor(uid, p);

				response.put("Index", indexOutwardDistribtor);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			}
			
			 else if (s.equals("ROLE_RSM")) {

					long countpages = outwardDistStockRepo.count();

					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					if (rem > 0L)
						pages++;

					List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
							.indexOutwardDistribtorRsm(uid, p);

					response.put("Index", indexOutwardDistribtor);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
				}
			
			 else if (s.equals("ROLE_ASM")) {

					long countpages = outwardDistStockRepo.count();

					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					if (rem > 0L)
						pages++;

					List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
							.indexOutwardDistribtorAsm(uid, p);

					response.put("Index", indexOutwardDistribtor);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
				}
			

			 else if (s.equals("ROLE_ASE")) {

					long countpages = outwardDistStockRepo.count();

					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					if (rem > 0L)
						pages++;

					List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
							.indexOutwardDistribtorAse(uid, p);

					response.put("Index", indexOutwardDistribtor);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
				}
		}
		return null;
	}
	
	

	



	@Override
	public Map<String, Object> indexOutwardDistributorStockDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<String, Object>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);

		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long size = outwardDistStockRepo.count();

				long pages = size / pagesize;

				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
						.indexOutwardDistribtor(p);

				response.put("Index", indexOutwardDistribtor);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				int size = outwardDistStockRepo.indexOutwardDistribtor(uid).size();

				int pages = size / pagesize;
				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
						.indexOutwardDistribtor(uid, p);

				response.put("Index", indexOutwardDistribtor);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			}
			
			 else if (s.equals("ROLE_RSM")) {

					long countpages = outwardDistStockRepo.count();

					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					if (rem > 0L)
						pages++;

					List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
							.indexOutwardDistribtorRsm(uid, p);

					response.put("Index", indexOutwardDistribtor);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
				}
			
			 else if (s.equals("ROLE_ASM")) {

					long countpages = outwardDistStockRepo.count();

					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					if (rem > 0L)
						pages++;

					List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
							.indexOutwardDistribtorAsm(uid, p);

					response.put("Index", indexOutwardDistribtor);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
				}
			

			 else if (s.equals("ROLE_ASE")) {

					long countpages = outwardDistStockRepo.count();

					long pages = countpages / pagesize;
					long rem = countpages % pagesize;
					if (rem > 0L)
						pages++;

					List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
							.indexOutwardDistribtorAse(uid, p);

					response.put("Index", indexOutwardDistribtor);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
				}
		}
		return null;
	
	}


	@Override
	public Map<String, Object> searchByOutwardDistributorStock(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				List<OutwardDistributorStockProjection> indexOutwardDistribtorSearch = outwardDistStockRepo
						.indexOutwardDistribtorSearch(p, search);

				int size = indexOutwardDistribtorSearch.size();

				response.put("data", indexOutwardDistribtorSearch);

				response.put("SearchCount", size);

				return response;
			} else if (s.equals("ROLE_DISTRIBUTOR")) {
				List<OutwardDistributorStockProjection> indexOutwardDistribtorSearch = outwardDistStockRepo
						.indexOutwardDistribtorSearch(uid, p, search);

				int size = indexOutwardDistribtorSearch.size();

				response.put("data", indexOutwardDistribtorSearch);

				response.put("SearchCount", size);

				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				List<OutwardDistributorStockProjection> receipt = this.outwardDistStockRepo.indexOutwardDistribtorSearchRsm(uid,p,search
						);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				List<OutwardDistributorStockProjection> receipt = this.outwardDistStockRepo.indexOutwardDistribtorSearchAsm(uid,p,search
						);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				List<OutwardDistributorStockProjection> receipt = this.outwardDistStockRepo.indexOutwardDistribtorSearchAse(uid,p,search
						);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> reportOutwardDistributorStockAsc(long did, int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<String, Object>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		int size = outwardDistStockRepo.indexOutwardDistribtor(p).size();
		int pages = size / pagesize;

		List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo.reportOutwardDistribtor(did, p);

		response.put("Index", indexOutwardDistribtor);
		response.put("CountPages", size);
		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> reportOutwardDistributorStockDesc(long did, int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<String, Object>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);

		Pageable p = PageRequest.of(pageno, pagesize, sort);

		int size = outwardDistStockRepo.indexOutwardDistribtor(p).size();

		int pages = size / pagesize;

		List<OutwardDistributorStockProjection> indexOutwardDistribtor = outwardDistStockRepo
				.reportOutwardDistribtor(did, p);

		response.put("Index", indexOutwardDistribtor);

		response.put("CountPages", size);

		response.put("Pages", pages);

		return response;
	}

	@Override
	public Map<String, Object> searchReportByOutwardDistributorStock(long did, int pageno, int pagesize,
			String search) {

		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		List<OutwardDistributorStockProjection> indexOutwardDistribtorSearch = outwardDistStockRepo
				.reportOutwardDistribtorSearch(did, p, search);

		int size = indexOutwardDistribtorSearch.size();

		response.put("data", indexOutwardDistribtorSearch);

		response.put("SearchCount", size);

		return response;
	}

	@Override
	public List<OutwardDistributorIndexDto> getAllRetailer(int id) {
		// TODO Auto-generated method stub
		List<OutwardDistributorIndexDto> allRetailer = outwardDistStockRepo.getAllRetailer(id);
		return allRetailer;
	}
	
	
	

//	@Override
//	public Map<String, Object> DistributorStockReport(int pageno, int pagesize, String field,String fromdate, String todate, int distid) {
//		
//		Map<String, Object> response = new HashMap<>();
//
//		Sort sort = Sort.by(Sort.Direction.ASC, field);
//		Pageable p = PageRequest.of(pageno, pagesize, sort);
//
//		long countpages = outwardDistStockRepo.count();
//		long pages = countpages / pagesize;
//
//		List<com.SCM.ReportDto.DistributorStockReport> ipo = outwardDistStockRepo.distributorstockreport(fromdate,todate,distid,p);
//
//		response.put("Index", ipo);
//		response.put("CountPages", countpages);
//		response.put("Pages", pages);
//
//		return response;
//	}

}
