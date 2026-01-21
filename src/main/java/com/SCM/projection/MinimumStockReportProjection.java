package com.SCM.projection;

public interface MinimumStockReportProjection {
	 Integer getProduct_id();
	 String getProduct_name();
	 String getDate();
	 Integer getTotalosqty();
	 Integer getMsqty();
	 Integer getTotalmrniqty();
	 Integer getTotalsriqty();
	 Integer getTotaldciqty();
	 Integer getTotalpriqty();
	 Integer getInwardqty();
	 Integer getOutwardqty();
	 Integer getClosingstock();
}
