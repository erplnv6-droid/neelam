package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.GstDto.BuyerDtls;
import com.SCM.GstDto.DocDtls;
import com.SCM.GstDto.EwbDtls;
import com.SCM.GstDto.SellerDtls;
import com.SCM.GstDto.TranDtls;
import com.SCM.GstDto.ValDtls;
import com.SCM.GstModel.EinvoiceData;
import com.SCM.GstRepository.EinvoiceRepository;
import com.SCM.dto.NewVoucherName;
import com.SCM.dto.SalesDto;
import com.SCM.mapper.GetCurrentUserRoleName;
import com.SCM.model.ActivityLog;
import com.SCM.model.DeliveryCharge;
import com.SCM.model.Retailer;
import com.SCM.model.Sales;
import com.SCM.model.SalesItems;
import com.SCM.model.SalesOrder;
import com.SCM.model.SalesReturn;
import com.SCM.model.VoucherMaster;
import com.SCM.projection.SalesProjection;
import com.SCM.projection.SalesReturnProjection;
import com.SCM.projection.TransportDetailUpdateReportProjection;
import com.SCM.projectionDto.ClosingStockReport;
import com.SCM.projectionDto.SalesReportDto;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.DeliveryChargeRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.SalesItemsRepo;
import com.SCM.repository.SalesRepo;
import com.SCM.repository.VoucherMasterRepo;
import com.SCM.service.SalesService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private SalesRepo salesRepo;

	@Autowired
	private DeliveryChargeRepo deliveryChargeRepo;

	@Autowired
	private ActivityLogRepo activityLogRepo;

	@Autowired
	private SalesItemsRepo salesItemsRepo;

	@Autowired
	private RetailerRepo retailerRepo;

	@Autowired
	private GetCurrentUserRoleName currentUserRoleName;

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	private final static String FOLDER_PATH = "D:/img/";

	private final Path fileStorageLocation;
	
//	private final Path fileStorageLocation1;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private EinvoiceRepository repository;
	
	@Autowired
	private VoucherMasterRepo voucherMasterRepo;

	public SalesServiceImpl(Environment env) throws IOException {

		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir5", "/invoiceAttachment/"))
				.toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
		
//		this.fileStorageLocation1 = Paths.get(env.getProperty("app.file.upload-dir11", "/invoiceAttachmentPdf/"))
//				.toAbsolutePath().normalize();
//		Files.createDirectories(this.fileStorageLocation1);
	}
	
	
	

	@Override

	public Sales saveSales(SalesDto salesdto, String salesitemsjson, String tranDtls, String docDtls, String sellerDtls,
			String buyerDtls, String valDtls, String ewbDtls, MultipartFile imgfiles, MultipartFile pdffiles)

			throws IOException {

//		System.out.println("ssssssssssssssssssssssssssssss"+salesdto.getItemList().get(0).getSlNo());

		VoucherMaster voucher=voucherMasterRepo.findById(salesdto.getVoucherMaster().getId()).get();
		
		
		Sales sales = new Sales();

		Long uid = currentUserRoleName.getUserId();
		String uname = currentUserRoleName.getUserName();
		String role = currentUserRoleName.getRolename();

		sales.setIrnno(salesdto.getIrnno());
		sales.setAckdate(salesdto.getAckdate());
		sales.setAckno(salesdto.getAckno());
		sales.setDeliverynoteno(salesdto.getDeliverynoteno());
		sales.setDestination(salesdto.getDestination());
		sales.setPackaginglistno(salesdto.getPackaginglistno());
		sales.setTaxamount(salesdto.getTaxamount());
		sales.setShippingcharge(salesdto.getShippingcharge());
		sales.setPanno(salesdto.getPanno());
		sales.setGstno(salesdto.getGstno());
		sales.setBuyerorderno(salesdto.getBuyerorderno());
		sales.setTotalnopkg(salesdto.getTotalnopkg());
		sales.setLrno(salesdto.getLrno());
		sales.setLrndate(salesdto.getLrndate());
		sales.setEdd(salesdto.getEdd());
//		sales.setNetAmount(salesdto.getNetAmount());

		sales.setTermsofdelivery(salesdto.getTermsofdelivery());
		sales.setPaymentTerms(salesdto.getPaymentTerms());
		sales.setEwaybillno(salesdto.getEwaybillno());
		sales.setGrnno(salesdto.getGrnno());
		sales.setGrndate(salesdto.getGrndate());
		sales.setGrossamount(salesdto.getGrossamount());
		sales.setGrandtotal(salesdto.getGrandtotal());
		sales.setInvoiceno(salesdto.getInvoiceno());
		sales.setType(salesdto.getType());
		sales.setStatus("directsales");
		sales.setInvoice_status("InACT");
		sales.setEway_status("InACT");
		sales.setTaxtype(salesdto.getTaxtype());
		sales.setMsmeno(salesdto.getMsmeno());
		sales.setUdyamno(salesdto.getUdyamno());
		sales.setInvoicedate(salesdto.getInvoicedate());
		sales.setDispatchedthrough(salesdto.getDispatchedthrough());
		sales.setBranch(salesdto.getBranch());
		sales.setWarehouse(salesdto.getWarehouse());
		sales.setDeliveryAddress(salesdto.getDeliveryAddress());
		sales.setCustomersubContacts(salesdto.getCustomersubContacts());
		sales.setCreateddate(LocalDate.now());
		sales.setCreatedtime(LocalTime.now());
		sales.setVersion(salesdto.getVersion());
		sales.setCreatebyname(uname);
		sales.setCreatedby(uid);
		sales.setIgst(salesdto.getIgst());
		sales.setSgst(salesdto.getSgst());
		sales.setCgst(salesdto.getCgst());
		sales.setIgstVal(salesdto.getIgst_val());
		sales.setCgstVal(salesdto.getCgst_val());
		sales.setSgstVal(salesdto.getSgst_val());
		sales.setRole(role);
		
		sales.setVersion("1.1");

		List<SalesItems> items = mapper.readValue(salesitemsjson, new TypeReference<List<SalesItems>>() {
		});
		sales.setSalesItems(items);

	
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSlNo(i + 1);
		}

		TranDtls dtls = mapper.readValue(tranDtls, new TypeReference<TranDtls>() {

		});
		salesdto.setTranDtls(dtls);
		sales.setTaxSch("GST");
		sales.setSupTyp(salesdto.getTranDtls().getSupTyp());

		sales.setEcmGstin(salesdto.getTranDtls().getEcmGstin());

		System.out.println("param data:" + sales);

//		sales.setIgstOnIntra(salesdto.getTranDtls().getIgstOnIntra());

		System.out.println("param data:" + sales);

		DocDtls doc = mapper.readValue(docDtls, new TypeReference<DocDtls>() {

		});

		salesdto.setDocDtls(doc);
		sales.setTyp("INV");
		sales.setNo(salesdto.getDocDtls().getNo());
		sales.setDt(salesdto.getDocDtls().getDt());

		SellerDtls seller = mapper.readValue(sellerDtls, new TypeReference<SellerDtls>() {

		});
		salesdto.setSellerDtls(seller);
		sales.setGstin(salesdto.getSellerDtls().getGstin());
		sales.setLglNm(salesdto.getSellerDtls().getLglNm());

		sales.setAddr1(salesdto.getSellerDtls().getAddr1());

		sales.setLoc(salesdto.getSellerDtls().getLoc());
		sales.setPin(salesdto.getSellerDtls().getPin());
		sales.setStcd(salesdto.getSellerDtls().getStcd());
		sales.setPh(salesdto.getSellerDtls().getPh());
		sales.setEm(salesdto.getSellerDtls().getEm());

		BuyerDtls buyer = mapper.readValue(buyerDtls, new TypeReference<BuyerDtls>() {

		});
		salesdto.setBuyerDtls(buyer);
		sales.setBgstin(salesdto.getBuyerDtls().getGstin());
		sales.setBlglNm(salesdto.getBuyerDtls().getLglNm());
		sales.setPos(salesdto.getBuyerDtls().getPos());
		sales.setBaddr1(salesdto.getBuyerDtls().getAddr1());
		sales.setBloc(salesdto.getBuyerDtls().getLoc());
		sales.setBpin(salesdto.getBuyerDtls().getPin());
		sales.setBstcd(salesdto.getBuyerDtls().getStcd());
		sales.setBph(salesdto.getBuyerDtls().getPh());
		sales.setBem(salesdto.getBuyerDtls().getEm());

		ValDtls val = mapper.readValue(valDtls, new TypeReference<ValDtls>() {

		});

		salesdto.setValDtls(val);
		sales.setAssVal(salesdto.getValDtls().getAssVal());
		sales.setCgstVal(salesdto.getValDtls().getCgstVal());
		sales.setSgstVal(salesdto.getValDtls().getSgstVal());
		sales.setIgstVal(salesdto.getValDtls().getIgstVal());
		sales.setCesVal(salesdto.getValDtls().getCesVal());
		sales.setStCesVal(salesdto.getValDtls().getStCesVal());
		sales.setVothChrg(salesdto.getValDtls().getOthChrg());

		sales.setTotInvVal(salesdto.getValDtls().getTotInvVal());


		EwbDtls ewb = mapper.readValue(ewbDtls, new TypeReference<EwbDtls>() {

		});

