package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Customer;
import controller.Controller;

public class FormRegister {
    public FormRegister() {
        showFormRegister();
    }

    public static void showFormRegister() {
        JFrame frame = new JFrame("Register");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField inputNomorTelepon = new JTextField();
        inputNomorTelepon.setBounds(100, 70, 200, 50);
        inputNomorTelepon.setBorder(BorderFactory.createTitledBorder("Nomor Telepon"));
        inputNomorTelepon.setBackground(null);
        frame.add(inputNomorTelepon);

        JTextField inputNama = new JTextField();
        inputNama.setBounds(100, 130, 200, 50);
        inputNama.setBorder(BorderFactory.createTitledBorder("Nama"));
        inputNama.setBackground(null);
        frame.add(inputNama);

        JTextField inputAlamat = new JTextField();
        inputAlamat.setBounds(100, 190, 200, 50);
        inputAlamat.setBorder(BorderFactory.createTitledBorder("Alamat Email"));
        inputAlamat.setBackground(null);
        frame.add(inputAlamat);

        JTextField inputPassword = new JTextField();
        inputPassword.setBounds(100, 250, 200, 50);
        inputPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        inputPassword.setBackground(null);
        frame.add(inputPassword);

        JButton buttonLogin = new JButton("Register");
        buttonLogin.setBounds(100, 320, 200, 30);
        frame.add(buttonLogin);

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(100, 360, 200, 30);
        frame.add(buttonCancel);

        buttonCancel.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        buttonLogin.addActionListener(e -> {
            String nomorTelepon = inputNomorTelepon.getText();
            String nama = inputNama.getText();
            String alamat = inputAlamat.getText();
            String password = inputPassword.getText();

            if (!nomorTelepon.isEmpty() && !password.isEmpty() && !nama.isEmpty() && !alamat.isEmpty()) {
                boolean registing = Controller.registerUser(nomorTelepon, alamat, nama, password);
                if (!registing) {
                    JOptionPane.showMessageDialog(frame, "nomor telepon dan email address sudah terdaftar!");
                } else {
                    JOptionPane.showMessageDialog(frame, "data anda berhasil tersimpan!");
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
