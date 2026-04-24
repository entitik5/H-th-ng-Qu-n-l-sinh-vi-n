package dao;

import model.GiangVien;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class xử lý các thao tác CRUD cho bảng tbl_giangvien
 */
public class GiangVienDAO {

    private Connection conn;

    public GiangVienDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // ==================== CREATE ====================

    public boolean them(GiangVien gv) {
        String sql = "INSERT INTO tbl_giangvien (maGV, hoTen, ngaySinh, gioiTinh, khoaBoMon, email, sdt) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getMaGV());
            ps.setString(2, gv.getHoTen());
            ps.setDate  (3, gv.getNgaySinh() != null ? Date.valueOf(gv.getNgaySinh()) : null);
            ps.setString(4, gv.getGioiTinh());
            ps.setString(5, gv.getKhoaBoMon());
            ps.setString(6, gv.getEmail());
            ps.setString(7, gv.getSdt());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm giảng viên: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public List<GiangVien> layTatCa() {
        List<GiangVien> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_giangvien ORDER BY maGV";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) danhSach.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách GV: " + e.getMessage());
        }
        return danhSach;
    }

    public GiangVien layTheoMa(String maGV) {
        String sql = "SELECT * FROM tbl_giangvien WHERE maGV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Lỗi lấy GV theo mã: " + e.getMessage());
        }
        return null;
    }

    // ==================== UPDATE ====================

    public boolean sua(GiangVien gv) {
        String sql = "UPDATE tbl_giangvien SET hoTen=?, ngaySinh=?, gioiTinh=?, "
                   + "khoaBoMon=?, email=?, sdt=? WHERE maGV=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getHoTen());
            ps.setDate  (2, gv.getNgaySinh() != null ? Date.valueOf(gv.getNgaySinh()) : null);
            ps.setString(3, gv.getGioiTinh());
            ps.setString(4, gv.getKhoaBoMon());
            ps.setString(5, gv.getEmail());
            ps.setString(6, gv.getSdt());
            ps.setString(7, gv.getMaGV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi sửa giảng viên: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean xoa(String maGV) {
        String sql = "DELETE FROM tbl_giangvien WHERE maGV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa giảng viên: " + e.getMessage());
            return false;
        }
    }

    // ==================== HELPER ====================

    public boolean kiemTraTonTai(String maGV) {
        String sql = "SELECT COUNT(*) FROM tbl_giangvien WHERE maGV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra GV: " + e.getMessage());
        }
        return false;
    }

    private GiangVien mapRow(ResultSet rs) throws SQLException {
        GiangVien gv = new GiangVien();
        gv.setMaGV     (rs.getString("maGV"));
        gv.setHoTen    (rs.getString("hoTen"));
        gv.setGioiTinh (rs.getString("gioiTinh"));
        gv.setKhoaBoMon(rs.getString("khoaBoMon"));
        gv.setEmail    (rs.getString("email"));
        gv.setSdt      (rs.getString("sdt"));
        Date ngaySinh = rs.getDate("ngaySinh");
        if (ngaySinh != null) gv.setNgaySinh(ngaySinh.toLocalDate());
        return gv;
    }
}