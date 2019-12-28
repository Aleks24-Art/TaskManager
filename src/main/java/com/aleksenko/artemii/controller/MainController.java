package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.view.MainView;
import com.aleksenko.artemii.model.*;
import java.util.Scanner;

public class MainController implements Controller{
    MainView view = new MainView();
    AbstractTaskList mainTaskList = new ArrayTaskList();

    @Override
    public void startWork() {
        int menuVariant;
        Scanner scanner = new Scanner(System.in);
        view.printTitle();
        while(true){
            try {
                view.printMenu();
                menuVariant = scanner.nextInt();
            } catch ()



        }
    }

    @Override
    public void addTask() {

    }

    @Override
    public void changeTask() {

    }

    @Override
    public void showTask() {

    }

    @Override
    public void showTaskListForInterval() {

    }

    @Override
    public void removeTask() {

    }

    @Override
    public void removeAllTasks() {

    }

    @Override
    public void finishWork() {
        /*System.exit(0);*/
    }

    @Override
    public void reminder() {
       /* Создадим поток для того что бы он шёл паралельно с
        программой и напоминал о скором выволнении задачи*/

    }

    @Override
    public void saveAllInfoToFileSystem() {
        /*Сохраняем всю инфу в файловую систему*/
    }
}
