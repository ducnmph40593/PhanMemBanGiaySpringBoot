package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotBlank(message = "Mã không được để trống")
    @Column(name = "manv")
    private String ma;
    @NotBlank(message = "Tên nhân viên không được để trống")
    @Column(name = "ten")
    private String ten;
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Column(name = "tendangnhap")
    private String tenDN;
    @NotBlank(message = "Mật khẩu không được để trống")
    @Column(name = "matkhau")
    private String matKhau;
    @Column(name = "vaiTro")
    private String vaiTro;
    @Column(name = "trangThai")
    private Integer trangThai;

    public String hienThiTT() {
        if (trangThai == 0) {
            return "Hoạt động";
        } else if (trangThai == 1) {
            return "Ngừng hoạt động";
        }
        return null;
    }

}
