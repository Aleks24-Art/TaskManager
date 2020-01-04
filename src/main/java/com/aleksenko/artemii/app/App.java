package com.aleksenko.artemii.app;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.model.*;
import com.aleksenko.artemii.view.MainView;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        /*Task task1 = new Task("Task1", LocalDateTime.now().plusDays(80));
        Task task2 = new Task("Task2", LocalDateTime.now().plusDays(20));
        Task task3 = new Task("Task3", LocalDateTime.now().plusDays(2));
        Task task4 = new Task("Task4", LocalDateTime.now().plusDays(160));

        controller.mainTaskList.add(task1);
        controller.mainTaskList.add(task2);
        controller.mainTaskList.add(task3);
        controller.mainTaskList.add(task4);*/
        MainController controller = new MainController();
        Thread remind = new ThreadForRemind(controller);
        remind.start();
        controller.startWork();
}
}
