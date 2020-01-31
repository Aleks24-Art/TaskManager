package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.model.Task;
import org.apache.log4j.Logger;

import java.awt.*;
import java.time.LocalDateTime;

/**
 * @version 1.0
 * This is class for remind about nearest tasks.
 */
public class ThreadForRemind extends Thread {
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(ThreadForRemind.class);

    @Override
    public void run() {
        while(true) {
            for (Task task : MainController.mainTaskList) {
                if (task.getTime().isBefore(LocalDateTime.now()) || !task.isActive()) {
                    task.setActive(false);
                    continue;
                }
                if (task.nextTimeAfter(LocalDateTime.now()).isBefore(LocalDateTime.now().plusMinutes(30))){
                    System.out.println("Не забудь про задачу!\n" + task.printInfo());
                    Toolkit.getDefaultToolkit().beep();
                }
            }
            try {
                sleep(20000);
            } catch (InterruptedException e) {
                logger.error("Ошибка при попытке sleep(20000)");
            }
        }
    }
}

