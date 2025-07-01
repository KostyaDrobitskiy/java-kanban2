import org.junit.jupiter.api.Test;
import task.Epic;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    void testEpicEqualityById() {
        Epic epic1 = new Epic("Task1", "Description1");
        epic1.setId(100);
        Epic epic2 = new Epic("Task2", "Description2");
        epic2.setId(100);
        assertEquals(epic1, epic2);
    }
    @Test
    void newEpicHasEmptySubtaskList() {
        Epic epic = new Epic("Epic", "Desc");
        assertTrue(epic.getSubtasks().isEmpty(), "У нового эпика не должно быть подзадач");
    }
}