package com.aleksenko.artemii.view;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.model.AbstractTaskList;
import com.aleksenko.artemii.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.log4j.Logger;

public class MainView implements View {
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(MainView.class);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
        String menuVariant = null;
        try {
            menuVariant = reader.readLine();
            while (!(menuVariant.matches("^[1-8]$"))) {
                System.out.println("Некоректный ввод!\n");
                menuVariant = reader.readLine();
            }
        } catch (IOException e) {
            logger.error("Ошбика при считывание " + e);
        }

        return menuVariant;
    }

    @Override
    public String getSubMenuVariant_1() {
        String variantOfTask = null;
        try {
            System.out.println("Введите '0' что бы выйти в меню");
            System.out.println("1. Добавить задачу с повторением");
            System.out.println("2. Добавить задачу без повторения");
            variantOfTask = reader.readLine();
            while (!(variantOfTask.matches("^[0-2]$"))) {
                System.out.println("Некоректный ввод номера! \n" +
                        "Повторите ввод с доступными пунктами меню (0-2)");
                variantOfTask = reader.readLine();
            }
        } catch (IOException e) {
            logger.error("Ошбика при считывание " + e);
        }
        return variantOfTask;
    }

    @Override
    public String getSubMenuVariant_4() {
        String menuVariant = null;
        System.out.println("Выберете вариант рассписания: ");
        System.out.println("Введите '0' что бы выйти меню");
        System.out.println("1. Посмотреть рассписание на неделю");
        System.out.println("2. Задать интервал рассписания самому");
        System.out.println("Ввод: ");
        try {
            menuVariant = reader.readLine();
            while (!(menuVariant.matches("^[0-2]$"))) {
                System.out.println("Некоректный ввод номера! \n" +
                        "Повторите ввод с доступными пунктами меню (0-2)");
                menuVariant = reader.readLine();
            }
            if (menuVariant.equals("2")) {
                System.out.println("Что бы посмотреть календарь задач необходим определённый отрезок времени");
            }
        } catch (IOException e) {
            logger.error("Ошбика при считывание " + e);
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
            logger.error("Задача " + task.getTitle() + " не была удалена!");
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
        int year, month, day = 1, hour = 1, minute = 1;
        LocalDateTime taskDate;
        System.out.println("Введите дату!");
        while (true) {
            try {
                System.out.println("Введите год: ");
                year = Integer.parseInt(reader.readLine());
                if (year < thisYear || year > 2100) {
                    logger.warn("Некорректный ввод года - " + year);
                    throw new NumberFormatException();
                }
                break;
            } catch (IOException | NumberFormatException e1) {
                logger.error("Некорректный ввод " + e1);
                System.out.println("Некорктный ввод!");
            }
        }

        while (true) {
            try {
                System.out.println("Введтие номер месяца: ");
                month = Integer.parseInt(reader.readLine());
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
                break;
            } catch (DateTimeException e1) {
                logger.error("Некорректный ввод " + e1);
                System.out.println("Повторите ввод с доступным диапазоном месяцев (1 - 12)");
            } catch (NumberFormatException e2) {
                logger.error("Некорректный ввод " + e2);
                System.out.println("Некорктный ввод!");
            } catch (IOException e3) {
                logger.error("Ошибка при считывании " + e3);
            }
        }

        while (true) {
            try {
                System.out.println("Введтие номер дня месяца: ");
                day = Integer.parseInt(reader.readLine());
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
                break;
            } catch (DateTimeException e1) {
                logger.error("Некорректный ввод " + e1);
                System.out.println("Недопустимое количество дней в введёном месяце");
            } catch (NumberFormatException e2) {
                logger.error("Некорректный ввод " + e2);
                System.out.println("Некорктный ввод!");
            } catch (IOException e3) {
                logger.error("Ошибка при считывании " + e3);
            }
        }

        while (true) {
            try {
                System.out.println("Введтие час в сутках: ");
                hour = Integer.parseInt(reader.readLine());
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
                break;
            } catch (DateTimeException e1) {
                logger.error("Некорректный ввод " + e1);
                System.out.println("Недопустимый час в сутках!");
            } catch (NumberFormatException e2) {
                logger.error("Некорректный ввод " + e2);
                System.out.println("Некорктный ввод!");
            } catch (IOException e3) {
                logger.error("Ошибка при считывании " + e3);
            }
        }

        while (true) {
            try {
                System.out.println("Введтие минуты в часе: ");
                minute = Integer.parseInt(reader.readLine());
                taskDate = LocalDateTime.of(year, month, day, hour, minute);
                break;
            } catch (DateTimeException e1) {
                logger.error("Некорректный ввод " + e1);
                System.out.println("Недопустимые минуты в часе!");
            } catch (NumberFormatException e2) {
                logger.error("Некорректный ввод " + e2);
                System.out.println("Некорктный ввод!");
            } catch (IOException e3) {
                logger.error("Ошибка при считывани " + e3);
            }
        }

        if (taskDate.isBefore(LocalDateTime.now())) {
            System.out.println("Дата не может быть в прошлом");
            taskDate = getTaskDate();
        }
        return taskDate;
    }

    @Override
    public String getTaskTitle() {
        System.out.println("Введите название задачи " +
                "\nПример: Убрать в шкафу" +
                "\nВвод:");
        try {
            return reader.readLine();
        } catch (IOException e) {
           logger.error("Ощибка при считывании " + e);
        }
        return null;
    }

    @Override
    public int getNumberVariantOfTask() {
        int variantOfTask;
        System.out.println("Выберете номер задачи для работы с ней");
        System.out.println("Введите '0' для выхода в меню");
        while (true) {
            try {
                variantOfTask = Integer.parseInt(reader.readLine());
                if (variantOfTask != 0 && (variantOfTask < 1 || variantOfTask > MainController.mainTaskList.size())) {
                    throw new NumberFormatException();
                } else {
                    break;
                }
            } catch (NumberFormatException e1) {
                logger.error("Некорректный ввод " + e1);
                System.out.println("Недопустимый ввод \n" +
                        "Ввод: ");
            } catch (IOException e2) {
                logger.error("Ошибка при считывании " + e2);
            }
        }
        return variantOfTask;
    }

    @Override
    public String variantOfRemove() {
        String menuVariant = null;
        System.out.println("Удалить все задачи?");
        System.out.println("0. Нет");
        System.out.println("1. Да");
        try {
            menuVariant = reader.readLine();
            while (!(menuVariant.matches("^[0-1]$"))) {
                System.out.println("Некоректный ввод номера! \n" +
                        "Повторите ввод с доступными пунктами меню (0-1)" +
                        "Ввод: ");
                menuVariant = reader.readLine();
            }
        } catch (IOException e1) {
            logger.error("Ошибка при считывании " + e1);
        }

        return menuVariant;
    }

    @Override
    public String variantOfTaskList() {
        String menuVariant = null;
        System.out.println("Выберите вариант работы со списком задач: ");
        System.out.println("1. Продолжить список с предыдущего запуска");
        System.out.println("2. Продолжить работу с новым списком задач (все предыдущее задачи будут удалены)");
        System.out.println("3. Продолжить работу со своим (интегрированным) списком задач");
        try {
            menuVariant = reader.readLine();
            while (!(menuVariant.matches("^[1-3]$"))) {
                System.out.println("Некоректный ввод номера! \n" +
                        "Повторите ввод с доступными пунктами меню (1-3)" +
                        "Ввод: ");
                menuVariant = reader.readLine();
            }
        } catch (IOException e1) {
            logger.error("Ошибка при считывании " + e1);
        }
        return menuVariant;
    }

    @Override
    public String variantOfChange(boolean isRepeated) {
        String variantOfChange = null;
        try {
            if (isRepeated) {
                variantOfChange = reader.readLine();
                while (!(variantOfChange.matches("^[1-5]$"))) {
                    System.out.println("Некоректный ввод");
                    variantOfChange = reader.readLine();
                }
            } else {
                variantOfChange = reader.readLine();
                while (!(variantOfChange.matches("^[1-3]$"))) {
                    System.out.println("Некоректный ввод");
                    variantOfChange = reader.readLine();
                }
            }
        } catch (IOException e1) {
            logger.error("Ошибка при считывании " + e1);
        }
        return variantOfChange;
    }

    @Override
    public int createInterval() {
        int interval;
        System.out.println("Ввод интервала повторения задачи...");
        System.out.println("Введите формат повторения в миунтах\n"
                + "Подсказка: \n"
                + "В одном часе - 60 минут \n"
                + "В одном дне - 1440 минут \n"
                + "В одной неделе - 10080 минут \n"
                + "Ввод: ");
        while (true) {
            try {
                interval = Integer.parseInt(reader.readLine());
                if (interval < 1) {
                    throw new NumberFormatException();
                } else {
                    break;
                }
            } catch (NumberFormatException e1) {
                System.out.println("Некорктный ввод! \n" +
                        "Введите формат повторения: ");
            } catch (IOException e2) {
                logger.error("ОШибка при считывании " + e2);
            }
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
        String newActive = null;
        System.out.println("1. Активация задачи.");
        System.out.println("2. Деактивация задачи.");
        try {
            newActive = reader.readLine();
            while (!(newActive.matches("^[1-2]$"))) {
                System.out.println("Некоректный ввод");
                newActive = reader.readLine();
            }
        } catch (IOException e1) {
            logger.error("Ошибка при считывании " + e1);
        }
        return newActive;
    }

    public void closeBufferedReader() {
        try {
            reader.close();
            System.out.println("Удачного Вам дня!");
        } catch (IOException e) {
            logger.error("Ошибка при закрытии потока " + e);
        }
    }

   /*public void printMessageToUser(String message) {
        System.out.println(message);
    }*/

    public void printSubMenuToRepeatedTaskCreating() {
        System.out.println("Для создания повторяющейся задачи необходимо ввести:\n" +
                "• Начало выполнения задачи\n" +
                "• Конец выполнения задачи\n" +
                "• Интервал повторения\n");
    }

    public void printSubMenuToAddingUserFile() {
        System.out.println("Для добавления своего списка задач необходимо соблюсти несколько обязательных пунктов: ");
        System.out.println("1. Ваш файл должен иметь название tasks.txt");
        System.out.println("2. Ваш файл должен находится по данному пути ...\\TaskManager\\target\\src\\main\\resources");
        System.out.println("3. Ваш файл должен соответствовать формату записи Json");
        System.out.println("4. Ваш файл должен содержать все необходимые поля задач");
        System.out.println("При невыполнии хотя бы одного пункта Вы начнете работу с пустым списком задач");
        System.out.println("Если по данному пути уже находится подобный файл,\n" +
                "просто перезапишите его на необхоюимый Вам файл");
        System.out.println("Перезапустити программу (Enter) полсе выполнения всех действий и нажмите '1' ");
    }

    public void printTaskList(AbstractTaskList mainTaskList) {
        int i = 1;
        System.out.println("Список доступных задач: \n");
        if (mainTaskList.size() == 0) {
            System.out.println("Ваш список задач пуст! \n"
                    + "Добавте задачи для работы с ними.");
        } else {
            for (Task task : mainTaskList) {
                System.out.println(i + ". " + task.printInfo());
                i++;
            }
        }
    }

    public void printCalendar(SortedMap<LocalDateTime, Set<Task>> calendar) {
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu hh:mm");
        for (Map.Entry<LocalDateTime, Set<Task>> map : calendar.entrySet()) {
            for (Task task : map.getValue()) {
                LocalDateTime key = map.getKey();
                System.out.println((key.format(dTF) + " " + task.printInfo() + "\n"));
            }
        }
    }

    public void compareStartEnd(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) System.out.println("Конец выполнения задачи не может быть до её начала!");
    }

    public BufferedReader getReader() {
        return reader;
    }
}
