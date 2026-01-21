package com.SCM.dtoReports;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimumStockReport {

	private String product_name;
	private String mrp;
	private int stockqty;
	private int closingstock;
    private String stockstatus;
    
    public MinimumStockReport() {}
	
	public MinimumStockReport(String product_name, String mrp, int stockqty, int closingstock,String stockstatus) {
		this.product_name = product_name;
		this.mrp = mrp;
		this.stockqty = stockqty;
		this.closingstock = closingstock;
		this.stockstatus = stockstatus;
	}

}
