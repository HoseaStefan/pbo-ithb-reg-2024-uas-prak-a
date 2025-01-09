package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import controller.Controller;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import javax.swing.ImageIcon;

public class DetailHistory {
    public DetailHistory(String transactionId) {
        showDetailHistory(transactionId);
    }

    public void showDetailHistory(String transactionId) {

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonBackground = new Color(64, 64, 64);
        Color buttonForeground = new Color(255, 255, 255);
        Border buttonBorder = BorderFactory.createLineBorder(new Color(128, 128, 128), 2);

        JFrame frame = new JFrame("Detail History Paket");
        frame.setUndecorated(true);
        frame.setBounds(0, 0, 800, 700);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 800, 700, 30, 30));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(244, 255, 213));
        panel.setBounds(0, 0, 800, 700);

        JLabel title = new JLabel("Detail History Paket");
        title.setBounds(280, 20, 500, 50);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(64, 64, 64));  
        panel.add(title);

        String[] columnNames = { "Status", "Evidence", "Date", "Updated By" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(80);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        table.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                if (value != null) {
                    label.setIcon(new ImageIcon((String) value)); 
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return label;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(64, 64, 64));  
        header.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 760, 500);
        panel.add(scrollPane);

        Controller.loadDetailHistoryData(tableModel, transactionId);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 600, 700, 50);
        backButton.setFont(buttonFont);
        backButton.setBackground(buttonBackground);
        backButton.setForeground(buttonForeground);
        backButton.setBorder(buttonBorder);
        backButton.setFocusPainted(false);

        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuHistory();  
        });

        panel.add(backButton);

        JLabel transactionIdLabel = new JLabel("Transaction ID: " + transactionId);
        transactionIdLabel.setBounds(20, 20, 500, 50);
        transactionIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(transactionIdLabel);

        frame.add(panel);
        frame.setVisible(true);
    }
}
