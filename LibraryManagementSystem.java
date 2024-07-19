import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Book {
    String title;
    String author;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}

public class LibraryManagementSystem{
    private static ArrayList<Book> books = new ArrayList<>();
    private static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        loadBooks();
        SwingUtilities.invokeLater(LibraryManagementSystem::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        JButton addButton = new JButton("Add Book");
        JButton viewButton = new JButton("View Books");
        JButton deleteButton = new JButton("Delete Book");
        JButton updateButton = new JButton("Update Book");
        JButton searchButton = new JButton("Search Book");
        JButton sortButton = new JButton("Sort Books");

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(searchButton);
        panel.add(sortButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Title and Author cannot be empty.");
                return;
            }
            books.add(new Book(title, author));
            saveBooks();
            titleField.setText("");
            authorField.setText("");
            JOptionPane.showMessageDialog(frame, "Book added successfully.");
        });

        viewButton.addActionListener(e -> {
            textArea.setText("");
            for (Book book : books) {
                textArea.append(book + "\n");
            }
        });

        deleteButton.addActionListener(e -> {
            String title = titleField.getText();
            boolean removed = books.removeIf(book -> book.title.equalsIgnoreCase(title));
            if (removed) {
                saveBooks();
                titleField.setText("");
                JOptionPane.showMessageDialog(frame, "Book deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "No book found with that title.");
            }
        });

        updateButton.addActionListener(e -> {
            String oldTitle = titleField.getText();
            for (Book book : books) {
                if (book.title.equalsIgnoreCase(oldTitle)) {
                    String newTitle = JOptionPane.showInputDialog("Enter new title:");
                    String newAuthor = JOptionPane.showInputDialog("Enter new author:");
                    if (newTitle != null && !newTitle.isEmpty() && newAuthor != null && !newAuthor.isEmpty()) {
                        book.title = newTitle;
                        book.author = newAuthor;
                        saveBooks();
                        titleField.setText("");
                        JOptionPane.showMessageDialog(frame, "Book updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "New title and author cannot be empty.");
                    }
                    break;
                }
            }
        });

        searchButton.addActionListener(e -> {
            String title = titleField.getText();
            textArea.setText("");
            for (Book book : books) {
                if (book.title.equalsIgnoreCase(title)) {
                    textArea.append(book + "\n");
                }
            }
        });

        sortButton.addActionListener(e -> {
            Collections.sort(books, Comparator.comparing(b -> b.title));
            JOptionPane.showMessageDialog(frame, "Books sorted by title.");
        });

        frame.setVisible(true);
    }

    private static void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" by ");
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
