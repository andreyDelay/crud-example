package com.andrey.application;


import com.andrey.application.repository.hibernate.HibernateRepositoryFactory;
import com.andrey.application.repository.jdbc.JdbcRepositoryFactory;
import com.andrey.application.view.View;
import com.andrey.application.view.MainBaseViewImpl;

/**
 * CRUD console Application
 *
 */
public class App {
    public static void main( String[] args ) {
        View view = new MainBaseViewImpl(new HibernateRepositoryFactory());
        view.startAction();
    }
}

