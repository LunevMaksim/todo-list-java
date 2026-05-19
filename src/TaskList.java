import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskList  {
    private List<Task> list = new ArrayList<>();
    private FileManager fileManager = new FileManager();
    private int nextId = 1;

    public TaskList() {
        loadFromFile();
    }

    //Загружает из файла
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
            System.err.println("Не удалось загрузить задачи: " + e.getMessage());
        }
    }

    //Сохраняет в файл
    private void saveToFile() {
        try {
            fileManager.saveAllTasks(list);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    //Добавляет задачу
    public void addTask(UserInput input) {
        Task task = new Task();

        task.setId(nextId++);

        String title = input.readLine("Введите заголовок задачи: ");
        task.setTitle(title);

        String description = input.readLine("Введите описание задачи: ");
        task.setDescription(description);

        task.setStatus(askForStatus(input));

        task.setDate(LocalDate.now());

        String category = input.readLine("Введите категорию задачи: ");
        task.setCategory(category);

        getList().add(task);
        saveToFile();
        input.println("Задача была успешно добавлена!");

    }

    //Удаляет задачу
    public void removeTask(UserInput input){
        if (getList().isEmpty()){
            input.printError("Список задач пуст!");
        }
        else {
            int number = input.readInt("Введите ID задачи, которую хотели бы удалить: ");
            int maxId = list.stream().mapToInt(Task::getId).max().orElse(0);
            if (number < 1 || number > maxId){
                input.printError("Такого ID не существует");
            }
            else {
                boolean found = false;
                for (int i = 0; i < getList().size(); i++) {
                    if (getList().get(i).getId() == number){
                        input.println("Задача №" + number + " '" + getList().get(i).getTitle() + "' " + "была успешно удалена");
                        getList().remove(i);
                        saveToFile();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    input.printError("Задача с ID " + number + " не найдена");
                }
            }
        }
    }

    //Вывод всех задач
    public void getAllTasks(UserInput input) {
        if (list.isEmpty()) {
            input.println("Список задач пуст");
        }
        else {
            for (Task task : list) {
                input.println(String.valueOf(task));
            }
        }
    }

    //Изменение задачи
    public void editTask(UserInput input){
        if (list.isEmpty()) {
            input.printError("Список задач пуст");
            return;
        }
        int idSearch = input.readInt("Введите ID задачи для редактирования: ");
        boolean valForSearch = false;

        for (int i = 0; i < getList().size(); i++) {
            if (getList().get(i).getId() == idSearch){
                valForSearch = true;
                Task taskEdit = getList().get(i);
                input.println("Что вы хотели бы изменить?");
                input.println("1.Название");
                input.println("2.Описание");
                input.println("3.Статус");
                input.println("4.Категория");
                boolean valForCommand = false;
                while (!valForCommand){
                        int num = input.readInt("Введите номер команды: ");
                        switch (num){
                            case 1:
                                String newTitle = input.readLine("Введите новое название: ");
                                taskEdit.setTitle(newTitle);
                                input.println("Название задачи №" + idSearch +" было успешно изменено");
                                valForCommand = true;
                                break;
                            case 2:
                                String newDescription = input.readLine("Введите новое описание: ");
                                taskEdit.setDescription(newDescription);
                                valForCommand = true;
                                input.println("Описание задачи №" + idSearch +" было успешно изменено");
                                break;
                            case 3:
                                taskEdit.setStatus(askForStatus(input));
                                valForCommand = true;
                                input.println("Статус задачи №" + idSearch +" был успешно изменён");
                                break;
                            case 4:
                                String newCategory = input.readLine("Введите новую категорию: ");
                                taskEdit.setCategory(newCategory);
                                valForCommand = true;
                                input.println("Категория задачи №" + idSearch +" была успешно изменена");
                                break;
                            default:
                                input.printError("Ошибка! Номер команды введён неверно!");
                        }
                }
                break;
            }
        }

        if (!valForSearch){
            input.printError("Ошибка! Id не был найден");
        }
        else{
            saveToFile();
        }
    }

    //Статус задачи
    public Status askForStatus(UserInput input) {
        return input.readStatus("Введите статус задачи(Новая, в работе, выполнена): ");
    }

    public List<Task> getList() {
        return list;
    }

}
