package com.andrey.application.repository.connection;

import com.andrey.application.repository.ProjectRepositoryFactory;
import com.andrey.application.repository.hibernate.HibernateRepositoryFactory;
import com.andrey.application.repository.jdbc.JdbcRepositoryFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private final static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private static ConnectionUtil instance;
    private static Connection connection;
    private static Properties context;
    private static final String propertyFilePath = "application.properties";

    private ConnectionUtil() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            context = PropertiesLoader.loadProperties(propertyFilePath);
            this.connection = DriverManager.getConnection(
                                                        context.getProperty("url"),
                                                        context.getProperty("user"),
                                                        context.getProperty("password"));
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

    private Properties getContext() {
        return context;
    }

    public static ProjectRepositoryFactory defineRepository() {
        ProjectRepositoryFactory repositoryFactory;
        try {
            context = ConnectionUtil.getInstance().getContext();
            context = PropertiesLoader.loadProperties(propertyFilePath);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        String profile = context.getProperty("profile");
        if (profile.equalsIgnoreCase("jdbc")) {
            repositoryFactory = new JdbcRepositoryFactory();
        } else if (profile.equalsIgnoreCase("hibernate")) {
            repositoryFactory = new HibernateRepositoryFactory();
        } else {
            throw new RuntimeException("Unknown profile.");
        }

        return repositoryFactory;
    }

}
