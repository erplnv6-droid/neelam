package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import com.SCM.dto.DeliveryChargeDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.DeliveryCharge;
import com.SCM.model.DeliveryChargeItems;
import com.SCM.model.Distributor;
import com.SCM.model.Notification;
import com.SCM.model.Retailer;
import com.SCM.model.SalesOrder;
import com.SCM.model.SalesOrderItems;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.DeliveryChallan;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DeliveryChargeRepo;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.NotificationRepository;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.SalesOrderItemsRepo;
import com.SCM.repository.SalesOrderRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.DeliveryChargeService;

@Service
public class DeliveryChargeServiceImpl implements DeliveryChargeService {

	@Autowired
	private DeliveryChargeRepo deliveryChargeRepo;

	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private SalesOrderItemsRepo salesOrderItemsRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private NotificationRepository notificationRepository;
	
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
	public DeliveryCharge saveDC(DeliveryChargeDto deliverychargedto) {
		
		
		DeliveryCharge deliveryCharge = new DeliveryCharge();
		
		VoucherMaster voucher=voucherMasterRepo.findById(deliverychargedto.getVoucherMaster().getId()).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		deliveryCharge.setCreatebyname(uname);
		deliveryCharge.setCreatedby(uid);
		deliveryCharge.setRole(role);

		deliveryCharge.setBuyerorderno(deliverychargedto.getBuyerorderno());
		deliveryCharge.setBuyerorderdate(deliverychargedto.getBuyerorderdate());
		deliveryCharge.setAckdate(deliverychargedto.getAckdate());
		deliveryCharge.setStatus("pending");
		deliveryCharge.setAckno(deliverychargedto.getAckno());
		deliveryCharge.setDestination(deliverychargedto.getDestination());
		deliveryCharge.setDeliverynotno(deliverychargedto.getDeliverynotno());
		deliveryCharge.setDispatchedthrough(deliverychargedto.getDispatchedthrough());
		deliveryCharge.setIrnno(deliverychargedto.getIrnno());
		deliveryCharge.setUdyamno(deliverychargedto.getUdyamno());
		deliveryCharge.setDeliveryAddress(deliverychargedto.getDeliveryAddress());
		deliveryCharge.setGstno(deliverychargedto.getGstno());
		deliveryCharge.setMsmeno(deliverychargedto.getMsmeno());
		deliveryCharge.setRemarks(deliverychargedto.getRemarks());
		deliveryCharge.setTaxtype(deliverychargedto.getTaxtype());
		deliveryCharge.setTermsofdelivery(deliverychargedto.getTermsofdelivery());
		deliveryCharge.setTotalnopkg(deliverychargedto.getTotalnopkg());
		deliveryCharge.setCgst(deliverychargedto.getCgst());
		deliveryCharge.setGrandtotal(deliverychargedto.getGrandtotal());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setType(deliverychargedto.getType());
		deliveryCharge.setSgst(deliverychargedto.getSgst());
		deliveryCharge.setDcdate(deliverychargedto.getDcdate());
		deliveryCharge.setRoundofvalue(deliverychargedto.getRoundofvalue());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setTaxvalue(deliverychargedto.getTaxvalue());
		deliveryCharge.setBankname(deliverychargedto.getBankname());
		deliveryCharge.setBranchname(deliverychargedto.getBranchname());
		deliveryCharge.setIfsccode(deliverychargedto.getIfsccode());
		deliveryCharge.setAccountno(deliverychargedto.getAccountno());
		deliveryCharge.setIgstoutput(deliverychargedto.getIgstoutput());;
		deliveryCharge.setCompany(deliverychargedto.getCompany());
		deliveryCharge.setPaymentTerms(deliverychargedto.getPaymentTerms());
		deliveryCharge.setDeliveryAddress(deliverychargedto.getDeliveryAddress());
		deliveryCharge.setBranch(deliverychargedto.getBranch());
		deliveryCharge.setCustomerSubContacts(deliverychargedto.getCustomerSubContacts());
		deliveryCharge.setWarehouse(deliverychargedto.getWarehouse());
		deliveryCharge.setDcItems(deliverychargedto.getDcItems());
		deliveryCharge.setCreateddate(LocalDate.now());
		deliveryCharge.setCreatedtime(LocalTime.now());
		deliveryCharge.setDistributor(deliverychargedto.getDistributor());
		deliveryCharge.setRetailer(deliverychargedto.getRetailer());
		deliveryCharge.setVoucherMaster(voucher);
		
//		if(deliveryChargeRepo.searchByVoucher(deliverychargedto.getVoucherid())==null)
//		{
//			deliveryCharge.setVoucherid(deliverychargedto.getVoucherid());
//			deliveryCharge.setVouchernumber(1);
//			String vseries = deliverychargedto.getVoucherid()+1;
//			deliveryCharge.setVoucherseries(vseries);
//		}
//		else {
//			DeliveryCharge searchByVoucher = deliveryChargeRepo.searchByVoucher(deliverychargedto.getVoucherid());
//			String voucherid = searchByVoucher.getVoucherid();
//			long vouchernumber = searchByVoucher.getVouchernumber();
//			long l = ++vouchernumber;
//			String newver = voucherid + vouchernumber;
//			deliveryCharge.setVoucherid(voucherid);
//			deliveryCharge.setVouchernumber(l);
//			deliveryCharge.setVoucherseries(newver);
//		}
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			DeliveryCharge topByVoucherOrderByStartnumberwithprefilnoDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			DeliveryCharge topByVoucherOrderByStartnumberwithprefilyesDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			DeliveryCharge lastrowstatus = deliveryChargeRepo.lastrowstatus();
			DeliveryCharge lastrowstatus = deliveryChargeRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				deliveryCharge.setStartnumberwithprefilno(startingnumber);
				deliveryCharge.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					deliveryCharge.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					deliveryCharge.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					deliveryCharge.setStartnumberwithprefilno(restartnumber + 1);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					deliveryCharge.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					deliveryCharge.setStartnumberwithprefilyes(formattedStartingNumber);
					deliveryCharge.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					deliveryCharge.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					deliveryCharge.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					deliveryCharge.setStartnumberwithprefilyes(restartnumberinc);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					deliveryCharge.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			DeliveryCharge topByVoucherOrderByStartnumberwithprefilnoDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			DeliveryCharge topByVoucherOrderByStartnumberwithprefilyesDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			DeliveryCharge lastrowstatus = deliveryChargeRepo.lastrowstatus();
			DeliveryCharge lastrowstatus = deliveryChargeRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				deliveryCharge.setStartnumberwithprefilno(startingnumber);
				deliveryCharge.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				deliveryCharge.setStartnumberwithprefilyes(formattedStartingNumber);
				deliveryCharge.setVoucherstatus("startnostatus");
			}
		}

		DeliveryCharge save = deliveryChargeRepo.save(deliveryCharge);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		deliveryCharge.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		deliveryChargeRepo.save(deliveryCharge);
	
		
		if (deliverychargedto.getDistributor() == null) {

			if (deliverychargedto.getRetailer() != null) {

				Retailer rt = retailerRepo.findById(deliverychargedto.getRetailer().getId()).get();
				
				deliveryCharge.setRetailer(deliverychargedto.getRetailer());
				deliveryCharge.setDistributor(rt.getDistributor());
				deliveryCharge.setRetailerstatus("customer");
			}
		}

		DeliveryCharge dc = deliveryChargeRepo.save(deliveryCharge);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		dc.getId();

		if (deliveryCharge.getDistributor() != null) {

			if (deliveryCharge.getRetailer() != null) {

				Optional<Retailer> r = retailerRepo.findById(deliverychargedto.getRetailer().getId());
				Retailer retailer = r.get();
				this.saveNotification(retailer, "Dc Created By " + userDetailsImpl.getUsername(), dc);
			}
		} else {

			Optional<Distributor> d = distributorRepo.findById(deliverychargedto.getDistributor().getId());
			Distributor distributor = d.get();
			this.saveNotification(distributor, "Dc Created By " + userDetailsImpl.getUsername(), dc);
		}

		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setDeliverychargeid((long) dc.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	// ----------- convert salesorder to dc

	@Override
	public DeliveryCharge convertSOtoDC(DeliveryChargeDto deliverychargedto, int salesorderId) {
		
		VoucherMaster voucher=voucherMasterRepo.findById(deliverychargedto.getVoucherMaster().getId()).get();

		Optional<SalesOrder> salesorder = salesOrderRepo.findById(salesorderId);
		SalesOrder so = salesorder.get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		DeliveryCharge deliveryCharge = new DeliveryCharge();
		deliveryCharge.setBuyerorderno(deliverychargedto.getBuyerorderno());
		deliveryCharge.setBuyerorderdate(deliverychargedto.getBuyerorderdate());
		deliveryCharge.setAckdate(deliverychargedto.getAckdate());
		deliveryCharge.setAckno(deliverychargedto.getAckno());
		deliveryCharge.setDestination(deliverychargedto.getDestination());
		deliveryCharge.setDeliverynotno(deliverychargedto.getDeliverynotno());
		deliveryCharge.setDispatchedthrough(deliverychargedto.getDispatchedthrough());
		deliveryCharge.setIrnno(deliverychargedto.getIrnno());
		deliveryCharge.setMsmeno(deliverychargedto.getMsmeno());
		deliveryCharge.setRemarks(deliverychargedto.getRemarks());
		deliveryCharge.setTaxtype(deliverychargedto.getTaxtype());
		deliveryCharge.setTermsofdelivery(deliverychargedto.getTermsofdelivery());
		deliveryCharge.setTotalnopkg(deliverychargedto.getTotalnopkg());
		deliveryCharge.setCgst(deliverychargedto.getCgst());
		deliveryCharge.setGrandtotal(deliverychargedto.getGrandtotal());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setBankname(deliverychargedto.getBankname());
		deliveryCharge.setBranchname(deliverychargedto.getBranchname());
		deliveryCharge.setIfsccode(deliverychargedto.getIfsccode());
		deliveryCharge.setIgstoutput(deliverychargedto.getIgstoutput());
		deliveryCharge.setType(deliverychargedto.getType());
		deliveryCharge.setSgst(deliverychargedto.getSgst());
		deliveryCharge.setDcdate(deliverychargedto.getDcdate());
		deliveryCharge.setAccountno(deliverychargedto.getAccountno());
		deliveryCharge.setRoundofvalue(deliverychargedto.getRoundofvalue());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setCompany(deliverychargedto.getCompany());
		deliveryCharge.setUdyamno(deliverychargedto.getUdyamno());
		deliveryCharge.setGstno(deliverychargedto.getGstno());
		deliveryCharge.setPaymentTerms(deliverychargedto.getPaymentTerms());
		deliveryCharge.setDeliveryAddress(deliverychargedto.getDeliveryAddress());
		deliveryCharge.setCustomerSubContacts(deliverychargedto.getCustomerSubContacts());
		deliveryCharge.setWarehouse(deliverychargedto.getWarehouse());
		deliveryCharge.setBranch(deliverychargedto.getBranch());
		deliveryCharge.setDcItems(deliverychargedto.getDcItems());
		deliveryCharge.setSalesorderId(so.getId());
		deliveryCharge.setCreateddate(LocalDate.now());
		deliveryCharge.setCreatedtime(LocalTime.now());
		deliveryCharge.setDistributor(deliverychargedto.getDistributor());
		deliveryCharge.setCreatebyname(uname);
		deliveryCharge.setCreatedby(uid);
		deliveryCharge.setRole(role);
		deliveryCharge.setVoucherMaster(voucher);

		if (deliverychargedto.getDistributor() == null) {

			if (deliverychargedto.getRetailer() != null) {

				Retailer rt = retailerRepo.findById(deliverychargedto.getRetailer().getId()).get();
				deliveryCharge.setDistributor(rt.getDistributor());
				deliveryCharge.setRetailer(deliverychargedto.getRetailer());
				deliveryCharge.setRetailerstatus("customer");
			}
		}
		
//		if(deliveryChargeRepo.searchByVoucher(deliverychargedto.getVoucherid())==null)
//		{
//			deliveryCharge.setVoucherid(deliverychargedto.getVoucherid());
//			deliveryCharge.setVouchernumber(1);
//			String vseries = deliverychargedto.getVoucherid()+1;
//			deliveryCharge.setVoucherseries(vseries);
//		}
//		else {
//			DeliveryCharge searchByVoucher = deliveryChargeRepo.searchByVoucher(deliverychargedto.getVoucherid());
//			String voucherid = searchByVoucher.getVoucherid();
//			long vouchernumber = searchByVoucher.getVouchernumber();
//			long l = ++vouchernumber;
//			String newver = voucherid + vouchernumber;
//			deliveryCharge.setVoucherid(voucherid);
//			deliveryCharge.setVouchernumber(l);
//			deliveryCharge.setVoucherseries(newver);
//		}
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			DeliveryCharge topByVoucherOrderByStartnumberwithprefilnoDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			DeliveryCharge topByVoucherOrderByStartnumberwithprefilyesDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			DeliveryCharge lastrowstatus = deliveryChargeRepo.lastrowstatus();
			DeliveryCharge lastrowstatus = deliveryChargeRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				deliveryCharge.setStartnumberwithprefilno(startingnumber);
				deliveryCharge.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					deliveryCharge.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					deliveryCharge.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					deliveryCharge.setStartnumberwithprefilno(restartnumber + 1);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					deliveryCharge.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					deliveryCharge.setStartnumberwithprefilyes(formattedStartingNumber);
					deliveryCharge.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					deliveryCharge.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					deliveryCharge.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					deliveryCharge.setStartnumberwithprefilyes(restartnumberinc);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					deliveryCharge.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					deliveryCharge.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			DeliveryCharge topByVoucherOrderByStartnumberwithprefilnoDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			DeliveryCharge topByVoucherOrderByStartnumberwithprefilyesDesc = deliveryChargeRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			DeliveryCharge lastrowstatus = deliveryChargeRepo.lastrowstatus();
			DeliveryCharge lastrowstatus = deliveryChargeRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				deliveryCharge.setStartnumberwithprefilno(startingnumber);
				deliveryCharge.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				deliveryCharge.setStartnumberwithprefilyes(formattedStartingNumber);
				deliveryCharge.setVoucherstatus("startnostatus");
			}
		}

		DeliveryCharge save = deliveryChargeRepo.save(deliveryCharge);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		deliveryCharge.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		deliveryChargeRepo.save(deliveryCharge);
		

		DeliveryCharge dc = deliveryChargeRepo.save(deliveryCharge);

		SalesOrder order = salesOrderRepo.findById(dc.getSalesorderId()).get();
		order.setStatus("converted");
		salesOrderRepo.save(order);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setDeliverychargeid((long) dc.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return save;
	}

	// --------- convert so to dc with pending so

	@Override
	public DeliveryCharge statusDC1(DeliveryChargeDto deliverychargedto) {

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		DeliveryCharge deliveryCharge = new DeliveryCharge();
		deliveryCharge.setBuyerorderno(deliverychargedto.getBuyerorderno());
		deliveryCharge.setBuyerorderdate(deliverychargedto.getBuyerorderdate());
		deliveryCharge.setAckdate(deliverychargedto.getAckdate());
		deliveryCharge.setAckno(deliverychargedto.getAckno());
		deliveryCharge.setDestination(deliverychargedto.getDestination());
		deliveryCharge.setDeliverynotno(deliverychargedto.getDeliverynotno());
		deliveryCharge.setDispatchedthrough(deliverychargedto.getDispatchedthrough());
		deliveryCharge.setIrnno(deliverychargedto.getIrnno());
		deliveryCharge.setMsmeno(deliverychargedto.getMsmeno());
		deliveryCharge.setRemarks(deliverychargedto.getRemarks());
		deliveryCharge.setTaxtype(deliverychargedto.getTaxtype());
		deliveryCharge.setTermsofdelivery(deliverychargedto.getTermsofdelivery());
		deliveryCharge.setTotalnopkg(deliverychargedto.getTotalnopkg());
		deliveryCharge.setCgst(deliverychargedto.getCgst());
		deliveryCharge.setGrandtotal(deliverychargedto.getGrandtotal());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setType(deliverychargedto.getType());
		deliveryCharge.setSgst(deliverychargedto.getSgst());
		deliveryCharge.setPaymentTerms(deliverychargedto.getPaymentTerms());
		deliveryCharge.setDcdate(deliverychargedto.getDcdate());
		deliveryCharge.setRoundofvalue(deliverychargedto.getRoundofvalue());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setBankname(deliverychargedto.getBankname());
		deliveryCharge.setBranchname(deliverychargedto.getBranchname());
		deliveryCharge.setIfsccode(deliverychargedto.getIfsccode());
		deliveryCharge.setIgstoutput(deliverychargedto.getIgstoutput());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setUdyamno(deliverychargedto.getUdyamno());
		deliveryCharge.setGstno(deliverychargedto.getGstno());
		deliveryCharge.setAccountno(deliverychargedto.getAccountno());
		deliveryCharge.setTaxvalue(deliverychargedto.getTaxvalue());
		deliveryCharge.setCompany(deliverychargedto.getCompany());
		deliveryCharge.setDeliveryAddress(deliverychargedto.getDeliveryAddress());
		deliveryCharge.setBranch(deliverychargedto.getBranch());
		deliveryCharge.setCustomerSubContacts(deliverychargedto.getCustomerSubContacts());
		deliveryCharge.setWarehouse(deliverychargedto.getWarehouse());
		deliveryCharge.setDcItems(deliverychargedto.getDcItems());
		deliveryCharge.setCreateddate(LocalDate.now());
		deliveryCharge.setCreatedtime(LocalTime.now());
		deliveryCharge.setDistributor(deliverychargedto.getDistributor());
		deliveryCharge.setCreatebyname(uname);
		deliveryCharge.setCreatedby(uid);
		deliveryCharge.setRole(role);
		
//		if(deliveryChargeRepo.searchByVoucher(deliverychargedto.getVoucherid())==null)
//		{
//			deliveryCharge.setVoucherid(deliverychargedto.getVoucherid());
//			deliveryCharge.setVouchernumber(1);
//			String vseries = deliverychargedto.getVoucherid()+1;
//			deliveryCharge.setVoucherseries(vseries);
//		}
//		else {
//			DeliveryCharge searchByVoucher = deliveryChargeRepo.searchByVoucher(deliverychargedto.getVoucherid());
//			String voucherid = searchByVoucher.getVoucherid();
//			long vouchernumber = searchByVoucher.getVouchernumber();
//			long l = ++vouchernumber;
//			String newver = voucherid + vouchernumber;
//			deliveryCharge.setVoucherid(voucherid);
//			deliveryCharge.setVouchernumber(l);
//			deliveryCharge.setVoucherseries(newver);
//		}

		if (deliveryCharge.getDistributor() == null) {
			
			if (deliveryCharge.getRetailer() != null) {
				
				Retailer rt = retailerRepo.findById(deliverychargedto.getRetailer().getId()).get();
				deliveryCharge.setDistributor(rt.getDistributor());
				deliveryCharge.setRetailer(deliverychargedto.getRetailer());
				deliveryCharge.setRetailerstatus(rt.getRetailerstatus());
			}
		}

		List<DeliveryChargeItems> deliveryChargeItems = deliveryCharge.getDcItems();
		List<String> allSalesOrderIds = new ArrayList<>();
		List<SalesOrderItems> salesOrderItems = deliverychargedto.getSalesOrderItems();

		for (SalesOrderItems salesOrderItem : salesOrderItems) {

			// ---------- SalesOrder Items

			int salesorderid = salesOrderItem.getSoid();
			int salesOrderItemId = salesOrderItem.getSalesorderitemId();
			int dcItemspending = salesOrderItem.getDcitemspending();
			int dcItemsPlaced = salesOrderItem.getDcitemsplaced();
			String dcStatus = salesOrderItem.getDcstatus();

			for (DeliveryChargeItems i : deliveryChargeItems) {

				int soProdid = salesOrderItem.getProduct().getId();
				int dcProdid = i.getProduct().getId();

				if (soProdid == dcProdid) {

//					float dcqtyplaced = i.getDcquantity_placed();
//					float dctotal = (float) (dcItemsPlaced + (dcItemsPlaced * 0.15));
//					float dcitemspendmax = dcqtyplaced + dcItemspending;

					i.setSalesOrderItemsId(salesOrderItemId);
				}
			}

			salesOrderItemsRepo.updatedcstatus(dcItemspending, dcItemsPlaced, dcStatus, salesOrderItemId);
			allSalesOrderIds.add(String.valueOf(salesorderid));
			deliveryCharge.setSalesorderids(String.join(",", allSalesOrderIds));

			SalesOrder order = salesOrderRepo.findById(salesorderid).get();

			if (dcItemspending == 0) {

				order.setStatus("converted");

			} else {
				order.setStatus("partialy converted");
			}

			salesOrderRepo.save(order);
		}

		DeliveryCharge dc = deliveryChargeRepo.save(deliveryCharge);

		return dc;
	}

	@Override
	public void deleteDCWithStatus(int id) {

		DeliveryCharge dc = deliveryChargeRepo.findById(id).orElseThrow(() -> new RuntimeException("DC id not found"));

		List<DeliveryChargeItems> dItems = dc.getDcItems();

		for (DeliveryChargeItems deliveryChargeItem : dItems) {

			int salesOrderItemsId = deliveryChargeItem.getSalesOrderItemsId();
			int dcqtyplaced = (int) deliveryChargeItem.getDcquantity_placed();

			List<SalesOrderItems> soitemsid = salesOrderItemsRepo.findBySalesorderitemId(salesOrderItemsId);

			for (SalesOrderItems items : soitemsid) {

				int originaldcItemspending = items.getDcitemspending();

				int dcitemstotal = (originaldcItemspending + dcqtyplaced);

				if (dcitemstotal == 0) {
					items.setDcstatus("pending");
				}

				items.setDcitemspending(dcitemstotal);
				items.getDcitemsplaced();
				items.setDcstatus("partial");

				salesOrderItemsRepo.save(items);
			}
			
			int dcid = dc.getSalesorderId();
			
			SalesOrder salesOrder = salesOrderRepo.findById(dcid).get();
			salesOrder.setStatus("pending");
			salesOrderRepo.save(salesOrder);

		}
		deliveryChargeRepo.deleteById(id);
	}

	@Override
	public List<DeliveryCharge> getAllDC() {

		return deliveryChargeRepo.findAll();
	}

	@Override
	public DeliveryCharge getDCById(int id) {

		DeliveryCharge dc = deliveryChargeRepo.findById(id).get();

		return dc;
	}

	@Override
	public void deleteDeliveryCharge(int id) {

		DeliveryCharge deliveryCharge = deliveryChargeRepo.findById(id).get();
		int dcid = deliveryCharge.getSalesorderId();
		
		SalesOrder salesOrder = salesOrderRepo.findById(dcid).get();
		salesOrder.setStatus("pending");
		salesOrderRepo.save(salesOrder);
		
		deliveryChargeRepo.deleteById(id);
	}

	@Override
	public DeliveryChargeDto updateDC(DeliveryChargeDto deliverychargedto, int id) {

		DeliveryCharge deliveryCharge = deliveryChargeRepo.findById(id).get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		deliveryCharge.setBuyerorderno(deliverychargedto.getBuyerorderno());
		deliveryCharge.setBuyerorderdate(deliverychargedto.getBuyerorderdate());
		deliveryCharge.setAckdate(deliverychargedto.getAckdate());
		deliveryCharge.setAckno(deliverychargedto.getAckno());
		deliveryCharge.setDestination(deliverychargedto.getDestination());
		deliveryCharge.setDeliverynotno(deliverychargedto.getDeliverynotno());
		deliveryCharge.setDispatchedthrough(deliverychargedto.getDispatchedthrough());
		deliveryCharge.setIrnno(deliverychargedto.getIrnno());
		deliveryCharge.setMsmeno(deliverychargedto.getMsmeno());
		deliveryCharge.setRemarks(deliverychargedto.getRemarks());
		deliveryCharge.setTaxtype(deliverychargedto.getTaxtype());
		deliveryCharge.setTermsofdelivery(deliverychargedto.getTermsofdelivery());
		deliveryCharge.setTotalnopkg(deliverychargedto.getTotalnopkg());
		deliveryCharge.setCgst(deliverychargedto.getCgst());
		deliveryCharge.setGrandtotal(deliverychargedto.getGrandtotal());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setType(deliverychargedto.getType());
		deliveryCharge.setSgst(deliverychargedto.getSgst());
		deliveryCharge.setDcdate(deliverychargedto.getDcdate());
		deliveryCharge.setRoundofvalue(deliverychargedto.getRoundofvalue());
		deliveryCharge.setIgst(deliverychargedto.getIgst());
		deliveryCharge.setBankname(deliverychargedto.getBankname());
		deliveryCharge.setBranchname(deliverychargedto.getBranchname());
		deliveryCharge.setIfsccode(deliverychargedto.getIfsccode());
		deliveryCharge.setIgstoutput(deliverychargedto.getIgstoutput());
		deliveryCharge.setPaymentTerms(deliverychargedto.getPaymentTerms());
		deliveryCharge.setGrossAmount(deliverychargedto.getGrossAmount());
		deliveryCharge.setCompany(deliverychargedto.getCompany());
		deliveryCharge.setDistributor(deliverychargedto.getDistributor());
		deliveryCharge.setDeliveryAddress(deliverychargedto.getDeliveryAddress());
		deliveryCharge.setBranch(deliverychargedto.getBranch());
		deliveryCharge.setCustomerSubContacts(deliverychargedto.getCustomerSubContacts());
		deliveryCharge.setWarehouse(deliverychargedto.getWarehouse());
		deliveryCharge.setDcItems(deliverychargedto.getDcItems());
		deliveryCharge.setUpdatedbyname(uname);
		deliveryCharge.setUpdatedby(uid);
		deliveryCharge.setUpdatedrole(role);
		deliveryCharge.setUpdateddate(LocalDate.now());
		deliveryCharge.setUpdatedtime(LocalTime.now());

		if(deliverychargedto.getDistributor() == null)
		{
		   Retailer r = retailerRepo.findById(deliverychargedto.getRetailer().getId()).get();

		   deliveryCharge.setDistributor(r.getDistributor());
		}
		
		DeliveryCharge savedc = deliveryChargeRepo.save(deliveryCharge);

		DeliveryChargeDto deliveryChargeDto2 = new DeliveryChargeDto();
		deliveryChargeDto2.setBuyerorderno(savedc.getBuyerorderno());
		deliveryChargeDto2.setBuyerorderdate(savedc.getBuyerorderdate());
		deliveryChargeDto2.setAckdate(savedc.getAckdate());
		deliveryChargeDto2.setAckno(savedc.getAckno());
		deliveryChargeDto2.setDestination(savedc.getDestination());
		deliveryChargeDto2.setDeliverynotno(savedc.getDeliverynotno());
		deliveryChargeDto2.setDispatchedthrough(savedc.getDispatchedthrough());
		deliveryChargeDto2.setIrnno(savedc.getIrnno());
		deliveryChargeDto2.setMsmeno(savedc.getMsmeno());
		deliveryChargeDto2.setRemarks(savedc.getRemarks());
		deliveryChargeDto2.setTaxtype(savedc.getTaxtype());
		deliveryChargeDto2.setTermsofdelivery(savedc.getTermsofdelivery());
		deliveryChargeDto2.setTotalnopkg(savedc.getTotalnopkg());
		deliveryChargeDto2.setCgst(savedc.getCgst());
		deliveryChargeDto2.setGrandtotal(savedc.getGrandtotal());
		deliveryChargeDto2.setGrossAmount(savedc.getGrossAmount());
		deliveryChargeDto2.setIgst(savedc.getIgst());
		deliveryChargeDto2.setType(savedc.getType());
		deliveryChargeDto2.setSgst(savedc.getSgst());
		deliveryChargeDto2.setDcdate(savedc.getDcdate());
		deliveryChargeDto2.setRoundofvalue(savedc.getRoundofvalue());
		deliveryChargeDto2.setIgst(savedc.getIgst());
		deliveryChargeDto2.setGrossAmount(savedc.getGrossAmount());
		deliveryChargeDto2.setCompany(savedc.getCompany());
		deliveryChargeDto2.setBranch(savedc.getBranch());
		deliveryCharge.setDistributor(deliverychargedto.getDistributor());
		deliveryCharge.setDeliveryAddress(savedc.getDeliveryAddress());
		deliveryChargeDto2.setCustomerSubContacts(savedc.getCustomerSubContacts());
		deliveryChargeDto2.setWarehouse(savedc.getWarehouse());
		deliveryChargeDto2.setDcItems(savedc.getDcItems());

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setDeliverychargeid((long) savedc.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return deliveryChargeDto2;
	}

	@Override
	public Map<String, Object> IndexDeliveryChallanAsc(int pageno, int pagesize, String field) {

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
				long countpages = deliveryChargeRepo.count();
				long pages = countpages / pagesize;

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallans(p);

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallans(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallans(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			if (s.equals("ROLE_RSM")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallansRsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallansRsm(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			if (s.equals("ROLE_ASM")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallansAsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallansAsm(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			if (s.equals("ROLE_ASE")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallansAse(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallansAse(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;

	}

	@Override
	public Map<String, Object> IndexDeliveryChallanDesc(int pageno, int pagesize, String field) {

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
				long countpages = deliveryChargeRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallans(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallans(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallans(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			if (s.equals("ROLE_RSM")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallansRsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallansRsm(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			if (s.equals("ROLE_ASM")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallansAsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallansAsm(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			if (s.equals("ROLE_ASE")) {

				long countpages = deliveryChargeRepo.indexDeliveryChallansAse(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<DeliveryChallan> ipo = deliveryChargeRepo.indexDeliveryChallansAse(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}

//	@Override
//	public List<DeliveryChallan> IndexDeliveryChallanSearch(int pageno, int pagesize, String search) {
//		// TODO Auto-generated method stub
//		Pageable p=PageRequest.of(pageno, pagesize);
//		ModelMapper modelMapper=new ModelMapper();
//		List<DeliveryChallan> deliveryCharges=deliveryChargeRepo.SearchByDeliveryCharge(search, p)
//				.stream().map(deliveryChallan -> modelMapper.map(deliveryChallan, DeliveryChallan.class))
//				.collect(Collectors.toList());
//	
//		
//		
//		return deliveryCharges;
//	}

	public Map<String, Object> SearchDeliveryChallan(int pageno, int pagesize, String search) {

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
				List<DeliveryChallan> deliverChallan = deliveryChargeRepo.SearchByDeliveryCharge(search, p);

				long searchcount = deliverChallan.size();

				response.put("data", deliverChallan);
				response.put("SearchCount", searchcount);

				return response;
			}

			if (s.equals("ROLE_DISTRIBUTOR")) {

				List<DeliveryChallan> deliverChallan = deliveryChargeRepo.SearchByDeliveryCharge(uid, search, p);

				long searchcount = deliverChallan.size();

				response.put("data", deliverChallan);
				response.put("SearchCount", searchcount);

				return response;

			}
			
			if (s.equals("ROLE_RSM")) {

				List<DeliveryChallan> deliverChallan = deliveryChargeRepo.SearchByDeliveryChargeRsm(uid, search, p);

				long searchcount = deliverChallan.size();

				response.put("data", deliverChallan);
				response.put("SearchCount", searchcount);

				return response;

			}
			
			if (s.equals("ROLE_ASM")) {

				List<DeliveryChallan> deliverChallan = deliveryChargeRepo.SearchByDeliveryChargeAsm(uid, search, p);

				long searchcount = deliverChallan.size();

				response.put("data", deliverChallan);
				response.put("SearchCount", searchcount);

				return response;

			}
			
			if (s.equals("ROLE_ASE")) {

				List<DeliveryChallan> deliverChallan = deliveryChargeRepo.SearchByDeliveryChargeAse(uid, search, p);

				long searchcount = deliverChallan.size();

				response.put("data", deliverChallan);
				response.put("SearchCount", searchcount);

				return response;

			}
			
			
			
			
			
		}
		return null;

	}

	public void saveNotification(Distributor distributor, String message, DeliveryCharge deliveryCharge) {

		Notification notification = new Notification();

		notification.setStatus(message);
		notification.setCreatedAt(new Date());
		notification.setDist_id(distributor.getId());
		notification.setNsmid(distributor.getNsmid());
		notification.setRsmid(distributor.getRsmid());
		notification.setAsmid(distributor.getAsmid());
		notification.setAseid(distributor.getAseid());
		notification.setDc_id(deliveryCharge.getId());
		notification.setDistributor_read("unread");
		notification.setRetailer_read("unread");
		notification.setNsm_read("unread");
		notification.setRsm_read("unread");
		notification.setAsm_read("unread");
		notification.setAse_read("unread");
		notification.setAdmin_read("unread");
		notificationRepository.save(notification);
	}

	public void saveNotification(Retailer retailer, String message, DeliveryCharge deliveryCharge) {

		Notification notification = new Notification();

		notification.setStatus(message);
		notification.setCreatedAt(new Date());
		notification.setRet_id(retailer.getId());
		notification.setNsmid(retailer.getNsmid());
		notification.setRsmid(retailer.getRsmid());
		notification.setAsmid(retailer.getAsmid());
		notification.setAseid(retailer.getAseid());
		notification.setDc_id(deliveryCharge.getId());
		notification.setDistributor_read("unread");
		notification.setRetailer_read("unread");
		notification.setNsm_read("unread");
		notification.setRsm_read("unread");
		notification.setAsm_read("unread");
		notification.setAse_read("unread");
		notification.setAdmin_read("unread");
		notificationRepository.save(notification);
	}

}
