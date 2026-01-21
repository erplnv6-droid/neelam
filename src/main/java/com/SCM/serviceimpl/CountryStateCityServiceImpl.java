package com.SCM.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.SCM.model.Cities;
import com.SCM.repository.CitiesRepo;
import com.SCM.repository.CountryRepo;
import com.SCM.repository.StatesRepository;
import com.SCM.service.CountryStateCityService;

@Service
public class CountryStateCityServiceImpl implements CountryStateCityService {

	@Autowired
	private CountryRepo countryRepo;
	
	@Autowired
	private StatesRepository statesRepository;
	
	@Autowired
	private CitiesRepo citiesRepo;
	
	
	@Override
	public List<com.SCM.model.Country> Country() {
		
		return countryRepo.findAll();
	}

	@Override
	public List<com.SCM.model.States> States() {
		
		return statesRepository.findAll();
	}

	@Override
	public List<Cities> City() {
		
		return citiesRepo.findAll();
	}

	
	@Override
	public com.SCM.model.Country CountryById(int id) {
		
		com.SCM.model.Country country = countryRepo.findById(id).get();
		
		return country;
	}

	@Override
	public com.SCM.model.States StatesById(int id) {
		
		com.SCM.model.States states = statesRepository.findById(id).get();
		
		return states;
	}

	@Override
	public Cities CitiesById(int id) {
		
		Cities cities = citiesRepo.findById(id).get();
		
		return cities;
	}

	
	@Override
	public List<Cities> CityFromState(int stateId) {
		
		return citiesRepo.getCityFromState(stateId);
	}

}
