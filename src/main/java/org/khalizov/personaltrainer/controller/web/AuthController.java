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
    public String home(Model model,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       @RequestParam(value = "sort", required = false) String sort) {
        List<PersonalTrainerDTO> trainers;
        if (sort != null && !sort.isEmpty()) {
            Sort sortOrder = switch (sort) {
                case "name" -> Sort.by("firstName").ascending();
                case "sport" -> Sort.by("sport").ascending();
                case "priceAsc" -> Sort.by("price.pricePerHour").ascending();
                case "priceDesc" -> Sort.by("price.pricePerHour").descending();
                default -> Sort.unsorted();
            };
            trainers = personalTrainerService.findAllSorted(sortOrder);
        } else {
            trainers = personalTrainerService.findAllUnsorted();
        }

        model.addAttribute("trainers", trainers);
        model.addAttribute("sort", sort);

        boolean authenticated = userDetails != null;
        model.addAttribute("authenticated", authenticated);

        if (authenticated) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userType", userDetails.isTrainer() ? "Trainer" : "User");

            if (userDetails.isTrainer()) {
                model.addAttribute("sport", userDetails.getTrainer().getSport());
                model.addAttribute("trainerId", userDetails.getTrainer().getTrainerId());
            }
        }

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
        return "login"; //
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

    @GetMapping("/services")
    public String services(){
        return "services";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}
