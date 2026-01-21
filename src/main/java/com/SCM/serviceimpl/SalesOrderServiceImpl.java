package com.SCM.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SCM.IndexDto.IndexRendingSalesOrderByDistributor;
import com.SCM.IndexDto.IndexRendingSalesOrderByRetailer;
import com.SCM.IndexDto.SalesOrderPageDtoProjection;
import com.SCM.dto.SalesOrderByRetailerAndDistibutorGroup;
import com.SCM.dto.SalesOrderCancelRequest;
import com.SCM.dto.SalesOrderDto;
import com.SCM.dto.SalesOrderItemsCancelDto;
import com.SCM.dtoReports.PendingSalesOrderByRetailerWithoutDistributor;
import com.SCM.dtoReports.SalesOrderReportIndex;
import com.SCM.model.ActivityLog;
import com.SCM.model.Distributor;
import com.SCM.model.Notification;
import com.SCM.model.PrimaryOrder;
import com.SCM.model.Product;
import com.SCM.model.PurchaseOrder;
import com.SCM.model.Retailer;
import com.SCM.model.SalesItems;
import com.SCM.model.SalesOrder;
import com.SCM.model.SalesOrderItems;
import com.SCM.model.VoucherMaster;
import com.SCM.model.WorkOrder;
import com.SCM.projectionDto.SalesOrderReportDto;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.NotificationRepository;
import com.SCM.repository.PrimaryOrderRepository;
import com.SCM.repository.ProductRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.SalesItemsRepo;
import com.SCM.repository.SalesOrderItemsRepo;
import com.SCM.repository.SalesOrderRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.repository.WorkOrderRepo;
import com.SCM.service.SalesOrderService;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {
	
	@Autowired
	private SalesOrderRepo salesOrderRepo;

	@Autowired
	private DistributorRepo distributorRepo;

	@Autowired
	private WorkOrderRepo workOrderRepo;

	@Autowired
	private SalesOrderItemsRepo salesOrderItemsRepo;

	@Autowired
	private PrimaryOrderRepository primaryOrderRepository;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private SalesItemsRepo salesItemsRepo;
	
	@Autowired
	private VoucherMasterRepo voucherMasterRepo;

	public long getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long uid = userDetails.getId();
		return uid.longValue();
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
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		String role = list.get(0);
		return role;
	}

	public SalesOrder saveSalesOrder(SalesOrderDto salesorderdto) {
		
		VoucherMaster voucher=voucherMasterRepo.findById(salesorderdto.getVoucherMaster().getId()).get();
		
		SalesOrder salesOrder = new SalesOrder();
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		salesOrder.setCreatebyname(uname);
		salesOrder.setCreatedby(uid.longValue());
		salesOrder.setRole(role);
		salesOrder.setBuyerorder(salesorderdto.getBuyerorder());
		salesOrder.setDestination(salesorderdto.getDestination());
		salesOrder.setDispatchedthrough(salesorderdto.getDispatchedthrough());
		salesOrder.setTermsofdelivery(salesorderdto.getTermsofdelivery());
		salesOrder.setVoucherno(salesorderdto.getVoucherno());
		salesOrder.setRemarks(salesorderdto.getRemarks());
		salesOrder.setSodate(salesorderdto.getSodate());
		salesOrder.setTaxtype(salesorderdto.getTaxtype());
		salesOrder.setQuotationstatus(salesorderdto.isQuotationstatus());
		salesOrder.setIgst(salesorderdto.getIgst());
		salesOrder.setCgst(salesorderdto.getCgst());
		salesOrder.setSgst(salesorderdto.getSgst());
		salesOrder.setPaymentTerms(salesorderdto.getPaymentTerms());
		salesOrder.setShippingcharge(salesorderdto.getShippingcharge());
		salesOrder.setGrossamount(salesorderdto.getGrossamount());
		salesOrder.setGrandtotal(salesorderdto.getGrandtotal());
		salesOrder.setDeliveryAddress(salesorderdto.getDeliveryAddress());
		salesOrder.setRoundingofvalue(salesorderdto.getRoundingofvalue());
		salesOrder.setCustomerSubContacts(salesorderdto.getCustomerSubContacts());
		salesOrder.setWarehouse(salesorderdto.getWarehouse());
		salesOrder.setBranch(salesorderdto.getBranch());
		salesOrder.setSalesOrderItems(salesorderdto.getSalesOrderItems());
		salesOrder.setStatus("pending");
		salesOrder.setCreateddate(LocalDate.now());
		salesOrder.setCreatedtime(LocalTime.now());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setRetailer(salesorderdto.getRetailer());
		salesOrder.setRetailerstatus(null);
		salesOrder.setBuyerorderdate(salesorderdto.getBuyerorderdate());
		salesOrder.setVoucherMaster(voucher);
		
		
		
		
		
//		if (this.salesOrderRepo.searchByVoucher(salesorderdto.getVoucherid()) == null) {
//			
//			salesOrder.setVoucherid(salesorderdto.getVoucherid());
//			salesOrder.setVouchernumber(1L);
//			String vseries = String.valueOf(salesorderdto.getVoucherid()) + '\001';
//			salesOrder.setVoucherseries(vseries);
//			
//		} else {
//			SalesOrder searchByVoucher = this.salesOrderRepo.searchByVoucher(salesorderdto.getVoucherid());
//			String voucherid = searchByVoucher.getVoucherid();
//			long vouchernumber = searchByVoucher.getVouchernumber();
//			long l = ++vouchernumber;
//			String newver = String.valueOf(voucherid) + vouchernumber;
//			salesOrder.setVoucherid(voucherid);
//			salesOrder.setVouchernumber(l);
//			salesOrder.setVoucherseries(newver);
//		}
		if (salesorderdto.getDistributor() == null)
		{
			if (salesorderdto.getRetailer() != null) {
				
				Retailer rt = this.retailerRepo.findById(Integer.valueOf(salesorderdto.getRetailer().getId())).get();
				salesOrder.setRetailer(salesorderdto.getRetailer());
				salesOrder.setDistributor(rt.getDistributor());
				salesOrder.setRetailerstatus("customer");
			}
		}
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			SalesOrder topByVoucherOrderByStartnumberwithprefilnoDesc = salesOrderRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SalesOrder topByVoucherOrderByStartnumberwithprefilyesDesc = salesOrderRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SalesOrder lastrowstatus = salesOrderRepo.lastrowstatus();
			SalesOrder lastrowstatus = salesOrderRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				salesOrder.setStartnumberwithprefilno(startingnumber);
				salesOrder.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					salesOrder.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					salesOrder.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					salesOrder.setStartnumberwithprefilno(restartnumber + 1);
					salesOrder.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					salesOrder.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					salesOrder.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					salesOrder.setStartnumberwithprefilyes(formattedStartingNumber);
					salesOrder.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					salesOrder.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					salesOrder.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					salesOrder.setStartnumberwithprefilyes(restartnumberinc);
					salesOrder.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					salesOrder.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					salesOrder.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			SalesOrder topByVoucherOrderByStartnumberwithprefilnoDesc = salesOrderRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			SalesOrder topByVoucherOrderByStartnumberwithprefilyesDesc = salesOrderRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			SalesOrder lastrowstatus = salesOrderRepo.lastrowstatus();
			SalesOrder lastrowstatus = salesOrderRepo.findTopByVoucherMasterOrderByIdDesc(voucher);
			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				salesOrder.setStartnumberwithprefilno(startingnumber);
				salesOrder.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				salesOrder.setStartnumberwithprefilyes(formattedStartingNumber);
				salesOrder.setVoucherstatus("startnostatus");
			}
		}

		SalesOrder save = salesOrderRepo.save(salesOrder);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		salesOrder.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		salesOrderRepo.save(salesOrder);
		
		
		
		
		
