public enum Status {
    NEW("Новая"),
    IN_PROGRESS("В работе"),
    COMPLETED("Выполнена");
    private final String russianName;

    Status(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
    public static Status fromRussianName(String input) {
        if (input == null) return null;

        String normalized = input.trim().toLowerCase();

        for (Status status : Status.values()) {
            if (status.getRussianName().toLowerCase().equals(normalized)) {
                return status;
            }
        }

        if (normalized.startsWith("нов")) return NEW;
        if (normalized.startsWith("в раб") || normalized.startsWith("раб")) return IN_PROGRESS;
        if (normalized.startsWith("вып") || normalized.startsWith("завер")) return COMPLETED;

        return null;
    }
}
