package com.example.demo.controllers;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.SanPham;

import com.example.demo.repository.assignment2.KhachHangRepository;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNo,
                        @RequestParam(name = "limit", defaultValue = "5") int pageSize,
                        @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            PageRequest p = PageRequest.of(pageNo - 1, pageSize);
            String k = "%" + timKiem + "%";
            Page<KhachHang> listKH = khachHangRepository.findByTenMaLike(k, p);

            int totalPages = listKH.getTotalPages();

            model.addAttribute("listKH", listKH.getContent());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);

            model.addAttribute("listKH", listKH);
            return "khach_hang/index";
        }
    }

    // Hàm này dùng để hiển thị view add
    @GetMapping("create")
    public String create(@ModelAttribute("data") KhachHang nv, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            return "khach_hang/create";
        }
    }

    // Hàm này dùng để add
    @PostMapping("store")
    public String save(Model model, @Valid KhachHang kh, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", kh);
            return "khach_hang/create";
        }
        khachHangRepository.save(kh);
        return "redirect:/khach-hang/index";
    }

    // Hàm này dùng để hiển thị view edit
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") KhachHang kh, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            model.addAttribute("listKHDetail", kh);
            return "khach_hang/edit";
        }
    }

    // Hàm này dùng để update
    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model, @Valid KhachHang kh, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listKHDetail", kh);
            return "khach_hang/edit";
        }
        kh.setId(id); // Ensure the ID is set correctly
        khachHangRepository.save(kh);
        return "redirect:/khach-hang/index";
    }

    // Hàm này dùng để delete
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        khachHangRepository.deleteById(id);
        return "redirect:/khach-hang/index";
    }

//    KhachHangRepository khachHangRepository = new KhachHangRepository();
//    List<KhachHang> listKH = new ArrayList<>();
//
//    @GetMapping("index")
//    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
//                           @RequestParam(defaultValue = "5") int size,
//                           @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        } else {
//            listKH = timKiem.isEmpty() ? khachHangRepository.findAllPaging(page, size)
//                    : this.khachHangRepository.findByTimKiem(timKiem);
//            if (listKH.isEmpty()) {
//                model.addAttribute("error", "Bảng trống");
//            } else {
//                model.addAttribute("listKH", listKH);
//            }
//
//            model.addAttribute("currentPage", page);
//            model.addAttribute("pageSize", size);
//            model.addAttribute("totalPages", (int) Math.ceil((double) khachHangRepository.findAll().size() / size));
//            return "khach_hang/index";
//        }
//    }
//
//    @GetMapping("delete/{id}")
//    public String delete(@PathVariable("id") Integer id) {
//        this.khachHangRepository.deleteById(id);
//        return "redirect:/khach-hang/index";
//    }
//
//    @GetMapping("create")
//    public String create(@ModelAttribute("data") KhachHang kh, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        return "khach_hang/create";
//    }
//
//    @PostMapping("store")
//    public String store(Model model, @Valid KhachHang kh, BindingResult validateResult) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("data", kh);
//            return "khach_hang/create";
//        }
//        khachHangRepository.create(kh);
//        return "redirect:/khach-hang/index";
//    }
//
//    @GetMapping("edit/{id}")
//    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        KhachHang listKHDetail = this.khachHangRepository.findById(id);
//        model.addAttribute("listKHDetail", listKHDetail);
//        return "khach_hang/edit";
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, @Valid KhachHang kh, BindingResult validateResult, Model model) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("listKHDetail", kh);
//            return "khach_hang/edit";
//        }
//        kh.setId(id);
//        khachHangRepository.update(kh);
//        return "redirect:/khach-hang/index";
//    }
//
//    @GetMapping("export")
//    public void export(HttpServletResponse response) throws IOException {
//        List<KhachHang> listKH = khachHangRepository.findAll();
//
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment; filename=khach_hang.xlsx");
//
//        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
//            Sheet sheet = workbook.createSheet("Khách hàng");
//
//            // Tạo hàng tiêu đề
//            Row headerRow = sheet.createRow(0);
//            String[] headers = {"ID", "Mã khách hàng", "Tên khách hàng", "SDT", "Trạng thái"};
//            for (int i = 0; i < headers.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//            }
//            // Điền dữ liệu vào các hàng
//            int rowNum = 1;
//            for (KhachHang nv : listKH) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(nv.getId());
//                row.createCell(1).setCellValue(nv.getMa());
//                row.createCell(2).setCellValue(nv.getTen());
//                row.createCell(3).setCellValue(nv.getSdt());
//                row.createCell(4).setCellValue(nv.getTrangThai());
//            }
//
//            workbook.write(out);
//        }
//    }
}
