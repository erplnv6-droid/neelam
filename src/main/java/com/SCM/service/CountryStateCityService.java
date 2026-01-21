package com.SCM.service;

import java.util.List;

import com.SCM.model.Cities;
import com.SCM.model.Country;
import com.SCM.model.States;

public interface CountryStateCityService {

	
	public List<Country> Country();
	
	public List<States> States();
	
	public List<Cities> City();
	
	
	public Country CountryById(int id);
	
	public States StatesById(int id);
	
	public Cities CitiesById(int id);
	
	
	public List<Cities> CityFromState(int stateId);
}
