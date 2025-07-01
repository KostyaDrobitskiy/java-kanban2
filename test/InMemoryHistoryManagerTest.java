import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void historyRetainsCorrectVersionOfTask() {

        Task task = new Task("Task", "desc");
        task.setId(1);
        historyManager.add(task);

        task.setName("Modified");

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals("Modified", history.get(0).getName(), "История должна сохранять актуальную ссылку на задачу");
    }

    @Test
    void removeShouldDeleteTaskFromHistory() {
        Task task1 = new Task("1", "Task1");
        Task task2 = new Task("2", "Task2");
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Неверное количество задач после удаления");
        assertEquals(task2, history.get(0), "Осталась неверная задача");
    }

    @Test
    void getHistoryShouldReturnTasksInOrder() {
        Task task1 = new Task("1", "Task1");
        Task task2 = new Task("2", "Task2");
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1); // Должен переместиться в конец

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Неверное количество задач");
        assertEquals(task2, history.get(0), "Неверный порядок задач");
        assertEquals(task1, history.get(1), "Неверный порядок задач");
    }

    @Test
    void removeFromMiddleShouldKeepOrder() {
        Task task1 = new Task("1", "Task1");
        Task task2 = new Task("2", "Task2");
        Task task3 = new Task("3", "Task3");
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Неверное количество задач после удаления");
        assertEquals(task1, history.get(0), "Неверный порядок после удаления");
        assertEquals(task3, history.get(1), "Неверный порядок после удаления");
    }
}