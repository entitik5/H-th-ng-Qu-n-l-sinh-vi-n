package view;

import controller.LopHocController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class LopHocPanel extends JPanel {

    private JTable            table;
    private DefaultTableModel tableModel;
    private JTextField        txtMaLop, txtTenLop, txtKhoaHoc, txtSiSo;
    private LopHocController  controller;

    public LopHocPanel() {
        String[] columns = {"Mã lớp", "Tên lớp", "Khóa học", "Sĩ số"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        controller = new LopHocController(this, tableModel);
        initUI();
        controller.taiDuLieu();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("QUẢN LÝ LỚP HỌC");
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
            "Thông tin lớp học", 0, 0,
            new Font("Arial", Font.BOLD, 13), new Color(30, 80, 160)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMaLop   = new JTextField(12);
        txtTenLop  = new JTextField(12);
        txtKhoaHoc = new JTextField(12);
        txtSiSo    = new JTextField(12);
        ((AbstractDocument) txtKhoaHoc.getDocument()).setDocumentFilter(new YearRangeDocumentFilter());
        ((AbstractDocument) txtSiSo.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        String[] labels = {"Mã lớp *", "Tên lớp *", "Khóa học *", "Sĩ số"};
        JTextField[] fields = {txtMaLop, txtTenLop, txtKhoaHoc, txtSiSo};

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
            if (controller.them(txtMaLop.getText().trim(), txtTenLop.getText().trim(),
                    txtKhoaHoc.getText().trim(), txtSiSo.getText().trim()))
                lamMoi();
        });
        btnSua.addActionListener(e -> {
            if (table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Chọn lớp cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.sua(txtMaLop.getText().trim(), txtTenLop.getText().trim(),
                    txtKhoaHoc.getText().trim(), txtSiSo.getText().trim()))
                lamMoi();
        });
        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn lớp cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.xoa(tableModel.getValueAt(row, 0).toString())) lamMoi();
        });
        btnLamMoi.addActionListener(e -> lamMoi());
    }

    private void dienVaoForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtMaLop.setText(tableModel.getValueAt(row, 0).toString()); txtMaLop.setEditable(false);
        txtTenLop.setText(tableModel.getValueAt(row, 1).toString());
        txtKhoaHoc.setText(tableModel.getValueAt(row, 2).toString());
        txtSiSo.setText(tableModel.getValueAt(row, 3).toString());
    }

    private void lamMoi() {
        txtMaLop.setText(""); txtMaLop.setEditable(true);
        txtTenLop.setText(""); txtKhoaHoc.setText(""); txtSiSo.setText("");
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


    private static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (isDigits(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (isDigits(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isDigits(String text) {
            return text != null && text.chars().allMatch(Character::isDigit);
        }
    }

    private static class YearRangeDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (isValidInput(fb.getDocument(), offset, 0, string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (isValidInput(fb.getDocument(), offset, length, text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidInput(javax.swing.text.Document doc, int offset, int length, String text)
                throws BadLocationException {
            String current = doc.getText(0, doc.getLength());
            StringBuilder future = new StringBuilder(current);
            future.replace(offset, offset + length, text == null ? "" : text);
            String candidate = future.toString();
            if (candidate.isEmpty()) return true;
            if (candidate.matches("\\d{1,4}") || candidate.matches("\\d{4}-?\\d{0,4}")) {
                return candidate.chars().filter(ch -> ch == '-').count() <= 1;
            }
            return false;
        }
    }
}