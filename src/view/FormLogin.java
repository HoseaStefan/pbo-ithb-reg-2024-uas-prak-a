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

import java.awt.*;

import model.Customer;
import controller.*;

public class FormLogin {
    public FormLogin(){
        showFormLogin();
    }

    public void showFormLogin(){
        JFrame frame = new JFrame("Login");
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

        JTextField inputNomorTelepon = new JTextField();
        inputNomorTelepon.setBounds(245, 140, 200, 50);
        inputNomorTelepon.setBorder(BorderFactory.createTitledBorder("Nomor Telepon"));
        frame.add(inputNomorTelepon);

        JTextField inputPassword = new JTextField();
        inputPassword.setBounds(245, 210, 200, 50);
        inputPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        frame.add(inputPassword);

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(245, 280, 200, 40);
        buttonLogin.setFont(buttonFont);
        buttonLogin.setBackground(buttonBackground);
        buttonLogin.setForeground(buttonForeground);
        buttonLogin.setBorder(buttonBorder);
        frame.add(buttonLogin);

        buttonLogin.addActionListener(e -> {
            String nomorTelepon = inputNomorTelepon.getText();
            String password = inputPassword.getText();

            if (!nomorTelepon.isEmpty() && !password.isEmpty()) {
                Customer verifying = Controller.verifyUser(nomorTelepon, password);
                if (verifying == null) {
                    JOptionPane.showMessageDialog(frame, "Salah username/password!");
                } else {
                    if (verifying.getPhone().equals("111")) {
                        frame.dispose();
                        new KurirMenu();
                        System.out.println("Kurir");
                    } else {
                        frame.dispose();
                        new CustomerMenu();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu kawan!");
            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(245, 340, 200, 40);
        buttonCancel.setFont(buttonFont);
        buttonCancel.setBackground(buttonBackground);
        buttonCancel.setForeground(buttonForeground);
        buttonCancel.setBorder(buttonBorder);
        frame.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        frame.setVisible(true);
    }
}