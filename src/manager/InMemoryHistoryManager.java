package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;
    private int size = 0;

    static class Node {
        Task task;
        Node next;
        Node prev;

        public Node(Task task, Node next, Node prev) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) return;
        // Удаляем существующий узел с этим id, чтобы избежать дублирования
        remove(task.getId());
        linkedLast(task);
    }

    private void linkedLast(Task task) {
        final Node newNode = new Node(task, null, tail);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;

        if (head == null) {
            head = newNode; // Если список был пустым, устанавливаем head
        }

        nodeMap.put(task.getId(), newNode);
        size++;
    }

    @Override
    public void remove(int id) {
        Node node = nodeMap.remove(id);
        if (node == null) {
            return; // Нет такого элемента
        }

        // Обновляем связи в списке
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            // Удаляемый узел был головой
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            // Удаляемый узел был хвостом
            tail = node.prev;
        }

        size--;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.task);
            current = current.next;
        }
        return result;
    }
}