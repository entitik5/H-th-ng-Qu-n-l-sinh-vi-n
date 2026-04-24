package view;

import controller.TaiKhoanController;
import model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField     txtTenDN;
    private JPasswordField txtMatKhau;
    private JButton        btnToggleMatKhau;
    private TaiKhoanController controller;

    public LoginFrame() {
        controller = new TaiKhoanController(this);
        initUI();
    }

    private void initUI() {
        setTitle("Đăng nhập - Hệ thống Quản lý Sinh viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblTitle = new JLabel("QUẢN LÝ SINH VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 80, 160));
        lblTitle.setBorder(new EmptyBorder(0, 0, 25, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblTenDN = new JLabel("Tên đăng nhập:");
        lblTenDN.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblTenDN, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        txtTenDN = new JTextField(18);
        txtTenDN.setFont(new Font("Arial", Font.PLAIN, 14));
        txtTenDN.setPreferredSize(new Dimension(200, 35));
        formPanel.add(txtTenDN, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblMatKhau, gbc);

        gbc.gridx = 1; gbc.weightx = 1;
        txtMatKhau = new JPasswordField(18);
        txtMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMatKhau.setPreferredSize(new Dimension(150, 35));

        JPanel passPanel = new JPanel(new BorderLayout(5, 0));
        passPanel.setBackground(new Color(245, 247, 250));
        passPanel.setPreferredSize(new Dimension(220, 35));
        passPanel.add(txtMatKhau, BorderLayout.CENTER);

        btnToggleMatKhau = new JButton("[x]");
        btnToggleMatKhau.setFont(new Font("Arial", Font.PLAIN, 12));
        btnToggleMatKhau.setMargin(new Insets(0, 0, 0, 0));
        btnToggleMatKhau.setPreferredSize(new Dimension(45, 35));
        btnToggleMatKhau.setFocusPainted(false);
        btnToggleMatKhau.setBorderPainted(true);
        btnToggleMatKhau.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        btnToggleMatKhau.setOpaque(true);
        btnToggleMatKhau.setBackground(new Color(235, 235, 235));
        btnToggleMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnToggleMatKhau.addActionListener(e -> toggleMatKhau());
        passPanel.add(btnToggleMatKhau, BorderLayout.EAST);

        formPanel.add(passPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(245, 247, 250));

        JButton btnDangNhap = taoNut("Đăng nhập", new Color(30, 80, 160));
        JButton btnThoat    = taoNut("Thoát",     new Color(200, 50, 50));
        btnPanel.add(btnDangNhap);
        btnPanel.add(btnThoat);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        btnThoat.addActionListener(e -> System.exit(0));
        txtMatKhau.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) xuLyDangNhap();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override public void windowOpened(WindowEvent e) { txtTenDN.requestFocus(); }
        });
    }

    private void xuLyDangNhap() {
        String tenDN   = txtTenDN.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();

        TaiKhoan tk = controller.dangNhap(tenDN, matKhau);
        if (tk != null) {
            dispose();
            new MainFrame(tk).setVisible(true);
        } else {
            txtMatKhau.setText("");
            txtMatKhau.requestFocus();
        }
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

    private JButton taoNut(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 38));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            final Color original = bgColor;
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(original.darker()); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(original); }
        });
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("TableHeader.foreground", Color.BLACK);
        } catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}