package com.aleksenko.artemii.controller;

import java.io.FileNotFoundException;

public interface Controller {
    void startWork() throws FileNotFoundException;
    void addTask();
    void changeTask();
    void showTask();
    void showTaskListForInterval();
    void removeTask();
    void removeAllTasks();
    void finishWork();
}
