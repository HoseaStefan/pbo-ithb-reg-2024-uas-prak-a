package view;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.File;

public class MainMenu {

    public MainMenu() {
        showMainMenu();
    }

    public void showMainMenu() {
        JFrame frame = new JFrame("Main Menu");
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(244, 255, 213));

        JLabel label = new JLabel();
        label.setBounds(0, 0, 700, 100);

        java.net.URL imageUrl = getClass().getResource("/sources/Pratama Express.png");
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        } else {
            label.setText("Image not found!");
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }

        frame.add(label);

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(64, 64, 64);
        Color buttonForeground = new Color(255, 255, 255);
        Border buttonBorder = BorderFactory.createLineBorder(new Color(128, 128, 128), 2);

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(220, 200, 250, 40);
        buttonLogin.setFont(buttonFont);
        buttonLogin.setBackground(buttonBackground);
        buttonLogin.setForeground(buttonForeground);
        buttonLogin.setBorder(buttonBorder);
        frame.add(buttonLogin);

        buttonLogin.addActionListener(e -> {
            frame.dispose();
            new FormLogin();
        });

        JButton buttonRegister = new JButton("Register");
        buttonRegister.setBounds(220, 300, 250, 40);
        buttonRegister.setFont(buttonFont);
        buttonRegister.setBackground(buttonBackground);
        buttonRegister.setForeground(buttonForeground);
        buttonRegister.setBorder(buttonBorder);
        frame.add(buttonRegister);

        buttonRegister.addActionListener(e -> {
            frame.dispose();
            new FormRegister();
        });

        frame.setVisible(true);
    }
}