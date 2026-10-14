import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

public class TaskViewer {

    private final TaskManager manager = new TaskManager();
    private final DefaultListModel<Task> model = new DefaultListModel<>();
    private final JList<Task> list = new JList<>(model);
    private final JTextField titleField = new JTextField();
    private final JCheckBox onlyPending = new JCheckBox("Só pendentes");
    private final JLabel status = new JLabel(" ");
    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskViewer().start());
    }

    private void start() {
        manager.load();

        frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 450);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setContentPane(root);

        // Topo: campo de texto + botão Adicionar
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(e -> addTask());
        titleField.addActionListener(e -> addTask()); // Enter também adiciona

        JPanel top = new JPanel(new BorderLayout(8, 0));
        top.add(titleField, BorderLayout.CENTER);
        top.add(addButton, BorderLayout.EAST);
        root.add(top, BorderLayout.NORTH);

        // Centro: lista com rolagem
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(l, value, index, isSelected, cellHasFocus);
                if (value instanceof Task && ((Task) value).isDone() && !isSelected) {
                    c.setForeground(Color.GRAY);
                }
                return c;
            }
        });
        root.add(new JScrollPane(list), BorderLayout.CENTER);

        // Base: botões, filtro e status
        JButton completeButton = new JButton("Concluir");
        completeButton.addActionListener(e -> completeSelected());

        JButton removeButton = new JButton("Remover");
        removeButton.addActionListener(e -> removeSelected());

        onlyPending.addActionListener(e -> refresh());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(completeButton);
        buttons.add(removeButton);
        buttons.add(onlyPending);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(buttons, BorderLayout.CENTER);
        bottom.add(status, BorderLayout.SOUTH);
        root.add(bottom, BorderLayout.SOUTH);

        refresh();
        frame.setVisible(true);
    }

    private void refresh() {
        model.clear();
        int pending = 0;
        for (Task t : manager.getAll()) {
            if (!t.isDone()) {
                pending++;
            }
            if (!onlyPending.isSelected() || !t.isDone()) {
                model.addElement(t);
            }
        }
        status.setText(pending + " pendente(s) de " + manager.getAll().size() + " tarefa(s)");
    }

    private void addTask() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Digite um título.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        manager.add(title);
        manager.save();
        titleField.setText("");
        refresh();
        titleField.requestFocusInWindow();
    }

    private void completeSelected() {
        Task selected = list.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(frame, "Selecione uma tarefa.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        manager.complete(selected.getId());
        manager.save();
        refresh();
    }

    private void removeSelected() {
        Task selected = list.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(frame, "Selecione uma tarefa.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int answer = JOptionPane.showConfirmDialog(frame,
                "Remover \"" + selected.getTitle() + "\"?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            manager.remove(selected.getId());
            manager.save();
            refresh();
        }
    }
}