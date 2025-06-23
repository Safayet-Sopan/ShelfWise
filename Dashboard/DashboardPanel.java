package Dashboard;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        ImageIcon backgroundImage = new ImageIcon("./assets/dashboardWelcome.png");
        Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(970, 720, Image.SCALE_SMOOTH);
        ImageIcon scaledBackground = new ImageIcon(scaledBackgroundImage);

        JLabel bgImg = new JLabel(scaledBackground);
        bgImg.setBounds(0, 0, 970, 720);
        add(bgImg);
    }
}