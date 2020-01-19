package com.aleksenko.artemii.model;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @version 1.0
 * This is the model class, from where we create our task list the same as {@link java.util.LinkedList}.
 */
public class LinkedTaskList extends AbstractTaskList {
    /**
     * Field to display number of tasks in task list
     */
    private int capacity = 0;
    /**
     * Field to display first node in chain
     */
    private TaskNode first;
    /**
     * Field to display last node in chain
     */
    private TaskNode last;

    /**
     * Class to make connection between tasks
     * @param <Task> some task
     */
    public static class TaskNode<Task> {
        private Task item;
        private TaskNode next;
        private TaskNode prev;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TaskNode)) {
                return false;
            }
            TaskNode<?> taskNode = (TaskNode<?>) o;
            return Objects.equals(item, taskNode.item) &&
                    Objects.equals(next, taskNode.next) &&
                    Objects.equals(prev, taskNode.prev);
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, next, prev);
        }

        TaskNode(Task task) {
            this.item = task;
            this.next = next;
            this.prev = prev;

        }
    }

    @Override
    public void add(Task task) throws IllegalArgumentException {
        TaskNode newNode = new TaskNode(task);
        if (first == null) {
            newNode.next = null;
            newNode.prev = null;
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        capacity++;
    }

    @Override
    public Task getTask(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException();
        }
        TaskNode nodeForSearch = first;
        for (int i = 0; i < index; i++) {
            nodeForSearch = nodeForSearch.next;
        }

        return (Task) nodeForSearch.item;
    }

    /**
     * Method to search node
     * @param index of node
     * @return node with task
     */
    public TaskNode getTaskNode(int index) {
        if (index < 0 || index >= capacity) {
            throw new IndexOutOfBoundsException();
        }
        TaskNode nodeForSearch = first;
        for (int i = 0; i < index; i++) {
            nodeForSearch = nodeForSearch.next;
        }

        return nodeForSearch;
    }

    @Override
    public boolean remove(Task task) {
        if (task.hashCode() == first.item.hashCode()) {
            return removeFirst();
        }

        if (task.hashCode() == last.item.hashCode()) {
            return removeLast();
        }

        return removeAnother(task);
    }

    @Override
    public int size() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        int counter = 0;
        LinkedTaskList list = (LinkedTaskList) o;
        if (this.size() != list.size())
            return false;
        for (int i = 0; i < this.size(); i++) {
            if (!(this.getTask(i).equals(list.getTask(i))))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * capacity;
    }

    @Override
    public String toString() {
        String result;
        result = "";
        TaskNode nodeForGetIndex = first;
        for (int i = 0; i < capacity; i++) {
            result += nodeForGetIndex.item + "";
            nodeForGetIndex = nodeForGetIndex.next;

        }
        return result;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TaskNode getFirst() {
        return first;
    }

    public void setFirst(TaskNode first) {
        this.first = first;
    }

    public TaskNode getLast() {
        return last;
    }

    public void setLast(TaskNode last) {
        this.last = last;
    }

    @Override
    public Iterator<Task> iterator() {
        return new TaskIterator();
    }

    /**
     * We use it to iterate our collection
     */
    public class TaskIterator implements Iterator {
        TaskNode taskNode1 = first;
        TaskNode taskNode = null;

        @Override
        public boolean hasNext() {

            return taskNode1 != null;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                taskNode = taskNode1;
                taskNode1 = taskNode1.next;
                return taskNode.item;
            }
            return null;

        }

        @Override
        public void remove() {
            if (taskNode == null) {
                throw new IllegalStateException();
            } else {
                LinkedTaskList.this.remove((Task) taskNode.item);
            }
        }

    }

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(this.toArray());
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    private Task[] toArray() {
        Task[] result = new Task[capacity];
        int i = 0;
        for (LinkedTaskList.TaskNode<Task> x = first; x != null; x = x.next) {
            result[i] = x.item;
            i++;
        }
        return result;
    }

    private boolean removeFirst() {
        if (first.equals(last)) {
            first = null;
            last = null;
        } else {
            first = first.next;
        }
        capacity--;
        return true;
    }

    private boolean removeLast() {
        if (last.equals(first)) {
            first = null;
            last = null;
        } else {
            last = last.prev;
        }
        capacity--;
        return true;
    }

    private boolean removeAnother(Task task) {
        TaskNode node;
        node = first;
        for (int i = 0; i < capacity; i++) {
            node = node.next;
            if (node.item.equals(task)) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                break;
            }
            if (i == capacity - 1) {
                return false;
            }
        }
        capacity--;
        return true;
    }
}