//		}
		SalesOrder so = (SalesOrder) this.salesOrderRepo.save(salesOrder);
//		

			
//List<Integer> integers=salesOrder.getSalesOrderItems().stream().map(m-> m.getProduct().getId()).collect(Collectors.toList());
//			
//	List<Product> products=productRepo.findAllById(integers);
//	
//
//	Map<Integer,Product> map=products.stream().collect(Collectors.toMap(Product::getId, p->p));
//	
//	List<SalesOrderItems> updatedItems = new ArrayList<>();
//	salesOrder.getSalesOrderItems().forEach(i -> {
//  Integer product2= i.getProduct().getId();
//  
//  Product product3=map.get(product2);
//  
//  String uomSecondary = product3.getUomSecondary();
//              float uomsecond = Float.parseFloat(uomSecondary);
//				int someasurement = i.getSomeasurement();
//				float result = someasurement * uomsecond;
//			
//
//				if (salesorderdto.getDistributor() != null) {
//				
//				i.setDist_pendingcancelpcsqty(someasurement);
//				i.setDist_pendingcancelkgqty(result);
//				i.setPendingcancelpcsqty(0);
//				i.setPendingcancelkgqty(0.0F);
//				
//			} else {
//				i.setPendingcancelpcsqty(someasurement);
//				i.setPendingcancelkgqty(result);
//				i.setDist_pendingcancelpcsqty(0);
//				i.setDist_pendingcancelkgqty(0.0F);
//			}
//				
//				
//			updatedItems.add(i);
//			
//		});
//	
//	if(salesorderdto.getRetailer() != null)
//	{
//		salesOrderRepo.save(salesOrder);
//	}
//	
//	
//	this.salesOrderItemsRepo.saveAll(updatedItems);
		
		
		
		


			
		List<Integer> productIds = salesOrder.getSalesOrderItems().stream()
		        .map(item -> item.getProduct().getId())
		        .filter(Objects::nonNull)
		        .collect(Collectors.toList());

		List<Product> products = productRepo.findAllById(productIds);
		Map<Integer, Product> productMap = products.stream()
		        .collect(Collectors.toMap(Product::getId, p -> p));

		List<SalesOrderItems> updatedItems = new ArrayList<>();

		salesOrder.getSalesOrderItems().forEach(i -> {
		    Integer productId = i.getProduct().getId();
		    Product product = productMap.get(productId);

		    if (product == null) {
		        System.out.println("Product not found for ID: " + productId);
		        return;
		    }

		    try {
		        float uomSecond = Float.parseFloat(product.getUomSecondary());
		        int someasurement = i.getSomeasurement();
		        float result = someasurement * uomSecond;

		        System.out.println("Processing: Product ID = " + productId +
		                ", Someasurement = " + someasurement + ", Result = " + result);

		        if (salesorderdto.getDistributor() != null && salesorderdto.getRetailer() == null) {
		            i.setDist_pendingcancelpcsqty(someasurement);
		            i.setDist_pendingcancelkgqty(result);
		            i.setPendingcancelpcsqty(0);
		            i.setPendingcancelkgqty(0.0F);
		        } else if(salesorderdto.getDistributor() == null && salesorderdto.getRetailer() != null){
		            i.setPendingcancelpcsqty(0);
		            i.setPendingcancelkgqty(0);
		            i.setDist_pendingcancelpcsqty(0);
		            i.setDist_pendingcancelkgqty(0.0F);
		        }
		    
		        updatedItems.add(i); // ✅ Prepare for batch save

		    } catch (NumberFormatException e) {
		        System.out.println("Invalid UOM Secondary for product ID: " + productId);
		    }
		});
	

		// ✅ Batch save at once
		salesOrderItemsRepo.saveAll(updatedItems);





		
		
		
	
		
		
		
		
		
