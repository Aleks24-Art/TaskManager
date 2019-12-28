package com.aleksenko.artemii.model;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Tasks {
    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        Iterable iter = new ArrayTaskList();

        return asStream(tasks)
                .filter(x -> x.nextTimeAfter(start) != null && x.nextTimeAfter(start).compareTo(end) <= 0)
                .collect(Collectors.toList());
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {
        SortedMap<LocalDateTime, Set<Task>> calendar = new TreeMap<>();
        for (Task task : tasks) {
            LocalDateTime key = start;
            while (inSchedule(key, end)) {
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
        return key.compareTo(end) <= 0;
    }

    private static Stream<Task> asStream(Iterable<Task> tasks) {
        return StreamSupport.stream(tasks.spliterator(), false);
    }
}
