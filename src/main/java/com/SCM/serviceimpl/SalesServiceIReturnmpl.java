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

import com.SCM.dto.SalesReturnDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Retailer;
import com.SCM.model.SalesOrder;
import com.SCM.model.SalesReturn;
import com.SCM.model.SalesReturnItems;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.SalesReturnProjection;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.SalesReturnRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.SalesReturnService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesServiceIReturnmpl implements SalesReturnService {

	@Autowired
	private SalesReturnRepo salesReturnRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private RetailerRepo retailerRepo;
	
	@Autowired
	private VoucherMasterRepo voucherMasterRepo;

	@Autowired
	ObjectMapper mapper;


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
	public SalesReturn saveSalesReturn(SalesReturnDto salesReturnDto) {

		VoucherMaster voucher=voucherMasterRepo.findById(salesReturnDto.getVoucherMaster().getId()).get();
		
		SalesReturn salesReturn = new SalesReturn();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		salesReturn.setCreatebyname(uname);
		salesReturn.setCreatedby(uid);
		salesReturn.setRole(role);
		salesReturn.setIrnno(salesReturnDto.getIrnno());
		salesReturn.setAckno(salesReturnDto.getAckno());
		salesReturn.setAckdate(salesReturnDto.getAckdate());

		salesReturn.setBuyerorderno(salesReturnDto.getBuyerorderno());
		salesReturn.setCreditnoteno(salesReturnDto.getCreditnoteno());
		salesReturn.setDated(salesReturnDto.getDated());
		salesReturn.setDeclaration(salesReturnDto.getDeclaration());
		salesReturn.setDestination(salesReturnDto.getDestination());
		salesReturn.setDispatcheddocno(salesReturnDto.getDispatcheddocno());
		salesReturn.setDispatchedthrough(salesReturnDto.getDispatchedthrough());
		salesReturn.setInvoicedate(salesReturnDto.getInvoicedate());
		salesReturn.setOtherrefernce(salesReturnDto.getOtherrefernce());
		salesReturn.setTermsofdelivery(salesReturnDto.getTermsofdelivery());
		salesReturn.setTermsofpayment(salesReturnDto.getTermsofpayment());
		salesReturn.setCgst(salesReturnDto.getCgst());
		salesReturn.setGrandtotal(salesReturnDto.getGrandtotal());
		salesReturn.setGrossAmount(salesReturnDto.getGrossAmount());
		salesReturn.setIgst(salesReturnDto.getIgst());
		salesReturn.setType(salesReturnDto.getType());
		salesReturn.setSgst(salesReturnDto.getSgst());
		salesReturn.setPaymentTerms(salesReturnDto.getPaymentTerms());
		salesReturn.setInvoiceno(salesReturnDto.getInvoiceno());
		salesReturn.setOtherrefernce(salesReturnDto.getOtherrefernce());
		salesReturn.setShippingcharge(salesReturnDto.getShippingcharge());
		salesReturn.setSalesreturndate(salesReturnDto.getSalesreturndate());
		salesReturn.setRoundvalue(salesReturnDto.getRoundvalue());
		salesReturn.setInvoiceno(salesReturnDto.getInvoiceno());
		salesReturn.setIgst(salesReturnDto.getIgst());
		salesReturn.setGrossAmount(salesReturnDto.getGrossAmount());
		salesReturn.setCustomercreditno(salesReturnDto.getCustomercreditno());
		salesReturn.setRoundingoff(salesReturnDto.getRoundingoff());
		salesReturn.setCustomercreaditdate(salesReturnDto.getCustomercreaditdate());
		salesReturn.setBranch(salesReturnDto.getBranch());
		salesReturn.setCompany(salesReturnDto.getCompany());
		salesReturn.setCustomerSubContacts(salesReturnDto.getCustomerSubContacts());
		salesReturn.setWarehouse(salesReturnDto.getWarehouse());
		salesReturn.setSalesReturnItems(salesReturnDto.getSalesReturnItems());
		
		List<SalesReturnItems> salesReturnItems = salesReturnDto.getSalesReturnItems();
		for(int i=0;i<salesReturnItems.size();i++)
		{
			salesReturnItems.get(i).setSlNo(i+1);
		}
	
		
		salesReturn.setCreateddate(LocalDate.now());
		salesReturn.setCreatedtime(LocalTime.now());
		salesReturn.setDistributor(salesReturnDto.getDistributor());
		salesReturn.setInvoice_status("InACT");
		salesReturn.setVersion("1.1");
		salesReturn.setTaxSch("GST");
		salesReturn.setSupTyp(salesReturnDto.getTranDtls().getSupTyp());
		salesReturn.setEcmGstin(salesReturnDto.getTranDtls().getEcmGstin());
		salesReturn.setTyp("CRN");
		salesReturn.setNo(salesReturnDto.getDocDtls().getNo());
		salesReturn.setDt(salesReturnDto.getDocDtls().getDt());
		salesReturn.setGstin(salesReturnDto.getSellerDtls().getGstin());
		salesReturn.setLglNm(salesReturnDto.getSellerDtls().getLglNm());
		salesReturn.setAddr1(salesReturnDto.getSellerDtls().getAddr1());
		salesReturn.setLoc(salesReturnDto.getSellerDtls().getLoc());
		salesReturn.setPin(salesReturnDto.getSellerDtls().getPin());
		salesReturn.setStcd(salesReturnDto.getSellerDtls().getStcd());
		salesReturn.setPh(salesReturnDto.getSellerDtls().getPh());
		salesReturn.setEm(salesReturnDto.getSellerDtls().getEm());
		salesReturn.setBgstin(salesReturnDto.getBuyerDtls().getGstin());
		salesReturn.setBlglNm(salesReturnDto.getBuyerDtls().getLglNm());
		salesReturn.setPos(salesReturnDto.getBuyerDtls().getPos());
		salesReturn.setBaddr1(salesReturnDto.getBuyerDtls().getAddr1());
		salesReturn.setBloc(salesReturnDto.getBuyerDtls().getLoc());
		salesReturn.setBpin(salesReturnDto.getBuyerDtls().getPin());
		salesReturn.setBstcd(salesReturnDto.getBuyerDtls().getStcd());
		salesReturn.setBph(salesReturnDto.getBuyerDtls().getPh());
		salesReturn.setBem(salesReturnDto.getBuyerDtls().getEm());
		salesReturn.setAssVal(salesReturnDto.getValDtls().getAssVal());
		salesReturn.setCgstVal(salesReturnDto.getValDtls().getCgstVal());
		salesReturn.setSgstVal(salesReturnDto.getValDtls().getSgstVal());
		salesReturn.setIgstVal(salesReturnDto.getValDtls().getIgstVal());
		salesReturn.setCesVal(salesReturnDto.getValDtls().getCesVal());
		salesReturn.setStCesVal(salesReturnDto.getValDtls().getStCesVal());
//		salesReturn.setVdiscount(salesReturnDto.getValDtls().getDiscount());
		salesReturn.setVothChrg(salesReturnDto.getValDtls().getOthChrg());
		salesReturn.setTotInvVal(salesReturnDto.getValDtls().getTotInvVal());
		

		salesReturn.setCreateddate(LocalDate.now());
		salesReturn.setCreatedtime(LocalTime.now());
		salesReturn.setDistributor(salesReturnDto.getDistributor());
		salesReturn.setRetailer(salesReturnDto.getRetailer());
		salesReturn.setVoucherMaster(voucher);
		
//		if(salesReturnRepo.searchByVoucher(salesReturnDto.getVoucherid())==null)
//		{
//			salesReturn.setVoucherid(salesReturnDto.getVoucherid());
//			salesReturn.setVouchernumber(1);
//			String vseries = salesReturnDto.getVoucherid()+1;
//			salesReturn.setVoucherseries(vseries);
//		}
//		else {
//			SalesReturn searchByVoucher = salesReturnRepo.searchByVoucher(salesReturnDto.getVoucherid());
//			String voucherid = searchByVoucher.getVoucherid();
//			long vouchernumber = searchByVoucher.getVouchernumber();
//			long l = ++vouchernumber;
//			String newver = voucherid + vouchernumber;
//			salesReturn.setVoucherid(voucherid);
//			salesReturn.setVouchernumber(l);
//			salesReturn.setVoucherseries(newver);
//		}
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			SalesReturn topByVoucherOrderByStartnumberwithprefilnoDesc = salesReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SalesReturn topByVoucherOrderByStartnumberwithprefilyesDesc = salesReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SalesReturn lastrowstatus = salesReturnRepo.lastrowstatus();
			SalesReturn lastrowstatus = salesReturnRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				salesReturn.setStartnumberwithprefilno(startingnumber);
				salesReturn.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					salesReturn.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					salesReturn.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					salesReturn.setStartnumberwithprefilno(restartnumber + 1);
					salesReturn.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					salesReturn.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					salesReturn.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					salesReturn.setStartnumberwithprefilyes(formattedStartingNumber);
					salesReturn.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					salesReturn.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					salesReturn.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					salesReturn.setStartnumberwithprefilyes(restartnumberinc);
					salesReturn.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					salesReturn.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					salesReturn.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			SalesReturn topByVoucherOrderByStartnumberwithprefilnoDesc = salesReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SalesReturn topByVoucherOrderByStartnumberwithprefilyesDesc = salesReturnRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SalesReturn lastrowstatus = salesReturnRepo.lastrowstatus();
			SalesReturn lastrowstatus = salesReturnRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				salesReturn.setStartnumberwithprefilno(startingnumber);
				salesReturn.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				salesReturn.setStartnumberwithprefilyes(formattedStartingNumber);
				salesReturn.setVoucherstatus("startnostatus");
			}
		}

		SalesReturn save = salesReturnRepo.save(salesReturn);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		salesReturn.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		salesReturnRepo.save(salesReturn);
		
		

		try {
			if (salesReturnDto.getDistributor() == null) {
				if (salesReturnDto.getRetailer() != null) {
					Retailer rt = retailerRepo.findById(salesReturnDto.getRetailer().getId()).get();

					salesReturn.setDistributor(rt.getDistributor());
					salesReturn.setRetailer(salesReturnDto.getRetailer());
					salesReturn.setRetailerstatus("customer");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		SalesReturn sr = salesReturnRepo.save(salesReturn);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSalesreturnid((long) sr.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	@Override
	public List<SalesReturn> getAllSalesReturn() {

		return salesReturnRepo.findAll();
	}

	@Override
	public SalesReturn getSalesReturnById(int id) {

		Optional<SalesReturn> salereturn = salesReturnRepo.findById(id);
		return salereturn.get();
	}

	@Override
	public void deleteSalesReturn(int id) {
		salesReturnRepo.deleteById(id);
	}

	@Override
	public SalesReturnDto updateSalesReturn(SalesReturnDto salesReturnDto, int id) {

		SalesReturn salesReturn = salesReturnRepo.findById(id).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		salesReturn.setUpdatedbyname(uname);
		salesReturn.setUpdatedby(uid);
		salesReturn.setUpdatedrole(role);
		salesReturn.setUpdateddate(LocalDate.now());
		salesReturn.setUpdatedtime(LocalTime.now());

		salesReturn.setBuyerorderno(salesReturnDto.getBuyerorderno());
		salesReturn.setCreditnoteno(salesReturnDto.getCreditnoteno());
		salesReturn.setDated(salesReturnDto.getDated());
		salesReturn.setDeclaration(salesReturnDto.getDeclaration());
		salesReturn.setDestination(salesReturnDto.getDestination());
		salesReturn.setDispatcheddocno(salesReturnDto.getDispatcheddocno());
		salesReturn.setDispatchedthrough(salesReturnDto.getDispatchedthrough());
		salesReturn.setInvoicedate(salesReturnDto.getInvoicedate());
		salesReturn.setOtherrefernce(salesReturnDto.getOtherrefernce());
		salesReturn.setTermsofdelivery(salesReturnDto.getTermsofdelivery());
		salesReturn.setTermsofpayment(salesReturnDto.getTermsofpayment());
		salesReturn.setCgst(salesReturnDto.getCgst());
		salesReturn.setGrandtotal(salesReturnDto.getGrandtotal());
		salesReturn.setGrossAmount(salesReturnDto.getGrossAmount());
		salesReturn.setIgst(salesReturnDto.getIgst());
		salesReturn.setType(salesReturnDto.getType());
		salesReturn.setSgst(salesReturnDto.getSgst());
		salesReturn.setSalesreturndate(salesReturnDto.getSalesreturndate());

		salesReturn.setRoundvalue(salesReturnDto.getRoundvalue());
		salesReturn.setInvoiceno(salesReturnDto.getInvoiceno());
		salesReturn.setIgst(salesReturnDto.getIgst());
		salesReturn.setPaymentTerms(salesReturnDto.getPaymentTerms());
		salesReturn.setInvoiceno(salesReturnDto.getInvoiceno());
		salesReturn.setOtherrefernce(salesReturnDto.getOtherrefernce());
		salesReturn.setShippingcharge(salesReturnDto.getShippingcharge());
		salesReturn.setCustomercreditno(salesReturnDto.getCustomercreditno());
		salesReturn.setCustomercreaditdate(salesReturnDto.getCustomercreaditdate());
		salesReturn.setGrossAmount(salesReturnDto.getGrossAmount());
		salesReturn.setDistributor(salesReturnDto.getDistributor());
		salesReturn.setRetailer(salesReturnDto.getRetailer());
		salesReturn.setCompany(salesReturnDto.getCompany());
		salesReturn.setBranch(salesReturnDto.getBranch());
		salesReturn.setCustomerSubContacts(salesReturnDto.getCustomerSubContacts());
		salesReturn.setWarehouse(salesReturnDto.getWarehouse());
		salesReturn.setSalesReturnItems(salesReturnDto.getSalesReturnItems());
		salesReturn.setRoundingoff(salesReturnDto.getRoundingoff());

		if (salesReturn.getDistributor() == null) {
			Retailer r = retailerRepo.findById(salesReturnDto.getRetailer().getId()).get();

			salesReturn.setDistributor(r.getDistributor());

//			List<SalesReturnItems> salesReturnItems = salesReturnDto.getSalesReturnItems();
//			for (int i = 0; i < salesReturnItems.size(); i++) {
//				salesReturnItems.get(i).setSlNo(i + 1);
//			}

		

			SalesReturnDto salesReturnDto2 = new SalesReturnDto();
//			salesReturnDto2.setBuyerorderno(savedsr.getBuyerorderno());
//			salesReturnDto2.setCreditnoteno(savedsr.getCreditnoteno());
//			salesReturnDto2.setDated(savedsr.getDated());
//			salesReturnDto2.setDeclaration(savedsr.getDeclaration());
//			salesReturnDto2.setDestination(savedsr.getDestination());
//			salesReturnDto2.setDispatcheddocno(savedsr.getDispatcheddocno());
//			salesReturnDto2.setDispatchedthrough(savedsr.getDispatchedthrough());
//			salesReturnDto2.setInvoicedate(savedsr.getInvoicedate());
//			salesReturnDto2.setOtherrefernce(savedsr.getOtherrefernce());
//			salesReturnDto2.setTermsofdelivery(savedsr.getTermsofdelivery());
//			salesReturnDto2.setTermsofpayment(savedsr.getTermsofpayment());
//			salesReturnDto2.setCgst(savedsr.getCgst());
//			salesReturnDto2.setGrandtotal(savedsr.getGrandtotal());
//			salesReturnDto2.setGrossAmount(savedsr.getGrossAmount());
//			salesReturnDto2.setIgst(savedsr.getIgst());
//			salesReturnDto2.setType(savedsr.getType());
//			salesReturnDto2.setSgst(savedsr.getSgst());
//			salesReturnDto2.setPaymentTerms(savedsr.getPaymentTerms());
//			salesReturnDto2.setInvoiceno(savedsr.getInvoiceno());
//			salesReturnDto2.setOtherrefernce(savedsr.getOtherrefernce());
//			salesReturnDto2.setShippingcharge(savedsr.getShippingcharge());
//			salesReturnDto2.setSalesreturndate(savedsr.getSalesreturndate());
//			salesReturnDto2.setRoundvalue(savedsr.getRoundvalue());
//			salesReturnDto2.setInvoiceno(savedsr.getInvoiceno());
//			salesReturnDto2.setIgst(savedsr.getIgst());
//			salesReturnDto2.setCompany(savedsr.getCompany());
//			salesReturnDto2.setCustomercreditno(savedsr.getCustomercreditno());
//			salesReturnDto2.setCustomercreaditdate(savedsr.getCustomercreaditdate());
//			salesReturnDto2.setGrossAmount(savedsr.getGrossAmount());
//			salesReturnDto2.setDistributor(savedsr.getDistributor());
//			salesReturnDto2.setBranch(savedsr.getBranch());
//			salesReturnDto2.setRetailer(savedsr.getRetailer());
//			salesReturnDto2.setCustomerSubContacts(savedsr.getCustomerSubContacts());
//			salesReturnDto2.setWarehouse(savedsr.getWarehouse());
//			salesReturnDto2.setSalesReturnItems(savedsr.getSalesReturnItems());
			
			List<SalesReturnItems> salesReturnItems = salesReturnDto.getSalesReturnItems();
			for(int i=0;i<salesReturnItems.size();i++)
			{
				salesReturnItems.get(i).setSlNo(i+1);
				
			}
			
	
			
			salesReturn.setInvoice_status("InACT");
			salesReturn.setVersion("1.1");
			salesReturn.setTaxSch("GST");
			salesReturn.setSupTyp(salesReturnDto.getTranDtls().getSupTyp());
			salesReturn.setEcmGstin(salesReturnDto.getTranDtls().getEcmGstin());
			salesReturn.setTyp("CRN");
			salesReturn.setNo(salesReturnDto.getDocDtls().getNo());
			salesReturn.setDt(salesReturnDto.getDocDtls().getDt());
			salesReturn.setGstin(salesReturnDto.getSellerDtls().getGstin());
			salesReturn.setLglNm(salesReturnDto.getSellerDtls().getLglNm());
			salesReturn.setAddr1(salesReturnDto.getSellerDtls().getAddr1());
			salesReturn.setLoc(salesReturnDto.getSellerDtls().getLoc());
			salesReturn.setPin(salesReturnDto.getSellerDtls().getPin());
			salesReturn.setStcd(salesReturnDto.getSellerDtls().getStcd());
			salesReturn.setPh(salesReturnDto.getSellerDtls().getPh());
			salesReturn.setEm(salesReturnDto.getSellerDtls().getEm());
			salesReturn.setBgstin(salesReturnDto.getBuyerDtls().getGstin());
			salesReturn.setBlglNm(salesReturnDto.getBuyerDtls().getLglNm());
			salesReturn.setPos(salesReturnDto.getBuyerDtls().getPos());
			salesReturn.setBaddr1(salesReturnDto.getBuyerDtls().getAddr1());
			salesReturn.setBloc(salesReturnDto.getBuyerDtls().getLoc());
			salesReturn.setBpin(salesReturnDto.getBuyerDtls().getPin());
			salesReturn.setBstcd(salesReturnDto.getBuyerDtls().getStcd());
			salesReturn.setBph(salesReturnDto.getBuyerDtls().getPh());
			salesReturn.setBem(salesReturnDto.getBuyerDtls().getEm());
			salesReturn.setAssVal(salesReturnDto.getValDtls().getAssVal());
			salesReturn.setCgstVal(salesReturnDto.getValDtls().getCgstVal());
			salesReturn.setSgstVal(salesReturnDto.getValDtls().getSgstVal());
			salesReturn.setIgstVal(salesReturnDto.getValDtls().getIgstVal());
			salesReturn.setCesVal(salesReturnDto.getValDtls().getCesVal());
			salesReturn.setStCesVal(salesReturnDto.getValDtls().getStCesVal());
//			salesReturn.setVdiscount(salesReturnDto.getValDtls().getDiscount());
			salesReturn.setVothChrg(salesReturnDto.getValDtls().getOthChrg());
			salesReturn.setTotInvVal(salesReturnDto.getValDtls().getTotInvVal());
			
			SalesReturn savedsr = salesReturnRepo.save(salesReturn);

			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Long loggeduser = userDetailsImpl.getId();
			ActivityLog activityLog = new ActivityLog();

			activityLog.setUpdatedate(new Date());
			activityLog.setUpdatedtime(LocalTime.now());
			activityLog.setSalesreturnid((long) savedsr.getId());
			activityLog.setLoggeduser(loggeduser);

			activityLogRepo.save(activityLog);

			return salesReturnDto2;
		}

		return salesReturnDto;
	}

	@Override
	public Map<String, Object> IndexSalesReturnAsc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		long id = userDetails.getId();

		int uid = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = salesReturnRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturn(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = salesReturnRepo.indexSalesReturn(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturn(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				long countpages = salesReturnRepo.indexSalesReturnRsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturnRsm(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = salesReturnRepo.indexSalesReturnAsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturnAsm(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				long countpages = salesReturnRepo.indexSalesReturnAse(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturnAse(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			
		}
		return null;
	}

	@Override
	public Map<String, Object> IndexSalesReturnDesc(int pageno, int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		long id = userDetails.getId();

		int uid = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = salesReturnRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturn(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = salesReturnRepo.indexSalesReturn(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturn(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				long countpages = salesReturnRepo.indexSalesReturnRsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturnRsm(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = salesReturnRepo.indexSalesReturnAsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturnAsm(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				long countpages = salesReturnRepo.indexSalesReturnAse(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesReturnProjection> ipo = salesReturnRepo.indexSalesReturnAse(uid, p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

		}

		return null;

	}

//	@Override
//	public List<SalesReturnProjection> IndexSalesReturnSearch(int pageno, int pagesize, String search) {
//		// TODO Auto-generated method stub
//		Pageable p=PageRequest.of(pageno, pagesize);
//		ModelMapper modelMapper=new ModelMapper();
//		List<SalesReturnProjection> salesReturn=salesReturnRepo.SearchBySales(search, p)
//				.stream().map(salesReturnProjection -> modelMapper.map(salesReturnProjection, SalesReturnProjection.class))
//				.collect(Collectors.toList());
//	
//		
//		return salesReturn;
//	
//	}

	@Override
	public Map<String, Object> SearchSalesReturn(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		long id = userDetails.getId();

		int uid = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				List<SalesReturnProjection> salesReturn = salesReturnRepo.SearchBySalesReturn(search, p);

				long searchcount = salesReturn.size();

				response.put("data", salesReturn);
				response.put("SearchCount", searchcount);

				return response;

			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				List<SalesReturnProjection> salesReturn = salesReturnRepo.SearchBySalesReturn(uid, search, p);

				long searchcount = salesReturn.size();

				response.put("data", salesReturn);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				List<SalesReturnProjection> salesReturn = salesReturnRepo.SearchBySalesReturnRsm(uid, search, p);

				long searchcount = salesReturn.size();

				response.put("data", salesReturn);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				List<SalesReturnProjection> salesReturn = salesReturnRepo.SearchBySalesReturnAsm(uid, search, p);

				long searchcount = salesReturn.size();

				response.put("data", salesReturn);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				List<SalesReturnProjection> salesReturn = salesReturnRepo.SearchBySalesReturnAse(uid, search, p);

				long searchcount = salesReturn.size();

				response.put("data", salesReturn);
				response.put("SearchCount", searchcount);

				return response;
			}
		}
		return null;
	}

}
