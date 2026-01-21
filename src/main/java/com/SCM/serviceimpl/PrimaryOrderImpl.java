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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SCM.ExportDto.ExportPrimaryWorkOrder;
import com.SCM.IndexDto.IndexPrimaryOrder;
import com.SCM.IndexDto.IndexPrimaryOrderByDistributorId;
import com.SCM.NotificationRequest.Tokens;
import com.SCM.NotificationService.NotifyService;
import com.SCM.NotifyRepository.TokenRepository;
import com.SCM.config.UserId;
import com.SCM.dto.PrimaryOrderDto;
import com.SCM.model.ActivityLog;
import com.SCM.model.Distributor;
import com.SCM.model.Notification;
import com.SCM.model.PrimaryOrder;
import com.SCM.model.WorkOrder;
import com.SCM.projection.PrimaryOrderIndexReport;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.NotificationRepository;
import com.SCM.repository.PrimaryOrderItemsRepository;
import com.SCM.repository.PrimaryOrderRepository;
import com.SCM.repository.WorkOrderRepo;
import com.SCM.service.PrimaryOrderService;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class PrimaryOrderImpl implements PrimaryOrderService {

	@Autowired
	private PrimaryOrderRepository primaryOrderRepository;

	@Autowired
	private WorkOrderRepo workOrderRepo;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private NotifyService notifyService;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private UserId userId;

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
	public PrimaryOrder savePrimaryWorkOder(PrimaryOrderDto primaryOrderDto) throws FirebaseMessagingException {

		PrimaryOrder lastpo = primaryOrderRepository.findFirstByOrderByIdDesc();

		if (lastpo == null) {
			
			int count = 1;
			String poid = "PO-";
			primaryOrderDto.setWorkOrderId(poid + count);

		} else {

			int count = lastpo.getId() + 1;
			String poid = "PO-";
			primaryOrderDto.setWorkOrderId(poid + count);
		}

		Optional<Distributor> d = distributorRepo.findById(primaryOrderDto.getDistributor().getId());
		Distributor distributor = d.get();

		PrimaryOrder primaryOrder = new PrimaryOrder();

		Long uid = getUserId();
		String uname = getUserName();

		String role = getRolename();
		primaryOrder.setCreatebyname(uname);
		primaryOrder.setCreatedby(uid);
		primaryOrder.setRole(role);

		primaryOrder.setWorkOrderId(primaryOrderDto.getWorkOrderId());
		primaryOrder.setWorkOrderDate(primaryOrderDto.getWorkOrderDate());
		primaryOrder.setDateCreated(primaryOrderDto.getDateCreated());
		primaryOrder.setRemarks(primaryOrderDto.getRemarks());
		primaryOrder.setStatus("pending");
		primaryOrder.setRetailerPinCode(primaryOrderDto.getRetailerPinCode());
		primaryOrder.setSaleAgentId(primaryOrderDto.getSaleAgentId());
		primaryOrder.setFrom_date(primaryOrderDto.getFrom_date());
		primaryOrder.setTo_Date(primaryOrderDto.getTo_Date());
		primaryOrder.setDistributor(primaryOrderDto.getDistributor());
		primaryOrder.setPrimaryOrderItems(primaryOrderDto.getPrimaryOrderItems());
		primaryOrder.setGrossTotal(primaryOrderDto.getGrossTotal());
		primaryOrder.setNetValue(primaryOrderDto.getNetValue());
		primaryOrder.setTaxAmount(primaryOrderDto.getTaxAmount());
		primaryOrder.setKgProductTotalQtyKg(primaryOrderDto.getKgProductTotalQtyKg());
		primaryOrder.setKgProductTotalQtyPcs(primaryOrderDto.getKgProductTotalQtyPcs());
		primaryOrder.setBoxProductTotalQtyPcs(primaryOrderDto.getBoxProductTotalQtyPcs());
		primaryOrder.setCorporateProductTotalQtyPcs(primaryOrderDto.getCorporateProductTotalQtyPcs());
		primaryOrder.setCookerProductTotalQtyPcs(primaryOrderDto.getCookerProductTotalQtyPcs());
		primaryOrder.setNoshProductTotalQtyPcs(primaryOrderDto.getNoshProductTotalQtyPcs());
		primaryOrder.setTotalQtyKg(primaryOrderDto.getTotalQtyKg());
		primaryOrder.setTotalQtyPcs(primaryOrderDto.getTotalQtyPcs());
		primaryOrder.setDistributorAddress(primaryOrderDto.getDistributorAddress());
		primaryOrder.setDeliveryAddress(primaryOrderDto.getDeliveryAddress());
		primaryOrder.setKgProductTotalprice(primaryOrderDto.getKgProductTotalprice());
		primaryOrder.setBoxProductTotalprice(primaryOrderDto.getBoxProductTotalprice());
		primaryOrder.setCorporateProductTotalprice(primaryOrderDto.getCorporateProductTotalprice());
		primaryOrder.setCookerProductTotalprice(primaryOrderDto.getCookerProductTotalprice());
		primaryOrder.setNoshProductTotalprice(primaryOrderDto.getNoshProductTotalprice());
		primaryOrder.setAseid(distributor.getAseid());
		primaryOrder.setAsmid(distributor.getAsmid());
		primaryOrder.setRsmid(distributor.getRsmid());
		primaryOrder.setNsmid(distributor.getNsmid());
		primaryOrder.setStateid(distributor.getStateid());
		primaryOrder.setZonesid(distributor.getZonesid());
		primaryOrder.setCreateddate(LocalDate.now());
		primaryOrder.setCreatedtime(LocalTime.now());
		primaryOrder.setEdd(primaryOrderDto.getEdd());

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		PrimaryOrder savedPrimaryOrder = primaryOrderRepository.save(primaryOrder);
		int primary_int = savedPrimaryOrder.getId();
		this.saveNotification(distributor, "Primary Order Created By " + userDetailsImpl.getUsername(),
				savedPrimaryOrder);

		System.out.println();
		List<Object> obj = primaryOrderDto.getSecondaryIds();
		System.out.println(obj);

		for (Object l : obj) {

			Optional<WorkOrder> w = workOrderRepo.findById((Integer) l);
			WorkOrder wo = w.get();

			wo.setConvertedToPo(true);
			wo.setPrimaryorderId(savedPrimaryOrder.getId());

			System.out.println(w);
			workOrderRepo.save(wo);
			System.out.println(wo);
		}

		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setPrimaryorderid((long) savedPrimaryOrder.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);
		
		try {
			
			
			Optional<Tokens> notificationAdminWork = tokenRepository.getNotificationAdminWork();

			long staffid = notificationAdminWork.get().getStaffid();

			System.out.println("staffid"+staffid);

			Optional<Tokens> findByStaffid = tokenRepository.findByStaffid(staffid);

			String adminToken=findByStaffid.get().getToken();

			System.out.println("adminToken"+adminToken);
			
			
			
			
			
			if(tokenRepository.getNotificationDistributor(distributor.getId()).isPresent())
			{
		Optional<Tokens> notificationDistributor = tokenRepository.getNotificationDistributor(distributor.getId());
			
//			String tokens=notificationDistributor.get().getToken();
//			System.out.println("token"+tokens);
			
			
	long distid=notificationDistributor.get().getStaffid();

	int dist_id=(int) distid;


	Optional<Distributor> findById = distributorRepo.findById(dist_id);

	System.out.println("findbyid"+findById);


	String distToken=" ";
	if(tokenRepository.findByStaffid(dist_id).isPresent())
	{
	Optional<Tokens> findByStaffid2 = tokenRepository.findByStaffid(dist_id);

	 distToken=findByStaffid2.get().getToken();

	System.out.println("distToken"+distToken);

	}


	int nsmid = findById.get().getNsmid();

	System.out.println("nsm"+nsmid);

	String nsmToken=" ";
	if(tokenRepository.findByStaffid(nsmid).isPresent())
	{
	Optional<Tokens> findByStaffid1 = tokenRepository.findByStaffid(nsmid);

	 nsmToken = findByStaffid1.get().getToken();
	}
	int rsmid=findById.get().getRsmid();


	String rsmToken=" ";
	if(tokenRepository.findByStaffid(rsmid).isPresent())
	{
	Optional<Tokens> findByStaffid2 = tokenRepository.findByStaffid(rsmid);

	 rsmToken = findByStaffid2.get().getToken();
	}
	int asmid=findById.get().getAsmid();

	String asmToken=" ";
	if(tokenRepository.findByStaffid(asmid).isPresent())
	{
	Optional<Tokens> findByStaffid3 = tokenRepository.findByStaffid(asmid);

	 asmToken = findByStaffid3.get().getToken();
	}

	int aseid=findById.get().getAseid();

	String aseToken=" ";
	if(tokenRepository.findByStaffid(aseid).isPresent())
	{
	Optional<Tokens> findByStaffid4 = tokenRepository.findByStaffid(aseid);

	 aseToken = findByStaffid4.get().getToken();

	}
	List<String> allTokens=new ArrayList<String>();

	if(distToken != null && !distToken.isEmpty())
	{
		allTokens.add(distToken);
	}

	if(nsmToken!= null && !nsmToken.isEmpty())
	{
		allTokens.add(nsmToken);
	}

	if(rsmToken!= null && !rsmToken.isEmpty())
	{
		allTokens.add(rsmToken);
	}
	if(asmToken!= null && !asmToken.isEmpty())
	{
		allTokens.add(asmToken);
	}
	if(aseToken!=null && !aseToken.isEmpty())
	{
		allTokens.add(aseToken);
	}
	if(adminToken!=null && !adminToken.isEmpty())
	{
		allTokens.add(adminToken);
	}

	for(int i=0;i<allTokens.size();i++)
	{
		System.out.println(allTokens.get(i));
	}

	int id=primaryOrder.getId();

	System.out.println("id"+id);


	notifyService.sendNotificationByDistributor(allTokens,"Primary Order Created By " + userDetailsImpl.getUsername(),"New Message",id);
		
			}
			}
			 catch(Exception e)
		        {
		        	e.printStackTrace();
		        }
	return savedPrimaryOrder;
		}

	

	@Override
	public PrimaryOrderDto updatePrimaryOrder(PrimaryOrderDto primaryOrderDto, int id) {
		Optional<PrimaryOrder> existingPrimaryWorkOderOptional = primaryOrderRepository.findById(id);

		PrimaryOrder existingpriamryWorkOder = existingPrimaryWorkOderOptional.get();

		Long uid = getUserId();
		String uname = getUserName();
		String role = getRolename();
		
		existingpriamryWorkOder.setUpdatedbyname(uname);
		existingpriamryWorkOder.setUpdatedby(uid);
		existingpriamryWorkOder.setUpdatedrole(role);
		existingpriamryWorkOder.setUpdateddate(LocalDate.now());
		existingpriamryWorkOder.setUpdatedtime(LocalTime.now());

		existingpriamryWorkOder.setWorkOrderDate(primaryOrderDto.getWorkOrderDate());
		existingpriamryWorkOder.setRetailerPinCode(primaryOrderDto.getRetailerPinCode());
		existingpriamryWorkOder.setOrderStatus(primaryOrderDto.isOrderStatus());
		existingpriamryWorkOder.setRemarks(primaryOrderDto.getRemarks());
		existingpriamryWorkOder.setSaleAgentId(primaryOrderDto.getSaleAgentId());
//		existingpriamryWorkOder.setDateCreated(primaryOrderDto.getDateCreated());
		
		LocalDate localDate=primaryOrderDto.getDateCreated();
		
		existingpriamryWorkOder.setDateCreated(localDate);
		
		existingpriamryWorkOder.setFrom_date(primaryOrderDto.getFrom_date());
		existingpriamryWorkOder.setTo_Date(primaryOrderDto.getTo_Date());
		existingpriamryWorkOder.setStatus(primaryOrderDto.getStatus());
		existingpriamryWorkOder.setDistributor(primaryOrderDto.getDistributor());
		existingpriamryWorkOder.setDeliveryAddress(primaryOrderDto.getDeliveryAddress());
		existingpriamryWorkOder.setDistributorAddress(primaryOrderDto.getDistributorAddress());
		existingpriamryWorkOder.setGrossTotal(primaryOrderDto.getGrossTotal());
		existingpriamryWorkOder.setNetValue(primaryOrderDto.getNetValue());
		existingpriamryWorkOder.setTaxAmount(primaryOrderDto.getTaxAmount());
		existingpriamryWorkOder.setKgProductTotalQtyKg(primaryOrderDto.getKgProductTotalQtyKg());
		existingpriamryWorkOder.setKgProductTotalQtyPcs(primaryOrderDto.getKgProductTotalQtyPcs());
		existingpriamryWorkOder.setBoxProductTotalQtyPcs(primaryOrderDto.getBoxProductTotalQtyPcs());
		existingpriamryWorkOder.setCorporateProductTotalQtyPcs(primaryOrderDto.getCorporateProductTotalQtyPcs());
		existingpriamryWorkOder.setCookerProductTotalQtyPcs(primaryOrderDto.getCookerProductTotalQtyPcs());
		existingpriamryWorkOder.setNoshProductTotalQtyPcs(primaryOrderDto.getNoshProductTotalQtyPcs());
		existingpriamryWorkOder.setTotalQtyKg(primaryOrderDto.getTotalQtyKg());
		existingpriamryWorkOder.setTotalQtyPcs(primaryOrderDto.getTotalQtyPcs());
		existingpriamryWorkOder.setKgProductTotalprice(primaryOrderDto.getKgProductTotalprice());
		existingpriamryWorkOder.setBoxProductTotalprice(primaryOrderDto.getBoxProductTotalprice());
		existingpriamryWorkOder.setCorporateProductTotalprice(primaryOrderDto.getCorporateProductTotalprice());
		existingpriamryWorkOder.setCookerProductTotalprice(primaryOrderDto.getCookerProductTotalprice());
		existingpriamryWorkOder.setNoshProductTotalprice(primaryOrderDto.getNoshProductTotalprice());
existingpriamryWorkOder.setEdd(primaryOrderDto.getEdd());

		existingpriamryWorkOder.setPrimaryOrderItems(primaryOrderDto.getPrimaryOrderItems());

		PrimaryOrder newPrimaryWorkOrder = primaryOrderRepository.save(existingpriamryWorkOder);

		PrimaryOrderDto newPriamaryWorkOrderDto1 = new PrimaryOrderDto();

		newPriamaryWorkOrderDto1.setWorkOrderDate(newPrimaryWorkOrder.getWorkOrderDate());
		newPriamaryWorkOrderDto1.setOrderStatus(newPrimaryWorkOrder.isOrderStatus());
		newPriamaryWorkOrderDto1.setRemarks(newPrimaryWorkOrder.getRemarks());
		newPriamaryWorkOrderDto1.setDateCreated(newPrimaryWorkOrder.getDateCreated());
		newPriamaryWorkOrderDto1.setRetailerPinCode(newPrimaryWorkOrder.getRetailerPinCode());
		newPriamaryWorkOrderDto1.setFrom_date(newPrimaryWorkOrder.getFrom_date());
		newPriamaryWorkOrderDto1.setTo_Date(newPrimaryWorkOrder.getTo_Date());
		newPriamaryWorkOrderDto1.setDistributor(newPrimaryWorkOrder.getDistributor());
		newPriamaryWorkOrderDto1.setDeliveryAddress(newPrimaryWorkOrder.getDeliveryAddress());
		newPriamaryWorkOrderDto1.setDistributorAddress(newPrimaryWorkOrder.getDistributorAddress());
		newPriamaryWorkOrderDto1.setGrossTotal(newPrimaryWorkOrder.getGrossTotal());
		newPriamaryWorkOrderDto1.setNetValue(newPrimaryWorkOrder.getNetValue());
		newPriamaryWorkOrderDto1.setStatus(newPrimaryWorkOrder.getStatus());
		newPriamaryWorkOrderDto1.setTaxAmount(newPrimaryWorkOrder.getTaxAmount());
		newPriamaryWorkOrderDto1.setKgProductTotalQtyKg(newPrimaryWorkOrder.getKgProductTotalQtyKg());
		newPriamaryWorkOrderDto1.setKgProductTotalQtyPcs(newPrimaryWorkOrder.getKgProductTotalQtyPcs());
		newPriamaryWorkOrderDto1.setBoxProductTotalQtyPcs(newPrimaryWorkOrder.getBoxProductTotalQtyPcs());
		newPriamaryWorkOrderDto1.setCorporateProductTotalQtyPcs(newPrimaryWorkOrder.getCorporateProductTotalQtyPcs());
		newPriamaryWorkOrderDto1.setCookerProductTotalQtyPcs(newPrimaryWorkOrder.getCookerProductTotalQtyPcs());
		newPriamaryWorkOrderDto1.setNoshProductTotalQtyPcs(newPrimaryWorkOrder.getNoshProductTotalQtyPcs());
		newPriamaryWorkOrderDto1.setTotalQtyKg(newPrimaryWorkOrder.getTotalQtyKg());
		newPriamaryWorkOrderDto1.setTotalQtyPcs(newPrimaryWorkOrder.getTotalQtyPcs());
		newPriamaryWorkOrderDto1.setKgProductTotalprice(newPrimaryWorkOrder.getKgProductTotalprice());
		newPriamaryWorkOrderDto1.setBoxProductTotalprice(newPrimaryWorkOrder.getBoxProductTotalprice());
		newPriamaryWorkOrderDto1.setCorporateProductTotalprice(newPrimaryWorkOrder.getCorporateProductTotalprice());
		newPriamaryWorkOrderDto1.setCookerProductTotalprice(newPrimaryWorkOrder.getCookerProductTotalprice());
		newPriamaryWorkOrderDto1.setNoshProductTotalprice(newPrimaryWorkOrder.getNoshProductTotalprice());
newPriamaryWorkOrderDto1.setEdd(newPrimaryWorkOrder.getEdd());
		System.out.println(newPriamaryWorkOrderDto1);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setPrimaryorderid((long) newPrimaryWorkOrder.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return newPriamaryWorkOrderDto1;
	}

	@Override
	public String deletePrimaryWorkOder(int id) {
		primaryOrderRepository.deleteById(id);
		return "delete primary order" + id;
	}

	@Override
	public PrimaryOrderDto getPriamryWorkOderById(int id) {

		Optional<PrimaryOrder> po = primaryOrderRepository.findById(id);

		if (!po.isPresent()) {
			throw new UsernameNotFoundException("id not found");
		}

		PrimaryOrder primaryOrder = po.get();

		PrimaryOrderDto primarOrderDto = new PrimaryOrderDto();
		primarOrderDto.setId(primaryOrder.getId());
		primarOrderDto.setWorkOrderId(primaryOrder.getWorkOrderId());
		primarOrderDto.setWorkOrderDate(primaryOrder.getWorkOrderDate());
		primarOrderDto.setDateCreated(primaryOrder.getDateCreated());
		primarOrderDto.setRemarks(primaryOrder.getRemarks());
		primarOrderDto.setRetailerPinCode(primaryOrder.getRetailerPinCode());
		primarOrderDto.setSaleAgentId(primaryOrder.getSaleAgentId());
		primarOrderDto.setFrom_date(primaryOrder.getFrom_date());
		primarOrderDto.setTo_Date(primaryOrder.getTo_Date());
		primarOrderDto.setDistributor(primaryOrder.getDistributor());
		primarOrderDto.setDeliveryAddress(primaryOrder.getDeliveryAddress());
		primarOrderDto.setAseid(primaryOrder.getId());
		primarOrderDto.setAsmid(primaryOrder.getAsmid());
		primarOrderDto.setRsmid(primaryOrder.getRsmid());
		primarOrderDto.setNsmid(primaryOrder.getNsmid());
		primarOrderDto.setStateid(primaryOrder.getStateid());
		primarOrderDto.setZonesid(primaryOrder.getZonesid());
		primarOrderDto.setGrossTotal(primaryOrder.getGrossTotal());
		primarOrderDto.setNetValue(primaryOrder.getNetValue());
		primarOrderDto.setTaxAmount(primaryOrder.getTaxAmount());
		primarOrderDto.setDistributorAddress(primaryOrder.getDistributorAddress());
		primarOrderDto.setKgProductTotalQtyKg(primaryOrder.getKgProductTotalQtyKg());
		primarOrderDto.setKgProductTotalQtyPcs(primaryOrder.getKgProductTotalQtyPcs());
		primarOrderDto.setBoxProductTotalQtyPcs(primaryOrder.getBoxProductTotalQtyPcs());
		primarOrderDto.setCorporateProductTotalQtyPcs(primaryOrder.getCorporateProductTotalQtyPcs());
		primarOrderDto.setCookerProductTotalQtyPcs(primaryOrder.getCookerProductTotalQtyPcs());
		primarOrderDto.setNoshProductTotalQtyPcs(primaryOrder.getNoshProductTotalQtyPcs());
		primarOrderDto.setTotalQtyKg(primaryOrder.getTotalQtyKg());
		primarOrderDto.setTotalQtyPcs(primaryOrder.getTotalQtyPcs());
		primarOrderDto.setKgProductTotalprice(primaryOrder.getKgProductTotalprice());
		primarOrderDto.setBoxProductTotalprice(primaryOrder.getBoxProductTotalprice());
		primarOrderDto.setCorporateProductTotalprice(primaryOrder.getCorporateProductTotalprice());
		primarOrderDto.setCookerProductTotalprice(primaryOrder.getCookerProductTotalprice());
		primarOrderDto.setNoshProductTotalprice(primaryOrder.getNoshProductTotalprice());
		primarOrderDto.setPrimaryOrderItems(primaryOrder.getPrimaryOrderItems());
primarOrderDto.setEdd(primaryOrder.getEdd());
		return primarOrderDto;
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderByASE(int aseId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderByAseID(aseId);
		return mapToListDto(p);
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderByASM(int asmId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderByAsmID(asmId);
		return mapToListDto(p);
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderByRSM(int rsmId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderByRsmID(rsmId);
		return mapToListDto(p);
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderByNSM(int nsmId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderByNsmID(nsmId);
		return mapToListDto(p);
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderByZONE(int zonesId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderByZoneId(zonesId);
		return mapToListDto(p);
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderBySTATE(int stateId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderBystateId(stateId);
		return mapToListDto(p);
	}

	@Override
	public List<PrimaryOrderDto> getPrimaryOrderByDistributor(int distId) {
		List<PrimaryOrder> p = primaryOrderRepository.getPrimaryOrderByDistributorId(distId);
		return mapToListDto(p);
	}

	public List<PrimaryOrderDto> mapToListDto(List<PrimaryOrder> primaryOrders) {

		List<PrimaryOrderDto> po = new ArrayList<>();
		for (PrimaryOrder p : primaryOrders) {

			PrimaryOrderDto primaryOrderDto = new PrimaryOrderDto();
			primaryOrderDto.setId(p.getId());
			primaryOrderDto.setWorkOrderId(p.getWorkOrderId());
			primaryOrderDto.setWorkOrderDate(p.getWorkOrderDate());
			primaryOrderDto.setRemarks(p.getRemarks());
			primaryOrderDto.setFrom_date(p.getFrom_date());
			primaryOrderDto.setTo_Date(p.getTo_Date());
			primaryOrderDto.setDateCreated(p.getDateCreated());
			primaryOrderDto.setSaleAgentId(p.getSaleAgentId());
			primaryOrderDto.setRetailerPinCode(p.getRetailerPinCode());
			primaryOrderDto.setAseid(p.getAseid());
			primaryOrderDto.setAsmid(p.getAsmid());
			primaryOrderDto.setRsmid(p.getRsmid());
			primaryOrderDto.setNsmid(p.getNsmid());
			primaryOrderDto.setStateid(p.getStateid());
			primaryOrderDto.setZonesid(p.getZonesid());
			primaryOrderDto.setDistributorAddress(p.getDistributorAddress());
			primaryOrderDto.setDistributor(p.getDistributor());
			primaryOrderDto.setDeliveryAddress(p.getDeliveryAddress());
			primaryOrderDto.setGrossTotal(p.getGrossTotal());
			primaryOrderDto.setNetValue(p.getNetValue());
			primaryOrderDto.setTaxAmount(p.getTaxAmount());
			primaryOrderDto.setKgProductTotalQtyKg(p.getKgProductTotalQtyKg());
			primaryOrderDto.setKgProductTotalQtyPcs(p.getKgProductTotalQtyPcs());
			primaryOrderDto.setBoxProductTotalQtyPcs(p.getBoxProductTotalQtyPcs());
			primaryOrderDto.setCorporateProductTotalQtyPcs(p.getCorporateProductTotalQtyPcs());
			primaryOrderDto.setCookerProductTotalQtyPcs(p.getCookerProductTotalQtyPcs());
			primaryOrderDto.setNoshProductTotalQtyPcs(p.getNoshProductTotalQtyPcs());
			primaryOrderDto.setTotalQtyKg(p.getTotalQtyKg());
			primaryOrderDto.setTotalQtyPcs(p.getTotalQtyPcs());
			primaryOrderDto.setKgProductTotalprice(p.getKgProductTotalprice());
			primaryOrderDto.setBoxProductTotalprice(p.getBoxProductTotalprice());
			primaryOrderDto.setCorporateProductTotalprice(p.getCorporateProductTotalprice());
			primaryOrderDto.setCookerProductTotalprice(p.getCookerProductTotalprice());
			primaryOrderDto.setNoshProductTotalprice(p.getNoshProductTotalprice());
primaryOrderDto.setEdd(p.getEdd());
			primaryOrderDto.setPrimaryOrderItems(p.getPrimaryOrderItems());

			po.add(primaryOrderDto);
		}
		return po;
	}

	@Override
	public List<PrimaryOrder> getAllPo() {

		return primaryOrderRepository.findAll();
	}

	@Override
	public List<PrimaryOrder> getAllPo1() {

		return primaryOrderRepository.getAllPrimaryWorkOrder();
	}

	@Override
	public Map<String, Object> IndexPrimaryOrderAsc(int pageno, int pagesize, String field) {

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

				long countpages = primaryOrderRepository.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.indexPrimaryOrder(p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = primaryOrderRepository.findBydistId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByDistId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_ASE")) {

				long countpages = primaryOrderRepository.findByaseId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByAseId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_ASM")) {

				long countpages = primaryOrderRepository.findByasmId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByAsmId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_RSM")) {

				long countpages = primaryOrderRepository.findByrsmId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByRsmId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

		}

		return null;

	}

	@Override
	public Map<String, Object> IndexPrimaryOrderDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
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

				long countpages = primaryOrderRepository.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.indexPrimaryOrder(p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {

				long countpages = primaryOrderRepository.findBydistId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByDistId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_ASE")) {

				long countpages = primaryOrderRepository.findByaseId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByAseId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_ASM")) {

				long countpages = primaryOrderRepository.findByasmId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByAsmId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

			else if (s.equals("ROLE_RSM")) {

				long countpages = primaryOrderRepository.findByrsmId(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexPrimaryOrder> ipo = primaryOrderRepository.findByRsmId(uid, p);
				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			}

		}

		return null;
	}

	@Override
	public Map<String, Object> SearchPrimaryOrder(int pageno, int pagesize, String search) {

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
				List<IndexPrimaryOrder> primaryOrders = primaryOrderRepository.SearchByPrimaryOrder(search, p);

				long searchcount = primaryOrders.size();

				response.put("data", primaryOrders);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_DISTRIBUTOR")) {
				List<IndexPrimaryOrder> primaryOrders = primaryOrderRepository.SearchByDistId(uid, search, p);

				long searchcount = primaryOrders.size();

				response.put("data", primaryOrders);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_ASE")) {
				List<IndexPrimaryOrder> primaryOrders = primaryOrderRepository.SearchByAseId(uid, search, p);

				long searchcount = primaryOrders.size();

				response.put("data", primaryOrders);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_ASM")) {
				List<IndexPrimaryOrder> primaryOrders = primaryOrderRepository.SearchByAsmId(uid, search, p);

				long searchcount = primaryOrders.size();

				response.put("data", primaryOrders);
				response.put("SearchCount", searchcount);

				return response;
			}

			else if (s.equals("ROLE_RSM")) {
				List<IndexPrimaryOrder> primaryOrders = primaryOrderRepository.SearchByRsmId(uid, search, p);

				long searchcount = primaryOrders.size();

				response.put("data", primaryOrders);
				response.put("SearchCount", searchcount);

				return response;
			}
		}
		return null;
	}

	@Override
	public List<ExportPrimaryWorkOrder> exportPo() {

		return primaryOrderRepository.ExportPrimaryWorkOrder();
	}

	@Autowired
	private PrimaryOrderItemsRepository primaryOrderItemsRepository;

	@Override
	public Map<String, Object> IndexPrimaryOrderItemsAsc(String startdate, String enddate, int distid, int pageno,
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

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM") || s.equals("ROLE_DISTRIBUTOR")) {
		
		long countpages = primaryOrderItemsRepository.getPoitemsByDistributorId(startdate, enddate, distid).size();
		long pages = countpages / pagesize;
		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
				.getPoitemsByDistributorIdPagination(startdate, enddate, distid, p);

		response.put("Index", poitems);
		response.put("CountPages", countpages);
		response.put("Pages", pages);
		// TODO Auto-generated method stub
		return response;
			}
		
		
		if (s.equals("ROLE_RSM")) {
			
	long countpages = primaryOrderItemsRepository.count();
	long pages = countpages / pagesize;
	List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
			.getPoitemsByDistributorIdPaginationRsm(startdate, enddate, distid, p);

	response.put("Index", poitems);
	response.put("CountPages", countpages);
	response.put("Pages", pages);
	// TODO Auto-generated method stub
	return response;
		}
		
		if (s.equals("ROLE_ASM")) {
			
			long countpages = primaryOrderItemsRepository.count();
			long pages = countpages / pagesize;
			List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
					.getPoitemsByDistributorIdPaginationAsm(startdate, enddate, distid, p);

			response.put("Index", poitems);
			response.put("CountPages", countpages);
			response.put("Pages", pages);
			// TODO Auto-generated method stub
			return response;
				}
		
	if (s.equals("ROLE_ASE")) {
			
			long countpages = primaryOrderItemsRepository.count();
			long pages = countpages / pagesize;
			List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
					.getPoitemsByDistributorIdPaginationAse(startdate, enddate, distid, p);

			response.put("Index", poitems);
			response.put("CountPages", countpages);
			response.put("Pages", pages);
			// TODO Auto-generated method stub
			return response;
				}
		
		
		
		
	}
		
		
		
		return null;
	}
	
//	@Override
//	public Map<String, Object> IndexPrimaryOrderItemsAsc(String startdate, String enddate, int distid, int pagno,
//			int pagesize, String field) {
//
//		Map<String, Object> response = new HashMap<>();
//		Sort sort = Sort.by(Sort.Direction.ASC, field);
//		Pageable p = PageRequest.of(pagno, pagesize, sort);
//		long countpages = primaryOrderItemsRepository.count();
//		long pages = countpages / pagesize;
//		long rem = countpages % pagesize;
//		if (rem > 0) {
//			pages++;
//		}
//		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
//				.getPoitemsByDistributorIdPagination(startdate, enddate, distid, p);
//
//		response.put("Index", poitems);
//		response.put("CountPages", countpages);
//		response.put("Pages", pages);
//		// TODO Auto-generated method stub
//		return response;
//	}
	
	

	@Override
	public Map<String, Object> IndexPrimaryOrderItemsDesc(String startdate, String enddate, int distid, int pagno,
			int pagesize, String field) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pagno, pagesize, sort.unsorted());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM") || s.equals("ROLE_DISTRIBUTOR")) {
		
		long countpages = primaryOrderItemsRepository.getPoitemsByDistributorId(startdate, enddate, distid).size();
		long pages = countpages / pagesize;
		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
				.getPoitemsByDistributorIdPagination(startdate, enddate, distid, p);

		response.put("Index", poitems);
		response.put("CountPages", countpages);
		response.put("Pages", pages);
		// TODO Auto-generated method stub
		return response;
			}
		
		
		if (s.equals("ROLE_RSM")) {
			
	long countpages = primaryOrderItemsRepository.count();
	long pages = countpages / pagesize;
	List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
			.getPoitemsByDistributorIdPaginationRsm(startdate, enddate, distid, p);

	response.put("Index", poitems);
	response.put("CountPages", countpages);
	response.put("Pages", pages);
	// TODO Auto-generated method stub
	return response;
		}
		
		if (s.equals("ROLE_ASM")) {
			
			long countpages = primaryOrderItemsRepository.count();
			long pages = countpages / pagesize;
			List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
					.getPoitemsByDistributorIdPaginationAsm(startdate, enddate, distid, p);

			response.put("Index", poitems);
			response.put("CountPages", countpages);
			response.put("Pages", pages);
			// TODO Auto-generated method stub
			return response;
				}
		
	if (s.equals("ROLE_ASE")) {
			
			long countpages = primaryOrderItemsRepository.count();
			long pages = countpages / pagesize;
			List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
					.getPoitemsByDistributorIdPaginationAse(startdate, enddate, distid, p);

			response.put("Index", poitems);
			response.put("CountPages", countpages);
			response.put("Pages", pages);
			// TODO Auto-generated method stub
			return response;
				}
		}
		return null;
	}

	@Override
	public Map<String, Object> SearchPrimaryOrderItemsDesc(String startdate, String enddate, int distid, int pagno,
			int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pagno, pagesize);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();

		Long id = userDetail.getId();
		int uid = (int) (long) id;

		List<String> list = userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {

			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM") || s.equals("ROLE_DISTRIBUTOR")) {
		
		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
				.searchPoitemsByDistributorIdPagination(startdate, enddate, distid, search, p);
		long searchcount = poitems.size();
		response.put("data", poitems);
		response.put("SearchCount", searchcount);
		return response;
			}
			
       if (s.equals("ROLE_RSM")) {
		
		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
				.searchPoitemsByDistributorIdPaginationRsm(startdate, enddate, distid, search, p);
		long searchcount = poitems.size();
		response.put("data", poitems);
		response.put("SearchCount", searchcount);
		return response;
			}
       
       if (s.equals("ROLE_ASM")) {
   		
   		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
   				.searchPoitemsByDistributorIdPaginationAsm(startdate, enddate, distid, search, p);
   		long searchcount = poitems.size();
   		response.put("data", poitems);
   		response.put("SearchCount", searchcount);
   		return response;
   			}
       
       if (s.equals("ROLE_ASE")) {
      		
      		List<IndexPrimaryOrderByDistributorId> poitems = primaryOrderItemsRepository
      				.searchPoitemsByDistributorIdPaginationAse(startdate, enddate, distid, search, p);
      		long searchcount = poitems.size();
      		response.put("data", poitems);
      		response.put("SearchCount", searchcount);
      		return response;
      			}
		}
		return response;
	}

	public void saveNotification(Distributor distributor, String message, PrimaryOrder primaryOrder) {

		Notification notification = new Notification();

		notification.setStatus(message);
		notification.setCreatedAt(new Date());

		notification.setDist_id(distributor.getId());
		notification.setNsmid(distributor.getNsmid());
		notification.setRsmid(distributor.getRsmid());
		notification.setAsmid(distributor.getAsmid());
		notification.setAseid(distributor.getAseid());
		notification.setP_id(primaryOrder.getId());
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
