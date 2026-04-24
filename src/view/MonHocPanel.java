package view;

import controller.MonHocController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MonHocPanel extends JPanel {

    private JTable            table;
    private DefaultTableModel tableModel;
    private JTextField        txtMaMH, txtTenMH;
    private JComboBox<String> cmbSoTinChi;
    private MonHocController  controller;

    public MonHocPanel() {
        String[] columns = {"Mã MH", "Tên môn học", "Số tín chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        controller = new MonHocController(this, tableModel);
        initUI();
        controller.taiDuLieu();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("QUẢN LÝ MÔN HỌC");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 80, 160));
        add(lblTitle, BorderLayout.NORTH);

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(30, 80, 160));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(190, 215, 255));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) dienVaoForm();
        });

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(260, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            "Thông tin môn học", 0, 0,
            new Font("Arial", Font.BOLD, 13), new Color(30, 80, 160)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaMH     = new JTextField(12);
        txtTenMH    = new JTextField(12);
        cmbSoTinChi = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        cmbSoTinChi.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbSoTinChi.setSelectedItem("3");

        String[] labels = {"Mã MH *", "Tên môn *", "Số tín chỉ *"};
        JComponent[] fields = {txtMaMH, txtTenMH, cmbSoTinChi};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            formPanel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            fields[i].setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(fields[i], gbc);
        }

        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 6, 6));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(10, 8, 8, 8));
        JButton btnThem   = taoNut("Thêm",    new Color(40, 160, 80));
        JButton btnSua    = taoNut("Sửa",     new Color(30, 80, 160));
        JButton btnXoa    = taoNut("Xóa",     new Color(200, 50, 50));
        JButton btnLamMoi = taoNut("Làm mới", new Color(120, 120, 120));
        btnPanel.add(btnThem); btnPanel.add(btnSua);
        btnPanel.add(btnXoa);  btnPanel.add(btnLamMoi);

        gbc.gridx = 0; gbc.gridy = labels.length;
        gbc.gridwidth = 2; gbc.insets = new Insets(10, 0, 0, 0);
        formPanel.add(btnPanel, gbc);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(formPanel, BorderLayout.EAST);

        btnThem.addActionListener(e -> {
            if (controller.them(txtMaMH.getText().trim(), txtTenMH.getText().trim(), cmbSoTinChi.getSelectedItem().toString()))
                lamMoi();
        });
        btnSua.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Chọn môn học cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.sua(txtMaMH.getText().trim(), txtTenMH.getText().trim(), cmbSoTinChi.getSelectedItem().toString()))
                lamMoi();
        });
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn môn học cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.xoa(tableModel.getValueAt(row, 0).toString())) lamMoi();
        });
        btnLamMoi.addActionListener(e -> lamMoi());
    }

    private void dienVaoForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtMaMH.setText(tableModel.getValueAt(row, 0).toString()); txtMaMH.setEditable(false);
        txtTenMH.setText(tableModel.getValueAt(row, 1).toString());
        cmbSoTinChi.setSelectedItem(tableModel.getValueAt(row, 2).toString());
    }

    private void lamMoi() {
        txtMaMH.setText(""); txtMaMH.setEditable(true);
        txtTenMH.setText(""); cmbSoTinChi.setSelectedItem("3");
        table.clearSelection();
    }

    private JButton taoNut(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color); btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}