import java.io.IOException;

public class ConsoleView {

    public ConsoleView() {}

    public void allMenu(TaskList list, UserInput input) {
        boolean val = true;
        while (val) {
            int num = input.readInt(menu(input));
            switch (num) {
                case 1:
                    list.getAllTasks(input);
                    break;
                case 2:
                    list.addTask(input);
                    break;
                case 3:
                    list.editTask(input);
                    break;
                case 4:
                    list.removeTask(input);
                    break;
                case 5:
                    input.println("Удачи!");
                    val = false;
                    break;
                default:
                    input.printError("Ошибка! Повторите ввод данных!");
            }
        }
    }

    // Вывод меню
    public String menu(UserInput input){
        input.println("=== Меню команд ===");
        input.println("1. Показать все задачи");
        input.println("2. Добавить задачу");
        input.println("3. Редактировать задачу");
        input.println("4. Удалить задачу");
        input.println("5. Выйти");
        input.print("Введите номер команды: ");
        return "";
    }
}
