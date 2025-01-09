package view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.CurrentUser;
import model.Customer;

import java.awt.*;

public class KurirMenu {
    public KurirMenu(){
        showKurirMenu();
    }

    public void showKurirMenu(){
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

        JButton buttonEditDetails = new JButton("Ganti Detail Pengiriman!");
        buttonEditDetails.setBounds(245, 200, 200, 40);
        buttonEditDetails.setFont(buttonFont);
        buttonEditDetails.setBackground(buttonBackground);
        buttonEditDetails.setForeground(buttonForeground);
        buttonEditDetails.setBorder(buttonBorder);
        frame.add(buttonEditDetails);

        buttonEditDetails.addActionListener(e -> {
            frame.dispose();
            new GantiDetailPengiriman();
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