//		salesdto.setEwbDtls(ewb);
//		sales.setDistance(salesdto.getEwbDtls().getDistance());
//		sales.setTransMode(salesdto.getEwbDtls().getTransMode());
//		sales.setTransId(salesdto.getEwbDtls().getTransId());
//		sales.setTransName(salesdto.getEwbDtls().getTransName());
//		sales.setTransDocDt(salesdto.getEwbDtls().getTransDocDt());
//		sales.setTransDocNo(salesdto.getEwbDtls().getTransDocNo());
//		sales.setVehNo(salesdto.getEwbDtls().getVehNo());
//		sales.setVehType(salesdto.getEwbDtls().getVehType());


		salesdto.setEwbDtls(ewb);
		sales.setDistance(salesdto.getEwbDtls().getDistance());
		sales.setTransMode(salesdto.getEwbDtls().getTransMode());
		sales.setTransporters(salesdto.getTransporters());
		sales.setTransName(salesdto.getEwbDtls().getTransName());
		sales.setTransDocDt(salesdto.getEwbDtls().getTransDocDt());
		sales.setTransDocNo(salesdto.getEwbDtls().getTransDocNo());
		sales.setVehNo(salesdto.getEwbDtls().getVehNo());
		sales.setVehType(salesdto.getEwbDtls().getVehType());
		sales.setDistributor(salesdto.getDistributor());
		sales.setVoucherid(salesdto.getVoucherid());
		sales.setVouchernumber(salesdto.getVouchernumber());
		sales.setVoucherseries(salesdto.getVoucherseries());
		sales.setVid(salesdto.getVid());
		sales.setDistributor(salesdto.getDistributor());
		sales.setRetailer(salesdto.getRetailer());
		sales.setVoucherMaster(voucher);

		// ---------------------------------- without distributor 
		
		if (salesdto.getDistributor() == null) {

			if (salesdto.getRetailer() != null) {

				Retailer rt = retailerRepo.findById(salesdto.getRetailer().getId()).get();

				sales.setRetailer(salesdto.getRetailer());
				sales.setDistributor(rt.getDistributor());
				sales.setRetailerstatus("customer");
			}
		}
		
		// -----------------------------------voucher 

//		if (salesRepo.searchByVoucher(salesdto.getVoucherid()) == null) {
//
//			sales.setVoucherid(salesdto.getVoucherid());
//			sales.setVouchernumber(1);
//			String newVoucher = salesdto.getVoucherid() + 1;
//			sales.setVoucherseries(newVoucher);
//		}
//
//		else {
//			Sales s = salesRepo.searchByVoucher(salesdto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser = newvid + vnum;
//			sales.setVoucherid(newvid);
//			sales.setVouchernumber(newvnum);
//			sales.setVoucherseries(newvser);
//
//		}
		
		
		
		
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			Sales topByVoucherOrderByStartnumberwithprefilnoDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Sales topByVoucherOrderByStartnumberwithprefilyesDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Sales lastrowstatus = salesRepo.lastrowstatus();
			Sales lastrowstatus = salesRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				sales.setStartnumberwithprefilno(startingnumber);
				sales.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					sales.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					sales.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					sales.setStartnumberwithprefilno(restartnumber + 1);
					sales.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					sales.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					sales.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					sales.setStartnumberwithprefilyes(formattedStartingNumber);
					sales.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					sales.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					sales.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					sales.setStartnumberwithprefilyes(restartnumberinc);
					sales.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					sales.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					sales.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			Sales topByVoucherOrderByStartnumberwithprefilnoDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Sales topByVoucherOrderByStartnumberwithprefilyesDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Sales lastrowstatus = salesRepo.lastrowstatus();
			Sales lastrowstatus = salesRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				sales.setStartnumberwithprefilno(startingnumber);
				sales.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				sales.setStartnumberwithprefilyes(formattedStartingNumber);
				sales.setVoucherstatus("startnostatus");
			}
		}

		Sales save = salesRepo.save(sales);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		sales.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		salesRepo.save(sales);
		
		

		salesRepo.save(sales);

		Sales s = salesRepo.save(sales);

		// ------------------------------------ img

		String pdffolder = "PDF_" + s.getId() + "_" + pdffiles.getOriginalFilename();
		String imgfolder = "IMG_" + s.getId() + "_" + imgfiles.getOriginalFilename();


		if (imgfiles.isEmpty() && pdffiles.isEmpty()) {
			sales.setImgname(null);
			sales.setImglocation(null);
			sales.setPdfname(null);
			sales.setPdflocation(null);

			salesRepo.save(sales);
		}
		if (!imgfiles.isEmpty() && pdffiles.isEmpty()) {

			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
					StandardCopyOption.REPLACE_EXISTING);

			sales.setImgname(imgfiles.getOriginalFilename());
			sales.setImglocation("invoiceAttachment/" + imgfolder);
			sales.setPdfname(null);
			sales.setPdflocation(null);

			salesRepo.save(sales);
		}
		if (imgfiles.isEmpty() && !pdffiles.isEmpty()) {

			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
					StandardCopyOption.REPLACE_EXISTING);

			sales.setImgname(null);
			sales.setImglocation(null);
			sales.setPdfname(pdffiles.getOriginalFilename());
			sales.setPdflocation("invoiceAttachment/" + pdffolder);

			salesRepo.save(sales);
		}
		if (!imgfiles.isEmpty() && !pdffiles.isEmpty()) {

			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
					StandardCopyOption.REPLACE_EXISTING);

			sales.setImgname(imgfiles.getOriginalFilename());
			sales.setImglocation("invoiceAttachment/" + imgfolder);
			sales.setPdfname(pdffiles.getOriginalFilename());
			sales.setPdflocation("invoiceAttachment/" + pdffolder);

		
		}
		
		
