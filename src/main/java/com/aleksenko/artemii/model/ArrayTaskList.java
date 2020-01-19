package com.aleksenko.artemii.model;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @version 1.0
 * This is the model class, where we create our task list, the same as {@link java.util.ArrayList}.
 */
public class ArrayTaskList extends AbstractTaskList {
    /**
     * Field to display number of tasks in task list
     */
    private int capacity = 0;
    /**
     * Array where we save our task in ArrayTaskList
     */
    private Task[] tasks = new Task[1];

    @Override
    public void add(Task task) {
        if (capacity == tasks.length) {
            reDo(tasks.length + 1);
            tasks[capacity++] = task;
        } else {
            tasks[capacity] = task;
            capacity++;
        }
    }

    @Override
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

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public Task getTask(int index) {
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

    /**
     * Method to iterate task list
     */
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

    /**
     * Method convert stream to array.
     * @return stream of array
     */
    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(tasks);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    /**
     * Method to display number of tasks in task list
     * @return number of tasks in task list
     */
    private int getCapacity() {
        return capacity;
    }

    /**
     * Method which return array with task list
     * @return array with task
     */
    public Task[] getTasks() {
        return tasks;
    }

    private void reDo(int newSize) {
        Task[] newTasks = new Task[newSize];
        System.arraycopy(tasks, 0, newTasks, 0, capacity);
        tasks = newTasks;
    }
    private int getIndex(Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(task)) {
                return i;
            }
        }
        return -1;
    }
}
