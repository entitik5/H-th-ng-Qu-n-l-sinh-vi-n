package view;

import controller.SinhVienController;
import model.LopHoc;
import model.Khoa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SinhVienPanel extends JPanel {

    private JTable             table;
    private DefaultTableModel  tableModel;
    private JTextField         txtTimKiem;
    private SinhVienController controller;
    private JTextField         txtMaSV, txtHoTen, txtNgaySinh, txtEmail, txtSdt;
    private JComboBox<LopHoc>  cmbLop;
    private JComboBox<Khoa>    cmbKhoa;   // THÊM MỚI
    private JComboBox<String>  cmbGioiTinh;

    public SinhVienPanel() {
        // Thêm cột "Khoa" vào bảng
        String[] columns = {"Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Lớp", "Khoa", "Email", "SĐT"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        controller = new SinhVienController(this, tableModel);
        initUI();
        controller.taiDuLieu();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("QUẢN LÝ SINH VIÊN");
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
        table.setGridColor(new Color(220, 225, 235));
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(90);
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Khoa
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 230)));
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) dienVaoForm();
        });

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            "Thông tin sinh viên",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13), new Color(30, 80, 160)));
        formPanel.setPreferredSize(new Dimension(290, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtMaSV     = new JTextField(14);
        txtHoTen    = new JTextField(14);
        txtNgaySinh = new JTextField(14);
        txtNgaySinh.setToolTipText("Định dạng: dd-MM-yyyy (ví dụ: 15-03-2004)");
        txtEmail    = new JTextField(14);
        txtSdt      = new JTextField(14);
        cmbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});

        cmbLop  = new JComboBox<>();
        controller.layDanhSachLop().forEach(lop -> cmbLop.addItem(lop));

        cmbKhoa = new JComboBox<>();
        controller.layDanhSachKhoa().forEach(khoa -> cmbKhoa.addItem(khoa));

        String[]    labels = {"Mã SV *", "Họ tên *", "Ngày sinh *", "Giới tính *", "Lớp *", "Khoa *", "Email", "SĐT"};
        Component[] inputs = {txtMaSV, txtHoTen, txtNgaySinh, cmbGioiTinh, cmbLop, cmbKhoa, txtEmail, txtSdt};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            inputs[i].setFont(new Font("Arial", Font.PLAIN, 13));
            formPanel.add(inputs[i], gbc);
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
        gbc.gridwidth = 2; gbc.weightx = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        formPanel.add(btnPanel, gbc);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(245, 247, 250));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(20);
        txtTimKiem.setFont(new Font("Arial", Font.PLAIN, 13));
        searchPanel.add(txtTimKiem);
        JButton btnTimKiem = taoNut("Tìm", new Color(30, 80, 160));
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

        btnThem.addActionListener(e -> themSinhVien());
        btnSua.addActionListener(e -> suaSinhVien());
        btnXoa.addActionListener(e -> xoaSinhVien());
        btnLamMoi.addActionListener(e -> lamMoiForm());
        btnTimKiem.addActionListener(e -> controller.timKiem(txtTimKiem.getText().trim()));
        btnTaiLai.addActionListener(e -> controller.taiDuLieu());
        txtTimKiem.addActionListener(e -> controller.timKiem(txtTimKiem.getText().trim()));
    }

    private void themSinhVien() {
        if (controller.them(
                txtMaSV.getText().trim(),
                txtHoTen.getText().trim(),
                txtNgaySinh.getText().trim(),
                cmbGioiTinh.getSelectedItem().toString(),
                cmbLop.getSelectedItem()  != null ? ((LopHoc) cmbLop.getSelectedItem()).getMaLop()   : "",
                cmbKhoa.getSelectedItem() != null ? ((Khoa)   cmbKhoa.getSelectedItem()).getMaKhoa() : "",
                txtEmail.getText().trim(),
                txtSdt.getText().trim()))
            lamMoiForm();
    }

    private void suaSinhVien() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (controller.sua(
                txtMaSV.getText().trim(),
                txtHoTen.getText().trim(),
                txtNgaySinh.getText().trim(),
                cmbGioiTinh.getSelectedItem().toString(),
                cmbLop.getSelectedItem()  != null ? ((LopHoc) cmbLop.getSelectedItem()).getMaLop()   : "",
                cmbKhoa.getSelectedItem() != null ? ((Khoa)   cmbKhoa.getSelectedItem()).getMaKhoa() : "",
                txtEmail.getText().trim(),
                txtSdt.getText().trim()))
            lamMoiForm();
    }

    private void xoaSinhVien() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (controller.xoa(tableModel.getValueAt(row, 0).toString(), tableModel.getValueAt(row, 1).toString()))
            lamMoiForm();
    }

    private void dienVaoForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtMaSV.setText(tableModel.getValueAt(row, 0).toString());
        txtHoTen.setText(tableModel.getValueAt(row, 1).toString());
        String ngayHienThi = tableModel.getValueAt(row, 2).toString();
        try {
            LocalDate date = LocalDate.parse(ngayHienThi, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            txtNgaySinh.setText(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } catch (Exception ex) { txtNgaySinh.setText(ngayHienThi); }
        cmbGioiTinh.setSelectedItem(tableModel.getValueAt(row, 3).toString());

        // Chọn đúng lớp
        String maLop = tableModel.getValueAt(row, 4).toString();
        for (int i = 0; i < cmbLop.getItemCount(); i++) {
            if (cmbLop.getItemAt(i).getMaLop().equals(maLop)) { cmbLop.setSelectedIndex(i); break; }
        }

        // Chọn đúng khoa
        String maKhoa = tableModel.getValueAt(row, 5).toString();
        for (int i = 0; i < cmbKhoa.getItemCount(); i++) {
            if (cmbKhoa.getItemAt(i).getMaKhoa().equals(maKhoa)) { cmbKhoa.setSelectedIndex(i); break; }
        }

        txtEmail.setText(tableModel.getValueAt(row, 6).toString());
        txtSdt.setText(tableModel.getValueAt(row, 7).toString());
        txtMaSV.setEditable(false);
    }

    private void lamMoiForm() {
        txtMaSV.setText(""); txtMaSV.setEditable(true);
        txtHoTen.setText(""); txtNgaySinh.setText("");
        txtEmail.setText(""); txtSdt.setText("");
        cmbGioiTinh.setSelectedIndex(0);
        if (cmbLop.getItemCount()  > 0) cmbLop.setSelectedIndex(0);
        if (cmbKhoa.getItemCount() > 0) cmbKhoa.setSelectedIndex(0);
        table.clearSelection();
        txtMaSV.requestFocus();
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