import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void managersAreInitializedProperly() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager);
        assertNotNull(historyManager);

        List<Task> history = historyManager.getHistory();
        assertNotNull(history);

        ArrayList<Task> tasks = ((InMemoryTaskManager) taskManager).listOfTask();
        assertNotNull(tasks);
    }
}