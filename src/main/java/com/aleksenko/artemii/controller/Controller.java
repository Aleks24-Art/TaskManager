package com.aleksenko.artemii.controller;

public interface Controller {
    void startWork();
    void addTask();
    void changeTask();
    void showTask();
    void showTaskListForInterval();
    void removeTask();
    void removeAllTasks();
    void finishWork();
    void reminder();
    void saveAllInfoToFileSystem();
}
