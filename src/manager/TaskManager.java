package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    ArrayList<Task> listOfTask();

    void clearTask();

    Task getTask(Integer id);

    void createTask(Task task);

    void updateTask(Task task);

    Task deleteTask(Integer id);

    ArrayList<Epic> listOfEpic();

    void clearEpic();

    Epic getEpic(Integer id);

    void createEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteEpicById(Integer id);

    // Список задач определенного эпика
    List<Subtask> getSubtaskByEpic(int epicId);

    ArrayList<Subtask> listOfSubtask();

    void clearSubtask();

    Subtask getSubtask(Integer id);

    void createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void deleteSubtaskById(Integer id);

    void updateEpicStatus(Epic epic);

}