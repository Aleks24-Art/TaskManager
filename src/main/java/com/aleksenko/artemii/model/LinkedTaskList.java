package com.aleksenko.artemii.model;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList {
    private int capacity = 0;
    private TaskNode first;
    private TaskNode last;

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

    public boolean remove(Task task) {
        if (task.hashCode() == first.item.hashCode()) {
            return removeFirst();
        }

        if (task.hashCode() == last.item.hashCode()) {
            return removeLast();
        }

        return removeAnother(task);
    }

    public boolean removeFirst() {
        if (first.equals(last)) {
            first = null;
            last = null;
        } else {
            first = first.next;
        }
        capacity--;
        return true;
    }

    public boolean removeLast() {
        if (last.equals(first)) {
            first = null;
            last = null;
        } else {
            last = last.prev;
        }
        capacity--;
        return true;
    }

    public boolean removeAnother(Task task) {
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

    private boolean isListsEqual(LinkedTaskList l1, LinkedTaskList l2) {

        return true;
    }

    @Override
    public Iterator<Task> iterator() {
        return new TaskIterator();
    }

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

    public Task[] toArray() {
        Task[] result = new Task[capacity];
        int i = 0;
        for (LinkedTaskList.TaskNode<Task> x = first; x != null; x = x.next) {
            result[i] = x.item;
            i++;
        }
        return result;
    }

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(this.toArray());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
