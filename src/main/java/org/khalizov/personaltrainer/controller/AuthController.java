package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.config.CustomOAuth2User;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/home"})
    public String home(@AuthenticationPrincipal Object principal, Model model) {
        if (principal instanceof CustomOAuth2User oAuth2User) {
            model.addAttribute("user", oAuth2User.getUser());
        } else if (principal instanceof UserDetails userDetails) {
            User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("user", user);
        }
        return "home";
    }

}
