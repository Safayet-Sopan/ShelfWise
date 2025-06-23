package Dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Extras.*;

import java.awt.*;

public class AllBooksPanel extends JPanel {
    private RoundedTextField searchField;
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private InventorySystem inventorySystem;

    public AllBooksPanel(InventorySystem inventorySystem) {
        Font titleFont = new Font("Red Hat Display", Font.PLAIN,35);
        Font labelFont = new Font("Red Hat Display", Font.PLAIN,25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);
        Color hoverColor = new Color(140, 169, 157);

        this.inventorySystem = inventorySystem;
        setLayout(null);
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("All Books");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(64, 37, 232, 46);
        add(titleLabel);

        // Search field
        JLabel searchLabel = new JLabel("Search Books:");
        searchLabel.setFont(labelFont);
        searchLabel.setBounds(64, 107, 272, 40);
        add(searchLabel);

        searchField = new RoundedTextField(30);
        searchField.setBounds(242, 102, 430, 50);
        searchField.setBackground(fieldBackground);
        searchField.setFont(fieldFont);
        add(searchField);

        RoundedButton searchBtn = new RoundedButton("Search", 30);
        searchBtn.setBounds(700, 102, 205, 50);
        searchBtn.setFont(buttonFont);
        searchBtn.setBackground(fieldBackground);
        searchBtn.setForeground(fieldForeground);
        searchBtn.setHoverBackgroundColor(hoverColor);
        searchBtn.addActionListener(e -> searchBooks());
        add(searchBtn);

        // Table
        String[] columns = {"ISBN", "Title", "Author", "Genre", "Price", "Stock"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable = new JTable(tableModel);
        booksTable.setFont(fieldFont);
        booksTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setBounds(64, 192, 841, 450);
        add(scrollPane);

        // Load initial data
        refreshBookTable();
    }

    public void refreshBookTable() {
        tableModel.setRowCount(0);
        for (Book book : inventorySystem.getAllBooks()) {
            Object[] row = {
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPrice(),
                book.getStockLevel()
            };
            tableModel.addRow(row);
        }
    }

    private void searchBooks() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        tableModel.setRowCount(0);
        
        if (searchTerm.isEmpty()) {
            refreshBookTable();
            return;
        }

        for (Book book : inventorySystem.getAllBooks()) {
            if (book.getIsbn().toLowerCase().contains(searchTerm) ||
                book.getTitle().toLowerCase().contains(searchTerm) ||
                book.getAuthor().toLowerCase().contains(searchTerm) ||
                book.getGenre().toLowerCase().contains(searchTerm)) {
                
                Object[] row = {
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getPrice(),
                    book.getStockLevel()
                };
                tableModel.addRow(row);
            }
        }
    }
}