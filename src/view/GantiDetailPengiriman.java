package view;

import controller.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;

import model.*;

public class GantiDetailPengiriman {

    private JFrame frame;

    public GantiDetailPengiriman() {
        showGantiDetailPengiriman();
    }

    public void showGantiDetailPengiriman() {
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(64, 64, 64);
        Color buttonForeground = new Color(255, 255, 255);
        Border buttonBorder = BorderFactory.createLineBorder(new Color(128, 128, 128), 2);

        frame = new JFrame("Transaction History");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 800, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 800, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(244, 255, 213));
        panel.setBounds(0, 0, 800, 700);

        JLabel title = new JLabel("Transaction History");
        title.setBounds(285, 20, 500, 50);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(64, 64, 64));
        panel.add(title);

        String[] columnNames = { "ID", "Created At", "Updated At", "Actions" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(80);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(table));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(64, 64, 64));
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 760, 500);
        panel.add(scrollPane);

        Controller.loadTransactionDataKurir(tableModel);

        JButton exitButton = new JButton("Back to home");
        exitButton.setBounds(50, 600, 700, 50);
        exitButton.setFont(buttonFont);
        exitButton.setBackground(buttonBackground);
        exitButton.setForeground(buttonForeground);
        exitButton.setBorder(buttonBorder);
        exitButton.setFocusPainted(false);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new KurirMenu();
        });

        panel.add(exitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {

        public ButtonRenderer() {
            setText("Change Details");
            setBackground(new Color(72, 209, 204));
            setForeground(Color.WHITE);
            setFocusPainted(false);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private int row;
        private JTable table;

        public ButtonEditor(JTable table) {
            super(new JCheckBox());
            this.table = table;
            button = new JButton("Change Details");
            button.setOpaque(true);
            button.setBackground(new Color(72, 209, 204));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                fireEditingStopped();
                String transactionId = (String) table.getModel().getValueAt(row, 0);
                System.out.println("Transaction ID: " + transactionId);
                frame.dispose();
                new FormChangeDetails(transactionId);
            });
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }
}
