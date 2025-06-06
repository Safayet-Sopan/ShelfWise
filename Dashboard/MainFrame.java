package Dashboard;
import javax.swing.*;
import StartPage.SignInpanel;
import StartPage.SignUpPanel;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cards;

    public MainFrame() {
        super("Shelf Wise");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        cards.add(new SignInpanel(this), "SignIn");
        cards.add(new SignUpPanel(this), "SignUp");

        getContentPane().add(cards);
        cardLayout.show(cards, "SignIn");
    }

    public void initMainUI() {
    cards.removeAll();

    // Split pane for sidebar + content
    JSplitPane split = new JSplitPane();
    split.setDividerLocation(310);
    split.setEnabled(false);

    SidebarPanel sidebar = new SidebarPanel();
    JPanel featureArea = new JPanel(null); // null layout for now, can change later
    featureArea.setBackground(Color.WHITE);

    // Example placeholder text for feature area
    JLabel label = new JLabel("Dashboard Placeholder", SwingConstants.CENTER);
    label.setBounds(100, 100, 300, 30);
    featureArea.add(label);

    split.setLeftComponent(sidebar);
    split.setRightComponent(featureArea);

    getContentPane().removeAll();
    getContentPane().add(split);
    revalidate();
    repaint();
}

    public void showSignUp() { cardLayout.show(cards, "SignUp"); }
    public void showSignIn() { cardLayout.show(cards, "SignIn"); }
}