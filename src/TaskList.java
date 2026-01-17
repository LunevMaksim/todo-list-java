import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskList {
    private List<Task> list = new ArrayList<>();
    private FileManager fileManager = new FileManager();
    private int nextId = 1;

    public TaskList() {
        loadFromFile();
    }

    public void loadFromFile() {
        try {
            List<Task> loadedTasks = fileManager.loadAllTasks();
            list.clear();
            list.addAll(loadedTasks);

            // Находим максимальный ID для продолжения нумерации
            nextId = list.stream()
                    .mapToInt(Task::getId)
                    .max()
                    .orElse(0) + 1;
        } catch (IOException e) {
            System.out.println("Не удалось загрузить задачи: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try {
            fileManager.saveAllTasks(list);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public void addTask(Scanner scanner) throws IOException {
        FileManager fileManager = new FileManager();
        Task task = new Task();

        task.setId(nextId++);

        System.out.print("Введите заголовок задачи: ");
        String title = scanner.nextLine();
        task.setTitle(title);

        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine();
        task.setDescription(description);

        System.out.print("Введите статус задачи(Новая, в работе, выполнена): ");
        String string = scanner.nextLine();
        Status status = Status.fromRussianName(string);
        if (status == null) {
            System.out.println("Неверный статус! Установлено значение по умолчанию: Новая");
            status = Status.NEW;
        }
        task.setStatus(status);

        task.setDate(LocalDate.now());

        System.out.print("Введите категорию задачи: ");
        String category = scanner.nextLine();
        task.setCategory(category);

        getList().add(task);
        saveToFile();
        System.out.println("Задача была успешно добавлена!");

    }

    public void removeTask(Scanner scanner){
        if (getList().isEmpty() || getList() == null){
            System.out.println("Список задач пуст!");
        }
        else {

            System.out.print("Введите ID задачи, которую хотели бы удалить: ");
            try {
                int number = scanner.nextInt();
                if (number < 1 || number > getList().size()){
                    System.out.println("Такого ID не существует");
                }
                else {
                    for (int i = 0; i < getList().size(); i++) {
                        if (getList().get(i).getId() == number){
                            System.out.println("Задача №" + number + " '" + getList().get(i).getTitle() + "' " + "была успешно удалена");
                            getList().remove(i);
                            break;
                        }
                    }
                }
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Неверный формат ID");
            }
        }
    }

    public void getAllTasks() throws FileNotFoundException {
        if (list.isEmpty()) {
            System.out.println("Список задач пуст");
        }
        else {
            for (Task task : list) {
                System.out.println(task);
            }
        }
    }

    public List<Task> getList() {
        return list;
    }
}
