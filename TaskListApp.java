
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskListApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private static final String FILE_NAME = "tasks.txt";
    private static final String BULLET = "\u2022"; // Unicode for bullet point

    public TaskListApp() {
        // Set up the frame
        setTitle("Task List Application");
        setSize(600, 400); // Increased window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskField = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Selected Task");

        // Set colors
        taskField.setBackground(Color.LIGHT_GRAY);
        addButton.setBackground(Color.GREEN);
        removeButton.setBackground(Color.RED);
        taskList.setBackground(Color.WHITE);

        // Load tasks from file
        loadTasksFromFile();

        // Set up the top panel (input field and add button)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(taskField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // Set up the bottom panel (remove button)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(removeButton, BorderLayout.CENTER);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add task action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText().trim();
                if (!task.isEmpty()) {
                    addTask(task);
                    taskField.setText("");
                    saveTasksToFile();
                }
            }
        });

        // Remove task action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    removeTask(selectedIndex);
                    saveTasksToFile();
                }
            }
        });
    }

    private void addTask(String task) {
        taskListModel.addElement(BULLET + " " + task);
    }

    private void removeTask(int index) {
        taskListModel.remove(index);
    }

    private void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskListModel.getSize(); i++) {
                writer.write(taskListModel.getElementAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                taskListModel.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskListApp().setVisible(true);
            }
        });
    }
}