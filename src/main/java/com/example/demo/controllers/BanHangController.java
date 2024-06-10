package com.example.demo.controllers;

import com.example.demo.entity.*;

import com.example.demo.repository.assignment2.*;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("ban-hang")
public class BanHangController {
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private HDCT_Repository hdctRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private KichThuocRepository kichThuocRepository;
    @Autowired
    private MauSacRepository mauSacRepository;
    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SPCT_Repository spct_repository;
    HoaDon hoaDonDetail;
    KhachHang khachHangDetail;
    Integer idHoaDon;
    double tongTien;
    List<HDCT> listHDCTL;

    public BanHangController() {
        listHDCTL = new ArrayList<>();
        hoaDonDetail = new HoaDon();
        khachHangDetail = new KhachHang();
        tongTien = 0;
    }

    @GetMapping("index")
    public String index(Model model, HttpSession session,
                        @RequestParam(name = "sdtKH", defaultValue = "") String sdtKH, KhachHang kh) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            if (sdtKH.isEmpty()) {
                model.addAttribute("listHDCT", listHDCTL);
                model.addAttribute("listHD", hoaDonRepository.findAllByTrangThai(1));
                model.addAttribute("listSPCT", spct_repository.findAllSPCTWithDetails());
                model.addAttribute("listKH", khachHangRepository.findAll());
                model.addAttribute("listNV", nhanVienRepository.findAll());
                model.addAttribute("listMS", mauSacRepository.findAll());
                model.addAttribute("listKT", kichThuocRepository.findAll());
                model.addAttribute("listSP", sanPhamRepository.findAll());
                model.addAttribute("hoaDonDetail", hoaDonDetail);
                model.addAttribute("khachHangDetail", khachHangDetail);
                model.addAttribute("tongTien", tongTien);
            } else {
                String sdt = sdtKH.trim();
                khachHangDetail = khachHangRepository.findBySdtLike(sdt);
                if (khachHangDetail == null) {
                    model.addAttribute("errorMessageSDT", "Đây là 1 khách hàng lẻ: ");
                    model.addAttribute("listHDCT", listHDCTL);
                    model.addAttribute("listHD", hoaDonRepository.findAllByTrangThai(1));
                    model.addAttribute("listSPCT", spct_repository.findAll());
                    model.addAttribute("listKH", khachHangRepository.findAll());
                    model.addAttribute("listNV", nhanVienRepository.findAll());
                    model.addAttribute("listMS", mauSacRepository.findAll());
                    model.addAttribute("listKT", kichThuocRepository.findAll());
                    model.addAttribute("listSP", sanPhamRepository.findAll());
                    model.addAttribute("hoaDonDetail", hoaDonDetail);
                    model.addAttribute("khachHangDetail", khachHangDetail);
                    model.addAttribute("tongTien", tongTien);
                    model.addAttribute("data", kh);
                } else {
                    model.addAttribute("listHDCT", listHDCTL);
                    model.addAttribute("listHD", hoaDonRepository.findAllByTrangThai(1));
                    model.addAttribute("listSPCT", spct_repository.findAll());
                    model.addAttribute("listKH", khachHangRepository.findAll());
                    model.addAttribute("listNV", nhanVienRepository.findAll());
                    model.addAttribute("listMS", mauSacRepository.findAll());
                    model.addAttribute("listKT", kichThuocRepository.findAll());
                    model.addAttribute("listSP", sanPhamRepository.findAll());
                    model.addAttribute("hoaDonDetail", hoaDonDetail);
                    model.addAttribute("khachHangDetail", khachHangDetail);
                    model.addAttribute("tongTien", tongTien);
                }
            }
            return "ban_hang/index";
        }
    }

    @PostMapping("tao-hoa-don/{id}")
    public String taoHoaDon(@PathVariable("id") Integer idNV,
                            HoaDon hoaDon) {

        NhanVien nv = new NhanVien();
        nv.setId(idNV);

        KhachHang kh = new KhachHang();
        kh.setId(khachHangDetail.getId());

        System.out.println("ID nhan vien" + idNV);

        // Thiết lập các thuộc tính cho hóa đơn
        HoaDon hd = new HoaDon();
        hd.setIdKH(kh);
        hd.setTrangThai(1);
        hd.setNgayMuaHang(LocalDateTime.now());
        hd.setIdNV(nv);


        hoaDonRepository.save(hd);
        return "redirect:/ban-hang/index";
    }

    private void updateTongTien() {
        tongTien = 0;
        for (HDCT hoaDonCT : listHDCTL) {
            tongTien += hoaDonCT.getSoLuong() * hoaDonCT.getDonGia();
        }
    }

    @GetMapping("deleteHDCT/{id}")
    public String deleteHDCT(@PathVariable("id") Integer id) {
        // Xóa HDCT từ cơ sở dữ liệu
        Optional<HDCT> deletedHDCTOptonal = hdctRepository.findById(id);
        HDCT deleteHDCT = deletedHDCTOptonal.get();

        hdctRepository.deleteById(id);

        // Cập nhật số lượng của SPCT liên quan
        SPCT chiTietSanPhamDetail = spct_repository.findByTheoId(deleteHDCT.getIdSPCT().getId());

        chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() + deleteHDCT.getSoLuong());
        spct_repository.save(chiTietSanPhamDetail);

        // Cập nhật danh sách chi tiết hoá đơn và tổng tiền, sau đó chuyển hướng về trang index

        listHDCTL = hdctRepository.findByIdHoaDonLike(idHoaDon);
        updateTongTien();
        return "redirect:/ban-hang/index";
    }

    @PostMapping("updateHDCT/{id}")
    public String updateSoluongHDCT(@PathVariable("id") Integer id, @RequestParam("soLuong") Integer soLuong, HttpSession session) {

        Optional<HDCT> updateHDCTOptonal = hdctRepository.findById(id);
        if (updateHDCTOptonal.isEmpty()) {
            session.setAttribute("err", "Không tìm thấy HDCT với ID: " + id);
            return "redirect:/ban-hang/index";
        }

        HDCT updateHDCT = updateHDCTOptonal.get();
        SPCT chiTietSanPhamDetail = spct_repository.findByTheoId(updateHDCT.getIdSPCT().getId());

        if (soLuong > chiTietSanPhamDetail.getSoLuong()) {
            session.setAttribute("errorSL", "Số lượng nhập lớn hơn số lượng tồn! Vui lòng nhập lại.");
            return "redirect:/ban-hang/index";
        }

        if (soLuong <= 0) {
            session.setAttribute("errorSL", "Số lượng nhập phải lớn hơn 0! Vui lòng nhập lại.");
            return "redirect:/ban-hang/index";
        }

        int soLuongHienTai = updateHDCT.getSoLuong();
        updateHDCT.setSoLuong(soLuong);
        hdctRepository.save(updateHDCT);

        if (soLuong > soLuongHienTai) {
            chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - (soLuong - soLuongHienTai));
        } else {
            chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() + (soLuongHienTai - soLuong));
        }
        spct_repository.save(chiTietSanPhamDetail);

        listHDCTL = hdctRepository.findByIdHoaDonLike(idHoaDon);
        updateTongTien();
        return "redirect:/ban-hang/index";
    }

    @GetMapping("selectHD/{id}")
    public String selectHD(@PathVariable("id") HoaDon hoaDonDetail1, Model model) {
        hoaDonDetail = hoaDonDetail1;

        idHoaDon = hoaDonDetail1.getId();
        System.out.println("idHoa Don" + idHoaDon);
        khachHangDetail = khachHangRepository.findByTheoId(hoaDonDetail1.getIdKH().getId());

        listHDCTL = hdctRepository.findByIdHoaDonLike(hoaDonDetail1.getId());

        updateTongTien();
        return "redirect:/ban-hang/index";
    }

    @PostMapping("selectSPCT/{id}")
    public String selectSPCT(@PathVariable("id") Integer id, @RequestParam("soLuong") Integer soLuong, HttpSession session) {
        Optional<SPCT> chiTietSanPhamDetailOptional = spct_repository.findById(id);

        if (chiTietSanPhamDetailOptional.isEmpty()) {
            session.setAttribute("errorSPCT", "Sản phẩm không tồn tại! Vui lòng thử lại.");
            return "redirect:/ban-hang/index";
        }

        SPCT chiTietSanPhamDetail = chiTietSanPhamDetailOptional.get();

        if (soLuong > chiTietSanPhamDetail.getSoLuong()) {
            session.setAttribute("errorSoLuong", "Số lượng nhập lớn hơn số lượng tồn! Vui lòng nhập lại.");
            return "redirect:/ban-hang/index";
        }

        if (soLuong <= 0) {
            session.setAttribute("errorSoLuong", "Số lượng nhập phải lớn hơn 0! Vui lòng nhập lại.");
            return "redirect:/ban-hang/index";
        }

        boolean daCoTrongHoaDon = false;

        for (HDCT hoaDonCT : listHDCTL) {
            if (hoaDonCT.getIdSPCT().getId().equals(id)) {
                // Nếu đã có trong hoá đơn, cập nhật số lượng và tổng tiền
                hoaDonCT.setSoLuong(hoaDonCT.getSoLuong() + soLuong);

                // Cập nhật số lượng sản phẩm trong kho
                chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - soLuong);
                spct_repository.save(chiTietSanPhamDetail);

                // Cập nhật thông tin chi tiết hoá đơn
                hdctRepository.save(hoaDonCT);
                daCoTrongHoaDon = true;
                break;
            }
        }
        // Nếu sản phẩm chưa có trong hoá đơn, thêm mới vào
        if (!daCoTrongHoaDon) {
            HoaDon hoaDonDetail = new HoaDon();
            SPCT spctDetail = new SPCT();
            spctDetail.setId(chiTietSanPhamDetail.getId());
            hoaDonDetail.setId(idHoaDon);
            HDCT hoaDonChiTiet = new HDCT();
            hoaDonChiTiet.setIdHD(hoaDonDetail);
            hoaDonChiTiet.setIdSPCT(spctDetail);
            hoaDonChiTiet.setDonGia(chiTietSanPhamDetail.getDonGia());
            hoaDonChiTiet.setSoLuong(soLuong);
            hoaDonChiTiet.setTrangThai(1);

            // Thêm mới chi tiết hoá đơn vào cơ sở dữ liệu
            hdctRepository.save(hoaDonChiTiet);

            // Cập nhật số lượng sản phẩm trong kho
            chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - soLuong);
            spct_repository.save(chiTietSanPhamDetail);
        }
        listHDCTL = hdctRepository.findByIdHoaDonLike(idHoaDon);
        updateTongTien();
        return "redirect:/ban-hang/index";
    }

    @PostMapping("thanh-toan/{id}")
    public String thanhToan(@PathVariable("id") Integer idNV, @RequestParam("idHoaDon") Integer idHoaDon, Model model) {
        HoaDon hoaDon = hoaDonRepository.findByTheoIdHD(idHoaDon);

        List<HDCT> listHDCT = hdctRepository.findByIdHoaDonLike(idHoaDon);

        if (hoaDon != null) {
            hoaDon.setTrangThai(0);
            hoaDonRepository.save(hoaDon);
            if (listHDCT != null) {
                for (HDCT hdct : listHDCT) {
                    hdct.setTrangThai(0);
                    hdctRepository.save(hdct);
                }
            }
        }
        return "redirect:/ban-hang/index";
    }


