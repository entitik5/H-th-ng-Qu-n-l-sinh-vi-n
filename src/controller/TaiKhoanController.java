package controller;

import dao.TaiKhoanDAO;
import model.TaiKhoan;

import javax.swing.*;
import java.awt.*;

/**
 * Controller xử lý logic đăng nhập, đổi mật khẩu, tạo tài khoản
 */
public class TaiKhoanController {

    private Component    view;
    private TaiKhoanDAO  tkDAO;

    public TaiKhoanController(Component view) {
        this.view  = view;
        this.tkDAO = new TaiKhoanDAO();
    }

    // ==================== ĐĂNG NHẬP ====================

    public TaiKhoan dangNhap(String tenDN, String matKhau) {
        if (tenDN.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        TaiKhoan tk = tkDAO.dangNhap(tenDN, matKhau);
        if (tk == null) {
            JOptionPane.showMessageDialog(view,
                "Tên đăng nhập hoặc mật khẩu không đúng!",
                "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
        return tk;
    }

    // ==================== ĐỔI MẬT KHẨU ====================

    public boolean doiMatKhau(String tenDN, String matKhauCu,
                               String matKhauMoi, String xacNhan) {
        if (matKhauCu.isEmpty() || matKhauMoi.isEmpty() || xacNhan.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập đầy đủ thông tin!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!matKhauMoi.equals(xacNhan)) {
            JOptionPane.showMessageDialog(view,
                "Mật khẩu mới không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (matKhauMoi.length() < 6) {
            JOptionPane.showMessageDialog(view,
                "Mật khẩu mới phải có ít nhất 6 ký tự!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (tkDAO.doiMatKhau(tenDN, matKhauCu, matKhauMoi)) {
            JOptionPane.showMessageDialog(view,
                "Đổi mật khẩu thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(view,
                "Mật khẩu cũ không đúng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== TẠO TÀI KHOẢN ====================

    public boolean taoTaiKhoan(String tenDN, String matKhau, String vaiTro, String maSV, String maGV) {
        if (tenDN.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ tên đăng nhập và mật khẩu!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (matKhau.length() < 6) {
            JOptionPane.showMessageDialog(view,
                "Mật khẩu phải có ít nhất 6 ký tự!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (tkDAO.kiemTraTonTai(tenDN)) {
            JOptionPane.showMessageDialog(view,
                "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        TaiKhoan tk = new TaiKhoan(tenDN, matKhau, vaiTro, maSV, maGV);
        if (tkDAO.them(tk)) {
            JOptionPane.showMessageDialog(view,
                "Tạo tài khoản thành công!\nTài khoản: " + tenDN
                + "\nVai trò: " + vaiTro,
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        JOptionPane.showMessageDialog(view, "Tạo thất bại!", "Lỗi",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
}