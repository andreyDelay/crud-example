package com.andrey.application.util;

public enum SQLQueries {
    INSERT_SPECIALTY_QUERY("INSERT INTO specialities VALUES(NULL,?);"),
    UPDATE_SPECIALITY_QUERY("UPDATE specialities SET spec_name = ? WHERE speciality_id = ?;"),
    DELETE_SPECIALITY_QUERY("DELETE FROM specialities WHERE speciality_id = ?;"),
    FIND_SPECIALITY_QUERY("SELECT speciality_id,spec_name FROM specialities WHERE speciality_id = ?;"),
    FIND_ALL_SPECIALITIES("SELECT speciality_id,spec_name FROM specialities;"),
    LAST_SAVED_SPECIALITY("SELECT speciality_id, spec_name FROM specialities WHERE speciality_id = LAST_INSERT_ID();"),

    INSERT_CUSTOMER_QUERY("INSERT INTO customers VALUES(NULL, ?, ?, ?);"),
    LAST_SAVED_CUSTOMER("SELECT customer_id,name,surname,age FROM customers " +
                                            "WHERE customer_id = (SELECT MAX(customer_id) FROM customers);"),
    UPDATE_CUSTOMER_QUERY("UPDATE customers SET name = ?, surname = ?, age = ? WHERE customer_id = ?;"),
    DELETE_FROM_CUSTOMERS("DELETE FROM customers WHERE customer_id = ?;"),

    INSERT_ACCOUNT_QUERY("INSERT INTO accounts VALUES" +
                                "(NULL,?,LAST_INSERT_ID(),DEFAULT);"),
    LAST_SAVED_ACCOUNT("SELECT id, name AS account_name, status FROM accounts WHERE id = LAST_INSERT_ID();"),
    UPDATE_ACCOUNT_NAME("UPDATE accounts SET name = ? WHERE id = ?;"),
    UPDATE_ACCOUNT_STATUS("UPDATE accounts SET  status = ? WHERE id = ?;"),
    FIND_ACCOUNT("SELECT id,accounts.name AS account_name,status FROM accounts WHERE id = ?;"),
    FIND_ALL_ACCOUNTS("SELECT id,accounts.name AS account_name,status FROM accounts;"),
    DELETE_ACCOUNT("DELETE FROM accounts WHERE customer_id = ?;"),

    INSERT_INTO_CUSTOMER_SPECIALITIES("INSERT INTO customer_specialities (speciality_id, customer_id) VALUES(?,?);"),
    DELETE_FROM_CUSTOMER_SPECIALITIES("DELETE FROM customer_specialities WHERE customer_id = ?;"),
    DEL_FROM_CUSTOMER_SPECIALITIES_BY_SPEC_ID("DELETE FROM customer_specialities WHERE speciality_id = ?;"),

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
                                            "FROM customers AS all_customers" +

                                    " LEFT JOIN " +
                                            "(SELECT accounts.customer_id," +
                                                    "accounts.name AS account_name," +
                                                    "status" +
                                            " FROM accounts) AS all_accounts" +
                                    " ON all_customers.customer_id = all_accounts.customer_id) AS customers_accounts" +

                                " LEFT JOIN " +
                                            "(SELECT cust_spec.customer_id AS customer_id," +
                                                    "group_concat(specialities.speciality_id) AS speciality_id," +
                                                    "group_concat(spec_name) AS spec_name " +
                                            " FROM specialities" +

                                    " LEFT JOIN" +
                                            "(SELECT customer_id," +
                                                    "customer_specialities.speciality_id" +
                                                    " FROM customer_specialities" +
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
                                                        "FROM customers AS all_customers" +

                                                    " LEFT JOIN " +
                                                        "(SELECT accounts.customer_id," +
                                                                "accounts.name AS account_name," +
                                                                "status" +
                                                        " FROM accounts) AS all_accounts" +
                                                    " ON all_customers.customer_id = all_accounts.customer_id) AS customers_accounts" +

                                    " LEFT JOIN " +
                                                        "(SELECT cust_spec.customer_id AS customer_id," +
                                                                "group_concat(specialities.speciality_id) AS speciality_id," +
                                                                "group_concat(spec_name) AS spec_name " +
                                                        " FROM specialities" +

                                                    " LEFT JOIN" +
                                                        "(SELECT customer_id," +
                                                                "customer_specialities.speciality_id" +
                                                                " FROM customer_specialities" +
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
