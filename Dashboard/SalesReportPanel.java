package Dashboard;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesReportPanel extends JPanel {
    private JTextField dateField;
    private JPanel reportPanel;
    private JTextArea reportArea;
    private InventorySystem inventorySystem;

    public SalesReportPanel(InventorySystem inventorySystem) {
        this.inventorySystem = inventorySystem; // Use the shared inventory system
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        // Title
        JLabel titleLabel = new JLabel("Sales Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 30, 300, 40);
        add(titleLabel);

        // Date input
        JLabel dateLabel = new JLabel("Select Date (yyyy-MM-dd)");
        dateLabel.setBounds(50, 100, 200, 30);
        dateLabel.setFont(labelFont);
        add(dateLabel);

        // Set default date to current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        dateField = new JTextField(currentDate);
        dateField.setBounds(50, 140, 400, 40);
        dateField.setFont(fieldFont);
        add(dateField);

        JButton generateBtn = new JButton("Generate Report");
        generateBtn.setBounds(470, 140, 150, 40);
        generateBtn.setFont(new Font("Arial", Font.BOLD, 14));
        generateBtn.setBackground(new Color(70, 130, 180));
        generateBtn.setForeground(Color.lightGray);
        generateBtn.addActionListener(e -> generateReport());
        add(generateBtn);

        // Clear report button
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(640, 140, 100, 40);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        clearBtn.setBackground(new Color(220, 53, 69));
        clearBtn.setForeground(Color.lightGray);
        clearBtn.addActionListener(e -> clearReport());
        add(clearBtn);

        // Report area
        reportPanel = new JPanel();
        reportPanel.setLayout(new BorderLayout());
        reportPanel.setBackground(new Color(220, 255, 220));
        reportPanel.setBounds(50, 200, 800, 400);
        reportPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        reportArea.setBackground(new Color(220, 255, 220));
        reportArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        reportPanel.add(scrollPane, BorderLayout.CENTER);
        add(reportPanel);

        // Generate initial report for current date
        generateReport();
    }

    private void generateReport() {
        String selectedDate = dateField.getText().trim();
        if (!selectedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Please enter date in format yyyy-MM-dd", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Sale> sales = inventorySystem.getSalesForDate(selectedDate);
        double totalSales = inventorySystem.getTotalSalesForDate(selectedDate);

        StringBuilder report = new StringBuilder();
        report.append("========================================================\n");
        report.append("                SALES REPORT FOR ").append(selectedDate).append("\n");
        report.append("========================================================\n\n");

        if (sales.isEmpty()) {
            report.append("No sales recorded for this date.\n\n");
            report.append("Tip: Make sure you have processed sales through the 'Sell Book' panel\n");
            report.append("for the selected date to see them in this report.\n");
        } else {
            report.append(String.format("%-30s %-15s %-10s %-12s %-15s\n", 
                                      "Book Title", "ISBN", "Quantity", "Unit Price", "Total Amount"));
            report.append("--------------------------------------------------------------------------------\n");
            
            for (Sale sale : sales) {
                String title = sale.getBook().getTitle();
                if (title.length() > 28) {
                    title = title.substring(0, 25) + "...";
                }
                
                report.append(String.format("%-30s %-15s %-10d $%-11.2f $%-14.2f\n",
                                          title,
                                          sale.getBook().getIsbn(),
                                          sale.getQuantitySold(),
                                          sale.getBook().getPrice(),
                                          sale.getTotalAmount()));
            }
            
            report.append("\n--------------------------------------------------------------------------------\n");
            report.append(String.format("TOTAL SALES AMOUNT: $%.2f\n", totalSales));
            report.append(String.format("NUMBER OF TRANSACTIONS: %d\n", sales.size()));
            
            // Calculate total books sold
            int totalBooksSold = 0;
            for (Sale sale : sales) {
                totalBooksSold += sale.getQuantitySold();
            }
            report.append(String.format("TOTAL BOOKS SOLD: %d\n", totalBooksSold));
        }

        report.append("\n========================================================\n");
        report.append("Report generated on: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        reportArea.setText(report.toString());
        
        // Scroll to top
        reportArea.setCaretPosition(0);
    }
    
    private void clearReport() {
        reportArea.setText("");
        // Reset date field to current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        dateField.setText(currentDate);
    }
    
    // Method to refresh the report (can be called from other panels)
    public void refreshReport() {
        generateReport();
    }
}