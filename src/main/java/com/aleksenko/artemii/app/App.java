package com.aleksenko.artemii.app;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.controller.ThreadForRemind;

public class App {
    public static void main( String[] args ) {
        MainController controller = new MainController();
        Thread remind = new ThreadForRemind(controller);
        remind.start();
       //controller.startWork();
    }
}
