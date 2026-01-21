package com.SCM.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexSalesExpense;
import com.SCM.IndexDto.SalesExpenseReport;
import com.SCM.IndexDto.SalesExpenseReportDTO;
import com.SCM.IndexDto.SalesExpenseReportItems;
import com.SCM.IndexDto.SalesExpenseReportItemsDTO;
import com.SCM.dto.SalesExpenseDTO;
import com.SCM.model.SalesExpense;
import com.SCM.model.SalesExpenseImages;
import com.SCM.model.SalesExpenseItems;
import com.SCM.payload.MessageResponse;
import com.SCM.repository.SalesExpItemsRepo;
import com.SCM.repository.SalesExpRepo;
import com.SCM.repository.SalesExpenseImageRepo;
import com.SCM.service.SalesExpenseService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesExpenseServiceImpl implements SalesExpenseService {

	@Autowired
	private SalesExpRepo salesExpRepo;

	@Autowired
	private SalesExpenseImageRepo salesExpenseImageRepo;
	
	@Autowired
	private SalesExpItemsRepo salesExpItemsRepo;

	@Autowired
	private ObjectMapper mapper;

//	private static final String FOLDER_PATH = "D:\\ImgPath/";

	@Value("${FILE_PATH}")
	private String FILE_PATH;

	private final Path fileStorageLocation;

	public SalesExpenseServiceImpl(Environment env) throws IOException {
		this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir4", "/SalesAgent/")).toAbsolutePath().normalize();
		Files.createDirectories(this.fileStorageLocation);
	}

	
	@Override
	public SalesExpense save1(SalesExpenseDTO salesExpenseDTO, String salesexpenseitems, MultipartFile[] salesexpfile) throws IOException {
	    
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> list = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
	    SalesExpense se = new SalesExpense();
	    se.setExpdate(salesExpenseDTO.getExpdate());
	    se.setDailyallownces(salesExpenseDTO.getDailyallownces());
	    se.setHometown(salesExpenseDTO.getHometown());
	    se.setTotalexp(salesExpenseDTO.getTotalexp());
	    se.setAllowancestatus(salesExpenseDTO.getAllowancestatus());
	    se.setAdminRemarks(salesExpenseDTO.getAdminRemarks());
	    se.setRsmRemarks(salesExpenseDTO.getRsmRemarks());
	    se.setStaffname(salesExpenseDTO.getStaffname());
	    se.setDesignation(salesExpenseDTO.getDesignation());
	    se.setStaffid(salesExpenseDTO.getStaffid());
	    se.setTravelfaretotal(salesExpenseDTO.getTravelfaretotal());
	    se.setOtherexpamounttotal(salesExpenseDTO.getOtherexpamounttotal());
	    se.setStatus("Pending");
	    
	    for(String s : list)
	    {
	    	if(s.equals("ROLE_RSM"))
	    	{
	    		se.setCreatebyrsmname(userDetailsImpl.getUsername());
	    		se.setCreatedbyrsmid(userDetailsImpl.getId());
	    	}
	    	else if(s.equals("ROLE_ADMIN"))
	    	{
	    		se.setCreatebyadminname(userDetailsImpl.getUsername());
	    		se.setCreatedbyadminid(userDetailsImpl.getId());
	    	}
	    }
	    

	    List<SalesExpenseItems> salesitemslist = mapper.readValue(salesexpenseitems, new TypeReference<List<SalesExpenseItems>>() {});
	    se.setSalesExpenseItems(salesitemslist);
	    
	    for(SalesExpenseItems item:salesitemslist)
	    {
	    	item.setAdminstatus("false");
	    	item.setRsmstatus("false");
	    }

	    SalesExpense savedExpense = salesExpRepo.save(se);
	    
	    if (salesexpfile != null && salesexpfile.length > 0) {
	    	
	        for (int i = 0; i < salesitemslist.size(); i++) {
	        	
	            if (i < salesexpfile.length) {
	                MultipartFile file = salesexpfile[i];
	                String originalFilename = file.getOriginalFilename();
	                String filepath = "_exp_" + savedExpense.getId() + "_" + originalFilename;
	                
	                SalesExpenseImages image = new SalesExpenseImages();
	                image.setSalesexpfilename(originalFilename);
	                image.setSalesexpfilelocation("SalesAgent/" + filepath);
	
	                SalesExpenseItems salesItem = salesitemslist.get(i);
	                image.setSalesExpenseItems(salesItem);
	  
	                salesExpenseImageRepo.save(image);
	                
	                System.out.println("Saving file to: " + fileStorageLocation.resolve(filepath));
	                
	                try {
	                    Files.copy(file.getInputStream(), fileStorageLocation.resolve(filepath), StandardCopyOption.REPLACE_EXISTING);
	                } catch (IOException e) {
	                    throw new IOException("Error saving file: " + originalFilename, e);
	                }
	            }
	        }
	    }

	    return savedExpense;
	}
	
	
	@Override
	public SalesExpense get(int id) {
		SalesExpense salesExpense = salesExpRepo.findById(id).get();
		return salesExpense;
	}


	
	@Override
	public ResponseEntity<Object> update1(int id, SalesExpenseDTO salesExpenseDTO, String salesexpenseitems, MultipartFile[] salesexpfile) throws IOException {
	    
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> list = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
	    SalesExpense se = salesExpRepo.findById(id).get();
	    se.setExpdate(salesExpenseDTO.getExpdate());
	    se.setDailyallownces(salesExpenseDTO.getDailyallownces());
	    se.setHometown(salesExpenseDTO.getHometown());
	    se.setTotalexp(salesExpenseDTO.getTotalexp());
	    se.setAllowancestatus(salesExpenseDTO.getAllowancestatus());
	    se.setAdminRemarks(salesExpenseDTO.getAdminRemarks());
	    se.setRsmRemarks(salesExpenseDTO.getRsmRemarks());
	    se.setStaffname(salesExpenseDTO.getStaffname());
	    se.setDesignation(salesExpenseDTO.getDesignation());
	    se.setStaffid(salesExpenseDTO.getStaffid());
	    se.setTravelfaretotal(salesExpenseDTO.getTravelfaretotal());
	    se.setOtherexpamounttotal(salesExpenseDTO.getOtherexpamounttotal());
	    se.setStatus("pending");

	    for(String s : list)
	    {
	    	if(s.equals("ROLE_RSM"))
	    	{
	    		se.setCreatebyrsmname(userDetailsImpl.getUsername());
	    		se.setCreatedbyrsmid(userDetailsImpl.getId());
	    	}
	    	else if(s.equals("ROLE_ADMIN"))
	    	{
	    		se.setCreatebyadminname(userDetailsImpl.getUsername());
	    		se.setCreatedbyadminid(userDetailsImpl.getId());
	    	}
	    }
	    
	    List<SalesExpenseItems> salesitemslist = mapper.readValue(salesexpenseitems, new TypeReference<List<SalesExpenseItems>>() {});
	   
	    System.out.println(salesitemslist);
	    
	    se.setSalesExpenseItems(salesitemslist);
	    
	   
	    
   for (SalesExpenseItems item : salesitemslist) {
	    	
	        if (item.getApprovalbyrsm() != null) {
	 
	            se.setStatus("RsmComplete");
	            break;  
	        }
	    }
   
   for (SalesExpenseItems item : salesitemslist) {
   	
       if (item.getApprovalbyadmin()!=null) {

           se.setStatus("Complete");
           break;  
       }
   }
	    
	    
	    
	    try {
	    for(SalesExpenseItems item:salesitemslist)
	    {
	    	
//	    	item.setAdminstatus("false");
//	    	item.setRsmstatus("false");
	        for(String s : list)
		    {
	        	
		    if(s.equals("ROLE_ADMIN"))
		    {
	    	if(item.getApprovalbyadmin()!=null && item.getApprovedexpensebyadmin()!=0)
	    	{
	    		item.setAdminstatus("true");
	    	}
//	    	else
//	    	{
//	    		item.setAdminstatus("false");
//	    	}
		    }
		    }
	        
	        
	        for(String s : list)
		    {
	        	
		    if(s.equals("ROLE_RSM"))
		    {
	        
	    	if(item.getApprovalbyrsm()!=null && item.getApprovedexpensebyrsm()!=0)
	    	{
	    		item.setRsmstatus("true");
	    	}
//	    	else
//	    	{
//	    		item.setRsmstatus("false");
//	    	}
		    }
		    }
	    }
	    }
	    catch(NullPointerException e)
	    {
	    	e.printStackTrace();
	    }
	   
	    
	    
	    
	    
	    
	    try {
	    
	    for (String s : list) {
//	        if (s.equals("ROLE_ASE")) {
//
//	            SalesExpenseItems bySalesexpId = salesExpItemsRepo.findBySalesexpId(se.getId());
//	            
//	            if (bySalesexpId != null && 
//	                bySalesexpId.getApprovalbyadmin().equals("Accept") ||
//	                bySalesexpId.getApprovalbyrsm().equals("Accept") || bySalesexpId.getApprovalbyadmin().equals("Reject") || bySalesexpId.getApprovalbyrsm().equals("Reject") || bySalesexpId.getApprovalbyadmin()==null || bySalesexpId.getApprovalbyrsm()==null) {
//	                
////	            throw new IllegalStateException("ASE cannot update the sales expense because it has already been approved or reject by both admin and RSM.");
//	            
//	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("ASE cannot update the sales expense because it has already been approved or reject by both admin and RSM"));
//	            
//	            }
//	    	
//
//	    	
//	        }
	    	
	    	if (s.equals("ROLE_ASE")) {
	    	    List<SalesExpenseItems> bySalesexpId = salesExpItemsRepo.findBySalesexpId(se.getId());
	    	    
	    	    for(SalesExpenseItems expense:bySalesexpId)
	    	    {
	    	    
	    	    if (bySalesexpId != null) {
	    	 
	    	        boolean isAdminApprovedOrRejected = 
	    	            (expense.getApprovalbyadmin() != null && 
	    	             (expense.getApprovalbyadmin().equals("Accept") || 
	    	            		 expense.getApprovalbyadmin().equals("Reject")));
	    	        
	    	        boolean isRsmApprovedOrRejected = 
	    	            (expense.getApprovalbyrsm() != null && 
	    	             (expense.getApprovalbyrsm().equals("Accept") || 
	    	            		 expense.getApprovalbyrsm().equals("Reject")));

	    	      
	    	        if ((expense.getApprovalbyadmin() == null && isRsmApprovedOrRejected) || 
	    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
	    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be edited"));
	    	        }
	    	        
	    	        if ((expense.getApprovalbyrsm() == null && isAdminApprovedOrRejected) || 
		    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
		    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
		    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be edited"));
		    	        }
	    	        
	    	        
	    	    }
	    	}
	    	}

	    	
	    	if(s.equals("ROLE_RSM"))
	    	{
	    		List<SalesExpenseItems> expenseItems= salesExpItemsRepo.findBySalesexpId(se.getId());
	    		
	    	    for(SalesExpenseItems expense:expenseItems)
	    	    {
	    		
	    		if(expenseItems != null && expense.getApprovalbyadmin().equals("Accept") || expense.getApprovalbyadmin().equals("Reject"))
	    		{
	    		    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Approved or Rejected expense cannot be edited"));
	    		}
	    	}
	    	}
	        if (s.equals("ROLE_ASM")) {
	        	  List<SalesExpenseItems> bySalesexpId = salesExpItemsRepo.findBySalesexpId(se.getId());
	        	  
	      	    for(SalesExpenseItems expense:bySalesexpId)
	    	    {
		    	    
		    	    if (bySalesexpId != null) {
		    	 
		    	        boolean isAdminApprovedOrRejected = 
		    	            (expense.getApprovalbyadmin() != null && 
		    	             (expense.getApprovalbyadmin().equals("Accept") || 
		    	            		 expense.getApprovalbyadmin().equals("Reject")));
		    	        
		    	        boolean isRsmApprovedOrRejected = 
		    	            (expense.getApprovalbyrsm() != null && 
		    	             (expense.getApprovalbyrsm().equals("Accept") || 
		    	            		 expense.getApprovalbyrsm().equals("Reject")));

		    	      
		    	        if ((expense.getApprovalbyadmin() == null && isRsmApprovedOrRejected) || 
		    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
		    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
		    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be edited"));
		    	        }
		    	        
		    	        if ((expense.getApprovalbyrsm() == null && isAdminApprovedOrRejected) || 
			    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
			    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
			    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be edited"));
			    	        }
		    	    }
	        }
	        }
	    
	    }
	    }
	    catch(NullPointerException e)
	    {
	    	e.printStackTrace();
	    }
 
	    
   SalesExpense savedExpense = salesExpRepo.save(se);
   
   
   if (salesexpfile != null && salesexpfile.length > 0) {
	        
	        for (int i = 0; i < salesitemslist.size(); i++) {
	            
	            if (i < salesexpfile.length) {
	                MultipartFile file = salesexpfile[i];
	                String originalFilename = file.getOriginalFilename();
	                String filepath = "_exp_" + savedExpense.getId() + "_" + originalFilename;

	                List<SalesExpenseImages> existingImages = salesExpenseImageRepo.findBySalesExpenseItems(salesitemslist.get(i));
	                
	                if (!file.isEmpty()) {  
	             
	                    if (!existingImages.isEmpty()) {
	                        SalesExpenseImages existingImage = existingImages.get(0);
	                        String oldFilePath = existingImage.getSalesexpfilelocation();

	                        Path path = Paths.get(FILE_PATH + oldFilePath);
	                        
	                        try {
	                            boolean isDeleted = Files.deleteIfExists(path);
	                            if (isDeleted) {
	                                
	                            } else {
	                                
	                            }
	                        } catch (IOException e) {
	                            throw new IOException("Error deleting old image: " + oldFilePath, e);
	                        }

	                        salesExpenseImageRepo.delete(existingImage);
	                    }

	                    // Save the new image
	                    SalesExpenseImages newImage = new SalesExpenseImages();
	                    newImage.setSalesexpfilename(originalFilename);
	                    newImage.setSalesexpfilelocation("SalesAgent/" + filepath);

	                    SalesExpenseItems salesItem = salesitemslist.get(i);
	                    newImage.setSalesExpenseItems(salesItem);

	                    salesExpenseImageRepo.save(newImage);

	                    try {
	                        Files.copy(file.getInputStream(), fileStorageLocation.resolve(filepath), StandardCopyOption.REPLACE_EXISTING);
	                    } catch (IOException e) {
	                        throw new IOException("Error saving file: " + originalFilename, e);
	                    }
	                } else {
	                    
	                }
	            }
	        }
	    }
	    

	    

	    return ResponseEntity.ok(savedExpense);
	}


	
	
	
	@Override
	public ResponseEntity<Object> deletesalesexp(int id) throws IOException {

 salesExpRepo.updatesalesexpitems(id);

		SalesExpenseImages salesExpenseImages = salesExpenseImageRepo.findById(id).orElse(null);

		if (salesExpenseImages != null) {

			String salesexpfilelocation = salesExpenseImages.getSalesexpfilelocation();

			if (salesexpfilelocation != null && !salesexpfilelocation.isEmpty()) {

				Path path = Paths.get(FILE_PATH + salesexpfilelocation);

				if (Files.exists(path)) {

					Files.delete(path);
					System.out.println("File deleted successfully: " + path);
				} else {
					System.out.println("File does not exist: " + path);
				}
			}
		}
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<String> list = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
		
		 SalesExpense se = salesExpRepo.findById(id).get();
		 
		 try {
		 
		  for (String s : list) {
		    	if (s.equals("ROLE_ASE")) {
		    	    List<SalesExpenseItems> bySalesexpId = salesExpItemsRepo.findBySalesexpId(se.getId());
		    	    
		    	    for(SalesExpenseItems expense:bySalesexpId)
		    	    {
		    	    
		    	    if (bySalesexpId != null) {
		    	 
		    	        boolean isAdminApprovedOrRejected = 
		    	            (expense.getApprovalbyadmin() != null && 
		    	             (expense.getApprovalbyadmin().equals("Accept") || 
		    	            		 expense.getApprovalbyadmin().equals("Reject")));
		    	        
		    	        boolean isRsmApprovedOrRejected = 
		    	            (expense.getApprovalbyrsm() != null && 
		    	             (expense.getApprovalbyrsm().equals("Accept") || 
		    	            		 expense.getApprovalbyrsm().equals("Reject")));

		    	      
		    	        if ((expense.getApprovalbyadmin() == null && isRsmApprovedOrRejected) || 
		    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
		    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
		    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be deleted"));
		    	        }
		    	        
		    	        if ((expense.getApprovalbyrsm() == null && isAdminApprovedOrRejected) || 
			    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
			    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
			    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be deleted"));
			    	        }
		    	    }
		    	}
		    	}
		    	
		       	if(s.equals("ROLE_RSM"))
		    	{
		    		List<SalesExpenseItems> expenseItems= salesExpItemsRepo.findBySalesexpId(se.getId());
		    		
		    	    for(SalesExpenseItems expense:expenseItems)
		    	    {
		    		
		    		if(expenseItems != null && expense.getApprovalbyadmin().equals("Accept") || expense.getApprovalbyadmin().equals("Reject"))
		    		{
		    		    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Approved or Rejected expense cannot be deleted"));
		    		}
		    	}
		    	}
		        
		        if (s.equals("ROLE_ASM")) {
		        	  List<SalesExpenseItems> bySalesexpId = salesExpItemsRepo.findBySalesexpId(se.getId());
		        	  
		      	    for(SalesExpenseItems expense:bySalesexpId)
		    	    {
			    	    
			    	    if (bySalesexpId != null) {
			    	 
			    	        boolean isAdminApprovedOrRejected = 
			    	            (expense.getApprovalbyadmin() != null && 
			    	             (expense.getApprovalbyadmin().equals("Accept") || 
			    	            		 expense.getApprovalbyadmin().equals("Reject")));
			    	        
			    	        boolean isRsmApprovedOrRejected = 
			    	            (expense.getApprovalbyrsm() != null && 
			    	             (expense.getApprovalbyrsm().equals("Accept") || 
			    	            		 expense.getApprovalbyrsm().equals("Reject")));

			    	      
			    	        if ((expense.getApprovalbyadmin() == null && isRsmApprovedOrRejected) || 
			    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
			    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
			    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be deleted"));
			    	        }
			    	        
			    	        if ((expense.getApprovalbyrsm() == null && isAdminApprovedOrRejected) || 
				    	            (isAdminApprovedOrRejected || isRsmApprovedOrRejected)) {
				    	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
				    	                                 .body(new MessageResponse("Approved or Rejected expense cannot be deleted"));
				    	        }
			    	    }
		        }
		        }
		  }
		  }
		 
		 catch(NullPointerException e)
		 {
			 e.printStackTrace();
		 }
		salesExpRepo.deletesalesexpimage(id);
		salesExpRepo.deleteById(id);
		return ResponseEntity.ok().body(new MessageResponse("Delete successfully"));
	}

	
	@Override
	public Map<String, Object> IndexSalesExpAsc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.ASC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long id = userDetailsImpl.getId();

		List<String> list = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesexp(p);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_RSM")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesASEandASMByRSMid(p, id);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_ASM")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesExpenseByASM(p, id);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_ASE")) {
				
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesExpenseByASE(p, id);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> IndexSalesExpDesc(int pageno, int pagesize, String field) {

		Map<String, Object> response = new HashMap<>();
		Sort sort = Sort.by(Sort.Direction.DESC, field);
		Pageable p = PageRequest.of(pageno, pagesize, sort);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long id = userDetailsImpl.getId();

		List<String> list = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		for (String s : list) {
			if (s.equals("ROLE_ADMIN")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesexp(p);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_RSM")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesASEandASMByRSMid(p, id);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_ASM")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesExpenseByASM(p, id);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			} else if (s.equals("ROLE_ASE")) {
				long countpages = salesExpRepo.count();
				long pages = countpages / pagesize;
				long rem = countpages % pagesize;
				if (rem > 0) {
					pages++;
				}

				List<IndexSalesExpense> indexsalesexp = salesExpRepo.indexsalesExpenseByASE(p, id);

				response.put("Index", indexsalesexp);
				response.put("Enteries", countpages);
				response.put("Pages", pages);

				return response;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> SearchSalesExp(int pageno, int pagesize, String search) {

		Map<String, Object> response = new HashMap<>();
		Pageable p = PageRequest.of(pageno, pagesize);

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long id = userDetailsImpl.getId();
		List<String> list = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

		for (String s : list) {
			
			if (s.equals("ROLE_ADMIN")) {
				
				List<IndexSalesExpense> searchBysalesexp = salesExpRepo.SearchBysalesexp(search, p);

				long searchcount = searchBysalesexp.size();

				response.put("data", searchBysalesexp);
				response.put("SearchCount", searchcount);

				return response;
				
			} else if (s.equals("ROLE_RSM")) {
				List<IndexSalesExpense> searchBysalesexp = salesExpRepo.SearchBysalesexpRSM(id, search, p);

				long searchcount = searchBysalesexp.size();

				response.put("data", searchBysalesexp);
				response.put("SearchCount", searchcount);

				return response;
			} else if (s.equals("ROLE_ASM")) {
				List<IndexSalesExpense> searchBysalesexp = salesExpRepo.SearchBysalesexpASM(id, search, p);

				long searchcount = searchBysalesexp.size();

				response.put("data", searchBysalesexp);
				response.put("SearchCount", searchcount);

				return response;
			} else if (s.equals("ROLE_ASE")) {
				List<IndexSalesExpense> searchBysalesexp = salesExpRepo.SearchBysalesexpASE(id, search, p);

				long searchcount = searchBysalesexp.size();

				response.put("data", searchBysalesexp);
				response.put("SearchCount", searchcount);

				return response;
			}
		}
		return response;
	}


	
	
	
	
	
//	---------------------------- sales expense report
	
	
	@Override
	public List<SalesExpenseReportDTO> salesexpreport(int sid,String fromdate,String todate) {
		
		List<SalesExpenseReport> salesexpensereport = salesExpRepo.salesexpensereport(sid,fromdate,todate);
		
	    List<SalesExpenseReportDTO> list = new ArrayList<SalesExpenseReportDTO>();
    
		salesexpensereport.forEach(i -> {
	
			SalesExpenseReportDTO expenseReportDTO = new SalesExpenseReportDTO();
			
			List<SalesExpenseReportItemsDTO> list2 = new ArrayList<SalesExpenseReportItemsDTO>();
			
			expenseReportDTO.setId(i.getId());
			expenseReportDTO.setStaff_name(i.getstaff_name());
			expenseReportDTO.setExpdate(i.getexpdate());
			expenseReportDTO.setHometown(i.gethometown());			
			expenseReportDTO.setDailyallownces(i.getdailyallownces());
			expenseReportDTO.setTotalexp(i.gettotalexp());
		    expenseReportDTO.setCountvisit(Optional.ofNullable(i.getcountvisit()).orElse(0.0));
		    expenseReportDTO.setStatus(i.getStatus());
		    expenseReportDTO.setDaytotalexpense(i.getdailyallownces()+i.gettotalexp());
expenseReportDTO.setTotalcountvisit(i.gettotalcountvisit());
expenseReportDTO.setTotalsecondaryvalue(i.gettotalsecondaryvalue());
//		    if (i.getsecondaryordervalue() == null) {
//	            throw new IllegalArgumentException("Secondary order value is null for sales expense report ID: " + i.getId());
//	        }
		    
		    expenseReportDTO.setSecondaryordervalue(Optional.ofNullable(i.getsecondaryordervalue()).orElse(0.0));
		    
//		    expenseReportDTO.setSecondaryordervalue(i.getsecondaryordervalue());
		    expenseReportDTO.setDesignation(i.getdesignation());
		    
		

			List<SalesExpenseReportItems> salesexpensereportItems = salesExpRepo.salesexpensereportItems(expenseReportDTO.getId());
			
			double sumdailyallowance = salesexpensereport.stream().mapToDouble(SalesExpenseReport::getdailyallownces).sum();
			double sumtotalexpense = salesexpensereport.stream().mapToDouble(SalesExpenseReport::gettotalexp).sum();
//			double sumcountvisit = salesexpensereport.stream().mapToDouble(SalesExpenseReport::getcountvisit).sum();
//			double sumcountvisit = salesexpensereport.stream()
//				    .mapToDouble(j -> i.getcountvisit() == null ? 0.0 : i.getcountvisit())  // Handle null directly
//				    .sum();
			
			double sumcountvisit = salesexpensereport.stream()
				    .mapToDouble(j -> j.getcountvisit() == null ? 0.0 : j.getcountvisit())  // ✅ Correct
				    .sum();

//			double sumsecondaryvalue = salesexpensereport.stream().mapToDouble(SalesExpenseReport::getsecondaryordervalue).sum();
			
//			double sumsecondaryvalue = salesexpensereport.stream().mapToDouble(k -> i.getsecondaryordervalue() == null ? 0.0 : i.getsecondaryordervalue()).sum();
			
			double sumsecondaryvalue = salesexpensereport.stream()
				    .mapToDouble(k -> k.getsecondaryordervalue() == null ? 0.0 : k.getsecondaryordervalue())
				    .sum();

            double sumfaretotal = salesexpensereportItems.stream().mapToDouble(SalesExpenseReportItems::gettravelfare).sum();
            double sumotherexptotal = salesexpensereportItems.stream().mapToDouble(SalesExpenseReportItems::getotherexpamount).sum();
            double sumaccepted = salesexpensereportItems.stream().mapToDouble(SalesExpenseReportItems::getapprovedexpensebyadmin).sum();
            double sumrsmaccepted=salesexpensereportItems.stream().mapToDouble(SalesExpenseReportItems::getapprovedexpensebyrsm).sum();
            
            
        
            expenseReportDTO.setGrandtotalfare(sumfaretotal);
			expenseReportDTO.setGrandtotaldailyallowances(sumdailyallowance);
			expenseReportDTO.setGrandtotalotherexpense(sumotherexptotal);
			expenseReportDTO.setGrandtotalexpense(sumtotalexpense);
			expenseReportDTO.setGrandtotalcountvisit(sumcountvisit);
			expenseReportDTO.setGrandtotalsecondaryvalue(sumsecondaryvalue);
			expenseReportDTO.setGrandtotalapprovedexpensebyadmin(sumaccepted);
			expenseReportDTO.setGrandtotalapprovedexpensebyrsm(sumrsmaccepted);
		    salesexpensereportItems.forEach(ii -> {
		    	
		    	SalesExpenseReportItemsDTO itemsDTO = new SalesExpenseReportItemsDTO();
		    	itemsDTO.setModeoftravel(ii.getmodeoftravel());
		    	itemsDTO.setOtherexp(ii.getotherexp());
		    	itemsDTO.setTravelfrom(ii.gettravelfrom());
		    	itemsDTO.setTravelto(ii.gettravelto());
		    	itemsDTO.setTravelfare(ii.gettravelfare());
		    	itemsDTO.setOtherexpamount(ii.getotherexpamount());
		    	itemsDTO.setAcceptedamountbyadmin(ii.getapprovedexpensebyadmin());
		    	itemsDTO.setAcceptedamountbyrsm(ii.getapprovedexpensebyrsm());
		    
		    	 list2.add(itemsDTO);
		    });
		   
		 
		    expenseReportDTO.setExpenseReportItemsDTO(list2);
		    
		    list.add(expenseReportDTO);
			
		});
		return list;
	}

	


	@Override
	public List<SalesExpenseImages> expenseImagesbyitemid(int itemid) {
		List<SalesExpenseImages> getsalesexpenseimagebysalesitemid = salesExpenseImageRepo.getsalesexpenseimagebysalesitemid(itemid);
		return getsalesexpenseimagebysalesitemid;
	}
}
