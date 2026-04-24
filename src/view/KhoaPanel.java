package view;

import controller.KhoaController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KhoaPanel extends JPanel {

    private JTable            table;
    private DefaultTableModel tableModel;
    private JTextField        txtTimKiem;
    private KhoaController    controller;
    private JTextField        txtMaKhoa, txtTenKhoa;
    private JTextArea         txtMoTa;

    public KhoaPanel() {
        String[] columns = {"Mã khoa", "Tên khoa", "Mô tả"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        controller = new KhoaController(this, tableModel);
        initUI();
        controller.taiDuLieu();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("QUẢN LÝ KHOA");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 80, 160));
        add(lblTitle, BorderLayout.NORTH);

        // ===== TABLE =====
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(30, 80, 160));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setSelectionBackground(new Color(190, 215, 255));
        table.setGridColor(new Color(220, 225, 235));
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) dienVaoForm();
        });

        // ===== FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            "Thông tin khoa",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), new Color(30, 80, 160)));
        formPanel.setPreferredSize(new Dimension(280, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtMaKhoa  = new JTextField(14);
        txtTenKhoa = new JTextField(14);
        txtMoTa    = new JTextArea(3, 14);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);

        String[]    labels = {"Mã khoa *", "Tên khoa *", "Mô tả"};
        Component[] inputs = {txtMaKhoa, txtTenKhoa, scrollMoTa};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            inputs[i].setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(inputs[i], gbc);
        }

        // ===== BUTTONS =====
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
        gbc.gridwidth = 2; gbc.weightx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        formPanel.add(btnPanel, gbc);

        // ===== SEARCH =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(245, 247, 250));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(txtTimKiem);
        JButton btnTimKiem = taoNut("Tìm",    new Color(30, 80, 160));
        btnTimKiem.setPreferredSize(new Dimension(80, 30));
        searchPanel.add(btnTimKiem);
        JButton btnTaiLai = taoNut("Tải lại", new Color(100, 100, 100));
        btnTaiLai.setPreferredSize(new Dimension(90, 30));
        searchPanel.add(btnTaiLai);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane,  BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        add(formPanel,   BorderLayout.EAST);

        // ===== EVENTS =====
        btnThem.addActionListener(e -> themKhoa());
        btnSua.addActionListener(e -> suaKhoa());
        btnXoa.addActionListener(e -> xoaKhoa());
        btnLamMoi.addActionListener(e -> lamMoiForm());
        btnTimKiem.addActionListener(e -> controller.timKiem(txtTimKiem.getText().trim()));
        btnTaiLai.addActionListener(e -> controller.taiDuLieu());
        txtTimKiem.addActionListener(e -> controller.timKiem(txtTimKiem.getText().trim()));
    }

    private void themKhoa() {
        if (controller.them(
                txtMaKhoa.getText().trim(),
                txtTenKhoa.getText().trim(),
                txtMoTa.getText().trim()))
            lamMoiForm();
    }

    private void suaKhoa() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (controller.sua(
                txtMaKhoa.getText().trim(),
                txtTenKhoa.getText().trim(),
                txtMoTa.getText().trim()))
            lamMoiForm();
    }

    private void xoaKhoa() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (controller.xoa(
                tableModel.getValueAt(row, 0).toString(),
                tableModel.getValueAt(row, 1).toString()))
            lamMoiForm();
    }

    private void dienVaoForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtMaKhoa.setText(tableModel.getValueAt(row, 0).toString());
        txtTenKhoa.setText(tableModel.getValueAt(row, 1).toString());
        Object moTaVal = tableModel.getValueAt(row, 2);
        txtMoTa.setText(moTaVal != null ? moTaVal.toString() : "");
        txtMaKhoa.setEditable(false);
    }

    private void lamMoiForm() {
        txtMaKhoa.setText(""); txtMaKhoa.setEditable(true);
        txtTenKhoa.setText("");
        txtMoTa.setText("");
        table.clearSelection();
        txtMaKhoa.requestFocus();
    }

    private JButton taoNut(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(color); btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 32));
        return btn;
    }
}