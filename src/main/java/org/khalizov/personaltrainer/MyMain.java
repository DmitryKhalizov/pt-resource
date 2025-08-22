package org.khalizov.personaltrainer;

import org.khalizov.personaltrainer.model.Personal_Trainer;
import org.khalizov.personaltrainer.model.TrainerLocation;
import org.khalizov.personaltrainer.model.User;
import org.khalizov.personaltrainer.service.PersonalTrainerService;
import org.khalizov.personaltrainer.service.TrainerLocationService;
import org.khalizov.personaltrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyMain implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalTrainerService personalTrainerService;

    @Autowired
    private TrainerLocationService trainerLocationService;

    @Override
    public void run(String... args) {



        System.out.println("Current users: ");
        for (User u : userService.getAllUsers()) {
            System.out.println(u);
        }

        System.out.println("Current trainers: ");
        for (Personal_Trainer pt : personalTrainerService.getAllTrainers()) {
            System.out.println(pt);
        }

        System.out.println("Current trainer locations: ");
        for (TrainerLocation tl : trainerLocationService.getAllTrainerLocations()) {
            System.out.println(tl);
        }




    }

}
