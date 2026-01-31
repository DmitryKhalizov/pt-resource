package org.khalizov.personaltrainer.controller.api;

import jakarta.validation.Valid;
import org.khalizov.personaltrainer.dto.PersonalTrainerCreateDTO;
import org.khalizov.personaltrainer.model.Sport;
import org.khalizov.personaltrainer.model.Status;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class TrainerRegistrationController {

    @Autowired
    private PersonalTrainerService personalTrainerService;

    @GetMapping("/register/trainer")
    private String showTrainerRegistrationForm(Model model){
        PersonalTrainerCreateDTO dto = new PersonalTrainerCreateDTO();
        dto.setStatus(Status.ACTIVE);

        model.addAttribute("trainer", dto);
        model.addAttribute("sports", Arrays.asList(Sport.values()));
        return "trainer-registration";
    }

    @PostMapping("/register/trainer")
    private String handleTrainerRegistration(
            @Valid @ModelAttribute("trainer") PersonalTrainerCreateDTO trainer,
            BindingResult bindingResult,
            @RequestParam(value = "profileImageFile", required = false) MultipartFile profileImageFile,
            Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("sports", Arrays.asList(Sport.values()));
            return "trainer-registration";
        }

        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            try {
                String contentType = profileImageFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    model.addAttribute("error", "Please upload a valid image file");
                    model.addAttribute("sports", Arrays.asList(Sport.values()));
                    return "trainer-registration";
                }

                if (profileImageFile.getSize() > 5 * 1024 * 1024) {
                    model.addAttribute("error", "Image size must be less than 5MB");
                    model.addAttribute("sports", Arrays.asList(Sport.values()));
                    return "trainer-registration";
                }

                trainer.setProfileImage(profileImageFile.getBytes());
                trainer.setProfileImageType(contentType);
            } catch (IOException e) {
                model.addAttribute("error", "Failed to upload image: " + e.getMessage());
                model.addAttribute("sports", Arrays.asList(Sport.values()));
                return "trainer-registration";
            }
        }

        trainer.setStatus(Status.ACTIVE);
        personalTrainerService.createTrainer(trainer);
        return "redirect:/register/trainer/success";
    }

    @GetMapping("/register/trainer/success")
    public String showTrainerRegistrationSuccess() {
        return "trainer-registration-success";
    }

}
