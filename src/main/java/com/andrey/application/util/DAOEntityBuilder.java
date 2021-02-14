package com.andrey.application.util;

import com.andrey.application.model.Account;
import com.andrey.application.model.AccountStatus;
import com.andrey.application.model.Customer;
import com.andrey.application.model.Speciality;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DAOEntityBuilder {

    public static Account buildAccount(ResultSet resultSet) {
        Account account = null;
        try {
            account = new Account();
            account.setName(resultSet.getString("account_name"));
            account.setStatus(AccountStatus.valueOf(resultSet.getString("status")));
            account.setId(resultSet.getInt("id")); //exception here when we build all customers
        } catch (SQLException e) {
            //TODO account id doesn't exist in final table aster query when we build data for all customers,
            // otherwise it does
        }
        return account;
    }

    public static Set<Speciality> buildSpecialities(ResultSet resultSet) throws SQLException {
        Set<Speciality> specialities = null;
        try {
                String fieldId = resultSet.getString("speciality_id");
                String fieldNames = resultSet.getString("spec_name");
                if (fieldId != null && fieldId.length() != 0) {

                    specialities = new HashSet<>();
                    String[] id = fieldId.split(",");
                    String[] names = fieldNames.split(",");

                    for (int i = 0; i < id.length; i++) {
                        Speciality speciality = new Speciality();
                        speciality.setId(Integer.parseInt(id[i]));
                        speciality.setName(names[i]);

                        specialities.add(speciality);
                    }
                }
        } catch (SQLException e) {
            //TODO what to print?
        }
        return specialities;
    }

    public static Speciality buildSpeciality(ResultSet resultSet) throws SQLException {
        Speciality speciality = null;
        try {
                speciality = new Speciality();
                speciality.setId(resultSet.getInt("speciality_id"));
                speciality.setName(resultSet.getString("spec_name"));
        } catch (SQLException e) {
            //TODO what to print?
        }
        return speciality;
    }

    public static Customer buildCustomer(ResultSet resultSet) {
        Customer customer = null;
        try {
                customer = new Customer();
                customer.setId(resultSet.getInt("customer_id"));
                customer.setName(resultSet.getString("name"));
                customer.setSurname(resultSet.getString("surname"));
                customer.setAge(resultSet.getInt("age"));
                customer.setAccount(DAOEntityBuilder.buildAccount(resultSet));
                customer.setSpecialities(DAOEntityBuilder.buildSpecialities(resultSet));
        } catch (SQLException e) {
            //TODO what to print?
        }
        return customer;
    }

    public static Customer simpleBuildCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = null;
        try {
                customer = new Customer();
                customer.setId(resultSet.getInt("customer_id"));
                customer.setName(resultSet.getString("name"));
                customer.setSurname(resultSet.getString("surname"));
                customer.setAge(resultSet.getInt("age"));
        } catch (SQLException e) {
            //TODO what to print?
        }
        return customer;
    }
}
