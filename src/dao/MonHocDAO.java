package dao;

import model.MonHoc;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class xử lý các thao tác CRUD cho bảng tbl_monhoc
 */
public class MonHocDAO {

    private Connection conn;

    public MonHocDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // ==================== CREATE ====================

    public boolean them(MonHoc mh) {
        String sql = "INSERT INTO tbl_monhoc (maMH, tenMH, soTinChi) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mh.getMaMH());
            ps.setString(2, mh.getTenMH());
            ps.setInt   (3, mh.getSoTinChi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm môn học: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public List<MonHoc> layTatCa() {
        List<MonHoc> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_monhoc ORDER BY maMH";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) danhSach.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách môn học: " + e.getMessage());
        }
        return danhSach;
    }

    public MonHoc layTheoMa(String maMH) {
        String sql = "SELECT * FROM tbl_monhoc WHERE maMH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Lỗi lấy môn học theo mã: " + e.getMessage());
        }
        return null;
    }

    // ==================== UPDATE ====================

    public boolean sua(MonHoc mh) {
        String sql = "UPDATE tbl_monhoc SET tenMH=?, soTinChi=? WHERE maMH=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mh.getTenMH());
            ps.setInt   (2, mh.getSoTinChi());
            ps.setString(3, mh.getMaMH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi sửa môn học: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean xoa(String maMH) {
        String sql = "DELETE FROM tbl_monhoc WHERE maMH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa môn học: " + e.getMessage());
            return false;
        }
    }

    // ==================== HELPER ====================

    public boolean kiemTraTonTai(String maMH) {
        String sql = "SELECT COUNT(*) FROM tbl_monhoc WHERE maMH = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra môn học: " + e.getMessage());
        }
        return false;
    }

    private MonHoc mapRow(ResultSet rs) throws SQLException {
        return new MonHoc(
            rs.getString("maMH"),
            rs.getString("tenMH"),
            rs.getInt("soTinChi")
        );
    }
}