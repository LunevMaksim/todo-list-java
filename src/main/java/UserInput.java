public interface UserInput {
    String readLine(String prompt);
    int readInt(String prompt);
    Status readStatus(String prompt);
    void print(String message);
    void println(String message);
    void printError(String message);
}
