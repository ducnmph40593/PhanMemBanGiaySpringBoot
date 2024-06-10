package com.example.demo.controllers;

import com.example.demo.securityConfig.AuthChecker;
import com.example.demo.securityConfig.RoleChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping("admin/trang-chu")
    public String admin(HttpSession session, Model model ) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        session.setAttribute("role", "admin"); // Gán vai trò admin
        return "menu";
    }

    @GetMapping("nhan-vien/trang-chu")
    public String nhanVien(HttpSession session, Model model) {
        if (!AuthChecker.isLoggedIn(session)) {
            session.setAttribute("error", "Bạn phải đăng nhập trước.");
            return "redirect:/login";
        }
        session.setAttribute("role", "nhanVien"); // Gán vai trò nhân viên
        return "menu";
    }

}
