package Dashboard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SidebarPanel extends JPanel implements MouseListener {

    private JButton accountBtn, addBookBtn, updateBookBtn, sellBookBtn, updateStockBtn, salesReportBtn, showBooksBtn, exitBtn;
    private Color normalColor = new Color(26, 46, 53);  // Sidebar dark greenish
    private Color hoverColor = new Color(50, 87, 100); // 'All Books' hover color

    private ImageIcon bgImage = new ImageIcon("assets/DashboardBg.png");
    private Image scaledBackgroundImage = bgImage.getImage().getScaledInstance(310, 720, Image.SCALE_SMOOTH);
    private ImageIcon scaledBackground = new ImageIcon(scaledBackgroundImage);

    public SidebarPanel() {
        setLayout(null);
        setBackground(normalColor);

        Font buttonFont = new Font("Red Hat Display", Font.BOLD, 25);
        
        // Account icon button
        accountBtn = new JButton(new ImageIcon("assets/accountIcon.png"));
        accountBtn.setBounds(35, 42, 30, 30);
        accountBtn.setContentAreaFilled(false);
        accountBtn.setBorderPainted(false);
        add(accountBtn);
        
        // Shelf-Wise logo label
        JLabel logoLbl = new JLabel(new ImageIcon("assets/wideLogo.png"));
        logoLbl.setBounds(100, 45, 166, 25);
        add(logoLbl);
        
        // All Books Button
        showBooksBtn = new JButton("All Books");
        showBooksBtn.setBounds(0, 215, 310, 50);
        showBooksBtn.setFont(buttonFont);
        showBooksBtn.setBackground(normalColor);
        showBooksBtn.setForeground(Color.WHITE);
        showBooksBtn.setFocusPainted(false);
        showBooksBtn.setOpaque(true);
        showBooksBtn.setContentAreaFilled(true);
        showBooksBtn.setBorderPainted(false);
        showBooksBtn.addMouseListener(this);

        // Add Books Button
        addBookBtn = new JButton("Add Books");
        addBookBtn.setBounds(0, 265, 310, 50);
        addBookBtn.setFont(buttonFont);
        addBookBtn.setBackground(normalColor);
        addBookBtn.setForeground(Color.WHITE);
        addBookBtn.setFocusPainted(false);
        addBookBtn.setOpaque(true);
        addBookBtn.setContentAreaFilled(true);
        addBookBtn.setBorderPainted(false);
        addBookBtn.addMouseListener(this);

        // Update Books Button
        updateBookBtn = new JButton("Update Books");
        updateBookBtn.setBounds(0, 315, 310, 50);
        updateBookBtn.setFont(buttonFont);
        updateBookBtn.setBackground(normalColor);
        updateBookBtn.setForeground(Color.WHITE);
        updateBookBtn.setFocusPainted(false);
        updateBookBtn.setOpaque(true);
        updateBookBtn.setContentAreaFilled(true);
        updateBookBtn.setBorderPainted(false);
        updateBookBtn.addMouseListener(this);

        // Sell Book Button
        sellBookBtn = new JButton("Sell Book");
        sellBookBtn.setBounds(0, 365, 310, 50);
        sellBookBtn.setFont(buttonFont);
        sellBookBtn.setBackground(normalColor);
        sellBookBtn.setForeground(Color.WHITE);
        sellBookBtn.setFocusPainted(false);
        sellBookBtn.setOpaque(true);
        sellBookBtn.setContentAreaFilled(true);
        sellBookBtn.setBorderPainted(false);
        sellBookBtn.addMouseListener(this);

        // Sales Report Button
        salesReportBtn = new JButton("Sales Report");
        salesReportBtn.setBounds(0, 415, 310, 50);
        salesReportBtn.setFont(buttonFont);
        salesReportBtn.setBackground(normalColor);
        salesReportBtn.setForeground(Color.WHITE);
        salesReportBtn.setFocusPainted(false);
        salesReportBtn.setOpaque(true);
        salesReportBtn.setContentAreaFilled(true);
        salesReportBtn.setBorderPainted(false);
        salesReportBtn.addMouseListener(this);
        
        add(showBooksBtn);
        add(addBookBtn);
        add(updateBookBtn);
        add(sellBookBtn);
        add(salesReportBtn);
        
        // Exit icon button
        exitBtn = new JButton(new ImageIcon("assets/exitIcon.png"));
        exitBtn.setBounds(30, 644, 30, 30);
        exitBtn.setContentAreaFilled(false);
        exitBtn.setBorderPainted(false);
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
        
        
        JLabel bgImg = new JLabel(scaledBackground);
        bgImg.setBounds(0, 0, 310, 720);
        add(bgImg);
    }

    // Hover effects

    public void mouseEntered(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        b.setBackground(hoverColor);
    }


    public void mouseExited(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        b.setBackground(normalColor);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    // Getters
    public JButton getAccountBtn() { return accountBtn; }
    public JButton getAddBookBtn() { return addBookBtn; }
    public JButton getUpdateBookBtn() { return updateBookBtn; }
    public JButton getUpdateStockBtn() { return updateStockBtn; }
    public JButton getSellBookBtn() { return sellBookBtn; }
    public JButton getSalesReportBtn() { return salesReportBtn; }
    public JButton getShowBooksBtn() { return showBooksBtn; }
    public JButton getExitBtn() { return exitBtn; }
}