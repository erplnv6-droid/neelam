package com.SCM.controller;

import com.SCM.model.Company;
import com.SCM.model.States;
import com.SCM.repository.StatesRepository;
import com.SCM.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StatesRepository statesRepository;
    
    
    @PostMapping("/")
    public Company saveCompany( @RequestBody Company company) {

        return companyService.savecompany(company);

    }
    
    @GetMapping("/getAll")
    public List<Company> getAllCompany(){

        return companyService.getAllCompany();

    }
    
    @GetMapping("/getById/{id}")
    public Company findCompanyById(@PathVariable int id){

        return companyService.getCompanyById(id);
    }

    
    @PutMapping("/update/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company company) {

        return companyService.updateCompany(company, id);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteCompany(@PathVariable int id){

        return companyService.deleteCompany(id);

    }


    @GetMapping("/getAllstate")
    public List<States> getAllState(){

        return statesRepository.findAll();
    }



//    @GetMapping("/getStatecodeById/{id}")
//    public State_code findStateCodeById(@PathVariable String id){
//
//        return statecodeRepository.findByStateid(id);
//    }


    @GetMapping("/getStateById/{id}")
    public Optional<States> findStateById(@PathVariable int id){

        return statesRepository.findById(id);
    }

}
