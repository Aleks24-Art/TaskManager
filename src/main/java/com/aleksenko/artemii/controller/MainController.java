package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.view.MainView;
import com.aleksenko.artemii.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainController implements Controller{
    private MainView view = new MainView();
    private AbstractTaskList mainTaskList = new ArrayTaskList();

    @Override
    public void startWork() {

        String menuVariant;
        view.printTitle();
        while(true) {
                view.printMenu();
                menuVariant = new Scanner(System.in).nextLine();
        while (!(menuVariant.matches("^[1-7]$"))) {
            System.out.println("Некоректный ввод номера");
            menuVariant = new Scanner(System.in).nextLine();
            }

                switch (menuVariant) {
                    case "1":
                        addTask();
                        break;
                    case "2":
                        changeTask();
                        break;
                    case "3":
                        showTask();
                        break;
                    case "4":
                        showTaskListForInterval();
                        break;
                    case "5":
                        removeTask();
                        break;
                    case "6":
                        removeAllTasks();
                    case "7":
                        finishWork();
                        break;
                }

        }
    }

    @Override
    public void addTask() {
        String variantOfTask;
        view.printSubMenu_1();
        variantOfTask = new Scanner(System.in).nextLine();
        while (!(variantOfTask.matches("^[1-2]$"))) {
            System.out.println("Некоректный пункт меню");
            variantOfTask = new Scanner(System.in).nextLine();
        }


        if (variantOfTask.equals("1")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String title;
            String strStart;
            String strEnd;
            int interval = 0, variantOfInterval;
            LocalDateTime start, end;

            System.out.println("Введите название задачи " +
                                "\nПример: Убрать в шкафу" +
                                "\nВвод:");
            title = new Scanner(System.in).nextLine();

            System.out.println("Введите дату начала выполнения задачи в формате yyy-MM-dd HH:mm" +
                                "\nПример: 1986-04-08 12:30" +
                                "\nВвод: ");
            strStart = new Scanner(System.in).nextLine();
           /* while (!(strStart.matches("^[2020-2100]-[1-12]-[1-31] [1-24]:[1-59]$"))) {
                System.out.println("Не правильное время");
                strStart = new Scanner(System.in).nextLine();
            }*/
            start =  LocalDateTime.parse(strStart, formatter);


            System.out.println("Введите дату конца выполнения задачи в формате yyyy-MM-dd HH:mm " +
                                "\nПример: 1986-04-08 12:30" +
                                "\nВвод: ");
            strEnd = new Scanner(System.in).nextLine();
            /*while (!(strEnd.matches("^[2020-2100]-[1-12]-[1-31] [1-24]:[1-59]$"))) {
                System.out.println("Не правильное время");
                strEnd = new Scanner(System.in).nextLine();
            }*/
            end =  LocalDateTime.parse(strEnd, formatter);

            System.out.println("Введите формат повторения \n"
                                + "1. Каждый час \n"
                                + "2. Каждый день \n"
                                + "3. Каждую неделю"
                                + "\nВвод:");
            variantOfInterval = new Scanner(System.in).nextInt();
            switch (variantOfInterval){
                case 1 :
                    interval = 1;
                    break;
                case 2 :
                    interval = 24;
                    break;
                case 3 :
                    interval = 168;
                    break;
            }
            Task newTask = new Task(title, start, end, interval);
            mainTaskList.add(newTask);
            showTask();
        }

        if (variantOfTask.equals("2")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String title;
            String strTime;
            LocalDateTime time;

            System.out.println("Введите название задачи " +
                                "\nПример: Моя задача" +
                                "\nВвод:");
            title = new Scanner(System.in).nextLine();
            //scanner.close();
            System.out.println("Введите дату выполнения задачи в формате yyyy-MM-dd HH:mm " +
                                "\nПример: 1986-04-08 12:30" +
                                "\nВвод:");
            strTime = new Scanner(System.in).nextLine();
            //scanner.close();
            time = LocalDateTime.parse(strTime, formatter);
            /*while (true) {
                if (time.isBefore(LocalDateTime.now())) {
                    System.out.println("Даты из прошлого не допустимы для планирования задач\n" +
                            "Введите дату повторно:");
                    strTime = new Scanner(System.in).nextLine();
                    while (!(strTime.matches("^[2020-2100]-[1-12]-[1-31] [1-24]:[1-59]$"))) { // 2019-12-31 4:40
                        System.out.println("Не правильное время");
                        strTime = new Scanner(System.in).nextLine();
                    }
                    time = LocalDateTime.parse(strTime, formatter);
                    continue;
                }
                break;
            }*/
            Task newTask = new Task(title, time);
            mainTaskList.add(newTask);
            showTask();
        }
    }


    @Override
    public void changeTask() {
        int variantOfTask;
        int i = 1;
        System.out.println("Список доступных для изменения задач: \n");
        if (mainTaskList.size() == 0) {
            System.out.println("Ваш список задач пуст! \n"
                                + "Добавте задачи для их редактирования.");
        } else {
            for (Task task : mainTaskList) {
                System.out.println(i + ". " + task.getTitle());
                i++;
            }
            System.out.println("Выберете номер задачи, которую хотите изменить \n"
                                + "Ввод: ");
            variantOfTask = new Scanner(System.in).nextInt();
            while (variantOfTask <= 0 || variantOfTask > mainTaskList.size()) {
                System.out.println("Некоректный номер задачи");
                variantOfTask = new Scanner(System.in).nextInt();
            }
            variantOfTask--;
            view.printSubMenu_2(mainTaskList.getTask(variantOfTask));
        }


    }

    @Override
    public void showTask() {
        for (Task task : mainTaskList) {
            System.out.println(task.printInfo());
        }
    }
    @Override
    public void showTaskListForInterval(){

    }

    @Override
    public void removeTask() {

    }

    @Override
    public void removeAllTasks() {
    }

    @Override
    public void finishWork() {
        System.exit(0);
    }

    @Override
    public void reminder() {
       /* Создадим поток для того что бы он шёл паралельно с
        программой и напоминал о скором выволнении задачи*/
       // ThreadForRemind remind = new ThreadForRemind(mainTaskList);
    }

    @Override
    public void saveAllInfoToFileSystem() {
        /*Сохраняем всю инфу в файловую систему*/
    }


}
