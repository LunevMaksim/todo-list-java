import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String FILE_NAME = "To-Do List.csv";

    public void saveAllTasks(List<Task> tasks) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toCsvString());
            }
        }
    }

    public List<Task> loadAllTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromCsvString(line);
                    tasks.add(task);
                } catch (Exception e) {
                    System.err.println("Ошибка при чтении строки: " + line + " - " + e.getMessage());
                }
            }
        }
        return tasks;
    }
}