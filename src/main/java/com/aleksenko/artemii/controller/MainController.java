package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.view.MainView;
import com.aleksenko.artemii.model.*;
import com.aleksenko.artemii.model.Tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainController implements Controller{
    private MainView view = new MainView();
    private AbstractTaskList mainTaskList = new ArrayTaskList();
    private File tasks = new File("src\\main\\java\\com\\aleksenko\\artemii\\tasks\\tasks.txt");

    public AbstractTaskList getMainTaskList() {
        return mainTaskList;
    }

    @Override
    public void startWork() {
        try {
            TaskIO.readText(mainTaskList, tasks);
        } catch (FileNotFoundException e) {
            System.out.println("Сохранённые файлы отсутствуют");
        }
        String menuVariant;
        view.printTitle();
        while(true) {
                view.printMenu();
                menuVariant = new Scanner(System.in).nextLine();
        while (!(menuVariant.matches("^[1-7]$"))) {
            System.out.println("Некоректный ввод номера! \n" +
                                "Повторите ввод с доступными пунктами меню (1-7)");
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
                        break;
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
            System.out.println("Некоректный ввод номера! \n" +
                    "Повторите ввод с доступными пунктами меню (1-2)");
            variantOfTask = new Scanner(System.in).nextLine();
        }

        if (variantOfTask.equals("1")) {
            String title;
            int interval;
            LocalDateTime start, end;
            System.out.println("Введите название задачи " +
                                "\nПример: Убрать в шкафу" +
                                "\nВвод:");
            title = new Scanner(System.in).nextLine();
            System.out.println("Для создания повторяющейся задачи необходимо ввести:\n" +
                                "• Начало выполнения задачи\n" +
                                "• Конец выполнения задачи\n" +
                                "• Интервал повторения\n");
            System.out.println("Ввод начала выполнения задачи...");
            start = createTaskDate();
            while (start.compareTo(LocalDateTime.now()) <= 0) {
                System.out.println("Дата выполнения задачи не может быть в прошлом");
                start = createTaskDate();
            }
            System.out.println("Ввод конца выполнения задачи...");
            end =  createTaskDate();
            while (end.compareTo(LocalDateTime.now()) <= 0) {
                System.out.println("Дата выполнения задачи не может быть в прошлом");
                end =  createTaskDate();
            }

            System.out.println("Ввод интервала повторения задачи...");
            interval = createInterval();

            Task newTask = new Task(title, start, end, interval);
            mainTaskList.add(newTask);
            System.out.println("Задача " + newTask.getTitle() + " успешно добавлена");
        }

        if (variantOfTask.equals("2")) {
            String title;
            LocalDateTime time;

            System.out.println("Введите название задачи " +
                                "\nПример: Сходить в магазин" +
                                "\nВвод:");
            title = new Scanner(System.in).nextLine();

            time = createTaskDate();
            while (time.compareTo(LocalDateTime.now()) <= 0) {
                System.out.println("Дата выполнения задачи не может быть в прошлом");
                time = createTaskDate();
            }
            Task newTask = new Task(title, time);
            mainTaskList.add(newTask);
            System.out.println("Задача " + newTask.getTitle() + " успешно добавлена");
        }
    }


    @Override
    public void changeTask() {
        int variantOfTask;
        Task taskToChange;
        boolean flagForCheck = true;
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
            while(flagForCheck) {
                flagForCheck = false;
                try {
                    System.out.println("Выберете номер задачи, которую хотите изменить \n"
                                + "Ввод: ");
                    variantOfTask = new Scanner(System.in).nextInt();
                    if (variantOfTask <= 0 || variantOfTask > mainTaskList.size()) {
                        throw new InputMismatchException();
                    }
                    variantOfTask--;
                    taskToChange = mainTaskList.getTask(variantOfTask);
                    view.printSubMenu_2(taskToChange);
                    changes(taskToChange);
                } catch (InputMismatchException e2) {
                    flagForCheck = true;
                    System.out.println("Некоректный номер задачи");
                    }
            }
        }
    }

    @Override
    public void showTask() {
        if (mainTaskList.size() == 0) {
            System.out.println("Список задач пуст");
        }
        for (Task task : mainTaskList) {
            System.out.println(task.printInfo());
        }
    }
    @Override
    public void showTaskListForInterval() {
        LocalDateTime startOfCalendar, endOfCalendar;
        System.out.println("Что бы посмотреть календарь задач необходим определённый отрезок времени");
        System.out.println("Ввод начала отрезка времени...");
        startOfCalendar = createTaskDate();
        System.out.println("Ввод конца отрезка времени...");
        endOfCalendar = createTaskDate();
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu hh:mm");
        SortedMap <LocalDateTime, Set<Task>> calendar = Tasks.calendar(mainTaskList, startOfCalendar, endOfCalendar);

        for (Map.Entry<LocalDateTime, Set<Task>> map : calendar.entrySet()) {
            for (Task task : map.getValue()) {
                LocalDateTime key = map.getKey();
                System.out.println(key.format(dTF) + " " + task.printInfo() +"\n");
            }
        }
    }

    @Override
    public void removeTask() {
        if (mainTaskList.size() == 0) {
            System.out.println("Список задач пуст!");
        }
        boolean flagForCheck = true;
        int i = 1, variantOfTask = 0;
        System.out.println("Список доступных для удаление задач: ");
        for (Task task : mainTaskList) {
            System.out.println(i + ". " + task.printInfo());
            i++;
        }
        System.out.println("Выберете номер задачи которую хотите удалить");
        while (flagForCheck) {
            flagForCheck = false;
            try {
                variantOfTask = new Scanner(System.in).nextInt();
                if (variantOfTask < 1 || variantOfTask > mainTaskList.size()) {
                    throw new InputMismatchException();
                }
                variantOfTask--;
            } catch (InputMismatchException e1) {
                flagForCheck = true;
                System.out.println("Недопустимый ввод \n" +
                        "Ввод: ");
            }
        }
        mainTaskList.remove(mainTaskList.getTask(variantOfTask));
        System.out.println("Задача успешно удалена!");
    }

    @Override
    public void removeAllTasks() {
        mainTaskList = new ArrayTaskList();
        System.out.println("Все задачи успешно удалены!");
    }

    @Override
    public void finishWork() {
        try {
            TaskIO.writeText(mainTaskList, tasks);
        } catch (IOException e) {
            System.out.println("Ошибка в записи в файл");
            e.printStackTrace();
        }
        System.out.println("Удачного Вам дня!");
        System.exit(0);
    }


    private LocalDateTime createTaskDate() {
        int thisYear = LocalDateTime.now().getYear();
        boolean flagForCheck = true;
        int year = 1, month = 1, day = 1, hour = 1, minute = 1;
        LocalDateTime taskDate = LocalDateTime.of(year, month, day, hour, minute);

        while (flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие год: ");
                year = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e1) {
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
            if (year < thisYear || year > 2100) {
                flagForCheck = true;
                System.out.println("Повторите ввод с доступным диапазоном времени (" + thisYear + " - 2100)");
            }
        }


        flagForCheck = true;
        while(flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие номер месяца: ");
                month = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                flagForCheck = true;
                System.out.println("Повторите ввод с доступным диапазоном месяцев (1 - 12)");
            } catch (InputMismatchException e2) {
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        flagForCheck = true;
        while(flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие номер дня месяца: ");
                day = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                flagForCheck = true;
                System.out.println("Недопустимое количество дней в введёном месяце");
            } catch (InputMismatchException e2) {
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        flagForCheck = true;
        while(flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие час в сутках: ");
                hour = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                flagForCheck = true;
                System.out.println("Недопустимый час в сутках!");
            } catch (InputMismatchException e2) {
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        flagForCheck = true;
        while(flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие минуты в часе: ");
                minute = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                flagForCheck = true;
                System.out.println("Недопустимые минуты в часе!");
            } catch (InputMismatchException e2) {
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        return taskDate;
    }
    private int createInterval() {
        String variantOfInterval;
        System.out.println("Введите формат повторения \n"
                + "1. Каждый час \n"
                + "2. Каждый день \n"
                + "3. Каждую неделю \n"
                + "Ввод: ");
        variantOfInterval = new Scanner(System.in).nextLine();
        while (!(variantOfInterval.matches("^[1-3]$"))) {
                System.out.println("Некорктный ввод! \n" +
                                    "Введите формат повторения: ");
                variantOfInterval = new Scanner(System.in).nextLine();
        }
        switch (variantOfInterval) {
            case "1" :
                return  1;
            case "2" :
                return  24;
            case "3" :
                return  168;
        }
        return 0;
    }
    private void changes(Task task) {
        String variantOfChange;
        String newTitle;
        int newInterval;
        String newActive;
        LocalDateTime newTime;
        LocalDateTime newStart;
        LocalDateTime newEnd;
        if (task.isRepeated()) {
            variantOfChange = new Scanner(System.in).nextLine();
            while (!(variantOfChange.matches("^[1-5]$"))) {
                System.out.println("Некоректный ввод");
                variantOfChange = new Scanner(System.in).nextLine();
            }
            switch (variantOfChange) {
                case "1" :
                    System.out.println("Введите новое название задачи: ");
                    newTitle = new Scanner(System.in).nextLine();
                    task.setTitle(newTitle);
                    break;
                case "2" :
                    System.out.println("Введите новое время старта выполнения задачи: ");
                    newStart = createTaskDate();
                    task.setStart(newStart);
                    break;
                case "3" :
                    System.out.println("Введите новое время конца выполнения задачи: ");
                    newEnd = createTaskDate();
                    task.setEnd(newEnd);
                    break;
                case "4" :
                    System.out.println("Введите новый интервал выполненя задачи: ");
                    newInterval = createInterval();
                    task.setInterval(newInterval);
                    break;
                case "5" :
                    System.out.println("1. Активация задачи.");
                    System.out.println("2. Деактивация задачи.");
                    newActive = new Scanner(System.in).nextLine();
                    while (!(newActive.matches("^[1-2]$"))){
                        System.out.println("Некоректный ввод");
                    }
                    switch (newActive) {
                        case "1" :
                            task.setActive(true);
                            break;
                        case "2" :
                            task.setActive(false);
                            break;
                    }
            }
        } else {
            variantOfChange = new Scanner(System.in).nextLine();
            while (!(variantOfChange.matches("^[1-3]$"))) {
                System.out.println("Некоректный ввод");
                variantOfChange = new Scanner(System.in).nextLine();
            }
            switch (variantOfChange) {
                case "1":
                    System.out.println("Введите новое название задачи: ");
                    newTitle = new Scanner(System.in).nextLine();
                    task.setTitle(newTitle);
                    break;
                case "2":
                    System.out.println("Введите новое время выполнения задачи: ");
                    newTime = createTaskDate();
                    task.setTime(newTime);
                    break;
                case "3" :
                    System.out.println("1. Активация задачи.");
                    System.out.println("2. Деактивация задачи.");
                    newActive = new Scanner(System.in).nextLine();
                    while (!(newActive.matches("^[1-2]$"))){
                        System.out.println("Некоректный ввод");
                    }
                    switch (newActive) {
                        case "1" :
                            task.setActive(true);
                            break;
                        case "2" :
                            task.setActive(false);
                            break;
                    }
            }

        }
    }
}
