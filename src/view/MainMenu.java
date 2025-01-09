package view;

import javax.swing.*;

public class MainMenu {

    public MainMenu() {
        showMainMenu();
    }

    public void showMainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(100, 100, 200, 30);
        frame.add(buttonLogin);

        buttonLogin.addActionListener(e -> {
            frame.dispose();
            // new FormLogin();
        });

        
        frame.setVisible(true);
    }
}