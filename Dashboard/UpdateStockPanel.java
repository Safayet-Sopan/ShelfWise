package Dashboard;

import javax.swing.*;
import java.awt.*;

public class UpdateStockPanel extends JPanel {
    private JTextField isbnField, newStockField;
    private JPanel bookDetailsPanel;
    private JPanel bookInfoPanel; // Changed from JLabel to JPanel
    private InventorySystem inventorySystem;
    private AllBooksPanel allBooksPanel;

    public UpdateStockPanel(InventorySystem inventorySystem, AllBooksPanel allBooksPanel) {
        this.inventorySystem = inventorySystem;
        this.allBooksPanel = allBooksPanel;
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Title
        JLabel titleLabel = new JLabel("Update Stock");
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

        // Book details area (light green)
        bookDetailsPanel = new JPanel();
        bookDetailsPanel.setLayout(null);
        bookDetailsPanel.setBackground(new Color(220, 255, 220));
        bookDetailsPanel.setBounds(50, 200, 800, 200);
        bookDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Create a panel to hold book info components instead of using HTML
        bookInfoPanel = new JPanel();
        bookInfoPanel.setLayout(new BoxLayout(bookInfoPanel, BoxLayout.Y_AXIS));
        bookInfoPanel.setBounds(20, 20, 760, 160);
        bookInfoPanel.setBackground(new Color(220, 255, 220));
        bookDetailsPanel.add(bookInfoPanel);
        add(bookDetailsPanel);

        // New stock level
        JLabel stockLabel = new JLabel("New Stock Level");
        stockLabel.setBounds(50, 430, 200, 30);
        stockLabel.setFont(labelFont);
        add(stockLabel);

        newStockField = new JTextField();
        newStockField.setBounds(50, 470, 400, 40);
        newStockField.setFont(fieldFont);
        add(newStockField);

        JButton updateBtn = new JButton("Update Stock");
        updateBtn.setBounds(470, 470, 140, 40);
        updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        updateBtn.setBackground(new Color(40, 167, 69));
        updateBtn.setForeground(Color.lightGray);
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
            
            JLabel priceLabel = new JLabel("Price: $" + book.getPrice());
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