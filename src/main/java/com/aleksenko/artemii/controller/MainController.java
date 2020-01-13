package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.view.MainView;
import com.aleksenko.artemii.model.*;
import com.aleksenko.artemii.model.Tasks;
import org.apache.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainController implements Controller {
    private final Logger logger = Logger.getLogger(MainController.class);
    private MainView view = new MainView();
    public static AbstractTaskList mainTaskList = new ArrayTaskList();
    private File tasks = new File("src\\main\\resources\\tasks.txt");
    private File log = new File("src\\main\\resources\\info.log");

    public MainController() {
        startWork();
    }

    AbstractTaskList getMainTaskList() {
        return mainTaskList;
    }

    @Override
    public void startWork() {
        clearLogFile(log);
        TaskIO.readText(mainTaskList, tasks);
        view.printTitle();
        while (true) {
            view.printMenu();
            switch (view.getMenuVariant()) {
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
                    break;
                case "7":
                    finishWork();
                    break;
            }
        }
    }

    @Override
    public void addTask() {
        boolean flagForCheck = true; // Для проверки что вторая дата будет после первой
        String variantOfTask = view.getSubMenuVariant_1();
        String title;
        LocalDateTime time, start = null, end = null;
        int interval;
        if (variantOfTask.equals("1")) {
            title = view.getTaskTitle();
            System.out.println("Для создания повторяющейся задачи необходимо ввести:\n" +
                    "• Начало выполнения задачи\n" +
                    "• Конец выполнения задачи\n" +
                    "• Интервал повторения\n");
            while (flagForCheck) {

                System.out.println("Ввод начала выполнения задачи...");
                start = view.getTaskDate();
                while (start.compareTo(LocalDateTime.now()) <= 0) {
                    System.out.println("Дата выполнения задачи не может быть в прошлом");
                    start = view.getTaskDate();
                }
                System.out.println("Ввод конца выполнения задачи...");
                end = view.getTaskDate();
                while (end.compareTo(LocalDateTime.now()) <= 0) {
                    System.out.println("Дата выполнения задачи не может быть в прошлом");
                    end = view.getTaskDate();
                }
                if (start.isBefore(end)) {
                    flagForCheck = false;
                } else {
                    flagForCheck = true;
                    System.out.println("Начало задаччи не может быть после её окончания");
                }
            }


            System.out.println("Ввод интервала повторения задачи...");
            interval = view.createInterval();

            Task newTask = new Task(title, start, end, interval);
            mainTaskList.add(newTask);
            logger.info("Добавлена задача с повторением" + newTask.getTitle());
            view.printAddStatus(newTask);
        } else {
            title = view.getTaskTitle();
            time = view.getTaskDate();
            while (time.compareTo(LocalDateTime.now()) <= 0) {
                System.out.println("Дата выполнения задачи не может быть в прошлом");
                time = view.getTaskDate();
            }
            Task newTask = new Task(title, time);
            mainTaskList.add(newTask);
            logger.info("Добавлена задача без повторения" + newTask.getTitle());
            view.printAddStatus(newTask);
        }
    }


    @Override
    public void changeTask() {
        Task taskToChange;
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
           System.out.println("Выберете номер задачи которую хотите изменить");
           taskToChange = mainTaskList.getTask(view.getSubMenuVariant());
           view.printSubMenu_2(taskToChange);
           changes(taskToChange);
           logger.info("Задача" + taskToChange + " была изменена");
           view.printChangeStatus(taskToChange);
        }
    }

    @Override
    public void showTask() {
        if (mainTaskList.size() == 0) {
            System.out.println("Список задач пуст");
        } else {
            for (Task task : mainTaskList) {
                System.out.println(task.printInfo());
                logger.debug("Пользователь вызвал весь список задач");
            }
        }

    } // Все правки внесены

    @Override
    public void showTaskListForInterval() {
        LocalDateTime startOfCalendar, endOfCalendar;
        System.out.println("Что бы посмотреть календарь задач необходим определённый отрезок времени");
        System.out.println("Ввод начала отрезка времени...");
        startOfCalendar = view.getTaskDate();
        System.out.println("Ввод конца отрезка времени...");
        endOfCalendar = view.getTaskDate();
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu hh:mm");
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(mainTaskList, startOfCalendar, endOfCalendar);

        for (Map.Entry<LocalDateTime, Set<Task>> map : calendar.entrySet()) {
            for (Task task : map.getValue()) {
                LocalDateTime key = map.getKey();
                System.out.println(key.format(dTF) + " " + task.printInfo() + "\n");
            }
        }
    } // Все правки внесены

    @Override
    public void removeTask() {
        Task taskForDelete;
        if (mainTaskList.size() == 0) {
            System.out.println("Список задач пуст!");
        } else {
            int i = 1;
            System.out.println("Список доступных для удаление задач: ");

            for (Task task : mainTaskList) {
                System.out.println(i + ". " + task.printInfo());
                i++;
            }
            System.out.println("Выберете номер задачи которую хотите удалить");
            taskForDelete = mainTaskList.getTask(view.getSubMenuVariant());
            mainTaskList.remove(taskForDelete);
            logger.info("Задача " + taskForDelete.getTitle() + " удалена");
            view.printRemoveStatus(taskForDelete);
        }
    } // Все правки внесены

    @Override
    public void removeAllTasks() {
        mainTaskList = new ArrayTaskList();
        view.printRemoveAllStatus();
    } // Все правки внесены

    @Override
    public void finishWork() {
        TaskIO.writeText(mainTaskList, tasks);
        System.out.println("Удачного Вам дня!");
        logger.info("Завершение программы");
        System.exit(0);
    } // Все правки внесены


    private void changes(Task task) {
        if (task.isRepeated()) {
            switch (view.variantOfChange(true)) {
                case "1":
                    System.out.println("Введите новое название задачи: ");
                    task.setTitle(view.getTaskTitle());
                    logger.debug("Поле Title было изменено");
                    break;
                case "2":
                    System.out.println("Введите новое время старта выполнения задачи: ");
                    task.setStart(view.getTaskDate());
                    logger.debug("Поле Start было изменено");
                    break;
                case "3":
                    System.out.println("Введите новое время конца выполнения задачи: ");
                    task.setEnd(view.getTaskDate());
                    logger.debug("Поле End было изменено");
                    break;
                case "4":
                    System.out.println("Введите новый интервал выполненя задачи: ");
                    task.setInterval(view.createInterval());
                    logger.debug("Поле Interval было изменено");
                    break;
                case "5":
                    changeActive(view.variantOfActivation(), task);
            }
        } else {
            switch (view.variantOfChange(false)) {
                case "1":
                    System.out.println("Введите новое название задачи: ");
                    task.setTitle(view.getTaskTitle());
                    logger.debug("Поле Title было изменено");
                    break;
                case "2":
                    System.out.println("Введите новое время выполнения задачи: ");
                    task.setTime(view.getTaskDate());
                    logger.debug("Поле Time было изменено");
                    break;
                case "3":
                    changeActive(view.variantOfActivation(), task);
            }

        }
    } // Все правки внесены
    private void changeActive(String s, Task task){
        switch (s) {
            case "1":
                task.setActive(true);
                logger.debug("Поле Active было активировано");
                break;
            case "2":
                task.setActive(false);
                logger.debug("Поле Active было деактивировано");
                break;
        }
    } // Все правки внесены
    private void clearLogFile(File file) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("");
        } catch (IOException e) {
            logger.error("Произошла ошибка при очистке файла info.log");
        }

        try {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при очистке файла info.log");
        }
    } // Все правки внесены
}
