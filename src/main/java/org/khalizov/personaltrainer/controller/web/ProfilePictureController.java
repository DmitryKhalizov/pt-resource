package org.khalizov.personaltrainer.controller.web;

import org.khalizov.personaltrainer.model.PersonalTrainer;
import org.khalizov.personaltrainer.repository.PersonalTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ProfilePictureController {

    @Autowired
    private PersonalTrainerRepository personalTrainerRepository;

    @GetMapping("/profile-pictures/{trainerId}")
    @ResponseBody
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Integer trainerId) {
        PersonalTrainer trainer = personalTrainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Trainer not found"));

        if (trainer.getProfileImage() == null || trainer.getProfileImage().length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        String contentType = trainer.getProfileImageType() != null
                ? trainer.getProfileImageType() : "image/jpeg";
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setCacheControl("max-age-86400");

        return ResponseEntity.ok()
                .headers(headers)
                .body(trainer.getProfileImage());
    }
}
