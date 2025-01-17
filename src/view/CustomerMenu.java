package view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.CurrentUser;
import model.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerMenu {
    public CustomerMenu(){
        showCustomerMenu();
    }

    public void showCustomerMenu(){
        CurrentUser currentUser = CurrentUser.getInstance();
        Customer customerLogin = currentUser.getUser();

        JFrame frame = new JFrame("Menu");
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

        JLabel labelWelcome = new JLabel("Welcome, " + customerLogin.getName());
        labelWelcome.setBounds(20, 110, 200, 50);
        labelWelcome.setFont(buttonFont);
        frame.add(labelWelcome);

        JButton buttonKirim = new JButton("Lakukan Pengiriman!");
        buttonKirim.setBounds(245, 200, 200, 40);
        buttonKirim.setFont(buttonFont);
        buttonKirim.setBackground(buttonBackground);
        buttonKirim.setForeground(buttonForeground);
        buttonKirim.setBorder(buttonBorder);
        frame.add(buttonKirim);

        JButton buttonHistory = new JButton("Cek History!");
        buttonHistory.setBounds(245, 260, 200, 40);
        buttonHistory.setFont(buttonFont);
        buttonHistory.setBackground(buttonBackground);
        buttonHistory.setForeground(buttonForeground);
        buttonHistory.setBorder(buttonBorder);
        frame.add(buttonHistory);

        buttonKirim.addActionListener(e -> {
            frame.dispose();
            new FormKirim();
        });

        buttonHistory.addActionListener(e -> {
            frame.dispose();
            new MenuHistory();
        });

        JButton buttonLogout = new JButton("Logout");
        buttonLogout.setBounds(245, 340, 200, 40);
        buttonLogout.setFont(buttonFont);
        buttonLogout.setBackground(buttonBackground);
        buttonLogout.setForeground(buttonForeground);
        buttonLogout.setBorder(buttonBorder);
        frame.add(buttonLogout);

        buttonLogout.addActionListener(e -> {
            frame.dispose();
            CurrentUser.getInstance().clearUser();
            new MainMenu();
        });

        frame.setVisible(true);
    }
}
