package org.khalizov.personaltrainer.service;

import org.khalizov.personaltrainer.dto.PersonalTrainerDTO;
import org.khalizov.personaltrainer.mapper.PersonalTrainerDTOService;
import org.khalizov.personaltrainer.model.Personal_Trainer;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PersonalTrainerService {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @Autowired
    private PersonalTrainerDTOService personalTrainerDTOService;

    public List<Personal_Trainer> getAllTrainers() {

        return personalTrainerRepository.findAll();

    }

    public List<PersonalTrainerDTO> getAllTrainerDTOs(){
        List<Personal_Trainer> trainers = personalTrainerRepository.findAll();
        return personalTrainerDTOService.getAllTrainerDTOs(trainers);
    }

    public Optional<Personal_Trainer> getTrainerById(Integer id) {

        return personalTrainerRepository.findById(id);

    }

    public Optional<PersonalTrainerDTO> getTrainerDTOById(Integer id) {
        return personalTrainerRepository.findById(id)
                .map(personalTrainerDTOService::toDTO);
    }

    public Personal_Trainer saveTrainer(Personal_Trainer trainer) {
        return personalTrainerRepository.save(trainer);
    }


        public Personal_Trainer createTrainer(String bio, String nickname, Integer experienceYears, String certification, String profileImage) {
            Personal_Trainer personalTrainer = new Personal_Trainer();
            personalTrainer.setDescription(bio);
            personalTrainer.setNickname(nickname);
            personalTrainer.setExperienceYears(experienceYears);
            personalTrainer.setCertification(certification);
            personalTrainer.setProfileImage(profileImage);

        return personalTrainerRepository.save(personalTrainer);
    }

    public void deleteTrainer(Integer id) {

            personalTrainerRepository.deleteById(id);
    }
}