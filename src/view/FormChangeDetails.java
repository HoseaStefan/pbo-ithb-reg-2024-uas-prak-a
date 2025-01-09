package view;

import javax.swing.*;
import javax.swing.border.Border;
import model.CurrentUser;
import model.Customer;
import controller.Controller;
import java.awt.*;
import java.sql.*;
import java.io.File;

public class FormChangeDetails {
    public FormChangeDetails(String transactionId) {
        showFormShipmentDetails(transactionId);
    }

    public void showFormShipmentDetails(String transactionId) {
        Customer customer = CurrentUser.getInstance().getUser();

        JFrame frame = new JFrame("Shipment Details");
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

        JComboBox<String> statusCombo = new JComboBox<>();
        statusCombo.setBounds(50, 120, 250, 50);
        statusCombo.setBorder(BorderFactory.createTitledBorder("Status"));
        String[] statuses = {"pending", "transit", "delivered"};
        for (String status : statuses) {
            statusCombo.addItem(status);
        }
        frame.add(statusCombo);

        JTextField inputCurrentPosition = new JTextField();
        inputCurrentPosition.setBounds(50, 240, 250, 50);
        inputCurrentPosition.setBorder(BorderFactory.createTitledBorder("Current Position"));
        frame.add(inputCurrentPosition);

        JButton chooseFileButton = new JButton("Choose Evidence Photo");
        chooseFileButton.setBounds(350, 120, 250, 50);
        frame.add(chooseFileButton);

        JLabel evidenceLabel = new JLabel("Select a photo as evidence for shipment");
        evidenceLabel.setBounds(350, 180, 250, 30);
        evidenceLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        frame.add(evidenceLabel);

        JTextField inputUpdatedBy = new JTextField();
        inputUpdatedBy.setBounds(350, 240, 250, 50);
        inputUpdatedBy.setBorder(BorderFactory.createTitledBorder("Updated By"));
        frame.add(inputUpdatedBy);

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

        String[] filePath = new String[1];

        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Evidence Photo");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePath[0] = selectedFile.getAbsolutePath();
            }
        });

        saveButton.addActionListener(e -> {
            String status = (String) statusCombo.getSelectedItem();
            String currentPosition = inputCurrentPosition.getText();
            String evidence = filePath[0];
            String updatedBy = inputUpdatedBy.getText();

            if (currentPosition.isEmpty() || evidence == null || evidence.isEmpty() || updatedBy.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields and choose an evidence photo!");
                return;
            }

            try {
                int transactionIdInt = Integer.parseInt(transactionId);

                Controller.addShipmentDetails(transactionIdInt, status, currentPosition, evidence, updatedBy);

                JOptionPane.showMessageDialog(frame, "Shipment details saved!");
                frame.dispose();
                new GantiDetailPengiriman();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Transaction ID format!");
            }
        });

        buttonCancel.addActionListener(e -> {
            frame.dispose();
            new KurirMenu();
        });

        frame.setVisible(true);
    }
}
