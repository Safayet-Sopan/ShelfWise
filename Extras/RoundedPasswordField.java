package Extras;
import javax.swing.*;
import java.awt.*;

public class RoundedPasswordField extends JPasswordField {
    private int radius;

    public RoundedPasswordField(int radius) {
        this.radius = radius;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2.dispose();
    }

    protected void paintBorder(Graphics g) {
        // No border drawing
    }
}