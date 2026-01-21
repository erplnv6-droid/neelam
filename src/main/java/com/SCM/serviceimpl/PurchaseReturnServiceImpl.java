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

import com.SCM.IndexDto.IndexPurchase;
import com.SCM.IndexDto.IndexPurchaseReutrn;
import com.SCM.IndexDto.PurchaseReturnPageDtoProjection;
import com.SCM.dto.PurchaseReturnDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.PurchaseReturn;
import com.SCM.model.PurchaseReturnItems;
import com.SCM.model.VoucherMaster;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.PurchaseReturnRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.PurchaseReturnService;

@Service
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

	@Autowired
	private PurchaseReturnRepo purchaseReturnRepo;

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
	public PurchaseReturn savePurchaseReturn(PurchaseReturnDto purchaseReturnDto) {
		PurchaseReturn purchaseReturn = new PurchaseReturn();

		VoucherMaster voucher=voucherMasterRepo.findById(purchaseReturnDto.getVoucherMaster().getId()).get();
		
		
		Long uid = getUserId();
		String uname = getUserName();

		String role = getRolename();
		purchaseReturn.setCreatebyname(uname);
		purchaseReturn.setCreatedby(uid);
		purchaseReturn.setRole(role);

		purchaseReturn.setDebitnoteno(purchaseReturnDto.getDebitnoteno());
		purchaseReturn.setOriginalinvoiceno(purchaseReturnDto.getOriginalinvoiceno());
		purchaseReturn.setOriginalinvoicenodate(purchaseReturnDto.getOriginalinvoicenodate());
		purchaseReturn.setOtherrefernce(purchaseReturnDto.getOtherrefernce());
		purchaseReturn.setRemarks(purchaseReturnDto.getRemarks());
		purchaseReturn.setCgst(purchaseReturnDto.getCgst());
		purchaseReturn.setEwaybillno(purchaseReturnDto.getEwaybillno());
		purchaseReturn.setGrossAmount(purchaseReturnDto.getGrossAmount());
		purchaseReturn.setIgst(purchaseReturnDto.getIgst());
		purchaseReturn.setType(purchaseReturnDto.getType());
		purchaseReturn.setTaxtype(purchaseReturnDto.getTaxtype());
		purchaseReturn.setSgst(purchaseReturnDto.getSgst());
		purchaseReturn.setRoundvalue(purchaseReturnDto.getRoundvalue());
		purchaseReturn.setGrandtotal(purchaseReturnDto.getGrandtotal());
		purchaseReturn.setShippingcharge(purchaseReturnDto.getShippingcharge());
		purchaseReturn.setPurchasereturndate(purchaseReturnDto.getPurchasereturndate());
		purchaseReturn.setSdndate(purchaseReturnDto.getSdndate());
		purchaseReturn.setShippingcharge(purchaseReturnDto.getShippingcharge());
		purchaseReturn.setPaymentTerms(purchaseReturnDto.getPaymentTerms());
		purchaseReturn.setSuppliercreditno(purchaseReturnDto.getSuppliercreditno());
		purchaseReturn.setSuppliercreaditdate(purchaseReturnDto.getSuppliercreaditdate());
		purchaseReturn.setPaymentTerms(purchaseReturnDto.getPaymentTerms());
		purchaseReturn.setOriginalinvoiceno(purchaseReturnDto.getOriginalinvoiceno());
		purchaseReturn.setOtherrefernce(purchaseReturnDto.getOtherrefernce());
		purchaseReturn.setWarehouse(purchaseReturnDto.getWarehouse());
		purchaseReturn.setCompany(purchaseReturnDto.getCompany());
		purchaseReturn.setShippingaddress(purchaseReturnDto.getShippingaddress());
		purchaseReturn.setNetamount(purchaseReturnDto.getNetamount());
		purchaseReturn.setBranch(purchaseReturnDto.getBranch());
		purchaseReturn.setSupplier(purchaseReturnDto.getSupplier());
		purchaseReturn.setSupplierSubContacts(purchaseReturnDto.getSupplierSubContacts());
		purchaseReturn.setPurchaseReturnItems(purchaseReturnDto.getPurchaseReturnItems());
		

		List<PurchaseReturnItems> purchaseReturnItems = purchaseReturnDto.getPurchaseReturnItems();
		for(int i=0;i<purchaseReturnItems.size();i++)
		{
			purchaseReturnItems.get(i).setSlNo(i+1);
		}
		
		
		purchaseReturn.setInvoice_status("InACT");
		purchaseReturn.setVersion("1.1");
		purchaseReturn.setTaxSch("GST");
		purchaseReturn.setSupTyp(purchaseReturnDto.getTranDtls().getSupTyp());
		purchaseReturn.setEcmGstin(purchaseReturnDto.getTranDtls().getEcmGstin());
		purchaseReturn.setTyp("DBN");
		purchaseReturn.setNo(purchaseReturnDto.getDocDtls().getNo());
		purchaseReturn.setDt(purchaseReturnDto.getDocDtls().getDt());
		purchaseReturn.setGstin(purchaseReturnDto.getSellerDtls().getGstin());
		purchaseReturn.setLglNm(purchaseReturnDto.getSellerDtls().getLglNm());
		purchaseReturn.setAddr1(purchaseReturnDto.getSellerDtls().getAddr1());
		purchaseReturn.setLoc(purchaseReturnDto.getSellerDtls().getLoc());
		purchaseReturn.setPin(purchaseReturnDto.getSellerDtls().getPin());
		purchaseReturn.setStcd(purchaseReturnDto.getSellerDtls().getStcd());
		purchaseReturn.setPh(purchaseReturnDto.getSellerDtls().getPh());
		purchaseReturn.setEm(purchaseReturnDto.getSellerDtls().getEm());
		purchaseReturn.setBgstin(purchaseReturnDto.getBuyerDtls().getGstin());
		purchaseReturn.setBlglNm(purchaseReturnDto.getBuyerDtls().getLglNm());
		purchaseReturn.setPos(purchaseReturnDto.getBuyerDtls().getPos());
		purchaseReturn.setBaddr1(purchaseReturnDto.getBuyerDtls().getAddr1());
		purchaseReturn.setBloc(purchaseReturnDto.getBuyerDtls().getLoc());
		purchaseReturn.setBpin(purchaseReturnDto.getBuyerDtls().getPin());
		purchaseReturn.setBstcd(purchaseReturnDto.getBuyerDtls().getStcd());
		purchaseReturn.setBph(purchaseReturnDto.getBuyerDtls().getPh());
		purchaseReturn.setBem(purchaseReturnDto.getBuyerDtls().getEm());
		purchaseReturn.setAssVal(purchaseReturnDto.getValDtls().getAssVal());
		purchaseReturn.setCgstVal(purchaseReturnDto.getValDtls().getCgstVal());
		purchaseReturn.setSgstVal(purchaseReturnDto.getValDtls().getSgstVal());
		purchaseReturn.setIgstVal(purchaseReturnDto.getValDtls().getIgstVal());
		purchaseReturn.setCesVal(purchaseReturnDto.getValDtls().getCesVal());
		purchaseReturn.setStCesVal(purchaseReturnDto.getValDtls().getStCesVal());
//		salesReturn.setVdiscount(salesReturnDto.getValDtls().getDiscount());
		purchaseReturn.setVothChrg(purchaseReturnDto.getValDtls().getOthChrg());
		purchaseReturn.setTotInvVal(purchaseReturnDto.getValDtls().getTotInvVal());
	
		purchaseReturn.setCreateddate(LocalDate.now());
		purchaseReturn.setCreatedtime(LocalTime.now());
		purchaseReturn.setVoucherMaster(voucher);
		
		
//		if(purchaseReturnRepo.searchByVoucher(purchaseReturnDto.getVoucherid())==null)
//		{
//			purchaseReturn.setVoucherid(purchaseReturnDto.getVoucherid());
//			purchaseReturn.setVouchernumber(1);
//			String vseries = purchaseReturnDto.getVoucherid()+1;
//			purchaseReturn.setVoucherseries(vseries);
//		}
//		else {
//			PurchaseReturn searchByVoucher = purchaseReturnRepo.searchByVoucher(purchaseReturnDto.getVoucherid());
//			String voucherid = searchByVoucher.getVoucherid();
//			long vouchernumber = searchByVoucher.getVouchernumber();
//			long l = ++vouchernumber;
//			String newver = voucherid + vouchernumber;
//			purchaseReturn.setVoucherid(voucherid);
//			purchaseReturn.setVouchernumber(l);
//			purchaseReturn.setVoucherseries(newver);
//		}
		
//		if (purchaseReturnRepo.searchByVoucher(purchaseReturnDto.getVoucherid())==null) {
//
//			purchaseReturn.setVoucherid(purchaseReturnDto.getVoucherid());
//			purchaseReturn.setVouchernumber(1);
//			String newVoucher=purchaseReturnDto.getVoucherid()+1;
//			purchaseReturn.setVoucherseries(newVoucher);
//			System.out.println("till this done1++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//		}
//		else {
//			 PurchaseReturn searchByVoucher = purchaseReturnRepo.searchByVoucher(purchaseReturnDto.getVoucherid());
//			String newvid = searchByVoucher.getVoucherid();
//			long vnum = searchByVoucher.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser = newvid+vnum;
//			purchaseReturn.setVoucherid(newvid);
//			purchaseReturn.setVouchernumber(newvnum);
//			purchaseReturn.setVoucherseries(newvser);
//			System.out.println("till this done2++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
//
//		}

		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			PurchaseReturn topByVoucherOrderByStartnumberwithprefilnoDesc = purchaseReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			PurchaseReturn topByVoucherOrderByStartnumberwithprefilyesDesc = purchaseReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			PurchaseReturn lastrowstatus = purchaseReturnRepo.lastrowstatus();
			PurchaseReturn lastrowstatus = purchaseReturnRepo.findTopByVoucherMasterOrderByIdDesc(voucher);
			

			String status = lastrowstatus.getVoucherstatus();
			if (voucher.getPrefil().equals("No")) {
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				purchaseReturn.setStartnumberwithprefilno(startingnumber);
				purchaseReturn.setVoucherstatus("startnostatus");
			}

		
if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					purchaseReturn.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					purchaseReturn.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					purchaseReturn.setStartnumberwithprefilno(restartnumber + 1);
					purchaseReturn.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					purchaseReturn.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					purchaseReturn.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || (voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					purchaseReturn.setStartnumberwithprefilyes(formattedStartingNumber);
					purchaseReturn.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					purchaseReturn.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					purchaseReturn.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

//					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					purchaseReturn.setStartnumberwithprefilyes(restartnumberinc);
					purchaseReturn.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					purchaseReturn.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					purchaseReturn.setVoucherstatus("restartnostatus");
				}
			}
			
		} catch (Exception e) {

			PurchaseReturn topByVoucherOrderByStartnumberwithprefilnoDesc = purchaseReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			PurchaseReturn topByVoucherOrderByStartnumberwithprefilyesDesc = purchaseReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			PurchaseReturn lastrowstatus = purchaseReturnRepo.lastrowstatus();
			PurchaseReturn lastrowstatus = purchaseReturnRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				purchaseReturn.setStartnumberwithprefilno(startingnumber);
				purchaseReturn.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				purchaseReturn.setStartnumberwithprefilyes(formattedStartingNumber);
				purchaseReturn.setVoucherstatus("startnostatus");
			}
		}

		PurchaseReturn save = purchaseReturnRepo.save(purchaseReturn);
