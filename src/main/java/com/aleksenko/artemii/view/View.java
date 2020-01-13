package com.aleksenko.artemii.view;


import com.aleksenko.artemii.model.Task;
import java.time.LocalDateTime;

public interface View {
    void printTitle();
    void printMenu();
    void printSubMenu_2(Task task);
    String variantOfActivation();
    int createInterval();
    String variantOfChange(boolean isRepeated);
    int getSubMenuVariant();
    String getTaskTitle();
    LocalDateTime getTaskDate();
    String getMenuVariant();
    String getSubMenuVariant_1();
    void printAddStatus(Task task);
    void printRemoveStatus(Task task);
    void printRemoveAllStatus();
    void printChangeStatus(Task task);
}
