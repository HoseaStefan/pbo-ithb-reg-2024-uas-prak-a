package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Customer;
import controller.*;

public class FormLogin {
    public FormLogin(){
        showFormLogin();
    }

    public void showFormLogin(){
        JFrame frame = new JFrame("Login");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setBackground(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField inputNomorTelepon = new JTextField();
        inputNomorTelepon.setBounds(100, 100, 200, 50);
        inputNomorTelepon.setBorder(BorderFactory.createTitledBorder("Nomor Telepon"));
        inputNomorTelepon.setBackground(null);
        frame.add(inputNomorTelepon);

        JTextField inputPassword = new JTextField();
        inputPassword.setBounds(100, 160, 200, 50);
        inputPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        inputPassword.setBackground(null);
        frame.add(inputPassword);

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setBounds(100, 240, 200, 30);
        frame.add(buttonLogin);

        buttonLogin.addActionListener(e -> {
            String nomorTelepon = inputNomorTelepon.getText();
            String password = inputPassword.getText();

            if (!nomorTelepon.isEmpty() && !password.isEmpty()) {
                Customer verifying = Controller.verifyUser(nomorTelepon, password);
                if (verifying == null) {
                    JOptionPane.showMessageDialog(frame, "Salah username/password!");
                } else {
                    frame.dispose();
                    new CustomerMenu();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu kawan!");
            }
        });
        
        frame.setVisible(true);
    }
}