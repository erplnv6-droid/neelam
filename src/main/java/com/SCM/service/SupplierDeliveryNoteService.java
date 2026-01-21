package com.SCM.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.SCM.dto.SupplierDeliveryNoteDto;
import com.SCM.model.SupplierDeliveryNote;

public interface SupplierDeliveryNoteService {

	public SupplierDeliveryNote saveSupplierDeliveryNote(SupplierDeliveryNoteDto supplierDeliveryNoteDto);

	// --- convert Po to supplier delivery note

	public SupplierDeliveryNote ConvertPotoSDN(SupplierDeliveryNoteDto supplierDeliveryNoteDto, int poId);

	public List<SupplierDeliveryNote> getAllSupplierDeliveryNote();

	public SupplierDeliveryNote getSupplierDeliveryNoteById(int id);

	public SupplierDeliveryNoteDto updateSupplierDeliveryNote(SupplierDeliveryNoteDto SupplierDeliveryNoteDto, int id);

	public void deleteSupplierDeliveryNote(int id);
	
	public void deleteSupplierDeliveryNote1(int id);
	

	public Map<String, Object> indexSupplierDeliveryDesc(int pagno, int pagesize, String field);

	public Map<String, Object> searchByIndexSupplierDelivery(int pageno, int pagesize, String search);

	public Map<String, Object> indexSupplierDeliveryAsc(int pagno, int pagesize, String field);
}
