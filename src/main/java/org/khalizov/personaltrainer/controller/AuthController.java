package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.config.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    /**
     * Home page - accessible to both authenticated and unauthenticated users
     * Uses @AuthenticationPrincipal to safely inject user details if logged in
     */
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            // User is authenticated
            model.addAttribute("authenticated", true);
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userType", userDetails.isTrainer() ? "Trainer" : "User");
            // Add other user-specific data as needed
            if (userDetails.isTrainer()) {
                model.addAttribute("sport", userDetails.getTrainer().getSport());
            }
        } else {
            // Unauthenticated user
            model.addAttribute("authenticated", false);
            model.addAttribute("message", "Please log in to access full features.");
        }
        return "home"; // Thymeleaf template: src/main/resources/templates/home.html
    }

    /**
     * Login page - shown when authentication is required
     */
    @GetMapping("/login")
    public String login(Model model) {
        // Optional: add error messages or custom login UI logic
        return "login"; // Template: login.html
    }

    /**
     * Handle login failure (if using form login)
     */
    @GetMapping("/login?error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    /**
     * Logout endpoint - triggers Spring Security logout
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/login?logout"; // Redirect to login with logout param
    }

    /**
     * Redirect root path to home
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}
