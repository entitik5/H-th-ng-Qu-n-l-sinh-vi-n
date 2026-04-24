package dao;

import model.Khoa;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class xử lý các thao tác CRUD cho bảng tbl_khoa
 */
public class KhoaDAO {

    private Connection conn;

    public KhoaDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // ==================== CREATE ====================

    public boolean them(Khoa khoa) {
        String sql = "INSERT INTO tbl_khoa (maKhoa, tenKhoa, moTa) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khoa.getMaKhoa());
            ps.setString(2, khoa.getTenKhoa());
            ps.setString(3, khoa.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm khoa: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public List<Khoa> layTatCa() {
        List<Khoa> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_khoa ORDER BY maKhoa";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                danhSach.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách khoa: " + e.getMessage());
        }
        return danhSach;
    }

    public Khoa layTheoMa(String maKhoa) {
        String sql = "SELECT * FROM tbl_khoa WHERE maKhoa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhoa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Lỗi lấy khoa theo mã: " + e.getMessage());
        }
        return null;
    }

    public List<Khoa> timKiem(String tuKhoa) {
        List<Khoa> ketQua = new ArrayList<>();
        String sql = "SELECT * FROM tbl_khoa WHERE tenKhoa LIKE ? OR maKhoa LIKE ? ORDER BY maKhoa";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String pattern = "%" + tuKhoa + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ketQua.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm khoa: " + e.getMessage());
        }
        return ketQua;
    }

    // ==================== UPDATE ====================

    public boolean sua(Khoa khoa) {
        String sql = "UPDATE tbl_khoa SET tenKhoa=?, moTa=? WHERE maKhoa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khoa.getTenKhoa());
            ps.setString(2, khoa.getMoTa());
            ps.setString(3, khoa.getMaKhoa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi sửa khoa: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean xoa(String maKhoa) {
        String sql = "DELETE FROM tbl_khoa WHERE maKhoa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhoa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa khoa: " + e.getMessage());
            return false;
        }
    }

    // ==================== HELPER ====================

    public boolean kiemTraTonTai(String maKhoa) {
        String sql = "SELECT COUNT(*) FROM tbl_khoa WHERE maKhoa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhoa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra khoa: " + e.getMessage());
        }
        return false;
    }

    private Khoa mapRow(ResultSet rs) throws SQLException {
        return new Khoa(
            rs.getString("maKhoa"),
            rs.getString("tenKhoa"),
            rs.getString("moTa")
        );
    }
}