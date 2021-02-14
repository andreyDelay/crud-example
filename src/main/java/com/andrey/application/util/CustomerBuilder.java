package com.andrey.application.util;

import com.andrey.application.model.Account;
import com.andrey.application.model.Customer;
import com.andrey.application.model.Speciality;

public class CustomerBuilder extends Builder {

    public Customer buildNewCustomer(String data) {
        AccountBuilder accountBuilder = new AccountBuilder();
        String [] fields = data.split(",");

        String name = fields[0].trim();
        String surname = fields[1].trim();
        int age = Integer.parseInt(fields[2].trim());
        String newAccountData = fields[3].trim() + "," + "ACTIVE";


        Account account = accountBuilder.buildAccount(newAccountData);
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setAge(age);
        customer.setAccount(account);

        return customer;
    }

    public Customer buildCustomerForUpdate(String data) {
        String [] fields = data.split(",");

        int id = Integer.parseInt(fields[0].trim());
        String name = fields[1].trim();
        String surname = fields[2].trim();
        int age = Integer.parseInt(fields[3].trim());

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setAge(age);

        return customer;
    }

    public Customer buildAddSpeciality(String data) {
        Customer customer = new Customer();

        String[] fields = data.split(",");
        int id = Integer.parseInt(fields[0].trim());
        String specialityName = fields[1];

        Speciality speciality = new Speciality();
        speciality.setName(specialityName);
        customer.setId(id);
        customer.addSpeciality(speciality);

        return customer;
    }

}
