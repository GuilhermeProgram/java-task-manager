public class Task {
    private int id;
    private String title;
    private boolean done;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.done = false;
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
