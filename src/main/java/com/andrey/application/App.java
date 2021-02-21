package com.andrey.application;


import com.andrey.application.view.MainBaseViewImpl;
import com.andrey.application.view.View;

/**
 * CRUD console Application
 *
 */
public class App {
    public static void main( String[] args ) {
        View view = new MainBaseViewImpl();
        view.startAction();
    }
}

