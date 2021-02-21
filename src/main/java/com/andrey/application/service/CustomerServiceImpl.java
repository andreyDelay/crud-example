package com.andrey.application.service;

import com.andrey.application.model.Customer;
import com.andrey.application.repository.CustomerRepository;
import com.andrey.application.repository.connection.ConnectionUtil;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerServiceImpl() {
        this.customerRepository = ConnectionUtil.defineRepository()
                                    .getCustomerRepository();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer newValue) {
        return customerRepository.update(newValue);
    }

    @Override
    public void delete(int id) {
        customerRepository.delete(id);
    }

    @Override
    public Optional<Customer> find(int id) {
        return customerRepository.find(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
