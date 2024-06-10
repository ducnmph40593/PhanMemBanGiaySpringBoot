package com.example.demo.controllers;

import com.example.demo.entity.KichThuoc;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.assignment2.MauSacRepository;

import com.example.demo.repository.assignment2.SanPhamRepository;
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
@RequestMapping("mau-sac")
public class MauSacController {
    @Autowired
    private MauSacRepository mauSacRepository;

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
            Page<MauSac> listMS = mauSacRepository.findByTenMaLike(k, p);

            int totalPages = listMS.getTotalPages();

            model.addAttribute("listMS", listMS.getContent());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);

            model.addAttribute("listMS", listMS);
            return "mau_sac/index";
        }
    }

    // Hàm này dùng để hiển thị view add
    @GetMapping("create")
    public String create(@ModelAttribute("data") MauSac ms, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            return "mau_sac/create";
        }
    }

    // Hàm này dùng để add
    @PostMapping("store")
    public String save(Model model, @Valid MauSac ms, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("data", ms);
            return "mau_sac/create";
        }
        mauSacRepository.save(ms);
        return "redirect:/mau-sac/index";
    }

    // Hàm này dùng để hiển thị view edit
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") MauSac ms, HttpSession session) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        } else {
            model.addAttribute("listMSDetail", ms);
            return "mau_sac/edit";
        }
    }

    // Hàm này dùng để update
    @PostMapping("update/{id}")
    public String update(@PathVariable("id") Integer id, Model model, @Valid MauSac ms, BindingResult validateResult) {
        if (validateResult.hasErrors()) {
            List<FieldError> listError = validateResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : listError) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("errors", errors);
            model.addAttribute("listMSDetail", ms);
            return "mau_sac/edit";
        }
        ms.setId(id); // Ensure the ID is set correctly
        mauSacRepository.save(ms);
        return "redirect:/mau-sac/index";
    }

    // Hàm này dùng để delete
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        mauSacRepository.deleteById(id);
        return "redirect:/mau-sac/index";
    }

//    MauSacRepository mauSacRepository = new MauSacRepository();
//    List<MauSac> listMS = new ArrayList<>();
//
//    @GetMapping("index")
//    public String getIndex(Model model, @RequestParam(defaultValue = "1") int page,
//                           @RequestParam(defaultValue = "5") int size,
//                           @RequestParam(name = "timKiem", defaultValue = "") String timKiem, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        } else {
//            listMS = timKiem.isEmpty() ? mauSacRepository.findAllPaging(page, size)
//                    : this.mauSacRepository.findByTimKiem(timKiem);
//            if (listMS.isEmpty()) {
//                model.addAttribute("error", "Bảng trống");
//            } else {
//                model.addAttribute("listMS", listMS);
//            }
//
//            model.addAttribute("currentPage", page);
//            model.addAttribute("pageSize", size);
//            model.addAttribute("totalPages", (int) Math.ceil((double) mauSacRepository.findAll().size() / size));
//            return "mau_sac/index";
//        }
//    }
//
//    @GetMapping("delete/{id}")
//    public String delete(@PathVariable("id") Integer id) {
//        this.mauSacRepository.deleteById(id);
//        return "redirect:/mau-sac/index";
//    }
//
//    @GetMapping("create")
//    public String create(@ModelAttribute("data") MauSac ms, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        return "mau_sac/create";
//    }
//
//    @PostMapping("store")
//    public String store(Model model, @Valid MauSac ms, BindingResult validateResult) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("data", ms);
//            return "mau_sac/create";
//        }
//        mauSacRepository.create(ms);
//        return "redirect:/mau-sac/index";
//    }
//
//    @GetMapping("edit/{id}")
//    public String edit(Model model, @PathVariable("id") Integer id, HttpSession session) {
//        if (!AuthChecker.isLoggedIn(session)) {
//            session.setAttribute("error", "Bạn phải đăng nhập trước.");
//            return "redirect:/login";
//        }
//        MauSac listMSDetail = this.mauSacRepository.findById(id);
//        model.addAttribute("listMSDetail", listMSDetail);
//        return "mau_sac/edit";
//    }
//
//    @PostMapping("update/{id}")
//    public String update(@PathVariable("id") Integer id, @Valid MauSac ms, BindingResult validateResult, Model model) {
//        if (validateResult.hasErrors()) {
//            List<FieldError> listError = validateResult.getFieldErrors();
//            Map<String, String> errors = new HashMap<>();
//            for (FieldError fe : listError) {
//                errors.put(fe.getField(), fe.getDefaultMessage());
//            }
//            model.addAttribute("errors", errors);
//            model.addAttribute("listMSDetail", ms);
//            return "mau_sac/edit";
//        }
//        ms.setId(id);
//        mauSacRepository.update(ms);
//        return "redirect:/mau-sac/index";
//    }
}
