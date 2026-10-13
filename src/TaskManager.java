import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task add (String title) {
      Task a = new Task (nextId, title);
      nextId++;
      tasks.add(a);
      return a;
    }
    public List<Task> getAll() {
        return tasks;
    }
}

