package com.example.demo.controllers;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.assignment2.KichThuocRepository;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("kich-thuoc")
public class KichThuocController {
    @Autowired
    private KichThuocRepository kichThuocRepository;

    @GetMapping("index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") int pageNo,
                        @RequestParam(name = "limit", defaultValue = "5") int pageSize,
                        @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            PageRequest p = PageRequest.of(pageNo - 1, pageSize);
            String k = "%" + timKiem + "%";
            Page<KichThuoc> listKT = kichThuocRepository.findByTenMaLike(k, p);

            int totalPages = listKT.getTotalPages();

            model.addAttribute("listKT", listKT.getContent());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);

            model.addAttribute("listKT", listKT);
            return "kich_thuoc/index";
        }
    }

    // Hàm này dùng để hiển thị view add
    @GetMapping("create")
    public String create(@ModelAttribute("data") KichThuoc kt, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            return "kich_thuoc/create";
        }
    }

    // Hàm này dùng để add
    @PostMapping("store")
    public String save(Model model, @Valid KichThuoc kt, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", kt);
            return "kich_thuoc/create";
        }
        kichThuocRepository.save(kt);
        return "redirect:/kich-thuoc/index";
    }

    // Hàm này dùng để hiển thị view edit
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") KichThuoc kt, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            model.addAttribute("listKTDetail", kt);
            return "kich_thuoc/edit";
        }
    }

    // Hàm này dùng để update
    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model, @Valid KichThuoc kt, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listKTDetail", kt);
            return "kich_thuoc/edit";
        }
        kt.setId(id); // Ensure the ID is set correctly
        kichThuocRepository.save(kt);
        return "redirect:/kich-thuoc/index";
    }

    // Hàm này dùng để delete
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        kichThuocRepository.deleteById(id);
        return "redirect:/kich-thuoc/index";
    }


//    KichThuocRepository kichThuocRepository = new KichThuocRepository();
//    List<KichThuoc> listKT = new ArrayList<>();
//
//    @GetMapping("index")
//    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
//                           @RequestParam(defaultValue = "5") int size,
//                           @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        } else {
//            listKT = timKiem.isEmpty() ? kichThuocRepository.findAllPaging(page, size)
//                    : this.kichThuocRepository.findByTimKiem(timKiem);
//            if (listKT.isEmpty()) {
//                model.addAttribute("error", "Bảng trống");
//            } else {
//                model.addAttribute("listKT", listKT);
//            }
//
//            model.addAttribute("currentPage", page);
//            model.addAttribute("pageSize", size);
//            model.addAttribute("totalPages", (int) Math.ceil((double) kichThuocRepository.findAll().size() / size));
//            return "kich_thuoc/index";
//        }
//    }
//
//
//    @GetMapping("delete/{id}")
//    public String delete(@PathVariable("id") Integer id) {
//        this.kichThuocRepository.deleteById(id);
//        return "redirect:/kich-thuoc/index";
//    }
//
//    @GetMapping("create")
//    public String create(@ModelAttribute("data") KichThuoc kt, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        return "kich_thuoc/create";
//    }
//
//    @PostMapping("store")
//    public String store(Model model, @Valid KichThuoc kt, BindingResult validateResult) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("data", kt);
//            return "kich_thuoc/create";
//        }
//        kichThuocRepository.create(kt);
//        return "redirect:/kich-thuoc/index";
//    }
//
//    @GetMapping("edit/{id}")
//    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        KichThuoc listKTDetail = this.kichThuocRepository.findById(id);
//        model.addAttribute("listKTDetail", listKTDetail);
//        return "kich_thuoc/edit";
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, @Valid KichThuoc kt, BindingResult validateResult, Model model) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("listKTDetail", kt);
//            return "kich_thuoc/edit";
//        }
//        kt.setId(id);
//        kichThuocRepository.update(kt);
//        return "redirect:/kich-thuoc/index";
//    }
}
