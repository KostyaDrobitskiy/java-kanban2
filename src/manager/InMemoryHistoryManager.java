package manager;
import task.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final Integer MAX_HISTORY_SIZE = 10;
    private final List<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