//		String imgfolder = "IMG_" + s.getId() + imgfiles.getOriginalFilename();
//		String pdffolder = "PDF_" + s.getId() + pdffiles.getOriginalFilename();
//
//		String imgContentType = imgfiles.getContentType();
//		String pdfContentType = pdffiles.getContentType();
//
//		
//		boolean isImage = imgContentType != null && (
//		        imgContentType.equals("image/jpeg") ||
//		        imgContentType.equals("image/png") ||
//		        imgContentType.equals("image/gif")); 
//
//		boolean isPdf = pdfContentType != null && pdfContentType.equals("application/pdf");
//
//		if (imgfiles.isEmpty() && pdffiles.isEmpty()) {
//		    sales.setImgname(null);
//		    sales.setImglocation(null);
//		    sales.setPdfname(null);
//		    sales.setPdflocation(null);
//		    salesRepo.save(sales);
//
//		} else if (!imgfiles.isEmpty() && pdffiles.isEmpty()) {
//
//		    if (!isImage) {
//		        throw new IllegalArgumentException("Invalid image file type. Only JPG, PNG, or GIF allowed.");
//		    }
//
//		    Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
//		            StandardCopyOption.REPLACE_EXISTING);
//
//		    sales.setImgname(imgfiles.getOriginalFilename());
//		    sales.setImglocation("invoiceAttachment/" + imgfolder);
//		    sales.setPdfname(null);
//		    sales.setPdflocation(null);
//		    salesRepo.save(sales);
//
//		} else if (imgfiles.isEmpty() && !pdffiles.isEmpty()) {
//
//		    if (!isPdf) {
//		        throw new IllegalArgumentException("Invalid PDF file type. Only application/pdf allowed.");
//		    }
//
//		    Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
//		            StandardCopyOption.REPLACE_EXISTING);
//
//		    sales.setImgname(null);
//		    sales.setImglocation(null);
//		    sales.setPdfname(pdffiles.getOriginalFilename());
//		    sales.setPdflocation("invoiceAttachment/" + pdffolder);
//		    salesRepo.save(sales);
//
//		} else {
//
//		    if (!isImage) {
//		        throw new IllegalArgumentException("Invalid image file type. Only JPG, PNG, or GIF allowed.");
//		    }
//		    if (!isPdf) {
//		        throw new IllegalArgumentException("Invalid PDF file type. Only application/pdf allowed.");
//		    }
//
//		    Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
//		            StandardCopyOption.REPLACE_EXISTING);
//		    Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
//		            StandardCopyOption.REPLACE_EXISTING);
//
//		    sales.setImgname(imgfiles.getOriginalFilename());
//		    sales.setImglocation("invoiceAttachment/" + imgfolder);
//		    sales.setPdfname(pdffiles.getOriginalFilename());
//		    sales.setPdflocation("invoiceAttachment/" + pdffolder);
//		    salesRepo.save(sales);
//		}
		
		
	


		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSalesid((long) s.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		salesRepo.save(sales);
		return s;

	}

	// ---------- convert dc to sales

	@Override
	public Sales ConvertDCToSales(SalesDto salesdto, String salesitemsjson, String tranDtls, String docDtls,

		String sellerDtls, String buyerDtls, String valDtls, String ewbDtls,

			MultipartFile imgfiles, MultipartFile pdffiles, int dcId) throws IOException {
		
		VoucherMaster voucher=voucherMasterRepo.findById(salesdto.getVoucherMaster().getId()).get();

		Optional<DeliveryCharge> deliverycharge = deliveryChargeRepo.findById(dcId);
		DeliveryCharge Dc = deliverycharge.get();

		Long uid = currentUserRoleName.getUserId();
		String uname = currentUserRoleName.getUserName();
		String role = currentUserRoleName.getRolename();

		Sales sales = new Sales();
		sales.setIrnno(salesdto.getIrnno());
		sales.setAckdate(salesdto.getAckdate());
		sales.setAckno(salesdto.getAckno());
		sales.setDeliverynoteno(salesdto.getDeliverynoteno());
		sales.setDestination(salesdto.getDestination());
		sales.setPackaginglistno(salesdto.getPackaginglistno());
		sales.setTaxamount(salesdto.getTaxamount());
		sales.setShippingcharge(salesdto.getShippingcharge());
		sales.setPanno(salesdto.getPanno());
		sales.setGstno(salesdto.getGstno());
		sales.setBuyerorderno(salesdto.getBuyerorderno());
		sales.setTotalnopkg(salesdto.getTotalnopkg());
		sales.setLrno(salesdto.getLrno());
		sales.setLrndate(salesdto.getLrndate());
		sales.setEdd(salesdto.getEdd());
		sales.setNetAmount(salesdto.getNetAmount());
		sales.setTermsofdelivery(salesdto.getTermsofdelivery());
		sales.setPaymentTerms(salesdto.getPaymentTerms());
		sales.setEwaybillno(salesdto.getEwaybillno());
		sales.setGrnno(salesdto.getGrnno());
		sales.setGrndate(salesdto.getGrndate());
		sales.setGrossamount(salesdto.getGrossamount());
		sales.setGrandtotal(salesdto.getGrandtotal());
		sales.setInvoiceno(salesdto.getInvoiceno());
		sales.setType(salesdto.getType());
		sales.setStatus("converted");
		sales.setInvoice_status("InACT");
		sales.setEway_status("InACT");
		sales.setTaxtype(salesdto.getTaxtype());
		sales.setMsmeno(salesdto.getMsmeno());
		sales.setUdyamno(salesdto.getUdyamno());
		sales.setInvoicedate(salesdto.getInvoicedate());
		sales.setDispatchedthrough(salesdto.getDispatchedthrough());
		sales.setBranch(salesdto.getBranch());
		sales.setWarehouse(salesdto.getWarehouse());
		sales.setDeliveryAddress(salesdto.getDeliveryAddress());
		sales.setCustomersubContacts(salesdto.getCustomersubContacts());
		sales.setCreateddate(LocalDate.now());
		sales.setCreatedtime(LocalTime.now());

		sales.setCreatebyname(uname);
		sales.setCreatedby(uid);
		sales.setRole(role);
		sales.setVersion("1.1");

		sales.setVersion(salesdto.getVersion());
		sales.setCreatebyname(uname);
		sales.setCreatedby(uid);
		sales.setRole(role);

		List<SalesItems> items = mapper.readValue(salesitemsjson, new TypeReference<List<SalesItems>>() {
		});
		sales.setSalesItems(items);

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSlNo(i + 1);
		}

		TranDtls dtls = mapper.readValue(tranDtls, new TypeReference<TranDtls>() {
		});
		salesdto.setTranDtls(dtls);
		sales.setTaxSch("GST");
		sales.setSupTyp(salesdto.getTranDtls().getSupTyp());

		sales.setEcmGstin(salesdto.getTranDtls().getEcmGstin());

//
//		TranDtls dtls = mapper.readValue(tranDtls, new TypeReference<TranDtls>() {
//		});
//		salesdto.setTranDtls(dtls);
//		sales.setTaxSch(salesdto.getTranDtls().getTaxSch());
//		sales.setSupTyp(salesdto.getTranDtls().getSupTyp());
//		sales.setRegRev(salesdto.getTranDtls().getRegRev());
//		sales.setEcmGstin(salesdto.getTranDtls().getEcmGstin());
//		sales.setIgstOnIntra(salesdto.getTranDtls().getIgstOnIntra());

		System.out.println("param data:" + sales);

		DocDtls doc = mapper.readValue(docDtls, new TypeReference<DocDtls>() {

		});
		salesdto.setDocDtls(doc);

		sales.setTyp("INV");

		sales.setNo(salesdto.getDocDtls().getNo());
		sales.setDt(salesdto.getDocDtls().getDt());

		SellerDtls seller = mapper.readValue(sellerDtls, new TypeReference<SellerDtls>() {

		});
		salesdto.setSellerDtls(seller);
		sales.setGstin(salesdto.getSellerDtls().getGstin());
		sales.setLglNm(salesdto.getSellerDtls().getLglNm());

		sales.setAddr1(salesdto.getSellerDtls().getAddr1());

//		sales.setTrdNm(salesdto.getSellerDtls().getTrdNm());
		sales.setAddr1(salesdto.getSellerDtls().getAddr1());
//		sales.setAddr2(salesdto.getSellerDtls().getAddr2());

		sales.setLoc(salesdto.getSellerDtls().getLoc());
		sales.setPin(salesdto.getSellerDtls().getPin());
		sales.setStcd(salesdto.getSellerDtls().getStcd());
		sales.setPh(salesdto.getSellerDtls().getPh());
		sales.setEm(salesdto.getSellerDtls().getEm());

		BuyerDtls buyer = mapper.readValue(buyerDtls, new TypeReference<BuyerDtls>() {

		});
		salesdto.setBuyerDtls(buyer);
		sales.setBgstin(salesdto.getBuyerDtls().getGstin());
		sales.setBlglNm(salesdto.getBuyerDtls().getLglNm());

		sales.setPos(salesdto.getBuyerDtls().getPos());
		sales.setBaddr1(salesdto.getBuyerDtls().getAddr1());

//		sales.setBtrdNm(salesdto.getBuyerDtls().getTrdNm());
		sales.setPos(salesdto.getBuyerDtls().getPos());
		sales.setBaddr1(salesdto.getBuyerDtls().getAddr1());
//		sales.setBaddr2(salesdto.getBuyerDtls().getAddr2());

		sales.setBloc(salesdto.getBuyerDtls().getLoc());
		sales.setBpin(salesdto.getBuyerDtls().getPin());
		sales.setBstcd(salesdto.getBuyerDtls().getStcd());
		sales.setBph(salesdto.getBuyerDtls().getPh());
		sales.setBem(salesdto.getBuyerDtls().getEm());

//
//		List<ItemList> item = mapper.readValue(itemList, new TypeReference<List<ItemList>>() {
//
//		});
//		salesdto.setItemList(item);
//		sales.setSlNo(salesdto.getItemList().get(0).getSlNo());
//		sales.setPrdDesc(salesdto.getItemList().get(0).getPrdDesc());
//		sales.setIsServc(salesdto.getItemList().get(0).getIsServc());
//		sales.setHsnCd(salesdto.getItemList().get(0).getHsnCd());
//		sales.setBarcde(salesdto.getItemList().get(0).getBarcde());
//		sales.setQty(salesdto.getItemList().get(0).getQty());
//		sales.setFreeQty(salesdto.getItemList().get(0).getFreeQty());
//		sales.setUnit(salesdto.getItemList().get(0).getUnit());
//		sales.setUnitPrice(salesdto.getItemList().get(0).getUnitPrice());
//		sales.setTotAmt(salesdto.getItemList().get(0).getTotAmt());
//		sales.setDiscount(salesdto.getItemList().get(0).getDiscount());
//		sales.setPreTaxVal(salesdto.getItemList().get(0).getPreTaxVal());
//		sales.setAssAmt(salesdto.getItemList().get(0).getAssAmt());
//		sales.setGstRt(salesdto.getItemList().get(0).getGstRt());
//		sales.setIgstAmt(salesdto.getItemList().get(0).getIgstAmt());
//		sales.setCgstAmt(salesdto.getItemList().get(0).getCgstAmt());
//		sales.setSgstAmt(salesdto.getItemList().get(0).getSgstAmt());
//		sales.setCesRt(salesdto.getItemList().get(0).getCesRt());
//		sales.setCesAmt(salesdto.getItemList().get(0).getCesAmt());
//		sales.setCesNonAdvlAmt(salesdto.getItemList().get(0).getCesNonAdvlAmt());
//		sales.setStateCesRt(salesdto.getItemList().get(0).getStateCesRt());
//		sales.setStateCesAmt(salesdto.getItemList().get(0).getStateCesAmt());
//		sales.setStateCesNonAdvlAmt(salesdto.getItemList().get(0).getStateCesNonAdvlAmt());
//		sales.setOthChrg(salesdto.getItemList().get(0).getOthChrg());
//		sales.setTotItemVal(salesdto.getItemList().get(0).getTotItemVal());
//		sales.setOrdLineRef(salesdto.getItemList().get(0).getOrdLineRef());
//		sales.setOrgCntry(salesdto.getItemList().get(0).getOrgCntry());
//		sales.setPrdSlNo(salesdto.getItemList().get(0).getPrdSlNo());
//		sales.setNm(salesdto.getItemList().get(0).getBchDtls().getNm());
//		sales.setExpDt(salesdto.getItemList().get(0).getBchDtls().getExpDt());
//		sales.setWrDt(salesdto.getItemList().get(0).getBchDtls().getWrDt());
//		sales.setAnm(salesdto.getItemList().get(0).getAttribDtls().get(0).getNm());
//		sales.setVal(salesdto.getItemList().get(0).getAttribDtls().get(0).getVal());

		ValDtls val = mapper.readValue(valDtls, new TypeReference<ValDtls>() {

		});

		salesdto.setValDtls(val);
		sales.setAssVal(salesdto.getValDtls().getAssVal());
		sales.setCgstVal(salesdto.getValDtls().getCgstVal());
		sales.setSgstVal(salesdto.getValDtls().getSgstVal());
		sales.setIgstVal(salesdto.getValDtls().getIgstVal());
		sales.setCesVal(salesdto.getValDtls().getCesVal());
		sales.setStCesVal(salesdto.getValDtls().getStCesVal());

		sales.setVothChrg(salesdto.getValDtls().getOthChrg());

		sales.setTotInvVal(salesdto.getValDtls().getTotInvVal());

