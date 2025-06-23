package StartPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Dashboard.MainFrame;
import Extras.*;

public class SignInpanel extends JPanel implements MouseListener {
    private RoundedButton lgnBtn, rgstrBtn;
    private ImageIcon backgroundImage = new ImageIcon("./assets/signInPageBg.png");
    private Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
    private ImageIcon scaledBackground = new ImageIcon(scaledBackgroundImage);

    public SignInpanel(MainFrame parent) {
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Red Hat Display", Font.PLAIN,25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);

        JLabel userLbl = new JLabel("Username");
        userLbl.setBounds(689, 222, 300, 40);
        userLbl.setFont(labelFont);
        userLbl.setForeground(fieldForeground);
        add(userLbl);

        RoundedTextField userFld = new RoundedTextField(30);
        userFld.setBounds(689, 268, 430, 50);
        userFld.setFont(fieldFont);
        userFld.setBackground(fieldBackground);
        add(userFld);

        JLabel passLbl = new JLabel("Password");
        passLbl.setBounds(689, 338, 300, 40);
        passLbl.setFont(labelFont);
        passLbl.setForeground(fieldForeground);
        add(passLbl);

        RoundedPasswordField passFld = new RoundedPasswordField(30);
        passFld.setBounds(689, 378, 430, 50);
        passFld.setFont(fieldFont);
        passFld.setBackground(fieldBackground);
        add(passFld);

        lgnBtn = new RoundedButton("Sign In", 30);
        lgnBtn.setBounds(689, 448, 205, 50);
        lgnBtn.setFont(buttonFont);
        lgnBtn.setBackground(fieldBackground);
        lgnBtn.setForeground(fieldForeground);
        lgnBtn.setHoverBackgroundColor(new Color(140, 169, 157));
        lgnBtn.addMouseListener(this);
        add(lgnBtn);

        rgstrBtn = new RoundedButton("Sign Up", 30);
        rgstrBtn.setBounds(914, 448, 205, 50);
        rgstrBtn.setFont(buttonFont);
        rgstrBtn.setBackground(fieldBackground);
        rgstrBtn.setForeground(fieldForeground);
        rgstrBtn.setHoverBackgroundColor(new Color(140, 169, 157));
        rgstrBtn.addMouseListener(this);
        add(rgstrBtn);

        JLabel bgImg = new JLabel(scaledBackground);
        bgImg.setBounds(0, 0, 1280, 720);
        add(bgImg);

        /// Sign In action
        lgnBtn.addActionListener(e -> {
            String username = userFld.getText().trim();
            String password = new String(passFld.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both username and password.");
            } else if (AccountManager.login(username, password)) {
                parent.initMainUI();
            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Invalid username or password.");
            }
        });

        // Sign Up navigation action
        rgstrBtn.addActionListener(e -> parent.showSignUp());
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}