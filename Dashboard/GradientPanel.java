package Dashboard;
import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color start, end;

    public GradientPanel(Color start, Color end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth(), h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, start, 0, h, end);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }
}