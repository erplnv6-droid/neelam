package com.SCM.dto;



import java.util.List;

import com.SCM.model.CartItems;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {
	

	private int id;
	private String name;
	private Distributor distributor;
	private long disid;
	private Retailer retailer;
	private long retid;
	private Staff staff;
	private long staffid;
    private List<CartItems> cartItems;

//    private List<CartItemsDto> cartItemsDto;
	
	
	
}
