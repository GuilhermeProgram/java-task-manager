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
    private Task findById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    public boolean complete(int id) {
        Task t = findById(id);
        if (t == null) {
            return false;
        }
        t.markAsDone();
        return true;
    }
}

