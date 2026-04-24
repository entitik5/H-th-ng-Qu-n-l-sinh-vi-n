package controller;

import dao.KhoaDAO;
import model.Khoa;
import view.KhoaPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controller xử lý logic nghiệp vụ cho màn hình Quản lý Khoa
 * Nhận sự kiện từ KhoaPanel, gọi KhoaDAO để thao tác DB
 */
public class KhoaController {

    private KhoaPanel          view;
    private KhoaDAO            khoaDAO;
    private DefaultTableModel  tableModel;

    public KhoaController(KhoaPanel view, DefaultTableModel tableModel) {
        this.view       = view;
        this.tableModel = tableModel;
        this.khoaDAO    = new KhoaDAO();
    }

    // ==================== LOAD DỮ LIỆU ====================

    public void taiDuLieu() {
        tableModel.setRowCount(0);
        List<Khoa> danhSach = khoaDAO.layTatCa();
        for (Khoa k : danhSach) {
            tableModel.addRow(new Object[]{
                k.getMaKhoa(), k.getTenKhoa(), k.getMoTa()
            });
        }
    }

    // ==================== THÊM ====================

    public boolean them(String maKhoa, String tenKhoa, String moTa) {
        if (maKhoa.isEmpty() || tenKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập đầy đủ các trường bắt buộc (*)",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (khoaDAO.kiemTraTonTai(maKhoa)) {
            JOptionPane.showMessageDialog(view,
                "Mã khoa đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Khoa khoa = new Khoa(maKhoa, tenKhoa, moTa);
        if (khoaDAO.them(khoa)) {
            JOptionPane.showMessageDialog(view,
                "Thêm khoa thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        } else {
            JOptionPane.showMessageDialog(view,
                "Thêm khoa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== SỬA ====================

    public boolean sua(String maKhoa, String tenKhoa, String moTa) {
        if (tenKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập tên khoa (*)",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        Khoa khoa = new Khoa(maKhoa, tenKhoa, moTa);
        if (khoaDAO.sua(khoa)) {
            JOptionPane.showMessageDialog(view,
                "Cập nhật khoa thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        } else {
            JOptionPane.showMessageDialog(view,
                "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== XÓA ====================

    public boolean xoa(String maKhoa, String tenKhoa) {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xóa khoa: " + tenKhoa + "?\nLưu ý: Chỉ xóa được khi không còn sinh viên thuộc khoa này!",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (khoaDAO.xoa(maKhoa)) {
                JOptionPane.showMessageDialog(view,
                    "Xóa khoa thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                taiDuLieu();
                return true;
            } else {
                JOptionPane.showMessageDialog(view,
                    "Xóa thất bại! Có thể còn sinh viên thuộc khoa này.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    // ==================== TÌM KIẾM ====================

    public void timKiem(String tuKhoa) {
        if (tuKhoa.isEmpty()) { taiDuLieu(); return; }
        tableModel.setRowCount(0);
        khoaDAO.timKiem(tuKhoa).forEach(k -> tableModel.addRow(new Object[]{
            k.getMaKhoa(), k.getTenKhoa(), k.getMoTa()
        }));
    }
}