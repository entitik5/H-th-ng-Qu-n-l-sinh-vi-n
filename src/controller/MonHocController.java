package controller;

import dao.MonHocDAO;
import model.MonHoc;
import view.MonHocPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Controller xử lý logic nghiệp vụ cho màn hình Quản lý Môn học
 */
public class MonHocController {

    private MonHocPanel      view;
    private MonHocDAO        monDAO;
    private DefaultTableModel tableModel;

    public MonHocController(MonHocPanel view, DefaultTableModel tableModel) {
        this.view       = view;
        this.monDAO     = new MonHocDAO();
        this.tableModel = tableModel;
    }

    // ==================== LOAD DỮ LIỆU ====================

    public void taiDuLieu() {
        tableModel.setRowCount(0);
        monDAO.layTatCa().forEach(mh -> tableModel.addRow(new Object[]{
            mh.getMaMH(), mh.getTenMH(), mh.getSoTinChi()
        }));
    }

    // ==================== THÊM ====================

    public boolean them(String maMH, String tenMH, String soTinChiStr) {
        if (maMH.isEmpty() || tenMH.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ các trường bắt buộc!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (monDAO.kiemTraTonTai(maMH)) {
            JOptionPane.showMessageDialog(view,
                "Mã môn học đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int soTinChi = parseSoTinChi(soTinChiStr);
        if (soTinChi < 1) return false;
        MonHoc mh = new MonHoc(maMH, tenMH, soTinChi);
        if (monDAO.them(mh)) {
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

    public boolean sua(String maMH, String tenMH, String soTinChiStr) {
        if (tenMH.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nhập đầy đủ các trường bắt buộc!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int soTinChi = parseSoTinChi(soTinChiStr);
        if (soTinChi < 1) return false;
        MonHoc mh = new MonHoc(maMH, tenMH, soTinChi);
        if (monDAO.sua(mh)) {
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

    public boolean xoa(String maMH) {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xóa môn " + maMH + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && monDAO.xoa(maMH)) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        }
        return false;
    }

    // ==================== HELPER ====================

    private int parseSoTinChi(String s) {
        try {
            int soTinChi = Integer.parseInt(s.trim());
            if (soTinChi < 1 || soTinChi > 4) {
                JOptionPane.showMessageDialog(view,
                    "Số tín chỉ phải là số nguyên từ 1 đến 4!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            return soTinChi;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                "Số tín chỉ phải là số nguyên từ 1 đến 4!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
}