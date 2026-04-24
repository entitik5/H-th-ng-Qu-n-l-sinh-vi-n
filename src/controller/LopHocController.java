package controller;

import dao.LopHocDAO;
import model.LopHoc;
import view.LopHocPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Controller xử lý logic nghiệp vụ cho màn hình Quản lý Lớp học
 */
public class LopHocController {

    private LopHocPanel      view;
    private LopHocDAO        lopDAO;
    private DefaultTableModel tableModel;

    public LopHocController(LopHocPanel view, DefaultTableModel tableModel) {
        this.view       = view;
        this.lopDAO     = new LopHocDAO();
        this.tableModel = tableModel;
    }

    // ==================== LOAD DỮ LIỆU ====================

    public void taiDuLieu() {
        tableModel.setRowCount(0);
        lopDAO.layTatCa().forEach(lop -> tableModel.addRow(new Object[]{
            lop.getMaLop(), lop.getTenLop(), lop.getKhoaHoc(), lop.getSiSo()
        }));
    }

    // ==================== THÊM ====================

    public boolean them(String maLop, String tenLop, String khoaHoc, String siSoStr) {
        if (maLop.isEmpty() || tenLop.isEmpty() || khoaHoc.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ các trường bắt buộc (*)!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (lopDAO.kiemTraTonTai(maLop)) {
            JOptionPane.showMessageDialog(view,
                "Mã lớp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int siSo;
        String khoaHocValue;
        try {
            siSo = parseSiSo(siSoStr);
            khoaHocValue = parseKhoaHoc(khoaHoc);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        LopHoc lop = new LopHoc(maLop, tenLop, khoaHocValue, siSo);
        if (lopDAO.them(lop)) {
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

    public boolean sua(String maLop, String tenLop, String khoaHoc, String siSoStr) {
        if (tenLop.isEmpty() || khoaHoc.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ các trường bắt buộc (*)!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int siSo;
        String khoaHocValue;
        try {
            siSo = parseSiSo(siSoStr);
            khoaHocValue = parseKhoaHoc(khoaHoc);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        LopHoc lop = new LopHoc(maLop, tenLop, khoaHocValue, siSo);
        if (lopDAO.sua(lop)) {
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

    public boolean xoa(String maLop) {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xóa lớp " + maLop + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && lopDAO.xoa(maLop)) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        }
        return false;
    }

    // ==================== HELPER ====================

    private int parseSiSo(String siSoStr) {
        if (siSoStr == null || siSoStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Nhập sĩ số lớp!");
        }
        return Integer.parseInt(siSoStr.trim());
    }

    private String parseKhoaHoc(String khoaHocStr) {
        if (khoaHocStr == null || khoaHocStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Nhập khóa học!");
        }
        String khoaHoc = khoaHocStr.trim();
        if (!khoaHoc.matches("\\d{4}-\\d{4}")) {
            throw new IllegalArgumentException("Khóa học phải theo định dạng YYYY-YYYY!");
        }
        String[] parts = khoaHoc.split("-");
        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);
        validateKhoaHocNam(start);
        validateKhoaHocNam(end);
        if (end != start + 4) {
            throw new IllegalArgumentException("Năm kết thúc phải bằng năm bắt đầu + 4!");
        }
        return khoaHoc;
    }

    private void validateKhoaHocNam(int nam) {
        if (nam < 1900 || nam > 2100) {
            throw new IllegalArgumentException("Khóa học phải là năm hợp lệ!");
        }
    }
}
