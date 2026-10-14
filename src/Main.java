public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.add("Estudar Java");
        manager.add("Fazer commit");

        manager.complete(1);
        System.out.println(manager.complete(99));
        manager.remove(2);

        for (Task t : manager.getAll()) {
            System.out.println(t);
        }
    }
}