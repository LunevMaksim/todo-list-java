import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUserInput implements UserInput{
    private Scanner scanner;
    public ConsoleUserInput(){
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true){
            try {
                System.out.print(prompt + " ");
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException exception){
                System.err.println("Неверный ввод, введите число");
                scanner.next();
            }
        }
    }

    @Override
    public Status readStatus(String prompt) {
        while (true){
            String string = readLine(prompt);
            Status status = Status.fromRussianName(string);
            if (status != null) {
                return status;
            }
            System.err.println("Неверный статус! Повторите попытку");
        }
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String message) {
        System.err.println("Ошибка: " + message);
    }
}
