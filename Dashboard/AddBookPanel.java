package Dashboard;

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends JPanel {
    private JTextField isbnField, titleField, authorField, genreField, priceField, stockField;
    private InventorySystem inventorySystem;
    private AllBooksPanel allBooksPanel; // Reference to AllBooksPanel for refreshing

    public AddBookPanel(InventorySystem inventorySystem, AllBooksPanel allBooksPanel) {
        this.inventorySystem = inventorySystem;
        this.allBooksPanel = allBooksPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Color fieldBackground = new Color(245, 245, 245);
        Color fieldForeground = Color.BLACK;

        // Title
        JLabel titleLabel = new JLabel("Add New Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 30, 300, 40);
        add(titleLabel);

        // ISBN
        JLabel isbnLbl = new JLabel("ISBN:");
        isbnLbl.setBounds(100, 100, 300, 40);
        isbnLbl.setFont(labelFont);
        isbnLbl.setForeground(fieldForeground);
        add(isbnLbl);

        isbnField = new JTextField();
        isbnField.setBounds(100, 140, 430, 50);
        isbnField.setFont(fieldFont);
        isbnField.setBackground(fieldBackground);
        add(isbnField);

        // Title
        JLabel titleLbl = new JLabel("Title:");
        titleLbl.setBounds(100, 200, 300, 40);
        titleLbl.setFont(labelFont);
        titleLbl.setForeground(fieldForeground);
        add(titleLbl);

        titleField = new JTextField();
        titleField.setBounds(100, 240, 430, 50);
        titleField.setFont(fieldFont);
        titleField.setBackground(fieldBackground);
        add(titleField);

        // Author
        JLabel authorLbl = new JLabel("Author:");
        authorLbl.setBounds(100, 300, 300, 40);
        authorLbl.setFont(labelFont);
        authorLbl.setForeground(fieldForeground);
        add(authorLbl);

        authorField = new JTextField();
        authorField.setBounds(100, 340, 430, 50);
        authorField.setFont(fieldFont);
        authorField.setBackground(fieldBackground);
        add(authorField);

        // Genre
        JLabel genreLbl = new JLabel("Genre:");
        genreLbl.setBounds(600, 100, 300, 40);
        genreLbl.setFont(labelFont);
        genreLbl.setForeground(fieldForeground);
        add(genreLbl);

        genreField = new JTextField();
        genreField.setBounds(600, 140, 430, 50);
        genreField.setFont(fieldFont);
        genreField.setBackground(fieldBackground);
        add(genreField);

        // Price
        JLabel priceLbl = new JLabel("Price ($):");
        priceLbl.setBounds(600, 200, 300, 40);
        priceLbl.setFont(labelFont);
        priceLbl.setForeground(fieldForeground);
        add(priceLbl);

        priceField = new JTextField();
        priceField.setBounds(600, 240, 430, 50);
        priceField.setFont(fieldFont);
        priceField.setBackground(fieldBackground);
        add(priceField);

        // Stock
        JLabel stockLbl = new JLabel("Stock:");
        stockLbl.setBounds(600, 300, 300, 40);
        stockLbl.setFont(labelFont);
        stockLbl.setForeground(fieldForeground);
        add(stockLbl);

        stockField = new JTextField();
        stockField.setBounds(600, 340, 430, 50);
        stockField.setFont(fieldFont);
        stockField.setBackground(fieldBackground);
        add(stockField);

        // Buttons
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(400, 450, 120, 50);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 16));
        clearBtn.setBackground(new Color(220, 53, 69));
        clearBtn.setForeground(Color.lightGray);
        clearBtn.addActionListener(e -> clearFields());
        add(clearBtn);

        JButton addBtn = new JButton("Add Book");
        addBtn.setBounds(550, 450, 120, 50);
        addBtn.setFont(new Font("Arial", Font.BOLD, 16));
        addBtn.setBackground(new Color(40, 167, 69));
        addBtn.setForeground(Color.lightGray);
        addBtn.addActionListener(e -> addBook());
        add(addBtn);
    }

    private void clearFields() {
        isbnField.setText("");
        titleField.setText("");
        authorField.setText("");
        genreField.setText("");
        priceField.setText("");
        stockField.setText("");
    }

    private void addBook() {
        try {
            String isbn = isbnField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            int stock = Integer.parseInt(stockField.getText().trim());

            if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than zero", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "Stock cannot be negative", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = inventorySystem.addBook(isbn, title, author, genre, price, stock);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                // Refresh the AllBooksPanel table to show the newly added book
                if (allBooksPanel != null) {
                    allBooksPanel.refreshBookTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book. ISBN might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for price and stock", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}