//
//		sales.setVothChrg(salesdto.getValDtls().getOthChrg());
//
//		sales.setTotInvVal(salesdto.getValDtls().getTotInvVal());

		EwbDtls ewb = mapper.readValue(ewbDtls, new TypeReference<EwbDtls>() {

		});

		salesdto.setEwbDtls(ewb);
		sales.setDistance(salesdto.getEwbDtls().getDistance());
		sales.setTransMode(salesdto.getEwbDtls().getTransMode());
		sales.setTransporters(salesdto.getTransporters());
		sales.setTransName(salesdto.getEwbDtls().getTransName());
		sales.setTransDocDt(salesdto.getEwbDtls().getTransDocDt());
		sales.setTransDocNo(salesdto.getEwbDtls().getTransDocNo());
		sales.setVehNo(salesdto.getEwbDtls().getVehNo());
		sales.setVehType(salesdto.getEwbDtls().getVehType());
		sales.setDistributor(salesdto.getDistributor());
		sales.setVoucherid(salesdto.getVoucherid());
		sales.setVouchernumber(salesdto.getVouchernumber());
		sales.setVoucherseries(salesdto.getVoucherseries());
		sales.setVid(salesdto.getVid());
		sales.setDcId(Dc.getId());
		sales.setCreateddate(LocalDate.now());
		sales.setCreatedtime(LocalTime.now());
		sales.setDistributor(salesdto.getDistributor());
		sales.setStatus("pending");
		sales.setVoucherMaster(voucher);

		if (salesdto.getDistributor() == null) {

			if (salesdto.getRetailer() != null) {

				Retailer rt = retailerRepo.findById(salesdto.getRetailer().getId()).get();

				sales.setRetailer(salesdto.getRetailer());
				sales.setDistributor(rt.getDistributor());
				sales.setRetailerstatus("customer");
			}
		}

//		if (salesRepo.searchByVoucher(salesdto.getVoucherid()) == null) {
////			salesdto.getVoucherid()
//			sales.setVoucherid(salesdto.getVoucherid());
//			sales.setVouchernumber(1);
//			String newVoucher = salesdto.getVoucherid() + 1;
//			sales.setVoucherseries(newVoucher);
//		} else {
//			Sales s = salesRepo.searchByVoucher(salesdto.getVoucherid());
//			String newvid = s.getVoucherid();
//			long vnum = s.getVouchernumber();
//			long newvnum = ++vnum;
//			String newvser = newvid + vnum;
//			sales.setVoucherid(newvid);
//			sales.setVouchernumber(newvnum);
//			sales.setVoucherseries(newvser);
//
//		}
		
	
		int startingnumber = voucher.getStartingnumber();
		int restartnumber = voucher.getRestartnumber();
		String prefixparticular = voucher.getPrefixparticular();
		String suffixparticular = voucher.getSuffixparticular();

		try {

			Sales topByVoucherOrderByStartnumberwithprefilnoDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Sales topByVoucherOrderByStartnumberwithprefilyesDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Sales lastrowstatus = salesRepo.lastrowstatus();
			Sales lastrowstatus = salesRepo.findTopByVoucherMasterOrderByIdDesc(voucher);


			
			
			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null)|| (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null&& !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				sales.setStartnumberwithprefilno(startingnumber);
				sales.setVoucherstatus("startnostatus");
			}

			String status = lastrowstatus.getVoucherstatus();

			if (voucher.getPrefil().equals("No")) {

				if (topByVoucherOrderByStartnumberwithprefilnoDesc != null && status.equals("startnostatus")) {

					int startnumberwithprefilno = topByVoucherOrderByStartnumberwithprefilnoDesc.getStartnumberwithprefilno();
					sales.setStartnumberwithprefilno(startnumberwithprefilno + 1);
					sales.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

					sales.setStartnumberwithprefilno(restartnumber + 1);
					sales.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					sales.setStartnumberwithprefilno(lastrowstatus.getStartnumberwithprefilno() + 1);
					sales.setVoucherstatus("restartnostatus");
				}
			}
			else if (voucher.getPrefil().equals("Yes")) {

				if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

					String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
					sales.setStartnumberwithprefilyes(formattedStartingNumber);
					sales.setVoucherstatus("startnostatus");
				}
				if (topByVoucherOrderByStartnumberwithprefilyesDesc != null && voucher.getWidth() > 0 && status.equals("startnostatus")) {

					String startnumberwithprefilyes = topByVoucherOrderByStartnumberwithprefilyesDesc.getStartnumberwithprefilyes();
					int incrementstartno = Integer.parseInt(startnumberwithprefilyes) + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",incrementstartno);

					sales.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					sales.setVoucherstatus("startnostatus");
				}
				if (restartnumber != 0 && status.equals("startnostatus")) {

////					restartnumber = restartnumber + 1;
					String restartnumberinc = String.format("%0" + voucher.getWidth() + "d", restartnumber);
					sales.setStartnumberwithprefilyes(restartnumberinc);
					sales.setVoucherstatus("restartnostatus");
				}
				if (restartnumber != 0 && status.equals("restartnostatus")) {

					int startnumberwithprefilyes = Integer.parseInt(lastrowstatus.getStartnumberwithprefilyes());
					startnumberwithprefilyes = startnumberwithprefilyes + 1;
					String startnumberwithprefilyesinc = String.format("%0" + voucher.getWidth() + "d",startnumberwithprefilyes);
					sales.setStartnumberwithprefilyes(startnumberwithprefilyesinc);
					sales.setVoucherstatus("restartnostatus");
				}
			}

		} catch (Exception e) {

			Sales topByVoucherOrderByStartnumberwithprefilnoDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilnoDesc(voucher);
			Sales topByVoucherOrderByStartnumberwithprefilyesDesc = salesRepo.findTopByVoucherMasterOrderByStartnumberwithprefilyesDesc(voucher);
//			Sales lastrowstatus = salesRepo.lastrowstatus();
			Sales lastrowstatus = salesRepo.findTopByVoucherMasterOrderByIdDesc(voucher);

			if ((voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc == null) || (voucher.getPrefil().equals("No") && topByVoucherOrderByStartnumberwithprefilnoDesc != null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent())) {

				sales.setStartnumberwithprefilno(startingnumber + 1);
				sales.setVoucherstatus("startnostatus");

			} else if ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null) || ((voucher.getPrefil().equals("Yes") && topByVoucherOrderByStartnumberwithprefilyesDesc == null && !Optional.ofNullable(lastrowstatus.getVoucherstatus()).isPresent()))) {

				String formattedStartingNumber = String.format("%0" + voucher.getWidth() + "d", startingnumber);
				sales.setStartnumberwithprefilyes(formattedStartingNumber);
				sales.setVoucherstatus("startnostatus");
			}
		}

		Sales save = salesRepo.save(sales);

		String startnumber1;

		if (save.getStartnumberwithprefilyes() != null) {
			startnumber1 = save.getStartnumberwithprefilyes();
		} else {
			startnumber1 = String.valueOf(save.getStartnumberwithprefilno());
		}

		sales.setVouchermasterSeries(prefixparticular + startnumber1 + suffixparticular);
		salesRepo.save(sales);
		
		

		Sales s = salesRepo.save(sales);

		DeliveryCharge charge = deliveryChargeRepo.findById(s.getDcId()).get();
		charge.setStatus("converted");
		deliveryChargeRepo.save(charge);

		// --------- img

		String imgfolder = "IMG_" + s.getId() + imgfiles.getOriginalFilename();
		String pdffolder = "PDF_" + s.getId() + pdffiles.getOriginalFilename();

		if (imgfiles.isEmpty() && pdffiles.isEmpty()) {
			sales.setImgname(null);
			sales.setImglocation(null);
			sales.setPdfname(null);
			sales.setPdflocation(null);

			salesRepo.save(sales);
		}
		if (!imgfiles.isEmpty() && pdffiles.isEmpty()) {

			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
					StandardCopyOption.REPLACE_EXISTING);

			sales.setImgname(imgfiles.getOriginalFilename());
			sales.setImglocation("invoiceAttachment/" + imgfolder);
			sales.setPdfname(null);
			sales.setPdflocation(null);

			salesRepo.save(sales);
		}
		if (imgfiles.isEmpty() && !pdffiles.isEmpty()) {

			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
					StandardCopyOption.REPLACE_EXISTING);

			sales.setImgname(null);
			sales.setImglocation(null);
			sales.setPdfname(pdffiles.getOriginalFilename());
			sales.setPdflocation("invoiceAttachment/" + pdffolder);

			salesRepo.save(sales);
		}
		if (!imgfiles.isEmpty() && !pdffiles.isEmpty()) {

			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(imgfolder),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(pdffolder),
					StandardCopyOption.REPLACE_EXISTING);

			sales.setImgname(imgfiles.getOriginalFilename());
			sales.setImglocation("invoiceAttachment/" + imgfolder);
			sales.setPdfname(pdffiles.getOriginalFilename());
			sales.setPdflocation("invoiceAttachment/" + pdffolder);

			salesRepo.save(sales);
		}

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();

		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setSalesid((long) s.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return s;
	}

