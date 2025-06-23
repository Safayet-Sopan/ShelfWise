package Dashboard;

import javax.swing.*;

import Extras.RoundedButton;
import Extras.RoundedTextField;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesReportPanel extends JPanel {
    private RoundedTextField dateField;
    private JPanel reportPanel;
    private JTextArea reportArea;
    private InventorySystem inventorySystem;

    public SalesReportPanel(InventorySystem inventorySystem) {
        
        Font titleFont = new Font("Red Hat Display", Font.PLAIN,35);
        Font labelFont = new Font("Red Hat Display", Font.PLAIN,25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Font reciptFont = new Font("Red Hat Display", Font.PLAIN, 12);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);
        Color hoverColor = new Color(140, 169, 157);
        
        this.inventorySystem = inventorySystem; // Use the shared inventory system
        setLayout(null);
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Sales Report");
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(64, 37, 232, 46);
        add(titleLabel);

        // Date input
        JLabel dateLabel = new JLabel("Select Date (YYYY-MM-DDD)");
        dateLabel.setBounds(64, 99, 372, 40);
        dateLabel.setFont(labelFont);
        add(dateLabel);

        // Set default date to current date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        dateField = new RoundedTextField(currentDate, 30);
        dateField.setBounds(64, 153, 372, 50);
        dateField.setBackground(fieldBackground);
        dateField.setFont(fieldFont);
        add(dateField);

        RoundedButton generateBtn = new RoundedButton("Generate Report",30);
        generateBtn.setBounds(470, 153, 279, 50);
        generateBtn.setFont(buttonFont);
        generateBtn.setBackground(fieldBackground);
        generateBtn.setForeground(fieldForeground);
        generateBtn.setHoverBackgroundColor(hoverColor);
        generateBtn.addActionListener(e -> generateReport());
        add(generateBtn);

        // Clear report button
        RoundedButton clearBtn = new RoundedButton("Clear", 30);
        clearBtn.setBounds(779, 153, 135, 50);
        clearBtn.setFont(buttonFont);
        clearBtn.setBackground(fieldBackground);
        clearBtn.setForeground(fieldForeground);
        clearBtn.setHoverBackgroundColor(hoverColor);
        clearBtn.addActionListener(e -> clearReport());
        add(clearBtn);

        // Report area
        reportPanel = new JPanel();
        reportPanel.setLayout(new BorderLayout());
        reportPanel.setBackground(fieldBackground);
        reportPanel.setBounds(64, 232, 852, 400);
        reportPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        reportArea = new JTextArea();
        reportArea.setFont(reciptFont);
        reportArea.setBackground(fieldBackground);
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
                
                report.append(String.format("%-30s %-15s %-10d BDT%-11.2f BDT%-14.2f\n",
                                          title,
                                          sale.getBook().getIsbn(),
                                          sale.getQuantitySold(),
                                          sale.getBook().getPrice(),
                                          sale.getTotalAmount()));
            }
            
            report.append("\n--------------------------------------------------------------------------------\n");
            report.append(String.format("TOTAL SALES AMOUNT: BDT%.2f\n", totalSales));
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