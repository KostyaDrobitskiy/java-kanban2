package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private int size = 0;
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) return;
        remove(task.getId());
        linkedLast(task);
    }

    private void linkedLast(Task task) {
        final Node newNode = new Node(task, null, tail);
        if (tail != null) {
            tail.setNext(newNode);
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
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            // Удаляемый узел был головой
            head = node.getNext();
        }

        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            // Удаляемый узел был хвостом
            tail = node.getPrev();
        }

        size--;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.getTask());
            current = current.getNext();
        }
        return result;
    }
}