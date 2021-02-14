package com.andrey.application.repository.jdbc;

import com.andrey.application.model.Account;
import com.andrey.application.model.Customer;
import com.andrey.application.model.Speciality;
import com.andrey.application.repository.AccountRepository;
import com.andrey.application.repository.CustomerRepository;
import com.andrey.application.repository.SpecialityRepository;
import com.andrey.application.repository.connection.ConnectionUtil;
import com.andrey.application.util.DAOEntityBuilder;
import com.andrey.application.util.SQLQueries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCustomerRepositoryImpl implements CustomerRepository {


    private final AccountRepository accountRepository = new JdbcAccountRepositoryImpl();
    private final SpecialityRepository specialityDAO= new JdbcSpecialityRepositoryImpl();

    @Override
    public Customer save(Customer newCustomer) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.INSERT_CUSTOMER_QUERY.getValue()))
        {
            statement.setString(1, newCustomer.getName());
            statement.setString(2, newCustomer.getSurname());
            statement.setInt(3, newCustomer.getAge());
            statement.execute();
            Account accountAfterSave = accountRepository.save(newCustomer.getAccount());
            ResultSet resultSet = statement.executeQuery(SQLQueries.LAST_SAVED_CUSTOMER.getValue());
            if (resultSet.next()) {
                newCustomer = DAOEntityBuilder.simpleBuildCustomer(resultSet);
            }
            //TODO accountRepository проглотил потенциальный exception, не могу понять как тут откатить если в accountRepository ошибка?
            newCustomer.setAccount(accountAfterSave);
            ConnectionUtil.commit();
        } catch (SQLException e) {
            try {
                ConnectionUtil.rollback();
            } catch (SQLException rollBackException) {
                System.out.println("Error during DB rollback in class " + this.getClass().getName());
            }
            System.out.println("Customer save failed.");
            return null;
        }
            return newCustomer;
    }

    @Override
    public Customer update(Customer updatedCustomer) {
        try {
            PreparedStatement statement;
            Speciality speciality = updatedCustomer.getSpecialities()
                                                    .stream()
                                                    .findFirst()
                                                    .orElse(null);
                if (speciality == null) {
                    statement = ConnectionUtil.getStatement(SQLQueries.UPDATE_CUSTOMER_QUERY.getValue());
                    statement.setString(1, updatedCustomer.getName());
                    statement.setString(2, updatedCustomer.getSurname());
                    statement.setInt(3, updatedCustomer.getAge());
                    statement.setInt(4, updatedCustomer.getId());
                    statement.execute();
                } else {
                    Speciality saved = specialityDAO.save(speciality);
                    insertIntoCustomerSpecialities(saved, updatedCustomer.getId());
                }
            updatedCustomer = find(updatedCustomer.getId()).orElse(null);
            ConnectionUtil.commit();
            } catch(SQLException e){
                try {
                    ConnectionUtil.rollback();
                } catch (SQLException rollBackException) {
                    System.out.println("Error during DB rollback in class " + this.getClass().getName());
                }
            System.out.println("Customer update failed.");
            return null;
            }
        return updatedCustomer;
    }


    @Override
    public void delete(Integer customerId) {
        try(PreparedStatement statement = ConnectionUtil.getStatement(
                                SQLQueries.DELETE_FROM_CUSTOMERS.getValue()))
        {
            deleteFromCustomerSpecialities(customerId);
            accountRepository.delete(customerId);
            statement.setInt(1, customerId);
            statement.execute();
            ConnectionUtil.commit();
        } catch (SQLException e) {
            try {
                ConnectionUtil.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Error during DB rollback in class " + this.getClass().getName());
            }
            System.out.println("Customer delete failed.");
        }
    }

    @Override
    public Optional<Customer> find(Integer id) {
        Optional<Customer> customer = Optional.empty();
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.FIND_CUSTOMER.getValue()))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer = Optional.of(DAOEntityBuilder.buildCustomer(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error find() method in class " + this.getClass().getName());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.FIND_ALL_CUSTOMERS.getValue()))
        {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = DAOEntityBuilder.buildCustomer(resultSet);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error findAll() method in class " + this.getClass().getName());
        }
        return customers;
    }

    private void insertIntoCustomerSpecialities(Speciality speciality, int customerId) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.INSERT_INTO_CUSTOMER_SPECIALITIES.getValue()))
        {
            statement.setInt(1, speciality.getId());
            statement.setInt(2, customerId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Speciality already exists for current customer!");
        }
    }

    private void deleteFromCustomerSpecialities(int customerId) {
        try(PreparedStatement statement =
                    ConnectionUtil.getStatement(SQLQueries.DELETE_FROM_CUSTOMER_SPECIALITIES.getValue()))
        {
            statement.setInt(1, customerId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("CustomerSpecialities delete was failed!");
        }
    }

}
