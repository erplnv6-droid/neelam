package com.SCM.serviceimpl;


import java.text.SimpleDateFormat;
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

import com.SCM.ExportDto.ExportWorkOrder;
import com.SCM.IndexDto.IndexPrimaryOrderByDistributorId;
import com.SCM.IndexDto.IndexWorkOrder;
import com.SCM.IndexDto.IndexWorkorderItemByRetailerId;
import com.SCM.NotificationRequest.Tokens;
import com.SCM.NotificationService.NotifyService;
import com.SCM.NotifyRepository.NotifiySaveRepository;
import com.SCM.NotifyRepository.TokenRepository;
import com.SCM.config.UserId;
import com.SCM.dto.WorkOrderDto;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.ActivityLog;
import com.SCM.model.Notification;
import com.SCM.model.Retailer;
import com.SCM.model.WorkOrder;
import com.SCM.model.WorkOrderItem;
import com.SCM.projection.OrderAchievementReportForRetailer;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.NotificationRepository;
import com.SCM.repository.PrimaryOrderItemsRepository;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.repository.WorkOrderItemRepo;
import com.SCM.repository.WorkOrderRepo;
import com.SCM.service.WorkOderService;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class WorkOderServiceImpl implements WorkOderService {

	@Autowired
	private WorkOrderRepo workOrderRepo;

	@Autowired
	private WorkOrderItemRepo workOrderItemRepo;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private PrimaryOrderItemsRepository primaryItemsRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private NotificationRepository notificationRepository;

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

	@Autowired
	private NotifyService notifyService;

	@Autowired
	private NotifiySaveRepository repository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private GetCurrentUserRoleName auth;

	@Autowired
	private UserId user;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private StaffRepo staffRepo;

	@Override
	public WorkOrder saveWorkOder(WorkOrderDto workOderDto) throws FirebaseMessagingException {

		Optional<Retailer> r = retailerRepo.findById(workOderDto.getRetailer().getId());
		Retailer rt = r.get();
		System.out.println("rt" + rt.getId());

		WorkOrder lastWo = workOrderRepo.findFirstByOrderByIdDesc();
		if (lastWo == null) {
			int count = 1;
			String woid = "SO-";
			workOderDto.setWorkOrderId(woid + count);
		} else {

			int count = lastWo.getId() + 1;
			String woid = "SO-";
			workOderDto.setWorkOrderId(woid + count);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		Date date = new Date();
		Date date1 = new Date();
		System.out.println(formatter.format(date));
		workOderDto.setCreatedDateAndTime((formatter.format(date)));
		workOderDto.setDateCreated(date1);
		workOderDto.setWorkOderItems(workOderDto.getWorkOderItems());
		WorkOrder workOrder = new WorkOrder();
		workOrder.setWorkOrderDate(workOderDto.getWorkOrderDate());
		workOrder.setConvertedToPo(false);
		workOrder.setOrderStatus(false);
		workOrder.setWorkOrderId(workOderDto.getWorkOrderId());
		workOrder.setDateCreated(workOderDto.getDateCreated());
		workOrder.setSaleAgentId(workOderDto.getSaleAgentId());
		workOrder.setRetailerPinCode(workOderDto.getRetailerPinCode());
		workOrder.setRemarks(workOderDto.getRemarks());
		workOrder.setCreatedDateAndTime(workOderDto.getCreatedDateAndTime());
		workOrder.setPaymentTerms(workOderDto.getPaymentTerms());
		workOrder.setGrossTotal(workOderDto.getGrossTotal());
		workOrder.setNetValue(workOderDto.getNetValue());
		workOrder.setTaxAmount(workOderDto.getTaxAmount());
		workOrder.setKgProductTotalQtyKg(workOderDto.getKgProductTotalQtyKg());
		workOrder.setKgProductTotalQtyPcs(workOderDto.getKgProductTotalQtyPcs());
		workOrder.setBoxProductTotalQtyPcs(workOderDto.getBoxProductTotalQtyPcs());
		workOrder.setCorporateProductTotalQtyPcs(workOderDto.getCorporateProductTotalQtyPcs());
		workOrder.setCookerProductTotalQtyPcs(workOderDto.getCookerProductTotalQtyPcs());
		workOrder.setNoshProductTotalQtyPcs(workOderDto.getNoshProductTotalQtyPcs());
		workOrder.setTotalQtyKg(workOderDto.getTotalQtyKg());
		workOrder.setTotalQtyPcs(workOderDto.getTotalQtyPcs());
		workOrder.setKgProductTotalprice(workOderDto.getKgProductTotalprice());
		workOrder.setBoxProductTotalprice(workOderDto.getBoxProductTotalprice());
		workOrder.setCorporateProductTotalprice(workOderDto.getCorporateProductTotalprice());
		workOrder.setCookerProductTotalprice(workOderDto.getCookerProductTotalprice());
		workOrder.setNoshProductTotalprice(workOderDto.getNoshProductTotalprice());
		workOrder.setRetailerAddress(workOderDto.getRetailerAddress());
		workOrder.setRet_id(workOderDto.getRetailer().getId());
		workOrder.setDeliveryAddress(workOderDto.getDeliveryAddress());
		workOrder.setPaymentTerms(workOderDto.getPaymentTerms());
		workOrder.setDist_id(rt.getDistributor().getId());
		workOrder.setAseid(rt.getDistributor().getAseid());
		workOrder.setAsmid(rt.getDistributor().getAsmid());
		workOrder.setRsmid(rt.getDistributor().getRsmid());
		workOrder.setNsmid(rt.getDistributor().getNsmid());
		workOrder.setStateid(rt.getDistributor().getStateid());
		workOrder.setZonesid(rt.getDistributor().getZonesid());
		workOrder.setCreateddate(LocalDate.now());
		workOrder.setCreatedtime(LocalTime.now());
		workOrder.setCreatedBy(uid);
		workOrder.setCreatebyname(uname);
		workOrder.setRole(role);
		workOrder.setTotal(workOderDto.getTotal());
		workOrder.setWorkOderItems(workOderDto.getWorkOderItems());
		rt.getWorkOrders().add(workOrder);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setWorkorderid((long) workOrder.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		WorkOrder www = workOrderRepo.save(workOrder);

		this.saveNotification(rt, "Secondary Order created by " + userDetailsImpl.getUsername(), www);

//        NotificationDataCreate create=new NotificationDataCreate();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RETAILER"));
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DISTRIBUTOR"));
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_NSM"));
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RSM"));
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASM"));
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ASE"));
		authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		try {

			Optional<Tokens> notificationAdminWork = tokenRepository.getNotificationAdminWork();

			long staffid = notificationAdminWork.get().getStaffid();

			System.out.println("staffid" + staffid);

			String adminToken = " ";

			if (tokenRepository.findByStaffid(staffid).isPresent()) {
				Optional<Tokens> findByStaffid = tokenRepository.findByStaffid(staffid);

				adminToken = findByStaffid.get().getToken();
			}

			System.out.println("adminToken" + adminToken);

			if (tokenRepository.getNotificationRetailer(rt.getId()).isPresent()) {
				Optional<Tokens> token = tokenRepository.getNotificationRetailer(rt.getId());

				long ret_id = token.get().getStaffid();
				System.out.println("retid suraj" + ret_id);
				int retid = (int) ret_id;

//	        
				System.out.println("retid" + retid);

//	        Staff staff=new Staff();
				String retToken = " ";

				if (tokenRepository.findByStaffid(retid).isPresent()) {
					Optional<Tokens> findByStaffid2 = tokenRepository.findByStaffid(retid);

					retToken = findByStaffid2.get().getToken();

					System.out.println("retToken" + retToken);
				}

				Optional<Retailer> findById = retailerRepo.findById(retid);
				int distid = findById.get().getDistributor().getId();
				System.out.println("dist id" + distid);

				String token2 = " ";

				if (tokenRepository.findByStaffid(distid).isPresent()) {
					Optional<Tokens> dist = tokenRepository.findByStaffid(distid);
					token2 = dist.get().getToken();
				}

//	      String token2 = dist.get().getToken();

				int rsmid = findById.get().getRsmid();

				System.out.println("rsmid" + rsmid);

				String rsmToken = " ";

				if (tokenRepository.findByStaffid(rsmid).isPresent()) {
					Optional<Tokens> rsmid1 = tokenRepository.findByStaffid(rsmid);

					rsmToken = rsmid1.get().getToken();
				}

				int nsmid = findById.get().getNsmid();

				String nsmToken = " ";
				if (tokenRepository.findByStaffid(nsmid).isPresent()) {
					Optional<Tokens> nsm_id = tokenRepository.findByStaffid(nsmid);

					nsmToken = nsm_id.get().getToken();
				}

				int asmid = findById.get().getAsmid();

				String asmToken = " ";
				if (tokenRepository.findByStaffid(asmid).isPresent()) {
					Optional<Tokens> asm_id = tokenRepository.findByStaffid(asmid);

					asmToken = asm_id.get().getToken();
				}

				int aseid = findById.get().getAseid();

				String aseToken = " ";
				if (tokenRepository.findByStaffid(aseid).isPresent()) {
					Optional<Tokens> ase_id = tokenRepository.findByStaffid(aseid);

					aseToken = ase_id.get().getToken();
				}

				List<String> allTokens = new ArrayList<String>();

				if (retToken != null && !retToken.isEmpty()) {
					allTokens.add(retToken);
				}

				if (token2 != null && !token2.isEmpty()) {
					allTokens.add(token2);
				}

				if (rsmToken != null && !rsmToken.isEmpty()) {
					allTokens.add(rsmToken);
				}

				if (nsmToken != null && !nsmToken.isEmpty()) {
					allTokens.add(nsmToken);
				}

				if (asmToken != null && !asmToken.isEmpty()) {
					allTokens.add(asmToken);
				}

				if (aseToken != null && !aseToken.isEmpty()) {
					allTokens.add(aseToken);
				}

				if (adminToken != null && !adminToken.isEmpty()) {
					allTokens.add(adminToken);
				}

				for (int i = 0; i < allTokens.size(); i++) {
					System.out.println(allTokens.get(i));
				}
				int id = workOrder.getId();

				System.out.println("id" + id);

				notifyService.sendNotificationByToken(allTokens,
						"Work Order Created by " + userDetailsImpl.getUsername(), "New Message", id);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return www;
	}

	@Override
	public List<WorkOrder> getAllWorkOrderWithRetailer() {

		List<WorkOrder> w = workOrderRepo.getAllWorkorderWithRetailer();

		return w;
	}

	@Override
	public WorkOrderDto getWorkOderById(int id) {

		Optional<WorkOrder> workOrderOptional = workOrderRepo.findById(id);
		System.out.println("WorkOrder:" + workOrderOptional);

		Optional<WorkOrder> op = workOrderRepo.findById(id);

		WorkOrderDto workOrderDto = new WorkOrderDto();
		if (op.isPresent()) {
			WorkOrder workOrder = op.get();

			workOrderDto.setId(workOrder.getId());
			workOrderDto.setWorkOrderId(workOrder.getWorkOrderId());
			workOrderDto.setWorkOrderDate(workOrder.getWorkOrderDate());
			workOrderDto.setDateCreated(workOrder.getDateCreated());
			workOrderDto.setRemarks(workOrder.getRemarks());
			workOrderDto.setRetailerPinCode(workOrder.getRetailerPinCode());
			workOrderDto.setSaleAgentId(workOrder.getSaleAgentId());
			workOrderDto.setDist_id(workOrder.getDist_id());
			workOrderDto.setRet_id(workOrder.getRet_id());
			workOrderDto.setOrderStatus(workOrder.isOrderStatus());
			workOrderDto.setConvertedToPo(workOrder.isConvertedToPo());
			workOrderDto.setRetailer(null);
			workOrderDto.setDeliveryAddress(workOrder.getDeliveryAddress());
			workOrderDto.setRetailerAddress(workOrder.getRetailerAddress());
			workOrderDto.setPaymentTerms(workOrder.getPaymentTerms());
			workOrderDto.setGrossTotal(workOrder.getGrossTotal());
			workOrderDto.setNetValue(workOrder.getNetValue());
			workOrderDto.setTaxAmount(workOrder.getTaxAmount());
			workOrderDto.setKgProductTotalQtyKg(workOrder.getKgProductTotalQtyKg());
			workOrderDto.setKgProductTotalQtyPcs(workOrder.getKgProductTotalQtyPcs());
			workOrderDto.setBoxProductTotalQtyPcs(workOrder.getBoxProductTotalQtyPcs());
			workOrderDto.setCorporateProductTotalQtyPcs(workOrder.getCorporateProductTotalQtyPcs());
			workOrderDto.setCookerProductTotalQtyPcs(workOrder.getCookerProductTotalQtyPcs());
			workOrderDto.setNoshProductTotalQtyPcs(workOrder.getNoshProductTotalQtyPcs());
			workOrderDto.setTotalQtyKg(workOrder.getTotalQtyKg());
			workOrderDto.setTotalQtyPcs(workOrder.getTotalQtyPcs());
			workOrderDto.setKgProductTotalprice(workOrder.getKgProductTotalprice());
			workOrderDto.setBoxProductTotalprice(workOrder.getBoxProductTotalprice());
			workOrderDto.setCorporateProductTotalprice(workOrder.getCorporateProductTotalprice());
			workOrderDto.setCookerProductTotalprice(workOrder.getCookerProductTotalprice());
			workOrderDto.setNoshProductTotalprice(workOrder.getNoshProductTotalprice());

			workOrderDto.setWorkOderItems(workOrder.getWorkOderItems());

			System.out.println("Retailer Id:");
			// System.out.println(retailerList);
			System.out.println("WorkOrderItems:");
			System.out.println(workOrder.getWorkOderItems());
//            System.out.println("WorkOrderNew:");
//            System.out.println(workOrdernew);
		}
//        return workOrdernew;
		return workOrderDto;
	}

	@Override
	public String deleteWorkOder(int id) {
		workOrderRepo.deleteById(id);
		return "WorkOrder Removed !!" + id;
	}

	@Override
	public WorkOrderDto updateWorkOder(WorkOrderDto workOrderDto, int id) {

		
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		System.out.println("updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

		
		
		
		
		
		
		
		
		
		
		Optional<WorkOrder> existingWorkOderOptional = workOrderRepo.findById(id);

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();

		WorkOrder existingWorkOder = existingWorkOderOptional.get();

		existingWorkOder.setWorkOrderDate(workOrderDto.getWorkOrderDate());
		existingWorkOder.setRet_id(workOrderDto.getRet_id());
		existingWorkOder.setRetailerPinCode(workOrderDto.getRetailerPinCode());
		existingWorkOder.setOrderStatus(workOrderDto.isOrderStatus());
		existingWorkOder.setRemarks(workOrderDto.getRemarks());
		existingWorkOder.setSaleAgentId(workOrderDto.getSaleAgentId());
		existingWorkOder.setDist_id(workOrderDto.getDist_id());
		existingWorkOder.setRet_id(workOrderDto.getRet_id());
		existingWorkOder.setDeliveryAddress(workOrderDto.getDeliveryAddress());
		existingWorkOder.setDateCreated(workOrderDto.getDateCreated());
		existingWorkOder.setGrossTotal(workOrderDto.getGrossTotal());
		existingWorkOder.setNetValue(workOrderDto.getNetValue());
		existingWorkOder.setTaxAmount(workOrderDto.getTaxAmount());
		existingWorkOder.setRetailerAddress(workOrderDto.getRetailerAddress());
		existingWorkOder.setKgProductTotalQtyKg(workOrderDto.getKgProductTotalQtyKg());
		existingWorkOder.setKgProductTotalQtyPcs(workOrderDto.getKgProductTotalQtyPcs());
		existingWorkOder.setBoxProductTotalQtyPcs(workOrderDto.getBoxProductTotalQtyPcs());
		existingWorkOder.setCorporateProductTotalQtyPcs(workOrderDto.getCorporateProductTotalQtyPcs());
		existingWorkOder.setCookerProductTotalQtyPcs(workOrderDto.getCookerProductTotalQtyPcs());
		existingWorkOder.setNoshProductTotalQtyPcs(workOrderDto.getNoshProductTotalQtyPcs());
		existingWorkOder.setTotalQtyKg(workOrderDto.getTotalQtyKg());
		existingWorkOder.setRetailerAddress(workOrderDto.getRetailerAddress());
		existingWorkOder.setTotalQtyPcs(workOrderDto.getTotalQtyPcs());
		existingWorkOder.setKgProductTotalprice(workOrderDto.getKgProductTotalprice());
		existingWorkOder.setBoxProductTotalprice(workOrderDto.getBoxProductTotalprice());
		existingWorkOder.setCorporateProductTotalprice(workOrderDto.getCorporateProductTotalprice());
		existingWorkOder.setCookerProductTotalprice(workOrderDto.getCookerProductTotalprice());
		existingWorkOder.setNoshProductTotalprice(workOrderDto.getNoshProductTotalprice());
		existingWorkOder.setWorkOderItems(workOrderDto.getWorkOderItems());
		existingWorkOder.setUpdateddate(LocalDate.now());
		existingWorkOder.setUpdatedtime(LocalTime.now());
		existingWorkOder.setUpdatedbyname(uname);
		existingWorkOder.setUpdatedby(uid);
		existingWorkOder.setUpdatedrole(role);

		WorkOrder newWorkOrder = workOrderRepo.save(existingWorkOder);

		WorkOrderDto newWorkOrderDto1 = new WorkOrderDto();

		newWorkOrderDto1.setWorkOrderId(newWorkOrder.getWorkOrderId());
		newWorkOrderDto1.setWorkOrderDate(newWorkOrder.getWorkOrderDate());
		newWorkOrderDto1.setOrderStatus(newWorkOrder.isOrderStatus());
		newWorkOrderDto1.setRemarks(newWorkOrder.getRemarks());
		newWorkOrderDto1.setRetailerPinCode(newWorkOrder.getRetailerPinCode());
		newWorkOrderDto1.setDist_id(newWorkOrder.getDist_id());
		newWorkOrderDto1.setRet_id(newWorkOrder.getRet_id());
		newWorkOrderDto1.setDeliveryAddress(newWorkOrder.getDeliveryAddress());
		newWorkOrderDto1.setWorkOderItems(newWorkOrder.getWorkOderItems());
		newWorkOrderDto1.setPaymentTerms(newWorkOrder.getPaymentTerms());
		newWorkOrderDto1.setGrossTotal(newWorkOrder.getGrossTotal());
		newWorkOrderDto1.setNetValue(newWorkOrder.getNetValue());
		newWorkOrderDto1.setTaxAmount(newWorkOrder.getTaxAmount());
		newWorkOrderDto1.setKgProductTotalQtyKg(newWorkOrder.getKgProductTotalQtyKg());
		newWorkOrderDto1.setKgProductTotalQtyPcs(newWorkOrder.getKgProductTotalQtyPcs());
		newWorkOrderDto1.setBoxProductTotalQtyPcs(newWorkOrder.getBoxProductTotalQtyPcs());
		newWorkOrderDto1.setCorporateProductTotalQtyPcs(newWorkOrder.getCorporateProductTotalQtyPcs());
		newWorkOrderDto1.setCookerProductTotalQtyPcs(newWorkOrder.getCookerProductTotalQtyPcs());
		newWorkOrderDto1.setNoshProductTotalQtyPcs(newWorkOrder.getNoshProductTotalQtyPcs());
		newWorkOrderDto1.setTotalQtyKg(newWorkOrder.getTotalQtyKg());
		newWorkOrderDto1.setRetailerAddress(newWorkOrder.getRetailerAddress());
		newWorkOrderDto1.setTotalQtyPcs(newWorkOrder.getTotalQtyPcs());
		newWorkOrderDto1.setKgProductTotalprice(newWorkOrder.getKgProductTotalprice());
		newWorkOrderDto1.setBoxProductTotalprice(newWorkOrder.getBoxProductTotalprice());
		newWorkOrderDto1.setCorporateProductTotalprice(newWorkOrder.getCorporateProductTotalprice());
		newWorkOrderDto1.setCookerProductTotalprice(newWorkOrder.getCookerProductTotalprice());
		newWorkOrderDto1.setNoshProductTotalprice(newWorkOrder.getNoshProductTotalprice());
		newWorkOrderDto1.setUpdateddate(LocalDate.now());
		newWorkOrderDto1.setUpdatedtime(LocalTime.now());

		newWorkOrderDto1.setUpdatedbyname(uname);
		newWorkOrderDto1.setUpdatedby(uid);
		newWorkOrderDto1.setUpdatedrole(role);

		System.out.println(existingWorkOder);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setWorkorderid((long) newWorkOrder.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return newWorkOrderDto1;
	}

//    @Override
//    public List<WorkOrder> getWorkOrdersByDistributor(int id, String from_date, String to_date) {
//        List<WorkOrder> workOrder = workOrderRepo.getWorkOrdersByDistributorId(id, from_date,to_date);
//        return workOrder;
//    }

	@Override
	public List<WorkOrderDto> getWorkOrdersbyId(int d_id, String from_date, String to_date) {

		List<WorkOrderDto> WorkOrderDto = new ArrayList<>();

		List<WorkOrder> w = workOrderRepo.getWorkOrder(d_id, from_date, to_date);

		for (WorkOrder wor : w) {

			WorkOrderDto woDto = new WorkOrderDto();
			woDto.setId(wor.getId());
			woDto.setWorkOderItems(wor.getWorkOderItems());
			woDto.setWorkOrderId(wor.getWorkOrderId());
			woDto.setWorkOrderDate(wor.getWorkOrderDate());
			woDto.setDateCreated(wor.getDateCreated());
			woDto.setRetailerPinCode(wor.getRetailerPinCode());
			woDto.setRemarks(wor.getRemarks());
			woDto.setSaleAgentId(wor.getSaleAgentId());
			woDto.setRet_id(wor.getRet_id());
			woDto.setDeliveryAddress(wor.getDeliveryAddress());
			System.out.println(wor);
			WorkOrderDto.add(woDto);
		}
		return WorkOrderDto;
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByASE(int aseId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByAseID(aseId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByASM(int asmId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByAsmID(asmId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByRSM(int rsmId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByRsmID(rsmId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByNSM(int nsmId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByNsmID(nsmId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByZONE(int zonesId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByZoneId(zonesId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderBySTATE(int stateId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderBystateId(stateId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByDistributor(int distId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByDistributorId(distId);
		return mapToListDto(w);
	}

	@Override
	public List<WorkOrderDto> getWorkOrderByRetailer(int retId) {
		List<WorkOrder> w = workOrderRepo.getWorkOrderByRetailerId(retId);
		return mapToListDto(w);
	}

	public List<WorkOrderDto> mapToListDto(List<WorkOrder> workOrders) {
		List<WorkOrderDto> wo = new ArrayList<>();
		for (WorkOrder w : workOrders) {

			WorkOrderDto workOrderDto = new WorkOrderDto();
			workOrderDto.setId(w.getId());
			workOrderDto.setWorkOrderId(w.getWorkOrderId());
			workOrderDto.setWorkOrderDate(w.getWorkOrderDate());
			workOrderDto.setRemarks(w.getRemarks());
			workOrderDto.setCreatedDateAndTime(w.getCreatedDateAndTime());
			workOrderDto.setDateCreated(w.getDateCreated());
			workOrderDto.setSaleAgentId(w.getSaleAgentId());
			workOrderDto.setRetailerPinCode(w.getRetailerPinCode());
			workOrderDto.setRet_id(w.getRet_id());
			workOrderDto.setPaymentTerms(w.getPaymentTerms());
			workOrderDto.setGrossTotal(w.getGrossTotal());
			workOrderDto.setNetValue(w.getNetValue());
			workOrderDto.setTaxAmount(w.getTaxAmount());
			workOrderDto.setKgProductTotalQtyKg(w.getKgProductTotalQtyKg());
			workOrderDto.setKgProductTotalQtyPcs(w.getKgProductTotalQtyPcs());
			workOrderDto.setBoxProductTotalQtyPcs(w.getBoxProductTotalQtyPcs());
			workOrderDto.setCorporateProductTotalQtyPcs(w.getCorporateProductTotalQtyPcs());
			workOrderDto.setCookerProductTotalQtyPcs(w.getCookerProductTotalQtyPcs());
			workOrderDto.setNoshProductTotalQtyPcs(w.getNoshProductTotalQtyPcs());
			workOrderDto.setTotalQtyKg(w.getTotalQtyKg());
			workOrderDto.setTotalQtyPcs(w.getTotalQtyPcs());
			workOrderDto.setKgProductTotalprice(w.getKgProductTotalprice());
			workOrderDto.setBoxProductTotalprice(w.getBoxProductTotalprice());
			workOrderDto.setCorporateProductTotalprice(w.getCorporateProductTotalprice());
			workOrderDto.setCookerProductTotalprice(w.getCookerProductTotalprice());
			workOrderDto.setNoshProductTotalprice(w.getNoshProductTotalprice());
			workOrderDto.setRetailerAddress(w.getRetailerAddress());
			workOrderDto.setDeliveryAddress(w.getDeliveryAddress());
			workOrderDto.setWorkOderItems(w.getWorkOderItems());
			workOrderDto.setAseid(w.getAseid());
			workOrderDto.setAsmid(w.getAsmid());
			workOrderDto.setRsmid(w.getRsmid());
			workOrderDto.setNsmid(w.getNsmid());
			workOrderDto.setStateid(w.getStateid());
			workOrderDto.setZonesid(w.getZonesid());

			wo.add(workOrderDto);
		}
		return wo;
	}

	@Override
	public WorkOrder getWorkOrderByPrimaryOrder(int id) {
		WorkOrder wo = workOrderRepo.getWorkOrderByPrimaryOrder(id);
		return wo;
	}

	public void updateEstimatedDays(int id, int pid, Date days, int primaryItemId) {

		WorkOrder wo = workOrderRepo.getWorkOrderByPrimaryOrder(id);

		if (wo == null) {
			primaryItemsRepo.updatePrimaryOrderEstimatedDaysById(days, primaryItemId);
		} else {
			List<WorkOrderItem> woi = wo.getWorkOderItems();

			for (WorkOrderItem w : woi) {

				System.out.println(w.getProductId());
				System.out.println(pid);

				if (w.getProductId() == pid) {

					System.out.println(w.getProductId());
					System.out.println(pid);

					workOrderItemRepo.updateWorkOrderEstimatedDaysById(days, w.getwItemId(), pid); // update work order
																									// item
					System.out.println("In IF!!");
				}
				System.out.println("In FOR!!");
			}
			primaryItemsRepo.updatePrimaryOrderEstimatedDaysById(days, primaryItemId);
		}
	}

	@Override
	public Map<String, Object> IndexWorkOrderAsc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = workOrderRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.indexWorkorder(p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RETAILER")) {
				long countpages = workOrderRepo.findByRet_id(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByret_id(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = workOrderRepo.findByDist_id(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findBydist_id(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASE"))

			{
				long countpages = workOrderRepo.findByaseid(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByAseid(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASM"))

			{
				long countpages = workOrderRepo.findByasmid(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByAsmid(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RSM"))

			{
				long countpages = workOrderRepo.findByrsmid(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByRsmid(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

		}

		return null;

	}

	@Override
	public Map<String, Object> IndexWorkOrderDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				long countpages = workOrderRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.indexWorkorder(p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RETAILER")) {
				long countpages = workOrderRepo.findByRet_id(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByret_id(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = workOrderRepo.findByDist_id(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findBydist_id(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASE"))

			{
				long countpages = workOrderRepo.findByaseid(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexWorkOrder> iwo = workOrderRepo.findByAseid(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_ASM"))

			{
				long countpages = workOrderRepo.findByasmid(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByAsmid(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}

			else if (s.equals("ROLE_RSM"))

			{
				long countpages = workOrderRepo.findByrsmid(riid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexWorkOrder> iwo = workOrderRepo.findByRsmid(riid, p);
				response.put("Index", iwo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> SearchWorkOrder(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int riid = (int) (long) rid;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {

				List<IndexWorkOrder> wo = workOrderRepo.SearchByWorkOrderOrder(search, p);

				long searchcount = wo.size();

				response.put("data", wo);
				response.put("SearchCount", searchcount);

				return response;

			}

			else if (s.equals("ROLE_RETAILER")) {

				List<IndexWorkOrder> wo = workOrderRepo.SearchByret_id(riid, search, p);

				long searchcount = wo.size();

				response.put("data", wo);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {
				List<IndexWorkOrder> wo = workOrderRepo.SearchBydist_id(riid, search, p);

				long searchcount = wo.size();

				response.put("data", wo);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_ASE"))

			{
				List<IndexWorkOrder> wo = workOrderRepo.SearchByAseid(riid, search, p);

				long searchcount = wo.size();

				response.put("data", wo);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_ASM")) {
				List<IndexWorkOrder> wo = workOrderRepo.SearchByAsmid(riid, search, p);

				long searchcount = wo.size();

				response.put("data", wo);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_RSM"))

			{
				List<IndexWorkOrder> wo = workOrderRepo.SearchByRsmid(riid, search, p);

				long searchcount = wo.size();

				response.put("data", wo);
				response.put("SearchCount", searchcount);

				return response;
			}

		}

		return null;
	}

	@Override
	public List<ExportWorkOrder> exportWo() {

		return workOrderRepo.ExcelexportfromWorkOrder();

	}

	@Override
	public Map<String, Object> orderAchievementByRetailerAsc(int retid,Date startDate,Date endDate,
			int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		long countpages = workOrderRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<OrderAchievementReportForRetailer> data = workOrderRepo.orderAchievementReportByPagination(retid,
				startDate, endDate, p);

		response.put("index", data);
		response.put("Enteries", countpages);
		response.put("pages", pages);
		return response;
	}

	@Override
	public Map<String, Object> orderAchievementByRetailerDesc(int retid, Date startDate,Date endDate,
			int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		long countpages = workOrderRepo.orderAchievementReport(retid, startDate, endDate).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<OrderAchievementReportForRetailer> data = workOrderRepo.orderAchievementReportByPagination(retid,
				startDate, endDate, p);

		response.put("index", data);
		response.put("Enteries", countpages);
		response.put("pages", pages);
		return response;
	}

	@Override
	public Map<String, Object> ascworkorderItemByRetailerId(String startdate, String enddate, int retid, int pageno,
			int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort.unsorted());
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
		
	long countpages = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDate(startdate, enddate, retid).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePagination(startdate, enddate, retid, p);

		response.put("Index", witem);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			if (s.equals("ROLE_DISTRIBUTOR")) {
				
				long countpages = workOrderItemRepo.count();
					long pages = countpages / pagesize;

					long rem = countpages % pagesize;
					if (rem > 0) {
						pages++;
					}
					List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationDistributor(startdate, enddate, retid, p);

					response.put("Index", witem);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
						}
			
if (s.equals("ROLE_RSM")) {
				
				long countpages = workOrderItemRepo.count();
					long pages = countpages / pagesize;

					long rem = countpages % pagesize;
					if (rem > 0) {
						pages++;
					}
					List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationRsm(startdate, enddate, uid, p);

					response.put("Index", witem);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
						}
			
if (s.equals("ROLE_ASM")) {
	
	long countpages = workOrderItemRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationAsm(startdate, enddate, uid, p);

		response.put("Index", witem);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}	


if (s.equals("ROLE_ASE")) {

long countpages = workOrderItemRepo.count();
long pages = countpages / pagesize;

long rem = countpages % pagesize;
if (rem > 0) {
pages++;
}
List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationAse(startdate, enddate, uid, p);

response.put("Index", witem);
response.put("Enteries", countpages);
response.put("Pages", pages);

return response;
}	

			
			
		}
		return null;
	}

	@Override
	public Map<String, Object> descworkorderItemByRetailerId(String startdate, String enddate, int retid, int pageno,
			int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort.unsorted());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
		
	long countpages = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDate(startdate, enddate, retid).size();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePagination(startdate, enddate, retid, p);

		response.put("Index", witem);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			if (s.equals("ROLE_DISTRIBUTOR")) {
				
				long countpages = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationDistributor(startdate, enddate, retid).size();
					long pages = countpages / pagesize;

					long rem = countpages % pagesize;
					if (rem > 0) {
						pages++;
					}
					List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationDistributor(startdate, enddate, retid, p);

					response.put("Index", witem);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
						}
			
if (s.equals("ROLE_RSM")) {
				
				long countpages = workOrderItemRepo.count();
					long pages = countpages / pagesize;

					long rem = countpages % pagesize;
					if (rem > 0) {
						pages++;
					}
					List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationRsm(startdate, enddate, retid, p);

					response.put("Index", witem);
					response.put("Enteries", countpages);
					response.put("Pages", pages);

					return response;
						}
			
if (s.equals("ROLE_ASM")) {
	
	long countpages = workOrderItemRepo.count();
		long pages = countpages / pagesize;

		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}
		List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationAsm(startdate, enddate, retid, p);

		response.put("Index", witem);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}	


if (s.equals("ROLE_ASE")) {

long countpages = workOrderItemRepo.count();
long pages = countpages / pagesize;

long rem = countpages % pagesize;
if (rem > 0) {
pages++;
}
List<IndexWorkorderItemByRetailerId> witem = workOrderItemRepo.getWorkOrderItemsByRetailerIdWithinDatePaginationAse(startdate, enddate, retid, p);

response.put("Index", witem);
response.put("Enteries", countpages);
response.put("Pages", pages);

return response;
}	
		}
		return null;
		
	}

	@Override
	public Map<String, Object> searchworkorderItemByRetailerId(String startdate, String enddate, int retid, int pageno,
			int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM"))
					{
				List<IndexWorkorderItemByRetailerId> poitems = workOrderItemRepo
						.searchWorkOrderItemsByRetailerIdWithinDate(startdate, enddate, retid, search, p);
				long searchcount = poitems.size();
				response.put("data", poitems);
				response.put("SearchCount", searchcount);
				return response;
		}
			
			if (s.equals("ROLE_DISTRIBUTOR"))
			{
		List<IndexWorkorderItemByRetailerId> poitems = workOrderItemRepo
				.searchWorkOrderItemsByRetailerIdWithinDateDistributor(startdate, enddate, retid, search, p);
		long searchcount = poitems.size();
		response.put("data", poitems);
		response.put("SearchCount", searchcount);
		return response;
}
		
			if (s.equals("ROLE_RSM"))
			{
		List<IndexWorkorderItemByRetailerId> poitems = workOrderItemRepo
				.searchWorkOrderItemsByRetailerIdWithinDateRsm(startdate, enddate, retid, search, p);
		long searchcount = poitems.size();
		response.put("data", poitems);
		response.put("SearchCount", searchcount);
		return response;
}
	
			if (s.equals("ROLE_ASM"))
			{
		List<IndexWorkorderItemByRetailerId> poitems = workOrderItemRepo
				.searchWorkOrderItemsByRetailerIdWithinDateAsm(startdate, enddate, retid, search, p);
		long searchcount = poitems.size();
		response.put("data", poitems);
		response.put("SearchCount", searchcount);
		return response;
}		
			
			if (s.equals("ROLE_ASE"))
			{
		List<IndexWorkorderItemByRetailerId> poitems = workOrderItemRepo
				.searchWorkOrderItemsByRetailerIdWithinDateAse(startdate, enddate, retid, search, p);
		long searchcount = poitems.size();
		response.put("data", poitems);
		response.put("SearchCount", searchcount);
		return response;
}	
			
			
	}
		return null;
	
	}
//	@Override
//	public List<WorkOrder> findByRet_id() {
//		
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		
//        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal(); 
//        
//        Long rid=userDetails.getId();
//        
//        int riid = (int) (long) rid;
//        
//		List<WorkOrder> findByRet_id = workOrderRepo.findByret_id(riid);
//		
//		return findByRet_id;
//	}

	public void saveNotification(Retailer retailer, String message, WorkOrder workOrder) {
		System.out.println("retailer::" + retailer.getDistributor().getId());
		Notification notification = new Notification();

		notification.setStatus(message);
		notification.setCreatedAt(new Date());
		notification.setRet_id(retailer.getId());
		notification.setDist_id(retailer.getDistributor().getId());
		notification.setNsmid(retailer.getNsmid());
		notification.setRsmid(retailer.getRsmid());
		notification.setAsmid(retailer.getAsmid());
		notification.setAseid(retailer.getAseid());
		notification.setW_id(workOrder.getId());
		notification.setDistributor_read("unread");
		notification.setRetailer_read("unread");
		notification.setNsm_read("unread");
		notification.setRsm_read("unread");
		notification.setAsm_read("unread");
		notification.setAse_read("unread");
		notification.setAdmin_read("unread");
		notificationRepository.save(notification);
	}



//	@Override
//	public Map<String, Object> orderAchievementByRetailerAsc(int retid, Date startDate, Date endDate, int pagno,
//			int pagesize, String field) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Map<String, Object> orderAchievementByRetailerDesc(int retid, Date startDate, Date endDate, int pagno,
//			int pagesize, String field) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	public Tokens saveTokens(Tokens tokens,Retailer retailer,WorkOrderDto workOrderDto)
//	{
//
//		Optional<Retailer> r = retailerRepo.findById(workOrderDto.getRetailer().getId());
//		Retailer rt = r.get();
//		
//		
//		Long uid = auth.getUserId();
//		String uname=auth.getUserName();
//
//		String role=auth.getRolename();
//		
//
//	tokens.setRolename(role);
//	tokens.setStaff_id(uid);
//	tokens.setStaffname(uname);
//	tokens.setRet_id(rt.getId());
//	tokens.setDist_id(rt.getDistributor().getId());
//	tokens.setNsmid(rt.getNsmid());
//	tokens.setRsmid(rt.getRsmid());
//	tokens.setAsmid(rt.getAsmid());
//	tokens.setAseid(rt.getAseid());
//	tokens.setW_id(workOrderDto.getId());
//	
//	
//	
//	
//		
//		
//		return tokenRepository.save(tokens);
//		
//		
//		
//		
//	}
	
	

}
