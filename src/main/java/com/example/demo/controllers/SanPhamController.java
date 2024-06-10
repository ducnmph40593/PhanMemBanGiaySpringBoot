package com.example.demo.controllers;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.SanPham;


import com.example.demo.repository.assignment2.SanPhamRepository;
import com.example.demo.securityConfig.AuthChecker;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("san-pham")
public class SanPhamController {
    @Autowired
    private SanPhamRepository sanPhamRepository;

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
            Page<SanPham> listSP = sanPhamRepository.findByTenMaLike(k, p);

            int totalPages = listSP.getTotalPages();

            model.addAttribute("listSP", listSP.getContent());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);

            model.addAttribute("listSP", listSP);

            return "san_pham/index";
        }
    }

    // Hàm này dùng để hiển thị view add
    @GetMapping("create")
    public String create(@ModelAttribute("data") SanPham sp, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            return "san_pham/create";
        }
    }

    // Hàm này dùng để add
    @PostMapping("store")
    public String save(Model model, @Valid SanPham sanPham, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", sanPham);
            return "san_pham/create";
        }
        sanPhamRepository.save(sanPham);
        return "redirect:/san-pham/index";
    }

    // Hàm này dùng để hiển thị view edit
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") SanPham sp, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            model.addAttribute("sanPhamDetail", sp);
            return "san_pham/edit";
        }
    }

    // Hàm này dùng để update
    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model, @Valid SanPham sp, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("sanPhamDetail", sp);
            return "san_pham/edit";
        }
        sp.setId(id); // Ensure the ID is set correctly
        sanPhamRepository.save(sp);
        return "redirect:/san-pham/index";
    }

    // Hàm này dùng để delete
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        sanPhamRepository.deleteById(id);
        return "redirect:/san-pham/index";
    }

/// ---------------------------------GD 1 -----------------------------
//    @GetMapping("index")
//    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
//                           @RequestParam(defaultValue = "5") int size,
//                           @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        } else {
//            listSP = timKiem.isEmpty() ? sanPhamRepository.findAllPaging(page, size)
//                    : this.sanPhamRepository.findByTimKiem(timKiem);
//            if (listSP.isEmpty()) {
//                model.addAttribute("error", "Bảng trống");
//            } else {
//                model.addAttribute("listSP", listSP);
//            }
//
//            model.addAttribute("currentPage", page);
//            model.addAttribute("pageSize", size);
//            model.addAttribute("totalPages", (int) Math.ceil((double) sanPhamRepository.findAll().size() / size));
//            return "san_pham/index";
//        }
//    }
//
//    @GetMapping("delete/{id}")
//    public String delete(@PathVariable("id") Integer id) {
//        this.sanPhamRepository.deleteById(id);
//        return "redirect:/san-pham/index";
//    }
//
//    @GetMapping("create")
//    public String create(@ModelAttribute("data") SanPham sp, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        return "san_pham/create";
//    }
//
//    @PostMapping("store")
//    public String store(Model model, @Valid SanPham sp, BindingResult validateResult) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("data", sp);
//            return "san_pham/create";
//        }
//        sanPhamRepository.create(sp);
//        return "redirect:/san-pham/index";
//    }
//
//    @GetMapping("edit/{id}")
//    public String edit(@PathVariable("id") Integer id, Model model, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        SanPham listSPDetail = this.sanPhamRepository.findById(id);
//        model.addAttribute("listSPDetail", listSPDetail);
//        return "/san_pham/edit";
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, @Valid SanPham sp, BindingResult validateResult, Model model) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("listSPDetail", sp);
//            return "/san_pham/edit";
//        }
//        sp.setId(id);
//        sanPhamRepository.update(sp);
//        return "redirect:/san-pham/index";
//    }

}
