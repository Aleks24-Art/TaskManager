package com.aleksenko.artemii.model;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @version 1.0
 * This is the abstract model class, from where we create the same methods for our task lists.
 */
public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {

    /**
     * Method return number of tasks in task list
     * @return number of tasks
     */
    public abstract int size();

    /**
     * @param index of task in array
     * @return task under this index
     */
    public abstract Task getTask(int index);

    /**
     * Method to add task in task list
     * @param task which we want to add in task list
     */
    public abstract void add(Task task);

    /**
     *
     * @param task for removing
     * @return success of removing task from task list
     */
    public abstract boolean remove(Task task);

    /**
     * Method create stream
     * @return stream of collection
     */
    public abstract Stream<Task> getStream();

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
