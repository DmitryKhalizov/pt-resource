package org.khalizov.personaltrainer.controller.api;

import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.UserCreateDTO;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.model.UserType;
import org.khalizov.personaltrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUserType(UserType.CLIENT);
        dto.setStatus(Status.ACTIVE);

        model.addAttribute("user", dto);
        return "registration";
    }

    @PostMapping("/register")
    public String handleRegistration(
            @Valid UserCreateDTO user,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("userTypes", UserType.values());
            model.addAttribute("statuses", Status.values());
            return "registration";
        }

        user.setUserType(UserType.CLIENT);
        user.setStatus(Status.ACTIVE);
        userService.createUser(user);
        return "redirect:/registration/success";
    }

    @GetMapping("/register/success")
    public String showSuccessPage(){
        return "registration-success";
    }


}