//    HoaDonRepository hoaDonRepository = new HoaDonRepository();
//    List<HoaDon> listHD;
//
//    HDCT_Repository hdctRepository = new HDCT_Repository();
//    List<HDCT> listHDCT;
//
//    KhachHangRepository khachHangRepository = new KhachHangRepository();
//    List<KhachHang> listKH;
//
//    KichThuocRepository kichThuocRepository = new KichThuocRepository();
//    List<KichThuoc> listKT;
//
//    MauSacRepository mauSacRepository = new MauSacRepository();
//    List<MauSac> listMS;
//
//    NhanVienRepository nhanVienRepository = new NhanVienRepository();
//    List<NhanVien> listNV = new ArrayList<>();
//
////    SanPhamRepository sanPhamRepository = new SanPhamRepository();
//    List<SanPham> listSP;
//
//    SPCT_Repository spct_repository = new SPCT_Repository();
//    List<SPCT> listSPCT;
//
//    HoaDon hoaDonDetail;
//    KhachHang khachHangDetail;
//    Integer idHoaDon;
//    double tongTien;
//    List<HDCT> listHDCTL;
//
//
//    public BanHangController() {
//        listSPCT = new ArrayList<>();
//        listSP = new ArrayList<>();
//        listMS = new ArrayList<>();
//        listKH = new ArrayList<>();
//        listKT = new ArrayList<>();
//        listHDCT = new ArrayList<>();
//        listHD = new ArrayList<>();
//
//        listHDCTL = new ArrayList<>();
//        hoaDonDetail = new HoaDon();
//        khachHangDetail = new KhachHang();
//        tongTien = 0;
//    }
//
//    @GetMapping("index")
//    public String getIndex(Model model, HttpSession session, @RequestParam(name = "sdtKH", defaultValue = "") String sdtKH) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        if (sdtKH.isEmpty()) {
//            listNV = this.nhanVienRepository.findAll();
//            model.addAttribute("listNV", listNV);
//            listKH = this.khachHangRepository.findAll();
//            model.addAttribute("listKH", listKH);
//            listHD = this.hoaDonRepository.findAll();
//            model.addAttribute("listHD", listHD);
//            listHDCT = this.hdctRepository.findAll();
//            model.addAttribute("listHDCT", listHDCT);
//            listKT = this.kichThuocRepository.findAll();
//            model.addAttribute("listKT", listKT);
//            listMS = this.mauSacRepository.findAll();
//            model.addAttribute("listMS", listMS);
////            listSP = this.sanPhamRepository.findAll();
//            model.addAttribute("listSP", listSP);
//            listSPCT = this.spct_repository.findAll();
//            model.addAttribute("listSPCT", listSPCT);
//
//            model.addAttribute("hoaDonDetail", hoaDonDetail);
//            model.addAttribute("khachHangDetail", khachHangDetail);
//            model.addAttribute("tongTien", tongTien);
//            model.addAttribute("listHDCT", listHDCTL);
//        } else {
//            KhachHang listKHDetail = khachHangRepository.findBySDTkh(sdtKH);
//            model.addAttribute("listKHDetail", listKHDetail);
//        }
//        return "ban_hang/index";
//    }
//
//    @GetMapping("search")
//    public String search(Model model, @RequestParam(name = "sdtKH", defaultValue = "") String sdtKH) {
//        KhachHang khachHang = khachHangRepository.findBySDTkh(sdtKH);
//        model.addAttribute("listKHDetail", khachHang);
//        model.addAttribute("listNV", nhanVienRepository.findAll());
//        model.addAttribute("listKH", khachHangRepository.findAll());
//        model.addAttribute("listHD", hoaDonRepository.findAll());
//        model.addAttribute("listHDCT", hdctRepository.findAll());
//        model.addAttribute("listKT", kichThuocRepository.findAll());
//        model.addAttribute("listMS", mauSacRepository.findAll());
////        model.addAttribute("listSP", sanPhamRepository.findAll());
//        model.addAttribute("listSPCT", spct_repository.findAll());
//        return "ban_hang/index";
//    }
//
//
//    @PostMapping("tao-hoa-don/{id}")
//    public String taoHoaDon(@PathVariable("id") Integer idNV, @RequestParam(name = "tenKH", defaultValue = "") String tenKH, HoaDon hoaDon) {
//        KhachHang khachHang = khachHangRepository.findBytenKH(tenKH);
//        hoaDon.setIdKH(khachHang.getId());
//        hoaDon.setTrangThai(1);
//        hoaDon.setNgayMuaHang(LocalDate.now());
//        hoaDon.setIdNV(idNV);
//        hoaDonRepository.create(hoaDon);
//        return "redirect:/ban-hang/index";
//    }
//
//
//    @GetMapping("deleteHDCT/{id}")
//    public String deleteHDCT(@PathVariable("id") Integer idHDCT) {
//        // Xóa HDCT từ cơ sở dữ liệu
//        HDCT deletedHDCT = hdctRepository.findById(idHDCT);
//        hdctRepository.deleteById(idHDCT);
//
//        // Cập nhật số lượng của SPCT liên quan
//        SPCT chiTietSanPhamDetail = spct_repository.findById(deletedHDCT.getIdSPCT());
//        chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() + deletedHDCT.getSoLuong());
//        spct_repository.update(chiTietSanPhamDetail);
//
//        // Cập nhật danh sách chi tiết hoá đơn và tổng tiền, sau đó chuyển hướng về trang index
//        listHDCTL = hdctRepository.findByIdHd(idHoaDon);
//        updateTongTien();
//        return "redirect:/ban-hang/index";
//    }
//
//    @GetMapping("selectHD/{id}")
//    public String selectHD(@PathVariable("id") Integer id) {
//        idHoaDon = id;
//        hoaDonDetail = hoaDonRepository.findById(id);
//        khachHangDetail = khachHangRepository.findById(hoaDonDetail.getIdKH());
//        listHDCTL = hdctRepository.findByIdHd(id);
//        updateTongTien();
//        return "redirect:/ban-hang/index";
//    }
//
//    @PostMapping("selectSPCT/{id}")
//    public String selectSPCT(@PathVariable("id") Integer id, @RequestParam("soLuong") Integer soLuong, HttpSession session) {
//        SPCT chiTietSanPhamDetail = spct_repository.findById(id);
//
//        if (soLuong > chiTietSanPhamDetail.getSoLuong()) {
//            session.setAttribute("errorSoLuong", "Số lượng nhập lớn hơn số lượng tồn! Vui lòng nhập lại.");
//            return "redirect:/ban-hang/index";
//        }
//
//        // Kiểm tra xem sản phẩm đã có trong hoá đơn chưa
//        boolean daCoTrongHoaDon = false;
//
//        for (HDCT hoaDonCT : listHDCTL) {
//            if (hoaDonCT.getIdSPCT().equals(id)) {
//                // Nếu đã có trong hoá đơn, cập nhật số lượng và tổng tiền
//                hoaDonCT.setSoLuong(hoaDonCT.getSoLuong() + soLuong);
//                hoaDonCT.setTongTien(soLuong * hoaDonCT.getDonGia());
//
//                // Cập nhật số lượng sản phẩm trong kho
//                chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - soLuong);
//                spct_repository.update(chiTietSanPhamDetail);
//
//                // Cập nhật thông tin chi tiết hoá đơn
//                hdctRepository.updateSoLuong(hoaDonCT);
//                daCoTrongHoaDon = true;
//                break;
//            }
//        }
//
//        // Nếu sản phẩm chưa có trong hoá đơn, thêm mới vào
//        if (!daCoTrongHoaDon) {
//            HDCT hoaDonChiTiet = new HDCT();
//            hoaDonChiTiet.setIdHD(idHoaDon);
//            hoaDonChiTiet.setIdSPCT(chiTietSanPhamDetail.getId());
//            hoaDonChiTiet.setDonGia(chiTietSanPhamDetail.getDonGia());
//            hoaDonChiTiet.setSoLuong(soLuong);
//            hoaDonChiTiet.setTrangThai(1);
//            hoaDonChiTiet.setTongTien(soLuong * chiTietSanPhamDetail.getDonGia());
//
//            // Thêm mới chi tiết hoá đơn vào cơ sở dữ liệu
//            hdctRepository.create(hoaDonChiTiet);
//
//            // Cập nhật số lượng sản phẩm trong kho
//            chiTietSanPhamDetail.setSoLuong(chiTietSanPhamDetail.getSoLuong() - soLuong);
//            spct_repository.update(chiTietSanPhamDetail);
//        }
//
//        // Cập nhật danh sách chi tiết hoá đơn và tổng tiền, sau đó chuyển hướng về trang index
//        listHDCTL = hdctRepository.findByIdHd(idHoaDon);
//        updateTongTien();
//        return "redirect:/ban-hang/index";
//    }
//
//
//    @PostMapping("thanh-toan/{id}")
//    public String thanhToan(@PathVariable("id") Integer idNV, @RequestParam("idHoaDon") Integer idHoaDon, Model model) {
//        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon);
//        List<HDCT> listHDCT = hdctRepository.findByIdHd(idHoaDon);
//
//        if (hoaDon != null) {
//            hoaDon.setTrangThai(0);
//            hoaDonRepository.update(hoaDon);
//            if (listHDCT != null) {
//                for (HDCT hdct : listHDCT) {
//                    hdct.setTrangThai(0);
//                    hdctRepository.update(hdct);
//                }
//            }
//        }
//        return "redirect:/ban-hang/index";
//    }
//
//
//    private void updateTongTien() {
//        tongTien = listHDCTL.stream().mapToDouble(HDCT::getTongTien).sum();
//    }
}
