package com.andrey.application.repository;

public interface ProjectRepositoryFactory {
    AccountRepository getAccountRepository();
    CustomerRepository getCustomerRepository();
    SpecialityRepository getSpecialityRepository();
}
