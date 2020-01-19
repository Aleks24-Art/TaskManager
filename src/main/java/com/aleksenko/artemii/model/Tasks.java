package com.aleksenko.artemii.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @version 1.0
 * This is the class with methods to get nearest tasks for interval
 */
public class Tasks {
    /**
     * Method which choose nearest tasks for some interval
     * @param tasks the same as {@link #calendar(Iterable, LocalDateTime, LocalDateTime)}
     * @param start the same as {@link #calendar(Iterable, LocalDateTime, LocalDateTime)}
     * @param end the same as {@link #calendar(Iterable, LocalDateTime, LocalDateTime)}
     * @return list of tasks which have been done on this interval
     */
    public static Iterable<Task>  incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        //Iterable iter = new ArrayTaskList();
        return asStream(tasks)
                .filter(x -> x.nextTimeAfter(start) != null && x.nextTimeAfter(start).compareTo(end) <= 0)
                .collect(Collectors.toList());
    }

    /**
     * Method which make calendar of task for some interval
     * @param tasks task list which we will iterate to find nearest tasks
     * @param start start of time interval where we try to find
     * @param end end of time interval where we try to find
     * @return sorted map of tasks which have been done on this interval
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar
            (Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();
        for (Task task : tasks) {
            LocalDateTime key = start;
            if (inSchedule(key, end)) {
                key = task.nextTimeAfter(key);
                if (inSchedule(key, end)) {
                    Set<Task> existedTasks = getExistedTasks(calendar, key);
                    existedTasks.add(task);
                    calendar.put(key, existedTasks);
                }
            }
        }
        return calendar;
    }

    private static Set<Task> getExistedTasks(SortedMap<LocalDateTime, Set<Task>> calendar, LocalDateTime key) {
        Set<Task> existedTasks = calendar.get(key);
        return calendar.get(key) == null ? new HashSet<>() : existedTasks;
    }
    private static boolean inSchedule(LocalDateTime key, LocalDateTime end) {
        if (key == null) return false;
        return key.compareTo(end) <= 0;
    }
    private static Stream<Task> asStream(Iterable<Task> tasks) {
        return StreamSupport.stream(tasks.spliterator(), false);
    }
}
