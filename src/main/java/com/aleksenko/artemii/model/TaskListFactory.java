package com.aleksenko.artemii.model;

public class TaskListFactory {

    public static AbstractTaskList createTaskList(ListTypes.types type) {
        if (type.equals(ListTypes.types.ARRAY)) {
            return new ArrayTaskList();
        }
        if (type.equals(ListTypes.types.LINKED)) {
            return new LinkedTaskList();
        }
        return null;
    }
}
