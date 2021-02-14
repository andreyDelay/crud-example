package com.andrey.application.repository.hibernate;

import com.andrey.application.repository.AccountRepository;
import com.andrey.application.repository.CustomerRepository;
import com.andrey.application.repository.ProjectRepositoryFactory;
import com.andrey.application.repository.SpecialityRepository;

public class HibernateRepositoryFactory implements ProjectRepositoryFactory {

    @Override
    public AccountRepository getAccountRepository() {
        return new HibernateAccountRepository();
    }

    @Override
    public CustomerRepository getCustomerRepository() {
        return new HibernateCustomerRepository();
    }

    @Override
    public SpecialityRepository getSpecialityRepository() {
        return new HibernateSpecialityRepository();
    }
}
