package Dashboard;

import javax.swing.*;

import Extras.RoundedButton;
import Extras.RoundedTextField;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellBookPanel extends JPanel {
    private RoundedTextField isbnField, quantityField;
    private JPanel bookDetailsPanel, receiptPanel;
    private JTextArea bookInfoArea, receiptArea;
    private InventorySystem inventorySystem;
    private AllBooksPanel allBooksPanel; // Reference to refresh the table

    public SellBookPanel(InventorySystem inventorySystem, AllBooksPanel allBooksPanel) {

        Font titleFont = new Font("Red Hat Display", Font.PLAIN,35);
        Font labelFont = new Font("Red Hat Display", Font.PLAIN,25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Font reciptFont = new Font("Red Hat Display", Font.PLAIN, 12);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);
        Color hoverColor = new Color(140, 169, 157);

        this.inventorySystem = inventorySystem;
        this.allBooksPanel = allBooksPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Sell Book");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(64, 37, 232, 46);
        add(titleLabel);

        // ISBN input
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(64, 98, 272, 40);
        isbnLabel.setFont(labelFont);
        add(isbnLabel);

        isbnField = new RoundedTextField(30);
        isbnField.setBounds(64, 152, 372, 50);
        isbnField.setBackground(fieldBackground);
        isbnField.setFont(fieldFont);
        add(isbnField);

        RoundedButton findBtn = new RoundedButton("Find Book", 30);
        findBtn.setBounds(470, 152, 205, 50);
        findBtn.setFont(buttonFont);
        findBtn.setBackground(fieldBackground);
        findBtn.setForeground(fieldForeground);
        findBtn.setHoverBackgroundColor(hoverColor);
        findBtn.addActionListener(e -> findBook());
        add(findBtn);

        // Book details area
        bookDetailsPanel = new JPanel();
        bookDetailsPanel.setLayout(null);
        bookDetailsPanel.setBackground(fieldBackground);
        bookDetailsPanel.setBounds(64, 223, 852, 142);
        bookDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        bookInfoArea = new JTextArea();
        bookInfoArea.setBounds(20, 20, 800, 102);
        bookInfoArea.setFont(reciptFont);
        bookInfoArea.setBackground(fieldBackground);
        bookInfoArea.setEditable(false);
        bookInfoArea.setLineWrap(true);
        bookInfoArea.setWrapStyleWord(true);
        bookDetailsPanel.add(bookInfoArea);
        add(bookDetailsPanel);

        // Quantity
        JLabel quantityLbl = new JLabel("Quantity:");
        quantityLbl.setBounds(64, 375, 372, 40);
        quantityLbl.setFont(labelFont);
        add(quantityLbl);

        quantityField = new RoundedTextField(30);
        quantityField.setBounds(64, 429, 372, 50);
        quantityField.setBackground(fieldBackground);
        quantityField.setFont(fieldFont);
        add(quantityField);

        RoundedButton processSaleBtn = new RoundedButton("Process Sale", 30);
        processSaleBtn.setBounds(470, 429, 205, 50);
        processSaleBtn.setFont(buttonFont);
        processSaleBtn.setBackground(fieldBackground);
        processSaleBtn.setForeground(fieldForeground);
        processSaleBtn.setHoverBackgroundColor(hoverColor);
        processSaleBtn.addActionListener(e -> processSale());
        add(processSaleBtn);

        // Receipt area
        receiptPanel = new JPanel();
        receiptPanel.setLayout(null);
        receiptPanel.setBackground(fieldBackground);
        receiptPanel.setBounds(64, 500, 852, 142);
        receiptPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        receiptArea = new JTextArea();
        receiptArea.setBounds(20, 20, 800, 102);
        receiptArea.setFont(reciptFont);
        receiptArea.setBackground(fieldBackground);
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
                               "Price: BDT " + book.getPrice() + "\n" +
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
                                "Price per unit: BDT " + book.getPrice() + "\n" +
                                "Quantity: " + quantity + "\n" +
                                "Total: BDT " + String.format("%.2f", saleAmount) + "\n\n" +
                                "Thank you for your purchase!";
                receiptArea.setText(receipt);

                // Update book info to show new stock level
                String bookDetails = "BOOK FOUND:\n" +
                                   "ISBN: " + book.getIsbn() + "\n" +
                                   "Title: " + book.getTitle() + "\n" +
                                   "Author: " + book.getAuthor() + "\n" +
                                   "Price: BDT " + book.getPrice() + "\n" +
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