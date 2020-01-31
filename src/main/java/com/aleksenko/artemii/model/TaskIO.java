package com.aleksenko.artemii.model;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;

import java.io.ObjectOutputStream;
import java.io.*;

public class TaskIO {
    private final static Logger logger = Logger.getLogger(TaskIO.class);
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(tasks);
        } catch (IOException e1) {
            logger.error("Ошибка при записи файла в поток");
        }

    }

    public static void read(AbstractTaskList tasks, InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            AbstractTaskList taskList = (AbstractTaskList) ois.readObject();
            for (Task task : taskList) {
                tasks.add(task);
            }
        } catch (IOException | ClassNotFoundException e1) {
            logger.error("Ошибка при считывания файла с потока");
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        ObjectOutputStream ous = null;
        try {
            ous = new ObjectOutputStream(new FileOutputStream(file));
        } catch (IOException e) {
            logger.error("Ошибка при заиси данных в файл");
        }
        try {
            if (ous != null) {
                ous.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при закрытии ObjectOutputStream");
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            logger.error("Ошибка при считывания данных с файла");
        }
        try {
            if (ois != null) {
                ois.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при закрытии ObjectInputStream");
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        Gson gson = new Gson();
        ArrayTaskList arrayTaskList = (ArrayTaskList) tasks;
        try {
            out.write(gson.toJson(arrayTaskList, ArrayTaskList.class));
        } catch (IOException e) {
            logger.error("Ошибка при записи в поток в формате Gson");
        }
        try {
            out.close();
        } catch (IOException e) {
            logger.error("Ошибка при закрытии потока");
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
            logger.error("Ошибка при закрытии потока");
        }
    }

    /**
     * Method which write task list in file in Gson format
     * @param tasks task list
     * @param file file where
     */
    public static void writeText(AbstractTaskList tasks, File file) {
        Gson gson = new Gson();
        ArrayTaskList taskList = (ArrayTaskList) tasks;
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(gson.toJson(taskList, ArrayTaskList.class));
            writer.flush();
            writer.close();
        } catch (IOException e1){
            logger.error("Ошибка при записи данных в файл в формате Gson");
        }

        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.error("Ошибка при потока FileWriter");
        }
    }

    /**
     * Method to read task list in Gson format from file
     * @param tasks where we should
     * @param file where we
     */
    public static void readText(AbstractTaskList tasks, File file) {
        Gson gson = new Gson();
        ArrayTaskList taskList;
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            taskList = gson.fromJson(reader, ArrayTaskList.class);
            for (Task task : taskList) {
                tasks.add(task);
            }
            try {
                reader.close();
            } catch (IOException e) {
                logger.error("Ошибка при заекрытии потока FileReader");
            }
        } catch (FileNotFoundException e ) {
            logger.error("Ошибка при считывании с файла в формате Gson (файл не найден или повреждён)");
        } catch (JsonSyntaxException | IllegalStateException e1) {
            logger.error("Ошибка при считывании с файла в формате Gson (неверный формат записи)");
            System.out.println("Не удалось загрузить файлы");
            System.out.println("Неверный формат записи в файле с задачами");
        }


    }
}