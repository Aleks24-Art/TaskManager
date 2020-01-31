package com.aleksenko.artemii.app;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.controller.ThreadForRemind;

/**
 * @author Aretemii Aleksenko
 * @version 1.0
 * This is the main class, where all app is started.
 */
public class App {
    /**
     * This is the start point of the program,
     * where we create two objects — to start remind and main app.
     * @param args main code
     */
    public static void main( String[] args ) {
   // new ThreadForRemind().start();
    new MainController();
    }
}
