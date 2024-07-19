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
        return "Title: " + title + ", Author: " + author;
    }
}

public class LibraryManagementSystem {
    private static ArrayList<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Exit");
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
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
