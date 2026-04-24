package model;

import java.time.LocalDate;

public class SinhVien {

    private String    maSV;
    private String    hoTen;
    private LocalDate ngaySinh;
    private String    gioiTinh;
    private String    maLop;
    private String    maKhoa;   // THÊM MỚI
    private String    email;
    private String    sdt;

    // ==================== CONSTRUCTOR ====================

    public SinhVien() {}

    public SinhVien(String maSV, String hoTen, LocalDate ngaySinh,
                    String gioiTinh, String maLop, String maKhoa,
                    String email, String sdt)
    {
        this.maSV     = maSV;
        this.hoTen    = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.maLop    = maLop;
        this.maKhoa   = maKhoa;
        this.email    = email;
        this.sdt      = sdt;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getMaSV()
    { return maSV; }

    public void setMaSV(String maSV)
    { this.maSV = maSV; }

    public String getHoTen()
    { return hoTen; }

    public void setHoTen(String hoTen)
    { this.hoTen = hoTen; }

    public LocalDate getNgaySinh()
    { return ngaySinh; }

    public void setNgaySinh(LocalDate ngaySinh)
    { this.ngaySinh = ngaySinh; }

    public String getGioiTinh()
    { return gioiTinh; }

    public void setGioiTinh(String gioiTinh)
    { this.gioiTinh = gioiTinh; }

    public String getMaLop()
    { return maLop; }

    public void setMaLop(String maLop)
    { this.maLop = maLop; }

    public String getMaKhoa()
    { return maKhoa; }

    public void setMaKhoa(String maKhoa)
    { this.maKhoa = maKhoa; }

    public String getEmail()
    { return email; }

    public void setEmail(String email)
    { this.email = email; }

    public String getSdt()
    { return sdt; }

    public void setSdt(String sdt)
    { this.sdt = sdt; }

    // ==================== METHODS ====================

    @Override
    public String toString() {
        return "[" + maSV + "] " + hoTen + " - " + maLop;
    }

    public String getInfo() {
        return "Mã SV: "     + maSV     + "\n" +
               "Họ tên: "    + hoTen    + "\n" +
               "Ngày sinh: " + ngaySinh + "\n" +
               "Giới tính: " + gioiTinh + "\n" +
               "Lớp: "       + maLop    + "\n" +
               "Khoa: "      + maKhoa   + "\n" +
               "Email: "     + email    + "\n" +
               "SĐT: "       + sdt;
    }
}