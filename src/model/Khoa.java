package model;

/**
 * Model class đại diện cho Khoa
 * Khớp với bảng tbl_khoa trong database
 */
public class Khoa {

    private String maKhoa;
    private String tenKhoa;
    private String moTa;

    // ==================== CONSTRUCTOR ====================

    public Khoa() {}

    public Khoa(String maKhoa, String tenKhoa, String moTa) {
        this.maKhoa  = maKhoa;
        this.tenKhoa = tenKhoa;
        this.moTa    = moTa;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getMaKhoa()
    { return maKhoa; }

    public void setMaKhoa(String maKhoa)
    { this.maKhoa = maKhoa; }

    public String getTenKhoa()
    { return tenKhoa; }

    public void setTenKhoa(String tenKhoa)
    { this.tenKhoa = tenKhoa; }

    public String getMoTa()
    { return moTa; }

    public void setMoTa(String moTa)
    { this.moTa = moTa; }

    // ==================== METHODS ====================

    @Override
    public String toString() {
        return "[" + maKhoa + "] " + tenKhoa;
    }

    public String getInfo() {
        return "Mã khoa: "  + maKhoa  + "\n" +
               "Tên khoa: " + tenKhoa + "\n" +
               "Mô tả: "    + moTa;
    }
}