package com.SCM.service;

import java.util.List;
import java.util.Map;

import com.SCM.dto.MaterialRecepitNoteDto;
import com.SCM.model.MaterialRecepitNote;

public interface MaterialReceiptNoteService {

	public MaterialRecepitNote saveMaterialRecepitNote(MaterialRecepitNoteDto materialRecepitNoteDto);
		
	// ----- convert SupplierDeliveryNote to MaterialReceiptNote
	
	public MaterialRecepitNote ConvertoMRNSupplierdeliverynote(MaterialRecepitNoteDto materialRecepitNoteDto,int supplierdeliverynoteId);

    public List<MaterialRecepitNote> getAllMaterialRecepitNote();

    public MaterialRecepitNote getMaterialRecepitNoteById(int id);
    
    public List<MaterialRecepitNote>  getMaterialRecepitNoteBySupplierdeliveryId(int supplierdeliverynoteId);

    public void deleteMaterialRecepitNote(int id);
    
    public void deleteMaterialRecepitNote1(int id);

    public MaterialRecepitNoteDto updateMaterialRecepitNote(MaterialRecepitNoteDto materialRecepitNoteDto, int id);
    
    
    public Map<String, Object> indexMaterialReceiptAsc(int pageno,int pagesize,String field);
    
    public Map<String, Object> indexMaterialReceiptDesc(int pageno,int pagesize,String field);
    
    public Map<String, Object> searchByMaterialReceipt(int pageno,int pagesize,String search);
}
