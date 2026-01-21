package com.SCM.IndexDto;

public interface IndexRendingSalesOrderByDistributor {
  Integer getproductid();
  
  String getean_code();
  
  String getproduct_name();
  
  String getproduct_type();
  
  String getuom_secondary();
  
  String getuom_primary();
  
  Integer getsalesorderitem_id();
  
  Integer getsalesorderid();
  
  Integer getsalesitemid();
  
  Integer getsopcsqty();
  
  Float getsokgqty();
  
  Integer getsipcsqty();
  
  Float getsikgqty();
  
  Integer getdist_cancelpcsqty();
  
  Float getdist_cancelkgqty();
  
  Integer getdist_pendingcancelpcsqty();
  
  Float getdist_pendingcancelkgqty();
  
  int getdist_addcancelpcsqty();
  
  float getdist_addcancelkgqty();
  
  String getcancelsalestatus();
  
  String getcancelstatus();
}
