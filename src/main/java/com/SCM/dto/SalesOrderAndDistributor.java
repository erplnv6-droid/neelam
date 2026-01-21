package com.SCM.dto;

import java.util.Date;

public interface SalesOrderAndDistributor {

	Integer getId();

	String getBuyerorder();

	String getVoucherno();

	String getDispatchedthrough();

	String getdestination();

	String getTermsofdelivery();

	Float getIgst();

	Float getCgst();

	Float getSgst();

	Float getGrossamount();

	Float getGrandtotal();

	String getShippingcharge();

	String getPayment_Terms();

	Float getRoundingofvalue();

	Date getSodate();

	Boolean getQuotationstatus();

	String getTaxtype();

	String Remarks();

	Integer getPrimaryorder_Id();

	Integer getDcid();

	String getStatus();

	Integer getDid();

	String getState_name();

	String getTrade_Name();

	String getBilling_Address();

	String getDelivery_Address();

	String getCity();

	String getCountry();

	String getPan_Number();

	String getGst_Number();

	String getGst_Type();

	String getPin_Code();

	String getPer_Mobile_Number();

	String getAlter_Mobile_Number();

	String getPer_Email();

	String getAlter_Email();

	String getCredit_Limit();

	String getCredit_Days();

	String getTransporter_Name();

	String getDelivery_Location();

	String getDiscount_Structure();

	Integer getBox_Product_Discount();

	Integer getKg_Product_Discount();

	Integer getCorporaet_Product_Discount();

	Integer getCooker_Product_Discount();

	Integer getNosh_Product_Discount();

	Integer getScheme_Discount();

	String getDistributor_Name1();

	String getDistributor_Name2();

	Date getDob1();

	Date getDob2();

	Date getDoa1();

	Date getDoa2();

	Long getMob_No1();

	Long getMob_No2();

	String getEmail1();

	String getEmail2();

	Float getSchemebox_Product_Discount();

	Float getSchemekg_Product_Discount();

	Float getSchemecorporate_Product_Discount();

	Float getSchemecooker_Product_Discount();

	Float getSchemenosh_Product_Disocunt();

	String getAadharcard();

	String getPancard();

}
