package model;

import java.time.LocalDate;

/**
 * Model class đại diện cho Giảng Viên
 * Khớp với bảng tbl_giangvien trong database
 */
public class GiangVien {

    private String    maGV;
    private String    hoTen;
    private LocalDate ngaySinh;
    private String    gioiTinh;
    private String    khoaBoMon;
    private String    email;
    private String    sdt;

    // ==================== CONSTRUCTOR ====================

    public GiangVien() {}

    public GiangVien(String maGV, String hoTen, LocalDate ngaySinh,
                     String gioiTinh, String khoaBoMon, String email, String sdt) {
        this.maGV      = maGV;
        this.hoTen     = hoTen;
        this.ngaySinh  = ngaySinh;
        this.gioiTinh  = gioiTinh;
        this.khoaBoMon = khoaBoMon;
        this.email     = email;
        this.sdt       = sdt;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getMaGV()                  
    { 
        return maGV; 
    }
    public void setMaGV(String maGV)       
    { 
        this.maGV = maGV; 
    }

    public String getHoTen()                 
    { 
        return hoTen; 
    }
    public void setHoTen(String hoTen)     
    { 
        this.hoTen = hoTen; 
    }

    public LocalDate getNgaySinh()                       
    { 
        return ngaySinh; 
    }
    public void setNgaySinh(LocalDate ngaySinh)     
    { 
        this.ngaySinh = ngaySinh; 
    }

    public String getGioiTinh()                  
    { 
        return gioiTinh; 
    }
    public void setGioiTinh(String gioiTinh)  
    { 
        this.gioiTinh = gioiTinh; 
    }

    public String getKhoaBoMon()                     
    { 
        return khoaBoMon; 
    }
    public void setKhoaBoMon(String khoaBoMon)     
    { 
        this.khoaBoMon = khoaBoMon; 
    }

    public String getEmail()                 
    { 
        return email; 
    }
    public void setEmail(String email)     
    { 
        this.email = email; 
    }

    public String getSdt()               
    { 
        return sdt; 
    }
    public void setSdt(String sdt)     
    { 
        this.sdt = sdt; 
    }

    // ==================== METHODS ====================

    @Override
    public String toString() {
        return "[" + maGV + "] " + hoTen;
    }

    public String getInfo() {
        return "Mã GV: "      + maGV      + "\n" +
               "Họ tên: "     + hoTen     + "\n" +
               "Ngày sinh: "  + ngaySinh  + "\n" +
               "Giới tính: "  + gioiTinh  + "\n" +
               "Khoa/Bộ môn: "+ khoaBoMon + "\n" +
               "Email: "      + email     + "\n" +
               "SĐT: "        + sdt;
    }
}