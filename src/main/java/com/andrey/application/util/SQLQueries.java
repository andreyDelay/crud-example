package com.andrey.application.util;

public enum SQLQueries {
    INSERT_SPECIALTY_QUERY("INSERT INTO crud.specialities(spec_name) VALUES(?);"),
    UPDATE_SPECIALITY_QUERY("UPDATE crud.specialities SET spec_name = ? WHERE speciality_id = ?;"),
    DELETE_SPECIALITY_QUERY("DELETE FROM crud.specialities WHERE speciality_id = ?;"),
    FIND_SPECIALITY_QUERY("SELECT speciality_id,spec_name FROM crud.specialities WHERE speciality_id = ?;"),
    FIND_ALL_SPECIALITIES("SELECT speciality_id,spec_name FROM crud.specialities;"),
    LAST_SAVED_SPECIALITY("SELECT speciality_id, spec_name FROM crud.specialities WHERE speciality_id = LASTVAL();"),

    INSERT_CUSTOMER_QUERY("INSERT INTO crud.customers(name,surname,age) VALUES(?, ?, ?);"),
    LAST_SAVED_CUSTOMER("SELECT customer_id,name,surname,age FROM crud.customers " +
                                            "WHERE customer_id = (SELECT MAX(customer_id) FROM crud.customers);"),
    UPDATE_CUSTOMER_QUERY("UPDATE crud.customers SET name = ?, surname = ?, age = ? WHERE customer_id = ?;"),
    DELETE_FROM_CUSTOMERS("DELETE FROM crud.customers WHERE customer_id = ?;"),

    INSERT_ACCOUNT_QUERY("INSERT INTO crud.accounts(name,customer_id,status) VALUES" +
                                "(?,(SELECT MAX(customer_id) FROM crud.customers),DEFAULT);"),
    LAST_SAVED_ACCOUNT("SELECT id, name AS account_name, status FROM crud.accounts WHERE id = LASTVAL();"),
    UPDATE_ACCOUNT_NAME("UPDATE crud.accounts SET name = ? WHERE id = ?;"),
    UPDATE_ACCOUNT_STATUS("UPDATE crud.accounts SET  status = ? WHERE id = ?;"),
    FIND_ACCOUNT("SELECT id,accounts.name AS account_name,status FROM crud.accounts WHERE id = ?;"),
    FIND_ALL_ACCOUNTS("SELECT id,accounts.name AS account_name,status FROM crud.accounts;"),
    DELETE_ACCOUNT("DELETE FROM crud.accounts WHERE customer_id = ?;"),

    INSERT_INTO_CUSTOMER_SPECIALITIES("INSERT INTO crud.customer_specialities (speciality_id, customer_id) VALUES(?,?);"),
    DELETE_FROM_CUSTOMER_SPECIALITIES("DELETE FROM crud.customer_specialities WHERE customer_id = ?;"),
    DEL_FROM_CUSTOMER_SPECIALITIES_BY_SPEC_ID("DELETE FROM crud.customer_specialities WHERE speciality_id = ?;"),

    FIND_CUSTOMER(            "SELECT customers_accounts.customer_id AS customer_id," +
                                        "name," +
                                        "surname," +
                                        "age," +
                                        "customers_accounts.account_name," +
                                        "status," +
                                        "speciality_id," +
                                        "spec_name" +
                                " FROM " +
                                            "(SELECT all_customers.customer_id AS customer_id," +
                                                    "name," +
                                                    "surname," +
                                                    "age," +
                                                    "account_name," +
                                                    "status " +
                                            "FROM crud.customers AS all_customers" +

                                    " LEFT JOIN " +
                                            "(SELECT accounts.customer_id," +
                                                    "accounts.name AS account_name," +
                                                    "status" +
                                            " FROM crud.accounts) AS all_accounts" +
                                    " ON all_customers.customer_id = all_accounts.customer_id) AS customers_accounts" +

                                " LEFT JOIN " +
                                            "(SELECT cust_spec.customer_id AS customer_id," +
                                                    "array_agg(specialities.speciality_id) AS speciality_id," +
                                                    "array_agg(spec_name) AS spec_name " +
                                            " FROM crud.specialities" +

                                    " LEFT JOIN" +
                                            "(SELECT customer_id," +
                                                    "customer_specialities.speciality_id" +
                                                    " FROM crud.customer_specialities" +
                                            ") AS cust_spec" +

                                    " ON specialities.speciality_id = cust_spec.speciality_id" +
                                        " group by cust_spec.customer_id" +
                                        ") AS customers_specialities" +

                                " ON customers_accounts.customer_id = customers_specialities.customer_id" +
                                " WHERE customers_accounts.customer_id = ?;"),

    FIND_ALL_CUSTOMERS(            "SELECT customers_accounts.customer_id AS customer_id," +
                                            "name," +
                                            "surname," +
                                            "age," +
                                            "customers_accounts.account_name," +
                                            "status," +
                                            "speciality_id," +
                                            "spec_name" +
                                    " FROM " +
                                                        "(SELECT all_customers.customer_id AS customer_id," +
                                                                "name," +
                                                                "surname," +
                                                                "age," +
                                                                "account_name," +
                                                                "status " +
                                                        "FROM crud.customers AS all_customers" +

                                                    " LEFT JOIN " +
                                                        "(SELECT accounts.customer_id," +
                                                                "accounts.name AS account_name," +
                                                                "status" +
                                                        " FROM crud.accounts) AS all_accounts" +
                                                    " ON all_customers.customer_id = all_accounts.customer_id) AS customers_accounts" +

                                    " LEFT JOIN " +
                                                        "(SELECT cust_spec.customer_id AS customer_id," +
                                                                "array_agg(specialities.speciality_id) AS speciality_id," +
                                                                "array_agg(spec_name) AS spec_name " +
                                                        " FROM crud.specialities" +

                                                    " LEFT JOIN" +
                                                        "(SELECT customer_id," +
                                                                "customer_specialities.speciality_id" +
                                                                " FROM crud.customer_specialities" +
                                                        ") AS cust_spec" +

                                                    " ON specialities.speciality_id = cust_spec.speciality_id" +
                                                        " group by cust_spec.customer_id" +
                                                        ") AS customers_specialities" +

                                    " ON customers_accounts.customer_id = customers_specialities.customer_id;");




    private String value;

    SQLQueries(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }


}
