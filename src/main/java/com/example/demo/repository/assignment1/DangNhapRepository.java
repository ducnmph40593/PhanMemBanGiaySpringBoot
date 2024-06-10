//package com.example.demo.repository.assignment1;
//
//import com.example.demo.entity.NhanVien;
//import com.example.demo.repository.assignment2.NhanVienRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//
//@Repository
//public class DangNhapRepository {
//    @Autowired
//    NhanVienRepository nhanVienRepository ;
//
//    public boolean validateLogin(NhanVien nv) {
//        for (NhanVien s : nhanVienRepository.findAll()) {
//            if (s.getTenDN().equalsIgnoreCase(nv.getTenDN()) && s.getMatKhau().equalsIgnoreCase(nv.getMatKhau())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//
//}
