package StartPage;
import javax.swing.*;
import Dashboard.MainFrame;
import java.awt.*;
import java.awt.event.*;

public class SignUpPanel extends JPanel implements MouseListener {
    private MainFrame parent;
    private JButton signUpBtn, backBtn;
    private JTextField userFld;
    private JPasswordField passFld;
    private ImageIcon backgroundImage = new ImageIcon("signInPageBg.png");
    private Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
    private ImageIcon scaledBackground = new ImageIcon(scaledBackgroundImage);

    public SignUpPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Red Hat Display", Font.BOLD, 25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.BOLD, 15);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);

        JLabel userLbl = new JLabel("Username");
        userLbl.setBounds(689, 222, 300, 40);
        userLbl.setFont(labelFont);
        userLbl.setForeground(fieldForeground);
        add(userLbl);

        userFld = new JTextField();
        userFld.setBounds(689, 268, 430, 50);
        userFld.setFont(fieldFont);
        userFld.setBackground(fieldBackground);
        add(userFld);

        JLabel passLbl = new JLabel("Password");
        passLbl.setBounds(689, 338, 300, 40);
        passLbl.setFont(labelFont);
        passLbl.setForeground(fieldForeground);
        add(passLbl);

        passFld = new JPasswordField();
        passFld.setBounds(689, 378, 430, 50);
        passFld.setFont(fieldFont);
        passFld.setBackground(fieldBackground);
        add(passFld);

        signUpBtn = new JButton("Sign Up");
        signUpBtn.setBounds(689, 448, 205, 50);
        signUpBtn.setFont(buttonFont);
        signUpBtn.setBackground(fieldBackground);
        signUpBtn.setForeground(fieldForeground);
        signUpBtn.setFocusPainted(false);
        signUpBtn.setOpaque(true);
        signUpBtn.setContentAreaFilled(true);
        signUpBtn.setBorderPainted(false);
        signUpBtn.addMouseListener(this);
        add(signUpBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(914, 448, 205, 50);
        backBtn.setFont(buttonFont);
        backBtn.setBackground(fieldBackground);
        backBtn.setForeground(fieldForeground);
        backBtn.setFocusPainted(false);
        backBtn.setOpaque(true);
        backBtn.setContentAreaFilled(true);
        backBtn.setBorderPainted(false);
        backBtn.addMouseListener(this);
        add(backBtn);

        JLabel bgImg = new JLabel(scaledBackground);
        bgImg.setBounds(0, 0, 1280, 720);
        add(bgImg);

        // Add action listeners
        signUpBtn.addActionListener(e -> handleSignUp());
        backBtn.addActionListener(e -> parent.showSignIn());
    }

    private void handleSignUp() {
        String username = userFld.getText().trim();
        String password = new String(passFld.getPassword());

        // Check if both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } else {
            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Sign up successful!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields after successful signup
            userFld.setText("");
            passFld.setText("");
        }
    }

    public void mouseEntered(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        b.setBackground(new Color(140, 169, 157));
    }

    public void mouseExited(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        b.setBackground(new Color(205, 247, 229));
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}