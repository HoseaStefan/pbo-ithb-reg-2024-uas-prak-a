package view;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import controller.Controller;

public class FormRegister {
    public FormRegister() {
        showFormRegister();
    }

    public void showFormRegister() {
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
        inputNomorTelepon.setBounds(350, 150, 200, 50);
        inputNomorTelepon.setBorder(BorderFactory.createTitledBorder("Nomor Telepon"));
        frame.add(inputNomorTelepon);

        JTextField inputNama = new JTextField();
        inputNama.setBounds(120, 150, 200, 50);
        inputNama.setBorder(BorderFactory.createTitledBorder("Nama"));
        frame.add(inputNama);

        JTextField inputAlamat = new JTextField();
        inputAlamat.setBounds(120, 220, 200, 50);
        inputAlamat.setBorder(BorderFactory.createTitledBorder("Alamat Email"));
        frame.add(inputAlamat);

        JTextField inputPassword = new JTextField();
        inputPassword.setBounds(350, 220, 200, 50);
        inputPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        frame.add(inputPassword);

        JButton buttonRegister = new JButton("Register");
        buttonRegister.setBounds(120, 320, 430, 40);
        buttonRegister.setFont(buttonFont);
        buttonRegister.setBackground(buttonBackground);
        buttonRegister.setForeground(buttonForeground);
        buttonRegister.setBorder(buttonBorder);
        frame.add(buttonRegister);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(120, 380, 430, 40);
        buttonCancel.setFont(buttonFont);
        buttonCancel.setBackground(buttonBackground);
        buttonCancel.setForeground(buttonForeground);
        buttonCancel.setBorder(buttonBorder);
        frame.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        buttonRegister.addActionListener(e -> {
            String nomorTelepon = inputNomorTelepon.getText();
            String nama = inputNama.getText();
            String alamat = inputAlamat.getText();
            String password = inputPassword.getText();

            if (!nomorTelepon.isEmpty() && !password.isEmpty() && !nama.isEmpty() && !alamat.isEmpty()) {
                boolean registering = Controller.registerUser(nomorTelepon, alamat, nama, password);
                if (!registering) {
                    JOptionPane.showMessageDialog(frame, "Nomor telepon dan email address sudah terdaftar!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Data anda berhasil tersimpan!");
                    frame.dispose();
                    new MainMenu();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu kawan!");
            }
        });

        frame.setVisible(true);
    }
}
