package com.aleksenko.artemii.model;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList {

    private int capacity = 0;
    private Task[] tasks = new Task[1];


    public void add(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException("invalid type of task");
        }
        if (capacity == tasks.length) {
            reDo(tasks.length + 1);
            tasks[capacity++] = task;
        } else {
            tasks[capacity] = task;
            capacity++;
        }
    }

    public void reDo(int newSize) {
        Task[] newTasks = new Task[newSize];
        System.arraycopy(tasks, 0, newTasks, 0, capacity);
        tasks = newTasks;
    }

    public int getIndex(Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(task)) {
                return i;
            }
        }
        return -1;
    }

    public boolean remove(Task task) {
        int pos = getIndex(task);
        if (pos == -1) {
            return false;
        }
        Task[] newTasks = new Task[tasks.length - 1];
        int j = 0;
        for (int i = 0; i < tasks.length; i++) {
            if (i != pos) {
                newTasks[j] = tasks[i];
                j++;
            }
        }
        capacity--;
        tasks = newTasks;
        return true;
    }

    public int size() {
        return capacity;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > capacity - 1) {
            throw new IndexOutOfBoundsException("illegal index");
        }
        return tasks[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayTaskList)) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return getCapacity() == that.getCapacity() &&
                Arrays.equals(getTasks(), that.getTasks());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getCapacity());
        result = 31 * result + Arrays.hashCode(getTasks());
        return result;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{"
                + "capacity="
                + capacity
                + ", tasks="
                + Arrays.toString(tasks)
                + '}';
    }

    @Override
    public Iterator<Task> iterator() {
        return new TaskIterator();
    }

    public class TaskIterator implements Iterator {
        private int currentIndex = 0;
        Task taskF;

        @Override
        public boolean hasNext() {
            return currentIndex < capacity;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                taskF = tasks[currentIndex];
                currentIndex++;
                return taskF;
            }
            return null;
        }

        @Override
        public void remove() {

            if (taskF == null) {
                throw new IllegalStateException();
            } else {
                ArrayTaskList.this.remove(taskF);
                taskF = null;
                currentIndex--;
            }
        }

    }

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(tasks);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
