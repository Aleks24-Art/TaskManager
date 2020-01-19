package com.aleksenko.artemii.controller;

/**
 * @version 1.0
 * This is the interface with controller methods.
 */
public interface Controller {

    /**
     *  Method loop where user can choose different functions of Task Manager from menu.
     */
    void startWork();

    /**
     * Method to add task (repeated and unrepeated) to task list.
     */
    void addTask();

    /**
     * Method to change task parameters.
     * If task list is empty, then it displays message in console.
     */
    void changeTask();

    /**
     * Method to show all tasks from task list.
     * If task list is empty, then it displays message in console.
     */
    void showTask();

    /**
     * Method where user can see his/her calendar of task for a week or for user interval
     */
    void showTaskListForInterval();

    /**
     * Method to remove task from task list.
     * If task list is empty, then it displays message in console.
     */
    void removeTask();

    /**
     * Method to remove all tasks from task list.
     * If task list is empty, then it displays message in console.
     */
    void removeAllTasks();

    /**
     * Method to change task repeated.
     * If task list is empty, then it displays message in console.
     */
    void changeRepeated();

    /**
     * Method display farewell message and wait for a user tap to finish work of a program.
     */
    void finishWork();
}
