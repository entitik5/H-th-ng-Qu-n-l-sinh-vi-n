package dao;

import model.TaiKhoan;
import util.DBConnection;
import util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {

    private Connection conn;

    public TaiKhoanDAO() {
        this.conn = DBConnection.getInstance().getConnection();
        if (this.conn == null) {
            throw new RuntimeException("Không thể kết nối database!");
        }
    }

    public TaiKhoan dangNhap(String tenDN, String matKhau) {
        String sql = "SELECT * FROM tbl_taikhoan WHERE tenDN = ? AND matKhau = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDN);
            ps.setString(2, PasswordUtil.hash(matKhau));  // hash trước khi so sánh
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                    rs.getString("tenDN"),
                    rs.getString("matKhau"),
                    rs.getString("vaiTro"),
                    rs.getString("maSV"),
                    rs.getString("maGV")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi đăng nhập: " + e.getMessage());
        }
        return null;
    }

    public boolean doiMatKhau(String tenDN, String matKhauCu, String matKhauMoi) {
        TaiKhoan tk = dangNhap(tenDN, matKhauCu);
        if (tk == null) return false;

        String sql = "UPDATE tbl_taikhoan SET matKhau = ? WHERE tenDN = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, PasswordUtil.hash(matKhauMoi));  // hash trước khi lưu
            ps.setString(2, tenDN);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi đổi mật khẩu: " + e.getMessage());
            return false;
        }
    }

    public boolean them(TaiKhoan tk) {
        String sql = "INSERT INTO tbl_taikhoan (tenDN, matKhau, vaiTro, maSV, maGV) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getTenDN());
            ps.setString(2, PasswordUtil.hash(tk.getMatKhau()));
            ps.setString(3, tk.getVaiTro());
            ps.setString(4, tk.getMaSV());
            ps.setString(5, tk.getMaGV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm tài khoản: " + e.getMessage());
            return false;
        }
    }

    public List<TaiKhoan> layTatCa() {
        List<TaiKhoan> danhSach = new ArrayList<>();
        String sql = "SELECT tenDN, vaiTro, maSV, maGV FROM tbl_taikhoan ORDER BY vaiTro, tenDN";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                danhSach.add(new TaiKhoan(
                    rs.getString("tenDN"), "",
                    rs.getString("vaiTro"),
                    rs.getString("maSV"),
                    rs.getString("maGV")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách tài khoản: " + e.getMessage());
        }
        return danhSach;
    }

    public boolean xoa(String tenDN) {
        String sql = "DELETE FROM tbl_taikhoan WHERE tenDN = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDN);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi xóa tài khoản: " + e.getMessage());
            return false;
        }
    }

    public boolean kiemTraTonTai(String tenDN) {
        String sql = "SELECT COUNT(*) FROM tbl_taikhoan WHERE tenDN = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDN);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra tài khoản: " + e.getMessage());
        }
        return false;
    }
}