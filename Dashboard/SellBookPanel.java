package Dashboard;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellBookPanel extends JPanel {
    private JTextField isbnField, quantityField;
    private JPanel bookDetailsPanel, receiptPanel;
    private JTextArea bookInfoArea, receiptArea;
    private InventorySystem inventorySystem;
    private AllBooksPanel allBooksPanel; // Reference to refresh the table

    public SellBookPanel(InventorySystem inventorySystem, AllBooksPanel allBooksPanel) {
        this.inventorySystem = inventorySystem;
        this.allBooksPanel = allBooksPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Title
        JLabel titleLabel = new JLabel("Sell Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 30, 300, 40);
        add(titleLabel);

        // ISBN input
        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setBounds(50, 100, 100, 30);
        isbnLabel.setFont(labelFont);
        add(isbnLabel);

        isbnField = new JTextField();
        isbnField.setBounds(50, 140, 400, 40);
        isbnField.setFont(fieldFont);
        add(isbnField);

        JButton findBtn = new JButton("Find Book");
        findBtn.setBounds(470, 140, 120, 40);
        findBtn.setFont(new Font("Arial", Font.BOLD, 14));
        findBtn.setBackground(new Color(70, 130, 180));
        findBtn.setForeground(Color.lightGray);
        findBtn.addActionListener(e -> findBook());
        add(findBtn);

        // Book details area
        bookDetailsPanel = new JPanel();
        bookDetailsPanel.setLayout(null);
        bookDetailsPanel.setBackground(new Color(220, 255, 220));
        bookDetailsPanel.setBounds(50, 200, 800, 150);
        bookDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        bookInfoArea = new JTextArea();
        bookInfoArea.setBounds(20, 20, 760, 110);
        bookInfoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        bookInfoArea.setBackground(new Color(220, 255, 220));
        bookInfoArea.setEditable(false);
        bookInfoArea.setLineWrap(true);
        bookInfoArea.setWrapStyleWord(true);
        bookDetailsPanel.add(bookInfoArea);
        add(bookDetailsPanel);

        // Quantity
        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(50, 380, 100, 30);
        quantityLabel.setFont(labelFont);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(50, 420, 400, 40);
        quantityField.setFont(fieldFont);
        add(quantityField);

        JButton processSaleBtn = new JButton("Process Sale");
        processSaleBtn.setBounds(470, 420, 140, 40);
        processSaleBtn.setFont(new Font("Arial", Font.BOLD, 14));
        processSaleBtn.setBackground(new Color(40, 167, 69));
        processSaleBtn.setForeground(Color.lightGray);
        processSaleBtn.addActionListener(e -> processSale());
        add(processSaleBtn);

        // Receipt area
        JLabel receiptTitle = new JLabel("Receipt");
        receiptTitle.setBounds(50, 480, 100, 30);
        receiptTitle.setFont(labelFont);
        add(receiptTitle);

        receiptPanel = new JPanel();
        receiptPanel.setLayout(null);
        receiptPanel.setBackground(new Color(220, 255, 220));
        receiptPanel.setBounds(50, 520, 800, 140);
        receiptPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        receiptArea = new JTextArea();
        receiptArea.setBounds(20, 20, 760, 100);
        receiptArea.setFont(new Font("Arial", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(220, 255, 220));
        receiptArea.setEditable(false);
        receiptArea.setLineWrap(true);
        receiptArea.setWrapStyleWord(true);
        receiptPanel.add(receiptArea);
        add(receiptPanel);
    }

    private void findBook() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ISBN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = inventorySystem.findBookByIsbn(isbn);
        if (book != null) {
            String bookDetails = "BOOK FOUND:\n" +
                               "ISBN: " + book.getIsbn() + "\n" +
                               "Title: " + book.getTitle() + "\n" +
                               "Author: " + book.getAuthor() + "\n" +
                               "Price: $" + book.getPrice() + "\n" +
                               "Available Stock: " + book.getStockLevel();
            bookInfoArea.setText(bookDetails);
            quantityField.setText("1");
            // Clear previous receipt
            receiptArea.setText("");
        } else {
            bookInfoArea.setText("No book found with ISBN: " + isbn);
            quantityField.setText("");
            receiptArea.setText("");
        }
    }

    private void processSale() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ISBN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText().trim());
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double saleAmount = inventorySystem.processSale(isbn, quantity);
            if (saleAmount >= 0) {
                // Create receipt
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(new Date());
                
                Book book = inventorySystem.findBookByIsbn(isbn);
                String receipt = "SALE RECEIPT\n" +
                                "Date: " + currentTime + "\n" +
                                "ISBN: " + book.getIsbn() + "\n" +
                                "Price per unit: $" + book.getPrice() + "\n" +
                                "Quantity: " + quantity + "\n" +
                                "Total: $" + String.format("%.2f", saleAmount) + "\n\n" +
                                "Thank you for your purchase!";
                receiptArea.setText(receipt);

                // Update book info to show new stock level
                String bookDetails = "BOOK FOUND:\n" +
                                   "ISBN: " + book.getIsbn() + "\n" +
                                   "Title: " + book.getTitle() + "\n" +
                                   "Author: " + book.getAuthor() + "\n" +
                                   "Price: $" + book.getPrice() + "\n" +
                                   "Available Stock: " + book.getStockLevel() + "\n\n" +
                                   "Sale completed successfully!";
                bookInfoArea.setText(bookDetails);
                bookInfoArea.setForeground(new Color(0, 128, 0)); // Green color for success message

                // Refresh the AllBooksPanel table to show updated stock
                allBooksPanel.refreshBookTable();

                JOptionPane.showMessageDialog(this, "Sale processed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear the form for next sale
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to process sale. Insufficient stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        isbnField.setText("");
        quantityField.setText("");
        bookInfoArea.setText("");
        bookInfoArea.setForeground(Color.BLACK); // Reset color to default
    }
}