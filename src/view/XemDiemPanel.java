package view;

import model.TaiKhoan;
import util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/**
 * Panel xem điểm cá nhân - CHỈ DÀNH CHO SINH VIÊN
 */
public class XemDiemPanel extends JPanel {

    private TaiKhoan        taiKhoan;
    private DefaultTableModel tableModel;
    private JLabel          lblDiemTB;
    private JLabel          lblHocLuc;

    public XemDiemPanel(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        initUI();
        taiDuLieu();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setBackground(new Color(245, 247, 250));
        JLabel lblTitle = new JLabel("KẾT QUẢ HỌC TẬP CỦA BẠN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 80, 160));
        titlePanel.add(lblTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Thông tin sinh viên
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            new EmptyBorder(12, 15, 12, 15)
        ));

        lblDiemTB  = taoInfoBox("Điểm trung bình", "—", new Color(30, 80, 160));
        lblHocLuc  = taoInfoBox("Học lực", "—", new Color(40, 160, 80));
        JLabel lblMaSV = taoInfoBox("Mã sinh viên", taiKhoan.getMaSV() != null ? taiKhoan.getMaSV() : "—", new Color(100, 100, 100));
        JLabel lblTK   = taoInfoBox("Tài khoản", taiKhoan.getTenDN(), new Color(100, 100, 100));

        infoPanel.add(lblMaSV);
        infoPanel.add(lblTK);
        infoPanel.add(lblDiemTB);
        infoPanel.add(lblHocLuc);

        // Bảng điểm
        String[] columns = {"Môn học", "Điểm số", "Xếp loại"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(30, 80, 160));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(190, 215, 255));
        table.getColumnModel().getColumn(0).setPreferredWidth(350);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Nút làm mới
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Arial", Font.BOLD, 13));
        btnLamMoi.setBackground(new Color(30, 80, 160));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLamMoi.addActionListener(e -> taiDuLieu());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setBackground(new Color(245, 247, 250));
        southPanel.add(btnLamMoi);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void taiDuLieu() {
        tableModel.setRowCount(0);
        if (taiKhoan.getMaSV() == null) {
            JOptionPane.showMessageDialog(this,
                "Tài khoản chưa được liên kết với sinh viên!\nLiên hệ admin để được hỗ trợ.",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double tongDiem = 0;
        int    soCot    = 0;

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT mh.tenMH, d.diemSo " +
             "FROM tbl_diemso d " +
             "JOIN tbl_monhoc mh ON d.maMH = mh.maMH " +
             "WHERE d.maSV = ? ORDER BY mh.tenMH";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, taiKhoan.getMaSV());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double diem = rs.getDouble("diemSo");
                String xepLoai = model.DiemSo.tinhXepLoai(diem);
                tableModel.addRow(new Object[]{
                    rs.getString("tenMH"),
                    diem,
                    xepLoai 
            });
            tongDiem += diem;
            soCot++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (soCot > 0) {
            double diemTB = Math.round(tongDiem / soCot * 100.0) / 100.0;
            lblDiemTB.setText("<html><center><span style='font-size:11px;color:gray'>Điểm trung bình</span><br>"
                + "<span style='font-size:22px;font-weight:bold;color:#1E50A0'>" + diemTB + "</span></center></html>");
            String hocLuc = tinhHocLuc(diemTB);
            lblHocLuc.setText("<html><center><span style='font-size:11px;color:gray'>Học lực</span><br>"
                + "<span style='font-size:22px;font-weight:bold;color:#28A050'>" + hocLuc + "</span></center></html>");
        } else {
            lblDiemTB.setText("<html><center><span style='font-size:11px;color:gray'>Điểm trung bình</span><br>"
                + "<span style='font-size:18px;color:gray'>Chưa có điểm</span></center></html>");
        }
    }

    private String tinhHocLuc(double diem) {
        if (diem >= 9.5) return "A+";
        if (diem >= 9.0) return "A";
        if (diem >= 8.5) return "A-";
        if (diem >= 8.0) return "B+";
        if (diem >= 7.5) return "B";
        if (diem >= 7.0) return "B-";
        if (diem >= 6.5) return "C+";
        if (diem >= 6.0) return "C";
        if (diem >= 5.5) return "D+";
        if (diem >= 5.0) return "D";
        return "F";
    }

    private JLabel taoInfoBox(String label, String value, Color color) {
        JLabel lbl = new JLabel("<html><center>"
            + "<span style='font-size:11px;color:gray'>" + label + "</span><br>"
            + "<span style='font-size:18px;font-weight:bold;color:" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()) + "'>" + value + "</span>"
            + "</center></html>", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        return lbl;
    }
}