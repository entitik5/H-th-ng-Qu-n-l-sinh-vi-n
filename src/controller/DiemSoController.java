package controller;

import dao.DiemSoDAO;
import model.DiemSo;
import view.DiemSoPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Xử lí logic Nhập Điểm số

public class DiemSoController {

    private DiemSoPanel      view;
    private DiemSoDAO        diemDAO;
    private DefaultTableModel tableModel;

    public DiemSoController(DiemSoPanel view, DefaultTableModel tableModel) {
        this.view       = view;
        this.diemDAO    = new DiemSoDAO();
        this.tableModel = tableModel;
    }

    // ==================== LOAD DỮ LIỆU ======================

    public void taiDuLieu() {
        tableModel.setRowCount(0);
        diemDAO.layTatCa().forEach(d -> tableModel.addRow(new Object[]{
            d.getMaDiem(), d.getMaSV(), d.getMaMH(),
            d.getDiemSo(), d.getXepLoai()
        }));
    }

    // ==================== NHẬP ĐIỂM =====================

    public boolean nhapDiem(String maSV, String maMH, String diemStr) {
        if (maSV == null || maMH == null || diemStr == null ||
            maSV.trim().isEmpty() || maMH.trim().isEmpty() || diemStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập đầy đủ thông tin!",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        double diem;
        try {
            diem = Double.parseDouble(diemStr);
            if (diem < 0 || diem > 10) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view,
                "Điểm không hợp lệ! Nhập từ 0.0 đến 10.0",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        DiemSo d = new DiemSo(maSV, maMH, diem);
        if (diemDAO.them(d)) {
            JOptionPane.showMessageDialog(view,
                "Nhập điểm thành công!\nXếp loại: " + d.getXepLoai(),
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        } else {
            JOptionPane.showMessageDialog(view,
                "Nhập điểm thất bại!\n(Có thể sinh viên đã có điểm môn này)",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== XÓA ĐIỂM ====================

    public boolean xoaDiem(int maDiem) {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xóa điểm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION && diemDAO.xoa(maDiem)) {
            JOptionPane.showMessageDialog(view, "Xóa thành công!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        }
        return false;
    }
}