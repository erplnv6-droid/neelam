package com.SCM.controller;

import com.SCM.model.Customer;
import com.SCM.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;


    @PostMapping("/add")
    public String add(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return "New Customer is added";


    }


    @GetMapping("/getAll")
    public List<Customer> getAllCustomers(){

        return customerService.getAllCustomers();

    }


    @GetMapping("/getById/{id}")
    public Customer findCustomerById(@PathVariable int id){

        return customerService.getCustomerById(id);
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {

        return customerService.updateCustomer(customer, id);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id){

        return customerService.deleteCustomer(id);

    }

}