//	@Override
//	public Sales ConvertDCToSales(SalesDto salesdto, int dcId) {
//
//		Optional<DeliveryCharge> deliverycharge = deliveryChargeRepo.findById(dcId);
//		DeliveryCharge Dc = deliverycharge.get();
//
//		Sales sales = new Sales();
//		sales.setIrnno(salesdto.getIrnno());
//		sales.setAckdate(salesdto.getAckdate());
//		sales.setAckno(salesdto.getAckno());
//		sales.setDeliverynoteno(salesdto.getDeliverynoteno());
//		sales.setDestination(salesdto.getDestination());
//		sales.setPackaginglistno(salesdto.getPackaginglistno());
//		sales.setTaxamount(salesdto.getTaxamount());
//		sales.setShippingcharge(salesdto.getShippingcharge());
//		sales.setPanno(salesdto.getPanno());
//		sales.setGstno(salesdto.getGstno());
//
//		sales.setStatus(salesdto.getStatus());
//
//		sales.setTotalnopkg(salesdto.getTotalnopkg());
//		sales.setLrno(salesdto.getLrno());
//		sales.setLrndate(salesdto.getLrndate());
//		sales.setEdd(sales.getEdd());
//		sales.setBuyerorderno(salesdto.getBuyerorderno());
//		sales.setNetvalue(salesdto.getNetvalue());
//		sales.setTermsofdelivery(salesdto.getTermsofdelivery());
//		sales.setEwaybillno(salesdto.getEwaybillno());
//		sales.setGrnno(salesdto.getGrnno());
//		sales.setGrndate(salesdto.getGrndate());
//		sales.setGrossamount(salesdto.getGrossamount());
//		sales.setInvoiceno(salesdto.getInvoiceno());
//		sales.setType(salesdto.getType());
//		sales.setPaymentTerms(salesdto.getPaymentTerms());
//
//		sales.setStatus(salesdto.getStatus());
//		;
//
//		sales.setTaxtype(salesdto.getTaxtype());
//		sales.setMsmeno(salesdto.getMsmeno());
//		sales.setUdyamno(salesdto.getUdyamno());
//		sales.setInvoicedate(salesdto.getInvoicedate());
//		sales.setDispatchedthrough(salesdto.getDispatchedthrough());
//		sales.setWarehouse(salesdto.getWarehouse());
//		sales.setDeliveryAddress(salesdto.getDeliveryAddress());
//		sales.setBranch(salesdto.getBranch());
//		sales.setCustomersubContacts(salesdto.getCustomersubContacts());
//		sales.setSalesItems(salesdto.getSalesItems());
//		sales.setDcId(Dc.getId());
//		sales.setCreateddate(LocalDate.now());
//		sales.setCreatedtime(LocalTime.now());
//
//		sales.setDistributor(salesdto.getDistributor());
//		sales.setStatus("pending");
//
//		if (salesdto.getDistributor() == null) {
//			if (salesdto.getRetailer() != null) {
//				Retailer rt = retailerRepo.findById(salesdto.getRetailer().getId()).get();
//				sales.setRetailer(salesdto.getRetailer());
//				sales.setDistributor(rt.getDistributor());
//				sales.setRetailerstatus(rt.getRetailerstatus());
//			}
//		}
//
//		sales.setVoucherid(salesdto.getVoucherid());
//		sales.setVouchernumber(salesdto.getVouchernumber());
//		sales.setVoucherseries(salesdto.getVoucherseries());
//		sales.setVoucherid(salesdto.getVoucherid());
//		sales.setVouchernumber(salesdto.getVouchernumber());
//		sales.setVoucherseries(salesdto.getVoucherseries());
//		sales.setVid(salesdto.getVid());
//
//		Sales s = salesRepo.save(sales);
//
//		DeliveryCharge charge = deliveryChargeRepo.findById(s.getDcId()).get();
//		charge.setStatus("converted");
//
//		deliveryChargeRepo.save(charge);
//
//		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		Long loggeduser = userDetailsImpl.getId();
//
//		ActivityLog activityLog = new ActivityLog();
//		activityLog.setCreatedate(new Date());
//		activityLog.setCreatedtime(LocalTime.now());
//		activityLog.setSalesid((long) s.getId());
//		activityLog.setLoggeduser(loggeduser);
//
//		activityLogRepo.save(activityLog);
//
//		return s;
//	}

	@Override
	public List<Sales> getAllSales() {

		return salesRepo.findAll();
	}

	public EinvoiceData getEinvoiceData(long id) {
		EinvoiceData data = repository.findById(id).get();
		return data;
	}

	@Override
	public Sales getSalesById(int id) {
		Sales s = salesRepo.findById(id).get();

		return s;
	}

	@Override
	public void deleteSales(int id) {
		salesRepo.deleteById(id);

	}

