package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private int nextId = 1;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int addId() {
        return nextId++;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //Task
    @Override
    public ArrayList<Task> listOfTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clearTask() {
        tasks.clear();
    }

    @Override
    public Task getTask(Integer id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void createTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return; // или выбросить исключение
        }
        int id = addId();
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public Task deleteTask(Integer id) {
        return tasks.remove(id);
    }

    //Epic
    @Override
    public ArrayList<Epic> listOfEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void clearEpic() {
        epics.clear();
    }

    @Override
    public Epic getEpic(Integer id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public void createEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            return;
        }
        int id = addId();
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic existing = epics.get(epic.getId());
            existing.setName(epic.getName());
            existing.setDescription(epic.getDescription());
            updateEpicStatus(existing);
        }
    }

    @Override
    public void deleteEpicById(Integer id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subId : epic.getSubtasks()) {
                subtasks.remove(subId);
            }
        }
    }

    // Список задач определенного эпика
    @Override
    public List<Subtask> getSubtaskByEpic(int epicId) {
        List<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subID : epic.getSubtasks()) {
                Subtask sub = subtasks.get(subID);
                if (sub != null) result.add(sub);
            }
        }
        return result;
    }

    //Subtask
    @Override
    public ArrayList<Subtask> listOfSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void clearSubtask() {
        subtasks.clear();
    }

    @Override
    public Subtask getSubtask(Integer id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getEpicId())) {
            throw new IllegalArgumentException("Эпик с таким ID не существует");
        }
        int id = addId();
        subtask.setId(id);
        subtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(id);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic);
            }
        }
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        if (epic == null) return;
        List<Integer> subIds = epic.getSubtasks();
        if (subIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        int newCount = 0;
        int doneCount = 0;
        for (int id : subIds) {
            Subtask subtask = subtasks.get(id);
            if (subtask != null) {
                switch (subtask.getStatus()) {
                    case NEW -> newCount++;
                    case DONE -> doneCount++;
                }
            }
        }
        if (doneCount == subIds.size()) {
            epic.setStatus(Status.DONE);
        } else if (newCount == subIds.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}