package model;

/**
 * Model class đại diện cho Môn Học
 * Khớp với bảng tbl_monhoc trong database
 */
public class MonHoc {

    private String maMH;
    private String tenMH;
    private int    soTinChi;

    // ==================== CONSTRUCTOR ====================

    public MonHoc() {}

    public MonHoc(String maMH, String tenMH, int soTinChi) {
        this.maMH      = maMH;
        this.tenMH     = tenMH;
        this.soTinChi  = soTinChi;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getMaMH()             
    { 
        return maMH; 
    }
    public void setMaMH(String maMH)  
    { 
        this.maMH = maMH; 
    }

    public String getTenMH()              
    { 
        return tenMH; 
    }
    public void setTenMH(String tenMH)  
    { 
        this.tenMH = tenMH; 
    }

    public int getSoTinChi()               
    { 
        return soTinChi; 
    }
    public void setSoTinChi(int soTinChi)   
    { 
        this.soTinChi = soTinChi; 
    }

    // ==================== METHODS ====================

    @Override
    public String toString() 
    {
        return tenMH;
    }

    public String getInfo() {
        return "Mã MH: "      + maMH     + "\n" +
               "Tên MH: "     + tenMH    + "\n" +
               "Số tín chỉ: " + soTinChi;
    }
}