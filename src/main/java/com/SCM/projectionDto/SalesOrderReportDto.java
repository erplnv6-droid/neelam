package com.SCM.projectionDto;

public interface SalesOrderReportDto {
  Long getproductid();
  
  String getproductname();
  
  Long getsopcsqty();
  
  Float getsokgqty();
  
  Long getcancelpcsqty();
  
  Float getcancelkgqty();
  
  Long getdistcancelpcsqty();
  
  Float getdistcancelkgqty();

}
