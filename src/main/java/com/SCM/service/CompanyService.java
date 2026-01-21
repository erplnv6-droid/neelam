package com.SCM.service;



import com.SCM.model.Company;

import java.util.List;

public interface CompanyService {
	
	public Company savecompany(Company company);

    public List<Company> getAllCompany();

    public Company getCompanyById(int id);

    public String deleteCompany(int id);

    public Company updateCompany(Company company, int id);

}
