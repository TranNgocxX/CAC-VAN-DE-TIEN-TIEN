package BTL.models;

import java.sql.Timestamp;

public class SanPham {
    private int id;
    private String tenSanPham;
    private String moTa;
    private double gia;
    private int soLuong;
    private String anhUrl;
    private int idLoai;
    private Timestamp ngayTao;
    private Timestamp ngayCapNhat;
    
    public SanPham() {}; 


    public int getId() { 
    	return id; 
    }
    public void setId(int id) { 
    	this.id = id; 
    }

    public String getTenSanPham() { 
    	return tenSanPham; 
    }
    public void setTenSanPham(String tenSanPham) { 
    	this.tenSanPham = tenSanPham; 
    }

    public String getMoTa() { 
    	return moTa; 
    }
    
    public void setMoTa(String moTa) { 
    	this.moTa = moTa;
    }

    public double getGia() { 
    	return gia; 
    }
    
    public void setGia(double gia) { 
    	this.gia = gia; 
    }

    public int getSoLuong() { 
    	return soLuong; 
    }
    
    public void setSoLuong(int soLuong) { 
    	this.soLuong = soLuong; 
    }

    public String getAnhUrl() { 
    	return anhUrl; 
    }
    
    public void setAnhUrl(String anhUrl) { 
    	this.anhUrl = anhUrl; 
    }

    public int getIdLoai() { 
    	return idLoai; 
    }
    
    public void setIdLoai(int idLoai) { 
    	this.idLoai = idLoai; 
    }

    public Timestamp getNgayTao() { 
    	return ngayTao; 
    }
    
    public void setNgayTao(Timestamp ngayTao) { 
    	this.ngayTao = ngayTao; 
    }

    public Timestamp getNgayCapNhat() { 
    	return ngayCapNhat; 
    }
    
    public void setNgayCapNhat(Timestamp ngayCapNhat) { 
    	this.ngayCapNhat = ngayCapNhat; 
    }
}