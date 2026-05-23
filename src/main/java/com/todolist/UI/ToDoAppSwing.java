package com.todolist.UI;

import com.todolist.Task;
import com.todolist.TaskList;
import com.todolist.Status;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class ToDoAppSwing {
    private JFrame frame;
    private TaskList taskList;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JTable table;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoAppSwing().createAndShowGUI());
    }

    private void createAndShowGUI() {
        taskList = new TaskList();

        frame = new JFrame("To-Do List");
        URL url = ToDoAppSwing.class.getResource("/images/icon.png");
        if (url != null) {
            frame.setIconImage(new ImageIcon(url).getImage());
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLocationRelativeTo(null);

        JPanel buttonsPanel = new JPanel();
        JButton buttonShow = new JButton("Показать все задачи");
        JButton buttonAdd = new JButton("Добавить задачу");
        JButton buttonEdit = new JButton("Редактировать задачу");
        JButton buttonDelete = new JButton("Удалить задачу");
        JButton buttonExit = new JButton("Выйти");
        buttonsPanel.add(buttonShow);
        buttonsPanel.add(buttonAdd);
        buttonsPanel.add(buttonEdit);
        buttonsPanel.add(buttonDelete);
        buttonsPanel.add(buttonExit);
        frame.add(buttonsPanel, BorderLayout.NORTH);

        buttonShow.addActionListener(e -> showOrRefreshTable());
        buttonAdd.addActionListener(e -> showAddDialog());
        buttonEdit.addActionListener(e -> editSelectedTask());
        buttonDelete.addActionListener(e -> deleteSelectedTask());
        buttonExit.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private void showOrRefreshTable() {
        if (scrollPane == null) {
            String[] columns = {"ID", "Название", "Описание", "Статус", "Дата", "Категория"};
            tableModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(tableModel);
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = table.getSelectedRow();
                        if (row != -1) showEditDialog(row);
                    }
                }
            });
            scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.revalidate();
        }
        refreshTableData();
        scrollPane.setVisible(true);
    }

    private void refreshTableData() {
        tableModel.setRowCount(0);
        for (Task task : taskList.getList()) {
            tableModel.addRow(new Object[]{
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getStatus().getRussianName(),
                    task.getDate(),
                    task.getCategory()
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(frame, "Добавить задачу", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(20);
        JTextArea descArea = new JTextArea(4, 20);
        JScrollPane descScroll = new JScrollPane(descArea);
        JComboBox<Status> statusCombo = new JComboBox<>(Status.values());
        JTextField categoryField = new JTextField(20);

        statusCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Status) setText(((Status) value).getRussianName());
                return this;
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Название:*"), gbc);
        gbc.gridx = 1;
        dialog.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Описание:"), gbc);
        gbc.gridx = 1;
        dialog.add(descScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Статус:"), gbc);
        gbc.gridx = 1;
        dialog.add(statusCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Категория:"), gbc);
        gbc.gridx = 1;
        dialog.add(categoryField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Отмена");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        okButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Название не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(descArea.getText());
            task.setStatus((Status) statusCombo.getSelectedItem());
            task.setCategory(categoryField.getText());
            task.setDate(java.time.LocalDate.now());
            taskList.addTask(task);
            dialog.dispose();
            if (scrollPane != null) refreshTableData();
        });
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void editSelectedTask() {
        if (table == null || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Выберите задачу для редактирования", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int row = table.getSelectedRow();
        showEditDialog(row);
    }

    private void showEditDialog(int row) {
        int taskId = (int) tableModel.getValueAt(row, 0);
        Task taskToEdit = taskList.getList().stream().filter(t -> t.getId() == taskId).findFirst().orElse(null);
        if (taskToEdit == null) return;

        JDialog dialog = new JDialog(frame, "Редактировать задачу", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(taskToEdit.getTitle(), 20);
        JTextArea descArea = new JTextArea(taskToEdit.getDescription(), 4, 20);
        JScrollPane descScroll = new JScrollPane(descArea);
        JComboBox<Status> statusCombo = new JComboBox<>(Status.values());
        statusCombo.setSelectedItem(taskToEdit.getStatus());
        statusCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Status) setText(((Status) value).getRussianName());
                return this;
            }
        });
        JTextField categoryField = new JTextField(taskToEdit.getCategory(), 20);

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Название:*"), gbc);
        gbc.gridx = 1;
        dialog.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Описание:"), gbc);
        gbc.gridx = 1;
        dialog.add(descScroll, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Статус:"), gbc);
        gbc.gridx = 1;
        dialog.add(statusCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Категория:"), gbc);
        gbc.gridx = 1;
        dialog.add(categoryField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        okButton.addActionListener(e -> {
            String newTitle = titleField.getText().trim();
            if (newTitle.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Название не может быть пустым", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            taskToEdit.setTitle(newTitle);
            taskToEdit.setDescription(descArea.getText());
            taskToEdit.setStatus((Status) statusCombo.getSelectedItem());
            taskToEdit.setCategory(categoryField.getText());
            taskList.saveToFile();
            dialog.dispose();
            if (scrollPane != null) refreshTableData();
        });
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void deleteSelectedTask() {
        if (table == null || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(frame, "Выберите задачу для удаления", "Ошибка", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int row = table.getSelectedRow();
        int taskId = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Удалить задачу #" + taskId + "?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            taskList.removeTaskById(taskId);
            refreshTableData();
        }
    }
}