package com.SCM.GstDto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemList {

	
	private String SlNo;
    private String PrdDesc;
    private String IsServc;
    private String HsnCd;

    private float Qty;
    private int FreeQty;
    private String Unit;
    private float UnitPrice;
    private float TotAmt;
    private float Discount;
private float Bdiscount;
    private float AssAmt;
    private float GstRt;
    private float IgstAmt;
    private float CgstAmt;
    private float SgstAmt;
//    private double CesRt;
//    private double CesAmt;
//    private double CesNonAdvlAmt;
//    private double StateCesRt;
//    private double StateCesAmt;
//    private double StateCesNonAdvlAmt;
//    private double OthChrg;
    private float TotItemVal;


	
    }
