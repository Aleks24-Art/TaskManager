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
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(MainController.class);
    /**
     * Field to use methods from {@link MainView}
     */
    private MainView view = new MainView();
    /**
     * Field to save all tasks
     */
    public static AbstractTaskList mainTaskList = new ArrayTaskList();
    /**
     * Field to save all tasks
     */
    private File tasks = new File("src\\main\\resources\\tasks.txt");

    /**
     * Constructor with app start method
     */
    public MainController() {
        askAboutTaskList();
        File log = new File("src\\main\\resources\\info.log");
        clearLogFile(log);
        view.printTitle();
        startWork();
    }

    @Override
    public void startWork() {
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
                    changeRepeated();
                    break;
                case "8":
                    finishWork();
                    break;
            }
        }
    }

    @Override
    public void addTask() {
        Task newTask;
        boolean flagForCheck = true;
        String variantOfTask = view.getSubMenuVariant_1();
        String title;
        LocalDateTime time, start = null, end = null;
        int interval;
        switch (variantOfTask) {
            case "0":
                break;
            case "1":
                title = view.getTaskTitle();
                System.out.println("Для создания повторяющейся задачи необходимо ввести:\n" +
                        "• Начало выполнения задачи\n" +
                        "• Конец выполнения задачи\n" +
                        "• Интервал повторения\n");
                while (flagForCheck) {
                    start = addStart();
                    end = addEnd();
                    flagForCheck = compareStartEnd(start, end);
                }

                System.out.println("Ввод интервала повторения задачи...");
                interval = view.createInterval();

                newTask = new Task(title, start, end, interval);
                mainTaskList.add(newTask);
                logger.info("Добавлена задача с повторением" + newTask.getTitle());
                view.printAddStatus(newTask);
                break;
            case "2":
                title = view.getTaskTitle();
                time = view.getTaskDate();
                while (time.compareTo(LocalDateTime.now()) <= 0) {
                    System.out.println("Дата выполнения задачи не может быть в прошлом");
                    time = view.getTaskDate();
                }
                newTask = new Task(title, time);
                mainTaskList.add(newTask);
                logger.info("Добавлена задача без повторения" + newTask.getTitle());
                view.printAddStatus(newTask);
                break;
        }
    }

    @Override
    public void changeTask() {
        Task taskToChange;
        int menuVariant;
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
           System.out.println("Введите '0' для выхода в меню");
           menuVariant = view.getNumberVariantOfTask();
           if (menuVariant == 0) {
                startWork();
           } else {
               menuVariant--;
               taskToChange = mainTaskList.getTask(menuVariant);
               view.printSubMenu_2(taskToChange);
               changes(taskToChange);
               logger.info("Задача" + taskToChange + " была изменена");
               view.printChangeStatus(taskToChange);
           }

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

    }

    @Override
    public void showTaskListForInterval() {
        String menuVariant = view.getSubMenuVariant_4();
        switch (menuVariant) {
            case "0":
                startWork();
                break;
            case "1":
                showTaskListForWeekInterval();
                break;
            case "2":
                showTaskListForUserInterval();
                break;
        }

    }

    @Override
    public void removeTask() {
        Task taskForDelete;
        int menuVariant;
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
            System.out.println("Введите '0' для выхода в меню");
            menuVariant = view.getNumberVariantOfTask();
            if (menuVariant == 0) {
                startWork();
            } else {
                menuVariant--;
                taskForDelete = mainTaskList.getTask(menuVariant);
                view.printRemoveStatus(taskForDelete, mainTaskList.remove(taskForDelete));
            }
        }
    }

    @Override
    public void removeAllTasks() {
        String variantOfRemove = view.variantOfRemove();
        if (variantOfRemove.equals("0")) {
            startWork();
        } else {
            mainTaskList = new ArrayTaskList();
            view.printRemoveAllStatus();
        }
    }

    @Override
    public void finishWork() {
        TaskIO.writeText(mainTaskList, tasks);
        System.out.println("Удачного Вам дня!");
        logger.info("Завершение программы");
        new Scanner(System.in).nextLine();
        System.exit(0);
    }

    @Override
    public void changeRepeated() {
        int variantOfTask;
        Task taskForChange;
        System.out.println("Выберите задачу в которой хотите изменить формат повторения");
        System.out.println("Введите '0' для выхода в меню");
        int i = 1;
        if (mainTaskList.size() == 0) {
            System.out.println("Ваш список задач пуст! \n"
                    + "Добавте задачи для их редактирования.");
        } else {
            for (Task task : mainTaskList) {
                System.out.println(i + ". " + task.getTitle());
                i++;
            }

        }
        variantOfTask = view.getNumberVariantOfTask();
        if (variantOfTask != 0) {
            variantOfTask--;
            taskForChange = mainTaskList.getTask(variantOfTask);
            if (taskForChange.isRepeated()) {
                toUnRepeated(taskForChange);
            } else {
                toRepeated(taskForChange);
            }
        }
    }

    private void askAboutTaskList() {
        String menuVariant = view.variantOfTaskList();
        switch (menuVariant) {
            case "1":
                TaskIO.readText(mainTaskList, tasks);
                break;
            case "2":
                clearTasksFile(tasks);
                break;
            case "3":
                System.out.println("Для добавления своего списка задач необходимо соблюсти несколько обязательных пунктов: ");
                System.out.println("1. Ваш файл должен иметь название tasks.txt");
                System.out.println("2. Ваш файл должен находится по данному пути ...\\TaskManager\\target\\src\\main\\resources");
                System.out.println("3. Ваш файл должен соответствовать формату записи Json");
                System.out.println("4. Ваш файл должен содержать все необходимые поля задач");
                System.out.println("При невыполнии хотя бы одного пункта Вы начнете работу с пустым списком задач");
                System.out.println("Если по данному пути уже находится подобный файл,\n" +
                        "просто перезапишите его на необхоюимый Вам файл");
                System.out.println("Перезапустити программу (Enter) полсе выполнения всех действий и нажмите '1' ");
                new Scanner(System.in).nextLine();
                System.exit(0);
                break;
        }
    }
    private void showTaskListForUserInterval() {
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
    }
    private void showTaskListForWeekInterval() {
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu hh:mm");
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(mainTaskList, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));
        for (Map.Entry<LocalDateTime, Set<Task>> map : calendar.entrySet()) {
            for (Task task : map.getValue()) {
                LocalDateTime key = map.getKey();
                System.out.println(key.format(dTF) + " " + task.printInfo() + "\n");
            }
        }
    }
    private LocalDateTime addStart() {
        LocalDateTime start;
        System.out.println("Ввод начала выполнения задачи...");
        start = view.getTaskDate();
        while (start.compareTo(LocalDateTime.now()) <= 0) {
            System.out.println("Дата выполнения задачи не может быть в прошлом");
            start = view.getTaskDate();
        }
        return start;
    }
    private LocalDateTime addEnd() {
        LocalDateTime end;
        System.out.println("Ввод конца выполнения задачи...");
        end = view.getTaskDate();
        while (end.compareTo(LocalDateTime.now()) <= 0) {
            System.out.println("Дата выполнения задачи не может быть в прошлом");
            end = view.getTaskDate();
        }
        return end;
    }
    private boolean compareStartEnd(LocalDateTime start, LocalDateTime end) {
        boolean flagForCheck;
        if (start.isBefore(end)) {
            flagForCheck = false;
        } else {
            flagForCheck = true;
            System.out.println("Начало задачи не может быть после или во время её окончания");
        }
        return flagForCheck;
    }
    private void toRepeated(Task task) {
            task.setTime(null);
            task.setRepeated(true);
            task.setStart(addStart());
            task.setEnd(addEnd());
            task.setInterval(view.createInterval());
    }
    private void toUnRepeated(Task task) {
            task.setRepeated(false);
            task.setStart(null);
            task.setEnd(null);
            task.setInterval(0);
            task.setTime(view.getTaskDate());
    }
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
    }
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
    }
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
    }
    private void clearTasksFile(File file) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write("{\"capacity\":0,\"tasks\":[null]}");
        } catch (IOException e) {
            logger.error("Произошла ошибка при очистке файла info.log");
        }

        try {
            if (bw != null) {
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при очистке файла tasks.txt");
        }
    }
}