//	public SalesDto updateSales(SalesDto salesdto, int id, String salesitemsjson, String tranDtls, String docDtls,
//			String sellerDtls, String buyerDtls, String itemList, String valDtls, String ewbDtls,
//			MultipartFile imgfiles, MultipartFile pdffiles) throws IOException {

	@Override

	public SalesDto updateSales(SalesDto salesdto, int id, String salesitemsjson, String tranDtls, String docDtls,
			String sellerDtls, String buyerDtls, String valDtls, String ewbDtls, MultipartFile imgfiles,
			MultipartFile pdffiles) throws IOException {

		Long uid = currentUserRoleName.getUserId();
		String uname = currentUserRoleName.getUserName();
		String role = currentUserRoleName.getRolename();

		Sales sales = salesRepo.findById(id).get();
		System.out.println(sales.getImgname() + "12312414141413413434");
		sales.setIrnno(salesdto.getIrnno());
		sales.setAckdate(salesdto.getAckdate());
		sales.setAckno(salesdto.getAckno());
		sales.setDeliverynoteno(salesdto.getDeliverynoteno());
		sales.setDestination(salesdto.getDestination());
		sales.setPackaginglistno(salesdto.getPackaginglistno());
		sales.setTaxamount(salesdto.getTaxamount());
		sales.setShippingcharge(salesdto.getShippingcharge());
		sales.setPanno(salesdto.getPanno());
		sales.setTotalnopkg(salesdto.getTotalnopkg());
		sales.setLrno(salesdto.getLrno());
		sales.setLrndate(salesdto.getLrndate());
		sales.setEdd(salesdto.getEdd());
		sales.setPaymentTerms(salesdto.getPaymentTerms());
		sales.setGstno(salesdto.getGstno());
		sales.setBuyerorderno(salesdto.getBuyerorderno());
		sales.setNetAmount(salesdto.getNetAmount());
		sales.setTermsofdelivery(salesdto.getTermsofdelivery());
		sales.setEwaybillno(salesdto.getEwaybillno());
		sales.setGrnno(salesdto.getGrnno());
		sales.setGrndate(salesdto.getGrndate());
		sales.setGrossamount(salesdto.getGrossamount());
		sales.setGrandtotal(salesdto.getGrandtotal());
		sales.setInvoiceno(salesdto.getInvoiceno());
		sales.setType(salesdto.getType());
		sales.setStatus(salesdto.getStatus());
		sales.setInvoice_status("InACT");
		sales.setTaxtype(salesdto.getTaxtype());
		sales.setMsmeno(salesdto.getMsmeno());
		sales.setUdyamno(salesdto.getUdyamno());
		sales.setTaxtype(salesdto.getTaxtype());
		sales.setMsmeno(salesdto.getMsmeno());
		sales.setUdyamno(salesdto.getUdyamno());
		sales.setInvoicedate(salesdto.getInvoicedate());
		sales.setDispatchedthrough(salesdto.getDispatchedthrough());
		sales.setWarehouse(salesdto.getWarehouse());
		sales.setRetailer(salesdto.getRetailer());
		sales.setDistributor(salesdto.getDistributor());
		sales.setDeliveryAddress(salesdto.getDeliveryAddress());
		sales.setBranch(salesdto.getBranch());
		sales.setCustomersubContacts(salesdto.getCustomersubContacts());
		sales.setSalesItems(salesdto.getSalesItems());
		sales.setUpdateddate(LocalDate.now());
		sales.setUpdatedtime(LocalTime.now());
		sales.setUpdatedbyname(uname);
		sales.setIgst(salesdto.getIgst());
		sales.setCgst(salesdto.getCgst());
		sales.setSgst(salesdto.getSgst());
		
		sales.setUpdatedby(uid);
		sales.setUpdatedrole(role);

		sales.setVoucherid(salesdto.getVoucherid());
		sales.setVouchernumber(salesdto.getVouchernumber());
		sales.setVoucherseries(salesdto.getVoucherseries());
		sales.setVid(salesdto.getVid());

		sales.setVersion(salesdto.getVersion());

		if (sales.getDistributor() == null) {
			Retailer r = retailerRepo.findById(salesdto.getRetailer().getId()).get();

			sales.setDistributor(r.getDistributor());
		}

		List<SalesItems> items = mapper.readValue(salesitemsjson, new TypeReference<List<SalesItems>>() {
		});

		sales.setVersion("1.1");

		sales.setSalesItems(items);

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSlNo(i + 1);
			;
		}

		TranDtls dtls = mapper.readValue(tranDtls, new TypeReference<TranDtls>() {
		});
		salesdto.setTranDtls(dtls);
		sales.setTaxSch("GST");
		sales.setSupTyp(salesdto.getTranDtls().getSupTyp());

		sales.setEcmGstin(salesdto.getTranDtls().getEcmGstin());

//		List<SalesItems> items = mapper.readValue(salesitemsjson, new TypeReference<List<SalesItems>>() {
//		});
//		sales.setSalesItems(items);

//		TranDtls dtls = mapper.readValue(tranDtls, new TypeReference<TranDtls>() {
//		});

		DocDtls doc = mapper.readValue(docDtls, new TypeReference<DocDtls>() {

		});
		salesdto.setDocDtls(doc);
		sales.setTyp("INV");
		sales.setNo(salesdto.getDocDtls().getNo());
		sales.setDt(salesdto.getDocDtls().getDt());

//		salesdto.setTranDtls(dtls);
//		sales.setTaxSch(salesdto.getTranDtls().getTaxSch());
//		sales.setSupTyp(salesdto.getTranDtls().getSupTyp());
//		sales.setRegRev(salesdto.getTranDtls().getRegRev());
//		sales.setEcmGstin(salesdto.getTranDtls().getEcmGstin());
//		sales.setIgstOnIntra(salesdto.getTranDtls().getIgstOnIntra());

		System.out.println("param data:" + sales);

		SellerDtls seller = mapper.readValue(sellerDtls, new TypeReference<SellerDtls>() {

		});
		salesdto.setSellerDtls(seller);
		sales.setGstin(salesdto.getSellerDtls().getGstin());
		sales.setLglNm(salesdto.getSellerDtls().getLglNm());

		sales.setAddr1(salesdto.getSellerDtls().getAddr1());

		sales.setLoc(salesdto.getSellerDtls().getLoc());
		sales.setPin(salesdto.getSellerDtls().getPin());
		sales.setStcd(salesdto.getSellerDtls().getStcd());
		sales.setPh(salesdto.getSellerDtls().getPh());
		sales.setEm(salesdto.getSellerDtls().getEm());

		BuyerDtls buyer = mapper.readValue(buyerDtls, new TypeReference<BuyerDtls>() {

		});
		salesdto.setBuyerDtls(buyer);
		sales.setBgstin(salesdto.getBuyerDtls().getGstin());
		sales.setBlglNm(salesdto.getBuyerDtls().getLglNm());

		sales.setPos(salesdto.getBuyerDtls().getPos());
		sales.setBaddr1(salesdto.getBuyerDtls().getAddr1());

		sales.setBloc(salesdto.getBuyerDtls().getLoc());
		sales.setBpin(salesdto.getBuyerDtls().getPin());
		sales.setBstcd(salesdto.getBuyerDtls().getStcd());
		sales.setBph(salesdto.getBuyerDtls().getPh());
		sales.setBem(salesdto.getBuyerDtls().getEm());

//List<ItemList> item=mapper.readValue(itemList,new TypeReference <List<ItemList>>()
//		{
//
//		});
//salesdto.setItemList(item);
//sales.setSlNo(salesdto.getItemList().get(0).getSlNo());
//sales.setPrdDesc(salesdto.getItemList().get(0).getPrdDesc());
//sales.setIsServc(salesdto.getItemList().get(0).getIsServc());
//sales.setHsnCd(salesdto.getItemList().get(0).getHsnCd());
//
//sales.setQty(salesdto.getItemList().get(0).getQty());
//
//sales.setUnit(salesdto.getItemList().get(0).getUnit());
//sales.setUnitPrice(salesdto.getItemList().get(0).getUnitPrice());
//sales.setTotAmt(salesdto.getItemList().get(0).getTotAmt());
//sales.setDiscount(salesdto.getItemList().get(0).getDiscount());
//
//sales.setAssAmt(salesdto.getItemList().get(0).getAssAmt());
//sales.setGstRt(salesdto.getItemList().get(0).getGstRt());
//sales.setIgstAmt(salesdto.getItemList().get(0).getIgstAmt());
//sales.setCgstAmt(salesdto.getItemList().get(0).getCgstAmt());
//sales.setSgstAmt(salesdto.getItemList().get(0).getSgstAmt());
//sales.setCesRt(salesdto.getItemList().get(0).getCesRt());
//sales.setCesAmt(salesdto.getItemList().get(0).getCesAmt());
//sales.setCesNonAdvlAmt(salesdto.getItemList().get(0).getCesNonAdvlAmt());
//sales.setStateCesRt(salesdto.getItemList().get(0).getStateCesRt());
//sales.setStateCesAmt(salesdto.getItemList().get(0).getStateCesAmt());
//sales.setStateCesNonAdvlAmt(salesdto.getItemList().get(0).getStateCesNonAdvlAmt());
//sales.setOthChrg(salesdto.getItemList().get(0).getOthChrg());
//sales.setTotItemVal(salesdto.getItemList().get(0).getTotItemVal());

//		salesdto.setDocDtls(doc);
//		sales.setTyp(salesdto.getDocDtls().getTyp());
//		sales.setNo(salesdto.getDocDtls().getNo());
//		sales.setDt(salesdto.getDocDtls().getDt());

		ValDtls val = mapper.readValue(valDtls, new TypeReference<ValDtls>() {

		});

		salesdto.setValDtls(val);
		sales.setAssVal(salesdto.getValDtls().getAssVal());
		sales.setCgstVal(salesdto.getValDtls().getCgstVal());
		sales.setSgstVal(salesdto.getValDtls().getSgstVal());
		sales.setIgstVal(salesdto.getValDtls().getIgstVal());
		sales.setCesVal(salesdto.getValDtls().getCesVal());
		sales.setStCesVal(salesdto.getValDtls().getStCesVal());
//sales.setVdiscount(salesdto.getValDtls().getDiscount());
		sales.setVothChrg(salesdto.getValDtls().getOthChrg());

		sales.setTotInvVal(salesdto.getValDtls().getTotInvVal());

