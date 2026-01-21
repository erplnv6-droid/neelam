package com.SCM.dto;

import lombok.Data;

@Data
public class WorkOrderItemsDto {

    private int wItemId;
    private int productId;
    private float mrp;
    private float qty;
    private float total;
    private float gstvalue;
    private float amount;
    private float discount;
	private float staffCancelQty;
	private float retailerCancelQty;
	private float disributorCancelQty;
   
}
