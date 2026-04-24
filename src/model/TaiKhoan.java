package model;

/**
 * Model class đại diện cho Tài Khoản đăng nhập
 * Khớp với bảng tbl_taikhoan trong database
 * 3 vai trò: admin, giangvien, sinhvien
 */
public class TaiKhoan {

    private String tenDN;
    private String matKhau;
    private String vaiTro;
    private String maSV;
    private String maGV;

    // ==================== CONSTRUCTOR ====================

    public TaiKhoan() {}

    public TaiKhoan(String tenDN, String matKhau, String vaiTro) {
        this.tenDN   = tenDN;
        this.matKhau = matKhau;
        this.vaiTro  = vaiTro;
    }

    public TaiKhoan(String tenDN, String matKhau, String vaiTro, String maSV, String maGV) {
        this.tenDN   = tenDN;
        this.matKhau = matKhau;
        this.vaiTro  = vaiTro;
        this.maSV    = maSV;
        this.maGV    = maGV;
    }

    // ==================== GETTERS & SETTERS ====================

    public String getTenDN()              
    { 
        return tenDN; 
    }
    public void   setTenDN(String tenDN)  
    { 
        this.tenDN = tenDN; 
    }

    public String getMatKhau()                
    { 
        return matKhau; 
    }
    public void   setMatKhau(String matKhau)  
    { 
        this.matKhau = matKhau; 
    }

    public String getVaiTro()               
    { 
        return vaiTro; 
    }
    public void   setVaiTro(String vaiTro)  
    { 
        this.vaiTro = vaiTro; 
    }

    public String getMaSV()             
    { 
        return maSV; 
    }
    public void   setMaSV(String maSV)  
    { 
        this.maSV = maSV; 
    }

    public String getMaGV()             
    { 
        return maGV; 
    }
    public void   setMaGV(String maGV)  
    { 
        this.maGV = maGV; 
    }

    // ==================== METHODS ====================

    public boolean isAdmin()      
    { 
        return "admin".equalsIgnoreCase(vaiTro); 
    }
    public boolean isGiangVien()  
    { 
        return "giangvien".equalsIgnoreCase(vaiTro); 
    }
    public boolean isSinhVien()   
    { 
        return "sinhvien".equalsIgnoreCase(vaiTro); 
    }

    public boolean xacThuc(String matKhauNhap) 
    {
        return this.matKhau.equals(matKhauNhap);
    }

    public String getTenVaiTro() {
        return switch (vaiTro.toLowerCase()) {
            case "admin"      -> "Quản trị viên";
            case "giangvien"  -> "Giảng viên";
            case "sinhvien"   -> "Sinh viên";
            default           -> vaiTro;
        };
    }

    @Override
    public String toString() {
        return tenDN + " (" + getTenVaiTro() + ")";
    }
}