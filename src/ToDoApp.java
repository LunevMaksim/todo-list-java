import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ToDoApp {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        TaskList list = new TaskList();
        try {
            list.loadFromFile();
        } catch (Exception e) {
            System.out.println("Примечание: Файл с задачами не найден или поврежден. Начнем с пустого списка.");
        }
        System.out.println("===== To-Do List =====");
        menu(scanner, list);
        scanner.close();
    }
    public static void menu(Scanner scanner, TaskList list) throws IOException {
        boolean val = true;
        while (val){
            try {
                System.out.println("=== Меню команд ===");
                System.out.println("1. Показать все задачи");
                System.out.println("2. Добавить задачу");
                System.out.println("3. Удалить задачу");
                System.out.println("4. Выйти");
                System.out.print("Введите номер команды: ");
                int num = scanner.nextInt();
                scanner.nextLine();

                switch (num){
                    case 1:
                        list.getAllTasks();
                        break;
                    case 2:
                        list.addTask(scanner);
                        break;
                    case 3:
                        list.removeTask(scanner);
                        break;
                    case 4:
                        System.out.println("Удачи!");
                        val = false;
                        break;
                    default:
                        System.out.println("Ошибка! Повторите ввод данных!");
                }
            } catch (InputMismatchException exception){
                System.out.println("Ошибка! Введите номер команды ещё раз!");
                scanner.nextLine();
            }
        }
    }
}
