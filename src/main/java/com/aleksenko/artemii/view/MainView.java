package com.aleksenko.artemii.view;

import com.aleksenko.artemii.model.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainView  implements View {

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
        System.out.println("3. Просмотреть существующею задачу");
        System.out.println("4. Просмотреть список задач на ближайшее время");
        System.out.println("5. Удалить задачу");
        System.out.println("6. Удалить все задачи");
        System.out.println("7. Завершить работу 'Easy to do!'");
        System.out.println("**************************************************\n");
        System.out.println("Ввод: ");
    }

    @Override
    public void printSubMenu_1() {
        System.out.println("1. Добавить задачу с повторением");
        System.out.println("2. Добавить задачу без повторения");
    }

    @Override
    public void printSubMenu_2(Task task) {
        if (task.isRepeated()) {
            System.out.println("Выберете параметр задачи который хотите изменить");
            System.out.println("1. Название задачи");
            System.out.println("2. Старт выполнения");
            System.out.println("3. Конец выполнения");
            System.out.println("3. Интервал повторения задачи");
        } else {
            System.out.println("Выберете параметр задачи который хотите изменить");
            System.out.println("1. Название задачи");
            System.out.println("2. Время выполнения");
        }
    }

    @Override
    public void printSubMenu_3() {

    }

    @Override
    public void printSubMenu_4() {

    }

    @Override
    public void printSubMenu_5() {

    }

    @Override
    public void printSubMenu_6() {

    }

}
