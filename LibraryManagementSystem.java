import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

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

public class LibraryManagementSystem {
    private static ArrayList<Book> books = new ArrayList<>();
    private static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        loadBooks();
        SwingUtilities.invokeLater(LibraryManagementSystem::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

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

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(deleteButton);
        panel.add(updateButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                books.add(new Book(title, author));
                saveBooks();
                titleField.setText("");
                authorField.setText("");
                JOptionPane.showMessageDialog(frame, "Book added successfully.");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                for (Book book : books) {
                    textArea.append(book + "\n");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                books.removeIf(book -> book.title.equalsIgnoreCase(title));
                saveBooks();
                titleField.setText("");
                JOptionPane.showMessageDialog(frame, "Book deleted successfully.");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldTitle = titleField.getText();
                for (Book book : books) {
                    if (book.title.equalsIgnoreCase(oldTitle)) {
                        String newTitle = JOptionPane.showInputDialog("Enter new title:");
                        String newAuthor = JOptionPane.showInputDialog("Enter new author:");
                        book.title = newTitle;
                        book.author = newAuthor;
                        saveBooks();
                        titleField.setText("");
                        JOptionPane.showMessageDialog(frame, "Book updated successfully.");
                        break;
                    }
                }
            }
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
