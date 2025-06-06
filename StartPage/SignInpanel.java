package StartPage;
import javax.swing.*;
import Dashboard.MainFrame;
import java.awt.*;
import java.awt.event.*;

public class SignInpanel extends JPanel implements MouseListener {
    private MainFrame parent;
    private JButton lgnBtn, rgstrBtn;
    private ImageIcon backgroundImage = new ImageIcon("./assets/signInPageBg.png");
    private Image scaledBackgroundImage = backgroundImage.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
    private ImageIcon scaledBackground = new ImageIcon(scaledBackgroundImage);

    public SignInpanel(MainFrame parent) {
        this.parent = parent;
        setLayout(null);
        setBackground(Color.WHITE);

        Font labelFont = new Font("Red Hat Display", Font.BOLD,25);
        Font fieldFont = new Font("Red Hat Display", Font.PLAIN, 15);
        Font buttonFont = new Font("Red Hat Display", Font.BOLD, 15);
        Color fieldBackground = new Color(205, 247, 229);
        Color fieldForeground = new Color(26, 46, 53);

        JLabel userLbl = new JLabel("Username");
        userLbl.setBounds(689, 222, 300, 40);
        userLbl.setFont(labelFont);
        userLbl.setForeground(fieldForeground);
        add(userLbl);

        JTextField userFld = new JTextField();
        userFld.setBounds(689, 268, 430, 50);
        userFld.setFont(fieldFont);
        userFld.setBackground(fieldBackground);
        add(userFld);

        JLabel passLbl = new JLabel("Password");
        passLbl.setBounds(689, 338, 300, 40);
        passLbl.setFont(labelFont);
        passLbl.setForeground(fieldForeground);
        add(passLbl);

        JPasswordField passFld = new JPasswordField();
        passFld.setBounds(689, 378, 430, 50);
        passFld.setFont(fieldFont);
        passFld.setBackground(fieldBackground);
        add(passFld);

        lgnBtn = new JButton("Sign In");
        lgnBtn.setBounds(689, 448, 205, 50);
        lgnBtn.setFont(buttonFont);
        lgnBtn.setBackground(fieldBackground);
        lgnBtn.setForeground(fieldForeground);
        lgnBtn.setFocusPainted(false);
        lgnBtn.setOpaque(true);
        lgnBtn.setContentAreaFilled(true);
        lgnBtn.setBorderPainted(false);
        lgnBtn.addMouseListener(this);
        add(lgnBtn);

        rgstrBtn = new JButton("Sign Up");
        rgstrBtn.setBounds(914, 448, 205, 50);
        rgstrBtn.setFont(buttonFont);
        rgstrBtn.setBackground(fieldBackground);
        rgstrBtn.setForeground(fieldForeground);
        rgstrBtn.setFocusPainted(false);
        rgstrBtn.setOpaque(true);
        rgstrBtn.setContentAreaFilled(true);
        rgstrBtn.setBorderPainted(false);
        rgstrBtn.addMouseListener(this);
        add(rgstrBtn);


        JLabel bgImg = new JLabel(scaledBackground);
        bgImg.setBounds(0, 0, 1280, 720);
        add(bgImg);


        lgnBtn.addActionListener(e -> parent.initMainUI());
        rgstrBtn.addActionListener(e -> parent.showSignUp());
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