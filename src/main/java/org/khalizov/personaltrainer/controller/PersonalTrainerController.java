package org.khalizov.personaltrainer.controller;

import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.mapper.PersonalTrainerDTOService;
import org.khalizov.personaltrainer.model.Personal_Trainer;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class PersonalTrainerController {

    @Autowired
    private PersonalTrainerService personalTrainerService;

    @Autowired
    private PersonalTrainerDTOService personalTrainerDTOService;

    @GetMapping
    public List<PersonalTrainerDTO> getAllTrainers(){
        List<Personal_Trainer> trainers = personalTrainerService.getAllTrainers();
        return personalTrainerDTOService.getAllTrainerDTOs(trainers);
    }

}