//		salesdto.setValDtls(val);
//		sales.setAssVal(salesdto.getValDtls().getAssVal());
//		sales.setCgstVal(salesdto.getValDtls().getCgstVal());
//		sales.setSgstVal(salesdto.getValDtls().getSgstVal());
//		sales.setIgstVal(salesdto.getValDtls().getIgstVal());
//		sales.setCesVal(salesdto.getValDtls().getCesVal());
//		sales.setStCesVal(salesdto.getValDtls().getStCesVal());
//		sales.setVdiscount(salesdto.getValDtls().getDiscount());
//		sales.setVothChrg(salesdto.getValDtls().getOthChrg());
//		sales.setRndOffAmt(salesdto.getValDtls().getRndOffAmt());
//		sales.setTotInvVal(salesdto.getValDtls().getTotInvVal());
//		sales.setTotInvValFc(salesdto.getValDtls().getTotInvValFc());
//>>>>>>> 9ed1d6cb8a811eb15cf5d9665903716707b2aded

		EwbDtls ewb = mapper.readValue(ewbDtls, new TypeReference<EwbDtls>() {

		});

		salesdto.setEwbDtls(ewb);
		sales.setDistance(salesdto.getEwbDtls().getDistance());
		sales.setTransMode(salesdto.getEwbDtls().getTransMode());
		sales.setTransporters(salesdto.getTransporters());
		sales.setTransName(salesdto.getEwbDtls().getTransName());
		sales.setTransDocDt(salesdto.getEwbDtls().getTransDocDt());
		sales.setTransDocNo(salesdto.getEwbDtls().getTransDocNo());
		sales.setVehNo(salesdto.getEwbDtls().getVehNo());
		sales.setVehType(salesdto.getEwbDtls().getVehType());

		Sales savedsales = salesRepo.save(sales);

		// --------- img

//		
		String imgfolder = "IMG_" + savedsales.getId() + imgfiles.getOriginalFilename();
		String pdffolder = "PDF_" + savedsales.getId() + pdffiles.getOriginalFilename();

		if (imgfiles.isEmpty() && pdffiles.isEmpty()) {
			try {

				Path imgtodeletepath = Paths.get(FILE_PATH + savedsales.getImglocation());
				Path pdftodeletepath = Paths.get(FILE_PATH + savedsales.getPdflocation());

//				try {
//					Files.delete(imgtodeletepath);
//					Files.delete(pdftodeletepath);
//				} catch (IOException e) {
//
//					e.printStackTrace();
//				}

			} catch (Exception e) {
				e.getStackTrace();
			}

			sales.setImgname(sales.getImgname());
			sales.setImglocation(sales.getImglocation());
			sales.setPdfname(sales.getPdfname());
			sales.setPdflocation(sales.getPdflocation());

			salesRepo.save(sales);
		}

		if (!imgfiles.isEmpty() && pdffiles.isEmpty()) {

			String imgfolder1 = "IMG_" + savedsales.getId() + imgfiles.getOriginalFilename();

			try {
				Path imgtodeletepath = Paths.get(FILE_PATH + savedsales.getImglocation());

//				if (Files.exists(imgtodeletepath)) {
//
//					Files.delete(imgtodeletepath);
//				}

			} catch (Exception e) {
				e.getStackTrace();
			}

			sales.setImgname(imgfiles.getOriginalFilename());
			sales.setImglocation(imgfolder);
			sales.setPdfname(sales.getPdfname());
			sales.setPdflocation(sales.getPdflocation());

			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder1),
					StandardCopyOption.REPLACE_EXISTING);

			salesRepo.save(sales);
		}
		if (imgfiles.isEmpty() && !pdffiles.isEmpty()) {

			String pdffolder1 = "PDF_" + savedsales.getId() + pdffiles.getOriginalFilename();

			try {

				Path pdftodeletepath = Paths.get(FILE_PATH + savedsales.getPdflocation());

//				if (Files.exists(pdftodeletepath)) {
//
//					Files.delete(pdftodeletepath);
//				}

			} catch (Exception e) {
				e.getStackTrace();
			}

			sales.setImgname(sales.getImgname());
			sales.setImglocation(sales.getImglocation());
			sales.setPdfname(pdffiles.getOriginalFilename());
			sales.setPdflocation(pdffolder);

			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder1),
					StandardCopyOption.REPLACE_EXISTING);

			System.out.println();

			salesRepo.save(sales);
		}
		if (!imgfiles.isEmpty() && !pdffiles.isEmpty()) {

			String imgfolder1 = "IMG_" + savedsales.getId() + imgfiles.getOriginalFilename();
			String pdffolder1 = "PDF_" + savedsales.getId() + pdffiles.getOriginalFilename();

			try {

				Path imgpath = Paths.get(FILE_PATH + savedsales.getImglocation());
				Path pdfpath = Paths.get(FILE_PATH + savedsales.getPdflocation());

//				Files.delete(imgpath);
//				Files.delete(pdfpath);

			} catch (Exception e) {
				e.getStackTrace();
			}

			sales.setImgname(imgfiles.getOriginalFilename());
			sales.setImglocation("invoiceAttachment/" + imgfolder1);
			sales.setPdfname(pdffiles.getOriginalFilename());
			sales.setPdflocation("invoiceAttachment/" + pdffolder1);

			Files.copy(imgfiles.getInputStream(), fileStorageLocation.resolve(imgfolder1),
					StandardCopyOption.REPLACE_EXISTING);
			Files.copy(pdffiles.getInputStream(), fileStorageLocation.resolve(pdffolder1),
					StandardCopyOption.REPLACE_EXISTING);

			salesRepo.save(sales);
		}

		salesRepo.save(sales);

		SalesDto salesDto2 = new SalesDto();
		salesDto2.setIrnno(savedsales.getIrnno());
		salesDto2.setAckdate(savedsales.getAckdate());
		salesDto2.setAckno(savedsales.getAckno());
		salesDto2.setDeliverynoteno(savedsales.getDeliverynoteno());
		salesDto2.setDestination(savedsales.getDestination());
		salesDto2.setPackaginglistno(savedsales.getPackaginglistno());
		salesDto2.setTaxamount(savedsales.getTaxamount());
		salesDto2.setShippingcharge(savedsales.getShippingcharge());
		salesDto2.setPanno(savedsales.getPanno());
		salesDto2.setGstno(savedsales.getGstno());
		salesDto2.setBuyerorderno(savedsales.getBuyerorderno());
		sales.setNetAmount(salesdto.getNetAmount());
		salesDto2.setPaymentTerms(savedsales.getPaymentTerms());
		salesDto2.setTotalnopkg(savedsales.getTotalnopkg());
		salesDto2.setLrno(savedsales.getLrno());
		salesDto2.setLrndate(savedsales.getLrndate());
		salesDto2.setEdd(savedsales.getEdd());
		salesDto2.setTermsofdelivery(savedsales.getTermsofdelivery());
		salesDto2.setEwaybillno(savedsales.getEwaybillno());
		salesDto2.setGrnno(savedsales.getGrnno());
		salesDto2.setGrndate(savedsales.getGrndate());
		salesDto2.setGrossamount(savedsales.getGrossamount());
		salesDto2.setInvoiceno(savedsales.getInvoiceno());
		salesDto2.setType(savedsales.getType());
		salesDto2.setStatus(savedsales.getStatus());
		salesDto2.setInvoice_status(savedsales.getInvoice_status());

		salesDto2.setTaxtype(savedsales.getTaxtype());
		salesDto2.setMsmeno(savedsales.getMsmeno());
		salesDto2.setUdyamno(savedsales.getUdyamno());
		salesDto2.setImglocation(savedsales.getImglocation());
		salesDto2.setImgname(savedsales.getImgname());

		salesDto2.setTaxtype(savedsales.getTaxtype());
		salesDto2.setMsmeno(savedsales.getMsmeno());
		salesDto2.setUdyamno(savedsales.getUdyamno());
		salesDto2.setRetailer(savedsales.getRetailer());

		salesDto2.setDispatchedthrough(savedsales.getDispatchedthrough());
		salesDto2.setInvoicedate(savedsales.getInvoicedate());
		salesDto2.setWarehouse(savedsales.getWarehouse());
		salesDto2.setDistributor(savedsales.getDistributor());
		salesDto2.setDeliveryAddress(savedsales.getDeliveryAddress());
		salesDto2.setBranch(savedsales.getBranch());
		salesDto2.setCustomersubContacts(savedsales.getCustomersubContacts());
		salesDto2.setSalesItems(savedsales.getSalesItems());

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long loggeduser = userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();

		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setSalesid((long) savedsales.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

		return salesDto2;
	}

	@Override
	public Map<String, Object> IndexSalesAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		List<SalesProjection> ipo = salesRepo.indexSales(p);

