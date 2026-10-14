public class Task {
    private int id;
    private String title;
    private boolean done;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.done = false;
    }

    public Task(int id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public boolean isDone() {
        return done;
    }

    public void markAsDone() {
        done = true;
    }

    public String toFileLine() {
        return id + ";" + done + ";" + title;
    }

    public static Task fromFileLine(String line) {
        String[] parts = line.split(";", 3);
        if (parts.length < 3) {
            return null;
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            boolean done = Boolean.parseBoolean(parts[1].trim());
            return new Task(id, parts[2], done);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        String mark;
        if (done) {
            mark = "[x]";
        } else {
            mark = "[ ]";
        }
        return mark + " " + id + " - " + title;
    }
}