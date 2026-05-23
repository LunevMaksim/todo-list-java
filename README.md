# To-Do List на Java

[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://java.com)
[![Maven](https://img.shields.io/badge/Maven-3.8+-orange.svg)](https://maven.apache.org)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

Консольный и графический (Swing) менеджер задач с сохранением в CSV и сборкой через Maven.  
Учебный проект для закрепления Java Core, ООП, работы с файлами, абстракции ввода/вывода и Maven.

## 📋 Оглавление
- [Функционал](#-функционал)
- [Технологии](#-технологии)
- [Установка и запуск](#-установка-и-запуск)
- [Структура проекта](#-структура-проекта)
- [Архитектура](#-архитектура)
- [Скриншоты](#-скриншоты)
- [Планы по развитию](#-планы-по-развитию)
- [Автор](#-автор)

## ✨ Функционал

### Консольная версия (`com.todolist.ToDoApp`)
- ✅ Добавление, просмотр, редактирование, удаление задач
- ✅ Статусы (Новая, В работе, Выполнена) с интеллектуальным вводом
- ✅ Категории, дата создания, автоматическая нумерация ID
- ✅ Сохранение в CSV и загрузка при старте
- ✅ Полная обработка ошибок ввода

### Графическая версия (`ToDoAppSwing`) – в разработке
- 🚧 Главное окно с кнопками и областью вывода
- 🚧 Иконка приложения (загружается из ресурсов)
- 🚧 Интеграция с `com.todolist.TaskList` через `SwingUserInput`

## 🛠 Технологии

- **Java 17** – язык
- **Maven** – сборка и управление зависимостями
- **Swing** – графический интерфейс (в процессе)
- **CSV** – хранение данных
- **com.todolist.UserInput** – абстракция ввода/вывода (позволяет легко менять UI)

## 🚀 Установка и запуск

### Требования
- JDK 17 или выше
- Maven 3.8+ (опционально, можно использовать встроенный в IDEA)

### Консольная версия

```bash
git clone https://github.com/LunevMaksim/todo-list-java.git
cd todo-list-java
mvn clean package
java -jar target/todo-list-java-1.0-SNAPSHOT.jar
```

### Графическая версия

(Пока в разработке, но уже можно собрать и запустить)

Тот же JAR запустит графическое окно с кнопками. Функциональность кнопок будет добавлена в ближайшее время.

## 📁 Структура проекта

```
todo-list-java/
├── pom.xml                     # Maven конфигурация
├── src/
│   ├── main/
│   │   ├── java/               # Исходный код
│   │   │   ├── UI/             # Графический интерфейс
│   │   │   │   ├── ToDoAppSwing.java
│   │   │   │   └── SwingUserInput.java
│   │   │   ├── com.todolist.ConsoleUserInput.java
│   │   │   ├── com.todolist.ConsoleView.java
│   │   │   ├── com.todolist.FileManager.java
│   │   │   ├── com.todolist.Status.java
│   │   │   ├── com.todolist.Task.java
│   │   │   ├── com.todolist.TaskList.java
│   │   │   ├── com.todolist.ToDoApp.java
│   │   │   └── com.todolist.UserInput.java
│   │   └── resources/          # Ресурсы
│   │       └── images/
│   │           └── icon.png    # Иконка приложения
├── README.md
├── LICENSE
└── .gitignore
```

## 🏗 Архитектура

- **com.todolist.UserInput** – интерфейс для ввода/вывода (абстракция).
- **com.todolist.ConsoleUserInput** – реализация для консоли.
- **SwingUserInput** – реализация для графического интерфейса (будет использовать диалоги).
- **com.todolist.TaskList** – бизнес-логика, не зависит от конкретного UI.
- **com.todolist.FileManager** – работа с CSV.
- **ToDoAppSwing** – главное окно, подключает `com.todolist.TaskList` и `SwingUserInput`.

Такое разделение позволяет легко заменять консоль на окна без изменения кода задач.

## 📸 Скриншоты

*Графический интерфейс в разработке – скриншоты появятся позже.*

## 🔮 Планы по развитию

- [x] Maven сборка и ресурсы
- [x] Иконка приложения
- [ ] Полная реализация `SwingUserInput`
- [ ] Вывод списка задач в `JTextArea`
- [ ] Обработчики кнопок (добавить, редактировать, удалить, обновить)
- [ ] Поиск, фильтрация, сортировка
- [ ] Экспорт в JSON
- [ ] Юнит-тесты

## 👨‍💻 Автор

**Максим** – студент ВГУ ФКН, начинающий Java-разработчик

- GitHub: [LunevMaksim](https://github.com/LunevMaksim)
- Telegram: [@Arbyzuk36](https://t.me/Arbyzuk36)
- Email: maksimlunev16@gmail.com

---

⭐ **Если проект вам полезен, поставьте звезду!**