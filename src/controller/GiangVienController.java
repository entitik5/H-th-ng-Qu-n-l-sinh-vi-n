/*
package controller;

import dao.GiangVienDAO;
import model.GiangVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Controller xử lý logic nghiệp vụ cho màn hình Quản lý Giảng viên

public class GiangVienController {

    private Component        view;
    private GiangVienDAO     gvDAO;
    private DefaultTableModel tableModel;

    public GiangVienController(Component view, DefaultTableModel tableModel) {
        this.view       = view;
        this.gvDAO      = new GiangVienDAO();
        this.tableModel = tableModel;
    }

    // ==================== LOAD DỮ LIỆU ====================

    public void taiDuLieu() {
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        gvDAO.layTatCa().forEach(gv -> tableModel.addRow(new Object[]{
            gv.getMaGV(), gv.getHoTen(),
            gv.getNgaySinh() != null ? gv.getNgaySinh().format(fmt) : "",
            gv.getGioiTinh(), gv.getKhoaBoMon(),
            gv.getEmail(), gv.getSdt()
        }));
    }

    // ==================== THÊM ====================

    public boolean them(String maGV, String hoTen, String ngaySinhStr,
                        String gioiTinh, String khoaBoMon, String email, String sdt) {
        if (maGV.isEmpty() || hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ các trường bắt buộc (*)",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (gvDAO.kiemTraTonTai(maGV)) {
            JOptionPane.showMessageDialog(view,
                "Mã giảng viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        LocalDate ngaySinh = parseNgaySinh(ngaySinhStr);
        GiangVien gv = new GiangVien(maGV, hoTen, ngaySinh, gioiTinh, khoaBoMon, email, sdt);
        if (gvDAO.them(gv)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        }
        JOptionPane.showMessageDialog(view, "Thêm thất bại!", "Lỗi",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // ==================== SỬA ====================

    public boolean sua(String maGV, String hoTen, String ngaySinhStr,
                       String gioiTinh, String khoaBoMon, String email, String sdt) {
        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ các trường bắt buộc (*)",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        LocalDate ngaySinh = parseNgaySinh(ngaySinhStr);
        GiangVien gv = new GiangVien(maGV, hoTen, ngaySinh, gioiTinh, khoaBoMon, email, sdt);
        if (gvDAO.sua(gv)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        }
        JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // ==================== XÓA ====================

    public boolean xoa(String maGV) {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xóa giảng viên " + maGV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && gvDAO.xoa(maGV)) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        }
        return false;
    }

    // ==================== HELPER ====================

    private LocalDate parseNgaySinh(String s) {
        if (s == null || s.isEmpty()) return null;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(s, fmt);
        }
        catch (DateTimeParseException e) { return null; }
    }
}
*/