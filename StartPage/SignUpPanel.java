package StartPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Dashboard.MainFrame;
import Extras.*;

public class SignUpPanel extends JPanel implements MouseListener {
    private RoundedButton registerBtn, backBtn;
    private RoundedTextField userFld;
    private RoundedPasswordField passFld;
    private ImageIcon backgroundImage = new ImageIcon("./assets/signUpPageBg.png");
    private Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
    private ImageIcon scaledBackground = new ImageIcon(scaledBackgroundImage);

    public SignUpPanel(MainFrame parent) {
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Red Hat Display", Font.PLAIN, 25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.PLAIN, 18);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);

        JLabel userLbl = new JLabel("Username");
        userLbl.setBounds(689, 222, 300, 40);
        userLbl.setFont(labelFont);
        userLbl.setForeground(fieldForeground);
        add(userLbl);

        userFld = new RoundedTextField(30);
        userFld.setBounds(689, 268, 430, 50);
        userFld.setFont(fieldFont);
        userFld.setBackground(fieldBackground);
        add(userFld);

        JLabel passLbl = new JLabel("Password");
        passLbl.setBounds(689, 338, 300, 40);
        passLbl.setFont(labelFont);
        passLbl.setForeground(fieldForeground);
        add(passLbl);

        passFld = new RoundedPasswordField(30);
        passFld.setBounds(689, 378, 430, 50);
        passFld.setFont(fieldFont);
        passFld.setBackground(fieldBackground);
        add(passFld);

        registerBtn = new RoundedButton("Register", 30);
        registerBtn.setBounds(689, 448, 205, 50);
        registerBtn.setFont(buttonFont);
        registerBtn.setBackground(fieldBackground);
        registerBtn.setForeground(fieldForeground);
        registerBtn.setHoverBackgroundColor(new Color(140, 169, 157));
        registerBtn.addMouseListener(this);
        add(registerBtn);

        backBtn = new RoundedButton("Back", 30);
        backBtn.setBounds(914, 448, 205, 50);
        backBtn.setFont(buttonFont);
        backBtn.setBackground(fieldBackground);
        backBtn.setForeground(fieldForeground);
        backBtn.setHoverBackgroundColor(new Color(140, 169, 157));
        backBtn.addMouseListener(this);
        add(backBtn);

        JLabel bgImg = new JLabel(scaledBackground);
        bgImg.setBounds(0, 0, 1280, 720);
        add(bgImg);

        // Register button action
        registerBtn.addActionListener(e -> {
            String username = userFld.getText().trim();
            String password = new String(passFld.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both username and password.");
            } else if (AccountManager.isAccountExists(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            } else {
                if (AccountManager.signUp(username, password)) {
                    JOptionPane.showMessageDialog(this, "Account registered successfully!");
                    parent.showSignIn();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register account.");
                }
            }
        });

        // Back button action
        backBtn.addActionListener(e -> parent.showSignIn());
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
