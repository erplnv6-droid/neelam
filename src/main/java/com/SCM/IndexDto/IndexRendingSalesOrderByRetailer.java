package com.SCM.IndexDto;

public interface IndexRendingSalesOrderByRetailer {
  Integer getproductid();
  
  String getean_code();
  
  String getproductname();
  
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
  
  Integer getcancelpcsqty();
  
  Float getcancelkgqty();
  
  Integer getpendingcancelpcsqty();
  
  Float getpendingcancelkgqty();
  
  String getcancelsalestatus();
  
  String getcancelstatus();
  
  int getaddcancelpcsqty();
  
  float getaddcancelkgqty();
}
