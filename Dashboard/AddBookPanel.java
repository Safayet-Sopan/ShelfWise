package Dashboard;

import javax.swing.*;

import Extras.RoundedButton;
import Extras.RoundedTextField;

import java.awt.*;

public class AddBookPanel extends JPanel {
    private RoundedTextField isbnField, titleField, authorField, genreField, priceField, stockField;
    private InventorySystem inventorySystem;
    private AllBooksPanel allBooksPanel; // Reference to AllBooksPanel for refreshing

    public AddBookPanel(InventorySystem inventorySystem, AllBooksPanel allBooksPanel) {

        Font titleFont = new Font("Red Hat Display", Font.PLAIN,35);
        Font labelFont = new Font("Red Hat Display", Font.PLAIN,25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);
        Color hoverColor = new Color(140, 169, 157);

        this.inventorySystem = inventorySystem;
        this.allBooksPanel = allBooksPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Add Book");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(64, 37, 232, 46);
        add(titleLabel);

        // ISBN
        JLabel isbnLbl = new JLabel("ISBN:");
        isbnLbl.setBounds(64, 147, 272, 40);
        isbnLbl.setFont(labelFont);
        isbnLbl.setForeground(fieldForeground);
        add(isbnLbl);

        isbnField = new RoundedTextField(30);
        isbnField.setBounds(64, 197, 389, 50);
        isbnField.setFont(fieldFont);
        isbnField.setBackground(fieldBackground);
        add(isbnField);

        // Title
        JLabel titleLbl = new JLabel("Title:");
        titleLbl.setBounds(64, 271, 272, 40);
        titleLbl.setFont(labelFont);
        titleLbl.setForeground(fieldForeground);
        add(titleLbl);

        titleField = new RoundedTextField(30);
        titleField.setBounds(64, 321, 389, 50);
        titleField.setFont(fieldFont);
        titleField.setBackground(fieldBackground);
        add(titleField);

        // Author
        JLabel authorLbl = new JLabel("Author:");
        authorLbl.setBounds(64, 395, 272, 40);
        authorLbl.setFont(labelFont);
        authorLbl.setForeground(fieldForeground);
        add(authorLbl);

        authorField = new RoundedTextField(30);
        authorField.setBounds(64, 445, 389, 50);
        authorField.setFont(fieldFont);
        authorField.setBackground(fieldBackground);
        add(authorField);

        // Genre
        JLabel genreLbl = new JLabel("Genre:");
        genreLbl.setBounds(513, 147, 272, 40);
        genreLbl.setFont(labelFont);
        genreLbl.setForeground(fieldForeground);
        add(genreLbl);

        genreField = new RoundedTextField(30);
        genreField.setBounds(513, 197, 389, 50);
        genreField.setFont(fieldFont);
        genreField.setBackground(fieldBackground);
        add(genreField);

        // Price
        JLabel priceLbl = new JLabel("Price BDT:");
        priceLbl.setBounds(513, 271, 272, 40);
        priceLbl.setFont(labelFont);
        priceLbl.setForeground(fieldForeground);
        add(priceLbl);

        priceField = new RoundedTextField(30);
        priceField.setBounds(513, 321, 389, 50);
        priceField.setFont(fieldFont);
        priceField.setBackground(fieldBackground);
        add(priceField);

        // Stock
        JLabel stockLbl = new JLabel("Stock:");
        stockLbl.setBounds(513, 395, 272, 40);
        stockLbl.setFont(labelFont);
        stockLbl.setForeground(fieldForeground);
        add(stockLbl);

        stockField = new RoundedTextField(30);
        stockField.setBounds(513, 445, 389, 50);
        stockField.setFont(fieldFont);
        stockField.setBackground(fieldBackground);
        add(stockField);

        // Buttons
        RoundedButton clearBtn = new RoundedButton("Clear", 30);
        clearBtn.setBounds(248, 553, 205, 50);
        clearBtn.setFont(buttonFont);
        clearBtn.setBackground(fieldBackground);
        clearBtn.setForeground(fieldForeground);
        clearBtn.setHoverBackgroundColor(hoverColor);
        clearBtn.addActionListener(e -> clearFields());
        add(clearBtn);

        RoundedButton addBtn = new RoundedButton("Add Book", 30);
        addBtn.setBounds(513, 553, 205, 50);
        addBtn.setFont(buttonFont);
        addBtn.setBackground(fieldBackground);
        addBtn.setForeground(fieldForeground);
        addBtn.setHoverBackgroundColor(hoverColor);
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