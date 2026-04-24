package dao;

import model.DiemSo;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemSoDAO {

    private Connection conn;

    public DiemSoDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    // ==================== CREATE ====================

    public boolean them(DiemSo d) {
        String sql = "INSERT INTO tbl_diemso (maSV, maMH, diemSo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getMaSV());
            ps.setString(2, d.getMaMH());
            ps.setDouble(3, d.getDiemSo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm điểm: " + e.getMessage());
            return false;
        }
    }

    // ==================== READ ====================

    public List<DiemSo> layTatCa() {
        List<DiemSo> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_diemso ORDER BY maSV";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) danhSach.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy bảng điểm: " + e.getMessage());
        }
        return danhSach;
    }

    public List<DiemSo> layTheoSV(String maSV) {
        List<DiemSo> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM tbl_diemso WHERE maSV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) danhSach.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy điểm theo SV: " + e.getMessage());
        }
        return danhSach;
    }

    public double layDiemTrungBinh(String maSV) {
        String sql = "SELECT AVG(diemSo) FROM tbl_diemso WHERE maSV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Lỗi lấy điểm TB: " + e.getMessage());
        }
        return 0.0;
    }

    // ==================== UPDATE ====================

    public boolean sua(DiemSo d) {
        String sql = "UPDATE tbl_diemso SET diemSo=? WHERE maSV=? AND maMH=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, d.getDiemSo());
            ps.setString(2, d.getMaSV());
            ps.setString(3, d.getMaMH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi sửa điểm: " + e.getMessage());
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean xoa(int maDiem) {
        String sql = "DELETE FROM tbl_diemso WHERE maDiem = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDiem);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa điểm: " + e.getMessage());
            return false;
        }
    }

    // ==================== THỐNG KÊ ====================

    /**
     * Lấy danh sách sinh viên kèm điểm TB, lớp, khoa.
     * Lọc theo: tenKhoa (null = tất cả), tenLop (null = tất cả), hocLuc (null = tất cả), tuKhoa tìm kiếm.
     * Trả về: [maSV, hoTen, tenLop, tenKhoa, diemTB(String), hocLuc]
     */
    public List<Object[]> layDanhSachSinhVienDiem(
            String filterKhoa, String filterLop, String filterHocLuc, String tuKhoa) {

        List<Object[]> ds = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT sv.maSV, " +
            "       CONVERT(sv.hoTen  USING utf8mb4) COLLATE utf8mb4_unicode_ci AS hoTen, " +
            "       CONVERT(l.tenLop  USING utf8mb4) COLLATE utf8mb4_unicode_ci AS tenLop, " +
            "       CONVERT(k.tenKhoa USING utf8mb4) COLLATE utf8mb4_unicode_ci AS tenKhoa, " +
            "       AVG(d.diemSo) as diemTB " +
            "FROM tbl_diemso d " +
            "JOIN tbl_sinhvien sv ON CONVERT(sv.maSV USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                      = CONVERT(d.maSV  USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "LEFT JOIN tbl_lophoc l ON CONVERT(l.maLop  USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                        = CONVERT(sv.maLop USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "LEFT JOIN tbl_khoa k   ON CONVERT(k.maKhoa  USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                        = CONVERT(sv.maKhoa USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "GROUP BY sv.maSV, sv.hoTen, l.tenLop, k.tenKhoa "
        );

        // Lọc sau GROUP BY bằng HAVING hoặc WHERE phụ — dùng subquery để filter hocLuc
        // Wrap thành subquery để có thể filter theo hocLuc
        String inner = "SELECT * FROM (" + sql + ") AS tmp WHERE 1=1 ";
        List<Object> params = new ArrayList<>();

        if (filterKhoa != null && !filterKhoa.isEmpty()) {
            inner += "AND tenKhoa = ? ";
            params.add(filterKhoa);
        }
        if (filterLop != null && !filterLop.isEmpty()) {
            inner += "AND tenLop = ? ";
            params.add(filterLop);
        }
        if (tuKhoa != null && !tuKhoa.trim().isEmpty()) {
            inner += "AND (maSV LIKE ? OR hoTen LIKE ? OR tenLop LIKE ? OR tenKhoa LIKE ?) ";
            String p = "%" + tuKhoa.trim() + "%";
            params.add(p); params.add(p); params.add(p); params.add(p);
        }
        inner += "ORDER BY tenKhoa, tenLop, diemTB DESC";

        try (PreparedStatement ps = conn.prepareStatement(inner)) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double diemTB = rs.getDouble("diemTB");
                String hocLuc = model.DiemSo.tinhXepLoai(diemTB);
                // Nếu filter theo học lực thì bỏ qua hàng không khớp
                if (filterHocLuc != null && !filterHocLuc.isEmpty() && !filterHocLuc.equals(hocLuc)) continue;
                ds.add(new Object[]{
                    rs.getString("maSV"),
                    rs.getString("hoTen"),
                    rs.getString("tenLop")  != null ? rs.getString("tenLop")  : "",
                    rs.getString("tenKhoa") != null ? rs.getString("tenKhoa") : "",
                    String.format("%.2f", diemTB),
                    hocLuc
                });
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách sinh viên điểm: " + e.getMessage());
        }
        return ds;
    }

    /** Lấy danh sách tên khoa (có sinh viên có điểm) để populate combobox */
    public List<String> layDanhSachKhoa() {
        List<String> ds = new ArrayList<>();
        String sql =
            "SELECT DISTINCT CONVERT(k.tenKhoa USING utf8mb4) COLLATE utf8mb4_unicode_ci AS tenKhoa " +
            "FROM tbl_sinhvien sv " +
            "JOIN tbl_khoa k ON CONVERT(k.maKhoa USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                 = CONVERT(sv.maKhoa USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "JOIN tbl_diemso d ON CONVERT(d.maSV USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                   = CONVERT(sv.maSV USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "ORDER BY tenKhoa";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) ds.add(rs.getString("tenKhoa"));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách khoa: " + e.getMessage());
        }
        return ds;
    }

    /** Lấy danh sách tên lớp (có sinh viên có điểm) để populate combobox */
    public List<String> layDanhSachLop() {
        List<String> ds = new ArrayList<>();
        String sql =
            "SELECT DISTINCT CONVERT(l.tenLop USING utf8mb4) COLLATE utf8mb4_unicode_ci AS tenLop " +
            "FROM tbl_sinhvien sv " +
            "JOIN tbl_lophoc l ON CONVERT(l.maLop  USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                   = CONVERT(sv.maLop USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "JOIN tbl_diemso d ON CONVERT(d.maSV USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "                   = CONVERT(sv.maSV USING utf8mb4) COLLATE utf8mb4_unicode_ci " +
            "ORDER BY tenLop";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) ds.add(rs.getString("tenLop"));
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách lớp: " + e.getMessage());
        }
        return ds;
    }

    // ==================== HELPER ====================

    private DiemSo mapRow(ResultSet rs) throws SQLException {
        DiemSo d = new DiemSo();
        d.setMaDiem(rs.getInt("maDiem"));
        d.setMaSV  (rs.getString("maSV"));
        d.setMaMH  (rs.getString("maMH"));
        d.setDiemSo(rs.getDouble("diemSo"));
        return d;
    }
}