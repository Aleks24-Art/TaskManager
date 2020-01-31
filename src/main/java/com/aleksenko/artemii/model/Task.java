package com.aleksenko.artemii.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @version 1.0
 * This is the model class, from where we create our tasks.
 */
public class Task implements Cloneable, Serializable {
    /**
     * This is name of the task
     */
    private String title;
    /**
     * This is interval of task repeating in minutes
     */
    private long interval;
    /**
     * This field for activate or deactivate the task
     */
    private boolean isActive;
    /**
     * Field to create both variant of task(repeated or unrepeated)
     */
    private boolean repeated;
    /**
     * This is time for unrepeated task when it must be done
     */
    private LocalDateTime time;
    /**
     * This is start time for repeated task
     */
    private LocalDateTime start;
    /**
     * This is end time for repeated task
     */
    private LocalDateTime end;

    /**
     * Constructor for creating unrepeated tasks
     * @param title name of task
     * @param time time when this task have been done
     */
    public Task(String title, LocalDateTime time) {
        this.setTime(time);
        this.title = title;
        repeated = false;
        isActive = true;
    }

    /**
     * Constructor for creating repeated tasks
     * @param title name of task
     * @param start time when user start doing task
     * @param end time when user end doing task
     * @param interval time in minutes to task repeating
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeated = true;
        isActive = true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean isRepeated() {
        return repeated;
    }

    /**
     * Method return time of task, if it is unrepeated else nearest time when task must be done, if it is repeated
     * @return time {@link LocalDateTime}
     */
    public LocalDateTime getTime() {
        if (repeated) {
            return nextTimeAfter(LocalDateTime.now());
        }
        return time;
    }

    /**
     * Method shows time when task will repeat after @param time
     * @param current time
     * @return time when task will repeat
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (!isActive) return null;
        if (repeated) {
            return getNextTime(current);
        }
        if (time.isAfter(current)) {
            return time;
        }
        return null;
    }

    /**
     * Method to print info about tasks
     * @return string with all task param
     */
    public String printInfo() {

        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu hh:mm");
        if (isRepeated()) {
            return "Название задачи: '" + getTitle() + "' Начало выполнения: " + getStart().format(dTF) + "  Конец выполнения: " + getEnd().format(dTF) + "  Интервал повторения(в минутах): " + getInterval() + "  Активность: " + activeToString(isActive);
        }
            return  "Название задачи: '" + getTitle() + "'     Время выполнения: " + getTime().format(dTF) + "     Активность: " + activeToString(isActive);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getInterval() == task.getInterval() &&
                isActive() == task.isActive() &&
                isRepeated() == task.isRepeated() &&
                Objects.equals(getTitle(), task.getTitle()) &&
                Objects.equals(getTime(), task.getTime()) &&
                Objects.equals(getStart(), task.getStart()) &&
                Objects.equals(getEnd(), task.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), time, start, end, interval, isActive, repeated);
    }

    @Override
    public String toString() {
        return "Task{" +
                " title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", isActive=" + isActive +
                ", repeated=" + repeated +
                '}';
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            //При попытке добавить логер в класс выбивало ошибку
        }
        return null;
    }

    private String activeToString(boolean active){
        return !active ? "Нет" : "Да";
    }
    private LocalDateTime getNextTime(LocalDateTime current) {
        LocalDateTime var = start;
        while (var.compareTo(end) <= 0) {
            if (var.compareTo(current) > 0) {
                return var;
            }
            var = var.plusMinutes(interval);
        }
        return null;
    }
}