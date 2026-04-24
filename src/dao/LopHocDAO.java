package dao;

import model.LopHoc;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class xử lý các thao tác CRUD cho bảng tbl_lophoc
 */
public class LopHocDAO {

    private Connection conn;

    public LopHocDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // ==================== CREATE ====================

    public boolean them(LopHoc lop) {
        String sql = "INSERT INTO tbl_lophoc (maLop, tenLop, khoaHoc, siSo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lop.getMaLop());
            ps.setString(2, lop.getTenLop());
            ps.setString(3, lop.getKhoaHoc());
            ps.setInt   (4, lop.getSiSo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm lớp: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public List<LopHoc> layTatCa() {
        List<LopHoc> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_lophoc ORDER BY maLop";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) danhSach.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách lớp: " + e.getMessage());
        }
        return danhSach;
    }

    public LopHoc layTheoMa(String maLop) {
        String sql = "SELECT * FROM tbl_lophoc WHERE maLop = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Lỗi lấy lớp theo mã: " + e.getMessage());
        }
        return null;
    }

    // ==================== UPDATE ====================

    public boolean sua(LopHoc lop) {
        String sql = "UPDATE tbl_lophoc SET tenLop=?, khoaHoc=?, siSo=? WHERE maLop=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lop.getTenLop());
            ps.setString(2, lop.getKhoaHoc());
            ps.setInt   (3, lop.getSiSo());
            ps.setString(4, lop.getMaLop());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi sửa lớp: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean xoa(String maLop) {
        String sql = "DELETE FROM tbl_lophoc WHERE maLop = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLop);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa lớp: " + e.getMessage());
            return false;
        }
    }

    // ==================== HELPER ====================

    public boolean kiemTraTonTai(String maLop) {
        String sql = "SELECT COUNT(*) FROM tbl_lophoc WHERE maLop = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra lớp: " + e.getMessage());
        }
        return false;
    }

    private LopHoc mapRow(ResultSet rs) throws SQLException {
        return new LopHoc(
            rs.getString("maLop"),
            rs.getString("tenLop"),
            rs.getString("khoaHoc"),
            rs.getInt("siSo")
        );
    }
}