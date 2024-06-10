package com.example.demo.controllers;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.SanPham;

import com.example.demo.repository.assignment2.NhanVienRepository;
import com.example.demo.securityConfig.AuthChecker;
import com.example.demo.securityConfig.ExcelConfig;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.validation.Valid;
import org.hibernate.Internal;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("nhan-vien")
public class NhanVienController {
    @Autowired
    private NhanVienRepository nhanVienRepository;

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

            Page<NhanVien> listNV = nhanVienRepository.findByTenMaLike(k, p);

            int totalPages = listNV.getTotalPages();

            model.addAttribute("listNV", listNV.getContent());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);

            model.addAttribute("listNV", listNV);
            return "nhan_vien/index";
        }
    }

    // Hàm này dùng để hiển thị view add
    @GetMapping("create")
    public String create(@ModelAttribute("data") NhanVien nv, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            return "nhan_vien/create";
        }
    }

    // Hàm này dùng để add
    @PostMapping("store")
    public String save(Model model, @Valid NhanVien nv, BindingResult validateResult ) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", nv);
            return "nhan_vien/create";
        }
        nhanVienRepository.save(nv);
        return "redirect:/nhan-vien/index";
    }

    // Hàm này dùng để hiển thị view edit
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") NhanVien nv, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
//          SanPham listSPDetail = this.sanPhamRepository.findById(id);
            model.addAttribute("listNVDetail", nv);
            return "nhan_vien/edit";

    }

    // Hàm này dùng để update
    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id,Model model, @Valid NhanVien nv, BindingResult validateResult ) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listNVDetail", nv);
            return "nhan_vien/edit";
        }
        nv.setId(id); // Ensure the ID is set correctly
        nhanVienRepository.save(nv);
        return "redirect:/nhan-vien/index";
    }

    // Hàm này dùng để delete
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        nhanVienRepository.deleteById(id);
        return "redirect:/nhan-vien/index";
    }

    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        List<NhanVien> listNV = nhanVienRepository.findAll(); // Lấy danh sách nhân viên

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=nhan_vien.xlsx");

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            Sheet sheet = workbook.createSheet("NhanVien");

            // Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "MaNV", "Tên nhân viên", "Tên đăng nhập", "Mật khẩu", "Trạng thái"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            // Điền dữ liệu vào các hàng
            int rowNum = 1;
            for (NhanVien nv : listNV) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nv.getId());
                row.createCell(1).setCellValue(nv.getMa());
                row.createCell(2).setCellValue(nv.getTen());
                row.createCell(3).setCellValue(nv.getTenDN());
                row.createCell(4).setCellValue(nv.getMatKhau());
                row.createCell(5).setCellValue(nv.getVaiTro());
                row.createCell(6).setCellValue(nv.getTrangThai());
            }
            workbook.write(out);
        }
    }

    @Autowired
    private ExcelConfig excelConfig;

    @PostMapping("import")
    public String importExcel(@RequestParam("file") MultipartFile file, Model model, HttpSession session) throws IOException {
        System.out.println("File ten" + file);
        boolean importResult = excelConfig.importExcelNV(file);
        if (importResult) {
            model.addAttribute("listNV", importResult);
            System.out.println("Import successful");
            return "redirect:/nhan-vien/index";
        } else {
            System.out.println("Import failed");
            model.addAttribute("errors", "File có id âm vui lòng chọn file mới");
        }
        return index(model, 1, 5, "", session);
    }

}
