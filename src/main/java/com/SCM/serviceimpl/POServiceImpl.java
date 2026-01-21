package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
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

import com.SCM.IndexDto.IndexPurchaseOrder;
import com.SCM.dto.PurchaseOrderDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.PoRepository;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.POService;

@Service
public class POServiceImpl implements POService {

	@Autowired
	private PoRepository poRepository;

	@Autowired
	private ActivityLogRepo activityLogRepo;

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
	public PurchaseOrder savePO(PurchaseOrderDto purchaseOrderDto) {

		VoucherMaster voucher = voucherMasterRepo.findById(purchaseOrderDto.getVoucherMaster().getId()).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		PurchaseOrder po = new PurchaseOrder();
		po.setCreatedby(uid);
		po.setCreatebyname(uname);
		po.setRole(role);

		po.setVoucherno(purchaseOrderDto.getVoucherno());
		po.setPodate(purchaseOrderDto.getPodate());
		po.setPodueon(purchaseOrderDto.getPodueon());
		po.setPaymentTerms(purchaseOrderDto.getPaymentTerms());
		po.setOtherrefernce(purchaseOrderDto.getOtherrefernce());
		po.setDestination(purchaseOrderDto.getDestination());
		po.setDeclaration(purchaseOrderDto.getDeclaration());
		po.setDispatchedthrough(purchaseOrderDto.getDispatchedthrough());
		po.setTermsofdelivery(purchaseOrderDto.getTermsofdelivery());
		po.setType(purchaseOrderDto.getType());
		po.setReferenceno(purchaseOrderDto.getReferenceno());
		po.setReferencedate(purchaseOrderDto.getReferencedate());
		po.setTaxtype(purchaseOrderDto.getTaxtype());
		po.setStatus("pending");
		po.setRemarks(purchaseOrderDto.getRemarks());
		po.setIgst(purchaseOrderDto.getIgst());
		po.setCgst(purchaseOrderDto.getCgst());
		po.setSgst(purchaseOrderDto.getSgst());
		po.setGrandtotal(purchaseOrderDto.getGrandtotal());
		po.setWarehouse(purchaseOrderDto.getWarehouse());
		po.setSupplier(purchaseOrderDto.getSupplier());
		po.setSupplierSubContacts(purchaseOrderDto.getSupplierSubContacts());
		po.setCompany(purchaseOrderDto.getCompany());
		po.setBranch(purchaseOrderDto.getBranch());
		po.setShippingcharge(purchaseOrderDto.getShippingcharge());
		po.setShippingAddress(purchaseOrderDto.getShippingAddress());
		po.setNetAmount(purchaseOrderDto.getNetAmount());
		po.setGrandtotal(purchaseOrderDto.getGrandtotal());
		po.setRoundingofvalue(purchaseOrderDto.getRoundingofvalue());
		po.setPurchaseOrderItems(purchaseOrderDto.getPurchaseOrderItems());
		po.setCreateddate(LocalDate.now());
		po.setCreatedtime(LocalTime.now());
		
		po.setVoucherMaster(voucher);
		

//		------------------------------ new voucher 

		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			PurchaseOrder topByVoucherOrderByStartnumberwithprefilnoDesc = poRepository.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			PurchaseOrder topByVoucherOrderByStartnumberwithprefilyesDesc = poRepository.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			PurchaseOrder lastrowstatus = poRepository.lastrowstatus();
			PurchaseOrder lastrowstatus = poRepository.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				po.setStartnumberwithprefilno(startingnumber);
				po.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					po.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					po.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					po.setStartnumberwithprefilno(restartnumber + 1);
					po.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					po.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					po.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					po.setStartnumberwithprefilyes(formattedStartingNumber);
					po.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					po.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					po.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					po.setStartnumberwithprefilyes(restartnumberinc);
					po.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					po.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					po.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			PurchaseOrder topByVoucherOrderByStartnumberwithprefilnoDesc = poRepository.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			PurchaseOrder topByVoucherOrderByStartnumberwithprefilyesDesc = poRepository.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			PurchaseOrder lastrowstatus = poRepository.lastrowstatus();
			PurchaseOrder lastrowstatus = poRepository.findTopByVoucherMasterOrderByIdDesc(voucher);

			

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				po.setStartnumberwithprefilno(startingnumber);
				po.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				po.setStartnumberwithprefilyes(formattedStartingNumber);
				po.setVoucherstatus("startnostatus");
			}
		}

		PurchaseOrder save = poRepository.save(po);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		po.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		poRepository.save(po);

		PurchaseOrder porder = poRepository.save(po);

		porder.getPurchaseOrderItems().forEach(poi -> {

			int poquantity = poi.getPoquantity();
			poi.setRemainingpcsqty(poquantity);
		});

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setPurchaseorderid((long) porder.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	@Override
	public List<PurchaseOrder> getAllPO() {
		return poRepository.findAll();
	}

	@Override
	public PurchaseOrder getPOById(int id) {

		PurchaseOrder po = poRepository.findById(id).get();
		return po;
	}

	@Override
	public PurchaseOrderDto updatePO(PurchaseOrderDto purchaseOrderDto, int id) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		PurchaseOrder po = poRepository.findById(id).get();

		po.setUpdatedby(uid);
		po.setUpdatedbyname(uname);
		po.setUpdatedrole(role);
		po.setUpdatedtime(LocalTime.now());
		po.setUpdateddate(LocalDate.now());

		po.setVoucherno(purchaseOrderDto.getVoucherno());
		po.setPodate(purchaseOrderDto.getPodate());
		po.setPaymentTerms(purchaseOrderDto.getPaymentTerms());
		po.setOtherrefernce(purchaseOrderDto.getOtherrefernce());
		po.setDestination(purchaseOrderDto.getDestination());
		po.setDeclaration(purchaseOrderDto.getDeclaration());
		po.setDispatchedthrough(purchaseOrderDto.getDispatchedthrough());
		po.setTermsofdelivery(purchaseOrderDto.getTermsofdelivery());
		po.setType(purchaseOrderDto.getType());
		po.setReferenceno(purchaseOrderDto.getReferenceno());
		po.setReferencedate(purchaseOrderDto.getReferencedate());
		po.setTaxtype(purchaseOrderDto.getTaxtype());
		po.setStatus(purchaseOrderDto.getStatus());
		po.setRemarks(purchaseOrderDto.getRemarks());
		po.setIgst(purchaseOrderDto.getIgst());
		po.setCgst(purchaseOrderDto.getCgst());
		po.setSgst(purchaseOrderDto.getSgst());
		po.setGrandtotal(purchaseOrderDto.getGrandtotal());
		po.setShippingcharge(purchaseOrderDto.getShippingcharge());
		po.setNetAmount(purchaseOrderDto.getNetAmount());
		po.setShippingAddress(purchaseOrderDto.getShippingAddress());
		po.setRoundingofvalue(purchaseOrderDto.getRoundingofvalue());
		po.setWarehouse(purchaseOrderDto.getWarehouse());
		po.setSupplier(purchaseOrderDto.getSupplier());
		po.setSupplierSubContacts(purchaseOrderDto.getSupplierSubContacts());
		po.setCompany(purchaseOrderDto.getCompany());
		po.setBranch(purchaseOrderDto.getBranch());
		po.setPurchaseOrderItems(purchaseOrderDto.getPurchaseOrderItems());

		PurchaseOrder savepo = poRepository.save(po);

		PurchaseOrderDto purchaseDto2 = new PurchaseOrderDto();
		purchaseDto2.setVoucherno(savepo.getVoucherno());
		purchaseDto2.setPodate(savepo.getPodate());
		po.setPaymentTerms(purchaseOrderDto.getPaymentTerms());
		purchaseDto2.setOtherrefernce(savepo.getOtherrefernce());
		purchaseDto2.setDestination(savepo.getDestination());
		purchaseDto2.setDeclaration(savepo.getDeclaration());
		purchaseDto2.setDispatchedthrough(savepo.getDispatchedthrough());
		purchaseDto2.setTermsofdelivery(savepo.getTermsofdelivery());
		purchaseDto2.setType(savepo.getType());
		purchaseDto2.setReferenceno(savepo.getReferenceno());
		purchaseDto2.setReferencedate(savepo.getReferencedate());
		purchaseDto2.setTaxtype(savepo.getTaxtype());
		purchaseDto2.setStatus(savepo.getStatus());
		;
		purchaseDto2.setRemarks(savepo.getRemarks());
		purchaseDto2.setIgst(savepo.getIgst());
		purchaseDto2.setCgst(savepo.getCgst());
		purchaseDto2.setSgst(savepo.getSgst());
		purchaseDto2.setShippingAddress(savepo.getShippingAddress());
		purchaseDto2.setGrandtotal(savepo.getGrandtotal());
		purchaseDto2.setShippingcharge(savepo.getShippingcharge());
		purchaseDto2.setNetAmount(savepo.getNetAmount());
		purchaseDto2.setRoundingofvalue(savepo.getRoundingofvalue());
		purchaseDto2.setWarehouse(savepo.getWarehouse());
		purchaseDto2.setSupplier(savepo.getSupplier());
		purchaseDto2.setSupplierSubContacts(savepo.getSupplierSubContacts());
		purchaseDto2.setCompany(savepo.getCompany());
		purchaseDto2.setBranch(savepo.getBranch());
		purchaseDto2.setPurchaseOrderItems(savepo.getPurchaseOrderItems());

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setPurchaseorderid((long) savepo.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);
		return purchaseDto2;
	}

	@Override
	public void deletePO(int id) {
		poRepository.deleteById(id);
	}

	@Override
	public Map<String, Object> IndexPOAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {

				long countpages = poRepository.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseOrder> ipo = poRepository.indexPurchaseOrder(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = poRepository.indexPurchaseOrderBySupplierId(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseOrder> ipo = poRepository.indexPurchaseOrderBySupplierId(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> IndexPODesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {

				long countpages = poRepository.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseOrder> ipo = poRepository.indexPurchaseOrder(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = poRepository.indexPurchaseOrderBySupplierId(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseOrder> ipo = poRepository.indexPurchaseOrderBySupplierId(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;

	}

	@Override
	public Map<String, Object> SearchPO(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();

		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {
				System.out.println("admin");
				List<IndexPurchaseOrder> orders = poRepository.SearchByPurchaseOrder(search, p);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				List<IndexPurchaseOrder> orders = poRepository.SearchByPurchaseOrderBySupplierId(search, p, sid);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			}
		}

		return null;
	}
}