////
		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		purchaseReturn.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		purchaseReturnRepo.save(purchaseReturn);
		
		
		
		

		PurchaseReturn pr = purchaseReturnRepo.save(purchaseReturn);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setPurchasereturnid((long) pr.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	@Override
	public List<PurchaseReturn> getAllPurchaseReturn() {

		return purchaseReturnRepo.findAll();
	}

	@Override
	public PurchaseReturn getPurchaseReturnById(int id) {

		Optional<PurchaseReturn> purchasereturn = purchaseReturnRepo.findById(id);
	
		return purchasereturn.get();
	}

	@Override
	public void deletePurchaseReturn(int id) {
		purchaseReturnRepo.deleteById(id);

	}

	@Override
	public PurchaseReturnDto updatePurchaseReturn(PurchaseReturnDto purchaseReturnDto, int id) {

		PurchaseReturn purchaseReturn = purchaseReturnRepo.findById(id).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		purchaseReturn.setUpdatedbyname(uname);
		purchaseReturn.setUpdatedby(uid);
		purchaseReturn.setUpdatedrole(role);
		purchaseReturn.setUpdateddate(LocalDate.now());
		purchaseReturn.setUpdatedtime(LocalTime.now());

		purchaseReturn.setCgst(purchaseReturnDto.getCgst());
		purchaseReturn.setEwaybillno(purchaseReturnDto.getEwaybillno());
		purchaseReturn.setGrossAmount(purchaseReturnDto.getGrossAmount());
		purchaseReturn.setIgst(purchaseReturnDto.getIgst());
		purchaseReturn.setType(purchaseReturnDto.getType());
		purchaseReturn.setTaxtype(purchaseReturnDto.getTaxtype());
		purchaseReturn.setSgst(purchaseReturnDto.getSgst());
		purchaseReturn.setRoundvalue(purchaseReturnDto.getRoundvalue());
		purchaseReturn.setGrandtotal(purchaseReturnDto.getGrandtotal());
		purchaseReturn.setShippingcharge(purchaseReturnDto.getShippingcharge());
		purchaseReturn.setPurchasereturndate(purchaseReturnDto.getPurchasereturndate());
		purchaseReturn.setSdndate(purchaseReturnDto.getSdndate());
		purchaseReturn.setPaymentTerms(purchaseReturnDto.getPaymentTerms());
		purchaseReturn.setOtherrefernce(purchaseReturnDto.getOtherrefernce());
		purchaseReturn.setShippingaddress(purchaseReturnDto.getShippingaddress());
		purchaseReturn.setPaymentTerms(purchaseReturnDto.getPaymentTerms());
		purchaseReturn.setOriginalinvoiceno(purchaseReturnDto.getOriginalinvoiceno());
		purchaseReturn.setSuppliercreditno(purchaseReturnDto.getSuppliercreditno());
		purchaseReturn.setSuppliercreaditdate(purchaseReturnDto.getSuppliercreaditdate());
		purchaseReturn.setWarehouse(purchaseReturnDto.getWarehouse());
		purchaseReturn.setBranch(purchaseReturnDto.getBranch());
		purchaseReturn.setSupplier(purchaseReturnDto.getSupplier());
		purchaseReturn.setSupplierSubContacts(purchaseReturnDto.getSupplierSubContacts());
		purchaseReturn.setPurchaseReturnItems(purchaseReturnDto.getPurchaseReturnItems());
		List<PurchaseReturnItems> purchaseReturnItems = purchaseReturnDto.getPurchaseReturnItems();
		for(int i=0;i<purchaseReturnItems.size();i++)
		{
			purchaseReturnItems.get(i).setSlNo(i+1);
		}
		
		
		purchaseReturn.setInvoice_status("InACT");
		purchaseReturn.setVersion("1.1");
		purchaseReturn.setTaxSch("GST");
		purchaseReturn.setSupTyp(purchaseReturnDto.getTranDtls().getSupTyp());
		purchaseReturn.setEcmGstin(purchaseReturnDto.getTranDtls().getEcmGstin());
		purchaseReturn.setTyp("DBN");
		purchaseReturn.setNo(purchaseReturnDto.getDocDtls().getNo());
		purchaseReturn.setDt(purchaseReturnDto.getDocDtls().getDt());
		purchaseReturn.setGstin(purchaseReturnDto.getSellerDtls().getGstin());
		purchaseReturn.setLglNm(purchaseReturnDto.getSellerDtls().getLglNm());
		purchaseReturn.setAddr1(purchaseReturnDto.getSellerDtls().getAddr1());
		purchaseReturn.setLoc(purchaseReturnDto.getSellerDtls().getLoc());
		purchaseReturn.setPin(purchaseReturnDto.getSellerDtls().getPin());
		purchaseReturn.setStcd(purchaseReturnDto.getSellerDtls().getStcd());
		purchaseReturn.setPh(purchaseReturnDto.getSellerDtls().getPh());
		purchaseReturn.setEm(purchaseReturnDto.getSellerDtls().getEm());
		purchaseReturn.setBgstin(purchaseReturnDto.getBuyerDtls().getGstin());
		purchaseReturn.setBlglNm(purchaseReturnDto.getBuyerDtls().getLglNm());
		purchaseReturn.setPos(purchaseReturnDto.getBuyerDtls().getPos());
		purchaseReturn.setBaddr1(purchaseReturnDto.getBuyerDtls().getAddr1());
		purchaseReturn.setBloc(purchaseReturnDto.getBuyerDtls().getLoc());
		purchaseReturn.setBpin(purchaseReturnDto.getBuyerDtls().getPin());
		purchaseReturn.setBstcd(purchaseReturnDto.getBuyerDtls().getStcd());
		purchaseReturn.setBph(purchaseReturnDto.getBuyerDtls().getPh());
		purchaseReturn.setBem(purchaseReturnDto.getBuyerDtls().getEm());
		purchaseReturn.setAssVal(purchaseReturnDto.getValDtls().getAssVal());
		purchaseReturn.setCgstVal(purchaseReturnDto.getValDtls().getCgstVal());
		 purchaseReturn.setSgstVal(purchaseReturnDto.getValDtls().getSgstVal());
		purchaseReturn.setIgstVal(purchaseReturnDto.getValDtls().getIgstVal());
		purchaseReturn.setCesVal(purchaseReturnDto.getValDtls().getCesVal());
		purchaseReturn.setStCesVal(purchaseReturnDto.getValDtls().getStCesVal());
//		salesReturn.setVdiscount(salesReturnDto.getValDtls().getDiscount());
		purchaseReturn.setVothChrg(purchaseReturnDto.getValDtls().getOthChrg());
		purchaseReturn.setTotInvVal(purchaseReturnDto.getValDtls().getTotInvVal());
		
		

		PurchaseReturn savepr = purchaseReturnRepo.save(purchaseReturn);

		PurchaseReturnDto purchaseReturnDto2 = new PurchaseReturnDto();

//		purchaseReturnDto2.setCgst(savepr.getCgst());
//		purchaseReturnDto2.setEwaybillno(savepr.getEwaybillno());
//		purchaseReturnDto2.setGrossAmount(savepr.getGrossAmount());
//		purchaseReturnDto2.setIgst(savepr.getIgst());
//		purchaseReturnDto2.setType(savepr.getType());
//		purchaseReturnDto2.setTaxtype(savepr.getTaxtype());
//		purchaseReturnDto2.setSgst(savepr.getSgst());
//		purchaseReturnDto2.setGrandtotal(savepr.getGrandtotal());
//		purchaseReturnDto2.setShippingcharge(savepr.getShippingcharge());
//		purchaseReturnDto2.setRoundvalue(savepr.getRoundvalue());
//		purchaseReturnDto2.setShippingaddress(savepr.getShippingaddress());
//		purchaseReturnDto2.setPurchasereturndate(savepr.getPurchasereturndate());
//		purchaseReturnDto2.setSdndate(savepr.getSdndate());
//		purchaseReturnDto2.setPaymentTerms(savepr.getPaymentTerms());
//		purchaseReturnDto2.setOtherrefernce(savepr.getOtherrefernce());
//		purchaseReturnDto2.setOriginalinvoiceno(savepr.getOriginalinvoiceno());
//		purchaseReturnDto2.setPaymentTerms(savepr.getPaymentTerms());
//		purchaseReturnDto2.setSuppliercreditno(savepr.getSuppliercreditno());
//		purchaseReturnDto2.setSuppliercreaditdate(savepr.getSuppliercreaditdate());
//		purchaseReturnDto2.setWarehouse(savepr.getWarehouse());
//		purchaseReturnDto2.setSupplier(savepr.getSupplier());
//		purchaseReturnDto2.setSupplierSubContacts(savepr.getSupplierSubContacts());
//		purchaseReturnDto2.setPurchaseReturnItems(savepr.getPurchaseReturnItems());
//		List<PurchaseReturnItems> purchaseReturnItems = purchaseReturnDto.getPurchaseReturnItems();
//		for(int i=0;i<purchaseReturnItems.size();i++)
//		{
//			purchaseReturnItems.get(i).setSlNo(i+1);
//		}
//		
//		

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setPurchasereturnid((long) savepr.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return purchaseReturnDto2;
	}

	@Override
	public Map<String, Object> indexPurchaseReturnAsc(int pageno, int pagesize, String field) {

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

				long countpages = purchaseReturnRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseReutrn> ipo = purchaseReturnRepo.indexPurchaseReturn(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages =purchaseReturnRepo.indexPurchaseReturnSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseReutrn> ipo = purchaseReturnRepo.indexPurchaseReturnSupplier(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> indexPurchaseReturnDesc(int pageno, int pagesize, String field) {

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

				long countpages = purchaseReturnRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseReutrn> ipo = purchaseReturnRepo.indexPurchaseReturn(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_SUPPLIER")) {
				long countpages =purchaseReturnRepo.indexPurchaseReturnSupplier(sid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPurchaseReutrn> ipo = purchaseReturnRepo.indexPurchaseReturnSupplier(sid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> searchByPurchaseReturn(int pageno, int pagesize, String search) {

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
				List<IndexPurchaseReutrn> orders = purchaseReturnRepo.searchByPurchaseReturn(search, p);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_SUPPLIER")) {
				List<IndexPurchaseReutrn> orders = purchaseReturnRepo.searchByPurchaseReturn(search, p, sid);

				long searchcount = orders.size();

				response.put("data", orders);
				response.put("SearchCount", searchcount);

				return response;

			}
		


	}
		return null;

}
}
