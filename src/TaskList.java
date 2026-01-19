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

        task.setStatus(askForStatus(scanner));

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
                int maxId = list.stream().mapToInt(Task::getId).max().orElse(0);
                if (number < 1 || number > maxId){
                    System.out.println("Такого ID не существует");
                }
                else {
                    boolean found = false;
                    for (int i = 0; i < getList().size(); i++) {
                        if (getList().get(i).getId() == number){
                            System.out.println("Задача №" + number + " '" + getList().get(i).getTitle() + "' " + "была успешно удалена");
                            getList().remove(i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Задача с ID " + number + " не найдена");
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

    public void editTask(Scanner scanner){
        if (list.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }
        int idSearch;
        while (true){
            try {
                System.out.print("Введите ID задачи для редактирования: ");
                idSearch = scanner.nextInt();
                break;
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Вы ввели не числовое значение!");
                scanner.nextLine();
            }
        }
        boolean valForSearch = false;
        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getId() == idSearch){
                valForSearch = true;
                Task taskEdit = getList().get(i);
                System.out.println("Что вы хотели бы изменить?");
                System.out.println("1.Название");
                System.out.println("2.Описание");
                System.out.println("3.Статус");
                System.out.println("4.Категория");
                boolean valForCommand = false;
                while (!valForCommand){
                    try {
                        System.out.print("Введите номер команды: ");
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        switch (num){
                            case 1:
                                System.out.print("Введите новое название: ");
                                String newTitle = scanner.nextLine();
                                taskEdit.setTitle(newTitle);
                                System.out.println("Название задачи №" + idSearch +" было успешно изменено");
                                valForCommand = true;
                                break;
                            case 2:
                                System.out.print("Введите новое описание: ");
                                String newDescription = scanner.nextLine();
                                taskEdit.setDescription(newDescription);
                                valForCommand = true;
                                System.out.println("Описание задачи №" + idSearch +" было успешно изменено");
                                break;
                            case 3:
                                taskEdit.setStatus(askForStatus(scanner));
                                valForCommand = true;
                                System.out.println("Статус задачи №" + idSearch +" был успешно изменён");
                                break;
                            case 4:
                                System.out.print("Введите новую категорию: ");
                                String newCategory = scanner.nextLine();
                                taskEdit.setCategory(newCategory);
                                valForCommand = true;
                                System.out.println("Категория задачи №" + idSearch +" была успешно изменена");
                                break;
                            default:
                                System.out.println("Ошибка! Номер команды введён неверно!");
                        }
                    } catch (InputMismatchException exception){
                        System.out.println("Ошибка! Введено не числовое значение!");
                        scanner.nextLine();
                    }
                }
                break;
            }
        }

        if (!valForSearch){
            System.out.println("Ошибка! Id не был найден");
        }
        else{
            saveToFile();
        }
    }
    public Status askForStatus(Scanner scanner) {
        System.out.print("Введите статус задачи(Новая, в работе, выполнена): ");
        String input = scanner.nextLine();
        Status status = Status.fromRussianName(input);
        if (status == null) {
            System.out.println("Неверный статус! Установлено значение по умолчанию: Новая");
            status = Status.NEW;
        }
        return status;
    }

    public List<Task> getList() {
        return list;
    }
}
