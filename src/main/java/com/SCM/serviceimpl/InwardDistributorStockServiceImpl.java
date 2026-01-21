package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexInwardDitributor;
import com.SCM.dto.InwardDistributorStockDto;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.InwardDistributorStock;
import com.SCM.model.OutwardDistributorStock;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.InwardDistributorStockRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.InwardDistributorStockService;

@Service
public class InwardDistributorStockServiceImpl implements InwardDistributorStockService {

	@Autowired
	private InwardDistributorStockRepo inwardDistributorStockRepo;

	@Autowired
	private GetCurrentUserRoleName auth;
	
	@Autowired
	private VoucherMasterRepo voucherMasterRepo;
	
	
	@Override
	public InwardDistributorStock save(InwardDistributorStockDto inwardDistributorStockDto) {
		
		VoucherMaster voucher=voucherMasterRepo.findById(inwardDistributorStockDto.getVoucherMaster().getId()).get();

		
		Long uid = auth.getUserId();
		String uname = auth.getUserName();
		String role = auth.getRolename();
		
		InwardDistributorStock stock = new InwardDistributorStock();
		stock.setIndate(inwardDistributorStockDto.getIndate());
		stock.setDistributorid(inwardDistributorStockDto.getDistributorid());
		stock.setInwardDistributorStockItems(inwardDistributorStockDto.getInwardDistributorStockItems());
		stock.setCreateddate(LocalDate.now());
		stock.setCreatedtime(LocalTime.now());
		stock.setInvoiceno(inwardDistributorStockDto.getInvoiceno());
		stock.setGrossamount(inwardDistributorStockDto.getGrossamount());
		stock.setGrandtotal(inwardDistributorStockDto.getGrandtotal());
		stock.setIgst(inwardDistributorStockDto.getIgst());
		stock.setCgst(inwardDistributorStockDto.getCgst());
		stock.setSgst(inwardDistributorStockDto.getSgst());
		stock.setRoundvalue(inwardDistributorStockDto.getRoundvalue());
        stock.setCreatebyname(uname);
		stock.setCreatedby(uid);
		stock.setRole(role);
		stock.setVoucherMaster(voucher);
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			InwardDistributorStock topByVoucherOrderByStartnumberwithprefilnoDesc = inwardDistributorStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			InwardDistributorStock topByVoucherOrderByStartnumberwithprefilyesDesc = inwardDistributorStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			InwardDistributorStock lastrowstatus = inwardDistributorStockRepo.lastrowstatus();
			InwardDistributorStock lastrowstatus = inwardDistributorStockRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				stock.setStartnumberwithprefilno(startingnumber);
				stock.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					stock.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					stock.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					stock.setStartnumberwithprefilno(restartnumber + 1);
					stock.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					stock.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					stock.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					stock.setStartnumberwithprefilyes(formattedStartingNumber);
					stock.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					stock.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					stock.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					stock.setStartnumberwithprefilyes(restartnumberinc);
					stock.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					stock.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					stock.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			InwardDistributorStock topByVoucherOrderByStartnumberwithprefilnoDesc = inwardDistributorStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			InwardDistributorStock topByVoucherOrderByStartnumberwithprefilyesDesc = inwardDistributorStockRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			InwardDistributorStock lastrowstatus = inwardDistributorStockRepo.lastrowstatus();
			InwardDistributorStock lastrowstatus = inwardDistributorStockRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				stock.setStartnumberwithprefilno(startingnumber);
				stock.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				stock.setStartnumberwithprefilyes(formattedStartingNumber);
				stock.setVoucherstatus("startnostatus");
			}
		}

		InwardDistributorStock save = inwardDistributorStockRepo.save(stock);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		stock.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		inwardDistributorStockRepo.save(stock);
		
	return 	inwardDistributorStockRepo.save(stock);
	}

	@Override
	public List<InwardDistributorStock> getAll() {
		
		List<InwardDistributorStock> all = inwardDistributorStockRepo.findAll();
		
		return all;
	}

	@Override
	public InwardDistributorStock getById(int id) {
		
		InwardDistributorStock inwardDistributorStock = inwardDistributorStockRepo.findById(id).get();
		
		return inwardDistributorStock;
	}

	@Override
	public InwardDistributorStock update(InwardDistributorStockDto inwardDistributorStockDto, int id) {
		
		Long uid = auth.getUserId();
		String uname = auth.getUserName();
		String role = auth.getRolename();
		
		InwardDistributorStock stock = inwardDistributorStockRepo.findById(id).get();
		stock.setIndate(inwardDistributorStockDto.getIndate());
		stock.setDistributorid(inwardDistributorStockDto.getDistributorid());
		stock.setInwardDistributorStockItems(inwardDistributorStockDto.getInwardDistributorStockItems());
		stock.setUpdateddate(LocalDate.now());
		stock.setUpdatedtime(LocalTime.now());
		stock.setInvoiceno(inwardDistributorStockDto.getInvoiceno());
		stock.setGrossamount(inwardDistributorStockDto.getGrossamount());
		stock.setGrandtotal(inwardDistributorStockDto.getGrandtotal());
		stock.setIgst(inwardDistributorStockDto.getIgst());
		stock.setCgst(inwardDistributorStockDto.getCgst());
		stock.setSgst(inwardDistributorStockDto.getSgst());
		stock.setRoundvalue(inwardDistributorStockDto.getRoundvalue());
		stock.setUpdatedbyname(uname);
		stock.setUpdatedby(uid);
		stock.setUpdatedrole(role);
		
		InwardDistributorStock update = inwardDistributorStockRepo.save(stock);
		
		return update;
	}

	@Override
	public void deleteInwardDistributor(int id) {
		
		inwardDistributorStockRepo.deleteById(id);	
	}

	@Override
	public Map<String, Object> indexInwardDistributorStockAsc(int pageno, int pagesize, String field) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				int size = inwardDistributorStockRepo.indexInwardDistributor(p).size();

				int pages = size / pagesize;

				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInwardDitributor> indexInwardDistributor = inwardDistributorStockRepo.indexInwardDistributor(p);

				response.put("Index", indexInwardDistributor);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				int size = inwardDistributorStockRepo.indexInwardDistributor(uid).size();

				int pages = size / pagesize;
				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributor(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				long countpages = inwardDistributorStockRepo.count();

				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributorRsm(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = inwardDistributorStockRepo.count();

				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributorAsm(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				long countpages = inwardDistributorStockRepo.count();

				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributorAse(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			
			
			
		}
		return null;
	}

	@Override
	public Map<String, Object> indexInwardDistributorStockDesc(int pageno, int pagesize, String field) {
	
		Map<String, Object> response = new HashMap<String, Object>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				int size = inwardDistributorStockRepo.indexInwardDistributor(p).size();

				int pages = size / pagesize;

				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInwardDitributor> indexInwardDistributor = inwardDistributorStockRepo.indexInwardDistributor(p);

				response.put("Index", indexInwardDistributor);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				int size = inwardDistributorStockRepo.indexInwardDistributor(uid).size();

				int pages = size / pagesize;
				long rem = size % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributor(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", size);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				long countpages = inwardDistributorStockRepo.count();

				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributorRsm(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = inwardDistributorStockRepo.count();

				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributorAsm(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				long countpages = inwardDistributorStockRepo.count();

				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<IndexInwardDitributor> indexInwardDistributor1 = inwardDistributorStockRepo.indexInwardDistributorAse(p, uid);

				response.put("Index", indexInwardDistributor1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			
			
		}
		return null;
	}

	@Override
	public Map<String, Object> searchByInwardDistributorStock(int pageno, int pagesize, String search) {
		
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

				List<IndexInwardDitributor> indexInwardDistribtorSearch = inwardDistributorStockRepo.indexInwardDistribtorSearch(p, search);

				int size = indexInwardDistribtorSearch.size();

				response.put("data", indexInwardDistribtorSearch);
				response.put("SearchCount", size);
				return response;
				
			} else if (s.equals("ROLE_DISTRIBUTOR")) {
				
				List<IndexInwardDitributor> indexInwardDistribtorSearch = inwardDistributorStockRepo.indexInwardDistribtorSearch(p, search, uid);

				int size = indexInwardDistribtorSearch.size();

				response.put("data", indexInwardDistribtorSearch);
				response.put("SearchCount", size);
				return response;
			}
			
			 else if (s.equals("ROLE_RSM")) {
					
					List<IndexInwardDitributor> indexInwardDistribtorSearch = inwardDistributorStockRepo.indexInwardDistribtorSearchRsm(p, search, uid);

					int size = indexInwardDistribtorSearch.size();

					response.put("data", indexInwardDistribtorSearch);
					response.put("SearchCount", size);
					return response;
				}
			
			 else if (s.equals("ROLE_ASM")) {
					
					List<IndexInwardDitributor> indexInwardDistribtorSearch = inwardDistributorStockRepo.indexInwardDistribtorSearchAsm(p, search, uid);

					int size = indexInwardDistribtorSearch.size();

					response.put("data", indexInwardDistribtorSearch);
					response.put("SearchCount", size);
					return response;
				}
			
			 else if (s.equals("ROLE_ASE")) {
					
					List<IndexInwardDitributor> indexInwardDistribtorSearch = inwardDistributorStockRepo.indexInwardDistribtorSearchAse(p, search, uid);

					int size = indexInwardDistribtorSearch.size();

					response.put("data", indexInwardDistribtorSearch);
					response.put("SearchCount", size);
					return response;
				}
		}
		
		return null;
	}
	
	

}
