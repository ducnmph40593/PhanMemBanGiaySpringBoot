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
@Table(name = "HDCT")
public class HDCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idHoaDon")
    private HoaDon idHD;

    @ManyToOne
    @JoinColumn(name = "idSPCT")
    private SPCT idSPCT;

    @NotNull(message = "So luong khong duoc bo trong")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column(name = "soLuong")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được bỏ trống")
    @Min(value = 1, message = "Đơn giá phải lớn hơn 0")
    @Column(name = "donGia")
    private Double donGia;

//    private Double tongTien;
    @Column(name = "trangThai")
    private Integer trangThai;

    public String hienThiTT() {
        if (trangThai == 0) {
            return "Đã thanh toán";
        } else if (trangThai == 1) {
            return "Chưa thanh toán";
        }
        return null;
    }
}
