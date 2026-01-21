package com.SCM.service;

import com.SCM.model.SupplyArea;

import java.util.List;

public interface SupplyAreaService {

	public SupplyArea saveSupplyArea(SupplyArea supplyArea);

	public List<SupplyArea> getAllSupplyArea();

	public SupplyArea getSupplyAreaById(int id);

	public String deleteSupplyArea(int id);

	public SupplyArea updateSupplyArea(SupplyArea supplyArea, int id);
}
