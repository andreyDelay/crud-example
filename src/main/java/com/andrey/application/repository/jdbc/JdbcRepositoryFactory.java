package com.andrey.application.repository.jdbc;

import com.andrey.application.repository.AccountRepository;
import com.andrey.application.repository.CustomerRepository;
import com.andrey.application.repository.ProjectRepositoryFactory;
import com.andrey.application.repository.SpecialityRepository;

public class JdbcRepositoryFactory implements ProjectRepositoryFactory {
    @Override
    public AccountRepository getAccountRepository() {
        return new JdbcAccountRepositoryImpl();
    }

    @Override
    public CustomerRepository getCustomerRepository() {
        return new JdbcCustomerRepositoryImpl();
    }

    @Override
    public SpecialityRepository getSpecialityRepository() {
        return new JdbcSpecialityRepositoryImpl();
    }
}
