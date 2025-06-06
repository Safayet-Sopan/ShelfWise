package Dashboard;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("📚 Book Store Management Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(50, 50, 600, 40);
        add(titleLabel);

        JLabel welcomeLabel = new JLabel("Welcome to the system!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setBounds(50, 120, 400, 30);
        add(welcomeLabel);
    }
}