//		response.put("Index", ipo);
//		response.put("CountPages", countpages);
//		response.put("Pages", pages);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		long id = userDetails.getId();

		int uid = (int) (long) id;

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			System.out.println(s + "string rolessssssssssss");
			if (s.equals("ROLE_ADMIN") || s.equals("ROLE_NSM")) {
				long countpages = salesRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

//				List<SalesProjection> ipo = salesRepo.indexSales(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = salesRepo.indexSales(uid).size();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
//				List<SalesProjection> ipo = salesRepo.indexSales(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				long countpages = salesRepo.indexSalesRsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo1 = salesRepo.indexSalesRsm(p,uid);

				response.put("Index", ipo1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = salesRepo.indexSalesAsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo1 = salesRepo.indexSalesAsm(p,uid);

				response.put("Index", ipo1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				long countpages = salesRepo.indexSalesAse(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo1 = salesRepo.indexSalesAse(p,uid);

				response.put("Index", ipo1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			
			
			
		}

		return null;
	}

	@Override
	public Map<String, Object> IndexSalesDesc(int pageno, int pagesize, String field) {

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
				long countpages = salesRepo.count();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo = salesRepo.indexSales(p);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;

			} else if (s.equals("ROLE_DISTRIBUTOR")) {
				long countpages = salesRepo.indexSales(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				List<SalesProjection> ipo = salesRepo.indexSales(p, uid);

				response.put("Index", ipo);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				long countpages = salesRepo.indexSalesRsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo1 = salesRepo.indexSalesRsm(p,uid);

				response.put("Index", ipo1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				long countpages = salesRepo.indexSalesAsm(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo1 = salesRepo.indexSalesAsm(p,uid);

				response.put("Index", ipo1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				long countpages = salesRepo.indexSalesAse(uid).size();
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<SalesProjection> ipo1 = salesRepo.indexSalesAse(p,uid);

				response.put("Index", ipo1);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}

		return null;

	}

//	@Override
//	public List<SalesProjection> IndexSalesSearch(int pageno, int pagesize, String search) {
//		// TODO Auto-generated method stub
//		Pageable p=PageRequest.of(pageno, pagesize);
//		ModelMapper modelMapper=new ModelMapper();
//		List<SalesProjection> sales=salesRepo.SearchBySales(search, p)
//				.stream().map(salesProjection -> modelMapper.map(salesProjection, SalesProjection.class))
//				.collect(Collectors.toList());	
//		return sales;
//	}

	public Map<String, Object> SearchSales(int pageno, int pagesize, String search) {

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

				List<SalesProjection> sales = salesRepo.SearchBySales(search, p);

				long searchcount = sales.size();

				response.put("data", sales);
				response.put("SearchCount", searchcount);

				return response;
			} else if (s.equals("ROLE_DISTRIBUTOR")) {

				List<SalesProjection> sales = salesRepo.SearchBySales(uid, search, p);
				long searchcount = sales.size();

				response.put("data", sales);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_RSM")) {

				List<SalesProjection> sales = salesRepo.SearchBySalesRsm(uid, search, p);
				long searchcount = sales.size();

				response.put("data", sales);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_ASM")) {

				List<SalesProjection> sales = salesRepo.SearchBySalesAsm(uid, search, p);
				long searchcount = sales.size();

				response.put("data", sales);
				response.put("SearchCount", searchcount);

				return response;
			}
			
			else if (s.equals("ROLE_ASE")) {

				List<SalesProjection> sales = salesRepo.SearchBySalesAse(uid, search, p);
				long searchcount = sales.size();

				response.put("data", sales);
				response.put("SearchCount", searchcount);

				return response;
			}
		}
		return null;
	}

	// Transport Details Update Report

	@Override
	public Map<String, Object> transportReportAsc(String start_date, String end_date, int pageno, int pagesize,
			String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int did = (int) (long) rid;
		

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		
		
		for (String s : list) {
			
			
			if(s.equals("ROLE_ADMIN"))
			{
				long countpages = salesRepo.count();
				
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				
			List<TransportDetailUpdateReportProjection> detailUpdateReportProjections=salesRepo.transportReportAdmin(start_date,end_date, p);
			
			response.put("Index", detailUpdateReportProjections);
			response.put("Enteries", countpages);
			response.put("Pages", pages);

			return response;
			}
			
			
			if(s.equals("ROLE_DISTRIBUTOR"))
			{
		long countpages = salesRepo.transportReport(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReport(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			if(s.equals("ROLE_NSM"))
			{
				long countpages = salesRepo.count();
				
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				
			List<TransportDetailUpdateReportProjection> detailUpdateReportProjections=salesRepo.transportReportAdmin(start_date,end_date, p);
			
			response.put("Index", detailUpdateReportProjections);
			response.put("Enteries", countpages);
			response.put("Pages", pages);

			return response;
			}
			
			
			
			if(s.equals("ROLE_RSM"))
			{
		long countpages = salesRepo.transportReportRsm(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReportRsm(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			if(s.equals("ROLE_ASM"))
			{
		long countpages = salesRepo.transportReportAsm(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReportAsm(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			if(s.equals("ROLE_ASE"))
			{
		long countpages = salesRepo.transportReportAse(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReportAse(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			
			
			
			
			}
		return null;
	}

	@Override
	public Map<String, Object> transportReportDesc(String start_date, String end_date, int pageno, int pagesize,
			String field) {

		Map<String, Object> response = new HashMap<>();

		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);
//
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Long rid = userDetails.getId();
		int did = (int) (long) rid;
		

		List<String> list = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		
		
		for (String s : list) {
			
			
			if(s.equals("ROLE_ADMIN"))
			{
				long countpages = salesRepo.count();
				
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				
			List<TransportDetailUpdateReportProjection> detailUpdateReportProjections=salesRepo.transportReportAdmin(start_date,end_date, p);
			
			response.put("Index", detailUpdateReportProjections);
			response.put("Enteries", countpages);
			response.put("Pages", pages);

			return response;
			}
			
			
			if(s.equals("ROLE_DISTRIBUTOR"))
			{
		long countpages = salesRepo.transportReport(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReport(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			if(s.equals("ROLE_NSM"))
			{
				long countpages = salesRepo.count();
				
				long pages = countpages / pagesize;

				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}
				
			List<TransportDetailUpdateReportProjection> detailUpdateReportProjections=salesRepo.transportReportAdmin(start_date,end_date, p);
			
			response.put("Index", detailUpdateReportProjections);
			response.put("Enteries", countpages);
			response.put("Pages", pages);

			return response;
			}
			
			
			
			if(s.equals("ROLE_RSM"))
			{
		long countpages = salesRepo.transportReportRsm(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReportRsm(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			if(s.equals("ROLE_ASM"))
			{
		long countpages = salesRepo.transportReportAsm(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReportAsm(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			if(s.equals("ROLE_ASE"))
			{
		long countpages = salesRepo.transportReportAse(start_date, end_date, did).size();
		long pages = countpages / pagesize;
		
		long rem = countpages % pagesize;
		if (rem > 0) {
			pages++;
		}

		List<TransportDetailUpdateReportProjection> transportReport = salesRepo.transportReportAse(start_date, end_date,
				p,did);

		response.put("Index", transportReport);
		response.put("Enteries", countpages);
		response.put("Pages", pages);

		return response;
			}
			
			
			
			
			
			
			}
		return null;
	}

	@Override
	public String generateVoucher(NewVoucherName newVoucherName) {

		if (salesRepo.searchByVoucher(newVoucherName.getName()) != null) {

			Sales searchByVoucher = salesRepo.searchByVoucher(newVoucherName.getName());

			long newNum = searchByVoucher.getVouchernumber();
			String newName = newVoucherName.getName() + ++newNum;
			System.out.println(newName + "==========================================" + " new name is this");
			return newName;

		} else {
			int num = 1;
			String newname = newVoucherName.getName() + num;
			return newname;
		}
	}

	public String generateVoucher1(String newVoucherName) {

		if (salesRepo.searchByVoucher(newVoucherName) != null) {

			Sales searchByVoucher = salesRepo.searchByVoucher(newVoucherName);

			long newNum = searchByVoucher.getVouchernumber();
			String newName = newVoucherName + ++newNum;
			System.out.println(newName + "==========================================" + " new name is this");
			return newName;

		} else {
			int num = 1;
			String newname = newVoucherName + num;
			return newname;
		}
	}

	public List<SalesItems> getAllItems() {
		List<SalesItems> items = salesItemsRepo.findAll();

		// Assign serial numbers (assuming items are already sorted as needed)
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSlNo(i + 1);
		}

		return items;
	}

	@Override
	public Sales saveSales(SalesDto salesdto, String salesitemsjson, String tranDtls, String docDtls, String sellerDtls,
			String buyerDtls, String itemList, String valDtls, String ewbDtls, MultipartFile imgfiles,
			MultipartFile pdffiles) throws IOException {

		return null;
	}
	

	
	@Override
	public List<SalesReportDto> salesByRetailerListAndDistList(List<Long> retids, List<Long> distids, String fromdate,
			String todate) {
		
		List<SalesReportDto> findAllSalesWithRandDandwId = salesRepo.findAllSalesWithRandDandwId(retids, distids, fromdate, todate);
		
		
 		return findAllSalesWithRandDandwId;
	}

	@Override
	public List<ClosingStockReport> closingStockByWidFdateTdate(String fromdate, String todate, String wid, Long pid) {
		
		List<ClosingStockReport> findAllClosingStockOfProduct = salesRepo.findAllClosingStockOfProduct(fromdate, todate, wid, pid);
		
		return findAllClosingStockOfProduct;
		
	}
	

}
