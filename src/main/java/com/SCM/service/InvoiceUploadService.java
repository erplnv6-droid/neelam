package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.IndexDto.IndexInvoiceUpload;
import com.SCM.dto.InvoiceUploadDto;
import com.SCM.model.InvoiceUpload;

public interface InvoiceUploadService {

	public InvoiceUpload saveInvoiceUpload(InvoiceUploadDto invoiceUploadDto, MultipartFile imgfiles,MultipartFile pdffiles) throws IOException;

	public List<InvoiceUpload> getAllInvoice();

	public InvoiceUpload getInvoiceById(int id);

	public String delete(int id);

//	public InvoiceUpload updateInvoiceUpload(InvoiceUploadDto invoiceUploadDto, MultipartFile imgfiles,MultipartFile pdffiles,long id);
	
	
	//=========== index
	
	
	public List<IndexInvoiceUpload> ffffffffffff(int distid);
	
    public Map<String, Object> IndexInvoiceAsc(int pagno,int pagesize,String field);
    
    public Map<String, Object> IndexInvoiceDesc(int pagno,int pagesize,String field);
    
    public Map<String, Object> SearchInvoice(int pageno,int pagesize,String search);
}
