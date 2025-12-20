package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.config.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomOAuth2User principal, Model model) {
        if(principal != null) {
            model.addAttribute("user", principal.getUser());
        }
        return "index";
    }
}
