package com.aleksenko.artemii.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainView  implements View {

    @Override
    public void printTitle() {
        System.out.println("Вас приветствует task manager 'Easy to do!' \n");
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

    }

    @Override
    public void printSubMenu_2() {

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
