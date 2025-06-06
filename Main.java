// import javax.swing.*;

import Dashboard.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Direct launch without explicitly using invokeLater
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}