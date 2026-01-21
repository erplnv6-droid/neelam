package com.SCM.ExportDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface ExportDistributor {

	int getid();

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

	Integer getBoxProduct_Discount();

	Integer getKgProduct_Discount();

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

	int getAseid();

	int getAsmid();

	int getRsmid();

	int getNsmid();

	int getStateid();

	int getzonesid();

	String getPassword();

	String getAadharcard();

	String getPancard();

	String getState_name();

	String getZone_Name();

	String getAsestaffname();

	String getAsmstaffname();

	String getRsmStaffname();

	String getNsmStaffname();
	
	LocalDate getCreateddate();
	
	LocalTime getCreatedtime();
	
	LocalDate getupdateddate();
	
	LocalTime getupdatedtime();
	
	String getcreatebyname();

	String getupdatedbyname();
	
	String getlatitude();
	
	String getlongitude();
}
