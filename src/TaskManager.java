import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final String FILE_NAME = "tasks.txt";

    private List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task add(String title) {
        Task a = new Task(nextId, title);
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

    public boolean remove(int id) {
        Task t = findById(id);
        if (t == null) {
            return false;
        }
        tasks.remove(t);
        return true;
    }

    public void save() {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toFileLine());
        }
        try {
            Files.write(Paths.get(FILE_NAME), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public void load() {
        Path path = Paths.get(FILE_NAME);
        if (!Files.exists(path)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                Task t = Task.fromFileLine(line);
                if (t != null) {
                    tasks.add(t);
                    if (t.getId() >= nextId) {
                        nextId = t.getId() + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}