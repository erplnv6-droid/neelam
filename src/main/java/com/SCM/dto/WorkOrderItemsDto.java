package com.SCM.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
