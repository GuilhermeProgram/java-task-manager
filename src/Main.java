import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.load();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Task Manager ===");
            System.out.println("1 - Adicionar tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Concluir tarefa");
            System.out.println("4 - Remover tarefa");
            System.out.println("5 - Listar pendentes");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            String option = scanner.nextLine().trim();

            if (option.equals("1")) {
                System.out.print("Título: ");
                String title = scanner.nextLine().trim();
                if (title.isEmpty()) {
                    System.out.println("O título não pode ser vazio.");
                } else {
                    Task created = manager.add(title);
                    manager.save();
                    System.out.println("Criada: " + created);
                }

            } else if (option.equals("2")) {
                if (manager.getAll().isEmpty()) {
                    System.out.println("Nenhuma tarefa cadastrada.");
                } else {
                    for (Task t : manager.getAll()) {
                        System.out.println(t);
                    }
                }

            } else if (option.equals("3")) {
                int id = readId(scanner);
                if (manager.complete(id)) {
                    manager.save();
                    System.out.println("Tarefa concluída!");
                } else {
                    System.out.println("Tarefa não encontrada.");
                }

            } else if (option.equals("4")) {
                int id = readId(scanner);
                if (manager.remove(id)) {
                    manager.save();
                    System.out.println("Tarefa removida.");
                } else {
                    System.out.println("Tarefa não encontrada.");
                }

            } else if (option.equals("5")) {
                boolean found = false;
                for (Task t : manager.getAll()) {
                    if (!t.isDone()) {
                        System.out.println(t);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Nenhuma tarefa pendente.");
                }

            } else if (option.equals("0")) {
                running = false;
                System.out.println("Até mais!");

            } else {
                System.out.println("Opção inválida.");
            }
        }

        scanner.close();
    }

    private static int readId(Scanner scanner) {
        System.out.print("Digite o numero de id da tarefa: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}