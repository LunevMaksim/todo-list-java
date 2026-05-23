package UI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ToDoAppSwing {
    public static void main(String[] args) {
        JFrame frame = new JFrame("To-Do LIst");

        URL url = ToDoAppSwing.class.getResource("/images/icon.png");
        if (url != null) {
            frame.setIconImage(new ImageIcon(url).getImage());
        }

        JPanel buttonsPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 900);
        frame.setLocationRelativeTo(null);

        JButton buttonAllTasks = new JButton("Показать все задачи");
        JButton buttonAddTasks = new JButton("Добавить задачу");
        JButton buttonChangeTasks = new JButton("Изменить задачу");
        JButton buttonDeletedTasks = new JButton("Удалить задачу");

        buttonsPanel.add(buttonAllTasks);
        buttonsPanel.add(buttonAddTasks);
        buttonsPanel.add(buttonChangeTasks);
        buttonsPanel.add(buttonDeletedTasks);
        frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);








        frame.setVisible(true);

    }
}
