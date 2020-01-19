package com.aleksenko.artemii.view;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.model.Task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class MainView implements View {
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(MainView.class);

    @Override
    public void printTitle() {
        final String YELLOW_BOLD = "\033[1;33m";
        System.out.println(YELLOW_BOLD + "Вас приветствует task manager 'Easy to do!' \n");
    }

    @Override
    public void printMenu() {
        System.out.println("Меню");
        System.out.println("**************************************************");
        System.out.println("1. Добавить новую задачу");
        System.out.println("2. Изменить существующею задачу");
        System.out.println("3. Просмотреть существующие задачи");
        System.out.println("4. Просмотреть список задач на ближайшее время");
        System.out.println("5. Удалить задачу");
        System.out.println("6. Удалить все задачи");
        System.out.println("7. Изенить формат повторения задачи");
        System.out.println("8. Завершить работу 'Easy to do!'");
        System.out.println("**************************************************\n");
        System.out.println("Ввод: ");
    }

    @Override
    public String getMenuVariant() {
        String menuVariant;
        menuVariant = new Scanner(System.in).nextLine();
        while (!(menuVariant.matches("^[1-8]$"))) {
            System.out.println("Некоректный ввод номера! \n" +
                    "Повторите ввод с доступными пунктами меню (1-8)");
            menuVariant = new Scanner(System.in).nextLine();
        }
        return menuVariant;
    }

    @Override
    public String getSubMenuVariant_1() {
        String variantOfTask;

        System.out.println("Введите '0' что бы выйти в меню");
        System.out.println("1. Добавить задачу с повторением");
        System.out.println("2. Добавить задачу без повторения");

        variantOfTask = new Scanner(System.in).nextLine();
        while (!(variantOfTask.matches("^[0-2]$"))) {
            System.out.println("Некоректный ввод номера! \n" +
                    "Повторите ввод с доступными пунктами меню (0-2)");
            variantOfTask = new Scanner(System.in).nextLine();
        }
        return variantOfTask;
    }

    @Override
    public String getSubMenuVariant_4() {
        String menuVariant;
        System.out.println("Выберете вариант рассписания: ");
        System.out.println("Введите '0' что бы выйти меню");
        System.out.println("1. Посмотреть рассписание на неделю");
        System.out.println("2. Задать интервал рассписания самому");
        System.out.println("Ввод: ");
        menuVariant = new Scanner(System.in).nextLine();
        while (!(menuVariant.matches("^[0-2]$"))) {
            System.out.println("Некоректный ввод номера! \n" +
                    "Повторите ввод с доступными пунктами меню (0-2)");
            menuVariant = new Scanner(System.in).nextLine();
        }
        return menuVariant;
    }

    @Override
    public void printAddStatus(Task task) {
        System.out.println("Задача " + task.getTitle() + " успешно добавлена");
    }

    @Override
    public void printRemoveStatus(Task task, boolean isDeleted) {
        if (isDeleted) {
            logger.info("Задача " + task.getTitle() + " удалена");
            System.out.println("Задача " + task.getTitle() + " успешно удалена");
        } else {
            logger.error("Задача не была удалена!");
            System.out.println("Задача " + task.getTitle() + " не была далена удалена");
        }

    }


    @Override
    public void printRemoveAllStatus() {
        System.out.println("Все задачи были удалены");
    }

    @Override
    public void printChangeStatus(Task task) {
        System.out.println("Задача " + task.getTitle() + " успешно изменена");
    }

    @Override
    public LocalDateTime getTaskDate() {
        int thisYear = LocalDateTime.now().getYear();
        boolean flagForCheck = true;
        int year = 1, month = 1, day = 1, hour = 1, minute = 1;
        LocalDateTime taskDate = LocalDateTime.of(year, month, day, hour, minute);

        while (flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введите год: ");
                year = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e1) {
                logger.error("Пользыватель ввёл некоректные данные " + e1);
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
            if (year < thisYear || year > 2100) {
                logger.warn("Пользыватель ввёл некоректный год - " + year);
                flagForCheck = true;
                System.out.println("Повторите ввод с доступным диапазоном времени (" + thisYear + " - 2100)");
            }
        }


        flagForCheck = true;
        while (flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие номер месяца: ");
                month = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                logger.error("Пользыватель ввёл некоректную дату " + e1);
                flagForCheck = true;
                System.out.println("Повторите ввод с доступным диапазоном месяцев (1 - 12)");
            } catch (InputMismatchException e2) {
                logger.error("Пользыватель ввёл некоректные данные " + e2);
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        flagForCheck = true;
        while (flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие номер дня месяца: ");
                day = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                logger.error("Пользыватель ввёл некоректную дату " + e1);
                flagForCheck = true;
                System.out.println("Недопустимое количество дней в введёном месяце");
            } catch (InputMismatchException e2) {
                logger.error("Пользыватель ввёл некоректные данные " + e2);
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        flagForCheck = true;
        while (flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие час в сутках: ");
                hour = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                logger.error("Пользыватель ввёл некоректную дату " + e1);
                flagForCheck = true;
                System.out.println("Недопустимый час в сутках!");
            } catch (InputMismatchException e2) {
                logger.error("Пользыватель ввёл некоректные данные " + e2);
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        flagForCheck = true;
        while (flagForCheck) {
            flagForCheck = false;
            try {
                System.out.println("Введтие минуты в часе: ");
                minute = new Scanner(System.in).nextInt();
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
            } catch (DateTimeException e1) {
                logger.error("Пользыватель ввёл некоректную дату " + e1);
                flagForCheck = true;
                System.out.println("Недопустимые минуты в часе!");
            } catch (InputMismatchException e2) {
                logger.error("Пользыватель ввёл некоректные данные " + e2);
                flagForCheck = true;
                System.out.println("Некорктный ввод!");
            }
        }

        return taskDate;
    }

    @Override
    public String getTaskTitle() {
        System.out.println("Введите название задачи " +
                "\nПример: Убрать в шкафу" +
                "\nВвод:");
        return new Scanner(System.in).nextLine();
    }

    @Override
    public int getNumberVariantOfTask() {
        boolean flagForCheck = true;
        int variantOfTask = 0;

        while (flagForCheck) {
            flagForCheck = false;
            try {
                variantOfTask = new Scanner(System.in).nextInt();
                if (variantOfTask != 0 && (variantOfTask < 1 || variantOfTask > MainController.mainTaskList.size())) {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e1) {
                logger.error("Пользыватель ввёл некоректные данные " + variantOfTask);
                flagForCheck = true;
                System.out.println("Недопустимый ввод \n" +
                        "Ввод: ");
            }
        }
        return variantOfTask;
    }

    @Override
    public String variantOfRemove() {
        //boolean flagForCheck = true;
        String menuVariant;
        System.out.println("Удалить все задачи?");
        System.out.println("0. Нет");
        System.out.println("1. Да");
        menuVariant = new Scanner(System.in).nextLine();
        while (!(menuVariant.matches("^[0-1]$"))) {
            System.out.println("Некоректный ввод номера! \n" +
                    "Повторите ввод с доступными пунктами меню (0-1)" +
                    "Ввод: ");
            menuVariant = new Scanner(System.in).nextLine();
        }
        return menuVariant;
    }

    @Override
    public String variantOfTaskList() {
        String menuVariant;
        System.out.println("Выберите вариант работы со списком задач: ");
        System.out.println("1. Продолжить список с предыдущего запуска");
        System.out.println("2. Продолжить работу с новым списком задач (все предыдущее задачи будут удалены)");
        System.out.println("3. Продолжить работу со своим (интегрированным) списком задач");
        menuVariant = new Scanner(System.in).nextLine();
        while (!(menuVariant.matches("^[1-3]$"))) {
            System.out.println("Некоректный ввод номера! \n" +
                    "Повторите ввод с доступными пунктами меню (1-3)" +
                    "Ввод: ");
            menuVariant = new Scanner(System.in).nextLine();
        }
        return menuVariant;
    }

    @Override
    public String variantOfChange(boolean isRepeated) {
        String variantOfChange;
        if (isRepeated) {
            variantOfChange = new Scanner(System.in).nextLine();
            while (!(variantOfChange.matches("^[1-5]$"))) {
                System.out.println("Некоректный ввод");
                variantOfChange = new Scanner(System.in).nextLine();
            }
        } else {
            variantOfChange = new Scanner(System.in).nextLine();
            while (!(variantOfChange.matches("^[1-3]$"))) {
                System.out.println("Некоректный ввод");
                variantOfChange = new Scanner(System.in).nextLine();
            }
        }
        return variantOfChange;
    }

    @Override
    public int createInterval() {
        int interval;
        System.out.println("Введите формат повторения в миунтах\n"
                + "Подсказка: \n"
                + "В одном часе - 60 минут \n"
                + "В одном дне - 1440 минут \n"
                + "В одной неделе - 10080 минут \n"
                + "Ввод: ");
        interval = new Scanner(System.in).nextInt();
        while (interval < 1) {
            System.out.println("Некорктный ввод! \n" +
                    "Введите формат повторения: ");
            interval = new Scanner(System.in).nextInt();
        }
        return interval;
    }

    @Override
    public void printSubMenu_2(Task task) {
        if (task.isRepeated()) {
            System.out.println("Выберете параметр задачи который хотите изменить");
            System.out.println("1. Название задачи");
            System.out.println("2. Старт выполнения");
            System.out.println("3. Конец выполнения");
            System.out.println("4. Интервал повторения задачи");
            System.out.println("5. Активность задачи");
        } else {
            System.out.println("Выберете параметр задачи который хотите изменить");
            System.out.println("1. Название задачи");
            System.out.println("2. Время выполнения");
            System.out.println("3. Активность задачи");
        }
    }

    @Override
    public String variantOfActivation() {
        String newActive;
        System.out.println("1. Активация задачи.");
        System.out.println("2. Деактивация задачи.");
        newActive = new Scanner(System.in).nextLine();
        while (!(newActive.matches("^[1-2]$"))) {
            System.out.println("Некоректный ввод");
        }
        return newActive;
    }
}
