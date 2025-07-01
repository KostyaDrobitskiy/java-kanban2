import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager managers = new InMemoryTaskManager(historyManager);

        Task task1 = new Task("Переезд", "Собрать вещи");
        Task task2 = new Task("Отпуск", "Купить зонт");
        Epic epic1 = new Epic("выучить язык", "составить график");
        Epic epic2 = new Epic("поход в магазин", "составить список");
        managers.createTask(task1);
        managers.createTask(task2);
        managers.createEpic(epic1);
        managers.createEpic(epic2);

        Subtask subtask1 = new Subtask("заниматься 2 раза", "или 3", epic1.getId());
        Subtask subtask2 = new Subtask("или 4 раза", "или пять", epic1.getId());
        Subtask subtask3 = new Subtask("купить курицу", "купить ананасы", epic2.getId());
        managers.createSubtask(subtask1);
        managers.createSubtask(subtask2);
        managers.createSubtask(subtask3);

        task1.setStatus(Status.NEW);
        managers.updateTask(task1);
        task2.setStatus(Status.IN_PROGRESS);
        managers.updateTask(task2);
        subtask1.setStatus(Status.NEW);
        managers.updateSubtask(subtask1);
        subtask2.setStatus(Status.IN_PROGRESS);
        managers.updateSubtask(subtask2);
        subtask3.setStatus(Status.DONE);
        managers.updateSubtask(subtask3);

        System.out.println("Задачи: " + managers.listOfTask());
        System.out.println("Эпики: " + managers.listOfEpic());
        System.out.println("Подзадачи: " + managers.listOfSubtask());

        managers.getTask(task1.getId());
        managers.getTask(task1.getId());
        managers.getTask(task1.getId());
        managers.getTask(task2.getId());
        managers.getEpic(epic1.getId());
        managers.getEpic(epic2.getId());
        managers.getSubtask(subtask1.getId());
        managers.getSubtask(subtask2.getId());
        managers.getSubtask(subtask3.getId());

        System.out.println("История: " + historyManager.getHistory());
    }
}