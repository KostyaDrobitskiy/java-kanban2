import task.Task;
import org.junit.jupiter.api.Test;
import manager.InMemoryHistoryManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    void historyRetainsCorrectVersionOfTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task = new Task("Task", "desc");
        task.setId(1);
        historyManager.add(task);

        task.setName("Modified");

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals("Modified", history.get(0).getName(), "История должна сохранять актуальную ссылку на задачу");
    }
}