package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "SPCT")
public class SPCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotBlank(message = "Mã không được để trống")
    @Column(name = "maSPCT")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "idKichThuoc")
    private KichThuoc idKichThuoc;
    @ManyToOne
    @JoinColumn(name = "idMauSac")
    private MauSac idMauSac;
    @ManyToOne
    @JoinColumn(name = "idSanPham")
    private SanPham idSanPham;
    @NotNull(message = "Số lượng không được trống")
    @Positive(message = "Số lượng phải là số dương")
    @Column(name = "soLuong")
    private Integer soLuong;
    @NotNull(message = "Đơn giá không được trống")
    @DecimalMin(value = "10", message = "Đơn giá phải lớn hơn 10")
    @Column(name = "donGia")
    private Double donGia;
    @NotNull(message = "Trạng thái không được để trống")
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
