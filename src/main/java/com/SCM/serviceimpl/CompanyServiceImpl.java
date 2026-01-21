package com.SCM.serviceimpl;

import com.SCM.model.ActivityLog;
import com.SCM.model.Company;
import com.SCM.repository.ActivityLogRepo;
import com.SCM.repository.CompanyRepository;
import com.SCM.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    
	@Autowired
	private ActivityLogRepo activityLogRepo;

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(int id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public String deleteCompany(int id) {
        companyRepository.deleteById(id);

        return "Company Removed !!"+ id;
    }

    @Override
    public Company updateCompany(Company company, int id) {

        Company existingCompany = companyRepository.findById(id).orElse(null);
        existingCompany.setName(company.getName());
        existingCompany.setCname(company.getCname());
        existingCompany.setPhone(company.getPhone());
        existingCompany.setEmail(company.getEmail());
        existingCompany.setGst(company.getGst());
        existingCompany.setAddress(company.getAddress());
        existingCompany.setCountry(company.getCountry());
        existingCompany.setStates(company.getStates());
        existingCompany.setCities(company.getCities());
        existingCompany.setZipcode(company.getZipcode());
        
        Company c =   companyRepository.save(existingCompany);
        
        UserDetailsImpl userDetailsImpl =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser =   userDetailsImpl.getId();
		ActivityLog activityLog = new ActivityLog();
 
		activityLog.setUpdatedate(new Date());
		activityLog.setUpdatedtime(LocalTime.now());
		activityLog.setBranchid((long) c.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);

        return c;
    }

	@Override
	public Company savecompany(Company company) {
		
		Company c = companyRepository.save(company);
		
		UserDetailsImpl userDetailsImpl =  (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long loggeduser =   userDetailsImpl.getId();
		
		ActivityLog activityLog = new ActivityLog();
		activityLog.setCreatedate(new Date());
		activityLog.setCreatedtime(LocalTime.now());
		activityLog.setCompanyid((long) c.getId());
		activityLog.setLoggeduser(loggeduser);

		activityLogRepo.save(activityLog);
		
		return c;
	}

}
