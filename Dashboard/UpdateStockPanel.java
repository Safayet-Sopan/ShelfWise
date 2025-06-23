package Dashboard;

import javax.swing.*;

import Extras.RoundedButton;
import Extras.RoundedTextField;

import java.awt.*;

public class UpdateStockPanel extends JPanel {
    private RoundedTextField isbnField, newStockField;
    private JPanel bookDetailsPanel;
    private JPanel bookInfoPanel; // Changed from JLabel to JPanel
    private InventorySystem inventorySystem;
    private AllBooksPanel allBooksPanel;

    public UpdateStockPanel(InventorySystem inventorySystem, AllBooksPanel allBooksPanel) {

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
        JLabel titleLabel = new JLabel("Update Stock");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(64, 37, 232, 46);
        add(titleLabel);

        // ISBN input
        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setBounds(64, 103, 272, 30);
        isbnLabel.setFont(labelFont);
        add(isbnLabel);

        isbnField = new RoundedTextField(30);
        isbnField.setBounds(64, 157, 372, 50);
        isbnField.setFont(fieldFont);
        isbnField.setBackground(fieldBackground);
        add(isbnField);

        RoundedButton findBtn = new RoundedButton("Find Book", 30);
        findBtn.setBounds(470, 157, 205, 50);
        findBtn.setFont(buttonFont);
        findBtn.setBackground(fieldBackground);
        findBtn.setForeground(fieldForeground);
        findBtn.setHoverBackgroundColor(hoverColor);
        findBtn.addActionListener(e -> findBook());
        add(findBtn);

        // Book details area (light green)
        bookDetailsPanel = new JPanel();
        bookDetailsPanel.setLayout(null);
        bookDetailsPanel.setBackground(fieldBackground);
        bookDetailsPanel.setBounds(64, 236, 852, 283);
        bookDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Create a panel to hold book info components instead of using HTML
        bookInfoPanel = new JPanel();
        bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
        bookInfoPanel.setBounds(20, 20, 812, 243);
        bookInfoPanel.setBackground(fieldBackground);
        bookDetailsPanel.add(bookInfoPanel);
        add(bookDetailsPanel);

        // New stock level
        JLabel stockLabel = new JLabel("New Stock Amount:");
        stockLabel.setBounds(64, 538, 372, 40);
        stockLabel.setFont(labelFont);
        add(stockLabel);

        newStockField = new RoundedTextField(30);
        newStockField.setBounds(64, 592, 372, 50);
        newStockField.setFont(fieldFont);
        newStockField.setBackground(fieldBackground);
        add(newStockField);

        RoundedButton updateBtn = new RoundedButton("Update Stock",30);
        updateBtn.setBounds(470, 592, 205, 50);
        updateBtn.setFont(buttonFont);
        updateBtn.setBackground(fieldBackground);
        updateBtn.setForeground(fieldForeground);
        updateBtn.setHoverBackgroundColor(hoverColor);
        updateBtn.addActionListener(e -> updateStock());
        add(updateBtn);
    }

    private void findBook() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ISBN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = inventorySystem.findBookByIsbn(isbn);
        if (book != null) {
            // Clear previous content
            bookInfoPanel.removeAll();
            
            // Create labels for book details using pure Java components
            JLabel headerLabel = new JLabel("Book Found:");
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            bookInfoPanel.add(headerLabel);
            
            bookInfoPanel.add(Box.createVerticalStrut(5));
            
            JLabel isbnLabel = new JLabel("ISBN: " + book.getIsbn());
            isbnLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(isbnLabel);
            
            JLabel titleLabel = new JLabel("Title: " + book.getTitle());
            titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(titleLabel);
            
            JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
            authorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(authorLabel);
            
            JLabel genreLabel = new JLabel("Genre: " + book.getGenre());
            genreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(genreLabel);
            
            JLabel priceLabel = new JLabel("Price: BDT " + book.getPrice());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(priceLabel);
            
            JLabel stockLabel = new JLabel("Current Stock: " + book.getStockLevel());
            stockLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(stockLabel);
            
            // Refresh the panel
            bookInfoPanel.revalidate();
            bookInfoPanel.repaint();
            
            newStockField.setText(String.valueOf(book.getStockLevel()));
        } else {
            // Clear previous content
            bookInfoPanel.removeAll();
            
            JLabel notFoundLabel = new JLabel("No book found with ISBN: " + isbn);
            notFoundLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bookInfoPanel.add(notFoundLabel);
            
            // Refresh the panel
            bookInfoPanel.revalidate();
            bookInfoPanel.repaint();
            
            newStockField.setText("");
        }
    }

    private void updateStock() {
        String isbn = isbnField.getText().trim();
        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ISBN!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int newStock = Integer.parseInt(newStockField.getText().trim());
            if (newStock < 0) {
                JOptionPane.showMessageDialog(this, "Stock level cannot be negative", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = inventorySystem.updateStock(isbn, newStock);
            if (success) {
                JOptionPane.showMessageDialog(this, "Stock updated successfully to " + newStock + " units!", 
                                             "Success", JOptionPane.INFORMATION_MESSAGE);
                findBook(); // Refresh the book info
                
                // Refresh the AllBooksPanel table to show updated stock
                if (allBooksPanel != null) {
                    allBooksPanel.refreshBookTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update stock", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}