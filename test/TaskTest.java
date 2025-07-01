import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;
class TaskTest {
    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task1", "Description1");
        task1.setId(100);
        Task task2 = new Task("Task2", "Description2");
        task2.setId(100);
        assertEquals(task1, task2);
    }
    @Test
    void subtaskAndEpicAreEqualIfIdEqual() {
        Subtask subtask = new Subtask("Subtask", "desc", 1);
        subtask.setId(10);
        Epic epic = new Epic("Epic", "desc");
        epic.setId(10);
        assertNotEquals(subtask, epic, "Subtask и Epic с одинаковым ID не должны быть равны");
    }
}