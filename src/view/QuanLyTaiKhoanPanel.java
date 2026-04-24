package view;

import controller.TaiKhoanController;
import dao.GiangVienDAO;
import dao.SinhVienDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Panel quản lý tài khoản - CHỈ DÀNH CHO ADMIN
 */
public class QuanLyTaiKhoanPanel extends JPanel {

    private JTable              table;
    private DefaultTableModel   tableModel;
    private JTextField          txtTenDN;
    private JPasswordField      txtMatKhau;
    private JButton             btnToggleMatKhau;
    private JComboBox<String>   cmbVaiTro;
    private JComboBox<String>   cmbMaSV;
    private JComboBox<String>   cmbMaGV;
    private SinhVienDAO         svDAO;
    private GiangVienDAO        gvDAO;
    private TaiKhoanController  controller;

    public QuanLyTaiKhoanPanel() {
        controller = new TaiKhoanController(this);
        this.svDAO = new SinhVienDAO();
        this.gvDAO = new GiangVienDAO();
        initUI();
        taiDuLieu();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setBackground(new Color(245, 247, 250));

        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 80, 160));

        JLabel lblAdmin = new JLabel("  ADMIN ONLY  ");
        lblAdmin.setFont(new Font("Arial", Font.BOLD, 11));
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setBackground(new Color(200, 50, 50));
        lblAdmin.setOpaque(true);
        lblAdmin.setBorder(new EmptyBorder(3, 8, 3, 8));

        titlePanel.add(lblTitle);
        titlePanel.add(lblAdmin);
        add(titlePanel, BorderLayout.NORTH);

        // Bảng danh sách tài khoản
        String[] columns = {"Tên đăng nhập", "Vai trò", "Liên kết"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
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

        // Form tạo tài khoản
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(270, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            "Tạo tài khoản mới", 0, 0,
            new Font("Arial", Font.BOLD, 13), new Color(30, 80, 160)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTenDN   = new JTextField(14);
        txtMatKhau = new JPasswordField(14);
        cmbVaiTro  = new JComboBox<>(new String[]{"sinhvien", "giangvien", "admin"});
        cmbMaSV    = new JComboBox<>();
        cmbMaGV    = new JComboBox<>();
        cmbMaSV.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbMaGV.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbMaSV.setVisible(false);
        cmbMaGV.setVisible(false);
        cmbVaiTro.addActionListener(e -> capNhatLienKetField());

        // Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblTenDN = new JLabel("Tên đăng nhập *");
        lblTenDN.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(lblTenDN, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtTenDN.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(txtTenDN, gbc);

        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblMatKhau = new JLabel("Mật khẩu *");
        lblMatKhau.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(lblMatKhau, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtMatKhau.setFont(new Font("Arial", Font.PLAIN, 13));

        // Panel chứa password field và nút toggle
        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.WHITE);
        passPanel.add(txtMatKhau, BorderLayout.CENTER);

        btnToggleMatKhau = new JButton("[x]");
        btnToggleMatKhau.setFont(new Font("Arial", Font.PLAIN, 12));
        btnToggleMatKhau.setPreferredSize(new Dimension(30, 25));
        btnToggleMatKhau.setFocusPainted(false);
        btnToggleMatKhau.setBorderPainted(false);
        btnToggleMatKhau.setContentAreaFilled(false);
        btnToggleMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnToggleMatKhau.addActionListener(e -> toggleMatKhau());
        passPanel.add(btnToggleMatKhau, BorderLayout.EAST);

        formPanel.add(passPanel, gbc);

        // Vai trò
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        JLabel lblVaiTro = new JLabel("Vai trò *");
        lblVaiTro.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(lblVaiTro, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        cmbVaiTro.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(cmbVaiTro, gbc);
        capNhatLienKetField();

        // Liên kết tài khoản với sinh viên/giảng viên
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        JLabel lblLienKet = new JLabel("Liên kết bản ghi");
        lblLienKet.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(lblLienKet, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(cmbMaSV, gbc);
        formPanel.add(cmbMaGV, gbc);

        // Phân quyền info box
        JTextArea txtInfo = new JTextArea(
            "Phân quyền:\n" +
            "• admin: toàn quyền hệ thống\n" +
            "• giangvien: xem SV, nhập điểm, thống kê\n" +
            "• sinhvien: chỉ xem điểm của bản thân"
        );
        txtInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        txtInfo.setBackground(new Color(235, 245, 255));
        txtInfo.setBorder(new EmptyBorder(8, 8, 8, 8));
        txtInfo.setEditable(false);
        txtInfo.setLineWrap(true);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 4, 8);
        formPanel.add(txtInfo, gbc);

        // Nút bấm
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 6, 6));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(10, 8, 8, 8));
        JButton btnThem = taoNut("Tạo tài khoản", new Color(40, 160, 80));
        JButton btnXoa  = taoNut("Xóa",           new Color(200, 50, 50));
        btnPanel.add(btnThem);
        btnPanel.add(btnXoa);

        gbc.gridy = 5; gbc.insets = new Insets(10, 0, 0, 0);
        formPanel.add(btnPanel, gbc);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(formPanel, BorderLayout.EAST);

        btnThem.addActionListener(e -> taoTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());
    }

    private void taiDuLieu() {
        tableModel.setRowCount(0);
        try {
            Connection conn = util.DBConnection.getInstance().getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT tenDN, vaiTro, maSV, maGV FROM tbl_taikhoan ORDER BY vaiTro, tenDN");
            while (rs.next()) {
                String maSV = rs.getString("maSV");
                String maGV = rs.getString("maGV");
                String lienKet = "-";
                if (maSV != null && !maSV.isEmpty()) lienKet = "SV: " + maSV;
                else if (maGV != null && !maGV.isEmpty()) lienKet = "GV: " + maGV;
                tableModel.addRow(new Object[]{
                    rs.getString("tenDN"),
                    rs.getString("vaiTro"),
                    lienKet
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void taoTaiKhoan() {
        String tenDN   = txtTenDN.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String vaiTro  = cmbVaiTro.getSelectedItem().toString();
        String maSV     = null;
        String maGV     = null;

        if (vaiTro.equals("sinhvien")) {
            if (cmbMaSV.getSelectedItem() == null || cmbMaSV.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn mã sinh viên để liên kết tài khoản!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            maSV = cmbMaSV.getSelectedItem().toString();
        } else if (vaiTro.equals("giangvien")) {
            if (cmbMaGV.getSelectedItem() == null || cmbMaGV.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn mã giảng viên để liên kết tài khoản!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            maGV = cmbMaGV.getSelectedItem().toString();
        }

        if (controller.taoTaiKhoan(tenDN, matKhau, vaiTro, maSV, maGV)) {
            taiDuLieu();
            lamMoi();
        }
    }

    private void xoaTaiKhoan() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                "Chọn tài khoản cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tenDN  = tableModel.getValueAt(row, 0).toString();
        String vaiTro = tableModel.getValueAt(row, 1).toString();

        if (vaiTro.equals("admin")) {
            JOptionPane.showMessageDialog(this,
                "Không thể xóa tài khoản admin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Xóa tài khoản: " + tenDN + "?", "Xác nhận",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = util.DBConnection.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM tbl_taikhoan WHERE tenDN = ?");
                ps.setString(1, tenDN);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Xóa thành công!", "OK", JOptionPane.INFORMATION_MESSAGE);
                    taiDuLieu();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void dienVaoForm() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtTenDN.setText(tableModel.getValueAt(row, 0).toString());
        String vaiTro = tableModel.getValueAt(row, 1).toString();
        cmbVaiTro.setSelectedItem(vaiTro);
        String lienKet = tableModel.getValueAt(row, 2).toString();
        if (vaiTro.equals("sinhvien") && lienKet.startsWith("SV: ")) {
            cmbMaSV.setSelectedItem(lienKet.substring(4));
        } else if (vaiTro.equals("giangvien") && lienKet.startsWith("GV: ")) {
            cmbMaGV.setSelectedItem(lienKet.substring(4));
        }
        txtTenDN.setEditable(false);
    }

    private void lamMoi() {
        txtTenDN.setText(""); txtTenDN.setEditable(true);
        txtMatKhau.setText("");
        cmbVaiTro.setSelectedIndex(0);
        table.clearSelection();
    }

    private void toggleMatKhau() {
        if (txtMatKhau.getEchoChar() == '\u0000') {
            txtMatKhau.setEchoChar('*');
            btnToggleMatKhau.setText("[x]");
        } else {
            txtMatKhau.setEchoChar('\u0000');
            btnToggleMatKhau.setText("[o]");
        }
    }

    private void capNhatLienKetField() {
        String vaiTro = cmbVaiTro.getSelectedItem().toString();
        cmbMaSV.removeAllItems();
        cmbMaGV.removeAllItems();

        if (vaiTro.equals("sinhvien")) {
            cmbMaSV.addItem("Chọn mã sinh viên");
            svDAO.layTatCa().forEach(sv -> cmbMaSV.addItem(sv.getMaSV()));
            cmbMaSV.setVisible(true);
            cmbMaGV.setVisible(false);
        } else if (vaiTro.equals("giangvien")) {
            cmbMaGV.addItem("Chọn mã giảng viên");
            gvDAO.layTatCa().forEach(gv -> cmbMaGV.addItem(gv.getMaGV()));
            cmbMaSV.setVisible(false);
            cmbMaGV.setVisible(true);
        } else {
            cmbMaSV.setVisible(false);
            cmbMaGV.setVisible(false);
        }
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