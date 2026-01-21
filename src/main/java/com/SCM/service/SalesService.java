package com.SCM.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.GstModel.EinvoiceData;
import com.SCM.dto.NewVoucherName;
import com.SCM.dto.SalesDto;
import com.SCM.model.Sales;
import com.SCM.model.SalesItems;
import com.SCM.projectionDto.ClosingStockReport;
import com.SCM.projectionDto.SalesReportDto;

public interface SalesService {

	public Sales saveSales(SalesDto salesdto, String salesitemsjson, String tranDtls, String docDtls, String sellerDtls,
			String buyerDtls, String valDtls, String ewbDtls, MultipartFile imgfiles, MultipartFile pdffiles)
			throws IOException;

	public Sales ConvertDCToSales(SalesDto salesdto, String salesitemsjson, String tranDtls, String docDtls,
			String sellerDtls, String buyerDtls, String valDtls, String ewbDtls, MultipartFile imgfiles,
			MultipartFile pdffiles, int dcId) throws IOException;

	public Sales saveSales(SalesDto salesdto, String salesitemsjson, String tranDtls, String docDtls, String sellerDtls,
			String buyerDtls, String itemList, String valDtls, String ewbDtls, MultipartFile imgfiles,
			MultipartFile pdffiles) throws IOException;

//    public Sales ConvertDCToSales(SalesDto salesdto,String salesitemsjson,String tranDtls,String docDtls,String sellerDtls,String buyerDtls,String itemList,String valDtls,String ewbDtls,
//    		MultipartFile imgfiles,MultipartFile pdffiles,int dcId) throws IOException;

	public List<Sales> getAllSales();

	public Sales getSalesById(int id);

	public EinvoiceData getEinvoiceData(long id);

	void deleteSales(int id);

//	public SalesDto updateSales(SalesDto salesdto,int id,String salesitemsjson,
//			String tranDtls,
//			String docDtls,String sellerDtls,String buyerDtls,
//    		String valDtls,String ewbDtls)throws IOException;

	public SalesDto updateSales(SalesDto salesdto, int id, String salesitemsjson, String tranDtls, String docDtls,
			String sellerDtls, String buyerDtls, String valDtls, String ewbDtls,

			MultipartFile imgfiles, MultipartFile pdffiles) throws IOException;

	public Map<String, Object> IndexSalesAsc(int pageno, int pagesize, String field);

	public Map<String, Object> IndexSalesDesc(int pageno, int pagesize, String field);

	public Map<String, Object> SearchSales(int pageno, int pagesize, String search);

	public Map<String, Object> transportReportAsc(String start_date, String end_date, int pageno, int pagesize,
			String field);

//	 public Map<String, Object> SearchSales(int pageno,int pagesize,String search);

	public Map<String, Object> transportReportDesc(String start_date, String end_date, int pageno, int pagesize,
			String field);

	public String generateVoucher(NewVoucherName newVoucherName);

	public List<SalesItems> getAllItems();
	
	 
	public List<SalesReportDto> salesByRetailerListAndDistList(List<Long> retids, List<Long> distids,String fromdate,String todate);

	
	public List<ClosingStockReport> closingStockByWidFdateTdate(String fromdate,String todate,String wid,Long pid);

}
