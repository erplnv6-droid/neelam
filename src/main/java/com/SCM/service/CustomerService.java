package com.SCM.service;

import com.SCM.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public Customer getCustomerById(int id);

    public String deleteCustomer(int id);

    public Customer updateCustomer(Customer customer, int id);
}
