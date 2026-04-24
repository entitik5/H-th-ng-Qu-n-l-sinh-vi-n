package model;

/**
 * Model class đại diện cho Lớp Học
 * Khớp với bảng tbl_lophoc trong database
 */
public class LopHoc {

    private String maLop;
    private String tenLop;
    private String khoaHoc;
    private int    siSo;

    // ==================== CONSTRUCTOR ====================

    public LopHoc() {}

    public LopHoc(String maLop, String tenLop, String khoaHoc, int siSo) {
        this.maLop    = maLop;
        this.tenLop   = tenLop;
        this.khoaHoc  = khoaHoc;
        this.siSo     = siSo;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getMaLop()              
    { 
        return maLop; 
    }
    public void setMaLop(String maLop)  
    { 
        this.maLop = maLop; 
    }

    public String getTenLop()               
    { 
        return tenLop; 
    }
    public void setTenLop(String tenLop)  
    { 
        this.tenLop = tenLop; 
    }

    public String getKhoaHoc()                  
    { 
        return khoaHoc; 
    }
    public void setKhoaHoc(String khoaHoc)    
    { 
        this.khoaHoc = khoaHoc; 
    }

    public int getSiSo()           
    { 
        return siSo; 
    }
    public void setSiSo(int siSo)   
    { 
        this.siSo = siSo; 
    }

    // ==================== METHODS ====================

    @Override
    public String toString() {
        return tenLop;
    }

    public String getInfo() {
        return "Mã lớp: "   + maLop   + "\n" +
               "Tên lớp: "  + tenLop  + "\n" +
               "Khóa học: " + khoaHoc + "\n" +
               "Sĩ số: "    + siSo;
    }
}