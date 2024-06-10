package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idNhanVien")
    private NhanVien idNV;

    @ManyToOne
    @JoinColumn(name = "idKhachHang")
    private KhachHang idKH;

    @NotNull(message = "Vui lòng nhập ngày mua hàng")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngayMuaHang")
    private LocalDateTime ngayMuaHang;

    @Column(name = "trangThai")
    private Integer trangThai;

    public String hienThiTT() {
        if (trangThai == 0) {
            return "Đã thanh toán";
        } else if (trangThai == 1) {
            return "Chưa thanh toán";
        }
        return "Trạng thái không xác định";
    }
}
