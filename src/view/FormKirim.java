package view;

import javax.swing.*;
import javax.swing.border.Border;

import model.CurrentUser;
import model.Customer;
import controller.Controller;

import java.awt.*;

public class FormKirim {
    public FormKirim(){
        showFormKirim();
    }

    public void showFormKirim() {
        Customer customer = CurrentUser.getInstance().getUser();

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

        JTextField inputName = new JTextField();
        inputName.setBounds(50, 120, 250, 50);
        inputName.setBorder(BorderFactory.createTitledBorder("Recipient Name"));
        frame.add(inputName);

        JTextField inputAddress = new JTextField();
        inputAddress.setBounds(50, 180, 250, 50);
        inputAddress.setBorder(BorderFactory.createTitledBorder("Recipient Address"));
        frame.add(inputAddress);

        JTextField inputPhone = new JTextField();
        inputPhone.setBounds(50, 240, 250, 50);
        inputPhone.setBorder(BorderFactory.createTitledBorder("Recipient Phone"));
        frame.add(inputPhone);

        JTextField inputWeight = new JTextField();
        inputWeight.setBounds(350, 120, 250, 50);
        inputWeight.setBorder(BorderFactory.createTitledBorder("Package Weight (kg)"));
        frame.add(inputWeight);

        JComboBox<String> packageTypeCombo = new JComboBox<>();
        packageTypeCombo.setBounds(350, 180, 250, 50);
        packageTypeCombo.setBorder(BorderFactory.createTitledBorder("Package Type"));

        String[] packageTypes = {"Regular", "Fast", "Super Fast"};
        for (String type : packageTypes) {
            packageTypeCombo.addItem(type);
        }
        frame.add(packageTypeCombo);

        JLabel costLabel = new JLabel("Cost per kg: 5000");
        costLabel.setBounds(350, 240, 250, 30);
        costLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(costLabel);

        packageTypeCombo.addActionListener(e -> {
            String selectedType = (String) packageTypeCombo.getSelectedItem();
            String costText;
            switch (selectedType) {
                case "Fast":
                    costText = "10000";
                    break;
                case "Super Fast":
                    costText = "20000";
                    break;
                default:
                    costText = "5000";
            }
            costLabel.setText("Cost per kg: " + costText);
        });

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(64, 64, 64);
        Color buttonForeground = new Color(255, 255, 255);
        Border buttonBorder = BorderFactory.createLineBorder(new Color(128, 128, 128), 2);

        JButton saveButton = new JButton("Save!");
        saveButton.setBounds(120, 320, 430, 40);
        saveButton.setFont(buttonFont);
        saveButton.setBackground(buttonBackground);
        saveButton.setForeground(buttonForeground);
        saveButton.setBorder(buttonBorder);
        frame.add(saveButton);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(120, 380, 430, 40);
        buttonCancel.setFont(buttonFont);
        buttonCancel.setBackground(buttonBackground);
        buttonCancel.setForeground(buttonForeground);
        buttonCancel.setBorder(buttonBorder);
        frame.add(buttonCancel);

        saveButton.addActionListener(e -> {
            String name = inputName.getText();
            String address = inputAddress.getText();
            String phone = inputPhone.getText();
            String weightText = inputWeight.getText();
            String packageType = (String) packageTypeCombo.getSelectedItem();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || weightText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Isi dulu semua!");
                return;
            }

            double weight;
            try {
                weight = Double.parseDouble(weightText);
                if (weight <= 0) {
                    JOptionPane.showMessageDialog(frame, "Weight harus lebih besar dari 0!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Format berat invalid!");
                return;
            }

            int totalCost = Controller.createTransaksi(customer, name, address, phone, weightText, packageType);

            if (totalCost > 0) {
                JOptionPane.showMessageDialog(frame, "Transaksi saved!\nTotal Cost: " + totalCost);
                frame.dispose();
                new MenuHistory();
            } else {
                JOptionPane.showMessageDialog(frame, "Gagal save transaksi! Coba Lagi");
            }

        });

        buttonCancel.addActionListener(e -> {
            frame.dispose();
            new CustomerMenu();
        });

        frame.setVisible(true);
    }

}
