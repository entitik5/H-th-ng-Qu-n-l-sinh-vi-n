package view;

import dao.GiangVienDAO;
import dao.SinhVienDAO;
import model.GiangVien;
import model.SinhVien;
import model.TaiKhoan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Panel xem thông tin cá nhân - dành cho Giảng viên
 */
public class ThongTinCaNhanPanel extends JPanel {

    private TaiKhoan      taiKhoan;
    private GiangVienDAO  gvDAO;
    private SinhVienDAO   svDAO;

    public ThongTinCaNhanPanel(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        this.gvDAO    = new GiangVienDAO();
        this.svDAO    = new SinhVienDAO();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("THÔNG TIN CÁ NHÂN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(30, 80, 160));
        add(lblTitle, BorderLayout.NORTH);

        String[][] rows;
        if (taiKhoan.isGiangVien()) {
            if (taiKhoan.getMaGV() == null) {
                JLabel lblMsg = new JLabel(
                    "<html><center>Tài khoản chưa được liên kết với giảng viên.<br>"
                    + "Liên hệ admin để được hỗ trợ.</center></html>",
                    SwingConstants.CENTER);
                lblMsg.setFont(new Font("Arial", Font.PLAIN, 14));
                lblMsg.setForeground(new Color(150, 100, 0));
                add(lblMsg, BorderLayout.CENTER);
                return;
            }

            GiangVien gv = gvDAO.layTheoMa(taiKhoan.getMaGV());
            if (gv == null) {
                add(new JLabel("Không tìm thấy thông tin giảng viên!", SwingConstants.CENTER),
                    BorderLayout.CENTER);
                return;
            }

            rows = new String[][] {
                {"Mã giảng viên",  gv.getMaGV()},
                {"Họ và tên",      gv.getHoTen()},
                {"Ngày sinh",      gv.getNgaySinh() != null
                                   ? gv.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                   : "—"},
                {"Giới tính",      gv.getGioiTinh()},
                {"Khoa / Bộ môn",  gv.getKhoaBoMon()},
                {"Email",          gv.getEmail()},
                {"Số điện thoại",  gv.getSdt()},
                {"Tài khoản",      taiKhoan.getTenDN()},
                {"Vai trò",        taiKhoan.getTenVaiTro()},
            };
        } else if (taiKhoan.isSinhVien()) {
            if (taiKhoan.getMaSV() == null) {
                JLabel lblMsg = new JLabel(
                    "<html><center>Tài khoản chưa được liên kết với sinh viên.<br>"
                    + "Liên hệ admin để được hỗ trợ.</center></html>",
                    SwingConstants.CENTER);
                lblMsg.setFont(new Font("Arial", Font.PLAIN, 14));
                lblMsg.setForeground(new Color(150, 100, 0));
                add(lblMsg, BorderLayout.CENTER);
                return;
            }

            SinhVien sv = svDAO.layTheoMa(taiKhoan.getMaSV());
            if (sv == null) {
                add(new JLabel("Không tìm thấy thông tin sinh viên!", SwingConstants.CENTER),
                    BorderLayout.CENTER);
                return;
            }

            rows = new String[][] {
                {"Mã sinh viên",    sv.getMaSV()},
                {"Họ và tên",       sv.getHoTen()},
                {"Ngày sinh",       sv.getNgaySinh() != null
                                     ? sv.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                     : "—"},
                {"Giới tính",       sv.getGioiTinh()},
                {"Lớp",             sv.getMaLop()},
                {"Email",           sv.getEmail()},
                {"Số điện thoại",   sv.getSdt()},
                {"Tài khoản",       taiKhoan.getTenDN()},
                {"Vai trò",         taiKhoan.getTenVaiTro()},
            };
        } else {
            rows = new String[][] {
                {"Tài khoản", taiKhoan.getTenDN()},
                {"Vai trò",   taiKhoan.getTenVaiTro()},
            };
        }

        // Card thông tin
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 230)),
            new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 8, 8, 8);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < rows.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lblKey = new JLabel(rows[i][0] + ":");
            lblKey.setFont(new Font("Arial", Font.BOLD, 14));
            lblKey.setForeground(new Color(80, 80, 80));
            lblKey.setPreferredSize(new Dimension(160, 30));
            card.add(lblKey, gbc);

            gbc.gridx = 1; gbc.weightx = 1;
            JLabel lblVal = new JLabel(rows[i][1] != null ? rows[i][1] : "—");
            lblVal.setFont(new Font("Arial", Font.PLAIN, 14));
            lblVal.setForeground(new Color(30, 30, 30));
            card.add(lblVal, gbc);
        }

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(245, 247, 250));
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);
    }
}