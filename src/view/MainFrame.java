package view;

import controller.TaiKhoanController;
import model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Màn hình chính sau khi đăng nhập thành công
 * Chứa menu điều hướng các chức năng
 */
public class MainFrame extends JFrame {

    private TaiKhoan           taiKhoan;
    private JPanel             contentPanel;
    private TaiKhoanController tkController;

    public MainFrame(TaiKhoan taiKhoan) {
        this.taiKhoan     = taiKhoan;
        this.tkController = new TaiKhoanController(this);
        initUI();
    }

    private void initUI() {
        setTitle("Hệ thống Quản lý Sinh viên - " + taiKhoan.getTenDN());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 80, 160));
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ SINH VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblUser = new JLabel("Xin chào, " + taiKhoan.getTenDN()
                + " (" + taiKhoan.getVaiTro() + ")");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 13));
        lblUser.setForeground(new Color(200, 220, 255));

        header.add(lblTitle, BorderLayout.WEST);
        header.add(lblUser,  BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ===== SIDEBAR (menu bên trái) =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(40, 55, 80));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Phân quyền theo 3 vai trò
        boolean isAdmin     = taiKhoan.isAdmin();
        boolean isGiangVien = taiKhoan.isGiangVien();
        boolean isSinhVien  = taiKhoan.isSinhVien();

        // Menu chung cho tất cả
        themNutMenu(sidebar, "", "Trang chủ");
        themNutMenu(sidebar, "", "Đổi mật khẩu");

        if (isSinhVien) {
            sidebar.add(Box.createVerticalStrut(10));
            themNhanVaiTro(sidebar, "— SINH VIÊN —", new Color(80, 160, 80));
            sidebar.add(Box.createVerticalStrut(6));
            themNutMenu(sidebar, "", "Điểm của tôi");
            themNutMenu(sidebar, "", "Thông tin cá nhân");
        }

        if (isGiangVien) {
            sidebar.add(Box.createVerticalStrut(10));
            themNhanVaiTro(sidebar, "— GIẢNG VIÊN —", new Color(255, 160, 50));
            sidebar.add(Box.createVerticalStrut(6));
            themNutMenu(sidebar, "", "Sinh viên");
            themNutMenu(sidebar, "", "Điểm số");
            themNutMenu(sidebar, "", "Thống kê");
            themNutMenu(sidebar, "", "Thông tin cá nhân");
        }

        if (isAdmin) {
            sidebar.add(Box.createVerticalStrut(10));
            themNhanVaiTro(sidebar, "— ADMIN —", new Color(255, 120, 50));
            sidebar.add(Box.createVerticalStrut(6));
            themNutMenu(sidebar, "", "Sinh viên");
            themNutMenu(sidebar, "", "Khoa");          // <<< THÊM MỚI
            themNutMenu(sidebar, "", "Lớp học");
            themNutMenu(sidebar, "", "Môn học");
            themNutMenu(sidebar, "", "Điểm số");
            themNutMenu(sidebar, "", "Thống kê");
            themNutMenu(sidebar, "", "Quản lý tài khoản");
        }

        sidebar.add(Box.createVerticalGlue());
        themNutMenu(sidebar, "", "Đăng xuất");

        add(sidebar, BorderLayout.WEST);

        // ===== CONTENT PANEL (bên phải) =====
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));
        add(contentPanel, BorderLayout.CENTER);

        // Hiển thị trang chủ mặc định
        hienThiTrangChu();
    }

    // ==================== ĐIỀU HƯỚNG ====================

    private void dieuHuong(String tenMenu) {
        switch (tenMenu) {
            case "Trang chủ"          -> hienThiTrangChu();
            case "Sinh viên"          -> hienThiPanel(new SinhVienPanel());
            case "Khoa"               -> hienThiPanel(new KhoaPanel());   // <<< THÊM MỚI
            case "Lớp học"            -> hienThiPanel(new LopHocPanel());
            case "Môn học"            -> hienThiPanel(new MonHocPanel());
            case "Điểm số"            -> hienThiPanel(new DiemSoPanel());
            case "Điểm của tôi"       -> hienThiPanel(new XemDiemPanel(taiKhoan));
            case "Thống kê"           -> hienThiPanel(new ThongKePanel());
            case "Thông tin cá nhân"  -> hienThiPanel(new ThongTinCaNhanPanel(taiKhoan));
            case "Quản lý tài khoản"  -> hienThiPanel(new QuanLyTaiKhoanPanel());
            case "Đổi mật khẩu"       -> doiMatKhau();
            case "Đăng xuất"          -> dangXuat();
        }
    }

    private void themNutMenu(JPanel sidebar, String icon, String ten) {
        JButton btn = taoNutMenu(icon + "  " + ten);
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(4));
        btn.addActionListener(e -> dieuHuong(ten));
    }

    private void themNhanVaiTro(JPanel sidebar, String text, Color color) {
        JLabel lbl = new JLabel("  " + text);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lbl);
    }

    private void hienThiPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void hienThiTrangChu() {
        contentPanel.removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));

        String quyenHan;
        String mauVaiTro;

        if (taiKhoan.isAdmin()) {
            mauVaiTro = "#C85000";
            quyenHan = "<li>Quản lý sinh viên, khoa, lớp học, môn học</li>"
                     + "<li>Nhập và xem điểm số</li>"
                     + "<li>Xem thống kê học lực</li>"
                     + "<li>Quản lý tài khoản người dùng</li>";
        } else if (taiKhoan.isGiangVien()) {
            mauVaiTro = "#8B6000";
            quyenHan = "<li>Xem danh sách sinh viên</li>"
                     + "<li>Nhập và chỉnh sửa điểm số</li>"
                     + "<li>Xem thống kê học lực</li>";
        } else {
            mauVaiTro = "#1E6040";
            quyenHan = "<li>Xem điểm số của bản thân</li>"
                     + "<li>Xem thông tin cá nhân</li>";
        }

        String thongKe = taiKhoan.isAdmin() ?
            "<table cellpadding='6' style='margin-top:10px'>"
            + "<tr><td style='padding:6px 20px;background:#f0f4ff;border-radius:4px'>Sinh viên</td>"
            + "<td style='padding:6px 20px;background:#f0f4ff'>Lớp học</td>"
            + "<td style='padding:6px 20px;background:#f0f4ff'>Khoa</td>"
            + "<td style='padding:6px 20px;background:#f0f4ff'>Môn học</td></tr>"
            + "</table>" : "";

        String html = "<html><body style='font-family:Arial;padding:30px'>"
            + "<h2 style='color:#1E50A0'>Chào mừng, " + taiKhoan.getTenDN() + "!</h2>"
            + "<p>Vai trò: <b style='color:" + mauVaiTro + "'>"
            + taiKhoan.getVaiTro() + "</b></p>"
            + "<p>Bạn có thể thực hiện các chức năng:</p>"
            + "<ul>" + quyenHan + "</ul>"
            + thongKe
            + "</body></html>";

        JLabel lblWelcome = new JLabel(html);
        panel.add(lblWelcome);

        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ==================== ĐỔI MẬT KHẨU ====================

    private void doiMatKhau() {
        JDialog dialog = new JDialog(this, "Đổi mật khẩu", true);
        dialog.setSize(380, 220);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPasswordField txtCu   = new JPasswordField(15);
        JPasswordField txtMoi  = new JPasswordField(15);
        JPasswordField txtXN   = new JPasswordField(15);
        JButton btnToggleCu    = new JButton("[x]");
        JButton btnToggleMoi   = new JButton("[x]");
        JButton btnToggleXN    = new JButton("[x]");

        // Mật khẩu cũ
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panel.add(new JLabel("Mật khẩu cũ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        JPanel panelCu = new JPanel(new BorderLayout());
        panelCu.add(txtCu, BorderLayout.CENTER);
        btnToggleCu.addActionListener(e -> togglePassword(txtCu, btnToggleCu));
        panelCu.add(btnToggleCu, BorderLayout.EAST);
        panel.add(panelCu, gbc);

        // Mật khẩu mới
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Mật khẩu mới:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        JPanel panelMoi = new JPanel(new BorderLayout());
        panelMoi.add(txtMoi, BorderLayout.CENTER);
        btnToggleMoi.addActionListener(e -> togglePassword(txtMoi, btnToggleMoi));
        panelMoi.add(btnToggleMoi, BorderLayout.EAST);
        panel.add(panelMoi, gbc);

        // Xác nhận
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panel.add(new JLabel("Xác nhận:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        JPanel panelXN = new JPanel(new BorderLayout());
        panelXN.add(txtXN, BorderLayout.CENTER);
        btnToggleXN.addActionListener(e -> togglePassword(txtXN, btnToggleXN));
        panelXN.add(btnToggleXN, BorderLayout.EAST);
        panel.add(panelXN, gbc);

        dialog.add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnOK     = new JButton("OK");
        JButton btnCancel = new JButton("Hủy");
        btnPanel.add(btnOK);
        btnPanel.add(btnCancel);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        btnOK.addActionListener(e -> {
            tkController.doiMatKhau(
                taiKhoan.getTenDN(),
                new String(txtCu.getPassword()),
                new String(txtMoi.getPassword()),
                new String(txtXN.getPassword())
            );
            dialog.dispose();
        });
        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void togglePassword(JPasswordField field, JButton button) {
        if (field.getEchoChar() == '\u0000') {
            field.setEchoChar('*');
            button.setText("[x]");
        } else {
            field.setEchoChar('\u0000');
            button.setText("[o]");
        }
    }

    private void dangXuat() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn đăng xuất?", "Xác nhận",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame().setVisible(true);
        }
    }

    // ==================== HELPER ====================

    private JButton taoNutMenu(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setForeground(new Color(200, 220, 255));
        btn.setBackground(new Color(40, 55, 80));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 42));
        btn.setPreferredSize(new Dimension(200, 42));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(5, 20, 5, 5));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(60, 80, 120));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(40, 55, 80));
            }
        });
        return btn;
    }
}