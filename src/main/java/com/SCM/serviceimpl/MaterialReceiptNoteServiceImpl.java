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

import com.SCM.IndexDto.IndexMaterialNote;
import com.SCM.IndexDto.IndexPurchaseOrder;
import com.SCM.IndexDto.MaterialReceiptProjection;
import com.SCM.dto.MaterialRecepitNoteDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.MaterialRecepitNote;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.SupplierDeliveryNote;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.MaterialReceiptNoteRepo;
import com.SCM.repository.SupplierDeliveryNoteRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.MaterialReceiptNoteService;

@Service
public class MaterialReceiptNoteServiceImpl implements MaterialReceiptNoteService {

	@Autowired
	private MaterialReceiptNoteRepo materialReceiptNoteRepo;

	@Autowired
	private SupplierDeliveryNoteRepo supplierDeliveryNoteRepo;

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
		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	
	@Override
	public MaterialRecepitNote saveMaterialRecepitNote(MaterialRecepitNoteDto materialRecepitNoteDto) {

		VoucherMaster voucher = voucherMasterRepo.findById(materialRecepitNoteDto.getVoucherMaster().getId()).get();
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		MaterialRecepitNote materialRecepitNote = new MaterialRecepitNote();
		materialRecepitNote.setCreatebyname(uname);
		materialRecepitNote.setCreatedby(uid);
		materialRecepitNote.setRole(role);
		materialRecepitNote.setMrndate(materialRecepitNoteDto.getMrndate());
		materialRecepitNote.setOtherreference(materialRecepitNoteDto.getOtherreference());
		materialRecepitNote.setReceiptdate(materialRecepitNoteDto.getReceiptdate());
		materialRecepitNote.setReceiptnoteno(materialRecepitNoteDto.getReceiptnoteno());
		materialRecepitNote.setTaxtype(materialRecepitNoteDto.getTaxtype());
		materialRecepitNote.setRefernceno(materialRecepitNoteDto.getRefernceno());
		materialRecepitNote.setType(materialRecepitNoteDto.getType());
		materialRecepitNote.setStatus("pending");
		materialRecepitNote.setGrossamount(materialRecepitNoteDto.getGrossamount());
		materialRecepitNote.setIgst(materialRecepitNoteDto.getIgst());
		materialRecepitNote.setCgst(materialRecepitNoteDto.getCgst());
		materialRecepitNote.setSgst(materialRecepitNoteDto.getSgst());
		materialRecepitNote.setRoundofvalue(materialRecepitNoteDto.getRoundofvalue());
		materialRecepitNote.setShippingcharge(materialRecepitNoteDto.getShippingcharge());
		materialRecepitNote.setCompany(materialRecepitNoteDto.getCompany());
		materialRecepitNote.setPaymentTerms(materialRecepitNoteDto.getPaymentTerms());
		materialRecepitNote.setGrandtotal(materialRecepitNoteDto.getGrandtotal());
		materialRecepitNote.setWarehouse(materialRecepitNoteDto.getWarehouse());
		materialRecepitNote.setSupplier(materialRecepitNoteDto.getSupplier());
		materialRecepitNote.setBranch(materialRecepitNoteDto.getBranch());
		materialRecepitNote.setRemarks(materialRecepitNoteDto.getRemarks());
		materialRecepitNote.setShippingAddress(materialRecepitNoteDto.getShippingAddress());
		materialRecepitNote.setSupplierSubContacts(materialRecepitNoteDto.getSupplierSubContacts());
		materialRecepitNote.setEinvoiceno(materialRecepitNoteDto.getEinvoiceno());
		materialRecepitNote.setEwaybillno(materialRecepitNoteDto.getEwaybillno());
		materialRecepitNote.setMaterialReceiptNoteItems(materialRecepitNoteDto.getMaterialReceiptNoteItems());
		materialRecepitNote.setCreateddate(LocalDate.now());
		materialRecepitNote.setCreatedtime(LocalTime.now());
		materialRecepitNote.setVoucherMaster(voucher);

//		if (materialReceiptNoteRepo.searchByVoucher(materialRecepitNoteDto.getVoucherid())==null) {
////			salesdto.getVoucherid()
//			System.out.println();
//			materialRecepitNote.setVoucherid(materialRecepitNoteDto.getVoucherid());
//			materialRecepitNote.setVouchernumber(1);
//			String newVoucher=materialRecepitNoteDto.getVoucherid()+1;
//			materialRecepitNote.setVoucherseries(newVoucher);
//			System.out.println("till this done1++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//
//		}
//		
//		else {
//			MaterialRecepitNote s = materialReceiptNoteRepo.searchByVoucher(materialRecepitNoteDto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser=newvid+vnum;
//			materialRecepitNote.setVoucherid(newvid);
//			materialRecepitNote.setVouchernumber(newvnum);
//			materialRecepitNote.setVoucherseries(newvser);
//			System.out.println("till this done2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//
//		}
//		
		
		
		
//		------------------------------ new voucher 

		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilnoDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilyesDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.lastrowstatus();
			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				materialRecepitNote.setStartnumberwithprefilno(startingnumber);
				materialRecepitNote.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					materialRecepitNote.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					materialRecepitNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					materialRecepitNote.setStartnumberwithprefilno(restartnumber + 1);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					materialRecepitNote.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					materialRecepitNote.setStartnumberwithprefilyes(formattedStartingNumber);
					materialRecepitNote.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					materialRecepitNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					materialRecepitNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

//					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					materialRecepitNote.setStartnumberwithprefilyes(restartnumberinc);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					materialRecepitNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilnoDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilyesDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.lastrowstatus();
			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				materialRecepitNote.setStartnumberwithprefilno(startingnumber);
				materialRecepitNote.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				materialRecepitNote.setStartnumberwithprefilyes(formattedStartingNumber);
				materialRecepitNote.setVoucherstatus("startnostatus");
			}
		}

		MaterialRecepitNote save = materialReceiptNoteRepo.save(materialRecepitNote);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		materialRecepitNote.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		materialReceiptNoteRepo.save(materialRecepitNote);
    
		
		MaterialRecepitNote mrn =  materialReceiptNoteRepo.save(materialRecepitNote);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setMaterialreceiptnoteid((long) mrn.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	// convert SupplierDeliveryNote to MaterialReceiptNote

	@Override
	public MaterialRecepitNote ConvertoMRNSupplierdeliverynote(MaterialRecepitNoteDto materialRecepitNoteDto,int supplierdeliverynoteId) {

		VoucherMaster voucher = voucherMasterRepo.findById(materialRecepitNoteDto.getVoucherMaster().getId()).get();
		
		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		Optional<SupplierDeliveryNote> supplierdeliverynote = supplierDeliveryNoteRepo.findById(supplierdeliverynoteId);
		SupplierDeliveryNote sdn = supplierdeliverynote.get();

		MaterialRecepitNote materialRecepitNote = new MaterialRecepitNote();
		materialRecepitNote.setCreatebyname(uname);
		materialRecepitNote.setCreatedby(uid);
		materialRecepitNote.setRole(role);
		materialRecepitNote.setMrndate(materialRecepitNoteDto.getMrndate());
		materialRecepitNote.setOtherreference(materialRecepitNoteDto.getOtherreference());
		materialRecepitNote.setReceiptdate(materialRecepitNoteDto.getReceiptdate());
		materialRecepitNote.setReceiptnoteno(materialRecepitNoteDto.getReceiptnoteno());
		materialRecepitNote.setRefernceno(materialRecepitNoteDto.getRefernceno());
		materialRecepitNote.setTaxtype(materialRecepitNoteDto.getTaxtype());
		materialRecepitNote.setType(materialRecepitNoteDto.getType());
		materialRecepitNote.setGrossamount(materialRecepitNoteDto.getGrossamount());
		materialRecepitNote.setIgst(materialRecepitNoteDto.getIgst());
		materialRecepitNote.setCgst(materialRecepitNoteDto.getCgst());
		materialRecepitNote.setSgst(materialRecepitNoteDto.getSgst());
		materialRecepitNote.setRoundofvalue(materialRecepitNoteDto.getRoundofvalue());
		materialRecepitNote.setGrandtotal(materialRecepitNoteDto.getGrandtotal());
		materialRecepitNote.setShippingAddress(materialRecepitNoteDto.getShippingAddress());
		materialRecepitNote.setPaymentTerms(materialRecepitNoteDto.getPaymentTerms());
		materialRecepitNote.setCompany(materialRecepitNoteDto.getCompany());
		materialRecepitNote.setWarehouse(materialRecepitNoteDto.getWarehouse());
		materialRecepitNote.setShippingcharge(materialRecepitNoteDto.getShippingcharge());
		materialRecepitNote.setSupplier(materialRecepitNoteDto.getSupplier());
		materialRecepitNote.setBranch(materialRecepitNoteDto.getBranch());
		materialRecepitNote.setStatus("pending");
		materialRecepitNote.setEinvoiceno(materialRecepitNoteDto.getEinvoiceno());
		materialRecepitNote.setEwaybillno(materialRecepitNoteDto.getEwaybillno());
		materialRecepitNote.setShippingAddress(materialRecepitNoteDto.getShippingAddress());
		materialRecepitNote.setRemarks(materialRecepitNoteDto.getRemarks());
		materialRecepitNote.setSupplierSubContacts(materialRecepitNoteDto.getSupplierSubContacts());
		materialRecepitNote.setMaterialReceiptNoteItems(materialRecepitNoteDto.getMaterialReceiptNoteItems());
		materialRecepitNote.setSupplierdeliverynote_id(sdn.getId());
		materialRecepitNote.setCreateddate(LocalDate.now());
		materialRecepitNote.setCreatedtime(LocalTime.now());
		materialRecepitNote.setVoucherMaster(voucher);
		System.out.println(uname);
		System.out.println(uid);
		System.out.println(role);
		
//		if (materialReceiptNoteRepo.searchByVoucher(materialRecepitNoteDto.getVoucherid())==null) {
////			salesdto.getVoucherid()
//			System.out.println();
//			materialRecepitNote.setVoucherid(materialRecepitNoteDto.getVoucherid());
//			materialRecepitNote.setVouchernumber(1);
//			String newVoucher=materialRecepitNoteDto.getVoucherid()+1;
//			materialRecepitNote.setVoucherseries(newVoucher);
//			System.out.println("till this done1++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//
//		}
//		
//		else {
//			MaterialRecepitNote s = materialReceiptNoteRepo.searchByVoucher(materialRecepitNoteDto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser=newvid+vnum;
//			materialRecepitNote.setVoucherid(newvid);
//			materialRecepitNote.setVouchernumber(newvnum);
//			materialRecepitNote.setVoucherseries(newvser);
//			System.out.println("till this done2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//
//		}
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilnoDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilyesDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.lastrowstatus();
			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				materialRecepitNote.setStartnumberwithprefilno(startingnumber);
				materialRecepitNote.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					materialRecepitNote.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					materialRecepitNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					materialRecepitNote.setStartnumberwithprefilno(restartnumber + 1);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					materialRecepitNote.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					materialRecepitNote.setStartnumberwithprefilyes(formattedStartingNumber);
					materialRecepitNote.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					materialRecepitNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					materialRecepitNote.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					materialRecepitNote.setStartnumberwithprefilyes(restartnumberinc);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					materialRecepitNote.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					materialRecepitNote.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilnoDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			MaterialRecepitNote topByVoucherOrderByStartnumberwithprefilyesDesc = materialReceiptNoteRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.lastrowstatus();
			MaterialRecepitNote lastrowstatus = materialReceiptNoteRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				materialRecepitNote.setStartnumberwithprefilno(startingnumber);
				materialRecepitNote.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				materialRecepitNote.setStartnumberwithprefilyes(formattedStartingNumber);
				materialRecepitNote.setVoucherstatus("startnostatus");
			}
		}

		MaterialRecepitNote save = materialReceiptNoteRepo.save(materialRecepitNote);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		materialRecepitNote.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		materialReceiptNoteRepo.save(materialRecepitNote);
    
		
		MaterialRecepitNote mrn =  materialReceiptNoteRepo.save(materialRecepitNote);


		
		SupplierDeliveryNote deliveryNote  = supplierDeliveryNoteRepo.findById(mrn.getSupplierdeliverynote_id()).get();
		deliveryNote.setStatus("converted");
		supplierDeliveryNoteRepo.save(deliveryNote);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setMaterialreceiptnoteid((long) mrn.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return mrn;
	}

	@Override
	public List<MaterialRecepitNote> getAllMaterialRecepitNote() {

		return materialReceiptNoteRepo.findAll();
	}

	@Override
	public MaterialRecepitNote getMaterialRecepitNoteById(int id) {

		MaterialRecepitNote mrn = materialReceiptNoteRepo.findById(id).get();

		return mrn;
	}

	@Override
	public void deleteMaterialRecepitNote(int id) {

		MaterialRecepitNote materialRecepitNote = materialReceiptNoteRepo.findById(id).get();

		int supplierdeliverynote_id = materialRecepitNote.getSupplierdeliverynote_id();

		SupplierDeliveryNote byId = supplierDeliveryNoteRepo.findById(supplierdeliverynote_id).get();
		byId.setStatus("pending");
		supplierDeliveryNoteRepo.save(byId);

		materialReceiptNoteRepo.deleteById(id);
	}

	
	@Override
	public void deleteMaterialRecepitNote1(int id) {
		
		materialReceiptNoteRepo.deleteById(id);		
	}
	
	
	@Override
	public MaterialRecepitNoteDto updateMaterialRecepitNote(MaterialRecepitNoteDto materialRecepitNoteDto, int id) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		MaterialRecepitNote materialRecepitNote = materialReceiptNoteRepo.findById(id).get();

		materialRecepitNote.setUpdatedbyname(uname);
		materialRecepitNote.setUpdatedby(uid);
		materialRecepitNote.setUpdatedrole(role);
		materialRecepitNote.setUpdateddate(LocalDate.now());
		materialRecepitNote.setUpdatedtime(LocalTime.now());

		materialRecepitNote.setMrndate(materialRecepitNoteDto.getMrndate());
		materialRecepitNote.setOtherreference(materialRecepitNoteDto.getOtherreference());
		materialRecepitNote.setReceiptdate(materialRecepitNoteDto.getReceiptdate());
		materialRecepitNote.setReceiptnoteno(materialRecepitNoteDto.getReceiptnoteno());
		materialRecepitNote.setRefernceno(materialRecepitNoteDto.getRefernceno());
		materialRecepitNote.setTaxtype(materialRecepitNoteDto.getTaxtype());
		materialRecepitNote.setType(materialRecepitNoteDto.getType());
		materialRecepitNote.setStatus(materialRecepitNoteDto.getStatus());
		materialRecepitNote.setGrossamount(materialRecepitNoteDto.getGrossamount());
		materialRecepitNote.setIgst(materialRecepitNoteDto.getIgst());
		materialRecepitNote.setCompany(materialRecepitNoteDto.getCompany());
		materialRecepitNote.setCgst(materialRecepitNoteDto.getCgst());
		materialRecepitNote.setSgst(materialRecepitNoteDto.getSgst());
		materialRecepitNote.setShippingAddress(materialRecepitNoteDto.getShippingAddress());
		materialRecepitNote.setPaymentTerms(materialRecepitNoteDto.getPaymentTerms());
		materialRecepitNote.setRoundofvalue(materialRecepitNoteDto.getRoundofvalue());
		materialRecepitNote.setPaymentTerms(materialRecepitNoteDto.getPaymentTerms());
		materialRecepitNote.setGrandtotal(materialRecepitNoteDto.getGrandtotal());
		materialRecepitNote.setEinvoiceno(materialRecepitNoteDto.getEinvoiceno());
		materialRecepitNote.setEwaybillno(materialRecepitNoteDto.getEwaybillno());
		materialRecepitNote.setShippingcharge(materialRecepitNoteDto.getShippingcharge());
		materialRecepitNote.setWarehouse(materialRecepitNoteDto.getWarehouse());
		materialRecepitNote.setSupplier(materialRecepitNoteDto.getSupplier());
		materialRecepitNote.setBranch(materialRecepitNoteDto.getBranch());
		materialRecepitNote.setSupplierSubContacts(materialRecepitNoteDto.getSupplierSubContacts());
		materialRecepitNote.setMaterialReceiptNoteItems(materialRecepitNoteDto.getMaterialReceiptNoteItems());
		materialRecepitNote.setRemarks(materialRecepitNoteDto.getRemarks());
		MaterialRecepitNote savedmrn = materialReceiptNoteRepo.save(materialRecepitNote);

		MaterialRecepitNoteDto noteDto = new MaterialRecepitNoteDto();
		noteDto.setMrndate(savedmrn.getMrndate());
		noteDto.setOtherreference(savedmrn.getOtherreference());
		noteDto.setReceiptdate(savedmrn.getReceiptdate());
		noteDto.setReceiptnoteno(savedmrn.getReceiptnoteno());
		noteDto.setRefernceno(savedmrn.getRefernceno());
		noteDto.setTaxtype(savedmrn.getTaxtype());
		noteDto.setType(savedmrn.getType());
		noteDto.setStatus(savedmrn.getStatus());
		noteDto.setGrossamount(savedmrn.getGrossamount());
		noteDto.setCompany(savedmrn.getCompany());
		noteDto.setIgst(savedmrn.getIgst());
		noteDto.setCgst(savedmrn.getCgst());
		noteDto.setSgst(savedmrn.getSgst());
		noteDto.setShippingAddress(savedmrn.getShippingAddress());
		noteDto.setPaymentTerms(savedmrn.getPaymentTerms());
		noteDto.setPaymentTerms(savedmrn.getPaymentTerms());
		noteDto.setRoundofvalue(savedmrn.getRoundofvalue());
		noteDto.setGrandtotal(savedmrn.getGrandtotal());
		noteDto.setEinvoiceno(savedmrn.getEinvoiceno());
		noteDto.setEwaybillno(savedmrn.getEwaybillno());
		noteDto.setShippingcharge(savedmrn.getShippingcharge());
		noteDto.setWarehouse(savedmrn.getWarehouse());
		noteDto.setSupplier(savedmrn.getSupplier());
		noteDto.setBranch(savedmrn.getBranch());
		noteDto.setSupplierSubContacts(savedmrn.getSupplierSubContacts());
		noteDto.setRemarks(savedmrn.getRemarks());
		noteDto.setMaterialReceiptNoteItems(savedmrn.getMaterialReceiptNoteItems());

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setMaterialreceiptnoteid((long) savedmrn.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return noteDto;
	}

	@Override
	public List<MaterialRecepitNote> getMaterialRecepitNoteBySupplierdeliveryId(int supplierdeliverynoteId) {

		List<MaterialRecepitNote> materialreceipt = materialReceiptNoteRepo.fetchMRNBySDN(supplierdeliverynoteId);

		return materialreceipt;
	}

	@Override
	public Map<String, Object> indexMaterialReceiptAsc(int pageno, int pagesize, String field) {

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

				long countpages = materialReceiptNoteRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMaterialNote> ipo = materialReceiptNoteRepo.indexMaterialReceipt(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = materialReceiptNoteRepo.indexMaterialReceiptSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMaterialNote> ipo = materialReceiptNoteRepo.indexMaterialReceiptSupplier(p,sid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> indexMaterialReceiptDesc(int pageno, int pagesize, String field) {

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

				long countpages = materialReceiptNoteRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMaterialNote> ipo = materialReceiptNoteRepo.indexMaterialReceipt(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = materialReceiptNoteRepo.indexMaterialReceiptSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMaterialNote> ipo = materialReceiptNoteRepo.indexMaterialReceiptSupplier(p,sid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;

	}

	@Override
	public Map<String, Object> searchByMaterialReceipt(int pageno, int pagesize, String search) {

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

				long countpages = materialReceiptNoteRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMaterialNote> ipo = materialReceiptNoteRepo.searchByMaterialReceipt(search, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			else if (s.equals("ROLE_SUPPLIER")) {
				long countpages = materialReceiptNoteRepo.searchByMaterialReceipt(search, p).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexMaterialNote> ipo = materialReceiptNoteRepo.searchByMaterialReceiptSupplier(search, p, sid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
}
}
