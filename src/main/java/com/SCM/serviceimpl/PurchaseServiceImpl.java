package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;

//import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexPurchase;
import com.SCM.IndexDto.IndexPurchaseOrder;
import com.SCM.IndexDto.PurchasePageDtoProjection;
import com.SCM.dto.PurchaseDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.MaterialRecepitNote;
import com.SCM.model.Purchase;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.MaterialReceiptNoteRepo;
import com.SCM.repository.PoRepository;
import com.SCM.repository.PurchaseRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepo purchaseRepo;

	@Autowired
	private PoRepository poRepository;

	@Autowired
	private MaterialReceiptNoteRepo materialReceiptNoteRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;
	
	@Autowired
	private VoucherMasterRepo voucherMasterRepo;

	@Autowired
	private ObjectMapper objectMapper;
	
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
	public Purchase savePurchase(PurchaseDto purchaseDto) {
		
		VoucherMaster voucher = voucherMasterRepo.findById(purchaseDto.getVoucherMaster().getId()).get();

		Purchase purchase = new Purchase();
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		purchase.setCreatebyname(uname);
		purchase.setCreatedby(uid);
		purchase.setRole(role);

		purchase.setCgst(purchaseDto.getCgst());
		purchase.setEwaybillno(purchaseDto.getEwaybillno());
		purchase.setTaxAmount(purchaseDto.getTaxAmount());
		purchase.setNetamount(purchaseDto.getNetamount());
		purchase.setOtherreference(purchaseDto.getOtherreference());
		purchase.setSupplierinvoiceno(purchaseDto.getSupplierinvoiceno());
		purchase.setSupplierinvoicedate(purchaseDto.getSupplierinvoicedate());
		purchase.setIgst(purchaseDto.getIgst());
		purchase.setInvoiceno(purchaseDto.getInvoiceno());
		purchase.setTaxtype(purchaseDto.getTaxtype());
		purchase.setStatus("pending");
		purchase.setSgst(purchaseDto.getSgst());
		purchase.setRoundofvalue(purchaseDto.getRoundofvalue());
		purchase.setGrandtotal(purchaseDto.getGrandtotal());
		purchase.setShippingcharge(purchaseDto.getShippingcharge());
		purchase.setShippingaddress(purchaseDto.getShippingaddress());
		purchase.setPurchasedate(purchaseDto.getPurchasedate());
		purchase.setWarehouse(purchaseDto.getWarehouse());
		purchase.setGrossAmount(purchaseDto.getGrossAmount());
		purchase.setOwners(purchaseDto.getOwners());
		purchase.setCompany(purchaseDto.getCompany());
		purchase.setSupplier(purchaseDto.getSupplier());
		purchase.setBranch(purchaseDto.getBranch());
		purchase.setRemarks(purchaseDto.getRemarks());
		purchase.setEwaybilldate(purchaseDto.getEwaybilldate());
		purchase.setPaymentTerms(purchaseDto.getPaymentTerms());
		purchase.setSupplierSubContacts(purchaseDto.getSupplierSubContacts());
		purchase.setPurchaseItems(purchaseDto.getPurchaseItems());
	

		purchase.setCreateddate(LocalDate.now());
		purchase.setCreatedtime(LocalTime.now());
		purchase.setVoucherMaster(voucher);

		
//		
//		if (purchaseRepo.searchByVoucher(purchaseDto.getVoucherid())==null) {
////			salesdto.getVoucherid()
//			System.out.println();
//			purchase.setVoucherid(purchaseDto.getVoucherid());
//			purchase.setVouchernumber(1);
//			String newVoucher=purchaseDto.getVoucherid()+1;
//			purchase.setVoucherseries(newVoucher);
//
//		}
//		
//		else {
//			Purchase s = purchaseRepo.searchByVoucher(purchaseDto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser=newvid+vnum;
//			purchase.setVoucherid(newvid);
//			purchase.setVouchernumber(newvnum);
//			purchase.setVoucherseries(newvser);
//
//		}
		
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			Purchase topByVoucherOrderByStartnumberwithprefilnoDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Purchase topByVoucherOrderByStartnumberwithprefilyesDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Purchase lastrowstatus = purchaseRepo.lastrowstatus();
			Purchase lastrowstatus = purchaseRepo.findTopByVoucherMasterOrderByIdDesc(voucher);
			


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				purchase.setStartnumberwithprefilno(startingnumber);
				purchase.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					purchase.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					purchase.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					purchase.setStartnumberwithprefilno(restartnumber + 1);
					purchase.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					purchase.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					purchase.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					purchase.setStartnumberwithprefilyes(formattedStartingNumber);
					purchase.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					purchase.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					purchase.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

//					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					purchase.setStartnumberwithprefilyes(restartnumberinc);
					purchase.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					purchase.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					purchase.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			Purchase topByVoucherOrderByStartnumberwithprefilnoDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Purchase topByVoucherOrderByStartnumberwithprefilyesDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Purchase lastrowstatus = purchaseRepo.lastrowstatus();
			
			Purchase lastrowstatus = purchaseRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				purchase.setStartnumberwithprefilno(startingnumber);
				purchase.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				purchase.setStartnumberwithprefilyes(formattedStartingNumber);
				purchase.setVoucherstatus("startnostatus");
			}
		}

		Purchase save = purchaseRepo.save(purchase);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		purchase.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		purchaseRepo.save(purchase);



		Purchase p = purchaseRepo.save(purchase);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setPurchaseid((long) p.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	
	
	
	@Override
	public Purchase ConvertPotoPurchase(PurchaseDto purchaseDto, int poId) {

		Optional<PurchaseOrder> po = poRepository.findById(poId);
		PurchaseOrder po1 = po.get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Purchase purchase = new Purchase();
		purchase.setCgst(purchaseDto.getCgst());
		purchase.setEwaybillno(purchaseDto.getEwaybillno());
		purchase.setTaxAmount(purchaseDto.getTaxAmount());
		purchase.setNetamount(purchaseDto.getNetamount());
		purchase.setIgst(purchaseDto.getIgst());;
		purchase.setInvoiceno(purchaseDto.getInvoiceno());
		purchase.setTaxtype(purchaseDto.getTaxtype());
		purchase.setSgst(purchaseDto.getSgst());
		purchase.setRoundofvalue(purchaseDto.getRoundofvalue());
		purchase.setGrandtotal(purchaseDto.getGrandtotal());
		purchase.setShippingcharge(purchaseDto.getShippingcharge());
		purchase.setShippingaddress(purchaseDto.getShippingaddress());
		purchase.setPurchasedate(purchaseDto.getPurchasedate());
		purchase.setWarehouse(purchaseDto.getWarehouse());
		purchase.setOtherreference(purchaseDto.getOtherreference());
		purchase.setSupplierinvoiceno(purchaseDto.getSupplierinvoiceno());
		purchase.setSupplierinvoicedate(purchaseDto.getSupplierinvoicedate());
		purchase.setGrossAmount(purchaseDto.getGrossAmount());
		purchase.setEwaybilldate(purchaseDto.getEwaybilldate());
		purchase.setOwners(purchaseDto.getOwners());
		purchase.setRemarks(purchaseDto.getRemarks());
		purchase.setPaymentTerms(purchaseDto.getPaymentTerms());
		purchase.setSupplier(purchaseDto.getSupplier());
		purchase.setBranch(purchaseDto.getBranch());
		purchase.setSupplierSubContacts(purchaseDto.getSupplierSubContacts());
		purchase.setPurchaseItems(purchaseDto.getPurchaseItems());
		purchase.setPoId(po1.getId());
		purchase.setCreateddate(LocalDate.now());
		purchase.setCreatedtime(LocalTime.now());

		purchase.setCreatebyname(uname);
		purchase.setCreatedby(uid);
		purchase.setRole(role);

		Purchase p = purchaseRepo.save(purchase);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setPurchaseid((long) p.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return p;
	}

	// -------- converted mrn to purchase

	@Override
	public Purchase ConvertMRNtoPurchase(PurchaseDto purchaseDto, int mrnId) {
		
		VoucherMaster voucher = voucherMasterRepo.findById(purchaseDto.getVoucherMaster().getId()).get();

		Optional<MaterialRecepitNote> mrn = materialReceiptNoteRepo.findById(mrnId);
		MaterialRecepitNote mrn1 = mrn.get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Purchase purchase = new Purchase();
		purchase.setCgst(purchaseDto.getCgst());
		purchase.setEwaybillno(purchaseDto.getEwaybillno());
		purchase.setTaxAmount(purchaseDto.getTaxAmount());
		purchase.setNetamount(purchaseDto.getNetamount());
		purchase.setIgst(purchaseDto.getIgst());
		purchase.setInvoiceno(purchaseDto.getInvoiceno());
		purchase.setTaxtype(purchaseDto.getTaxtype());
		purchase.setStatus("not converted");
		purchase.setSgst(purchaseDto.getSgst());
		purchase.setRoundofvalue(purchaseDto.getRoundofvalue());
		purchase.setGrandtotal(purchaseDto.getGrandtotal());
		purchase.setShippingcharge(purchaseDto.getShippingcharge());
		purchase.setShippingaddress(purchaseDto.getShippingaddress());
		purchase.setPurchasedate(purchaseDto.getPurchasedate());
		purchase.setRemarks(purchaseDto.getRemarks());
		purchase.setOtherreference(purchaseDto.getOtherreference());
		purchase.setSupplierinvoiceno(purchaseDto.getSupplierinvoiceno());
		purchase.setSupplierinvoicedate(purchaseDto.getSupplierinvoicedate());
		purchase.setPaymentTerms(purchaseDto.getPaymentTerms());
		purchase.setWarehouse(purchaseDto.getWarehouse());
		purchase.setGrossAmount(purchaseDto.getGrossAmount());
		purchase.setOwners(purchaseDto.getOwners());
		purchase.setEwaybilldate(purchaseDto.getEwaybilldate());
		purchase.setBranch(purchaseDto.getBranch());
		purchase.setSupplier(purchaseDto.getSupplier());
		purchase.setSupplierSubContacts(purchaseDto.getSupplierSubContacts());
		purchase.setPurchaseItems(purchaseDto.getPurchaseItems());
		purchase.setMrnId(mrn1.getId());
		purchase.setCreateddate(LocalDate.now());
		purchase.setCreatedtime(LocalTime.now());

		purchase.setCreatebyname(uname);
		purchase.setCreatedby(uid);
		purchase.setRole(role);
		purchase.setVoucherMaster(voucher);

		Purchase p = purchaseRepo.save(purchase);

		MaterialRecepitNote recepitNote = materialReceiptNoteRepo.findById(p.getMrnId()).get();
		recepitNote.setStatus("converted");
		materialReceiptNoteRepo.save(recepitNote);

		
//		if (purchaseRepo.searchByVoucher(purchaseDto.getVoucherid())==null) {
////			salesdto.getVoucherid()
//			System.out.println();
//			purchase.setVoucherid(purchaseDto.getVoucherid());
//			purchase.setVouchernumber(1);
//			String newVoucher=purchaseDto.getVoucherid()+1;
//			purchase.setVoucherseries(newVoucher);
//
//		}
//	
//		else {
//			Purchase s = purchaseRepo.searchByVoucher(purchaseDto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser=newvid+vnum;
//			purchase.setVoucherid(newvid);
//			purchase.setVouchernumber(newvnum);
//			purchase.setVoucherseries(newvser);
//		}
		
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			Purchase topByVoucherOrderByStartnumberwithprefilnoDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Purchase topByVoucherOrderByStartnumberwithprefilyesDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Purchase lastrowstatus = purchaseRepo.lastrowstatus();
			
			Purchase lastrowstatus = purchaseRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				purchase.setStartnumberwithprefilno(startingnumber);
				purchase.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					purchase.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					purchase.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					purchase.setStartnumberwithprefilno(restartnumber + 1);
					purchase.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					purchase.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					purchase.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					purchase.setStartnumberwithprefilyes(formattedStartingNumber);
					purchase.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					purchase.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					purchase.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

//					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					purchase.setStartnumberwithprefilyes(restartnumberinc);
					purchase.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					purchase.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					purchase.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			Purchase topByVoucherOrderByStartnumberwithprefilnoDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Purchase topByVoucherOrderByStartnumberwithprefilyesDesc = purchaseRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Purchase lastrowstatus = purchaseRepo.lastrowstatus();
			Purchase lastrowstatus = purchaseRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				purchase.setStartnumberwithprefilno(startingnumber);
				purchase.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				purchase.setStartnumberwithprefilyes(formattedStartingNumber);
				purchase.setVoucherstatus("startnostatus");
			}
		}

		Purchase save = purchaseRepo.save(purchase);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		purchase.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		purchaseRepo.save(purchase);

	    materialReceiptNoteRepo.save(recepitNote);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setPurchaseid((long) p.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return p;
	}

	
	@Override
	public List<Purchase> getAllPurchase() {
		return purchaseRepo.findAll();
	}

	@Override
	public Purchase getPurchaseById(int id) {
		Optional<Purchase> purchase = purchaseRepo.findById(id);
		Purchase p = purchase.get();
		return p;
	}

	@Override
	public PurchaseDto updatePurchase(PurchaseDto purchaseDto, int id) {

		
//		Purchase purchase = p.get();

		Long uid = getUserId();
		String uname = getUserName();

		String role = getRolename();
		
		Purchase purchase = purchaseRepo.findById(id).get();

		purchase.setUpdatedbyname(uname);
		purchase.setUpdatedby(uid);
		purchase.setUpdatedrole(role);
		purchase.setUpdateddate(LocalDate.now());
		purchase.setUpdatedtime(LocalTime.now());

		purchase.setCgst(purchaseDto.getCgst());
		purchase.setEwaybillno(purchaseDto.getEwaybillno());
		purchase.setTaxAmount(purchaseDto.getTaxAmount());
		purchase.setNetamount(purchaseDto.getNetamount());
		purchase.setGrossAmount(purchaseDto.getGrossAmount());
		purchase.setIgst(purchaseDto.getIgst());
		purchase.setOtherreference(purchaseDto.getOtherreference());
		purchase.setSupplierinvoiceno(purchaseDto.getSupplierinvoiceno());
		purchase.setSupplierinvoicedate(purchaseDto.getSupplierinvoicedate());
		purchase.setInvoiceno(purchaseDto.getInvoiceno());
		purchase.setTaxtype(purchaseDto.getTaxtype());
		purchase.setStatus(purchaseDto.getStatus());
		purchase.setSgst(purchaseDto.getSgst());
		purchase.setRoundofvalue(purchaseDto.getRoundofvalue());
		purchase.setGrandtotal(purchaseDto.getGrandtotal());
		purchase.setShippingcharge(purchaseDto.getShippingcharge());
		purchase.setShippingaddress(purchaseDto.getShippingaddress());
		purchase.setPurchasedate(purchaseDto.getPurchasedate());
		purchase.setWarehouse(purchaseDto.getWarehouse());
		purchase.setOwners(purchaseDto.getOwners());
		purchase.setEwaybilldate(purchaseDto.getEwaybilldate());
		purchase.setSupplier(purchaseDto.getSupplier());
		purchase.setBranch(purchaseDto.getBranch());
		purchase.setSupplierSubContacts(purchaseDto.getSupplierSubContacts());
		purchase.setPurchaseItems(purchaseDto.getPurchaseItems());


		Purchase savedpurchase = purchaseRepo.save(purchase);

		PurchaseDto purchaseDto2 = new PurchaseDto();
		purchaseDto2.setCgst(savedpurchase.getCgst());
		purchaseDto2.setEwaybillno(savedpurchase.getEwaybillno());
		purchaseDto2.setTaxAmount(savedpurchase.getTaxAmount());
		purchaseDto2.setNetamount(savedpurchase.getNetamount());
		purchaseDto2.setGrossAmount(savedpurchase.getGrossAmount());
		purchaseDto2.setIgst(savedpurchase.getIgst());
		purchaseDto2.setOtherreference(savedpurchase.getOtherreference());
		purchaseDto2.setSupplierinvoiceno(savedpurchase.getSupplierinvoiceno());
		purchaseDto2.setSupplierinvoicedate(savedpurchase.getSupplierinvoicedate());
		purchaseDto2.setInvoiceno(savedpurchase.getInvoiceno());
		purchaseDto2.setTaxtype(savedpurchase.getTaxtype());
		purchaseDto2.setStatus(savedpurchase.getStatus());
		purchaseDto2.setSgst(savedpurchase.getSgst());
		purchaseDto2.setRoundofvalue(savedpurchase.getRoundofvalue());
		purchaseDto2.setGrandtotal(savedpurchase.getGrandtotal());
		purchaseDto2.setEwaybilldate(savedpurchase.getEwaybilldate());
		purchaseDto2.setShippingcharge(savedpurchase.getShippingcharge());
		purchaseDto2.setShippingaddress(savedpurchase.getShippingaddress());
		purchaseDto2.setPurchasedate(savedpurchase.getPurchasedate());
		purchaseDto2.setWarehouse(savedpurchase.getWarehouse());
		purchaseDto2.setBranch(savedpurchase.getBranch());
		purchaseDto2.setSupplier(savedpurchase.getSupplier());
		purchaseDto2.setOwners(savedpurchase.getOwners());
		purchaseDto2.setSupplierSubContacts(savedpurchase.getSupplierSubContacts());
		purchaseDto2.setPurchaseItems(savedpurchase.getPurchaseItems());


		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setPurchaseid((long) savedpurchase.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return purchaseDto2;
	}

	@Override
	public void deletePurchase(int id) {
		purchaseRepo.deleteById(id);
	}

	@Override
	public Map<String, Object> indexPurchaseAsc(int pageno, int pagesize, String field) {

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

				List<IndexPurchase> ipo = purchaseRepo.indexPurchase(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = purchaseRepo.indexPurchaseSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchase> ipo = purchaseRepo.indexPurchaseSupplier(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> indexPurchaseDesc(int pageno, int pagesize, String field) {

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

				List<IndexPurchase> ipo = purchaseRepo.indexPurchase(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = purchaseRepo.indexPurchaseSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchase> ipo = purchaseRepo.indexPurchaseSupplier(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> searchByPurchase(int pageno, int pagesize, String search) {

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
				List<IndexPurchase> orders = purchaseRepo.searchByPurchase(search, p);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				List<IndexPurchase> orders = purchaseRepo.searchByPurchase(search, p, sid);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			}
		


	}
		return null;
	}
	

	@Override
	public String deletePurchaseConvert(int id) {
		
//		if (purchaseRepo.existsById(id)) {
//			Purchase purchase=purchaseRepo.findById(id).get();
//			int mrnId = purchase.getMrnId();
//			MaterialRecepitNote materialRecepitNote = materialReceiptNoteRepo.findById(mrnId).get();
//			materialRecepitNote.setStatus("pending");
//			purchaseRepo.deleteById(id);	
//			return "succesfully deleted the purchase with id "+id;
//		}
		
		 Purchase purchase = purchaseRepo.findById(id).get();
		
		 int mrnId = purchase.getMrnId();
		 
		 MaterialRecepitNote mrn = materialReceiptNoteRepo.findById(mrnId).get();
		 mrn.setStatus("pending");
		 materialReceiptNoteRepo.save(mrn);
		
		 purchaseRepo.deleteById(id);
		 return "delete converted";
		
//		return "no purchase is present with id "+id;
	}
	

}
