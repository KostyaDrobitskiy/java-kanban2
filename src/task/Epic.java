package task;
import java.util.*;
public class Epic extends Task {
    private final List<Integer> subtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(int id) {
        subtasks.add(id);
    }
    public void removeSubtaskId(int id) {
        subtasks.remove((Integer) id);
    }
    @Override
    public String toString() {
        return  "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks +
                '}';
    }
}