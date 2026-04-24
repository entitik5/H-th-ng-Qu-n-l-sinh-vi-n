package dao;

import model.SinhVien;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class xử lý các thao tác CRUD cho bảng tbl_sinhvien
 */
public class SinhVienDAO {

    private Connection conn;

    public SinhVienDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // ==================== CREATE ====================

    public boolean them(SinhVien sv) {
        String sql = "INSERT INTO tbl_sinhvien (maSV, hoTen, ngaySinh, gioiTinh, maLop, maKhoa, email, sdt) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSV());
            ps.setString(2, sv.getHoTen());
            ps.setDate  (3, Date.valueOf(sv.getNgaySinh()));
            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getMaLop());
            ps.setString(6, sv.getMaKhoa());
            ps.setString(7, sv.getEmail());
            ps.setString(8, sv.getSdt());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm sinh viên: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public List<SinhVien> layTatCa() {
        List<SinhVien> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_sinhvien ORDER BY maSV";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                danhSach.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách SV: " + e.getMessage());
        }
        return danhSach;
    }

    public SinhVien layTheoMa(String maSV) {
        String sql = "SELECT * FROM tbl_sinhvien WHERE maSV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Lỗi lấy SV theo mã: " + e.getMessage());
        }
        return null;
    }

    public List<SinhVien> layTheoLop(String maLop) {
        List<SinhVien> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_sinhvien WHERE maLop = ? ORDER BY hoTen";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) danhSach.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy SV theo lớp: " + e.getMessage());
        }
        return danhSach;
    }

    public List<SinhVien> timKiem(String tuKhoa) {
        List<SinhVien> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM tbl_sinhvien "
                   + "WHERE hoTen LIKE ? OR maSV LIKE ? ORDER BY maSV";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + tuKhoa + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ketQua.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm SV: " + e.getMessage());
        }
        return ketQua;
    }

    // ==================== UPDATE ====================

    public boolean sua(SinhVien sv) {
        String sql = "UPDATE tbl_sinhvien SET hoTen=?, ngaySinh=?, gioiTinh=?, "
                   + "maLop=?, maKhoa=?, email=?, sdt=? WHERE maSV=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getHoTen());
            ps.setDate  (2, Date.valueOf(sv.getNgaySinh()));
            ps.setString(3, sv.getGioiTinh());
            ps.setString(4, sv.getMaLop());
            ps.setString(5, sv.getMaKhoa());
            ps.setString(6, sv.getEmail());
            ps.setString(7, sv.getSdt());
            ps.setString(8, sv.getMaSV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi sửa sinh viên: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean xoa(String maSV) {
        String sql = "DELETE FROM tbl_sinhvien WHERE maSV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa sinh viên: " + e.getMessage());
            return false;
        }
    }

    // ==================== HELPER ====================

    public boolean kiemTraTonTai(String maSV) {
        String sql = "SELECT COUNT(*) FROM tbl_sinhvien WHERE maSV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra SV: " + e.getMessage());
        }
        return false;
    }

    /**
     * Map một hàng ResultSet thành object SinhVien
     */
    private SinhVien mapRow(ResultSet rs) throws SQLException {
        return new SinhVien(
            rs.getString("maSV"),
            rs.getString("hoTen"),
            rs.getDate("ngaySinh").toLocalDate(),
            rs.getString("gioiTinh"),
            rs.getString("maLop"),
            rs.getString("maKhoa"),
            rs.getString("email"),
            rs.getString("sdt")
        );
    }
}