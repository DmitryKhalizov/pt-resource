package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.config.CustomUserDetails;
import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.mapper.PersonalTrainerDTOMapper;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trainers")
public class TrainerViewController {

    @Autowired
    private PersonalTrainerService personalTrainerService;

    @Autowired
    private PersonalTrainerDTOMapper personalTrainerDTOMapper;

    @GetMapping
    public String showTrainers(Model model,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<PersonalTrainerDTO> trainers = personalTrainerService.getAllTrainers();

        model.addAttribute("trainers", trainers);

        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("isTrainer", userDetails.isTrainer());
        }
        return "trainers";
    }
}
