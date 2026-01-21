package  com.SCM.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.repository.StateZoneRepo;
import com.SCM.service.CountryStateCityService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class Country_State_CityController {

	@Autowired
	private CountryStateCityService countryStateCityService;
	
	@Autowired
	private StateZoneRepo stateZoneRepo;
	
	
    @GetMapping("/country")
	public ResponseEntity<?> showCountry()
	{
		return new ResponseEntity<>(countryStateCityService.Country(),HttpStatus.OK);
	}
    
    @GetMapping("/state")
   	public ResponseEntity<?> showstates()
   	{
   		return new ResponseEntity<>(countryStateCityService.States(),HttpStatus.OK);
   	}
    
    @GetMapping("/city")
   	public ResponseEntity<?> showCity()
   	{
   		return new ResponseEntity<>(countryStateCityService.City(),HttpStatus.OK);
   	}
    
    
    
    @GetMapping("/country/{id}")
	public ResponseEntity<?> showCountryByID(@PathVariable("id")int id)
	{
		return new ResponseEntity<>(countryStateCityService.CountryById(id),HttpStatus.OK);
	}
    
    
    @GetMapping("/state/{id}")
	public ResponseEntity<?> showStatesByID(@PathVariable("id")int id)
	{
		return new ResponseEntity<>(countryStateCityService.StatesById(id),HttpStatus.OK);
	}
    
    
    @GetMapping("/city/{id}")
	public ResponseEntity<?> showCitiesByID(@PathVariable("id")int id)
	{
		return new ResponseEntity<>(countryStateCityService.CitiesById(id),HttpStatus.OK);
	}
    
    
    @GetMapping("/city/state/{stateId}")
  	public ResponseEntity<?> showCitiesByStateID(@PathVariable("stateId")int stateId)
  	{
  		return new ResponseEntity<>(countryStateCityService.CityFromState(stateId),HttpStatus.OK);
  	}
    
    
    @GetMapping("/statezone")
   	public ResponseEntity<?> showStatesZone()
   	{
   		return new ResponseEntity<>(stateZoneRepo.findAll(),HttpStatus.OK);
   	}
    
    
    @GetMapping("/statezone/{id}")
   	public ResponseEntity<?> showStatesZonebyId(@PathVariable("id")int id)
   	{
   		return new ResponseEntity<>(stateZoneRepo.findById(id),HttpStatus.OK);
   	}
    
}
