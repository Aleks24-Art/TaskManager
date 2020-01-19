package com.aleksenko.artemii.model;

/**
 * This class uses factory method pattern to create task list objects
 */
public class TaskListFactory {
    /**
     * @param type here we put one of our enum types of task lists {@link ListTypes}
     * @return {@link ArrayTaskList} or {@link LinkedTaskList}
     */
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
