package com.aleksenko.artemii.view;

import com.aleksenko.artemii.model.Task;

interface View {
    void printTitle();
    void printMenu();
    void printSubMenu_1();
    void printSubMenu_2(Task task);
}
