package com.andrey.application.controller;

import com.andrey.application.model.Customer;
import com.andrey.application.repository.CustomerRepository;
import com.andrey.application.service.CustomerService;
import com.andrey.application.service.CustomerServiceImpl;
import com.andrey.application.util.CustomerBuilder;
import com.andrey.application.validators.CustomerValidator;
import com.andrey.application.validators.ValidatingOption;

import java.util.List;

public class CustomerControllerImpl {

    private final CustomerBuilder customerBuilder = new CustomerBuilder();
    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerRepository customerRepository) {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    public boolean isDataValid(String data, ValidatingOption validatingOption) throws IllegalArgumentException {
        try {
            CustomerValidator customerValidator = new CustomerValidator();
            customerValidator.validate(data, validatingOption);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Customer save(String data) {
        if (isDataValid(data, ValidatingOption.CUSTOMER_SAVE)) {
            Customer customer = customerBuilder.buildNewCustomer(data);
            return customerService.save(customer);
        }
        return null;
    }

    public Customer update(String data) {
        if (isDataValid(data, ValidatingOption.CUSTOMER_UPDATE)) {
            Customer customerToUpdate = customerBuilder.buildCustomerForUpdate(data);
            return customerService.update(customerToUpdate);
        }
        return null;
    }

    public Customer addSpeciality(String data) {
        if (isDataValid(data, ValidatingOption.CUSTOMER_ADD_SPECIALITY)) {
            Customer customer = customerBuilder.buildAddSpeciality(data);
            return customerService.update(customer);
        }
        return null;
    }

    public void delete(String data) {
        if (isDataValid(data, ValidatingOption.ID)) {
            int customerId = Integer.parseInt(data.trim());
            customerService.delete(customerId);
        }
    }

    public Customer find(String data) {
        Customer customer = null;
        if (isDataValid(data, ValidatingOption.ID)) {
            int customerId = Integer.parseInt(data.trim());
            customer = customerService.find(customerId).orElse(null);
        }
        return customer;
    }

    public List<Customer> findAll() {
        return customerService.findAll();
    }

}
