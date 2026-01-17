import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String description;
    private Status status;
    private LocalDate date;
    private String category;


    public Task(int id, String title, String description, Status status, LocalDate date, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.category = category;
    }
    public Task() {
    }


    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Status getStatus() {
        return status;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Задача №" + id +
                ", название: '" + title + '\'' +
                ", описание: '" + description + '\'' +
                ", статус: '" + status.getRussianName() + "'" +
                ", дата: " + date +
                ", категория: '" + category + '\'';
    }

    public String toCsvString() {
        return id + ";" + title + ";" + description + ";" +
                status.name() + ";" + date + ";" + category;
    }


    public static Task fromCsvString(String csvLine) {
        String[] parts = csvLine.split(";");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Некорректный формат CSV строки: " + csvLine);
        }

        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        String description = parts[2];
        Status status = Status.valueOf(parts[3]);
        LocalDate date = LocalDate.parse(parts[4]);
        String category = parts[5];

        return new Task(id, title, description, status, date, category);
    }
}
