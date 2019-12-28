package com.aleksenko.artemii.model;

import java.io.Serializable;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {
    public abstract int size();

    public abstract Task getTask(int index);

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Stream<Task> getStream();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
