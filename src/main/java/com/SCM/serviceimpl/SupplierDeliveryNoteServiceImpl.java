package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.SCM.IndexDto.IndexSupplierDelivery;
import com.SCM.dto.SupplierDeliveryNoteDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.MaterialRecepitNote;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.PurchaseOrderItems;
import com.SCM.model.SupplierDeliverNoteItems;
import com.SCM.model.SupplierDeliveryNote;
import com.SCM.model.SupplierSubContacts;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.PoRepository;
import com.SCM.repository.SupplierDeliveryNoteRepo;
import com.SCM.repository.SupplierSubContactRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.SupplierDeliveryNoteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class SupplierDeliveryNoteServiceImpl implements SupplierDeliveryNoteService {

	@Autowired
	private SupplierDeliveryNoteRepo supplierDeliveryNoteRepo;
	
	@Autowired
private SupplierSubContactRepo subContactRepo;

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
	public SupplierDeliveryNote saveSupplierDeliveryNote(SupplierDeliveryNoteDto supplierDeliveryNoteDto) {

		VoucherMaster voucher = voucherMasterRepo.findById(supplierDeliveryNoteDto.getVoucherMaster().getId()).get();
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		SupplierDeliveryNote supplierDeliveryNote = new SupplierDeliveryNote();
		supplierDeliveryNote.setCreatebyname(uname);
		supplierDeliveryNote.setCreatedby(uid);
		supplierDeliveryNote.setRole(role);

		supplierDeliveryNote.setSdndate(supplierDeliveryNoteDto.getSdndate());
		supplierDeliveryNote.setTaxtype(supplierDeliveryNoteDto.getTaxtype());
		supplierDeliveryNote.setType(supplierDeliveryNoteDto.getType());
		supplierDeliveryNote.setStatus("pending");
		supplierDeliveryNote.setShippingaddress(supplierDeliveryNoteDto.getShippingaddress());
		supplierDeliveryNote.setRemarks(supplierDeliveryNoteDto.getRemarks());
		supplierDeliveryNote.setGrossamount(supplierDeliveryNoteDto.getGrossamount());
		supplierDeliveryNote.setShippingcharge(supplierDeliveryNoteDto.getShippingcharge());
		supplierDeliveryNote.setPaymentTerms(supplierDeliveryNoteDto.getPaymentTerms());
		supplierDeliveryNote.setIgst(supplierDeliveryNoteDto.getIgst());
		supplierDeliveryNote.setCgst(supplierDeliveryNoteDto.getCgst());
		supplierDeliveryNote.setSgst(supplierDeliveryNoteDto.getSgst());
		supplierDeliveryNote.setRoundofvalue(supplierDeliveryNoteDto.getRoundofvalue());
		supplierDeliveryNote.setGrandtotal(supplierDeliveryNoteDto.getGrandtotal());
//		supplierDeliveryNote.setInvoiceno(supplierDeliveryNoteDto.getInvoiceno());
		supplierDeliveryNote.setWarehouse(supplierDeliveryNoteDto.getWarehouse());
		supplierDeliveryNote.setSupplier(supplierDeliveryNoteDto.getSupplier());
		supplierDeliveryNote.setSupplierSubContacts(supplierDeliveryNoteDto.getSupplierSubContacts());
		supplierDeliveryNote.setBranch(supplierDeliveryNoteDto.getBranch());
		supplierDeliveryNote.setSupplierDeliverNoteItems(supplierDeliveryNoteDto.getSupplierDeliverNoteItems());
		supplierDeliveryNote.setCreateddate(LocalDate.now());
		supplierDeliveryNote.setCreatedtime(LocalTime.now());
		supplierDeliveryNote.setVoucherMaster(voucher);
		
	
 //     SupplierDeliveryNote sdn = supplierDeliveryNoteRepo.save(supplierDeliveryNote);
		
//		------------------------------ new voucher 

		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilnoDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilyesDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.lastrowstatus();
			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);
			

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				supplierDeliveryNote.setStartnumberwithprefilno(startingnumber);
				supplierDeliveryNote.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					supplierDeliveryNote.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					supplierDeliveryNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					supplierDeliveryNote.setStartnumberwithprefilno(restartnumber + 1);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					supplierDeliveryNote.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					supplierDeliveryNote.setStartnumberwithprefilyes(formattedStartingNumber);
					supplierDeliveryNote.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					supplierDeliveryNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					supplierDeliveryNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

//					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					supplierDeliveryNote.setStartnumberwithprefilyes(restartnumberinc);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					supplierDeliveryNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilnoDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilyesDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.lastrowstatus();
			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				supplierDeliveryNote.setStartnumberwithprefilno(startingnumber + 1);
				supplierDeliveryNote.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				supplierDeliveryNote.setStartnumberwithprefilyes(formattedStartingNumber);
				supplierDeliveryNote.setVoucherstatus("startnostatus");
			}
		}

		SupplierDeliveryNote save = supplierDeliveryNoteRepo.save(supplierDeliveryNote);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		supplierDeliveryNote.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		supplierDeliveryNoteRepo.save(supplierDeliveryNote);
		SupplierDeliveryNote deliveryNote=supplierDeliveryNoteRepo.save(supplierDeliveryNote);
		
