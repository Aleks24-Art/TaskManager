package com.aleksenko.artemii.app;

import com.aleksenko.artemii.controller.MainController;
import com.aleksenko.artemii.model.Task;

import java.awt.*;
import java.time.LocalDateTime;

public class ThreadForRemind extends Thread {
    private MainController controller;
    ThreadForRemind(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while(true) {
            for (Task task : controller.getMainTaskList()) {
                if (task.getTime().isBefore(LocalDateTime.now()) || !task.isActive()) {
                    task.setActive(false);
                    continue;
                }
                if (task.nextTimeAfter(LocalDateTime.now()).isBefore(LocalDateTime.now().plusMinutes(10))){
                    System.out.println("Не забудь про задачу!\n" + task.printInfo());
                    Toolkit.getDefaultToolkit().beep();
                }
            }
            try {
                sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

