package com.aleksenko.artemii.model;

import java.io.IOException;

import com.google.gson.Gson;

import java.io.ObjectOutputStream;
import java.io.*;

public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        try {
            oos.writeObject(tasks);
        } catch (IOException e1) {
            e1.getStackTrace();
        } finally {
            oos.close();
        }

    }

    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(in);
        try {
            AbstractTaskList taskList = (AbstractTaskList) ois.readObject();
            for (Task task : taskList) {
                tasks.add(task);
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            ois.close();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        ObjectOutputStream ous = null;
        try {
            ous = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ous.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new Gson();
        ArrayTaskList arrayTaskList = (ArrayTaskList) tasks;
        try {
            out.write(gson.toJson(arrayTaskList, ArrayTaskList.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new Gson();
        ArrayTaskList abstractTaskList = gson.fromJson(in, ArrayTaskList.class);
        for (Task task : abstractTaskList) {
            tasks.add(task);
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        Gson gson = new Gson();
        ArrayTaskList taskList = (ArrayTaskList) tasks;
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.write(gson.toJson(taskList, ArrayTaskList.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file) throws FileNotFoundException {
        Gson gson = new Gson();
        ArrayTaskList taskList;
        FileReader reader = new FileReader(file);
        taskList = gson.fromJson(reader, ArrayTaskList.class);
        for (Task task : taskList) {
            tasks.add(task);
        }
    }
}
