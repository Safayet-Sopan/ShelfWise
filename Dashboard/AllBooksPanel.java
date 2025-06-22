package Dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AllBooksPanel extends JPanel {
    private JTextField searchField;
    private JTable booksTable;
    private DefaultTableModel tableModel;
    private InventorySystem inventorySystem;

    public AllBooksPanel(InventorySystem inventorySystem) {
        this.inventorySystem = inventorySystem;
        setLayout(null);
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("All Books");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 30, 200, 40);
        add(titleLabel);

        // Search field
        JLabel searchLabel = new JLabel("Search Books:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        searchLabel.setBounds(50, 100, 120, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(180, 100, 300, 35);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(500, 100, 100, 35);
        searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        searchBtn.setBackground(new Color(70, 130, 180));
        searchBtn.setForeground(Color.lightGray);
        searchBtn.addActionListener(e -> searchBooks());
        add(searchBtn);

        // Table
        String[] columns = {"ISBN", "Title", "Author", "Genre", "Price ($)", "Stock"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        booksTable = new JTable(tableModel);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 12));
        booksTable.setRowHeight(25);
        booksTable.setBackground(new Color(226, 247, 236));

        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setBounds(50, 160, 800, 400);
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