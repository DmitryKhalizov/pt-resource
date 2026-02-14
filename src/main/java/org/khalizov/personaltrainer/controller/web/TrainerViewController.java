package org.khalizov.personaltrainer.controller.web;

import jakarta.validation.Valid;
import org.khalizov.personaltrainer.config.CustomUserDetails;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.dto.PersonalTrainerUpdateDTO;
import org.khalizov.personaltrainer.model.Sport;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/trainers")
public class TrainerViewController {

    @Autowired
    private PersonalTrainerService personalTrainerService;


    @GetMapping
    public String showTrainers(Model model,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam(value = "sport", required = false) Sport sport) {
        List<PersonalTrainerDTO> trainers;
        if(sport != null) {
            trainers = personalTrainerService.getTrainersBySport(sport);
            model.addAttribute("selectedSport", sport);
        } else {
            trainers = personalTrainerService.getAllTrainers();
        }

        model.addAttribute("trainers", trainers);
        model.addAttribute("sports", Arrays.asList(Sport.values()));

        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isTrainer", userDetails.isTrainer());
        }
        return "trainers";
    }
    @GetMapping("/{trainerId}/edit")
    public String showEditForm(@PathVariable Integer trainerId,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model) {
        if (userDetails == null || !userDetails.isTrainer() ||
                !userDetails.getTrainer().getTrainerId().equals(trainerId)) {
            return "redirect:/home"; // Unauthorized
        }

        PersonalTrainerDTO trainer = personalTrainerService.getTrainerById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        PersonalTrainerUpdateDTO dto = new PersonalTrainerUpdateDTO();
        dto.setTrainerId(trainerId);

        dto.setFirstName(trainer.getFirstName());
        dto.setLastName(trainer.getLastName());
        dto.setNickname(trainer.getNickname());
        dto.setEmail(trainer.getEmail());
        dto.setDescription(trainer.getDescription());
        dto.setSport(trainer.getSport());
        dto.setExperienceYears(trainer.getExperienceYears());
        dto.setStatus(trainer.getStatus());
        if (trainer.getPrice() != null) {
            dto.setPricePerHour(trainer.getPrice().getPricePerHour());
            dto.setPriceFiveHours(trainer.getPrice().getPriceFiveHours());
            dto.setPriceTenHours(trainer.getPrice().getPriceTenHours());
            dto.setSpecialPrice(trainer.getPrice().getSpecialPrice());
        }
        if (!trainer.getLocations().isEmpty()) {
            dto.setLocationId(trainer.getLocations().iterator().next().getLocationId());
        }

        model.addAttribute("trainer", dto);
        model.addAttribute("sports", Arrays.asList(Sport.values()));
        return "trainer-edit";
    }

    @PostMapping("/{trainerId}/edit")
    public String updateTrainer(@PathVariable Integer trainerId,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                @Valid @ModelAttribute("trainer") PersonalTrainerUpdateDTO dto,
                                BindingResult result,
                                @RequestParam(value = "profileImageFile", required = false) MultipartFile profileImageFile,
                                Model model) {
        if (userDetails == null || !userDetails.isTrainer() ||
                !userDetails.getTrainer().getTrainerId().equals(trainerId)) {
            return "redirect:/home"; // Unauthorized
        }

        if (result.hasErrors()) {
            model.addAttribute("sports", Arrays.asList(Sport.values()));
            return "trainer-edit";
        }


        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            try {
                String contentType = profileImageFile.getContentType();
                if (contentType != null && contentType.startsWith("image/") &&
                        profileImageFile.getSize() <= 5 * 1024 * 1024) {
                    dto.setProfileImage(profileImageFile.getBytes());
                    dto.setProfileImageType(contentType);
                } else {
                    model.addAttribute("error", "Invalid image file");
                    model.addAttribute("sports", Arrays.asList(Sport.values()));
                    return "trainer-edit";
                }
            } catch (IOException e) {
                model.addAttribute("error", "Failed to upload image");
                model.addAttribute("sports", Arrays.asList(Sport.values()));
                return "trainer-edit";
            }
        }

        personalTrainerService.updateTrainer(trainerId, dto);
        return "redirect:/home";
    }
}

