package com.SCM.dto;

import java.util.List;

import lombok.Data;

@Data
public class SalesOrderCancelRequest {

	private List<SalesOrderItemsCancelDto> soitems;
}
