package com.SCM.GstLoginUserDto;

import java.util.List;




import com.SCM.GstDto.AddlDocDtls;
import com.SCM.GstDto.BuyerDtls;
import com.SCM.GstDto.DispDtls;
import com.SCM.GstDto.DocDtls;
import com.SCM.GstDto.EwbDtls;
import com.SCM.GstDto.ExpDtls;
import com.SCM.GstDto.ItemList;
import com.SCM.GstDto.PayDtls;
import com.SCM.GstDto.PrecDocDtls;
import com.SCM.GstDto.RefDtls;
import com.SCM.GstDto.SellerDtls;
import com.SCM.GstDto.ShipDtls;
import com.SCM.GstDto.TranDtls;
import com.SCM.GstDto.ValDtls;

import com.SCM.model.Sales;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EinvoiceDto {

private String Version;


	
private TranDtls TranDtls;
	
	private DocDtls DocDtls;
	
	private SellerDtls SellerDtls;
	
	private BuyerDtls BuyerDtls;
	
	private DispDtls DispDtls;
	
	private ShipDtls ShipDtls;
	
	private List<ItemList> ItemList;
	
	private ValDtls ValDtls;
	
	private PayDtls PayDtls;
	
	private RefDtls RefDtls;
	
	private List<AddlDocDtls> AddlDocDtls;
	
	private ExpDtls ExpDtls;
	
	private EwbDtls EwbDtls;
	
	private int Sales_id;
	


	

	


	
}
