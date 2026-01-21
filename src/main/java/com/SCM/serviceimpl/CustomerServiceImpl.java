package com.SCM.serviceimpl;


import com.SCM.model.Customer;
import com.SCM.repository.CustomerRepository;
import com.SCM.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    
    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    
    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    
    @Override
    public String deleteCustomer(int id) {
        customerRepository.deleteById(id);

        return "Customer Removed !!"+ id;
    }

    @Override
    public Customer updateCustomer(Customer customer, int id) {

        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        existingCustomer.setName(customer.getName());
        existingCustomer.setNumber(customer.getNumber());
        existingCustomer.setCname(customer.getCname());
        existingCustomer.setGstno(customer.getGstno());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAddress(customer.getAddress());
        existingCustomer.setCountry(customer.getCountry());
        existingCustomer.setStates(customer.getStates());
        existingCustomer.setCities(customer.getCities());
        existingCustomer.setZipcode(customer.getZipcode());
        existingCustomer.setOpenbal(customer.getOpenbal());
        existingCustomer.setOpendate(customer.getOpendate());
        existingCustomer.setPan(customer.getPan());

        return customerRepository.save(existingCustomer);
    }

}
