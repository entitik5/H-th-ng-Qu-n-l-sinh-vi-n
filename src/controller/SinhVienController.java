package controller;

import dao.SinhVienDAO;
import dao.LopHocDAO;
import dao.KhoaDAO;
import model.SinhVien;
import model.LopHoc;
import model.Khoa;
import view.SinhVienPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller xử lý logic nghiệp vụ cho màn hình Quản lý Sinh viên
 * Nhận sự kiện từ SinhVienPanel, gọi SinhVienDAO để thao tác DB
 */
public class SinhVienController {

    private SinhVienPanel     view;
    private SinhVienDAO       svDAO;
    private LopHocDAO         lopDAO;
    private KhoaDAO           khoaDAO;
    private DefaultTableModel tableModel;

    public SinhVienController(SinhVienPanel view, DefaultTableModel tableModel) {
        this.view       = view;
        this.tableModel = tableModel;
        this.svDAO      = new SinhVienDAO();
        this.lopDAO     = new LopHocDAO();
        this.khoaDAO    = new KhoaDAO();
    }

    // ==================== LOAD DỮ LIỆU ====================

    public void taiDuLieu() {
        tableModel.setRowCount(0);
        List<SinhVien> danhSach = svDAO.layTatCa();
        java.time.format.DateTimeFormatter fmt =
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (SinhVien sv : danhSach) {
            tableModel.addRow(new Object[]{
                sv.getMaSV(), sv.getHoTen(),
                sv.getNgaySinh().format(fmt),
                sv.getGioiTinh(), sv.getMaLop(),
                sv.getMaKhoa(),
                sv.getEmail(), sv.getSdt()
            });
        }
    }

    public List<LopHoc> layDanhSachLop() {
        return lopDAO.layTatCa();
    }

    public List<Khoa> layDanhSachKhoa() {
        return khoaDAO.layTatCa();
    }

    // ==================== THÊM ====================

    public boolean them(String maSV, String hoTen, String ngaySinhStr,
                        String gioiTinh, String maLop, String maKhoa,
                        String email, String sdt) {
        // Validate
        if (maSV.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập đầy đủ các trường bắt buộc (*)",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (svDAO.kiemTraTonTai(maSV)) {
            JOptionPane.showMessageDialog(view,
                "Mã SV đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        LocalDate ngaySinh = parseNgaySinh(ngaySinhStr);
        if (ngaySinh == null) return false;

        if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(view,
                "Email không hợp lệ! Ví dụ: abc@gmail.com",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validateSdt(sdt)) {
            JOptionPane.showMessageDialog(view,
                "Số điện thoại không hợp lệ!\nPhải là số VN 10 chữ số, bắt đầu 03/05/07/08/09",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        SinhVien sv = new SinhVien(maSV, hoTen, ngaySinh, gioiTinh, maLop, maKhoa, email, sdt);
        if (svDAO.them(sv)) {
            JOptionPane.showMessageDialog(view,
                "Thêm sinh viên thành công!", "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
            taiDuLieu();
            return true;
        } else {
            JOptionPane.showMessageDialog(view,
                "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // ==================== SỬA ====================

    public boolean sua(String maSV, String hoTen, String ngaySinhStr,
                       String gioiTinh, String maLop, String maKhoa,
                       String email, String sdt) {
        if (hoTen.isEmpty() || ngaySinhStr.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Vui lòng nhập đầy đủ các trường bắt buộc (*)",
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        LocalDate ngaySinh = parseNgaySinh(ngaySinhStr);
        if (ngaySinh == null) return false;

        if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(view,
                "Email không hợp lệ! Ví dụ: abc@gmail.com",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validateSdt(sdt)) {
            JOptionPane.showMessageDialog(view,
                "Số điện thoại không hợp lệ!\nPhải là số VN 10 chữ số, bắt đầu 03/05/07/08/09",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        SinhVien sv = new SinhVien(maSV, hoTen, ngaySinh, gioiTinh, maLop, maKhoa, email, sdt);
        if (svDAO.sua(sv)) {
            JOptionPane.showMessageDialog(view,
                "Cập nhật thành công!", "Thành công",
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

    public boolean xoa(String maSV, String hoTen) {
        int confirm = JOptionPane.showConfirmDialog(view,
            "Xóa sinh viên: " + hoTen + "?", "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (svDAO.xoa(maSV)) {
                JOptionPane.showMessageDialog(view,
                    "Xóa thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                taiDuLieu();
                return true;
            } else {
                JOptionPane.showMessageDialog(view,
                    "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    // ==================== TÌM KIẾM ====================

    public void timKiem(String tuKhoa) {
        if (tuKhoa.isEmpty()) { taiDuLieu(); return; }
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        svDAO.timKiem(tuKhoa).forEach(sv -> tableModel.addRow(new Object[]{
            sv.getMaSV(), sv.getHoTen(),
            sv.getNgaySinh().format(fmt),
            sv.getGioiTinh(), sv.getMaLop(),
            sv.getMaKhoa(),
            sv.getEmail(), sv.getSdt()
        }));
    }

    // ==================== HELPER ====================

    private LocalDate parseNgaySinh(String ngaySinhStr) {
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(ngaySinhStr, fmt);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view,
                "Ngày sinh không hợp lệ!\nĐịnh dạng đúng: dd-MM-yyyy (ví dụ: 15-03-2004)",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private boolean validateEmail(String email) {
        if (email == null || email.isEmpty())
            return true;
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean validateSdt(String sdt) {
        if (sdt == null || sdt.isEmpty())
            return true;
        return sdt.matches("^(0[3|5|7|8|9])[0-9]{8}$");
    }
}