package com.aleksenko.artemii.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private long interval;
    private boolean isActive;
    private boolean repeated;

    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;

    public Task(String title, LocalDateTime time) throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException("invalid time");
        }
        this.setTime(time);
        this.title = title;
        repeated = false;
        isActive = false;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (interval < 0) {
            throw new IllegalArgumentException("invalid interval");
        }
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeated = true;
        isActive = false;
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

    public LocalDateTime getTime() {
        if (repeated) {
            return start;
        }
        return time;
    }

    public boolean isRepeated() {
        if (repeated) {
            return true;
        }
        return false;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {
        if (!repeated) {
            this.repeated = true;
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    private LocalDateTime getNextTime(LocalDateTime current) {
        LocalDateTime var = start;
        while (var.compareTo(end) <= 0) {
            if (var.compareTo(current) > 0) {
                return var;
            }
            var = var.plusHours(interval);
        }
        return null;
    }

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

    public String printInfo() {
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("dd MMM uuuu hh:mm");
        if (isRepeated()) {
            return "Title '" + getTitle() + "' Start = " + getTime().format(dTF) + " End = " + getEnd().format(dTF) + " Interval = " + getInterval();
        }
        return  "Title '" + getTitle() + "' Time = " + getTime().format(dTF);

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
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}