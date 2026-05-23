package com.todolist;

import java.io.IOException;

public class ToDoApp {
    public static void main(String[] args) throws IOException {

        System.out.println("===== To-Do List =====");
        TaskList list = new TaskList();
        
        try {
            list.loadFromFile();
        } catch (Exception e) {
            System.out.println("Примечание: Файл с задачами не найден или поврежден. Начнем с пустого списка.");
        }

        ConsoleUserInput input = new ConsoleUserInput();
        ConsoleView consoleView = new ConsoleView();
        consoleView.allMenu(list, input);

    }
}

