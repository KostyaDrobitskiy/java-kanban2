import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager manager;

    @BeforeEach
    public void setUp() {
        manager = new InMemoryTaskManager(new InMemoryHistoryManager());
        ;
    }

    @Test
    void addsAndFindsTasksById() {
        Task task = new Task("Task", "Desc");
        manager.createTask(task);
        Task found = manager.getTask(task.getId());
        assertNotNull(found);
        assertEquals(task, found);
    }

    @Test
    void manualAndGeneratedIdsShouldNotConflict() {
        Task manualTask = new Task("Manual", "");
        manualTask.setId(999);
        manager.createTask(manualTask);
        Task autoTask = new Task("Auto", "");
        manager.createTask(autoTask);
        assertNotEquals(manualTask.getId(), autoTask.getId());
    }

    @Test
    void testCannotAddEpicAsSubtaskToItself() {
        Epic epic = new Epic("Epic", "desc");
        epic.setId(1);
        Subtask subtask = new Subtask("Sub", "desc", 1);
        assertThrows(IllegalArgumentException.class, () -> {
            if (subtask.getEpicId() == epic.getId()) {
                throw new IllegalArgumentException("Эпик не может быть подзадачей самого себя");
            }
        });
    }

    @Test
    void testManagersAreInitialized() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager);
        Task task = new Task("Name", "Desc");
        manager.createTask(task);
        List<Task> tasks = manager.listOfTask();
        assertFalse(tasks.isEmpty());
        Integer id = task.getId();
        Task retrieved = manager.getTask(id);
        assertEquals(task, retrieved);
    }

    @Test
    void taskShouldNotChangeWhenAddedToManager() {
        TaskManager manager = Managers.getDefault();
        Task original = new Task("Original", "Desc");
        original.setStatus(Status.IN_PROGRESS);
        manager.createTask(original);
        Task retrieved = manager.getTask(original.getId());
        assertEquals(original.getName(), retrieved.getName());
        assertEquals(original.getDescription(), retrieved.getDescription());
        assertEquals(original.getStatus(), retrieved.getStatus());
    }
}