//		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (salesOrder.getDistributor() != null) {
//			
//			if (salesOrder.getRetailer() != null) {
//				
//				Retailer retailer = this.retailerRepo.findById(Integer.valueOf(salesorderdto.getRetailer().getId()))
//						.get();
//				saveNotification(retailer, "Sales Order Created By " + userDetailsImpl.getUsername(), so);
//			}
//		} else {
//			
//		
//			
//			Distributor distributor = this.distributorRepo.findById(Integer.valueOf(salesorderdto.getDistributor().getId())).get();
//			saveNotification(distributor, "Sales Order Created By " + userDetailsImpl.getUsername(), so);
//		}
//		so.getSalesOrderItems().forEach(item -> {
//			
//			item.setCancelstatus("Not Cancelled");
//			item.setDcitemspending(0);
//			item.setDcitemsplaced(0);
//			item.setDcstatus("pending");
//		});
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (salesOrder.getDistributor() != null) {
		    
		    if (salesOrder.getRetailer() != null) {
		        Integer retailerId = salesorderdto.getRetailer() != null ? salesorderdto.getRetailer().getId() : null;

		        if (retailerId != null) {
		            Retailer retailer = this.retailerRepo.findById(retailerId)
		                    .orElse(null); // Safely fetch
		            if (retailer != null) {
		                saveNotification(retailer, "Sales Order Created By " + userDetailsImpl.getUsername(), so);
		            }
		        }
		    }

		} else {
		    Integer distributorId = (salesorderdto.getDistributor() != null)
		            ? salesorderdto.getDistributor().getId()
		            : null;

		    if (distributorId != null) {
		        Distributor distributor = this.distributorRepo.findById(distributorId)
		                .orElse(null);
		        if (distributor != null) {
		            saveNotification(distributor, "Sales Order Created By " + userDetailsImpl.getUsername(), so);
		        }
		    } else {
		        System.out.println(" Distributor ID is null. Skipping distributor notification.");
		    }
		}

		
		so = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSalesorderid(Long.valueOf(so.getId()));
		activityLog.setLoggeduser(loggeduser);
		this.activityLogRepo.save(activityLog);
		
		
		return save;
			}


	public SalesOrder convertPrimaryOrdertoSalesOrder(SalesOrderDto salesorderdto, int primaryorderId) {
		
		Optional<PrimaryOrder> primaryorder = this.primaryOrderRepository.findById(Integer.valueOf(primaryorderId));
		PrimaryOrder po = primaryorder.get();
		
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setBuyerorder(salesorderdto.getBuyerorder());
		salesOrder.setDestination(salesorderdto.getDestination());
		salesOrder.setDispatchedthrough(salesorderdto.getDispatchedthrough());
		salesOrder.setTermsofdelivery(salesorderdto.getTermsofdelivery());
		salesOrder.setVoucherno(salesorderdto.getVoucherno());
		salesOrder.setRemarks(salesorderdto.getRemarks());
		salesOrder.setSodate(salesorderdto.getSodate());
		salesOrder.setTaxtype(salesorderdto.getTaxtype());
		salesOrder.setIgst(salesorderdto.getIgst());
		salesOrder.setCgst(salesorderdto.getCgst());
		salesOrder.setSgst(salesorderdto.getSgst());
		salesOrder.setShippingcharge(salesorderdto.getShippingcharge());
		salesOrder.setGrossamount(salesorderdto.getGrossamount());
		salesOrder.setPaymentTerms(salesorderdto.getPaymentTerms());
		salesOrder.setGrandtotal(salesorderdto.getGrandtotal());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setRoundingofvalue(salesorderdto.getRoundingofvalue());
		salesOrder.setQuotationstatus(salesorderdto.isQuotationstatus());
		salesOrder.setDeliveryAddress(salesorderdto.getDeliveryAddress());
		salesOrder.setCustomerSubContacts(salesorderdto.getCustomerSubContacts());
		salesOrder.setWarehouse(salesorderdto.getWarehouse());
		salesOrder.setBranch(salesorderdto.getBranch());
		salesOrder.setSalesOrderItems(salesorderdto.getSalesOrderItems());
		salesOrder.setPrimaryorderId(po.getId());
		salesOrder.setCreateddate(LocalDate.now());
		salesOrder.setCreatedtime(LocalTime.now());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setCreatebyname(uname);
		salesOrder.setCreatedby(uid.longValue());
		salesOrder.setRole(role);
		if (this.salesOrderRepo.searchByVoucher(salesorderdto.getVoucherid()) == null) {
			salesOrder.setVoucherid(salesorderdto.getVoucherid());
			salesOrder.setVouchernumber(1L);
			String vseries = String.valueOf(salesorderdto.getVoucherid()) + '\001';
			salesOrder.setVoucherseries(vseries);
		} else {
			SalesOrder searchByVoucher = this.salesOrderRepo.searchByVoucher(salesorderdto.getVoucherid());
			String voucherid = searchByVoucher.getVoucherid();
			long vouchernumber = searchByVoucher.getVouchernumber();
			long l = ++vouchernumber;
			String newver = String.valueOf(voucherid) + vouchernumber;
			salesOrder.setVoucherid(voucherid);
			salesOrder.setVouchernumber(l);
			salesOrder.setVoucherseries(newver);
		}
		if (salesorderdto.getDistributor() == null && salesorderdto.getRetailer() != null) {
			Retailer rt = this.retailerRepo.findById(Integer.valueOf(salesorderdto.getRetailer().getId())).get();
			salesOrder.setRetailer(salesorderdto.getRetailer());
			salesOrder.setDistributor(rt.getDistributor());
			salesOrder.setRetailerstatus(rt.getRetailerstatus());
		}
		SalesOrder so = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		PrimaryOrder order = this.primaryOrderRepository.findById(Integer.valueOf(so.getPrimaryorderId())).get();
		order.setStatus("converted");
		this.primaryOrderRepository.save(order);
		so.getSalesOrderItems().forEach(item -> {
			item.setDcitemspending(0);
			item.setDcitemsplaced(0);
			item.setDcstatus("pending");
			System.out.println();
		});
		so = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSalesorderid(Long.valueOf(so.getId()));
		activityLog.setLoggeduser(loggeduser);
		this.activityLogRepo.save(activityLog);
		return so;
	}

	public SalesOrder convertWorkordertoSalesorder(SalesOrderDto salesorderdto, int workorderId) {
		
		Optional<WorkOrder> wo = this.workOrderRepo.findById(Integer.valueOf(workorderId));
		WorkOrder workOrder = wo.get();
		
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setBuyerorder(salesorderdto.getBuyerorder());
		salesOrder.setDestination(salesorderdto.getDestination());
		salesOrder.setDispatchedthrough(salesorderdto.getDispatchedthrough());
		salesOrder.setTermsofdelivery(salesorderdto.getTermsofdelivery());
		salesOrder.setVoucherno(salesorderdto.getVoucherno());
		salesOrder.setRemarks(salesorderdto.getRemarks());
		salesOrder.setSodate(salesorderdto.getSodate());
		salesOrder.setTaxtype(salesorderdto.getTaxtype());
		salesOrder.setIgst(salesorderdto.getIgst());
		salesOrder.setCgst(salesorderdto.getCgst());
		salesOrder.setSgst(salesorderdto.getSgst());
		salesOrder.setShippingcharge(salesorderdto.getShippingcharge());
		salesOrder.setGrossamount(salesorderdto.getGrossamount());
		salesOrder.setPaymentTerms(salesorderdto.getPaymentTerms());
		salesOrder.setGrandtotal(salesorderdto.getGrandtotal());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setRoundingofvalue(salesorderdto.getRoundingofvalue());
		salesOrder.setQuotationstatus(salesorderdto.isQuotationstatus());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setDeliveryAddress(salesorderdto.getDeliveryAddress());
		salesOrder.setCustomerSubContacts(salesorderdto.getCustomerSubContacts());
		salesOrder.setWarehouse(salesorderdto.getWarehouse());
		salesOrder.setStatus("pending");
		salesOrder.setBranch(salesorderdto.getBranch());
		salesOrder.setSalesOrderItems(salesorderdto.getSalesOrderItems());
		salesOrder.setWorkorderId(workOrder.getId());
		salesOrder.setCreatebyname(uname);
		salesOrder.setCreatedby(uid.longValue());
		salesOrder.setRole(role);
		salesOrder.setCreateddate(LocalDate.now());
		salesOrder.setCreatedtime(LocalTime.now());
		
		if (this.salesOrderRepo.searchByVoucher(salesorderdto.getVoucherid()) == null) {
			salesOrder.setVoucherid(salesorderdto.getVoucherid());
			salesOrder.setVouchernumber(1L);
			String vseries = String.valueOf(salesorderdto.getVoucherid()) + '\001';
			salesOrder.setVoucherseries(vseries);
		} else {
			SalesOrder searchByVoucher = this.salesOrderRepo.searchByVoucher(salesorderdto.getVoucherid());
			String voucherid = searchByVoucher.getVoucherid();
			long vouchernumber = searchByVoucher.getVouchernumber();
			long l = ++vouchernumber;
			String newver = String.valueOf(voucherid) + vouchernumber;
			salesOrder.setVoucherid(voucherid);
			salesOrder.setVouchernumber(l);
			salesOrder.setVoucherseries(newver);
		}
		if (salesorderdto.getDistributor() == null)
			if (salesorderdto.getRetailer() != null) {
				Optional<Retailer> r = this.retailerRepo.findById(Integer.valueOf(salesorderdto.getRetailer().getId()));
				Retailer rt = r.get();
				salesOrder.setRetailer(salesorderdto.getRetailer());
				salesOrder.setDistributor(rt.getDistributor());
				salesOrder.setRetailerstatus(rt.getRetailerstatus());
			}
		SalesOrder so = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		WorkOrder order = this.workOrderRepo.findById(Integer.valueOf(so.getWorkorderId())).get();
		order.setStatus("converted");
		this.workOrderRepo.save(order);
		so.getSalesOrderItems().forEach(item -> {
			item.setDcitemspending(0);
			item.setDcitemsplaced(0);
			item.setDcstatus("pending");
			System.out.println();
		});
		so = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		return so;
	}

	public List<SalesOrder> getAllSalesOrder() {
		return this.salesOrderRepo.findAll();
	}

	public SalesOrder getSalesOrderById(int id) {
		SalesOrder salesorder = this.salesOrderRepo.findById(Integer.valueOf(id)).get();
		return salesorder;
	}

	public void deleteSalesOrder(int id) {
		this.salesOrderRepo.deleteById(Integer.valueOf(id));
	}

	public SalesOrderDto updateSalesOrder(SalesOrderDto salesorderdto, int id) {
		SalesOrder salesOrder = this.salesOrderRepo.findById(Integer.valueOf(id)).get();
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		salesOrder.setUpdatedbyname(uname);
		salesOrder.setUpdatedby(uid.longValue());
		salesOrder.setUpdatedrole(role);
		salesOrder.setUpdateddate(LocalDate.now());
		salesOrder.setUpdatedtime(LocalTime.now());
		salesOrder.setBuyerorder(salesorderdto.getBuyerorder());
		salesOrder.setDestination(salesorderdto.getDestination());
		salesOrder.setDispatchedthrough(salesorderdto.getDispatchedthrough());
		salesOrder.setTermsofdelivery(salesorderdto.getTermsofdelivery());
		salesOrder.setVoucherno(salesorderdto.getVoucherno());
		salesOrder.setRemarks(salesorderdto.getRemarks());
		salesOrder.setSodate(salesorderdto.getSodate());
		salesOrder.setTaxtype(salesorderdto.getTaxtype());
		salesOrder.setQuotationstatus(salesorderdto.isQuotationstatus());
		salesOrder.setIgst(salesorderdto.getIgst());
		salesOrder.setCgst(salesorderdto.getCgst());
		salesOrder.setPaymentTerms(salesorderdto.getPaymentTerms());
		salesOrder.setSgst(salesorderdto.getSgst());
		salesOrder.setShippingcharge(salesorderdto.getShippingcharge());
		salesOrder.setGrossamount(salesorderdto.getGrossamount());
		salesOrder.setGrandtotal(salesorderdto.getGrandtotal());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setRoundingofvalue(salesorderdto.getRoundingofvalue());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setRetailer(salesorderdto.getRetailer());
		salesOrder.setDeliveryAddress(salesorderdto.getDeliveryAddress());
		salesOrder.setCustomerSubContacts(salesorderdto.getCustomerSubContacts());
		salesOrder.setWarehouse(salesorderdto.getWarehouse());
		salesOrder.setBranch(salesorderdto.getBranch());
		salesOrder.setSalesOrderItems(salesorderdto.getSalesOrderItems());
		salesOrder.getSalesOrderItems().forEach(i -> {
			Product product = this.productRepo.findById(Integer.valueOf(i.getProduct().getId())).get();
			int someasurement = i.getSomeasurement();
			String uomSecondary = product.getUomSecondary();
			float uomsecond = Float.parseFloat(uomSecondary);
			float result = someasurement * uomsecond;
			if (salesorderdto.getDistributor() != null && salesorderdto.getRetailer() == null) {
				i.setDist_pendingcancelpcsqty(someasurement);
				i.setDist_pendingcancelkgqty(result);
				i.setPendingcancelpcsqty(0);
				i.setPendingcancelkgqty(0.0F);
			} else {
				i.setPendingcancelpcsqty(someasurement);
				i.setPendingcancelkgqty(result);
				i.setDist_pendingcancelpcsqty(0);
				i.setDist_pendingcancelkgqty(0.0F);
			}
			this.salesOrderItemsRepo.save(i);
		});
		if (salesorderdto.getDistributor() == null) {
			Retailer r = this.retailerRepo.findById(Integer.valueOf(salesorderdto.getRetailer().getId())).get();
			salesOrder.setDistributor(r.getDistributor());
		}
		SalesOrder savedso = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		SalesOrderDto salesOrderDto2 = new SalesOrderDto();
		salesOrderDto2.setBuyerorder(savedso.getBuyerorder());
		salesOrderDto2.setDestination(savedso.getDestination());
		salesOrderDto2.setDispatchedthrough(savedso.getDispatchedthrough());
		salesOrderDto2.setTermsofdelivery(savedso.getTermsofdelivery());
		salesOrderDto2.setVoucherno(savedso.getVoucherno());
		salesOrderDto2.setRemarks(savedso.getRemarks());
		salesOrderDto2.setSodate(savedso.getSodate());
		salesOrderDto2.setTaxtype(savedso.getTaxtype());
		salesOrderDto2.setIgst(savedso.getIgst());
		salesOrderDto2.setCgst(savedso.getCgst());
		salesOrderDto2.setSgst(savedso.getSgst());
		salesOrderDto2.setShippingcharge(savedso.getShippingcharge());
		salesOrderDto2.setGrossamount(savedso.getGrossamount());
		salesOrderDto2.setGrandtotal(savedso.getGrandtotal());
		salesOrderDto2.setPaymentTerms(savedso.getPaymentTerms());
		salesOrderDto2.setDistributor(savedso.getDistributor());
		salesOrderDto2.setRoundingofvalue(savedso.getRoundingofvalue());
		salesOrderDto2.setQuotationstatus(savedso.isQuotationstatus());
		salesOrderDto2.setDistributor(savedso.getDistributor());
		salesOrderDto2.setRetailer(savedso.getRetailer());
		salesOrderDto2.setDeliveryAddress(savedso.getDeliveryAddress());
		salesOrderDto2.setCustomerSubContacts(savedso.getCustomerSubContacts());
		salesOrderDto2.setBranch(savedso.getBranch());
		salesOrderDto2.setWarehouse(savedso.getWarehouse());
		salesOrderDto2.setSalesOrderItems(savedso.getSalesOrderItems());
		savedso.getSalesOrderItems().forEach(i -> {
			int someasurement = i.getSomeasurement();
			String uomSecondary = i.getProduct().getUomSecondary();
			float uomsecond = Float.parseFloat(uomSecondary);
			float result = someasurement * uomsecond;
			System.out.println(someasurement);
			System.out.println(uomsecond);
			System.out.println(result);
			i.setPendingcancelpcsqty(someasurement);
			i.setPendingcancelkgqty(result);
			this.salesOrderItemsRepo.save(i);
		});
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setSalesorderid(Long.valueOf(savedso.getId()));
		activityLog.setLoggeduser(loggeduser);
		this.activityLogRepo.save(activityLog);
		return salesOrderDto2;
	}

	public List<SalesOrderItems> fetchsalesitemsbydistandproduct(int distid) {
		return this.salesOrderItemsRepo.fetchsalesOrderbyDistributorid(distid);
	}

	public List<SalesOrderItems> fetchsalesitemsbyretandproduct(int retid) {
		return this.salesOrderItemsRepo.fetchsalesOrderbyRetailerid(retid);
	}

	public List<SalesOrderItems> getSalesorderitemsoitemsID(int soitemsid) {
		return this.salesOrderItemsRepo.getSalesOrderItemsBySoitemsId(soitemsid);
	}

	public List<SalesOrderReportIndex> fetchSalesOrderByDistId(long distid, String startdate, String enddate) {
		List<SalesOrderReportIndex> sales = this.salesOrderRepo.fetchSalesOrderByDistributorId(distid, startdate,
				enddate);
		return sales;
	}

	public List<SalesOrderReportIndex> fetchSalesOrderByRetailerId(long retid, String startdate, String enddate) {
		return null;
	}

	public Map<String, Object> indexSalesOrderAsc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long id = userDetails.getId().longValue();
		int uid = (int) id;
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				long countpages = this.salesOrderRepo.count();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrder((Pageable) pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = this.salesOrderRepo.indexSalesOrder(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrder(uid,
						(Pageable) pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				long countpages = this.salesOrderRepo.indexSalesOrderRsm(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrderRsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				long countpages = this.salesOrderRepo.indexSalesOrderAsm(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrderAsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				long countpages = this.salesOrderRepo.indexSalesOrderAse(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrderAse(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
		}
		return null;
	}

	public Map<String, Object> indexSalesOrderDesc(int pageno, int pagesize, String field) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long id = userDetails.getId().longValue();
		int uid = (int) id;
		List<String> list = (List<String>) userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				long countpages = this.salesOrderRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrder((Pageable) pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = this.salesOrderRepo.indexSalesOrder(uid).size();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0L)
					pages++;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrder(uid,
						(Pageable) pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_RSM")) {
				long countpages = this.salesOrderRepo.indexSalesOrderRsm(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrderRsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				long countpages = this.salesOrderRepo.indexSalesOrderAsm(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrderAsm(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				long countpages = this.salesOrderRepo.indexSalesOrderAse(uid).size();
				long pages = countpages / pagesize;
				List<SalesOrderPageDtoProjection> ipo = this.salesOrderRepo.indexSalesOrderAse(uid,pageRequest);
				response.put("Index", ipo);
				response.put("Enteries", Long.valueOf(countpages));
				response.put("Pages", Long.valueOf(pages));
				return response;
			}
		}
		return null;
	}

	public Map<String, Object> searchBySalesOrder(int pageno, int pagesize, String search) {
		Map<String, Object> response = new HashMap<>();
		PageRequest pageRequest = PageRequest.of(pageno, pagesize);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
		Long id = userDetail.getId();
		int uid = (int) id.longValue();
		List<String> list = (List<String>) userDetail.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		for (String s : list) {
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				List<SalesOrderPageDtoProjection> receipt = this.salesOrderRepo.searchBySalesOrder(search,
						(Pageable) pageRequest);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			if (s.equals("ROLE_DISTRIBUTOR")) {
				List<SalesOrderPageDtoProjection> receipt = this.salesOrderRepo.searchBySalesOrder(uid, search,
						(Pageable) pageRequest);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			if (s.equals("ROLE_RSM")) {
				List<SalesOrderPageDtoProjection> receipt = this.salesOrderRepo.searchBySalesOrderRsm(uid, search,
						(Pageable) pageRequest);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_ASM")) {
				List<SalesOrderPageDtoProjection> receipt = this.salesOrderRepo.searchBySalesOrderAsm(uid, search,
						(Pageable) pageRequest);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
			
			if (s.equals("ROLE_ASE")) {
				List<SalesOrderPageDtoProjection> receipt = this.salesOrderRepo.searchBySalesOrderAse(uid, search,
						(Pageable) pageRequest);
				int size = receipt.size();
				response.put("data", receipt);
				response.put("SearchCount", Integer.valueOf(size));
				return response;
			}
		}
		return null;
	}

	public void saveNotification(Distributor distributor, String message, SalesOrder salesOrder) {
		Notification notification = new Notification();
		notification.setStatus(message);
		notification.setCreatedAt(new Date());
		notification.setDist_id(distributor.getId());
		notification.setNsmid(distributor.getNsmid());
		notification.setRsmid(distributor.getRsmid());
		notification.setAsmid(distributor.getAsmid());
		notification.setAseid(distributor.getAseid());
		notification.setSo_id(salesOrder.getId());
		notification.setDistributor_read("unread");
		notification.setRetailer_read("unread");
		notification.setNsm_read("unread");
		notification.setRsm_read("unread");
		notification.setAsm_read("unread");
		notification.setAse_read("unread");
		notification.setAdmin_read("unread");
		this.notificationRepository.save(notification);
	}

	public void saveNotification(Retailer retailer, String message, SalesOrder salesOrder) {
		Notification notification = new Notification();
		notification.setStatus(message);
		notification.setCreatedAt(new Date());
		notification.setRet_id(retailer.getId());
		notification.setNsmid(retailer.getNsmid());
		notification.setRsmid(retailer.getRsmid());
		notification.setAsmid(retailer.getAsmid());
		notification.setAseid(retailer.getAseid());
		notification.setSo_id(salesOrder.getId());
		notification.setDistributor_read("unread");
		notification.setRetailer_read("unread");
		notification.setNsm_read("unread");
		notification.setRsm_read("unread");
		notification.setAsm_read("unread");
		notification.setAse_read("unread");
		notification.setAdmin_read("unread");
		this.notificationRepository.save(notification);
	}

	public List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailerwithoutdistributor(int retid,
			String fromdate, String todate) {
		List<PendingSalesOrderByRetailerWithoutDistributor> pobysobyretwithoutdistributor = this.salesOrderRepo
				.pendingsobyretailerwithoutdistributor(retid, fromdate, todate);
		return pobysobyretwithoutdistributor;
	}

	public List<PendingSalesOrderByRetailerWithoutDistributor> pendingsobyretailercustomer(int retid, String fromdate,
			String todate) {
		List<PendingSalesOrderByRetailerWithoutDistributor> pobysobyretcustomer = this.salesOrderRepo
				.pendingsobyretailercustomer(retid, fromdate, todate);
		return pobysobyretcustomer;
	}

	public String CancelItems(SalesOrderCancelRequest salesOrderCancelRequest, int soitemid) {
		List<SalesOrderItemsCancelDto> soitems = salesOrderCancelRequest.getSoitems();
		SalesOrderItems soi = this.salesOrderItemsRepo.findById(Integer.valueOf(soitemid)).get();
		soitems.forEach(c -> {
			if (soi.getSalesorderitemId() == c.getId()) {
				soi.setCancelpcsqty(c.getCancelpcsqty());
				soi.setCancelkgqty(c.getCancelkgqty());
				soi.setAddcancelpcsqty(c.getAddcancelpcsqty());
				soi.setAddcancelkgqty(c.getAddcancelkgqty());
				soi.setPendingcancelpcsqty(c.getPendingcancelpcsqty());
				soi.setPendingcancelkgqty(c.getPendingcancelkgqty());
				soi.setCancelstatus(c.getCancelstatus());
				if (soi.getPendingcancelpcsqty() == 0)
					soi.setCancelstatus("Cancelled");
			}
		});
		this.salesOrderItemsRepo.save(soi);
		return "update";
	}

	public String CancelItemsbydistributor(SalesOrderCancelRequest salesOrderCancelRequest, int soitemid) {
		List<SalesOrderItemsCancelDto> soitems = salesOrderCancelRequest.getSoitems();
		SalesOrderItems soi = this.salesOrderItemsRepo.findById(Integer.valueOf(soitemid)).get();
		soitems.forEach(c -> {
			if (soi.getSalesorderitemId() == c.getId()) {
				soi.setDist_cancelpcsqty(c.getDist_cancelpcsqty());
				soi.setDist_cancelkgqty(c.getDist_cancelkgqty());
				soi.setDist_addcancelpcsqty(c.getDist_addcancelpcsqty());
				soi.setDist_addcancelkgqty(c.getDist_addcancelkgqty());
				soi.setDist_pendingcancelpcsqty(c.getDist_pendingcancelpcsqty());
				soi.setDist_pendingcancelkgqty(c.getDist_pendingcancelkgqty());
				soi.setCancelstatus(c.getCancelstatus());
				if (soi.getDist_pendingcancelpcsqty() == 0)
					soi.setCancelstatus("Cancelled");
			}
		});
		this.salesOrderItemsRepo.save(soi);
		return "update";
	}

	public List<IndexRendingSalesOrderByRetailer> Pendingsalesorderbyretailerid(int retid, String startdate,
			String enddate) {
		List<IndexRendingSalesOrderByRetailer> pendingSalesOrderByRetailerReport = this.salesOrderRepo
				.PendingSalesOrderByRetailerReport(retid, startdate, enddate);
		for (IndexRendingSalesOrderByRetailer report : pendingSalesOrderByRetailerReport) {
			int getsalesorderitemid = report.getsalesorderitem_id().intValue();
			Integer getpendingcancelpcsqty = report.getpendingcancelpcsqty();
			Float getpendingcancelkgqty = report.getpendingcancelkgqty();
			Integer getsalesitemid = report.getsalesitemid();
			SalesOrderItems salesOrderItems = this.salesOrderItemsRepo.findById(Integer.valueOf(getsalesorderitemid))
					.get();
			SalesOrderItems soi = salesOrderItems;
			salesOrderItems.setPendingcancelpcsqty(getpendingcancelpcsqty.intValue());
			salesOrderItems.setPendingcancelkgqty(getpendingcancelkgqty.floatValue());
			this.salesOrderItemsRepo.save(soi);
			Optional<SalesItems> salesItems = this.salesItemsRepo.findById(getsalesitemid);

			if (salesItems.isPresent()) {

				SalesItems ssi = salesItems.get();
				SalesItems ssii = ssi;
				ssii.setCancelsalestatus("alreadysale");
				this.salesItemsRepo.save(ssii);
			}
		}
		return pendingSalesOrderByRetailerReport;
	}

	public List<IndexRendingSalesOrderByDistributor> Pendingsalesorderbydistributorid(int distid, String startdate,
			String enddate) {
		List<IndexRendingSalesOrderByDistributor> pendingSalesOrderByDistributorReport = this.salesOrderRepo
				.PendingSalesOrderByDistributorReport(distid, startdate, enddate);
		for (IndexRendingSalesOrderByDistributor report : pendingSalesOrderByDistributorReport) {
			int getsalesorderitemid = report.getsalesorderitem_id().intValue();
			Integer getpendingcancelpcsqty = report.getdist_pendingcancelpcsqty();
			System.out.println(getpendingcancelpcsqty);
			Float getpendingcancelkgqty = report.getdist_pendingcancelkgqty();
			System.out.println(getpendingcancelkgqty);
			Integer getsalesitemid = report.getsalesitemid();
			SalesOrderItems salesOrderItems = this.salesOrderItemsRepo.findById(Integer.valueOf(getsalesorderitemid))
					.get();
			SalesOrderItems soi = salesOrderItems;
			salesOrderItems.setDist_pendingcancelpcsqty(getpendingcancelpcsqty.intValue());
			salesOrderItems.setDist_pendingcancelkgqty(getpendingcancelkgqty.floatValue());
			this.salesOrderItemsRepo.save(soi);
			Optional<SalesItems> salesItems = this.salesItemsRepo.findById(getsalesitemid);
			if (salesItems.isPresent()) {
				SalesItems ssi = salesItems.get();
				SalesItems ssii = ssi;
				ssii.setCancelsalestatus("alreadysale");
				this.salesItemsRepo.save(ssii);
			}
		}
		return pendingSalesOrderByDistributorReport;
	}

	public List<IndexRendingSalesOrderByRetailer> getSalesOrderItemsById(int soitemid) {
		List<IndexRendingSalesOrderByRetailer> pendingSalesOrderID = this.salesOrderRepo.PendingSalesOrderID(soitemid);
		return pendingSalesOrderID;
	}

	public List<IndexRendingSalesOrderByDistributor> getSalesOrderItemsById1(int soitemid) {
		List<IndexRendingSalesOrderByDistributor> pendingSalesOrderID = this.salesOrderRepo
				.PendingSalesOrderID1(soitemid);
		return pendingSalesOrderID;
	}

	public Map<String, Object> indexSalesOrderByRetailerGroupAsc(int pageno, int pagesize, String field, Long retid) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerGroup((Pageable) pageRequest, retid.longValue())
				.size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerGroup((Pageable) pageRequest, retid.longValue());
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> indexSalesOrderByRetailerGroupDesc(int pageno, int pagesize, String field, Long retid) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerGroup((Pageable) pageRequest, retid.longValue())
				.size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerGroup((Pageable) pageRequest, retid.longValue());
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> searchBySalesOrderByRetailerGroup(int pageno, int pagesize, String search, Long retid) {
		return null;
	}

	public Map<String, Object> indexSalesOrderByDistributorGroupAsc(int pageno, int pagesize, String field,
			Long retid) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo
				.findSalesOrderByDistributorGroup((Pageable) pageRequest, retid.longValue()).size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByDistributorGroup((Pageable) pageRequest, retid.longValue());
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> indexSalesOrderByDistributorGroupDesc(int pageno, int pagesize, String field,
			Long retid) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo
				.findSalesOrderByDistributorGroup((Pageable) pageRequest, retid.longValue()).size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByDistributorGroup((Pageable) pageRequest, retid.longValue());
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> searchBySalesOrderByDistributorGroup(int pageno, int pagesize, String search,
			Long retid) {
		return null;
	}

	public Map<String, Object> indexSalesOrderByRetailerAndDistributorGroupAsc(int pageno, int pagesize, String field,
			Long retid, Long distid) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerAndDistributorGroup((Pageable) pageRequest,
				retid.longValue(), distid.longValue()).size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerAndDistributorGroup((Pageable) pageRequest, retid.longValue(),
						distid.longValue());
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> indexSalesOrderByRetailerAndDistributorGroupDesc(int pageno, int pagesize, String field,
			Long retid, Long distid) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerAndDistributorGroup((Pageable) pageRequest,
				retid.longValue(), distid.longValue()).size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerAndDistributorGroup((Pageable) pageRequest, retid.longValue(),
						distid.longValue());
		response.put("Index", ipo);
		response.put("Enteries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> indexSalesOrderByRetailerlistAndDistributorlistGroupAsc(int pageno, int pagesize,
			String field, List<Long> retids, List<Long> distids) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerlistAndDistributorlistGroup(retids, distids)
				.size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerlistAndDistributorlistGroup((Pageable) pageRequest, retids, distids);
		response.put("Index", ipo);
		response.put("Entries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> indexSalesOrderByRetailerlistAndDistributorlistGroupDesc(int pageno, int pagesize,
			String field, List<Long> retids, List<Long> distids) {
		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] { field });
		PageRequest pageRequest = PageRequest.of(pageno, pagesize, sort);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerlistAndDistributorlistGroup(retids, distids)
				.size();
		long pages = countpages / pagesize;
		long rem = countpages % pagesize;
		if (rem > 0L)
			pages++;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerlistAndDistributorlistGroup((Pageable) pageRequest, retids, distids);
		response.put("Index", ipo);
		response.put("Entries", Long.valueOf(countpages));
		response.put("Pages", Long.valueOf(pages));
		return response;
	}

	public Map<String, Object> searchSalesOrderByRetailerlistAndDistributorlistGroupDesc(int pageno, int pagesize,
			String field, List<Long> retids, List<Long> distids, String search) {
		Map<String, Object> response = new HashMap<>();
		PageRequest pageRequest = PageRequest.of(pageno, pagesize);
		long countpages = this.salesOrderRepo.findSalesOrderByRetailerlistAndDistributorlistGroupForSearch(
				(Pageable) pageRequest, retids, distids, search).size();
		long pages = countpages / pagesize;
		List<SalesOrderByRetailerAndDistibutorGroup> ipo = this.salesOrderRepo
				.findSalesOrderByRetailerlistAndDistributorlistGroupForSearch((Pageable) pageRequest, retids, distids,
						search);
		long searchcount = ipo.size();
		response.put("data", ipo);
		response.put("SearchCount", Long.valueOf(searchcount));
		return response;
	}

	public SalesOrder updateSalesOrder1(SalesOrderDto salesorderdto, int id) {
		SalesOrder salesOrder = this.salesOrderRepo.findById(Integer.valueOf(id)).get();
		Long uid = Long.valueOf(getUserId());
		String uname = getUserName();
		String role = getRolename();
		salesOrder.setUpdatedbyname(uname);
		salesOrder.setUpdatedby(uid.longValue());
		salesOrder.setUpdatedrole(role);
		salesOrder.setUpdateddate(LocalDate.now());
		salesOrder.setUpdatedtime(LocalTime.now());
		salesOrder.setBuyerorder(salesorderdto.getBuyerorder());
		salesOrder.setDestination(salesorderdto.getDestination());
		salesOrder.setDispatchedthrough(salesorderdto.getDispatchedthrough());
		salesOrder.setTermsofdelivery(salesorderdto.getTermsofdelivery());
		salesOrder.setVoucherno(salesorderdto.getVoucherno());
		salesOrder.setRemarks(salesorderdto.getRemarks());
		salesOrder.setSodate(salesorderdto.getSodate());
		salesOrder.setTaxtype(salesorderdto.getTaxtype());
		salesOrder.setQuotationstatus(salesorderdto.isQuotationstatus());
		salesOrder.setIgst(salesorderdto.getIgst());
		salesOrder.setCgst(salesorderdto.getCgst());
		salesOrder.setPaymentTerms(salesorderdto.getPaymentTerms());
		salesOrder.setSgst(salesorderdto.getSgst());
		salesOrder.setShippingcharge(salesorderdto.getShippingcharge());
		salesOrder.setGrossamount(salesorderdto.getGrossamount());
		salesOrder.setGrandtotal(salesorderdto.getGrandtotal());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setRoundingofvalue(salesorderdto.getRoundingofvalue());
		salesOrder.setDeliveryAddress(salesorderdto.getDeliveryAddress());
		salesOrder.setCustomerSubContacts(salesorderdto.getCustomerSubContacts());
		salesOrder.setWarehouse(salesorderdto.getWarehouse());
		salesOrder.setBranch(salesorderdto.getBranch());
		salesOrder.setRetailer(salesorderdto.getRetailer());
		salesOrder.setDistributor(salesorderdto.getDistributor());
		salesOrder.setSalesOrderItems(salesorderdto.getSalesOrderItems());
		salesOrder.getSalesOrderItems().forEach(i -> {
			Product product = this.productRepo.findById(Integer.valueOf(i.getProduct().getId())).get();
			int someasurement = i.getSomeasurement();
			String uomSecondary = product.getUomSecondary();
			float uomsecond = Float.parseFloat(uomSecondary);
			float result = someasurement * uomsecond;
			i.setPendingcancelpcsqty(someasurement);
			i.setPendingcancelkgqty(result);
		});
		SalesOrder savedso = (SalesOrder) this.salesOrderRepo.save(salesOrder);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setSalesorderid(Long.valueOf(savedso.getId()));
		activityLog.setLoggeduser(loggeduser);
		this.activityLogRepo.save(activityLog);
		return savedso;
	}

	public List<SalesOrderReportDto> salesOrderByRetailerListAndDistList(List<Long> retids, List<Long> distids,
			String fromdate, String todate) {
		List<SalesOrderReportDto> findAllSalesOrderByDistListAndRetList = this.salesOrderRepo
				.findAllSalesOrderByDistListAndRetList(retids, distids, fromdate, todate);
		return findAllSalesOrderByDistListAndRetList;
	}
}
