package com.aleksenko.artemii.view;


import com.aleksenko.artemii.model.Task;
import java.time.LocalDateTime;

/**
 * @version 1.0
 * This is the interface with view methods.
 */
public interface View {
    /**
     * Method display title message and set text color.
     */
    void printTitle();

    /**
     * Method display all functional of task manager in console menu.
     */
    void printMenu();

    /**
     * @param task  method choose which sub menu print based on task fields.
     * Method display variant for changing task fields.
     */
    void printSubMenu_2(Task task);

    /**
     * Method display variant for changing task activity.
     * @return number of variant
     */
    String variantOfActivation();

    /**
     * Method for getting interval of task repeating.
     * @return interval of task repeating in minutes
     */
    int createInterval();

    /**
     * Method to get number of fields to changing.
     * @param isRepeated to choose which list of fields to change method need to display
     * @return number of changing
     */
    String variantOfChange(boolean isRepeated);

    /**
     * Method to get new task title from the user.
     * @return name of task
     */
    String getTaskTitle();

    /**
     * Method to get time from user.
     * @return time in {@link LocalDateTime}
     */
    LocalDateTime getTaskDate();

    /**
     * Method to get from user number of main menu.
     * @return number of main menu
     */
    String getMenuVariant();

    /**
     * Method display all tasks from task list and get number of task from user.
     * @return number of task
     */
    int getNumberVariantOfTask();

    /**
     * Method display variant for adding task and get variant of menu from the user.
     * @return variant of menu
     */
    String getSubMenuVariant_1();

    /**
     * Method display variant of showing task for nearest time.
     * @return variant of time interval
     */
    String getSubMenuVariant_4();

    /**
     * Method display status of adding.
     * @param task which was added
     */
    void printAddStatus(Task task);

    /**
     * Method display status of removing.
     * @param task which was removed
     * @param isDeleted shows task delete status
     */
    void printRemoveStatus(Task task, boolean isDeleted);

    /**
     * Method display status of removing all tasks.
     */
    void printRemoveAllStatus();

    /**
     * Method display status of changing.
     * @param task which was changed
     */
    void printChangeStatus(Task task);

    /**
     * Method display variant of removing
     * @return number of variant
     */
    String variantOfRemove();

    /**
     * Method display all variant of starting work with task list
     * @return number of variant
     */
    String variantOfTaskList();
}
