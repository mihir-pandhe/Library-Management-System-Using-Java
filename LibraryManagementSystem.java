import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    String title;
    String author;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return title + "," + author;
    }
}

public class LibraryManagementSystem {
    private static ArrayList<Book> books = new ArrayList<>();
    private static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        loadBooks();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Update Book");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    books.add(new Book(title, author));
                    saveBooks();
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    System.out.println("Available Books:");
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 3:
                    System.out.print("Enter book title to search: ");
                    String searchTitle = scanner.nextLine();
                    for (Book book : books) {
                        if (book.title.equalsIgnoreCase(searchTitle)) {
                            System.out.println(book);
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter book title to delete: ");
                    String deleteTitle = scanner.nextLine();
                    books.removeIf(book -> book.title.equalsIgnoreCase(deleteTitle));
                    saveBooks();
                    System.out.println("Book deleted successfully.");
                    break;
                case 5:
                    System.out.print("Enter book title to update: ");
                    String oldTitle = scanner.nextLine();
                    for (Book book : books) {
                        if (book.title.equalsIgnoreCase(oldTitle)) {
                            System.out.print("Enter new title: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("Enter new author: ");
                            String newAuthor = scanner.nextLine();
                            book.title = newTitle;
                            book.author = newAuthor;
                            saveBooks();
                            System.out.println("Book updated successfully.");
                            break;
                        }
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    books.add(new Book(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load books: " + e.getMessage());
        }
    }

    private static void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save books: " + e.getMessage());
        }
    }
}
