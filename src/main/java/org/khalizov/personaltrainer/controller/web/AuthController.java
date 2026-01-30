package org.khalizov.personaltrainer.controller.web;

import org.khalizov.personaltrainer.config.CustomUserDetails;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {


    private final PersonalTrainerService personalTrainerService;

    public AuthController(PersonalTrainerService personalTrainerService) {
        this.personalTrainerService = personalTrainerService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails userDetails,
                       @RequestParam(name = "sort", required = false) String sortParam,
                       Model model) {
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

        List<PersonalTrainerDTO> trainers;
        if(sortParam == null || sortParam.isBlank()){
            trainers = personalTrainerService.findAllUnsorted();
        } else {
            Sort sort = mapSortParam(sortParam);
            trainers = personalTrainerService.findAllSorted(sort);
        }

        model.addAttribute("trainers", trainers);
        model.addAttribute("sort", sortParam);

        return "home";
    }

    private Sort mapSortParam(String sortParam) {
        return switch (sortParam) {
            case "name" -> Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());
            case "sport" -> Sort.by("sport").ascending();
            case "priceAsc" -> Sort.by("price.pricePerHour").ascending();
            case "priceDesc" -> Sort.by("price.pricePerHour").descending();
            default -> Sort.unsorted();
        };
    }


    @GetMapping("/login")
    public String login(Model model) {
        // Optional: add error messages or custom login UI logic
        return "login"; // Template: login.html
    }


    @GetMapping("/login?error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/login?logout";
    }


    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}