//		deliveryNote.getSupplierDeliverNoteItems().forEach(poi -> {
//
//			int poquantity = poi.getSdnquantity();
//			poi.setpc(poquantity);
//		});
    

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSupplierdeliverynoteid((long) deliveryNote.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	
	// Convert PurchaseOrder to Supplier delivery Note

	@Override
	public SupplierDeliveryNote ConvertPotoSDN(SupplierDeliveryNoteDto supplierDeliveryNoteDto, int poId) {

		VoucherMaster voucher = voucherMasterRepo.findById(supplierDeliveryNoteDto.getVoucherMaster().getId()).get();
		
		PurchaseOrder po = poRepository.findById(poId).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		SupplierDeliveryNote supplierDeliveryNote = new SupplierDeliveryNote();
		supplierDeliveryNote.setSdndate(supplierDeliveryNoteDto.getSdndate());
		supplierDeliveryNote.setTaxtype(supplierDeliveryNoteDto.getTaxtype());
		supplierDeliveryNote.setType(supplierDeliveryNoteDto.getType());
		supplierDeliveryNote.setStatus("pending");
		supplierDeliveryNote.setShippingaddress(supplierDeliveryNoteDto.getShippingaddress());
		supplierDeliveryNote.setRemarks(supplierDeliveryNoteDto.getRemarks());
		supplierDeliveryNote.setGrossamount(supplierDeliveryNoteDto.getGrossamount());
		supplierDeliveryNote.setShippingcharge(supplierDeliveryNoteDto.getShippingcharge());
		supplierDeliveryNote.setIgst(supplierDeliveryNoteDto.getIgst());
		supplierDeliveryNote.setCgst(supplierDeliveryNoteDto.getCgst());
		supplierDeliveryNote.setSgst(supplierDeliveryNoteDto.getSgst());
		supplierDeliveryNote.setPaymentTerms(supplierDeliveryNoteDto.getPaymentTerms());
		supplierDeliveryNote.setRoundofvalue(supplierDeliveryNoteDto.getRoundofvalue());
		supplierDeliveryNote.setGrandtotal(supplierDeliveryNoteDto.getGrandtotal());
		supplierDeliveryNote.setInvoiceno(supplierDeliveryNoteDto.getInvoiceno());
		supplierDeliveryNote.setWarehouse(supplierDeliveryNoteDto.getWarehouse());
		supplierDeliveryNote.setSupplier(supplierDeliveryNoteDto.getSupplier());
		supplierDeliveryNote.setBranch(supplierDeliveryNoteDto.getBranch());
		supplierDeliveryNote.setSupplierSubContacts(supplierDeliveryNoteDto.getSupplierSubContacts());
		supplierDeliveryNote.setSupplierDeliverNoteItems(supplierDeliveryNoteDto.getSupplierDeliverNoteItems());
		supplierDeliveryNote.setPoId(po.getId());
		supplierDeliveryNote.setCreateddate(LocalDate.now());
		supplierDeliveryNote.setCreatedtime(LocalTime.now());
		supplierDeliveryNote.setCreatebyname(uname);
		supplierDeliveryNote.setCreatedby(uid);
		supplierDeliveryNote.setRole(role);
		supplierDeliveryNote.setVoucherMaster(voucher);

//		if (supplierDeliveryNoteRepo.searchByVoucher(supplierDeliveryNoteDto.getVoucherid()) == null) {
//
//			System.out.println();
//			supplierDeliveryNote.setVoucherid(supplierDeliveryNoteDto.getVoucherid());
//			supplierDeliveryNote.setVouchernumber(1);
//			String newVoucher = supplierDeliveryNoteDto.getVoucherid() + 1;
//			supplierDeliveryNote.setVoucherseries(newVoucher);
//			System.out.println("till this done1++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//		}
//		else {
//			SupplierDeliveryNote s = supplierDeliveryNoteRepo.searchByVoucher(supplierDeliveryNoteDto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser = newvid + vnum;
//			supplierDeliveryNote.setVoucherid(newvid);
//			supplierDeliveryNote.setVouchernumber(newvnum);
//			supplierDeliveryNote.setVoucherseries(newvser);
//		}
		
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilnoDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilyesDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.lastrowstatus();
			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				supplierDeliveryNote.setStartnumberwithprefilno(startingnumber);
				supplierDeliveryNote.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					supplierDeliveryNote.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					supplierDeliveryNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					supplierDeliveryNote.setStartnumberwithprefilno(restartnumber + 1);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					supplierDeliveryNote.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					supplierDeliveryNote.setStartnumberwithprefilyes(formattedStartingNumber);
					supplierDeliveryNote.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					supplierDeliveryNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					supplierDeliveryNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					supplierDeliveryNote.setStartnumberwithprefilyes(restartnumberinc);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					supplierDeliveryNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					supplierDeliveryNote.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilnoDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SupplierDeliveryNote topByVoucherOrderByStartnumberwithprefilyesDesc = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.lastrowstatus();
			SupplierDeliveryNote lastrowstatus = supplierDeliveryNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				supplierDeliveryNote.setStartnumberwithprefilno(startingnumber);
				supplierDeliveryNote.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				supplierDeliveryNote.setStartnumberwithprefilyes(formattedStartingNumber);
				supplierDeliveryNote.setVoucherstatus("startnostatus");
			}
		}

		SupplierDeliveryNote save = supplierDeliveryNoteRepo.save(supplierDeliveryNote);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		supplierDeliveryNote.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		supplierDeliveryNoteRepo.save(supplierDeliveryNote);
		SupplierDeliveryNote deliveryNote=supplierDeliveryNoteRepo.save(supplierDeliveryNote);

		
		SupplierDeliveryNote sdn = supplierDeliveryNoteRepo.save(supplierDeliveryNote);

		PurchaseOrder order = poRepository.findById(sdn.getPoId()).get();

		List<SupplierDeliverNoteItems> supplierDeliverNoteItems = sdn.getSupplierDeliverNoteItems();
		List<PurchaseOrderItems> purchaseOrderItems = order.getPurchaseOrderItems();

		for (SupplierDeliverNoteItems sdni : supplierDeliverNoteItems) {
			
			for (PurchaseOrderItems poi : purchaseOrderItems) {
				int id2 = sdni.getProduct().getId();
				int id = poi.getProduct().getId();

				if (id == id2) {
					
					float pp = poi.getRemainingpcsqty() - sdni.getSdnquantity();
					poi.setRemainingpcsqty(pp);

					poRepository.save(order);

					System.out.println(poi.getRemainingpcsqty());
					if (poi.getRemainingpcsqty() <=  sdni.getSdnaltquantity()) {
						
						System.out.println("+++++++++++++++++++++++++++");
						order.setStatus("converted");
						poRepository.save(order);
					}
				}
			}
		}
		System.out.println(order);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSupplierdeliverynoteid((long) sdn.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return sdn;
	}

	@Override
	public List<SupplierDeliveryNote> getAllSupplierDeliveryNote() {

		return supplierDeliveryNoteRepo.findAll();
	}

	@Override
	public SupplierDeliveryNote getSupplierDeliveryNoteById(int id) {

		Optional<SupplierDeliveryNote> sdn = supplierDeliveryNoteRepo.findById(id);
		SupplierDeliveryNote supplierDeliveryNote = sdn.get();

		return supplierDeliveryNote;
	}

	@Override
	public SupplierDeliveryNoteDto updateSupplierDeliveryNote(SupplierDeliveryNoteDto supplierDeliveryNoteDto, int id) {

		SupplierDeliveryNote supplierDeliveryNote = supplierDeliveryNoteRepo.findById(id).get();

		Long uid = getUserId();
		String uname = getUserName();

		String role = getRolename();

		supplierDeliveryNote.setUpdatedbyname(uname);
		supplierDeliveryNote.setUpdatedby(uid);
		supplierDeliveryNote.setUpdatedrole(role);
		supplierDeliveryNote.setUpdateddate(LocalDate.now());
		supplierDeliveryNote.setUpdatedtime(LocalTime.now());

		supplierDeliveryNote.setSdndate(supplierDeliveryNoteDto.getSdndate());
		supplierDeliveryNote.setTaxtype(supplierDeliveryNoteDto.getTaxtype());
		supplierDeliveryNote.setType(supplierDeliveryNoteDto.getType());
		supplierDeliveryNote.setStatus(supplierDeliveryNoteDto.getStatus());
		supplierDeliveryNote.setShippingaddress(supplierDeliveryNoteDto.getShippingaddress());
		supplierDeliveryNote.setRemarks(supplierDeliveryNoteDto.getRemarks());
		supplierDeliveryNote.setGrossamount(supplierDeliveryNoteDto.getGrossamount());
		supplierDeliveryNote.setShippingcharge(supplierDeliveryNoteDto.getShippingcharge());
		supplierDeliveryNote.setIgst(supplierDeliveryNoteDto.getIgst());
		supplierDeliveryNote.setCgst(supplierDeliveryNoteDto.getCgst());
		supplierDeliveryNote.setSgst(supplierDeliveryNoteDto.getSgst());
		supplierDeliveryNote.setRoundofvalue(supplierDeliveryNoteDto.getRoundofvalue());
		supplierDeliveryNote.setGrandtotal(supplierDeliveryNoteDto.getGrandtotal());
		supplierDeliveryNote.setInvoiceno(supplierDeliveryNoteDto.getInvoiceno());
		supplierDeliveryNote.setWarehouse(supplierDeliveryNoteDto.getWarehouse());
		supplierDeliveryNote.setSupplier(supplierDeliveryNoteDto.getSupplier());
		supplierDeliveryNote.setPaymentTerms(supplierDeliveryNoteDto.getPaymentTerms());
		supplierDeliveryNote.setBranch(supplierDeliveryNoteDto.getBranch());
		supplierDeliveryNote.setSupplierSubContacts(supplierDeliveryNoteDto.getSupplierSubContacts());
		supplierDeliveryNote.setSupplierDeliverNoteItems(supplierDeliveryNoteDto.getSupplierDeliverNoteItems());

		SupplierDeliveryNote savedsdn = supplierDeliveryNoteRepo.save(supplierDeliveryNote);

		SupplierDeliveryNoteDto deliveryNoteDto = new SupplierDeliveryNoteDto();
		deliveryNoteDto.setSdndate(savedsdn.getSdndate());
		deliveryNoteDto.setTaxtype(savedsdn.getTaxtype());
		deliveryNoteDto.setType(savedsdn.getType());
		deliveryNoteDto.setStatus(savedsdn.getStatus());
		deliveryNoteDto.setShippingaddress(savedsdn.getShippingaddress());
		deliveryNoteDto.setRemarks(savedsdn.getRemarks());
		deliveryNoteDto.setGrossamount(savedsdn.getGrossamount());
		deliveryNoteDto.setShippingcharge(savedsdn.getShippingcharge());
		deliveryNoteDto.setIgst(savedsdn.getIgst());
		deliveryNoteDto.setCgst(savedsdn.getCgst());
		deliveryNoteDto.setSgst(savedsdn.getSgst());
		deliveryNoteDto.setPaymentTerms(savedsdn.getPaymentTerms());
		deliveryNoteDto.setRoundofvalue(savedsdn.getRoundofvalue());
		deliveryNoteDto.setGrandtotal(savedsdn.getGrandtotal());
		deliveryNoteDto.setInvoiceno(savedsdn.getInvoiceno());
		deliveryNoteDto.setWarehouse(savedsdn.getWarehouse());
		deliveryNoteDto.setSupplier(savedsdn.getSupplier());
		deliveryNoteDto.setBranch(savedsdn.getBranch());
		deliveryNoteDto.setSupplierSubContacts(savedsdn.getSupplierSubContacts());
		deliveryNoteDto.setSupplierDeliverNoteItems(savedsdn.getSupplierDeliverNoteItems());

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setSupplierdeliverynoteid((long) savedsdn.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return deliveryNoteDto;
	}

	@Override
	public void deleteSupplierDeliveryNote(int id) {

		SupplierDeliveryNote supplierDeliveryNote = supplierDeliveryNoteRepo.findById(id).get();

		int poId = supplierDeliveryNote.getPoId();

		PurchaseOrder p = poRepository.findById(poId).get();
		p.setStatus("pending");
		poRepository.save(p);

		supplierDeliveryNoteRepo.deleteById(id);
	}

	@Override
	public void deleteSupplierDeliveryNote1(int id) {
		supplierDeliveryNoteRepo.deleteById(id);
	}

//	---------------------- sahil ---------------------------------------------//

	@Override
	public Map<String, Object> indexSupplierDeliveryDesc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN")) {

				long countpages = supplierDeliveryNoteRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexSupplierDelivery> ipo = supplierDeliveryNoteRepo.indexSupplierDelivery(p);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = supplierDeliveryNoteRepo.indexSupplierDeliveryBySupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexSupplierDelivery> ipo = supplierDeliveryNoteRepo.indexSupplierDeliveryBySupplier(p, sid);

				response.put("Index", ipo);
				response.put("CountPages", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;

	}

	@Override
	public Map<String, Object> indexSupplierDeliveryAsc(int pagno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN")) {

				long countpages = supplierDeliveryNoteRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexSupplierDelivery> ipo = supplierDeliveryNoteRepo.indexSupplierDelivery(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {

				long countpages = supplierDeliveryNoteRepo.indexSupplierDeliveryBySupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexSupplierDelivery> ipo = supplierDeliveryNoteRepo.indexSupplierDeliveryBySupplier(p, sid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;

	}

	@Override
	public Map<String, Object> searchByIndexSupplierDelivery(int pageno, int pagesize, String search) {

		Pageable p = PageRequest.of(pageno, pagesize);
		Map<String, Object> response = new HashMap<>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int sid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {
				System.out.println("admin");
				List<IndexSupplierDelivery> supplierdelivery = supplierDeliveryNoteRepo
						.searchByIndexSupplierDelivery(search, p);

				long searchcount = supplierdelivery.size();

				response.put("data", supplierdelivery);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				
				List<IndexSupplierDelivery> supplierdelivery = supplierDeliveryNoteRepo.searchByIndexSupplierDeliveryBySupplier(search, p, sid);

				long searchcount = supplierdelivery.size();

				response.put("data", supplierdelivery);
				response.put("SearchCount", searchcount);

				return response;

			}
		}

		return null;

	}

}
