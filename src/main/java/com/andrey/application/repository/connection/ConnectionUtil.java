package com.andrey.application.repository.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionUtil {

    private final static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static ConnectionUtil instance;
    private static Connection connection;
    //?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
    private final String url = "jdbc:postgresql://localhost:5432/crud_db?currentSchema=crud";
    private final String user = "postgres";
    private final String password = "root";

    private ConnectionUtil() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection to database was failed: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        return sessionFactory;
    }

    private Connection getConnection() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            System.out.println("Error during execution connection.setAutoCommit(false)!");
        }
        return connection;
    }

    private static ConnectionUtil getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionUtil();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectionUtil();
        }
        return instance;
    }

    public static PreparedStatement getStatement(String query) throws SQLException {
        PreparedStatement statement = null;
        try {
            connection = ConnectionUtil.getInstance().getConnection();
            statement = connection.prepareStatement(query);
        } catch (SQLException exception) {
            System.out.println("Couldn't create prepareStatement!");
        }
        if (statement == null) {
            throw new SQLException();
        }
        return statement;
    }

    public static void commit() throws SQLException {
        connection.commit();
    }

    public static void rollback() throws SQLException {
        connection.rollback();
    }

}
