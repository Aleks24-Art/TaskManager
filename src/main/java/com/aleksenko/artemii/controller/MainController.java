package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.enums.FileDeleteValue;
import com.aleksenko.artemii.view.MainView;
import com.aleksenko.artemii.model.*;
import com.aleksenko.artemii.model.Tasks;
import org.apache.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
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
    private final File tasks = new File("src\\main\\resources\\tasks.txt");

    /**
     * Constructor with app start method
     */
    public MainController() {
        askAboutTaskList();
        final File log = new File("src\\main\\resources\\info.log");
        clearFile(log, FileDeleteValue.LOG);
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
        String variantOfTask = view.getSubMenuVariant_1();
        String title;
        LocalDateTime time, start, end;
        int interval;
        switch (variantOfTask) {
            case "0":
                break;
            case "1":
                title = view.getTaskTitle();
                view.printSubMenuToRepeatedTaskCreating();
                while (true) {
                    start = addStart();
                    end = addEnd();
                    if (start.isBefore(end)) {
                        break;
                    }
                }
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
        view.printTaskList(mainTaskList);
        menuVariant = view.getNumberVariantOfTask();
        if (menuVariant == 0) {
            startWork();
        } else {
            menuVariant--;
            taskToChange = mainTaskList.getTask(menuVariant);
            view.printSubMenu_2(taskToChange);
            changes(taskToChange);
            logger.info("Задача" + taskToChange.getTitle() + " была изменена");
            view.printChangeStatus(taskToChange);
        }

    }

    @Override
    public void showTask() {
        view.printTaskList(mainTaskList);
        logger.debug("Пользователь вызвал весь список задач. Количество выведеных задач = " + mainTaskList.size());
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
        view.printTaskList(mainTaskList);
        menuVariant = view.getNumberVariantOfTask();
        if (menuVariant == 0) {
            startWork();
        } else {
            menuVariant--;
            taskForDelete = mainTaskList.getTask(menuVariant);
            view.printRemoveStatus(taskForDelete, mainTaskList.remove(taskForDelete));
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
        view.closeBufferedReader();
        logger.info("Завершение программы");
        System.exit(0);
    }

    @Override
    public void changeRepeated() {
        int variantOfTask;
        Task taskForChange;
        view.printTaskList(mainTaskList);
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
                clearFile(tasks, FileDeleteValue.TASK);
                break;
            case "3":
                view.printSubMenuToAddingUserFile();
                try {
                    view.getReader().readLine();
                } catch (IOException e) {
                    logger.error("Ошибка при потке задержать консоль" + e);
                }
                System.exit(0);
                break;
        }
    }

    private void showTaskListForUserInterval() {
        LocalDateTime startOfCalendar, endOfCalendar;
        startOfCalendar = view.getTaskDate();
        endOfCalendar = view.getTaskDate();
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(mainTaskList, startOfCalendar, endOfCalendar);
        view.printCalendar(calendar);
    }

    private void showTaskListForWeekInterval() {
        SortedMap<LocalDateTime, Set<Task>> calendar = Tasks.calendar(mainTaskList, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));
        view.printCalendar(calendar);
    }

    private LocalDateTime addStart() {
        LocalDateTime start;
        start = view.getTaskDate();
        return start;
    }

    private LocalDateTime addEnd() {
        LocalDateTime end;
        end = view.getTaskDate();
        while (end.compareTo(LocalDateTime.now()) <= 0) {
            end = view.getTaskDate();
        }
        return end;
    }

    private void toRepeated(Task task) {
        LocalDateTime start, end;
        task.setTime(null);
        task.setRepeated(true);
        start = addStart();
        end = addEnd();
        while (start.isAfter(end)) {
            start = addStart();
            end = addEnd();
        }
        task.setStart(start);
        task.setEnd(end);
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
            LocalDateTime newStart = null, newEnd = null;
            switch (view.variantOfChange(true)) {
                case "1":
                    String newTitle;
                    newTitle = view.getTaskTitle();
                    logger.debug("Поле Title для задачи " + task.getTitle() + " было изменено на " + newTitle);
                    task.setTitle(newTitle);
                    break;
                case "2":
                    while (true) {
                        newStart = view.getTaskDate();
                        if (newStart.isBefore(task.getEnd())) {
                            break;
                        } else {
                            view.compareStartEnd(newStart, task.getEnd());
                        }
                    }
                    task.setStart(newStart);
                    logger.debug("Поле Start для задачи " + task.getTitle() + " было изменено на " + newStart);
                    break;
                case "3":
                    while (true) {
                        newEnd = view.getTaskDate();
                        if (task.getStart().isBefore(newEnd)) {
                            break;
                        } else {
                            view.compareStartEnd(task.getStart(), newEnd);
                        }
                    }
                    task.setEnd(newEnd);
                    logger.debug("Поле End для задачи " + task.getTitle() + " было изменено на " + newEnd);
                    break;
                case "4":
                    int newInterval;
                    newInterval = view.createInterval();
                    task.setInterval(newInterval);
                    logger.debug("Поле Interval для задачи " + task.getTitle() + " было изменено на " + newInterval);
                    break;
                case "5":
                    changeActive(view.variantOfActivation(), task);
                    break;
            }
        } else {
            switch (view.variantOfChange(false)) {
                case "1":
                    String newTitle;
                    newTitle = view.getTaskTitle();
                    logger.debug("Поле Title для задачи " + task.getTitle() + " было изменено на " + newTitle);
                    task.setTitle(newTitle);
                    break;
                case "2":
                    LocalDateTime newTime;
                    newTime = view.getTaskDate();
                    task.setTime(newTime);
                    logger.debug("Поле Time для задачи " + task.getTitle() + " было изменено на " + newTime);
                    break;
                case "3":
                    changeActive(view.variantOfActivation(), task);
            }

        }
    }

    private void changeActive(String s, Task task) {
        switch (s) {
            case "1":
                task.setActive(true);
                logger.debug("Поле Active для задачи " + task.getTitle() + " было активировано");
                break;
            case "2":
                task.setActive(false);
                logger.debug("Поле Active для задачи " + task.getTitle() + " было деактивировано");
                break;
        }
    }

    private void clearFile(File file, FileDeleteValue value) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            if (value == FileDeleteValue.TASK) {
                bw.write("");
            } else {
                bw.write("{\"capacity\":0,\"tasks\":[null]}");
            }
        } catch (IOException e) {
            logger.error("Ошибка записи в файл " + e);
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                logger.error("Ошибка при закрытии потока " + e);
            }
        }
    }
}
