package com.aleksenko.artemii.app;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.model.AbstractTaskList;
import com.aleksenko.artemii.model.ArrayTaskList;
import com.aleksenko.artemii.model.Task;
import com.aleksenko.artemii.model.TaskIO;
import com.aleksenko.artemii.view.MainView;

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
        MainController controller = new MainController();
        controller.startWork();
        ThreadForRemind remind = new ThreadForRemind(controller);
        remind.start();

        /*Task task1 = new Task("Task1", now().plusHours(2));
        Task task2 = new Task("Task1", now(), now().plusHours(10), 60);
        File file = new File("tasks.txt");
        MainView mainView = new MainView();
        mainView.printTitle();
        mainView.printMenu();
        AbstractTaskList tasks = new ArrayTaskList();
        tasks.add(task1);
        tasks.add(task2);
        TaskIO.writeText(tasks, file);
        AbstractTaskList abstractTaskList = new ArrayTaskList();
        TaskIO.readText(abstractTaskList, file);
        System.out.println(abstractTaskList.toString());
*/
       /* String s;
    s = new Scanner(System.in).nextLine();
        if (s.matches("^[1-7]$")){
    System.out.println("True enter");

}    */
}
}
